package lk.ijse.star_digitalBook.dto;

import java.time.LocalDate;

public class employeeDTO {
    private int id;
    private String fullName;
    private String nic;
    private String contact;
    private String jobTitle;
    private double salary;
    private LocalDate hireDate;  
    private String status;
    private String imagePath;

    public employeeDTO() {
    }

    public employeeDTO(String fullName, String nic, String contact, String jobTitle, double salary, LocalDate hireDate, String status, String imagePath) {
        this.fullName = fullName;
        this.nic = nic;
        this.contact = contact;
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.hireDate = hireDate;
        this.status = status;
        this.imagePath = imagePath;
    }

    public employeeDTO(int id, String fullName, String nic, String contact, String jobTitle, double salary, LocalDate hireDate, String status, String imagePath) {
        this.id = id;
        this.fullName = fullName;
        this.nic = nic;
        this.contact = contact;
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.hireDate = hireDate;
        this.status = status;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "employeeDTO{" + "id=" + id + ", fullName=" + fullName + ", nic=" + nic + ", contact=" + contact + ", jobTitle=" + jobTitle + ", salary=" + salary + ", hireDate=" + hireDate + ", status=" + status + ", imagePath=" + imagePath + '}';
    }
    
    

}