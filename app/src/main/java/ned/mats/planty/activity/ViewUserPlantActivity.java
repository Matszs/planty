package ned.mats.planty.activity;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.view.View;
import android.view.WindowInsets;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ned.mats.planty.R;
import ned.mats.planty.data.PlantTip;
import ned.mats.planty.data.UserPlant;

public class ViewUserPlantActivity extends WearableActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {
	UserPlant userPlant = null;
	private GoogleMap mMap;
	private MapFragment mMapFragment;
	private LatLng location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_user_plant);

		long userPlantId = getIntent().getLongExtra("user_plant_id", 0);
		if(userPlantId == 0)
			finish();

		userPlant = UserPlant.getPlant(userPlantId);
		if(userPlant == null)
			finish();

		TextView name = (TextView)findViewById(R.id.plant_name_field);
		name.setText(userPlant.name);


		String tipString = "";
		List<PlantTip> userPlantPlantTips = userPlant.plant.tips();

		for(PlantTip tip : userPlantPlantTips) {
			tipString += tip.tip + "\n\n";
		}

		TextView tipsList = (TextView)findViewById(R.id.tips_text);
		tipsList.setText(tipString);

		DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm");
		TextView created = (TextView)findViewById(R.id.plant_created_field);
		created.setText(dtfOut.print(userPlant.created));

		if(userPlant.lat != 0 && userPlant.lng != 0) {
			location = new LatLng(userPlant.lat, userPlant.lng);

			final FrameLayout topFrameLayout = (FrameLayout) findViewById(R.id.root_container);
			final FrameLayout mapFrameLayout = (FrameLayout) findViewById(R.id.map_container);

			topFrameLayout.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
				@Override
				public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
					// Call through to super implementation and apply insets
					insets = topFrameLayout.onApplyWindowInsets(insets);

					FrameLayout.LayoutParams params =
							(FrameLayout.LayoutParams) mapFrameLayout.getLayoutParams();

					// Add Wearable insets to FrameLayout container holding map as margins
					params.setMargins(
							insets.getSystemWindowInsetLeft(),
							insets.getSystemWindowInsetTop(),
							insets.getSystemWindowInsetRight(),
							insets.getSystemWindowInsetBottom());
					mapFrameLayout.setLayoutParams(params);

					return insets;
				}
			});

			mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
			mMapFragment.getMapAsync(this);
		}
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		// Map is ready to be used.
		mMap = googleMap;

		// Set the long click listener as a way to exit the map.
		mMap.setOnMapLongClickListener(this);

		// Add a marker with a title that is shown in its info window.
		mMap.addMarker(new MarkerOptions().position(location).title("Jouw plant"));

		// Move the camera to show the marker.
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
	}

	@Override
	public void onMapLongClick(LatLng latLng) {

	}
}
