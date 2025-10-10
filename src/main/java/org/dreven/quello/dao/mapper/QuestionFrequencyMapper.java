package org.dreven.quello.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.dreven.quello.controller.dto.dashboard.DashDistributionItemRsp;
import org.dreven.quello.dao.entity.QuestionFrequency;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface QuestionFrequencyMapper extends BaseMapper<QuestionFrequency> {

    LocalDate getMaxDataDate();

    List<DashDistributionItemRsp> getQuestionDistributions(@Param("dataDate")LocalDate dataDate);

}