package dev.ivy.wallet.wallet.constant;

/**
 * <h1>Feedback type enum</h1>
 * Created by Qinyi.
 */
public enum FeedbackType {

    PASS("pass", "Coupon related feedback"),
    APP("app", "wallet app related feedback");

    /** feedback code */
    private String code;

    /** feedback type description */
    private String desc;

    FeedbackType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
