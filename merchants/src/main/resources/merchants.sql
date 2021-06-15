CREATE TABLE 'merchants' (
    'id' int(10) unsigned NOT NULL AUTO_INCREMENT,
    'name' varchar(64) COLLATE uft8_bin NOT NULL COMMENT 'Merchant Name',
    'logo_url' varchar(256) COLLATE uft8_bin NOT NULL COMMENT 'Merchant logo',
    'business_license_url' varchar(256) COLLATE uft8_bin NOT NULL COMMENT 'Merchant Business License',
    'phone' varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'Merchant Phone Number',
    'address' varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'Merchant Address',
    'pass_audit' BOOLEAN NOT NULL COMMENT 'Pass audit or not',
    PRIMARY KEY ('id')
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARACTER=utf8;