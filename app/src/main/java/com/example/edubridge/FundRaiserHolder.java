package com.example.edubridge;

public class FundRaiserHolder {
    private String description;
    private String image;
    private String raisedMoney;
    private String name;
    private String totalMoney;

    public FundRaiserHolder() {
        // Default constructor required for calls to DataSnapshot.getValue(MoreStudentHomeRV.class)
    }

    public FundRaiserHolder(String description, String image, String raisedMoney, String name, String totalMoney) {
        this.description = description;
        this.image = image;
        this.raisedMoney = raisedMoney;
        this.name = name;
        this.totalMoney = totalMoney;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRaisedMoney() {
        return raisedMoney;
    }

    public void setRaisedMoney(String raisedMoney) {
        this.raisedMoney = raisedMoney;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }
}

