package com.kia99.skyrestaurant.View.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kia99.skyrestaurant.Controller.odau.CapNhatWifiController;
import com.kia99.skyrestaurant.Model.WifiQuanAnModel;
import com.kia99.skyrestaurant.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PopupCapNhatWifiActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edt_tenWifi, edt_passwordWifi;
    Button btn_dongy;
    String maquanan;
    CapNhatWifiController capNhatWifiController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_popup_capnhatwifi);

        maquanan = getIntent().getStringExtra("maquanan");

        init();
        capNhatWifiController = new CapNhatWifiController(this);
    }

    private void init() {
        edt_passwordWifi = (EditText) findViewById(R.id.edt_passwordWifi);
        edt_tenWifi = (EditText) findViewById(R.id.edt_tenWifi);
        btn_dongy = (Button) findViewById(R.id.btn_dongy);
        btn_dongy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String tenwifi = edt_tenWifi.getText().toString();
        String password = edt_passwordWifi.getText().toString();
        if (tenwifi.trim().length() > 0 && password.trim().length() > 0) {

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String ngaydang = simpleDateFormat.format(calendar.getTime());
            WifiQuanAnModel wifiQuanAnModel = new WifiQuanAnModel();
            wifiQuanAnModel.setTen(tenwifi);
            wifiQuanAnModel.setPassword(password);
            wifiQuanAnModel.setNgaydang(ngaydang);

            capNhatWifiController.ThemWifi(this, wifiQuanAnModel, maquanan);
        } else {
            Toast.makeText(this, R.string.loicapnhat, Toast.LENGTH_LONG).show();
        }
        finish();

    }
}
