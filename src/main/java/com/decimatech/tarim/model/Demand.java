package com.decimatech.tarim.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Entity
@Table(name = "demand")
public class Demand extends AbstractBaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "demand_id")
    private Long demandId;

    @Column(name = "vendor_id")
    private Long vendorId;

    @NotBlank
    @Column(name = "customer_name")
    private String customerName;

    @NotBlank
    @Column(name = "customer_surname")
    private String customerSurname;

    @NotBlank
    @Column(name = "customer_tel")
    private String customerTel;

    @NotBlank
    @Column(name = "customer_address")
    private String customerAddress;

    @NotBlank
    @Column(name = "customer_city")
    private String customerCity;

    @NotBlank
    @Column(name = "customer_district")
    private String customerDistrict;

    @Column(name = "demand_note")
    private String demandNote;


    @Column(name = "demand_state")
    private String demandState = "OPEN";

    @NotBlank
    @Column(name = "demand_type")
    private String demandType;

    @Column(name = "demand_owner")
    private String demandOwner;

    @Column(name = "unread")
    private boolean unread = false;

    public enum State {
        OPEN("Açık"),
        IN_PROGRESS("Servis Aşamasında"),
        COMPLETED("Tamamlandı"),
        REJECTED("Reddedildi"),
        UNCOMPLETED("Yarım Kaldı");

        private final String displayName;

        State(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum Type {
        SETUP("İlk Kurulum"),
        WARRANTY("Garanti Kapsamında Servis"),
        NOTWARRANTY("Garanti Dışı Servis");

        private final String displayName;

        Type(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public Long getDemandId() {
        return demandId;
    }

    public void setDemandId(Long demandId) {
        this.demandId = demandId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerSurname() {
        return customerSurname;
    }

    public void setCustomerSurname(String customerSurname) {
        this.customerSurname = customerSurname;
    }

    public String getCustomerTel() {
        return customerTel;
    }

    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getCustomerDistrict() {
        return customerDistrict;
    }

    public void setCustomerDistrict(String customerDistrict) {
        this.customerDistrict = customerDistrict;
    }

    public String getDemandNote() {
        return demandNote;
    }

    public void setDemandNote(String demandNote) {
        this.demandNote = demandNote;
    }

    public String getDemandState() {
        return demandState;
    }

    public void setDemandState(String demandState) {
        this.demandState = demandState;
    }

    public String getDemandType() {
        return demandType;
    }

    public void setDemandType(String demandType) {
        this.demandType = demandType;
    }

    public String getDemandOwner() {
        return demandOwner;
    }

    public void setDemandOwner(String demandOwner) {
        this.demandOwner = demandOwner;
    }

    public boolean isUnread() {
        return unread;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }
}
