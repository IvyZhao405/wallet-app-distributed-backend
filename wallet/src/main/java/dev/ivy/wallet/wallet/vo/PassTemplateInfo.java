package dev.ivy.wallet.wallet.vo;

import dev.ivy.wallet.wallet.entity.Merchants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <h1>Coupon Info (coupon user hasn't claimed)</h1>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassTemplateInfo extends PassTemplate{

    /** Coupon template */
    private PassTemplate passTemplate;

    /** Coupon belong merchant */
    private Merchants merchants;


}
