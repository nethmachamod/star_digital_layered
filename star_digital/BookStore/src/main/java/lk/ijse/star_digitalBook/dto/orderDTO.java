package lk.ijse.star_digitalBook.dto;

import java.time.LocalDateTime;
import java.util.List;
import lk.ijse.star_digitalBook.dto.OrderItemDTO;

public class orderDTO {
    private String orderid;
    private LocalDateTime orderDate;
    private String ordertype;
    private String ordercontact;    
    private String customerid;
    private double amount;
    private String discount;
    private String total;
    private List<OrderItemDTO> orderItems;

    public orderDTO() {
    }

    public orderDTO(String orderid, LocalDateTime orderDate, String ordertype, String ordercontact, 
                    String customerid, double amount, String discount, String total, List<OrderItemDTO> orderItems) {
        this.orderid = orderid;
        this.orderDate = orderDate;
        this.ordertype = ordertype;
        this.ordercontact = ordercontact;
        this.customerid = customerid;
        this.amount = amount;
        this.discount = discount;
        this.total = total;
        this.orderItems = orderItems;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    public String getOrdercontact() {
        return ordercontact;
    }

    public void setOrdercontact(String ordercontact) {
        this.ordercontact = ordercontact;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "orderDTO{" +
                "orderid='" + orderid + '\'' +
                ", orderDate=" + orderDate +
                ", ordertype='" + ordertype + '\'' +
                ", ordercontact='" + ordercontact + '\'' +
                ", customerid='" + customerid + '\'' +
                ", amount=" + amount +
                ", discount='" + discount + '\'' +
                ", total='" + total + '\'' +
                ", orderItems=" + orderItems +
                '}';
    }
}