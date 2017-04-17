package ned.mats.planty.activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.CircularArray;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.wearable.Wearable;

import org.joda.time.DateTime;

import java.util.List;

import ned.mats.planty.R;
import ned.mats.planty.adapter.PlantsListAdapter;
import ned.mats.planty.data.Plant;
import ned.mats.planty.data.UserPlant;

public class AddUserPlantActivity extends Activity implements LocationListener {

	private GoogleApiClient mGoogleApiClient;
	private WearableListView listView;
	private List<Plant> plants;
	private Plant plant = null;

	private static final int PERMISSIONS_REQUEST_CODE = 100;
	private static final int UPDATE_INTERVAL_MS = 5 * 1000;
	private static final int FASTEST_INTERVAL_MS = 5 * 1000;

	protected void connectGoogleApi() {
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
					@Override
					public void onConnected(@Nullable Bundle bundle) {

						if (ActivityCompat.checkSelfPermission(AddUserPlantActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
							Toast.makeText(AddUserPlantActivity.this, "Geen toegang tot locatie", Toast.LENGTH_LONG).show();

							return;
						}

						LocationRequest locationRequest = LocationRequest.create()
								.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
								.setInterval(UPDATE_INTERVAL_MS)
								.setFastestInterval(FASTEST_INTERVAL_MS);

						LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, AddUserPlantActivity.this);

					}

					@Override
					public void onConnectionSuspended(int i) {
						System.out.println("> Connection suspended");
					}
				})
				.addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
					@Override
					public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
						System.out.println("> Connection failed");
					}
				})
				.addApi(LocationServices.API)
				.addApi(Wearable.API)
				.build();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_user_plant);

		connectGoogleApi();

		listView = (WearableListView)findViewById(R.id.wearable_list);
		plants = Plant.getAllPlants();
		final PlantsListAdapter plantsListAdapter = new PlantsListAdapter(this, plants);
		listView.setAdapter(plantsListAdapter);

		final FrameLayout frameLayout1 = (FrameLayout)findViewById(R.id.frame_layout_1);
		final FrameLayout frameLayout2 = (FrameLayout)findViewById(R.id.frame_layout_2);
		frameLayout1.setVisibility(View.VISIBLE);

		listView.setClickListener(new WearableListView.ClickListener() {
			@Override
			public void onClick(WearableListView.ViewHolder viewHolder) {
				listView.scrollToPosition(viewHolder.getPosition());
				plant = plants.get(viewHolder.getPosition());

				if(plant != null) {

					frameLayout1.setVisibility(View.GONE);
					frameLayout2.setVisibility(View.VISIBLE);

					TextView plantName = (TextView)findViewById(R.id.plant_name);
					plantName.setText(plant.name);

				}

			}

			@Override
			public void onTopEmptyRegionClick() {

			}
		});

		CircledImageView acceptButton = (CircledImageView)findViewById(R.id.button_accept);
		CircledImageView denyButton = (CircledImageView)findViewById(R.id.button_deny);

		denyButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				frameLayout2.setVisibility(View.GONE);
				frameLayout1.setVisibility(View.VISIBLE);

			}
		});

		acceptButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				if (ActivityCompat.checkSelfPermission(AddUserPlantActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
					Toast.makeText(AddUserPlantActivity.this, "Geen toegang tot locatie", Toast.LENGTH_LONG).show();

					return;
				}
				Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

				double latitude = 0;
				double longitude = 0;

				if(lastLocation != null) {
					latitude = lastLocation.getLatitude();
					longitude = lastLocation.getLongitude();
				}

				UserPlant userPlant = new UserPlant();
				userPlant.name = plant.name;
				userPlant.lat = latitude;
				userPlant.lng = longitude;
				userPlant.plant = plant;
				userPlant.created = new DateTime();
				userPlant.save();

				finish();
			}
		});

	}

	protected void onStart() {
		mGoogleApiClient.connect();
		checkLocationPermission();
		super.onStart();
	}

	protected void onStop() {
		mGoogleApiClient.disconnect();
		super.onStop();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mGoogleApiClient.isConnected()) {
			LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
		}
		mGoogleApiClient.disconnect();
	}

	@Override
	public void onLocationChanged(Location location) {
		System.out.println("> onLocationChanged");
	}

	public void checkLocationPermission() {
		boolean accessCoarseLocationPermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

		if(!accessCoarseLocationPermissionGranted) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_CODE);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
		if (requestCode == PERMISSIONS_REQUEST_CODE) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

			}
		}
	}
}
