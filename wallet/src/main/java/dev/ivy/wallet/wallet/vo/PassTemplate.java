package dev.ivy.wallet.wallet.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <h1>Released Pass object class</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassTemplate {

    /** Merchant id */
    private Integer id;

    /** Coupon title */
    private String title;

    /** Coupon summary */
    private String summary;

    /** Coupon description */
    private String desc;

    /** Coupon limit */
    private Long limit;

    /** Coupon has token, for merchant to verify*/
    private Boolean hasToken;

    /** Coupon background color */
    private Integer background;

    /** Coupon start date */
    private Date start;

    /** Coupon end date */
    private Date end;

}
