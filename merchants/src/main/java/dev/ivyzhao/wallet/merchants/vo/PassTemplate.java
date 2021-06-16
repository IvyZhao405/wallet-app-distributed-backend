package dev.ivyzhao.wallet.merchants.vo;

import dev.ivyzhao.wallet.merchants.constant.ErrorCode;
import dev.ivyzhao.wallet.merchants.dao.MerchantsDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <h1>Merchant leased coupon class</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassTemplate {

    /** Merchant id **/
    private Integer id;

    /** Coupon title */
    private String title;

    /** Coupon summary */
    private String summary;

    /** Coupon detailed description */
    private String desc;

    /** Max quantity */
    private Long limit;

    /** Coupon has token for verification */
    private Boolean hasToken; //token is saved in Redis Set, retrieve token from Redis

    /** Coupon background color */
    private Integer background;

    /** Coupon start time */
    private Date start;

    /** Coupon end time */
    private Date end;

    /**
     * <h2>Validate coupon</h2>
     * @param merchantsDao {@link MerchantsDao}
     * @return {@link ErrorCode}
     */
    public ErrorCode validate(MerchantsDao merchantsDao) {
        if (merchantsDao.findById(id) == null) {
            return ErrorCode.MERCHANTS_NOT_EXISTS;
        }
        return ErrorCode.SUCCESS;
    }
}
