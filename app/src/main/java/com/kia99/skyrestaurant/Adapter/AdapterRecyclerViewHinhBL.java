package com.kia99.skyrestaurant.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kia99.skyrestaurant.Model.BinhluanModel;
import com.kia99.skyrestaurant.R;
import com.kia99.skyrestaurant.View.activity.HinhChiTietBinhLuan;

import java.util.ArrayList;
import java.util.List;

public class AdapterRecyclerViewHinhBL extends RecyclerView.Adapter<AdapterRecyclerViewHinhBL.ViewHolderHinhBl> {
    Context context;
    int layout;
    List<Bitmap> listHinhBinhLuan;
    BinhluanModel binhluanModel;
    boolean ischiTietBinhluan;

    public AdapterRecyclerViewHinhBL(Context context, int layout, List<Bitmap> listHinhBinhLuan, BinhluanModel binhluanModel, boolean ischiTietBinhluan) {
        this.context = context;
        this.layout = layout;
        this.binhluanModel = binhluanModel;
        this.listHinhBinhLuan = listHinhBinhLuan;
        this.ischiTietBinhluan = ischiTietBinhluan;
    }

    public class ViewHolderHinhBl extends RecyclerView.ViewHolder {
        ImageView img_custom_hinhBinhLuan;
        TextView txt_custom_sohinhBinhLuan;
        FrameLayout frame_khunghinh_SLHinhAnh;

        public ViewHolderHinhBl(@NonNull View itemView) {
            super(itemView);
            img_custom_hinhBinhLuan = (ImageView) itemView.findViewById(R.id.img_custom_hinhBinhLuan);
            txt_custom_sohinhBinhLuan = (TextView) itemView.findViewById(R.id.txt_custom_sohinhBinhLuan);
            frame_khunghinh_SLHinhAnh = (FrameLayout) itemView.findViewById(R.id.frame_khunghinh_SLHinhAnh);
        }
    }

    @NonNull
    @Override
    public AdapterRecyclerViewHinhBL.ViewHolderHinhBl onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolderHinhBl viewHolderHinhBl = new ViewHolderHinhBl(view);
        return viewHolderHinhBl;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerViewHinhBL.ViewHolderHinhBl holder, int position) {
        holder.img_custom_hinhBinhLuan.setImageBitmap(listHinhBinhLuan.get(position));

        if (!ischiTietBinhluan) {
            if (position == 3) {
                int slHinhConlai = listHinhBinhLuan.size() - 4;
                if (slHinhConlai > 0) {
                    holder.frame_khunghinh_SLHinhAnh.setVisibility(View.VISIBLE);
                    holder.txt_custom_sohinhBinhLuan.setText("+" + slHinhConlai);
                    holder.img_custom_hinhBinhLuan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent iChitietHinhBl = new Intent(context, HinhChiTietBinhLuan.class);
                            iChitietHinhBl.putExtra("binhluanmodel", binhluanModel);
                            context.startActivity(iChitietHinhBl);
                        }
                    });

                }
            }

        }
    }

    @Override
    public int getItemCount() {
        if (ischiTietBinhluan) {
            if (listHinhBinhLuan.size() < 4) {
               return listHinhBinhLuan.size();
            } else {
                return 4;
            }

        } else {
            return listHinhBinhLuan.size();
        }
    }


}
