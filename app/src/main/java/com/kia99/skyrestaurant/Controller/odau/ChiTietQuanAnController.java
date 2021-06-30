package com.kia99.skyrestaurant.Controller.odau;

import android.view.View;
import android.widget.TextView;

import com.kia99.skyrestaurant.Model.WifiQuanAnModel;
import com.kia99.skyrestaurant.R;

import java.util.ArrayList;
import java.util.List;

public class ChiTietQuanAnController {

    List<WifiQuanAnModel> wifiQuanAnModelList;
    WifiQuanAnModel wifiQuanAnModel;

    public ChiTietQuanAnController() {
        wifiQuanAnModel = new WifiQuanAnModel();
        wifiQuanAnModelList = new ArrayList<>();

    }

    public void HienThiDanhSachWifi(String maquanan, TextView txt_tenWifi, TextView txt_passwordWifi, TextView txt_NgaydangWifi) {
        ChiTietQuanAn_interface chiTietQuanAn_interface = new ChiTietQuanAn_interface() {
            @Override
            public void HienthiDanhSachWifi(WifiQuanAnModel wifiQuanAnModel) {
                wifiQuanAnModelList.add(wifiQuanAnModel);
                txt_tenWifi.setText("Tên Wifi: " + wifiQuanAnModel.getTen());
                txt_passwordWifi.setText( "Mật khẩu: " + wifiQuanAnModel.getPassword());
                txt_NgaydangWifi.setText(wifiQuanAnModel.getNgaydang());

            }
        };
        wifiQuanAnModel.LayDsWifiQuanAn(maquanan, chiTietQuanAn_interface);

    }
}
