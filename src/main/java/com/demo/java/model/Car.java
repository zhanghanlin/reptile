package com.demo.java.model;

import com.demo.java.common.annotation.Ignore;

import java.util.Date;

import static com.demo.java.common.utils.Config.STRING_EMPTY;

public class Car {
    @Ignore
    private String id;  //Id
    private String carName; //名称
    private String price;   //价格 单位万
    private String onTime;  //上牌时间
    private String mileage; //里程数
    private String speedCase;   //变速箱类型
    private String inspectExpire;// 年检到期
    private String safeExpire;  //保险到期
    private String accident;    //事故
    private String userName;    //联系人
    private String phone;   //联系方式
    private String address; //地址

    @Ignore
    private String source;  //来源标识,默认使用抓取任务中唯一任务标识
    @Ignore
    private Date createTime;    //抓取时间
    @Ignore
    private Date updateTime;    //更新时间
    @Ignore
    private String url;     //原始URL

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOnTime() {
        return onTime;
    }

    public void setOnTime(String onTime) {
        this.onTime = onTime;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getSpeedCase() {
        return speedCase;
    }

    public void setSpeedCase(String speedCase) {
        this.speedCase = speedCase;
    }

    public String getInspectExpire() {
        return inspectExpire;
    }

    public void setInspectExpire(String inspectExpire) {
        this.inspectExpire = inspectExpire;
    }

    public String getSafeExpire() {
        return safeExpire;
    }

    public void setSafeExpire(String safeExpire) {
        this.safeExpire = safeExpire;
    }

    public String getAccident() {
        return accident;
    }

    public void setAccident(String accident) {
        this.accident = accident;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAddress() {
        return address == null ? STRING_EMPTY : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
