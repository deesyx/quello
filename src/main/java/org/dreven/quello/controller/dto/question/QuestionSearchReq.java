package org.dreven.quello.controller.dto.question;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dreven.quello.common.enums.QuestionStatus;
import org.dreven.quello.common.enums.QuestionType;
import org.dreven.quello.controller.dto.base.PageParam;

@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionSearchReq extends PageParam {
    private String title;
    private QuestionStatus status;
    private QuestionType questionType;
}
