package dev.ivy.wallet.wallet.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>User's request to claim coupon</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GainPassTemplateRequest {

    /** User id */
    private Long userId;

    /** PassTemplate object */
    private PassTemplate passTemplate;
}
