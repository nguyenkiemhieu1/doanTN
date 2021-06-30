package com.kia99.skyrestaurant.View.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
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
import com.kia99.skyrestaurant.Controller.odau.ChiTietQuanAnController;
import com.kia99.skyrestaurant.Controller.odau.ThucDonController;
import com.kia99.skyrestaurant.Model.MonAnModel;
import com.kia99.skyrestaurant.Model.QuanAnModels;
import com.kia99.skyrestaurant.Model.ThemThucDonModel;
import com.kia99.skyrestaurant.Model.ThucDonModel;
import com.kia99.skyrestaurant.Model.TienIchModel;
import com.kia99.skyrestaurant.R;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CapNhatQuanAnActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener{
    TextView  txt_NgaydangWifi, txt_diachiQuanAn, txt_GioiHanGia, txt_thoiGian_hoatdong, txt_TrangThaiHoatDong, txt_sl_HinhAnh, txt_sl_CheckIn, txt_sl_BinhLuan, txt_sl_LuuLai, txt_TieudeToolbar, txt_tenWifi, txt_passwordWifi;
    ImageView img_HinhQuanAn_CTQA, imageView;
    QuanAnModels quanAnModels;
    Toolbar toolbar;
    RecyclerView recyclerView, recyclerViewThucdonQuanAn;
    AdapterDanhSachBinhLuan adapterDanhSachBinhLuan;
    GoogleMap googleMap;
    SupportMapFragment mapFragment;
    Button btn_binhluanquanAn;
    EditText  txt_TenQuanAn_chitietquanan,txt_DiaChiQuanAn_chitietquanan;
    List<Bitmap> hinhChupList;
//

    List<ThucDonModel> thucDonModelList;
    List<String> thucDonList;
    final int RESULT_THUCDON = 117;
    List<ThemThucDonModel> themThucDonModelList;
//

    View khungMap;
    LinearLayout linear_Khungtienich, linear_khungWifi;

    VideoView videoView;

    ChiTietQuanAnController chiTieuQuanAnController;

    LinearLayout khungchuathucdon;

    ThucDonController thucDonController;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_capnhatquanan);
        quanAnModels = getIntent().getParcelableExtra("quanan01");
        init();


        CloneThucDon();
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

        khungchuathucdon = (LinearLayout) findViewById(R.id.KhungChuaThucDonadmin);

        txt_TenQuanAn_chitietquanan = (EditText) findViewById(R.id.txt_TenQuanAn_chitietquanan);
        txt_DiaChiQuanAn_chitietquanan = (EditText) findViewById(R.id.txt_DiaChiQuanAn_chitietquanan);
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
//
        themThucDonModelList = new ArrayList<>();
        thucDonModelList = new ArrayList<>();
        thucDonList = new ArrayList<>();
        hinhChupList = new ArrayList<>();
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
                Intent intent = new Intent(CapNhatQuanAnActivity.this, CapNhatDanhSachWifiActivity.class);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_THUCDON:
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(bitmap);
                hinhChupList.add(bitmap);
                break;

        }
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
                            ImageView imageTienIch = new ImageView(CapNhatQuanAnActivity.this);
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

    private void CloneThucDon() {
        View view = LayoutInflater.from(CapNhatQuanAnActivity.this).inflate(R.layout.layout_clone_thucdon, null);
        Spinner spinnerThucDon = (Spinner) view.findViewById(R.id.spinnerThucDon);
        Button buttonThemThucDon = (Button) view.findViewById(R.id.btn_ThemCloneThucDon);
        EditText edt = (EditText) view.findViewById(R.id.edt_tenmon);
        EditText edtGiaTien = (EditText) view.findViewById(R.id.edt_giatien);
        ImageView img_chuphinh = (ImageView) view.findViewById(R.id.img_chuphinh);
        imageView = img_chuphinh;

        ArrayAdapter<String> arrayAdapterThucDon = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, thucDonList);
        spinnerThucDon.setAdapter(arrayAdapterThucDon);
        if (thucDonModelList.size() == 0) {
            LayDanhSachThucDon(arrayAdapterThucDon);
        }

        img_chuphinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, RESULT_THUCDON);
            }
        });

        buttonThemThucDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);

                long time = Calendar.getInstance().getTimeInMillis();
                String tenHinh = String.valueOf(time) + ".jpg";

                int position = spinnerThucDon.getSelectedItemPosition();
                String maThucdon = thucDonModelList.get(position).getMathucdon();

                MonAnModel monAnModel = new MonAnModel();
                monAnModel.setTenmon(edt.getText().toString());
                monAnModel.setGiatien(Long.parseLong(edtGiaTien.getText().toString()));
                monAnModel.setHinhanh(tenHinh);

                ThemThucDonModel themThucDonModel = new ThemThucDonModel();
                themThucDonModel.setMathucdon(maThucdon);
                themThucDonModel.setMonAnModel(monAnModel);
                themThucDonModelList.add(themThucDonModel);

                CloneThucDon();
            }

        });

        khungchuathucdon.addView(view);
    }

    private void LayDanhSachThucDon(ArrayAdapter<String> adapterkhuvucThucDon) {
        FirebaseDatabase.getInstance().getReference().child("thucdons").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot valueskhuVuc : snapshot.getChildren()) {
                    ThucDonModel thucDonModel = new ThucDonModel();
                    String key = valueskhuVuc.getKey();
                    String value = valueskhuVuc.getValue(String.class);

                    thucDonModel.setTenthucdon(value);
                    thucDonModel.setMathucdon(key);

                    thucDonModelList.add(thucDonModel);

                    thucDonList.add(value);
                }
                adapterkhuvucThucDon.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_binhluanChitietquanAn:
                DatabaseReference noderoot = FirebaseDatabase.getInstance().getReference();

                for (int i = 0; i < themThucDonModelList.size(); i++) {
                noderoot.child("thucdonquanans").child(quanAnModels.getMaquanan()).child(themThucDonModelList.get(i).getMathucdon()).push().setValue(themThucDonModelList.get(i).getMonAnModel());

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Bitmap bitmap = hinhChupList.get(i);

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] data = byteArrayOutputStream.toByteArray();
                FirebaseStorage.getInstance().getReference().child("hinhanh/" +themThucDonModelList.get(i).getMonAnModel().getHinhanh()).putBytes(data);
            }

        }


    }
}
