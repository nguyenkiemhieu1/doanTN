package com.kia99.skyrestaurant.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kia99.skyrestaurant.Model.DatMonModel;
import com.kia99.skyrestaurant.Model.MonAnModel;
import com.kia99.skyrestaurant.Model.ThucDonModel;
import com.kia99.skyrestaurant.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterDanhSachMonAn extends RecyclerView.Adapter<AdapterDanhSachMonAn.ViewHolderMonAn> {
    private Context context;
    private List<MonAnModel> monAnModelList;
    public static List<DatMonModel> datMonModelList = new ArrayList<>();

    public AdapterDanhSachMonAn(Context context, List<MonAnModel> monAnModelList) {
        this.context = context;
        this.monAnModelList = monAnModelList;
    }

    @NonNull
    @Override
    public ViewHolderMonAn onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_monan, parent, false);
        return new ViewHolderMonAn(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMonAn holder, int position) {
        MonAnModel monAnModel = monAnModelList.get(position);
        holder.txt_TenMonAn.setText("- " + monAnModel.getTenmon());
        holder.txt_slmua.setTag(0);
        holder.img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dem = Integer.parseInt(holder.txt_slmua.getTag().toString());
                dem++;
                holder.txt_slmua.setText(dem + "");
                holder.txt_slmua.setTag(dem);

                DatMonModel datMonModelTag = (DatMonModel) holder.img_remove.getTag();
                if(datMonModelTag != null){
                    AdapterDanhSachMonAn.datMonModelList.remove(datMonModelTag);
                }

                DatMonModel datMonModel = new DatMonModel();
                datMonModel.setSoluong(dem);
                datMonModel.setTenMonan(monAnModel.getTenmon());

                holder.img_remove.setTag(datMonModel);

                AdapterDanhSachMonAn.datMonModelList.add(datMonModel);
            }
        });
        holder.img_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dem = Integer.parseInt(holder.txt_slmua.getTag().toString().trim());
                if (dem != 0) {
                    dem--;
                    if(dem == 0){
                        DatMonModel datMonModel = (DatMonModel) v.getTag();
                        AdapterDanhSachMonAn.datMonModelList.remove(datMonModel);
                    }
                }
                holder.txt_slmua.setText(dem + "");
                holder.txt_slmua.setTag(dem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return monAnModelList.size();
    }

    public class ViewHolderMonAn extends RecyclerView.ViewHolder {
        TextView txt_TenMonAn, txt_slmua;
        ImageView img_remove, img_add;

        public ViewHolderMonAn(@NonNull View itemView) {
            super(itemView);
            txt_TenMonAn = (TextView) itemView.findViewById(R.id.txt_tenMonAN);
            img_add = (ImageView) itemView.findViewById(R.id.img_add);
            img_remove = (ImageView) itemView.findViewById(R.id.img_remove);
            txt_slmua = (TextView) itemView.findViewById(R.id.txt_slmua);

        }

    }
}
