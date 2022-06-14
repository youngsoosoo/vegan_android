package com.inhatc.vegan_android;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

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

public class MapsCafeActivity extends FragmentActivity implements OnMapReadyCallback {

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


        Latitude.clear();
        Longitude.clear();
        Title.clear();
        Snipper.clear();

        //위도
        Latitude.add(37.499848);
        Latitude.add(37.535019);
        Latitude.add(37.533722);
        Latitude.add(37.537908);
        Latitude.add(37.507973);
        Latitude.add(37.384432);
        //경도
        Longitude.add(126.555608);
        Longitude.add(126.638949);
        Longitude.add(126.656547);
        Longitude.add(126.656339);
        Longitude.add(126.726380);
        Longitude.add(126.643333);
        //제목
        Title.add("\t\t도르팍 비건");
        Title.add("\t\t또아랑");
        Title.add("\t\t브레헨");
        Title.add("\t\t틈밀");
        Title.add("\t\t비건 아크 베이커리");
        Title.add("\t\t비건 무드");
        //내용
        Snipper.add("\t\t주소 : 인천 중구 백운로 4 104호\n\t\t번호 : 0507-1423-5945\n\t\t대표 메뉴 : 희한하게 카라멜맛 호두스콘 3,500원\n\t\t영업시간 : 매일 12:40 - 19:00");
        Snipper.add("\t\t주소 : 인천 서구 크리스탈로102번길 22 경연타워\n\t\t번호 : 070-4833-7027\n\t\t대표 메뉴 : 녹차 쇼콜라 갸또 4,500원\n\t\t영업시간 : 매일 12:00 - 20:00");
        Snipper.add("\t\t주소 : 인천 서구 청라에메랄드로 99\n\t\t번호 : 032-623-4530\n\t\t대표 메뉴 : 비건라떼 4,500원\n\t\t영업시간 : 매일 09:00 - 20:00");
        Snipper.add("\t\t주소 : 인천 서구 청라에메랄드로163번길 16 1층\n\t\t번호 : X\n\t\t대표 메뉴 : 머핀&파운드 3,000원\n\t\t영업시간 : 매일 10:00 - 20:00");
        Snipper.add("\t\t주소 : 인천 부평구 길주로585번길 7-20 102호\n\t\t번호 : 0507-1380-3422\n\t\t대표 메뉴 : 쑥치아바타 3,800원\n\t\t영업시간 : 매일 12:00 - 17:00");
        Snipper.add("\t\t주소 : 인천 연수구 하모니로 144\n\t\t번호 : 0507-1339-2175\n\t\t대표 메뉴 : 병아리콩 큐브 파운드 4,200원\n\t\t영업시간 : 매일 12:00 - 18:00");

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