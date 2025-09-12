package org.dreven.quello.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.dreven.quello.controller.dto.question.QuestionSearchReq;
import org.dreven.quello.dao.entity.Question;

import java.util.List;

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

    List<Question> search(Page<Question> page, @Param("req") QuestionSearchReq req);
}