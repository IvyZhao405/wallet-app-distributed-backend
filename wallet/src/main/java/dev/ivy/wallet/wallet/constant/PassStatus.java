package dev.ivy.wallet.wallet.constant;

public enum PassStatus {
    UNUSED(1, "unused"),
    USED(2, "used"),
    ALL(3, "all collected");

    /** status code */
    private Integer code;

    /** status description */
    private String desc;

    PassStatus(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getDesc(){
        return this.desc;
    }
}
