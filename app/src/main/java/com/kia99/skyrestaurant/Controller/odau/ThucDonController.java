package com.kia99.skyrestaurant.Controller.odau;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kia99.skyrestaurant.Adapter.AdapterThucDonQuanAn;
import com.kia99.skyrestaurant.Model.ThucDonModel;

import java.util.List;

public class ThucDonController {
    ThucDonModel thucDonModel;

    public ThucDonController() {
        thucDonModel = new ThucDonModel();

    }

    public void getDanhSachThucDonQuanAnTheoMa(Context context, String maquanan, RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        ThucDon_Interrface thucDon_interrface = new ThucDon_Interrface() {
            @Override
            public void getThucDonThanhCong(List<ThucDonModel> thucDonModelList) {
                AdapterThucDonQuanAn adapterThucDonQuanAn = new AdapterThucDonQuanAn(context, thucDonModelList);
                recyclerView.setAdapter(adapterThucDonQuanAn);
                adapterThucDonQuanAn.notifyDataSetChanged();

            }
        };
        thucDonModel.getDanhSachThucDonTheoMa(maquanan, thucDon_interrface);

    }
}
