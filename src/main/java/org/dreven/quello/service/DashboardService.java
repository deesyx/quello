package org.dreven.quello.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dreven.quello.common.enums.Period;
import org.dreven.quello.common.enums.QuestionStatus;
import org.dreven.quello.common.utils.QuarterUtils;
import org.dreven.quello.controller.dto.dashboard.DashDetailRsp;
import org.dreven.quello.controller.dto.dashboard.DashboardOverviewRsp;
import org.dreven.quello.controller.dto.dashboard.DashboardSearchReq;
import org.dreven.quello.dao.entity.Question;
import org.dreven.quello.dao.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardService {

    private final QuestionMapper questionMapper;

    public DashDetailRsp getDetail(DashboardSearchReq req) {
        definePeriodDate(LocalDate.now(), req);

        DashDetailRsp rsp = new DashDetailRsp();
        List<Question> curQuestions = questionMapper.selectList(new LambdaQueryWrapper<Question>()
                .ge(Question::getCreatedAt, req.getPeriodStartDate())
                .le(Question::getCreatedAt, req.getPeriodEndDate())
        );
        List<Question> preQuestions = questionMapper.selectList(new LambdaQueryWrapper<Question>()
                .ge(Question::getCreatedAt, req.getPeriodStartDatePre())
                .le(Question::getCreatedAt, req.getPeriodEndDatePre())
        );

        DashboardOverviewRsp overview = getOverview(curQuestions, preQuestions, req);
        rsp.setOverview(overview);

        return rsp;
    }

    public void definePeriodDate(LocalDate curDate, DashboardSearchReq req) {
        if (req.getPeriod() == Period.YEAR) {
            req.setPeriodStartDate(Year.of(curDate.getYear()).atMonth(1).atDay(1));
            req.setPeriodEndDate(Year.of(curDate.getYear()).atMonth(12).atDay(31));
            req.setPeriodStartDatePre(Year.of(curDate.getYear() - 1).atMonth(1).atDay(1));
            req.setPeriodEndDatePre(Year.of(curDate.getYear() - 1).atMonth(12).atDay(31));
        } else if (req.getPeriod() == Period.QUARTER) {
            req.setPeriodStartDate(QuarterUtils.getQuarterStartDate(curDate));
            req.setPeriodEndDate(QuarterUtils.getQuarterEndDate(curDate));
            req.setPeriodStartDatePre(QuarterUtils.getPreviousQuarterStartDate(curDate));
            req.setPeriodEndDatePre(QuarterUtils.getPreviousQuarterEndDate(curDate));
        } else if (req.getPeriod() == Period.MONTH) {
            req.setPeriodStartDate(curDate.with(TemporalAdjusters.firstDayOfMonth()));
            req.setPeriodEndDate(curDate.with(TemporalAdjusters.lastDayOfMonth()));
            req.setPeriodStartDatePre(curDate.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()));
            req.setPeriodEndDatePre(curDate.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()));
        } else {
            LocalDate curWeekStartDate = curDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            req.setPeriodStartDate(curWeekStartDate);
            req.setPeriodEndDate(curWeekStartDate.plusDays(6));
            req.setPeriodStartDatePre(curWeekStartDate.minusWeeks(1));
            req.setPeriodEndDatePre(curWeekStartDate.minusWeeks(1).plusDays(6));
        }
    }

    private DashboardOverviewRsp getOverview(List<Question> curQuestions, List<Question> preQuestions, DashboardSearchReq req) {
        DashboardOverviewRsp overview = new DashboardOverviewRsp();
        Long curTotalQuestions = questionMapper.selectCount(new LambdaQueryWrapper<Question>()
                .le(Question::getCreatedAt, req.getPeriodEndDate())
        );
        Long preTotalQuestions = questionMapper.selectCount(new LambdaQueryWrapper<Question>()
                .le(Question::getCreatedAt, req.getPeriodEndDatePre())
        );
        overview.setTotalQuestions(curTotalQuestions.intValue());
        overview.setTotalQuestionsGr(BigDecimal.valueOf(curTotalQuestions - preTotalQuestions).divide(BigDecimal.valueOf(preTotalQuestions), 4, RoundingMode.HALF_UP));

        overview.setNewQuestions(curQuestions.size());
        overview.setNewQuestionsGr(BigDecimal.valueOf(curQuestions.size() - preQuestions.size()).divide(BigDecimal.valueOf(preQuestions.size()), 4, RoundingMode.HALF_UP));

        long curAdaptionQuestionCount = curQuestions.stream()
                .filter(it -> !QuestionStatus.REVIEWING.equals(it.getStatus()) && !QuestionStatus.CLOSED.equals(it.getStatus()))
                .count();
        long preAdaptionQuestionCount = preQuestions.stream()
                .filter(it -> !QuestionStatus.REVIEWING.equals(it.getStatus()) && !QuestionStatus.CLOSED.equals(it.getStatus()))
                .count();
        BigDecimal curAdaptionRate = BigDecimal.valueOf(curAdaptionQuestionCount).divide(BigDecimal.valueOf(curQuestions.size()), 4, RoundingMode.HALF_UP);
        BigDecimal preAdaptionRate = BigDecimal.valueOf(preAdaptionQuestionCount).divide(BigDecimal.valueOf(preQuestions.size()), 4, RoundingMode.HALF_UP);
        overview.setAdoptionRate(curAdaptionRate);
        overview.setAdoptionRateGr(curAdaptionRate.subtract(preAdaptionRate));

        long curResolvedQuestions = curQuestions.stream()
                .filter(it -> QuestionStatus.RESOLVED.equals(it.getStatus()))
                .count();
        long preResolvedQuestions = preQuestions.stream()
                .filter(it -> QuestionStatus.RESOLVED.equals(it.getStatus()))
                .count();
        overview.setResolvedQuestions((int) curResolvedQuestions);
        if (preResolvedQuestions != 0) {
            overview.setResolvedQuestionsGr(BigDecimal.valueOf(curResolvedQuestions - preResolvedQuestions).divide(BigDecimal.valueOf(preResolvedQuestions), 4, RoundingMode.HALF_UP));
        } else {
            overview.setResolvedQuestionsGr(BigDecimal.ONE);
        }

        final LocalDate now = LocalDate.now();
        long curOvertimeQuestions = curQuestions.stream()
                .filter(it -> it.getPlannedResolutionDate() != null)
                .filter(it -> {
                    if (it.getActualResolutionDate() != null) {
                        return it.getActualResolutionDate().isAfter(it.getPlannedResolutionDate());
                    } else {
                        return now.isAfter(it.getPlannedResolutionDate());
                    }
                })
                .count();
        long preOvertimeQuestions = preQuestions.stream()
                .filter(it -> it.getPlannedResolutionDate() != null)
                .filter(it -> {
                    if (it.getActualResolutionDate() != null) {
                        return it.getActualResolutionDate().isAfter(it.getPlannedResolutionDate());
                    } else {
                        return now.isAfter(it.getPlannedResolutionDate());
                    }
                })
                .count();
        BigDecimal curOvertimeRate = BigDecimal.valueOf(curOvertimeQuestions).divide(BigDecimal.valueOf(curQuestions.size()), 4, RoundingMode.HALF_UP);
        BigDecimal preOvertimeRate = BigDecimal.valueOf(preOvertimeQuestions).divide(BigDecimal.valueOf(preQuestions.size()), 4, RoundingMode.HALF_UP);
        overview.setOvertimeRate(curOvertimeRate);
        overview.setOvertimeRateGr(curOvertimeRate.subtract(preOvertimeRate));

        long curOnScheduleResolutionRate = curQuestions.stream()
                .filter(it -> it.getPlannedResolutionDate() != null && it.getActualResolutionDate() != null)
                .filter(it -> it.getPlannedResolutionDate().isAfter(it.getActualResolutionDate()))
                .count();
        long preOnScheduleResolutionRate = preQuestions.stream()
                .filter(it -> it.getPlannedResolutionDate() != null && it.getActualResolutionDate() != null)
                .filter(it -> it.getPlannedResolutionDate().isAfter(it.getActualResolutionDate()))
                .count();
        BigDecimal curOnScheduleResolutionRateRate = BigDecimal.valueOf(curOnScheduleResolutionRate).divide(BigDecimal.valueOf(curQuestions.size()), 4, RoundingMode.HALF_UP);
        BigDecimal preOnScheduleResolutionRateRate = BigDecimal.valueOf(preOnScheduleResolutionRate).divide(BigDecimal.valueOf(preQuestions.size()), 4, RoundingMode.HALF_UP);
        overview.setOnScheduleResolutionRate(curOnScheduleResolutionRateRate);
        overview.setOnScheduleResolutionRateGr(curOnScheduleResolutionRateRate.subtract(preOnScheduleResolutionRateRate));

        return overview;
    }
}
