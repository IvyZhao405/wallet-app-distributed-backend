package dev.ivy.wallet.wallet.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>Log Object</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogObject {

    /** Log action type */
    private String action;

    /** User id */
    private Long userId;

    /** Current timestamp */
    private Long timestamp;

    /**Client Ip */
    private String remoteIp;

    /** log content */
    private Object info = null;
}
