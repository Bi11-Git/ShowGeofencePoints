package gr.hua.dit.android.showgeofencepoints;

import androidx.fragment.app.FragmentActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import gr.hua.dit.android.showgeofencepoints.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    ContentResolver resolver;

    private static final String AUTHORITY = "gr.hua.dit.android.assignmentprovider";
    public static final String CONTENT_URI = "content://"+AUTHORITY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        resolver = this.getContentResolver();

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

        // Add a marker in Athens and move the camera
        LatLng marker = new LatLng(37.9715, 23.7267);    //Athens
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 16));


        Cursor cursor = resolver.query(Uri.parse(CONTENT_URI+"/coordinates")
                ,null,null,null,null);


        if (cursor.moveToFirst()){
            do{
//                System.out.println(cursor.getString(2) + "++++++++++++++++++");

                String actons = cursor.getString(2);

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(cursor.getDouble(0), cursor.getDouble(1)));
                markerOptions.title(actons);
                markerOptions.snippet(cursor.getString(3));

                if(actons.equals("Exit ")) {
                    markerOptions.icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                }

                mMap.addMarker(markerOptions);




            }while (cursor.moveToNext());
        }
    }
}