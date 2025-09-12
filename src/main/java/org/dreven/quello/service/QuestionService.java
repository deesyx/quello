package org.dreven.quello.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dreven.quello.controller.dto.QuestionDTO;
import org.dreven.quello.dao.entity.Question;
import org.dreven.quello.dao.mapper.QuestionMapper;
import org.dreven.quello.common.transfer.QuestionTransfer;
import org.springframework.stereotype.Service;

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
}
