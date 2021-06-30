package com.kia99.skyrestaurant.View.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.kia99.skyrestaurant.Adapter.AdapterHinhBinhLuanDuocChon;
import com.kia99.skyrestaurant.Controller.BinhluanController;
import com.kia99.skyrestaurant.Model.BinhluanModel;
import com.kia99.skyrestaurant.R;

import java.util.ArrayList;
import java.util.List;

public class BinhLuanActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txt_DiaChiquanAnBl, txt_TenquanAnBl, txt_dangBinhLuan;
    EditText edt_TieudeBinhLuan, edt_noidungBinhLuan;
    String tenquanan;
    String diachi;
    String maquanan;
    Toolbar toolbar;
    ImageButton igBtn_Camera;
    final int REQUEST_CHONHINHANHBINHLUAN = 11;
    RecyclerView recyclerView;
    AdapterHinhBinhLuanDuocChon adapterHinhBinhLuanDuocChon;

    SharedPreferences sharedPreferences;

    BinhluanController binhluanController;
    List<String> listHinhChon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_binhluan);

        maquanan = getIntent().getStringExtra("maquanan");
        tenquanan = getIntent().getStringExtra("tenquanan");
        diachi = getIntent().getStringExtra("diachi");

        sharedPreferences = getSharedPreferences("luudangnhap", MODE_PRIVATE);
        String  mauser = sharedPreferences.getString("mauser", "");


        init();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        binhluanController = new BinhluanController();
        listHinhChon = new ArrayList<>();

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void init() {
        txt_dangBinhLuan = (TextView) findViewById(R.id.txt_dangBinhLuan);
        txt_DiaChiquanAnBl = (TextView) findViewById(R.id.txt_DiaChiquanAnBl);
        txt_TenquanAnBl = (TextView) findViewById(R.id.txt_TenquanAnBl);
        txt_TenquanAnBl.setText(tenquanan);
        txt_DiaChiquanAnBl.setText(diachi);

        edt_noidungBinhLuan = (EditText) findViewById(R.id.edt_noidungBinhLuan);
        edt_TieudeBinhLuan = (EditText) findViewById(R.id.edt_TieudeBinhLuan);
        igBtn_Camera = (ImageButton) findViewById(R.id.igBtn_Camera);
        toolbar = (Toolbar) findViewById(R.id.toolbar_bl);
        igBtn_Camera.setOnClickListener(this);
        txt_dangBinhLuan.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_HinhDuocChon);

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
            case R.id.igBtn_Camera:
                Intent intent = new Intent(BinhLuanActivity.this, ChonHinhBinhLuanActivity.class);
                startActivityForResult(intent, REQUEST_CHONHINHANHBINHLUAN);
                break;
            case R.id.txt_dangBinhLuan:
                BinhluanModel binhluanModel = new BinhluanModel();
                String tieude = edt_TieudeBinhLuan.getText().toString();
                String noidung = edt_noidungBinhLuan.getText().toString();
                String  mauser = sharedPreferences.getString("mauser", "");
                if(noidung.trim().length() == 0){
                    Toast.makeText(BinhLuanActivity.this, "Bạn chưa nhập nội dung bình luận", Toast.LENGTH_LONG).show();
                }else {
                    binhluanModel.setTieudebl(tieude);
                    binhluanModel.setNoidungbl(noidung);
                    binhluanModel.setChamdiem(0);
                    binhluanModel.setLuotthich((long) 0);
//                    binhluanModel.setMauser("sjemCGV1gccqZdAhQa8KmdNZXYf1");
                    binhluanModel.setMauser(mauser);

                    binhluanController.ThemBinhLuan(maquanan, binhluanModel, listHinhChon);
                    startActivity(new Intent(BinhLuanActivity.this,ChiTietQuanAnActivity.class));
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHONHINHANHBINHLUAN) {
            if (resultCode == RESULT_OK) {
                listHinhChon = data.getStringArrayListExtra("listHinhDuocChon");
                adapterHinhBinhLuanDuocChon = new AdapterHinhBinhLuanDuocChon(this, R.layout.custom_layout_hienthi_hinhduocchon, listHinhChon);
                recyclerView.setAdapter(adapterHinhBinhLuanDuocChon);
                adapterHinhBinhLuanDuocChon.notifyDataSetChanged();

            }
        }

    }
}
