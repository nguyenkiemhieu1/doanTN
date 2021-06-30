package com.kia99.skyrestaurant.View.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.kia99.skyrestaurant.Controller.odau.controllerThongTin;
import com.kia99.skyrestaurant.Model.QuanAnModels;
import com.kia99.skyrestaurant.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CapNhatThongTinNguoiDung extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    EditText edt_hoten, edt_diachi, edt_sdt;
    CircleImageView circleImageView;
    ImageView imageView;
    Button btnUpdate;

    final int RESULT_IMG = 111;

    controllerThongTin controllerThongTin;

    SharedPreferences sharedPreferences;
    List<Uri> hinhAnhNguoidung;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_update_thongtinthanhvien);
        init();

        sharedPreferences = getSharedPreferences("luudangnhap", MODE_PRIVATE);

        String mauser = sharedPreferences.getString("mauser", "");

        toolbar.setTitle("Thông tin người dùng");

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        controllerThongTin.HienThiThongTinNguoiDungUD(mauser, edt_hoten, edt_diachi, edt_sdt, circleImageView);
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_update);
        edt_diachi = (EditText) findViewById(R.id.edt_diachi);
        edt_hoten = (EditText) findViewById(R.id.edt_hoten);
        edt_sdt = (EditText) findViewById(R.id.edt_sdt);
        circleImageView = (CircleImageView) findViewById(R.id.circleimg_update_hinh);
        imageView = (ImageView) findViewById(R.id.img_hinhanhupdate);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);

        hinhAnhNguoidung = new ArrayList<>();

        controllerThongTin = new controllerThongTin();

        imageView.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.img_hinhanhupdate:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, RESULT_IMG);
                break;
            case R.id.btnUpdate:
                CapNhatThongTinNguoiDung();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_IMG:
                if (RESULT_OK == resultCode) {
                    Uri uri = data.getData();
                    circleImageView.setImageURI(uri);
                    hinhAnhNguoidung.add(uri);
                }

                break;
        }
    }

    private void CapNhatThongTinNguoiDung() {
        String hotenUpdate = edt_hoten.getText().toString();
        String sdtUpdate = edt_sdt.getText().toString();
        String diachiUpdate = edt_diachi.getText().toString();
        String mauserUpdate = sharedPreferences.getString("mauser", "");

        DatabaseReference noderoot = FirebaseDatabase.getInstance().getReference();
        DatabaseReference nodeThongtin = noderoot.child("thongtinnguoidungs");
        nodeThongtin.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    nodeThongtin.child(mauserUpdate).child("diachi").setValue(diachiUpdate);
                    nodeThongtin.child(mauserUpdate).child("hoten").setValue(hotenUpdate);
                    nodeThongtin.child(mauserUpdate).child("sdt").setValue(sdtUpdate);
                    for (Uri hinhquan : hinhAnhNguoidung) {
                        FirebaseStorage.getInstance().getReference().child("hinhanh/" + hinhquan.getLastPathSegment()).putFile(hinhquan);
                        noderoot.child(mauserUpdate).child("hinhanh").setValue(hinhquan.getLastPathSegment());

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
