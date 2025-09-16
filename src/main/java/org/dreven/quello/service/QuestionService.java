package org.dreven.quello.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dreven.quello.common.enums.QuestionStatus;
import org.dreven.quello.common.transfer.QuestionTransfer;
import org.dreven.quello.controller.dto.base.PageResult;
import org.dreven.quello.controller.dto.question.QuestionCreateReq;
import org.dreven.quello.controller.dto.question.QuestionDTO;
import org.dreven.quello.controller.dto.question.QuestionSearchReq;
import org.dreven.quello.controller.dto.question.QuestionUpdateReq;
import org.dreven.quello.dao.entity.Question;
import org.dreven.quello.dao.mapper.QuestionMapper;
import org.dreven.quello.exception.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static org.dreven.quello.exception.GlobalErrorCodeConstants.NOT_FOUND;
import static org.dreven.quello.exception.GlobalErrorCodeConstants.REPEATED_REQUESTS;

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
        question.setQuestionId("Q" + System.currentTimeMillis());
        question.setStatus(QuestionStatus.PENDING);
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
}
