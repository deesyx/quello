package org.dreven.quello.controller.dto.question;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dreven.quello.common.enums.QuestionStatus;
import org.dreven.quello.common.enums.QuestionType;
import org.dreven.quello.controller.dto.base.PageParam;

@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionSearchReq extends PageParam {

    @Schema(description = "标题")
    private String title;

    @Schema(description = "状态")
    private QuestionStatus status;

    @Schema(description = "问题类型")
    private QuestionType questionType;
}
