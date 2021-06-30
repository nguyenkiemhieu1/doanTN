package com.kia99.skyrestaurant.Model;

public class ChonHinhAnhBinhLuanModel {

    String duongdan;
    boolean isCheck;

    public ChonHinhAnhBinhLuanModel(String duongdan, boolean isCheck) {
        this.duongdan = duongdan;
        this.isCheck = isCheck;
    }

    public String getDuongdan() {
        return duongdan;
    }

    public void setDuongdan(String duongdan) {
        this.duongdan = duongdan;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
