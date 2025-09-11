package org.dreven.quello.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.dreven.quello.dao.entity.Question;

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

}