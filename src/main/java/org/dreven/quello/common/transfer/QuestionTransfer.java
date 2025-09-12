package org.dreven.quello.common.transfer;

import org.dreven.quello.controller.dto.question.QuestionDTO;
import org.dreven.quello.dao.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QuestionTransfer {
    
    QuestionTransfer INSTANCE = Mappers.getMapper(QuestionTransfer.class);
    
    QuestionDTO toDTO(Question question);
    
    Question toEntity(QuestionDTO questionDTO);
}