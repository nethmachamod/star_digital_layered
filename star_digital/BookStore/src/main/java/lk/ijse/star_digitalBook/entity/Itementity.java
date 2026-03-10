package lk.ijse.star_digitalBook.entity;

public class Itementity {
    private int id;
    private String name;
    private String category;
    private Double price;
    private String imagePath;

    public Itementity() { }

    public Itementity(int id, String name, String category, Double price, String imagePath) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.imagePath = imagePath;
    }

    public Itementity(String name, String category, Double price, String imagePath) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.imagePath = imagePath;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
} 