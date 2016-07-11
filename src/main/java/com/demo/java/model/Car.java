package com.demo.java.model;

public class Car {
    private String id;
    private String title;
    private String img;
    private String year;
    private String kilometre;
    private String liter;
    private String type;
    private String price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getKilometre() {
        return kilometre;
    }

    public void setKilometre(String kilometre) {
        this.kilometre = kilometre;
    }

    public String getLiter() {
        return liter;
    }

    public void setLiter(String liter) {
        this.liter = liter;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", year='" + year + '\'' +
                ", kilometre='" + kilometre + '\'' +
                ", liter='" + liter + '\'' +
                ", type='" + type + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
