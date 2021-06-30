package com.kia99.skyrestaurant.View.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kia99.skyrestaurant.Adapter.AdapterChonHinhBinhLuan;
import com.kia99.skyrestaurant.Model.ChonHinhAnhBinhLuanModel;
import com.kia99.skyrestaurant.R;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class ChonHinhBinhLuanActivity extends AppCompatActivity implements View.OnClickListener {

    List<ChonHinhAnhBinhLuanModel> listDuongDan;
    List<String> hinhDuocChon;
    RecyclerView recyclerView;
    AdapterChonHinhBinhLuan adapterChonHinhBinhLuan;

    TextView txt_chonHinhAnhBinhLuan;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chonhinhanh);

        listDuongDan = new ArrayList<>();
        hinhDuocChon = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_chonHinhAnh);
        RecyclerView.LayoutManager layoutManager = new  GridLayoutManager(this, 2);
        adapterChonHinhBinhLuan = new AdapterChonHinhBinhLuan(this, R.layout.custom_layout_chonhinhbinhluan, listDuongDan);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterChonHinhBinhLuan);



        int checkREAD_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(checkREAD_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }else {
            getTatCaHinhAnhtrongTheNho();
        }
        txt_chonHinhAnhBinhLuan = (TextView) findViewById(R.id.txt_chonHinhAnhBinhLuan);
        txt_chonHinhAnhBinhLuan.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getTatCaHinhAnhtrongTheNho();
            }
        }
    }

    private  void getTatCaHinhAnhtrongTheNho(){
        final String[] projection = {MediaStore.Images.Media.DATA};
        final Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String duongdan = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

            ChonHinhAnhBinhLuanModel chonHinhAnhBinhLuanModel = new ChonHinhAnhBinhLuanModel(duongdan, false);


            listDuongDan.add(chonHinhAnhBinhLuanModel);
            adapterChonHinhBinhLuan.notifyDataSetChanged();
            cursor.moveToNext();
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case  R.id.txt_chonHinhAnhBinhLuan:
                for (ChonHinhAnhBinhLuanModel value : listDuongDan){
                    if (value.isCheck()){
                        hinhDuocChon.add(value.getDuongdan());
                    }
                }
                Intent intentHinhDKchon = getIntent();
                intentHinhDKchon.putStringArrayListExtra("listHinhDuocChon", (ArrayList<String>) hinhDuocChon);

                setResult(RESULT_OK, intentHinhDKchon);

                finish();
                break;
        }
    }
}
