package com.kia99.skyrestaurant.View.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kia99.skyrestaurant.Adapter.AdapterDanhSachBinhLuan;
import com.kia99.skyrestaurant.Controller.odau.CapNhatWifiController;
import com.kia99.skyrestaurant.Controller.odau.ChiTietQuanAnController;
import com.kia99.skyrestaurant.Controller.odau.ThucDonController;
import com.kia99.skyrestaurant.Model.QuanAnModels;
import com.kia99.skyrestaurant.Model.TienIchModel;
import com.kia99.skyrestaurant.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChiTietQuanAnActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    TextView txt_TenQuanAn_chitietquanan, txt_NgaydangWifi, txt_diachiQuanAn, txt_GioiHanGia, txt_DiaChiQuanAn_chitietquanan, txt_thoiGian_hoatdong, txt_TrangThaiHoatDong, txt_sl_HinhAnh, txt_sl_CheckIn, txt_sl_BinhLuan, txt_sl_LuuLai, txt_TieudeToolbar, txt_tenWifi, txt_passwordWifi;
    ImageView img_HinhQuanAn_CTQA;
    QuanAnModels quanAnModels;
    Toolbar toolbar;
    RecyclerView recyclerView, recyclerViewThucdonQuanAn;
    AdapterDanhSachBinhLuan adapterDanhSachBinhLuan;
    GoogleMap googleMap;
    SupportMapFragment mapFragment;
    Button btn_binhluanquanAn;

    View khungMap;
    LinearLayout linear_Khungtienich, linear_khungWifi;

    VideoView videoView;

    ChiTietQuanAnController chiTieuQuanAnController;

    ThucDonController thucDonController;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chitietquanan);
        quanAnModels = getIntent().getParcelableExtra("quanan");
        init();


        toolbar = (Toolbar) findViewById(R.id.toolbar_chitiet);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        HienThiChiTietQuanAn();
    }

    private void init() {

        chiTieuQuanAnController = new ChiTietQuanAnController();
        thucDonController = new ThucDonController();

        btn_binhluanquanAn = (Button) findViewById(R.id.btn_binhluanChitietquanAn);

        txt_TenQuanAn_chitietquanan = (TextView) findViewById(R.id.txt_TenQuanAn_chitietquanan);
        txt_DiaChiQuanAn_chitietquanan = (TextView) findViewById(R.id.txt_diachiQuanAn);
        txt_thoiGian_hoatdong = (TextView) findViewById(R.id.txt_thoiGian_hoatdong);
        txt_TrangThaiHoatDong = (TextView) findViewById(R.id.txt_TrangThaiHoatDong);
        txt_sl_HinhAnh = (TextView) findViewById(R.id.txt_sl_HinhAnh);
        txt_sl_CheckIn = (TextView) findViewById(R.id.txt_sl_CheckIn);
        txt_sl_BinhLuan = (TextView) findViewById(R.id.txt_sl_BinhLuan);
        txt_sl_LuuLai = (TextView) findViewById(R.id.txt_sl_LuuLai);
        txt_TieudeToolbar = (TextView) findViewById(R.id.txt_TieudeToolbar);
        txt_diachiQuanAn = (TextView) findViewById(R.id.txt_diachiQuanAn);
        txt_GioiHanGia = (TextView) findViewById(R.id.txt_GioiHanGia);
        txt_passwordWifi = (TextView) findViewById(R.id.txt_passwordWifi);
        txt_tenWifi = (TextView) findViewById(R.id.txt_tenWifi);
        txt_NgaydangWifi = (TextView) findViewById(R.id.txt_NgaydangWifi);
        videoView = (VideoView) findViewById(R.id.VideoViewTrailer);
        recyclerViewThucdonQuanAn = (RecyclerView) findViewById(R.id.recycler_chitiet_ThucDon);

        khungMap = (View) findViewById(R.id.khungMap);


        img_HinhQuanAn_CTQA = (ImageView) findViewById(R.id.img_HinhQuanAn_CTQA);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_Binhluan_ctqa);

        linear_Khungtienich = (LinearLayout) findViewById(R.id.linear_Khungtienich);
        linear_khungWifi = (LinearLayout) findViewById(R.id.linear_khungWifi);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        khungMap.setOnClickListener(this);
        btn_binhluanquanAn.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void HienThiChiTietQuanAn() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String giohientai = dateFormat.format(calendar.getTime());
        String giomocua = quanAnModels.getGiomocua();
        String giodongcua = quanAnModels.getGiodongcua();
        try {
            Date datehientai = dateFormat.parse(giohientai);
            Date dateMocua = dateFormat.parse(giomocua);
            Date dateDongcua = dateFormat.parse(giodongcua);

            if (datehientai.after(dateMocua) && datehientai.before(dateDongcua)) {

                txt_TrangThaiHoatDong.setText(getString(R.string.dangmocua));
            } else {
                txt_TrangThaiHoatDong.setText(getString(R.string.dangmdongcua));

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        txt_TieudeToolbar.setText(quanAnModels.getTenquanan());

        txt_TenQuanAn_chitietquanan.setText(quanAnModels.getTenquanan());
        txt_DiaChiQuanAn_chitietquanan.setText(quanAnModels.getChiNhanhQuanAnModelList().get(0).getDiachi());
        txt_thoiGian_hoatdong.setText(giomocua + " - " + giodongcua);
        txt_sl_HinhAnh.setText(quanAnModels.getHinhquanAn().size() + "");
        txt_sl_BinhLuan.setText(quanAnModels.getBinhluanModelList().size() + "");
        DownLoadHinhTienIch();

        NumberFormat numberFormat = new DecimalFormat("###,###");
        String giatoithieu = numberFormat.format(quanAnModels.getGiatoithieu()) + "VNĐ";
        String giatoida = numberFormat.format(quanAnModels.getGiatoida()) + "VNĐ";

        if (quanAnModels.getGiatoithieu() != 0 && quanAnModels.getGiatoida() != 0) {
            txt_GioiHanGia.setText(giatoithieu + " - " + giatoida);
        } else {
            txt_GioiHanGia.setVisibility(View.INVISIBLE);
        }

        StorageReference storageHinhQuanAn = FirebaseStorage.getInstance().getReference().child("hinhanh").child(quanAnModels.getHinhquanAn().get(0));
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhQuanAn.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                img_HinhQuanAn_CTQA.setImageBitmap(bitmap);
            }
        });
        if (quanAnModels.getVideogioithieu() != null) {

            videoView.setVisibility(View.VISIBLE);
            img_HinhQuanAn_CTQA.setVisibility(View.GONE);


            StorageReference storageVideoTrailer = FirebaseStorage.getInstance().getReference().child("video").child(quanAnModels.getVideogioithieu());
            storageVideoTrailer.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    videoView.setVideoURI(uri);
                    videoView.start();
                    videoView.seekTo(1);
                }
            });
        } else {
            videoView.setVisibility(View.GONE);
            img_HinhQuanAn_CTQA.setVisibility(View.VISIBLE);

        }

        NestedScrollView nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollViewChitiet);
        nestedScrollView.smoothScrollBy(0, 0);

        chiTieuQuanAnController.HienThiDanhSachWifi(quanAnModels.getMaquanan(), txt_tenWifi, txt_passwordWifi, txt_NgaydangWifi);

        linear_khungWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiTietQuanAnActivity.this, CapNhatDanhSachWifiActivity.class);
                intent.putExtra("maquanan", quanAnModels.getMaquanan());
                intent.putExtra("tenquanan", quanAnModels.getTenquanan());
                startActivity(intent);
            }
        });
        thucDonController.getDanhSachThucDonQuanAnTheoMa(this,quanAnModels.getMaquanan(), recyclerViewThucdonQuanAn);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapterDanhSachBinhLuan = new AdapterDanhSachBinhLuan(this, R.layout.custom_binhluan, quanAnModels.getBinhluanModelList());
        recyclerView.setAdapter(adapterDanhSachBinhLuan);
        adapterDanhSachBinhLuan.notifyDataSetChanged();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        double latitude = quanAnModels.getChiNhanhQuanAnModelList().get(0).getLatitude();
        double longitude = quanAnModels.getChiNhanhQuanAnModelList().get(0).getLongitude();

        LatLng latLng = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(quanAnModels.getTenquanan());
        googleMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
        googleMap.moveCamera(cameraUpdate);

    }

    private void DownLoadHinhTienIch() {
        for (String matienich : quanAnModels.getTienich()) {

            DatabaseReference nodeTienIch = FirebaseDatabase.getInstance().getReference().child("quanlytienichs").child(matienich);
            nodeTienIch.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    TienIchModel tienIchModel = dataSnapshot.getValue(TienIchModel.class);

                    StorageReference storageHinhQuanAn = FirebaseStorage.getInstance().getReference().child("hinhtienich").child(tienIchModel.getHinhtienich());
                    long ONE_MEGABYTE = 1024 * 1024;
                    storageHinhQuanAn.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            ImageView imageTienIch = new ImageView(ChiTietQuanAnActivity.this);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(70, 70);
                            layoutParams.setMargins(10, 10, 10, 10);
                            imageTienIch.setLayoutParams(layoutParams);
                            imageTienIch.setScaleType(ImageView.ScaleType.FIT_XY);
                            imageTienIch.setPadding(5, 5, 5, 5);

                            imageTienIch.setImageBitmap(bitmap);
                            linear_Khungtienich.addView(imageTienIch);
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.khungMap:
                Intent intentMap = new Intent(ChiTietQuanAnActivity.this, DuongDiQuanAnActivity.class);
                intentMap.putExtra("Latitude", quanAnModels.getChiNhanhQuanAnModelList().get(0).getLatitude());
                intentMap.putExtra("Longitude", quanAnModels.getChiNhanhQuanAnModelList().get(0).getLongitude());
                startActivity(intentMap);
                break;
            case R.id.btn_binhluanChitietquanAn:
                Intent intentbinhLuan = new Intent(ChiTietQuanAnActivity.this, BinhLuanActivity.class);
                intentbinhLuan.putExtra("maquanan", quanAnModels.getMaquanan());
                intentbinhLuan.putExtra("tenquanan", quanAnModels.getTenquanan());
                intentbinhLuan.putExtra("diachi", quanAnModels.getChiNhanhQuanAnModelList().get(0).getDiachi());
                startActivity(intentbinhLuan);
                break;

        }


    }
}
