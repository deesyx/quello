package org.dreven.quello.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dreven.quello.common.enums.QuestionStatus;
import org.dreven.quello.common.transfer.QuestionTransfer;
import org.dreven.quello.controller.dto.base.PageResult;
import org.dreven.quello.controller.dto.question.QuestionCreateReq;
import org.dreven.quello.controller.dto.question.QuestionDTO;
import org.dreven.quello.controller.dto.question.QuestionSearchReq;
import org.dreven.quello.dao.entity.Question;
import org.dreven.quello.dao.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionMapper questionMapper;

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
        question.setQuestionId(generateQuestionId());
        question.setStatus(QuestionStatus.PENDING);
        questionMapper.insert(question);
        return true;
    }

    private String generateQuestionId() {
        return "Q" + System.currentTimeMillis();
    }
}
