package lk.ijse.star_digitalBook.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class paymententity {
    private String paymentid;
    private String orderid;
    private String customer_id;
    private LocalDate paymentDate = LocalDate.now();
    private LocalDateTime paymentDateTime = LocalDateTime.now();
    private String paymentmethod;
    private String total;
    private String paidamount;
    private String balanace;

    public paymententity() {
    }

    public paymententity(String paymentid, String orderid, String customer_id,
                         String paymentmethod, String total, String paidamount, String balanace) {
        this.paymentid = paymentid;
        this.orderid = orderid;
        this.customer_id = customer_id;
        this.paymentmethod = paymentmethod;
        this.total = total;
        this.paidamount = paidamount;
        this.balanace = balanace;
    }

    // Getters and Setters
    public String getPaymentid() { return paymentid; }
    public void setPaymentid(String paymentid) { this.paymentid = paymentid; }
    
    public String getOrderid() { return orderid; }
    public void setOrderid(String orderid) { this.orderid = orderid; }
    
    public String getCustomer_id() { return customer_id; }
    public void setCustomer_id(String customer_id) { this.customer_id = customer_id; }
    
    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }
    
    public LocalDateTime getPaymentDateTime() { return paymentDateTime; }
    public void setPaymentDateTime(LocalDateTime paymentDateTime) { this.paymentDateTime = paymentDateTime; }
    
    public String getPaymentmethod() { return paymentmethod; }
    public void setPaymentmethod(String paymentmethod) { this.paymentmethod = paymentmethod; }
    
    public String getTotal() { return total; }
    public void setTotal(String total) { this.total = total; }
    
    public String getPaidamount() { return paidamount; }
    public void setPaidamount(String paidamount) { this.paidamount = paidamount; }
    
    public String getBalanace() { return balanace; }
    public void setBalanace(String balanace) { this.balanace = balanace; }

    @Override
    public String toString() {
        return "paymentDTO{" +
                "paymentid='" + paymentid + '\'' +
                ", orderid='" + orderid + '\'' +
                ", customer_id='" + customer_id + '\'' +
                ", paymentDate=" + paymentDate +
                ", paymentDateTime=" + paymentDateTime +
                ", paymentmethod='" + paymentmethod + '\'' +
                ", total='" + total + '\'' +
                ", paidamount='" + paidamount + '\'' +
                ", balanace='" + balanace + '\'' +
                '}';
    }
}

//
//package lk.ijse.star_digitalBook.dto;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//
//public class paymentDTO {
//    private String paymentid;
//    private String orderid;
//    private String customer_id;
//     private LocalDate paymentDate = LocalDate.now();
//    private LocalDateTime paymentDateTime = LocalDateTime.now();
//    private String paymentmethod;
//    private String total;
//    private String paidamount;
//    private String balanace;
//
//    public paymentDTO() {
//    }
//
//    public paymentDTO(String paymentid, String orderid, String customer_id, String paymentmethod, String total, String paidamount, String balanace) {
//        this.paymentid = paymentid;
//        this.orderid = orderid;
//        this.customer_id = customer_id;
//        this.paymentmethod = paymentmethod;
//        this.total = total;
//        this.paidamount = paidamount;
//        this.balanace = balanace;
//    }
//
//    public String getPaymentid() {
//        return paymentid;
//    }
//
//    public void setPaymentid(String paymentid) {
//        this.paymentid = paymentid;
//    }
//
//    public String getOrderid() {
//        return orderid;
//    }
//
//    public void setOrderid(String orderid) {
//        this.orderid = orderid;
//    }
//
//    public String getCustomer_id() {
//        return customer_id;
//    }
//
//    public void setCustomer_id(String customer_id) {
//        this.customer_id = customer_id;
//    }
//
//    public LocalDate getPaymentDate() {
//        return paymentDate;
//    }
//
//    public void setPaymentDate(LocalDate paymentDate) {
//        this.paymentDate = paymentDate;
//    }
//
//    public LocalDateTime getPaymentDateTime() {
//        return paymentDateTime;
//    }
//
//    public void setPaymentDateTime(LocalDateTime paymentDateTime) {
//        this.paymentDateTime = paymentDateTime;
//    }
//
//    public String getPaymentmethod() {
//        return paymentmethod;
//    }
//
//    public void setPaymentmethod(String paymentmethod) {
//        this.paymentmethod = paymentmethod;
//    }
//
//    public String getTotal() {
//        return total;
//    }
//
//    public void setTotal(String total) {
//        this.total = total;
//    }
//
//    public String getPaidamount() {
//        return paidamount;
//    }
//
//    public void setPaidamount(String paidamount) {
//        this.paidamount = paidamount;
//    }
//
//    public String getBalanace() {
//        return balanace;
//    }
//
//    public void setBalanace(String balanace) {
//        this.balanace = balanace;
//    }
//
//    @Override
//    public String toString() {
//        return "paymentDTO{" + "paymentid=" + paymentid + ", orderid=" + orderid + ", customer_id=" + customer_id + ", paymentDate=" + paymentDate + ", paymentDateTime=" + paymentDateTime + ", paymentmethod=" + paymentmethod + ", total=" + total + ", paidamount=" + paidamount + ", balanace=" + balanace + '}';
//    }
//    
//    
//}
//
//    