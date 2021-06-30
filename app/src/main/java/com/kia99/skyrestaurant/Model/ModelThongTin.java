package com.kia99.skyrestaurant.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kia99.skyrestaurant.Controller.odau.ThongTin_Interface;

public class ModelThongTin implements Parcelable {
    private String mathanhvien;
    private String hovaten;
    private String diachi;
    private String hinhanh;
    private String sdt;

    private DatabaseReference datanodeThongTin;

    protected ModelThongTin(Parcel in) {
        mathanhvien = in.readString();
        hovaten = in.readString();
        diachi = in.readString();
        hinhanh = in.readString();
        sdt = in.readString();
    }

    public static final Creator<ModelThongTin> CREATOR = new Creator<ModelThongTin>() {
        @Override
        public ModelThongTin createFromParcel(Parcel in) {
            return new ModelThongTin(in);
        }

        @Override
        public ModelThongTin[] newArray(int size) {
            return new ModelThongTin[size];
        }
    };

    public String getMathanhvien() {
        return mathanhvien;
    }

    public void setMathanhvien(String mathanhvien) {
        this.mathanhvien = mathanhvien;
    }

    public String getHoten() {
        return hovaten;
    }

    public void setHoten(String hoten) {
        this.hovaten = hoten;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

     public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public ModelThongTin() {
        datanodeThongTin = FirebaseDatabase.getInstance().getReference().child("thongtinnguoidungs");
    }

    public void ThemThongtinThanhVien(ModelThongTin modelThongTin, String mathanhvien) {
        datanodeThongTin.child(mathanhvien).setValue(modelThongTin);

    }

    public void getThongTin(String maThanhVien, ThongTin_Interface thongTin_interface) {
        DatabaseReference nodeThongTinThanhVien = datanodeThongTin.child(maThanhVien);
        nodeThongTinThanhVien.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot Snapshot_thongtin : snapshot.getChildren()) {
                    ModelThongTin modelThongTin = Snapshot_thongtin.getValue(ModelThongTin.class);
                    thongTin_interface.LayThongTinNguoiDung(modelThongTin);
                    Log.v("debug",modelThongTin.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mathanhvien);
        dest.writeString(hovaten);
        dest.writeString(diachi);
        dest.writeString(hinhanh);
        dest.writeString(sdt);
    }
}
