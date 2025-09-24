package org.dreven.quello.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.validation.Valid;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.dreven.quello.controller.dto.dashboard.DashQuestionTrendItemRsp;
import org.dreven.quello.controller.dto.dashboard.DashboardSearchReq;
import org.dreven.quello.controller.dto.question.QuestionSearchReq;
import org.dreven.quello.dao.entity.Question;

import java.util.List;

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

    List<Question> search(Page<Question> page, @Param("req") QuestionSearchReq req);

    List<DashQuestionTrendItemRsp> getQuestionTrendsByWeek(@Param("req") DashboardSearchReq req);

    List<DashQuestionTrendItemRsp> getQuestionTrendsByMonth(@Param("req") DashboardSearchReq req);
}