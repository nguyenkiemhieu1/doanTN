package com.kia99.skyrestaurant.Model;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kia99.skyrestaurant.Controller.odau.ChiTietQuanAn_interface;
import com.kia99.skyrestaurant.R;

public class WifiQuanAnModel {
    private String ten, password, ngaydang;

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNgaydang() {
        return ngaydang;
    }

    public void setNgaydang(String ngaydang) {
        this.ngaydang = ngaydang;
    }

    private DatabaseReference nodeWifiQuanAn;

    public void LayDsWifiQuanAn(String maquanan, ChiTietQuanAn_interface chiTietQuanAn_interface) {
        Query  querynodeWifiQuanAn = FirebaseDatabase.getInstance().getReference().child("wifiquanans").child(maquanan).orderByKey();
        querynodeWifiQuanAn.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot valueWifi : snapshot.getChildren()){
                    WifiQuanAnModel wifiQuanAnModel = valueWifi.getValue(WifiQuanAnModel.class);
                    chiTietQuanAn_interface.HienthiDanhSachWifi(wifiQuanAnModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void ThemWifiQuanAn(Context context, WifiQuanAnModel wifiQuanAnModel, String maquanan){
        DatabaseReference datanodeWifi = FirebaseDatabase.getInstance().getReference().child("wifiquanans").child(maquanan);
        datanodeWifi.push().setValue(wifiQuanAnModel, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(context, context.getResources().getString(R.string.themthanhcong), Toast.LENGTH_LONG).show();
            }
        });
    }
}
