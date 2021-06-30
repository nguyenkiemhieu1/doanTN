package com.kia99.skyrestaurant.Controller;

import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.kia99.skyrestaurant.Model.DownloadPolyLine;
import com.kia99.skyrestaurant.Model.ParserPolyline;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DanDuongQuanAnController {
    ParserPolyline parserPolyline;
    DownloadPolyLine downloadPolyLine;

    public DanDuongQuanAnController() {

    }

    public void HienThiDuongDiQuanAn(GoogleMap googleMap, String duongdan) {
        parserPolyline = new ParserPolyline();
        downloadPolyLine = new DownloadPolyLine();
        downloadPolyLine.execute(duongdan);

        try {
            String data = downloadPolyLine.get();
//            Log.d("testdata", data);
            List<LatLng> latLngs = parserPolyline.LayDanhSachToaDo(data);
            PolylineOptions polylineOptions = new PolylineOptions();

            for(LatLng toado :latLngs){
                polylineOptions.add(toado);
            }
            Polyline polyline  = googleMap.addPolyline(polylineOptions);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
