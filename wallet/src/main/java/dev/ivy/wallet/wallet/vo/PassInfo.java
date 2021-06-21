package dev.ivy.wallet.wallet.vo;


import dev.ivy.wallet.wallet.entity.Merchants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>User claimed coupon info (for returning to user)</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassInfo {

    /** Coupon */
    private Pass pass;

    /** Coupon template */
    private PassTemplate passTemplate;

    /** Coupon's belong merchant */
    private Merchants merchants;
}
