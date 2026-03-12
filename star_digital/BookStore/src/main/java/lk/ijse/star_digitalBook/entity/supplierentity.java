package lk.ijse.star_digitalBook.entity;

import java.time.LocalDate;

public class supplierentity {
    private int supid;
    private String supname;
    private String supcontact;
    private LocalDate supdate;
    private String supagency;
    private String companyname;
    private String supstatus;
    private String imagepath;

    public supplierentity() {
    }

    public supplierentity(String supname, String supcontact, LocalDate supdate, String supagency, String companyname, String supstatus, String imagepath) {
        this.supname = supname;
        this.supcontact = supcontact;
        this.supdate = supdate;
        this.supagency = supagency;
        this.companyname = companyname;
        this.supstatus = supstatus;
        this.imagepath = imagepath;
    }

    public supplierentity(int supid, String supname, String supcontact, LocalDate supdate, String supagency, String companyname, String supstatus, String imagepath) {
        this.supid = supid;
        this.supname = supname;
        this.supcontact = supcontact;
        this.supdate = supdate;
        this.supagency = supagency;
        this.companyname = companyname;
        this.supstatus = supstatus;
        this.imagepath = imagepath;
    }



    public int getSupid() {
        return supid;
    }

    public void setSupid(int supid) {
        this.supid = supid;
    }

    public String getSupname() {
        return supname;
    }

    public void setSupname(String supname) {
        this.supname = supname;
    }

    public String getSupcontact() {
        return supcontact;
    }

    public void setSupcontact(String supcontact) {
        this.supcontact = supcontact;
    }

    public LocalDate getSupdate() {
        return supdate;
    }

    public void setSupdate(LocalDate supdate) {
        this.supdate = supdate;
    }

    public String getSupagency() {
        return supagency;
    }

    public void setSupagency(String supagency) {
        this.supagency = supagency;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getSupstatus() {
        return supstatus;
    }

    public void setSupstatus(String supstatus) {
        this.supstatus = supstatus;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    @Override
    public String toString() {
        return "supplierDTO{" + "supid=" + supid + ", supname=" + supname + ", supcontact=" + supcontact + ", supdate=" + supdate + ", supagency=" + supagency + ", companyname=" + companyname + ", supstatus=" + supstatus + ", imagepath=" + imagepath + '}';
    }
    
    

}