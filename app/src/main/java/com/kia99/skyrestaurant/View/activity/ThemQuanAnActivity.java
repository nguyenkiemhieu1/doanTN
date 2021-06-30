package com.kia99.skyrestaurant.View.activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.kia99.skyrestaurant.Model.ChiNhanhQuanAnModel;
import com.kia99.skyrestaurant.Model.MonAnModel;
import com.kia99.skyrestaurant.Model.QuanAnModels;
import com.kia99.skyrestaurant.Model.ThemThucDonModel;
import com.kia99.skyrestaurant.Model.ThucDonModel;
import com.kia99.skyrestaurant.Model.TienIchModel;
import com.kia99.skyrestaurant.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ThemQuanAnActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    final int RESULT_IMG = 111;
    final int RESULT_IMG2 = 112;
    final int RESULT_IMG3 = 113;
    final int RESULT_IMG4 = 114;
    final int RESULT_IMG5 = 115;
    final int RESULT_IMG6 = 116;
    final int RESULT_THUCDON = 117;
    final int RESULT_VIDEO = 110;

    Button btn_giomocua, btn_giodongcua, btn_ThemQuanAn;
    String giomocua, giodongcua, khuvuc;
    Spinner spinnerKhuVuc;
    EditText edt_TenQuanAnNew, edt_GiaToiDaNew, edt_GiaToiThieuNew;
    RadioGroup groupTrangThai;

    List<ThucDonModel> thucDonModelList;
    List<String> selectedTienIchList;
    List<String> KhuVucList;
    List<String> thucDonList;
    List<String> chiNhanhList;
    List<Bitmap> hinhChupList;
    List<ThemThucDonModel> themThucDonModelList;
    List<Uri> hinhQuanAn;

    Uri uriVideo;

    ArrayAdapter<String> arrayAdapterKhuVuc;
    LinearLayout horizontal_TienIch, KhungChiNhanh, KhungChuaChiNhanh, khungchuathucdon;

    ImageView imageView, img_hinhQuan, img_hinhQuan2, img_hinhQuan3, img_hinhQuan4, img_hinhQuan5, img_hinhQuan6, imgVideo;
    VideoView VideoView;

    Toolbar toolbar_add;
    RadioButton radioGiaoHang, radioKhongGiaoHang;
    String maquanan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themquanan);
        init();

        CloneThucDon();
        CloneChiNhanh();
        LayDanhSachKhuVuc();
        LayDanhSachTienIch();

        toolbar_add.setTitle("Thêm quán ăn");
        setSupportActionBar(toolbar_add);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private void init() {
        btn_giodongcua = (Button) findViewById(R.id.btn_giodongcua);
        btn_giomocua = (Button) findViewById(R.id.btn_giomocua);
        btn_ThemQuanAn = (Button) findViewById(R.id.btn_ThemQuanAn);

        edt_TenQuanAnNew = (EditText) findViewById(R.id.edt_TenQuanAnNew);
        edt_GiaToiDaNew = (EditText) findViewById(R.id.edt_GiaToiDaNew);
        edt_GiaToiThieuNew = (EditText) findViewById(R.id.edt_GiaToiThieuNew);

        groupTrangThai = (RadioGroup) findViewById(R.id.groupTrangThai);

        spinnerKhuVuc = (Spinner) findViewById(R.id.spinnerKhuVuc);

        horizontal_TienIch = (LinearLayout) findViewById(R.id.horizontal_TienIch);
        KhungChiNhanh = (LinearLayout) findViewById(R.id.khungChiNhanh);
        KhungChuaChiNhanh = (LinearLayout) findViewById(R.id.KhungChuaChiNhanh);
        khungchuathucdon = (LinearLayout) findViewById(R.id.KhungChuaThucDon);
        toolbar_add = (Toolbar)findViewById(R.id.toolbar_add);

        thucDonModelList = new ArrayList<>();

        thucDonList = new ArrayList<>();
        KhuVucList = new ArrayList<>();
        chiNhanhList = new ArrayList<>();
        themThucDonModelList = new ArrayList<>();
        hinhChupList = new ArrayList<>();
        hinhQuanAn = new ArrayList<>();

        img_hinhQuan = (ImageView) findViewById(R.id.img_hinhQuan);
        img_hinhQuan2 = (ImageView) findViewById(R.id.img_hinhQuan2);
        img_hinhQuan3 = (ImageView) findViewById(R.id.img_hinhQuan3);
        img_hinhQuan4 = (ImageView) findViewById(R.id.img_hinhQuan4);
        img_hinhQuan5 = (ImageView) findViewById(R.id.img_hinhQuan5);
        img_hinhQuan6 = (ImageView) findViewById(R.id.img_hinhQuan6);
        imgVideo = (ImageView) findViewById(R.id.imgVideo);
        VideoView = (VideoView) findViewById(R.id.VideoView);


        arrayAdapterKhuVuc = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, KhuVucList);
        spinnerKhuVuc.setAdapter(arrayAdapterKhuVuc);
        arrayAdapterKhuVuc.notifyDataSetChanged();

        selectedTienIchList = new ArrayList<>();

        btn_giomocua.setOnClickListener(this);
        btn_giodongcua.setOnClickListener(this);
        spinnerKhuVuc.setOnItemSelectedListener(this);
        img_hinhQuan.setOnClickListener(this);
        img_hinhQuan2.setOnClickListener(this);
        img_hinhQuan3.setOnClickListener(this);
        img_hinhQuan4.setOnClickListener(this);
        img_hinhQuan5.setOnClickListener(this);
        img_hinhQuan6.setOnClickListener(this);
        imgVideo.setOnClickListener(this);
        btn_ThemQuanAn.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_IMG:
                if (RESULT_OK == resultCode) {
                    Uri uri = data.getData();
                    img_hinhQuan.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }

                break;
            case RESULT_IMG2:
                if (RESULT_OK == resultCode) {
                    Uri uri = data.getData();
                    img_hinhQuan2.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }

                break;
            case RESULT_IMG3:
                if (RESULT_OK == resultCode) {
                    Uri uri = data.getData();
                    img_hinhQuan3.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }

                break;
            case RESULT_IMG4:
                if (RESULT_OK == resultCode) {
                    Uri uri = data.getData();
                    img_hinhQuan4.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }

                break;
            case RESULT_IMG5:
                if (RESULT_OK == resultCode) {
                    Uri uri = data.getData();
                    img_hinhQuan5.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }

                break;
            case RESULT_IMG6:
                if (RESULT_OK == resultCode) {
                    Uri uri = data.getData();
                    img_hinhQuan6.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }

                break;
            case RESULT_THUCDON:
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(bitmap);
                hinhChupList.add(bitmap);
                break;
            case RESULT_VIDEO:
                imgVideo.setVisibility(View.GONE);
                Uri uri = data.getData();
                uriVideo = uri;
                VideoView.setVideoURI(uri);
                VideoView.start();
                break;
        }

    }

    private void CloneThucDon() {
        View view = LayoutInflater.from(ThemQuanAnActivity.this).inflate(R.layout.layout_clone_thucdon, null);
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

    private void CloneChiNhanh() {
        View view = LayoutInflater.from(ThemQuanAnActivity.this).inflate(R.layout.layout_clone_chinhanh, null);
        ImageButton imageButtonThem = (ImageButton) view.findViewById(R.id.ibtn_ThemChiNhanh);
        ImageButton imageButtonBot = (ImageButton) view.findViewById(R.id.ibtn_XoaThemChiNhanh);

        imageButtonThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) view.findViewById(R.id.edt_ChiNhanh);

                String TenChiNhanh = editText.getText().toString();
                v.setVisibility(View.GONE);
                imageButtonBot.setVisibility(View.VISIBLE);
                imageButtonBot.setTag(TenChiNhanh);
                chiNhanhList.add(TenChiNhanh);
                CloneChiNhanh();
            }
        });
        imageButtonBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenChiNhanh = v.getTag().toString();
                chiNhanhList.remove(tenChiNhanh);
                KhungChuaChiNhanh.removeView(view);
            }
        });
        KhungChuaChiNhanh.addView(view);
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

    private void LayDanhSachKhuVuc() {
        FirebaseDatabase.getInstance().getReference().child("khuvucs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot valueskhuVuc : snapshot.getChildren()) {
                    String tenKhuVuc = valueskhuVuc.getKey();
                    KhuVucList.add(tenKhuVuc);
                }
                arrayAdapterKhuVuc.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void LayDanhSachTienIch() {
        FirebaseDatabase.getInstance().getReference().child("quanlytienichs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot valuesTienIch : snapshot.getChildren()) {
                    String maTienIch = valuesTienIch.getKey();
                    TienIchModel tienIchModel = valuesTienIch.getValue(TienIchModel.class);
                    tienIchModel.setMaTienIch(maTienIch);

                    CheckBox checkBox = new CheckBox(ThemQuanAnActivity.this);
                    checkBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    checkBox.setText(tienIchModel.getTentienich());
                    checkBox.setTag(maTienIch);
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                selectedTienIchList.add(maTienIch);
                            } else {
                                selectedTienIchList.remove(maTienIch);
                            }
                        }
                    });
                    horizontal_TienIch.addView(checkBox);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        Calendar calendar = Calendar.getInstance();
        int gio = calendar.get(Calendar.HOUR_OF_DAY);
        int phut = calendar.get(Calendar.MINUTE);
        switch (v.getId()) {
            case R.id.btn_giodongcua:
                TimePickerDialog timePickerDialogDongCua = new TimePickerDialog(ThemQuanAnActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        giodongcua = hourOfDay + ":" + minute;
                        ((Button) v).setText(giodongcua);
                    }
                }, gio, phut, true);
                timePickerDialogDongCua.show();
                break;
            case R.id.btn_giomocua:
                TimePickerDialog timePickerDialogdmocua = new TimePickerDialog(ThemQuanAnActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        giomocua = hourOfDay + ":" + minute;
                        ((Button) v).setText(giomocua);
                    }
                }, gio, phut, true);
                timePickerDialogdmocua.show();
                break;
            case R.id.img_hinhQuan:
                ChonHinhAnhTuGallary(RESULT_IMG);
                break;
            case R.id.img_hinhQuan2:
                ChonHinhAnhTuGallary(RESULT_IMG2);
                break;
            case R.id.img_hinhQuan3:
                ChonHinhAnhTuGallary(RESULT_IMG3);
                break;
            case R.id.img_hinhQuan4:
                ChonHinhAnhTuGallary(RESULT_IMG4);
                break;
            case R.id.img_hinhQuan5:
                ChonHinhAnhTuGallary(RESULT_IMG5);
                break;
            case R.id.img_hinhQuan6:
                ChonHinhAnhTuGallary(RESULT_IMG6);
                break;
            case R.id.imgVideo:
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Chọn Video..."), RESULT_VIDEO);
                break;
            case R.id.btn_ThemQuanAn:
//                ThemQuanAn();
                break;
        }
    }

    private void ThemQuanAn() {
        String tenquanAn = edt_TenQuanAnNew.getText().toString();
        long giatoida = Long.parseLong(edt_GiaToiDaNew.getText().toString());
        long giatoithieu = Long.parseLong(edt_GiaToiThieuNew.getText().toString());

        int RadioSelected = groupTrangThai.getCheckedRadioButtonId();
        boolean giaohang = false;
        if (RadioSelected == R.id.radioGiaoHang) {
            giaohang = true;
        } else {
            giaohang = false;
        }


        DatabaseReference noderoot = FirebaseDatabase.getInstance().getReference();
        DatabaseReference nodeQuanAn = noderoot.child("quanans");


        maquanan = nodeQuanAn.push().getKey();

        noderoot.child("khuvucs").child(khuvuc).push().setValue(maquanan);

        for (String chinhanh : chiNhanhList) {
            String urlGeoCoding = "https://maps.googleapis.com/maps/api/geocode/json?address=" + chinhanh.replace(" ", "%20") + "&key=AIzaSyDB4NIzwEvupxZVYEKZn59-iWK5svRGbzk";
            downloadingToaDo downloadingToaDo = new downloadingToaDo();
            downloadingToaDo.execute(urlGeoCoding);
//            ChiNhanhQuanAnModel chiNhanhQuanAnModel = new ChiNhanhQuanAnModel();
//            chiNhanhQuanAnModel.setDiachi(chinhanh);
//            chiNhanhQuanAnModel.setLatitude(1);
//            chiNhanhQuanAnModel.setLatitude(1);

//            FirebaseDatabase.getInstance().getReference().child("chinhanhquanans").child(maquanan).push().setValue(chiNhanhQuanAnModel);
        }

            QuanAnModels quanAnModels = new QuanAnModels();
            quanAnModels.setTenquanan(tenquanAn);
            quanAnModels.setGiatoida(giatoida);
            quanAnModels.setGiatoithieu(giatoithieu);
            quanAnModels.setGiaohang(giaohang);
            quanAnModels.setVideogioithieu(uriVideo.getLastPathSegment());
            quanAnModels.setTienich(selectedTienIchList);
            quanAnModels.setLuotthich(0);
            nodeQuanAn.child(maquanan).setValue(quanAnModels).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                }
            });
            FirebaseStorage.getInstance().getReference().child("video/" + uriVideo.getLastPathSegment()).putFile(uriVideo);
            for (Uri hinhquan : hinhQuanAn) {
                FirebaseStorage.getInstance().getReference().child("hinhanh/" + hinhquan.getLastPathSegment()).putFile(hinhquan);
                noderoot.child("hinhanhquanans").child(maquanan).push().child(hinhquan.getLastPathSegment());

            }

            for (int i = 0; i < themThucDonModelList.size(); i++) {
                noderoot.child("thucdonquanans").child(maquanan).child(themThucDonModelList.get(i).getMathucdon()).push().setValue(themThucDonModelList.get(i).getMonAnModel());

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Bitmap bitmap = hinhChupList.get(i);

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] data = byteArrayOutputStream.toByteArray();
                FirebaseStorage.getInstance().getReference().child("hinhanh/" +themThucDonModelList.get(i).getMonAnModel().getHinhanh()).putBytes(data);
            }

    }

    class downloadingToaDo extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray array = jsonObject.getJSONArray("results");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject j = array.getJSONObject(i);
                    String address = j.getString("formatted_address");

                    JSONObject geo = j.getJSONObject("geometry");

                    JSONObject location = geo.getJSONObject("location");
                    double lat = (double) location.get("lat");
                    double longt = (double) location.get("long");

                    ChiNhanhQuanAnModel chiNhanhQuanAnModel = new ChiNhanhQuanAnModel();
                    chiNhanhQuanAnModel.setDiachi(address);
                    chiNhanhQuanAnModel.setLatitude(lat);
                    chiNhanhQuanAnModel.setLatitude(longt);

                    FirebaseDatabase.getInstance().getReference().child("chinhanhquanans").child(maquanan).push().setValue(chiNhanhQuanAnModel);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinnerKhuVuc:
                khuvuc = KhuVucList.get(position);
                break;
        }
    }

    private void ChonHinhAnhTuGallary(int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn hình..."), requestCode);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
