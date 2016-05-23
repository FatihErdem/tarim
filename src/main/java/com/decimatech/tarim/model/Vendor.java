package com.decimatech.tarim.model;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Vendor extends AbstractUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "vendor_id")
    private Long vendorId;

    @NotBlank
    @Column(name = "vendor_name")
    private String vendorName;

    @NotBlank
    @Column(name = "vendor_tel")
    private String vendorTel;

    @NotBlank
    @Column(name = "vendor_address")
    private String vendorAddress;

    @NotBlank
    @Column(name = "vendor_city")
    private String vendorCity;

    @NotBlank
    @Column(name = "vendor_district")
    private String vendorDistrict;

    @NotBlank
    @Column(name = "vendor_officer")
    private String vendorOfficer;

    @NotBlank
    @Column(name = "vendor_officer_tel")
    private String vendorOfficerTel;

    @NotBlank
    @Column(name = "vendor_email")
    private String vendorEmail;

    public Long getVendorId() {
        return vendorId;
    }

    public String getVendorEmail() {
        return vendorEmail;
    }

    public void setVendorEmail(String vendorEmail) {
        this.vendorEmail = vendorEmail;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorTel() {
        return vendorTel;
    }

    public void setVendorTel(String vendorTel) {
        this.vendorTel = vendorTel;
    }

    public String getVendorAddress() {
        return vendorAddress;
    }

    public void setVendorAddress(String vendorAddress) {
        this.vendorAddress = vendorAddress;
    }

    public String getVendorCity() {
        return vendorCity;
    }

    public void setVendorCity(String vendorCity) {
        this.vendorCity = vendorCity;
    }

    public String getVendorDistrict() {
        return vendorDistrict;
    }

    public void setVendorDistrict(String vendorDistrict) {
        this.vendorDistrict = vendorDistrict;
    }

    public String getVendorOfficer() {
        return vendorOfficer;
    }

    public void setVendorOfficer(String vendorOfficer) {
        this.vendorOfficer = vendorOfficer;
    }

    public String getVendorOfficerTel() {
        return vendorOfficerTel;
    }

    public void setVendorOfficerTel(String vendorOfficerTel) {
        this.vendorOfficerTel = vendorOfficerTel;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("VENDOR");
        List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
        list.add(simpleGrantedAuthority);
        return list;
    }

    @Override
    public String toString() {
        return "Bayi numarası: " + vendorId + " || " + vendorName;
    }
}