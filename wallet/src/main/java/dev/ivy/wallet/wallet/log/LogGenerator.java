package dev.ivy.wallet.wallet.log;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * <h1>Log Generator</h1>
 */
@Slf4j
public class LogGenerator {

    /**
     * <h2>Generate log</h2>
     * @param request {@link HttpServletRequest}
     * @param userId user id
     * @param action log type
     * @param info log info, can be NULL
     */
    public static void genLog(HttpServletRequest request, Long userId, String action, Object info) {
        log.info(
                JSON.toJSONString(
                        new LogObject(action, userId, System.currentTimeMillis(), request.getRemoteAddr(), info)
                )
        );
    }
}
