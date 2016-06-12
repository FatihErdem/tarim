package com.decimatech.tarim.model;

import javax.persistence.*;

@Entity
public class ReplacedPart extends AbstractBaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "replaced_part_id")
    private Long replacedPartId;

    @Column(name = "maintain_id")
    private Long maintainId;

    @Column(name = "part_serial_no")
    private String partSerialNo;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "total_price")
    private Double totalPrice;

    public Long getReplacedPartId() {
        return replacedPartId;
    }

    public void setReplacedPartId(Long replacedPartId) {
        this.replacedPartId = replacedPartId;
    }

    public Long getMaintainId() {
        return maintainId;
    }

    public void setMaintainId(Long maintainId) {
        this.maintainId = maintainId;
    }

    public String getPartSerialNo() {
        return partSerialNo;
    }

    public void setPartSerialNo(String partSerialNo) {
        this.partSerialNo = partSerialNo;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
