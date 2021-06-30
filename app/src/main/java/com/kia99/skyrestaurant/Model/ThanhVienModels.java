package com.kia99.skyrestaurant.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ThanhVienModels implements Parcelable {
    private   String hoten;
    private String hinhanh;

    private String mathanhvien;

    private DatabaseReference datanodeThanhVien;

    public ThanhVienModels(){

        datanodeThanhVien = FirebaseDatabase.getInstance().getReference().child("thanhviens");

    }

    protected ThanhVienModels(Parcel in) {
        hoten = in.readString();
        hinhanh = in.readString();
        mathanhvien = in.readString();
    }

    public static final Creator<ThanhVienModels> CREATOR = new Creator<ThanhVienModels>() {
        @Override
        public ThanhVienModels createFromParcel(Parcel in) {
            return new ThanhVienModels(in);
        }

        @Override
        public ThanhVienModels[] newArray(int size) {
            return new ThanhVienModels[size];
        }
    };

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getMathanhvien() {
        return mathanhvien;
    }

    public void setMathanhvien(String mathanhvien) {
        this.mathanhvien = mathanhvien;
    }

    public void ThemThongTinThanhViens(ThanhVienModels thanhVienModels, String userID){
        datanodeThanhVien.child(userID).setValue(thanhVienModels);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hoten);
        dest.writeString(hinhanh);
        dest.writeString(mathanhvien);
    }
}
