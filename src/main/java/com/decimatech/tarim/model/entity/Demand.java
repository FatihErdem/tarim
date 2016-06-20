package com.decimatech.tarim.model.entity;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "demand")
public class Demand extends AbstractBaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "demand_id")
    private Long demandId;

    @Column(name = "vendor_id")
    private Long vendorId;

    @Column(name = "maintain_id")
    private Long maintainId;

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

    @NotNull
    @Column(name = "customer_city")
    private Long customerCity;

    @NotNull
    @Column(name = "customer_district")
    private Long customerDistrict;

    @NotNull
    @Column(name = "demand_note")
    private String demandNote;

    @Column(name = "demand_state")
    private String demandState = "OPEN";

    @NotBlank
    @Column(name = "demand_type")
    private String demandType;

    @Column(name = "demand_owner")
    private String demandOwner;

    @Column(name = "unread_by_vendor")
    private boolean unreadVendor;

    @Column(name = "unread_by_admin")
    private boolean unreadAdmin;

    public enum State {
        OPEN("Açık"),
        IN_PROGRESS("Servis Aşamasında"),
        COMPLETED("Tamamlandı"),
        REJECTED("Reddedildi"),
        UNCOMPLETED("Yarım Kaldı"),
        APPROVED("Onaylandı");

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

    public boolean isUnreadVendor() {
        return unreadVendor;
    }

    public void setUnreadVendor(boolean unreadVendor) {
        this.unreadVendor = unreadVendor;
    }

    public boolean isUnreadAdmin() {
        return unreadAdmin;
    }

    public void setUnreadAdmin(boolean unreadAdmin) {
        this.unreadAdmin = unreadAdmin;
    }

    public void setInProgress() {
        this.setDemandState("IN_PROGRESS");
        this.setUnreadAdmin(true);
    }

    public void setCompleted() {
        this.setDemandState("COMPLETED");
        this.setUnreadAdmin(true);
    }

    public void setOpen(){
        this.setDemandState("OPEN");
    }

    public void setRejected(){
        this.setDemandState("REJECTED");
        this.setUnreadVendor(true);
    }

    public void setApproved(){
        this.setDemandState("APPROVED");
        this.setUnreadVendor(true);
    }

    public Long getMaintainId() {
        return maintainId;
    }

    public void setMaintainId(Long maintainId) {
        this.maintainId = maintainId;
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

    public Long getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(Long customerCity) {
        this.customerCity = customerCity;
    }

    public Long getCustomerDistrict() {
        return customerDistrict;
    }

    public void setCustomerDistrict(Long customerDistrict) {
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

}
