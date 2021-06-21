package dev.ivy.wallet.wallet.vo;

import com.google.common.base.Enums;
import dev.ivy.wallet.wallet.constant.FeedbackType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>Use feedback</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

    /** User Id */
    private Long userId;

    /**FeedBack type */
    private String type;

    /**PassTemplate RowKey, if it's app feedback type then don't have value */
    private String templateId;

    /**feedback content*/
    private String comment;

    public boolean validate() {
        FeedbackType feedbackType = Enums.getIfPresent(
                FeedbackType.class, this.type.toUpperCase()
        ).orNull();

        return !(null == feedbackType || null == comment);
    }

}

