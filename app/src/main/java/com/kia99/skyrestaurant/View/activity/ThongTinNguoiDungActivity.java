package com.kia99.skyrestaurant.View.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.kia99.skyrestaurant.Controller.odau.controllerThongTin;
import com.kia99.skyrestaurant.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ThongTinNguoiDungActivity  extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    CircleImageView imgAnh;
    TextView txt_hoten, txt_diachi, txt_sdt;
    ImageView img_update_thongtin;
    Button btn_dangxuat;

    controllerThongTin  controllerThongTin;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thongtinthanhvien);

        init();

        toolbar.setTitle("Thông tin người dùng");
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        sharedPreferences = getSharedPreferences("luudangnhap", MODE_PRIVATE);
        String mauser = sharedPreferences.getString("mauser","");
        String mauser1 = "sjemCGV1gccqZdAhQa8KmdNZXYf1";
        controllerThongTin.HienThiThongTinNguoiDung(mauser1, txt_hoten, txt_diachi, txt_sdt, imgAnh);
    }

    private void init() {
        txt_hoten = (TextView) findViewById(R.id.txt_hotentv);
        txt_sdt = (TextView) findViewById(R.id.txt_sdt);
        txt_diachi = (TextView) findViewById(R.id.txt_diachi);
        imgAnh = (CircleImageView) findViewById(R.id.CircleImageView);

        toolbar  = (Toolbar) findViewById(R.id.toolbar_thongtin);
        img_update_thongtin = (ImageView) findViewById(R.id.img_update_thongtin);
        btn_dangxuat = (Button) findViewById(R.id.btn_dangxuat);
        img_update_thongtin.setOnClickListener(this);
        btn_dangxuat.setOnClickListener(this);
        controllerThongTin = new controllerThongTin();

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case  R.id.img_update_thongtin:
                Intent intent = new Intent(ThongTinNguoiDungActivity.this, CapNhatThongTinNguoiDung.class);
                startActivity(intent);
                break;
            case R.id.btn_dangxuat:
                FirebaseAuth.getInstance().signOut();
                Intent intentdx = new Intent(ThongTinNguoiDungActivity.this, LoginActivity.class);
                startActivity(intentdx);
                break;
        }

    }
}
