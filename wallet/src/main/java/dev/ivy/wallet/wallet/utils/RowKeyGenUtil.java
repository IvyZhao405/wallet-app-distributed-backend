package dev.ivy.wallet.wallet.utils;

import dev.ivy.wallet.wallet.vo.Feedback;
import dev.ivy.wallet.wallet.vo.GainPassTemplateRequest;
import dev.ivy.wallet.wallet.vo.PassTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * <h1>RowKey generator util </h1>
 */
@Slf4j
public class RowKeyGenUtil {

    /**
     * <h2>RowKey generated for PassTemplate table</h2>
     * @param passTemplate {@link PassTemplate}
     * @return String RowKey
     */
    public static String genPassTemplateRowKey(PassTemplate passTemplate){
        String passInfo = String.valueOf(passTemplate.getId()) + "_" + passTemplate.getTitle();
        String rowKey = DigestUtils.md5Hex(passInfo);
        log.info("GenPassTemplateRowKey: {}, {}", passInfo, rowKey);
        return rowKey;
    }

    /**
     * <h2>Based on coupon claim request, generate RowKey
     *     only used when claiming coupon </h2>
     *  Pass RowKey = reversed(userId) + inverse(timestamp) (long - current time) + PassTemplate RowKey
     *  inverse(timestamp) has smaller value
     * @param request {@link GainPassTemplateRequest}
     * @return
     */
    public static String genPassRowKey(GainPassTemplateRequest request) {

        return new StringBuilder(String.valueOf(request.getUserId())).reverse().toString()
                + (Long.MAX_VALUE - System.currentTimeMillis())
                + genPassTemplateRowKey(request.getPassTemplate());
    }

    /**
     * <h2>Rowkey generated for Feedback table</h2>
     * @param feedback {@link Feedback}
     * @return RowkKey
     * Same user's feedback is best kept close to each other.
     */
    public static String genFeedbackRowKey(Feedback feedback) {
        //userId's prefix is user count, so by reverse we get a random number.
        //Later date's feedback would have smaller value by (Long.MAX_VALUE - time)
        return new StringBuilder(String.valueOf(feedback.getUserId())).reverse().toString()+
                (Long.MAX_VALUE - System.currentTimeMillis());

    }
}
