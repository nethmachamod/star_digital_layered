package lk.ijse.star_digitalBook.entity;

import java.time.LocalDate;

public class inventoryentity {

    private String inventoryId;
    private String inventoryName;
    private String qtyOnHand;
    private String inventoryType;
    private LocalDate stockInDate;

    public inventoryentity() {}

    public inventoryentity(String inventoryId, String inventoryName, String qtyOnHand,
                           String inventoryType, LocalDate stockInDate) {
        this.inventoryId = inventoryId;
        this.inventoryName = inventoryName;
        this.qtyOnHand = qtyOnHand;
        this.inventoryType = inventoryType;
        this.stockInDate = stockInDate;
    }

    public inventoryentity(String inventoryName, String qtyOnHand, String inventoryType, LocalDate stockInDate) {
        this.inventoryName = inventoryName;
        this.qtyOnHand = qtyOnHand;
        this.inventoryType = inventoryType;
        this.stockInDate = stockInDate;
    }
    

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public void setInventoryName(String inventoryName) {
        this.inventoryName = inventoryName;
    }

    public String getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(String qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }

    public LocalDate getStockInDate() {
        return stockInDate;
    }

    public void setStockInDate(LocalDate stockInDate) {
        this.stockInDate = stockInDate;
    }

    @Override
    public String toString() {
        return "inventoryDTO{" + "inventoryId=" + inventoryId + ", inventoryName=" + inventoryName + ", qtyOnHand=" + qtyOnHand + ", inventoryType=" + inventoryType + ", stockInDate=" + stockInDate + '}';
    }
    
}
