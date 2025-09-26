package org.dreven.quello.dao.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dreven.quello.dao.entity.base.BaseDO;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionFrequency extends BaseDO {

    /**
     * 数据日期
     */
    private LocalDate dataDate;

    /**
     * 问题类别
     */
    private String category;

    /**
     * 频率
     */
    private Integer frequency;
}
