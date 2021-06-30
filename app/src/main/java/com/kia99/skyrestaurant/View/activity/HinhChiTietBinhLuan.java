package com.kia99.skyrestaurant.View.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kia99.skyrestaurant.Adapter.AdapterRecyclerViewHinhBL;
import com.kia99.skyrestaurant.Model.BinhluanModel;
import com.kia99.skyrestaurant.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HinhChiTietBinhLuan extends AppCompatActivity {
    CircleImageView circleimg_odau_bl1;
    TextView txt_tieudebl1, txt_noidung_bl1, txt_chamdiembl1;
    RecyclerView recycler_customBl_HinhAnh;
    List<Bitmap> bitmapList;
    BinhluanModel binhluanModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_binhluan);
        init();

        binhluanModel = getIntent().getParcelableExtra("binhluanmodel");

        txt_tieudebl1.setText(binhluanModel.getTieudebl());
        txt_noidung_bl1.setText(binhluanModel.getNoidungbl());
       txt_chamdiembl1.setText(binhluanModel.getChamdiem() + "");
        setHinhAnhBinhLuan(circleimg_odau_bl1, binhluanModel.getThanhVienModels().getHinhanh());

        for (String linkhinh : binhluanModel.getListHinhanh()) {
            StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("hinhanh").child(linkhinh);
            long ONE_MEGABYTE = 1024 * 1024;
            storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                    bitmapList.add(bitmap);
                    if(bitmapList.size() == binhluanModel.getListHinhanh().size()){

                        AdapterRecyclerViewHinhBL adapterRecyclerViewHinhBL = new AdapterRecyclerViewHinhBL(HinhChiTietBinhLuan.this, R.layout.custom_hinhbinhluan, bitmapList, binhluanModel, true);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(HinhChiTietBinhLuan.this, 2);
                        recycler_customBl_HinhAnh.setLayoutManager(layoutManager);
                        recycler_customBl_HinhAnh.setAdapter(adapterRecyclerViewHinhBL);
                        adapterRecyclerViewHinhBL.notifyDataSetChanged();
                    }
                }
            });
        }
    }
    private void init(){
        circleimg_odau_bl1 = (CircleImageView) findViewById(R.id.circleimg_odau_bl1);
        txt_noidung_bl1 = (TextView) findViewById(R.id.txt_noidung_bl1);
        txt_tieudebl1 = (TextView) findViewById(R.id.txt_tieudebl1);
        txt_chamdiembl1 = (TextView) findViewById(R.id.txt_chamdiembl1);
        recycler_customBl_HinhAnh = (RecyclerView) findViewById(R.id.recycler_customBl_HinhAnh);

        bitmapList = new ArrayList<>();
    }
    private void setHinhAnhBinhLuan(final CircleImageView circleImageView, String linkhinh) {
        StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("thanhvien").child(linkhinh);
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                circleImageView.setImageBitmap(bitmap);
            }
        });
    }

}
