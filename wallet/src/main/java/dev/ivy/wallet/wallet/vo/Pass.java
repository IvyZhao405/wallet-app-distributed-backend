package dev.ivy.wallet.wallet.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <h1>User claimed pass object </h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pass {

    /** User id */
    private Long userId;

    /** pass's RowKey in Hbase */
    private String rowKey;

    /** PassTemplate's RowKey in Hbase */
    private String templateId;

    /** pass/copon token, might be null, if so fill -1 */
    private String token;

    /** assigned date */
    private Date assignedDate;

    /** consumed date */
    private Date conDate;

}
