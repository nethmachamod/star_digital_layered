package lk.ijse.star_digitalBook.dto;

public class customerDTO {

    private String id;
    private String name;
    private String number;
    private String address;

    public customerDTO() {
    }

    public customerDTO(String id, String name, String number, String address) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "customerDTO{" + "id=" + id + ", name=" + name + ", number=" + number + ", address=" + address + '}';
    }

}
