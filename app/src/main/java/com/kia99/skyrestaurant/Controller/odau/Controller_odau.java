package com.kia99.skyrestaurant.Controller.odau;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kia99.skyrestaurant.Adapter.Adapter_odau;
import com.kia99.skyrestaurant.Adapter.Adapter_quanLy_QuanAn;
import com.kia99.skyrestaurant.Controller.odau.odau_interface;
import com.kia99.skyrestaurant.Model.QuanAnModels;
import com.kia99.skyrestaurant.R;

import java.util.ArrayList;
import java.util.List;

public class Controller_odau {
    Context context;
    QuanAnModels quanAnModels;
    Adapter_odau adapter_odau;
    int itemdaco = 5;

    int itemdaco1 = 10;
    public  Controller_odau(Context context){
        this.context = context;
        quanAnModels = new QuanAnModels();

    }
    public  void getDanhSachQuanAnController(Context context,NestedScrollView nestedScrollView, RecyclerView recyclerView_odau, ProgressBar progressBar, Location vitrihientai) {

        List<QuanAnModels> quanAnModels1 = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView_odau.setLayoutManager(layoutManager);
         adapter_odau = new Adapter_odau(context,quanAnModels1, R.layout.custom_recyclerview_fragment_odau);
        recyclerView_odau.setAdapter(adapter_odau);

        progressBar.setVisibility(View.VISIBLE);

        final odau_interface odau_interface = new odau_interface() {
            @Override
            public void getLaydanhsachQuanAn(QuanAnModels quanAnModels) {
                List<Bitmap> bitmaps = new ArrayList<>();
                for (String linkhinh : quanAnModels.getHinhquanAn()){
                    StorageReference storageReferencehinhanh = FirebaseStorage.getInstance().getReference().child("hinhanh")
                            .child(linkhinh) ;
                    long ONE_MEGABYTE = 1024 * 1024;
                    storageReferencehinhanh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            bitmaps.add(bitmap);
                            quanAnModels.setBitmapList(bitmaps);

                            if(quanAnModels.getBitmapList().size() == quanAnModels.getHinhquanAn().size()){
                                quanAnModels1.add(quanAnModels);
                                adapter_odau.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);

                                Log.d("kiemtra", quanAnModels.getTenquanan() +"");
                            }
                        }
                    });

                }
            }
        };

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(v.getChildAt(v.getChildCount() - 1) != null){
                    if(scrollY >= (v.getChildAt(v.getChildCount()-1)).getMeasuredHeight() - v.getMeasuredHeight()){

                        itemdaco += 5;
                        quanAnModels.getDanhSachQuanAn(odau_interface, vitrihientai,itemdaco,itemdaco - 5);
                    }
                }
            }
        });

        quanAnModels.getDanhSachQuanAn(odau_interface, vitrihientai,itemdaco,0);

    }
    public  void getDanhSachQuanAnControllerAdmin(Context context,NestedScrollView nestedScrollView, RecyclerView recyclerView_odau, ProgressBar progressBar, Location vitrihientai) {

        List<QuanAnModels> quanAnModels1 = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView_odau.setLayoutManager(layoutManager);
        Adapter_quanLy_QuanAn adapter_quanLy_quanAn = new Adapter_quanLy_QuanAn(context,quanAnModels1, R.layout.custom_layout_quanan);
        recyclerView_odau.setAdapter(adapter_quanLy_quanAn);

        progressBar.setVisibility(View.VISIBLE);

        final odau_interface odau_interface = new odau_interface() {
            @Override
            public void getLaydanhsachQuanAn(QuanAnModels quanAnModels) {
                List<Bitmap> bitmaps = new ArrayList<>();
                for (String linkhinh : quanAnModels.getHinhquanAn()){
                    StorageReference storageReferencehinhanh = FirebaseStorage.getInstance().getReference().child("hinhanh")
                            .child(linkhinh) ;
                    long ONE_MEGABYTE = 1024 * 1024;
                    storageReferencehinhanh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            bitmaps.add(bitmap);
                            quanAnModels.setBitmapList(bitmaps);

                            if(quanAnModels.getBitmapList().size() == quanAnModels.getHinhquanAn().size()){
                                quanAnModels1.add(quanAnModels);
                                adapter_quanLy_quanAn.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);

                                Log.d("kiemtra", quanAnModels.getTenquanan() +"");
                            }
                        }
                    });

                }
            }
        };

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(v.getChildAt(v.getChildCount() - 1) != null){
                    if(scrollY >= (v.getChildAt(v.getChildCount()-1)).getMeasuredHeight() - v.getMeasuredHeight()){

                        itemdaco1 += 3;
                        quanAnModels.getDanhSachQuanAn(odau_interface, vitrihientai,itemdaco1,itemdaco1 - 3);
                    }
                }
            }
        });

        quanAnModels.getDanhSachQuanAn(odau_interface, vitrihientai,itemdaco1,0);

    }

}
