package dev.ivyzhao.wallet.merchants.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * <h1>Merchant Object Model</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "merchants")
public class Merchants {

    /** Merchant id, primaryKey */
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    /** Merchatn name, unique */
    @Basic
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    /** Merchant logo URL */
    @Basic
    @Column(name = "logo_url", nullable = false)
    private String logoUrl;

    /** Merchant business license */
    @Basic
    @Column(name = "business_license_url", nullable = false)
    private String businessLicenseUrl;

    /** Merchant phone number */
    @Basic
    @Column(name = "phone", nullable = false)
    private String phone;

    /** Merchant address */
    @Basic
    @Column(name = "address", nullable = false)
    private String address;

    /** Merchant passed audit or not **/
    @Basic
    @Column(name = "pass_audit", nullable = false)
    private Boolean passAudit = false;

}
