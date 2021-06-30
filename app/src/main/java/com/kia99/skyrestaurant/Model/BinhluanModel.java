package com.kia99.skyrestaurant.Model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.PropertyName;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.List;

public class BinhluanModel implements Parcelable {
    private double chamdiem;
    private Long luotthich;
    private ThanhVienModels thanhVienModels;
    @PropertyName("noidung")
    private String noidungbl;
    @PropertyName("tieude")
    private String tieudebl;
    private String mauser;
    private List<String> listHinhanh;
    String maBl;

    public BinhluanModel() {

    }

    protected BinhluanModel(Parcel in) {
        chamdiem = in.readDouble();
        if (in.readByte() == 0) {
            luotthich = null;
        } else {
            luotthich = in.readLong();
        }
        noidungbl = in.readString();
        tieudebl = in.readString();
        mauser = in.readString();
        listHinhanh = in.createStringArrayList();
        maBl = in.readString();
        thanhVienModels = in.readParcelable(ThanhVienModels.class.getClassLoader());
    }

    public static final Creator<BinhluanModel> CREATOR = new Creator<BinhluanModel>() {
        @Override
        public BinhluanModel createFromParcel(Parcel in) {
            return new BinhluanModel(in);
        }

        @Override
        public BinhluanModel[] newArray(int size) {
            return new BinhluanModel[size];
        }
    };

    public String getMaBl() {
        return maBl;
    }

    public void setMaBl(String maBl) {
        this.maBl = maBl;
    }

    public List<String> getListHinhanh() {
        return listHinhanh;
    }

    public void setListHinhanh(List<String> listHinhanh) {
        this.listHinhanh = listHinhanh;
    }

    public double getChamdiem() {
        return chamdiem;
    }

    public void setChamdiem(double chamdiem) {
        this.chamdiem = chamdiem;
    }

    public Long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(Long luotthich) {
        this.luotthich = luotthich;
    }

    public ThanhVienModels getThanhVienModels() {
        return thanhVienModels;
    }

    public void setThanhVienModels(ThanhVienModels thanhVienModels) {
        this.thanhVienModels = thanhVienModels;
    }

    public String getNoidungbl() {
        return noidungbl;
    }

    public void setNoidungbl(String noidungbl) {
        this.noidungbl = noidungbl;
    }

    public String getTieudebl() {
        return tieudebl;
    }

    public void setTieudebl(String tieudebl) {
        this.tieudebl = tieudebl;
    }

    public String getMauser() {
        return mauser;
    }

    public void setMauser(String mauser) {
        this.mauser = mauser;
    }

    @Override
    public String toString() {
        return "BinhluanModel{" +
                "chamdiem=" + chamdiem +
                ", luotthich=" + luotthich +
                ", thanhVienModels=" + thanhVienModels +
                ", noidungbl='" + noidungbl + '\'' +
                ", tieudebl='" + tieudebl + '\'' +
                ", mauser='" + mauser + '\'' +
                ", listHinhanh=" + listHinhanh +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(chamdiem);
        if (luotthich == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(luotthich);
        }
        dest.writeString(noidungbl);
        dest.writeString(tieudebl);
        dest.writeString(mauser);
        dest.writeStringList(listHinhanh);
        dest.writeString(maBl);
        dest.writeParcelable(thanhVienModels, flags);
    }

    public void ThemBinhLuan(String maQuanAn, BinhluanModel binhluanModel, List<String> listHinhanh) {
        DatabaseReference nodeBinhluan = FirebaseDatabase.getInstance().getReference().child("binhluans");
        String mabinhluan = nodeBinhluan.child(maQuanAn).push().getKey();
        nodeBinhluan.child(maQuanAn).child(mabinhluan).setValue(binhluanModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (listHinhanh.size() > 0) {
                        for (String valuesHinh : listHinhanh) {
                            Uri uri = Uri.fromFile(new File(valuesHinh));
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("hinhanh/" + uri.getLastPathSegment());
                            storageReference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                }
                            });
                        }
                    }
                }
            }
        });

        if(listHinhanh.size() > 0){
            for (String valuesHinh : listHinhanh) {
                Uri uri = Uri.fromFile(new File(valuesHinh));
                FirebaseDatabase.getInstance().getReference().child("hinhanhbinhluans").child(mabinhluan).push().setValue(uri.getLastPathSegment());

            }
        }
    }
}
