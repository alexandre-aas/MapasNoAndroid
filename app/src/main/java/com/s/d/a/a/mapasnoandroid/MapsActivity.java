package com.s.d.a.a.mapasnoandroid;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.UiSettings;

import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.Manifest;
import android.widget.Toast;
import android.content.pm.PackageManager;
import android.util.Log;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int CODIGO_SOLICITACAO_LOCALIZACAO = 911;
    private String TAG = "API Google Maps";
    UiSettings configuracoesMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        solicitarPermissao(Manifest.permission.ACCESS_FINE_LOCATION, CODIGO_SOLICITACAO_LOCALIZACAO);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        int permissao;

        if (mMap != null) {
            permissao = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);

            if (permissao == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

                //Definir comportamentos do Mapa
                configuracoesMap = mMap.getUiSettings();
                configuracoesMap.setZoomControlsEnabled(true);
                configuracoesMap.setScrollGesturesEnabled(true);
                configuracoesMap.setTiltGesturesEnabled(true);
                configuracoesMap.setRotateGesturesEnabled(true);

            } else{
                Log.i(TAG, "Permissão para exibir localização do usuário negada!");
            }
        }
    }

    protected void solicitarPermissao(String tipoDePermissao, int codigoDaSolicitacao) {
        int permission = ContextCompat.checkSelfPermission(this, tipoDePermissao);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{tipoDePermissao}, codigoDaSolicitacao
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int codigoDaSolicitacao,
                                           String listaDePermissoes[], int[] listaDeConcessoes) {
        switch (codigoDaSolicitacao) {
            case CODIGO_SOLICITACAO_LOCALIZACAO: {

                if (listaDeConcessoes.length == 0
                        || listaDeConcessoes[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Impossível mostrar localização - permissão requirida!", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }


}
