package com.kia99.skyrestaurant.Controller.odau;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kia99.skyrestaurant.Model.ModelThongTin;

public class controllerThongTin {
    ModelThongTin modelThongTin;
    public controllerThongTin(){
        modelThongTin = new ModelThongTin();
    }
    public void ThemThongTin(ModelThongTin modelThongTin,String mathanhvien){
        modelThongTin.ThemThongtinThanhVien(modelThongTin, mathanhvien);
    }
    public void HienThiThongTinNguoiDung(String mathanhvien, TextView hoten, TextView diachi, TextView sdt, ImageView hinhthanhvien){
        ThongTin_Interface thongTin_interface = new ThongTin_Interface() {
            @Override
            public void LayThongTinNguoiDung(ModelThongTin modelThongTin) {
                hoten.setText(modelThongTin.getHoten());
                diachi.setText(modelThongTin.getDiachi());
                sdt.setText(modelThongTin.getSdt());

                StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("thanhvien").child(modelThongTin.getHinhanh());
                long ONE_MEGABYTE = 1024 * 1024;
                storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        hinhthanhvien.setImageBitmap(bitmap);
                    }
                });

            }
        };
        modelThongTin.getThongTin(mathanhvien, thongTin_interface);
    }
    public void HienThiThongTinNguoiDungUD(String mathanhvien, EditText hoten, EditText diachi, EditText sdt, ImageView hinhthanhvien){
        ThongTin_Interface thongTin_interface = new ThongTin_Interface() {
            @Override
            public void LayThongTinNguoiDung(ModelThongTin modelThongTin) {
                hoten.setText(modelThongTin.getHoten());
                diachi.setText(modelThongTin.getDiachi());
                sdt.setText(modelThongTin.getSdt());

                StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("thanhvien").child(modelThongTin.getHinhanh());
                long ONE_MEGABYTE = 1024 * 1024;
                storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        hinhthanhvien.setImageBitmap(bitmap);
                    }
                });

            }
        };
        modelThongTin.getThongTin(mathanhvien, thongTin_interface);
    }

}
