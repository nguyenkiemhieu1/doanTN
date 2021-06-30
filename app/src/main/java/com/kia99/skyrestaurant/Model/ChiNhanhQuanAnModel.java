package com.kia99.skyrestaurant.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ChiNhanhQuanAnModel implements Parcelable {
    private  String diachi;
    private  double longitude;
    private double latitude;
    private  double khoangCach;
    public ChiNhanhQuanAnModel(){

    }
    protected ChiNhanhQuanAnModel(Parcel in) {
        diachi = in.readString();
        longitude = in.readDouble();
        latitude = in.readDouble();
        khoangCach = in.readDouble();
    }

    public static final Creator<ChiNhanhQuanAnModel> CREATOR = new Creator<ChiNhanhQuanAnModel>() {
        @Override
        public ChiNhanhQuanAnModel createFromParcel(Parcel in) {
            return new ChiNhanhQuanAnModel(in);
        }

        @Override
        public ChiNhanhQuanAnModel[] newArray(int size) {
            return new ChiNhanhQuanAnModel[size];
        }
    };

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getKhoangCach() {
        return khoangCach;
    }

    public void setKhoangCach(double khoangCach) {
        this.khoangCach = khoangCach;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(diachi);
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
        dest.writeDouble(khoangCach);
    }
}
