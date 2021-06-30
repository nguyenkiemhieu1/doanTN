package com.kia99.skyrestaurant.View.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kia99.skyrestaurant.Controller.odau.CapNhatWifiController;
import com.kia99.skyrestaurant.R;

public class  CapNhatDanhSachWifiActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recycler_DanhsachWifi;
    Button btn_updatewifi;
    CapNhatWifiController capNhatWifiController;

    Toolbar toolbar;
    String maquanan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_capnhatdanhsachwifi);
        init();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler_DanhsachWifi.setLayoutManager(layoutManager);

        maquanan = getIntent().getStringExtra("maquanan");
        String tenquanan = getIntent().getStringExtra("tenquanan");
        capNhatWifiController = new CapNhatWifiController(this);
        capNhatWifiController.HienThiDanhSachWifi(maquanan, recycler_DanhsachWifi);

        toolbar.setTitle(tenquanan);

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    //    5+1=2
    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_chitiet);
        recycler_DanhsachWifi = (RecyclerView) findViewById(R.id.recycler_DanhsachWifi);
        btn_updatewifi = (Button) findViewById(R.id.btn_updatewifi);
        btn_updatewifi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent iPopup = new Intent(this, PopupCapNhatWifiActivity.class);

        iPopup.putExtra("maquanan", maquanan);

        startActivity(iPopup);
    }

    @Override
    public boolean onNavigateUp() {
        finish();
        return super.onNavigateUp();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
