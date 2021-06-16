package dev.ivy.wallet.wallet.constant;

/**
 * App constants
 */
public class Constants {
    /** Merchant coupon Kafka Topic */
    public static final String TEMPLATE_TOPIC = "merchants-template";

    /** token file temp folder */
    public static final String TOKEN_DIR = "/tmp/token/";

    /** used token file suffix */
    public static final String USED_TOKEN_SUFFIX = "_";

    /** user count redis key */
    public static final String USE_COUNT_REDIS_KEY = "wallet-user-count";

    /**
     * <h2>User HBase Table</h2>
     * */
    public class UserTable {

        /** User HBase table name */
        public static final String TABLE_NAME = "pb:user";

        /** User Info column family */
        public static final String FAMILY_B = "b";

        /** Username */
        public static final String NAME = "name";

        /** Age */
        public static final String AGE = "age";

        /** Sex */
        public static final String SEX = "sex";

        /** User Extra Info column family */
        public static final String FAMILY_O = "o";

        /** phone number */
        public static final String PHONE = "phone";

        /** address */
        public static final String ADDRESS = "address";
    }

    /**
     * <h2>PassTemplate HBase Table</h2>
     * */
    public class PassTemplateTable {

        /** PassTemplate HBase table name */
        public static final String TABLE_NAME = "pb:passtemplate";

        /** Pass Info column family */
        public static final String FAMILY_B = "b";

        /** Merchant id */
        public static final String ID = "id";

        /** Coupon title*/
        public static final String TITLE = "title";

        /** Coupon summary */
        public static final String SUMMARY = "summary";

        /** Coupon description */
        public static final String DESC = "desc";

        /** Whether coupon has token */
        public static final String HAS_TOKEN = "has_token";

        /** Coupon background color */
        public static final String BACKGROUND = "background";

        /** Constraints Info column family */
        public static final String FAMILY_C = "c";

        /** Max quantity */
        public static final String LIMIT = "limit";

        /** Coupon start time */
        public static final String START = "start";

        /** Coupon end time */
        public static final String END = "end";
    }

    /**
     * <h2>Pass HBase Table</h2>
     * */
    public class PassTable {

        /** Pass HBase table name */
        public static final String TABLE_NAME = "pb:pass";

        /** User Pass Info column family  */
        public static final String FAMILY_I = "i";

        /** User id */
        public static final String USER_ID = "user_id";

        /** Pass id */
        public static final String TEMPLATE_ID = "template_id";

        /** Coupon token */
        public static final String TOKEN = "token";

        /** Claimed date */
        public static final String ASSIGNED_DATE = "assigned_date";

        /** Consumed date */
        public static final String CON_DATE = "con_date";
    }

    /**
     * <h2>Feedback Hbase Table</h2>
     * */
    public class Feedback {

        /** Feedback HBase table name */
        public static final String TABLE_NAME = "pb:feedback";

        /** Feedback column family */
        public static final String FAMILY_I = "i";

        /** User id */
        public static final String USER_ID = "user_id";

        /** Feedback type */
        public static final String TYPE = "type";

        /** PassTemplate RowKey, If it is app 评论, then -1 */
        public static final String TEMPLATE_ID = "template_id";

        /** Feedback content */
        public static final String COMMENT = "comment";
    }
}
