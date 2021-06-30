package com.kia99.skyrestaurant.Controller;

import com.kia99.skyrestaurant.Model.BinhluanModel;

import java.util.List;

public class BinhluanController {
    BinhluanModel binhluanModel;

    public BinhluanController(){
        binhluanModel = new BinhluanModel();

    }
     public void ThemBinhLuan(String maQuanAn, BinhluanModel binhluanModel, List<String> listHinhanh){
        binhluanModel.ThemBinhLuan(maQuanAn, binhluanModel, listHinhanh);

     }
}
