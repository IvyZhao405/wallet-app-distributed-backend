package dev.ivy.wallet.wallet.constant;

/**
 * <h1>Feedback type enum</h1>
 * Created by Qinyi.
 */
public enum FeedbackType {

    PASS(1, "Coupon related feedback"),
    APP(2, "wallet app related feedback");

    /** feedback code */
    private Integer code;

    /** feedback type description */
    private String desc;

    FeedbackType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
