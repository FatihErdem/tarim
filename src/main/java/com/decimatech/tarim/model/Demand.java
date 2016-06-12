package com.decimatech.tarim.model;

import com.google.common.base.Objects;
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

    @Column(name = "unread")
    private boolean unread = true;

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

    public void setInProgress(){
        this.setDemandState("IN_PROGRESS");
    }

    public void setCompleted(){
        this.setDemandState("COMPLETED");
    }

    public void setRead(){
        this.setUnread(false);
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

    public boolean isUnread() {
        return unread;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Demand demand = (Demand) o;
        return unread == demand.unread &&
                Objects.equal(demandId, demand.demandId) &&
                Objects.equal(vendorId, demand.vendorId) &&
                Objects.equal(customerName, demand.customerName) &&
                Objects.equal(customerSurname, demand.customerSurname) &&
                Objects.equal(customerTel, demand.customerTel) &&
                Objects.equal(customerAddress, demand.customerAddress) &&
                Objects.equal(customerCity, demand.customerCity) &&
                Objects.equal(customerDistrict, demand.customerDistrict) &&
                Objects.equal(demandNote, demand.demandNote) &&
                Objects.equal(demandState, demand.demandState) &&
                Objects.equal(demandType, demand.demandType) &&
                Objects.equal(demandOwner, demand.demandOwner);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(demandId, vendorId, customerName, customerSurname, customerTel, customerAddress, customerCity, customerDistrict, demandNote, demandState, demandType, demandOwner, unread);
    }
}
