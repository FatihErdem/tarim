package com.decimatech.tarim.model;

import javax.persistence.*;

@Entity
public class Maintain {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "maintain_id")
    private Long maintainId;

    @Column(name = "demand_id")
    private Long demandId;

    @Column(name = "machine_id")
    private Long machineId;

    @Column(name = "vendor_id")
    private Long vendorId;

    @Column(name = "machine_serial_no")
    private String machineSerialNo;

    @Column(name = "route")
    private String route;

    @Column(name = "total_distance")
    private Long totalDistance;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "finish_time")
    private String finishTime;

    @Column(name = "total_time")
    private String totalTime;

    @Column(name = "invoice_date")
    private String invoiceDate;

    @Column(name = "warranty")
    private Boolean warranty;

    @Column(name = "warranty_desc")
    private String warrantyDesc;

    @Column(name = "maintain_desc")
    private String maintainDesc;

    @Column(name = "process")
    private String process;

    @Column(name = "report_owner_sig")
    private String reportOwnerSig;

    @Column(name = "customer_sig")
    private String customerSig;

    @Column(name = "vendor_manager_sig")
    private String vendorManagerSig;

    @Column(name = "labor_cost")
    private Long laborCost;

    @Column(name = "distance_cost")
    private Long distanceCost;

    @Column(name = "total_cost")
    private Long totalCost;

    @Column(name = "report_date")
    private String reportDate;


    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getMachineSerialNo() {
        return machineSerialNo;
    }

    public void setMachineSerialNo(String machineSerialNo) {
        this.machineSerialNo = machineSerialNo;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Long getDemandId() {
        return demandId;
    }

    public void setDemandId(Long demandId) {
        this.demandId = demandId;
    }

    public Long getMaintainId() {
        return maintainId;
    }

    public void setMaintainId(Long maintainId) {
        this.maintainId = maintainId;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Long getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(Long totalDistance) {
        this.totalDistance = totalDistance;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public Boolean getWarranty() {
        return warranty;
    }

    public void setWarranty(Boolean warranty) {
        this.warranty = warranty;
    }

    public String getWarrantyDesc() {
        return warrantyDesc;
    }

    public void setWarrantyDesc(String warrantyDesc) {
        this.warrantyDesc = warrantyDesc;
    }

    public String getMaintainDesc() {
        return maintainDesc;
    }

    public void setMaintainDesc(String maintainDesc) {
        this.maintainDesc = maintainDesc;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getReportOwnerSig() {
        return reportOwnerSig;
    }

    public void setReportOwnerSig(String reportOwnerSig) {
        this.reportOwnerSig = reportOwnerSig;
    }

    public String getCustomerSig() {
        return customerSig;
    }

    public void setCustomerSig(String customerSig) {
        this.customerSig = customerSig;
    }

    public String getVendorManagerSig() {
        return vendorManagerSig;
    }

    public void setVendorManagerSig(String vendorManagerSig) {
        this.vendorManagerSig = vendorManagerSig;
    }

    public Long getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(Long laborCost) {
        this.laborCost = laborCost;
    }

    public Long getDistanceCost() {
        return distanceCost;
    }

    public void setDistanceCost(Long distanceCost) {
        this.distanceCost = distanceCost;
    }

    public Long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Long totalCost) {
        this.totalCost = totalCost;
    }
}
