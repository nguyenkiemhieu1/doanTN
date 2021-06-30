package com.kia99.skyrestaurant.Controller.dangki;

import com.kia99.skyrestaurant.Model.ThanhVienModels;

public class DangKyController {
    ThanhVienModels thanhVienModel;
    public  DangKyController(){
        thanhVienModel = new ThanhVienModels();

    }
    public void Themthongtinthanhvien(ThanhVienModels thanhVienModel, String userID){
        thanhVienModel.ThemThongTinThanhViens(thanhVienModel, userID);
    }
}
