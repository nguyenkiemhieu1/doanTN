package com.kia99.skyrestaurant.Model;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kia99.skyrestaurant.Controller.odau.odau_interface;

public class QuanAnModels implements Parcelable {
    private boolean giaohang;
    private String giodongcua;
    private String giomocua;
    private String tenquanan;
    private String videogioithieu;
    private String maquanan;
    DatabaseReference noderoot;
    private Long luotthich;
    private Long giatoithieu;
    private Long giatoida;

    private List<String> tienich, hinhquanAn;
    private List<BinhluanModel> binhluanModelList;
    private List<ChiNhanhQuanAnModel> chiNhanhQuanAnModelList;
    private List<ThucDonModel> thucDonModels;

    private List<Bitmap> bitmapList;

    protected QuanAnModels(Parcel in) {
        giaohang = in.readByte() != 0;
        giodongcua = in.readString();
        giomocua = in.readString();
        tenquanan = in.readString();
        videogioithieu = in.readString();
        maquanan = in.readString();
        if (in.readByte() == 0) {
            luotthich = null;
        } else {
            luotthich = in.readLong();
        }
        tienich = in.createStringArrayList();
        hinhquanAn = in.createStringArrayList();
        chiNhanhQuanAnModelList = new ArrayList<ChiNhanhQuanAnModel>();
        in.readTypedList(chiNhanhQuanAnModelList, ChiNhanhQuanAnModel.CREATOR);
        binhluanModelList = new ArrayList<BinhluanModel>();
        in.readTypedList(binhluanModelList, BinhluanModel.CREATOR);

        if (in.readByte() == 0) {
            giatoithieu = null;
        } else {
            giatoithieu = in.readLong();
        }
        if (in.readByte() == 0) {
            giatoida = null;
        } else {
            giatoida = in.readLong();

        }
    }

    public List<ThucDonModel> getThucDonModels() {
        return thucDonModels;
    }

    public void setThucDonModels(List<ThucDonModel> thucDonModels) {
        this.thucDonModels = thucDonModels;
    }

    public static final Creator<QuanAnModels> CREATOR = new Creator<QuanAnModels>() {
        @Override
        public QuanAnModels createFromParcel(Parcel in) {
            return new QuanAnModels(in);
        }

        @Override
        public QuanAnModels[] newArray(int size) {
            return new QuanAnModels[size];
        }
    };

    public Long getGiatoithieu() {
        return giatoithieu;
    }

    public void setGiatoithieu(Long giatoithieu) {
        this.giatoithieu = giatoithieu;
    }

    public Long getGiatoida() {
        return giatoida;
    }

    public void setGiatoida(Long giatoida) {
        this.giatoida = giatoida;
    }

    public List<Bitmap> getBitmapList() {
        return bitmapList;
    }

    public void setBitmapList(List<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
    }

    public QuanAnModels() {
        noderoot = FirebaseDatabase.getInstance().getReference();
    }

    public List<BinhluanModel> getBinhluanModelList() {
        return binhluanModelList;
    }

    public void setBinhluanModelList(List<BinhluanModel> binhluanModelList) {
        this.binhluanModelList = binhluanModelList;
    }

    public List<ChiNhanhQuanAnModel> getChiNhanhQuanAnModelList() {
        return chiNhanhQuanAnModelList;
    }

    public void setChiNhanhQuanAnModelList(List<ChiNhanhQuanAnModel> chiNhanhQuanAnModelList) {
        this.chiNhanhQuanAnModelList = chiNhanhQuanAnModelList;
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public boolean isGiaohang() {
        return giaohang;
    }

    public void setGiaohang(boolean giaohang) {
        this.giaohang = giaohang;
    }

    public String getGiodongcua() {
        return giodongcua;
    }

    public void setGiodongcua(String giodongcua) {
        this.giodongcua = giodongcua;
    }

    public String getGiomocua() {
        return giomocua;
    }

    public void setGiomocua(String giomocua) {
        this.giomocua = giomocua;
    }

    public String getTenquanan() {
        return tenquanan;
    }

    public void setTenquanan(String tenquanan) {
        this.tenquanan = tenquanan;
    }

    public String getVideogioithieu() {
        return videogioithieu;
    }

    public void setVideogioithieu(String videogioithieu) {
        this.videogioithieu = videogioithieu;
    }

    public List<String> getTienich() {
        return tienich;
    }

    public void setTienich(List<String> tienich) {
        this.tienich = tienich;
    }

    public String getMaquanan() {
        return maquanan;
    }

    public void setMaquanan(String maquanan) {
        this.maquanan = maquanan;
    }

    public List<String> getHinhquanAn() {
        return hinhquanAn;
    }

    public void setHinhquanAn(List<String> hinhquanAn) {
        this.hinhquanAn = hinhquanAn;
    }

    private DataSnapshot dataroot;

    public void getDanhSachQuanAn(final odau_interface odau_interface, final Location vitrihientai, int itemtieptheo, int itemdaco) {

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataroot = snapshot;

                LayDanhSachQuanAn(snapshot, odau_interface, vitrihientai, itemtieptheo, itemdaco);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        if (dataroot != null) {
            LayDanhSachQuanAn(dataroot, odau_interface, vitrihientai, itemtieptheo, itemdaco);

        } else {
            noderoot.addListenerForSingleValueEvent(valueEventListener);
        }

    }

    private void LayDanhSachQuanAn(DataSnapshot snapshot, odau_interface odau_interface, Location vitrihientai, int itemtieptheo, int itembandau) {
        DataSnapshot dataSnapshotQuanAn = snapshot.child("quanans");
        int i = 0;

        for (DataSnapshot valuesQuanAn : dataSnapshotQuanAn.getChildren()) {
            if (i == itemtieptheo) {
                break;
            }
            if (i < itembandau) {
                i++;
                continue;
            }
            i++;
            QuanAnModels quanAnModels = valuesQuanAn.getValue(QuanAnModels.class);
            quanAnModels.setMaquanan(valuesQuanAn.getKey());
            //  danh sach hinh anh
            DataSnapshot dataSnapshotHinhQuanAn = snapshot.child("hinhanhquanans").child(valuesQuanAn.getKey());
            List<String> hinhanhList = new ArrayList<>();

            for (DataSnapshot valuesHinhQuanAn : dataSnapshotHinhQuanAn.getChildren()) {
                hinhanhList.add(valuesHinhQuanAn.getValue(String.class));
            }
//            Log.d("KiemTra", String.valueOf(quanAnModels.getTenquanan()));
            quanAnModels.setHinhquanAn(hinhanhList);
// lấy ds bình luận
            DataSnapshot dataSnapshotBinhluan = snapshot.child("binhluans").child(quanAnModels.getMaquanan());
            List<BinhluanModel> binhluanModels = new ArrayList<>();

            for (DataSnapshot valuesbinhluan : dataSnapshotBinhluan.getChildren()) {
                BinhluanModel binhluanModel = valuesbinhluan.getValue(BinhluanModel.class);
                binhluanModel.setMaBl(valuesbinhluan.getKey());
//                Log.d("kiemtra",valuesbinhluan.getKey()+ "" );

                ThanhVienModels thanhVienModels = snapshot.child("thanhviens").child(binhluanModel.getMauser()).getValue(ThanhVienModels.class);
                binhluanModel.setThanhVienModels(thanhVienModels);

                List<String> hinhanhblList = new ArrayList<>();
                DataSnapshot snapshothinhanhBl = snapshot.child("hinhanhbinhluans").child(binhluanModel.getMaBl());
                for (DataSnapshot valueshinhanhBinhluan : snapshothinhanhBl.getChildren()) {
                    hinhanhblList.add(valueshinhanhBinhluan.getValue(String.class));
                }
                binhluanModel.setListHinhanh(hinhanhblList);

                binhluanModels.add(binhluanModel);
//                Log.d("kiemtra",String.valueOf(binhluanModel.getNoidungbl()));
            }
            quanAnModels.setBinhluanModelList(binhluanModels);
            //lấy chi nhánh quán ăn

            List<ChiNhanhQuanAnModel> chiNhanhQuanAnModels = new ArrayList<>();
            DataSnapshot dataSnapshotChiNhanhQuanAN = snapshot.child("chinhanhquanans").child(quanAnModels.getMaquanan());
            for (DataSnapshot valuesQuanAnchinhanh : dataSnapshotChiNhanhQuanAN.getChildren()) {
                ChiNhanhQuanAnModel chiNhanhQuanAnModel = valuesQuanAnchinhanh.getValue(ChiNhanhQuanAnModel.class);
                Location vitriquanan = new Location("");
                vitriquanan.setLatitude(chiNhanhQuanAnModel.getLatitude());
                vitriquanan.setLongitude(chiNhanhQuanAnModel.getLongitude());

                double khoangcach = vitrihientai.distanceTo(vitriquanan) / 1000;
//                       Log.d("kiemtrakhoangcach", khoangcach + " : " + chiNhanhQuanAnModel.getDiachi());
                chiNhanhQuanAnModel.setKhoangCach(khoangcach);
                chiNhanhQuanAnModels.add(chiNhanhQuanAnModel);
            }
            quanAnModels.setChiNhanhQuanAnModelList(chiNhanhQuanAnModels);

            odau_interface.getLaydanhsachQuanAn(quanAnModels);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (giaohang ? 1 : 0));
        dest.writeString(giodongcua);
        dest.writeString(giomocua);
        dest.writeString(tenquanan);
        dest.writeString(videogioithieu);
        dest.writeString(maquanan);
        if (luotthich == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(luotthich);
        }


        dest.writeStringList(tienich);
        dest.writeStringList(hinhquanAn);
        dest.writeTypedList(chiNhanhQuanAnModelList);
        dest.writeTypedList(binhluanModelList);
        if (giatoithieu == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(giatoithieu);
        }
        if (giatoida == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(giatoida);
        }
    }
}
