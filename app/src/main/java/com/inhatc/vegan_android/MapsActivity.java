package com.inhatc.vegan_android;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.inhatc.vegan_android.databinding.ActivityMapsBinding;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private List<Double> Latitude = new ArrayList<>();
    private List<Double> Longitude = new ArrayList<>();
    private List<String> Title = new ArrayList<>();
    private List<String> Snipper = new ArrayList<>();
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        title = (TextView) findViewById(R.id.mapText);

        Latitude.add(37.527539);
        Latitude.add(37.474004);
        Latitude.add(37.507785);
        Latitude.add(37.448477 );
        Longitude.add(126.672327);
        Longitude.add(126.618553);
        Longitude.add(126.726396);
        Longitude.add(126.695391);
        Title.add("\t\t손오공 마라탕");
        Title.add("\t\t태화원");
        Title.add("\t\t단미트레이");
        Title.add("\t\t일용할 양식");
        Snipper.add("\t\t주소 : 인천 서구 봉오재3로 115 1층\n\t\t번호 : 0507-1349-6881\n\t\t대표 메뉴 : 버섯 꿔바로우 9,900원\n\t\t영업시간 : 매일 11:00 - 22:00");
        Snipper.add("\t\t주소 : 인천 중구 차이나타운로59번길 10\n\t\t번호 : 032-766-7688\n\t\t대표 메뉴 : 자연송이밥 20,000원\n\t\t영업시간 : 매일 11:00 - 21:30");
        Snipper.add("\t\t주소 : 인천광역시 부평구 길주로585번길 7-19\n\t\t번호 : 032-505-7276\n\t\t시금치두유리조또 11,000원\n\t\t영업시간 : 매일 11:00 - 21:00");
        Snipper.add("\t\t주소 : 인천 남동구 인주대로522번길 50 1층\n\t\t번호 : 0507-1393-3312\n\t\t바질페스토 파스타 16,500원\n\t\t영업시간 : 매일 11:00 - 21:00");


        double dLatitude = 37.448344;
        double dLongitude = 126.657474;
        LatLng objLocation;


        // Add a marker in Sydney and move the camera
        objLocation = new LatLng(dLatitude, dLongitude);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(objLocation));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(12));

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        for(int i=0; i<Latitude.size();i++){
            // 마커 표시하기 : 위치지정, 풍선말 설정
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(new LatLng(Double.valueOf(Latitude.get(i)), Double.valueOf(Longitude.get(i))))
                    .title(Title.get(i))
                    .snippet(Snipper.get(i));

            // 마커를 추가하고 말풍선 표시한 것을 보여주도록 호출
            mMap.addMarker(markerOptions).showInfoWindow();

        }



        // 마커 클릭했을 떄 처리 : 리스너 달기
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                title.setText("");
                title.setText(marker.getTitle() + "\n" + marker.getSnippet());
                return false;
            }
        });


    }
}