package com.kia99.skyrestaurant.Controller.odau;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.kia99.skyrestaurant.Adapter.AdapterDanhSachWifi;
import com.kia99.skyrestaurant.Model.WifiQuanAnModel;
import com.kia99.skyrestaurant.R;

import java.util.ArrayList;
import java.util.List;

public class CapNhatWifiController {
    WifiQuanAnModel  wifiQuanAnModel;
    Context context;
    List<WifiQuanAnModel> wifiQuanAnModelList;
    public  CapNhatWifiController(Context context){
        wifiQuanAnModel = new WifiQuanAnModel();
        this.context = context;
    }
    public void HienThiDanhSachWifi(String maquanan, RecyclerView recyclerView){
        wifiQuanAnModelList = new ArrayList<>();
        ChiTietQuanAn_interface chiTietQuanAn_interface = new ChiTietQuanAn_interface() {
            @Override
            public void HienthiDanhSachWifi(WifiQuanAnModel wifiQuanAnModel) {

                wifiQuanAnModelList.add(wifiQuanAnModel);

                AdapterDanhSachWifi adapterDanhSachWifi = new AdapterDanhSachWifi(context, R.layout.layout_wifi_chitietquanan, wifiQuanAnModelList);
                recyclerView.setAdapter(adapterDanhSachWifi);
                adapterDanhSachWifi.notifyDataSetChanged();
            }
        };
        wifiQuanAnModel.LayDsWifiQuanAn(maquanan, chiTietQuanAn_interface);

    }
    public  void ThemWifi(Context context, WifiQuanAnModel wifiQuanAnModel, String maquanan){
        wifiQuanAnModel.ThemWifiQuanAn(context, wifiQuanAnModel, maquanan);
    }
}
