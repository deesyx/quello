package org.dreven.quello.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dreven.quello.common.enums.QuestionStatus;
import org.dreven.quello.common.transfer.QuestionTransfer;
import org.dreven.quello.common.utils.JsonUtils;
import org.dreven.quello.controller.dto.base.PageResult;
import org.dreven.quello.controller.dto.question.QuestionCreateReq;
import org.dreven.quello.controller.dto.question.QuestionDTO;
import org.dreven.quello.controller.dto.question.QuestionSearchReq;
import org.dreven.quello.controller.dto.question.QuestionUpdateReq;
import org.dreven.quello.dao.entity.Question;
import org.dreven.quello.dao.entity.QuestionFrequency;
import org.dreven.quello.dao.mapper.QuestionFrequencyMapper;
import org.dreven.quello.dao.mapper.QuestionMapper;
import org.dreven.quello.exception.ServiceException;
import org.dreven.quello.service.bo.QuestionCategory;
import org.dreven.quello.service.llm.DashScopeLLMClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.dreven.quello.exception.GlobalErrorCodeConstants.NOT_FOUND;
import static org.dreven.quello.exception.GlobalErrorCodeConstants.REPEATED_REQUESTS;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionMapper questionMapper;
    private final DashScopeLLMClient dashScopeLLMClient;
    private final QuestionFrequencyMapper questionFrequencyMapper;

    public QuestionDTO getDetail(String questionId) {
        Question question = questionMapper.selectOne(new LambdaQueryWrapper<Question>()
                .eq(Question::getQuestionId, questionId)
        );
        return QuestionTransfer.INSTANCE.toDTO(question);
    }

    public PageResult<QuestionDTO> search(QuestionSearchReq req) {
        Page<Question> page = new Page<>(req.getPageNo(), req.getPageSize());
        List<Question> ans = questionMapper.search(page, req);

        PageResult<QuestionDTO> pageResult = new PageResult<>();
        pageResult.setList(ans.stream().map(QuestionTransfer.INSTANCE::toDTO).toList());
        pageResult.setPageSize(page.getSize());
        pageResult.setCurrentPage(page.getCurrent());
        pageResult.setTotalPages(page.getPages());
        pageResult.setTotal(page.getTotal());
        return pageResult;
    }

    public Boolean createQuestion(QuestionCreateReq req) {
        Question question = QuestionTransfer.INSTANCE.toEntity(req);
        question.setQuestionId("Q" + System.currentTimeMillis());
        question.setStatus(QuestionStatus.REVIEWING);
        questionMapper.insert(question);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean updateQuestion(QuestionUpdateReq req) {
        Question question = questionMapper.selectOne(new LambdaQueryWrapper<Question>()
                .eq(Question::getQuestionId, req.getQuestionId())
                .last("for update")
        );

        if (question == null) {
            throw new ServiceException(NOT_FOUND).setMessage("问题不存在");
        }
        if (!Objects.equals(question.getVersion(), req.getVersion())) {
            throw new ServiceException(REPEATED_REQUESTS).setMessage("问题版本不一致，刷新后重试");
        }

        BeanUtils.copyProperties(req, question, "questionId", "version");
        int count = questionMapper.updateById(question);
        if (count == 0) {
            throw new ServiceException(REPEATED_REQUESTS).setMessage("问题版本不一致，刷新后重试");
        }
        return true;
    }

    public void classifyQuestions() {
        List<Question> questions = questionMapper.selectList(new LambdaQueryWrapper<Question>()
                .isNotNull(Question::getTitle)
        );
        Set<String> questionTitles = questions.stream().map(Question::getTitle).collect(Collectors.toSet());
        String systemMessage = """
                ## 任务
                将问题分类并统计频率，然后返回json格式的答案
                
                ## 输出
                输出为json格式，例如
                [
                  {
                    "category": "xxx",
                    "frequency": 10
                  },
                  {
                    "category": "yyy",
                    "frequency": 3
                  }
                ]
                """;
        String userMessage = "问题以数组形式给出，为：" + JsonUtils.toJsonString(questionTitles);
        List<QuestionCategory> questionCategories = dashScopeLLMClient.getJsonAnswer("qwen-max", systemMessage, userMessage, new TypeReference<>() {
        });

        LocalDate now = LocalDate.now();
        List<QuestionFrequency> questionFrequencies = questionCategories.stream().map(it -> {
            QuestionFrequency questionFrequency = new QuestionFrequency();
            questionFrequency.setCategory(it.getCategory());
            questionFrequency.setFrequency(it.getFrequency());
            questionFrequency.setDataDate(now);
            return questionFrequency;
        }).toList();

        questionFrequencyMapper.delete(new LambdaQueryWrapper<QuestionFrequency>()
                .eq(QuestionFrequency::getDataDate, now)
        );
        for (QuestionFrequency questionFrequency : questionFrequencies) {
            questionFrequencyMapper.insert(questionFrequency);
        }
    }
}
