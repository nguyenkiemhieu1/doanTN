package com.kia99.skyrestaurant.Adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kia99.skyrestaurant.Model.WifiQuanAnModel;
import com.kia99.skyrestaurant.R;

import java.util.List;

public class AdapterDanhSachWifi extends RecyclerView.Adapter<AdapterDanhSachWifi.ViewHolderDSWifi> {

    Context context;
    int resource;
    List<WifiQuanAnModel> wifiQuanAnModelList;

    public AdapterDanhSachWifi(Context context, int resource, List<WifiQuanAnModel> wifiQuanAnModelList) {
        this.context = context;
        this.resource = resource;
        this.wifiQuanAnModelList = wifiQuanAnModelList;
    }

    public class ViewHolderDSWifi extends RecyclerView.ViewHolder {

        TextView txt_tenwifi, txt_ngaydang, txt_password;

        public ViewHolderDSWifi(@NonNull View itemView) {
            super(itemView);
            txt_tenwifi = (TextView) itemView.findViewById(R.id.txt_tenWifi);
            txt_ngaydang = (TextView) itemView.findViewById(R.id.txt_NgaydangWifi);
            txt_password = (TextView) itemView.findViewById(R.id.txt_passwordWifi);
        }
    }
    @NonNull
    @Override
    public AdapterDanhSachWifi.ViewHolderDSWifi onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(resource, parent, false);
        ViewHolderDSWifi viewHolderDSWifi = new ViewHolderDSWifi(view);

        return viewHolderDSWifi;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDanhSachWifi.ViewHolderDSWifi holder, int position) {
        WifiQuanAnModel wifiQuanAnModel = wifiQuanAnModelList.get(position);
        holder.txt_tenwifi.setText("Tên Wifi: " + wifiQuanAnModel.getTen());
        holder.txt_password.setText("Mật khẩu: " + wifiQuanAnModel.getPassword());
        holder.txt_ngaydang.setText(wifiQuanAnModel.getNgaydang());
    }

    @Override
    public int getItemCount() {
        return wifiQuanAnModelList.size();
    }
}
