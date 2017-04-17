package ned.mats.planty.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.support.wearable.view.WearableListView;
import android.support.wearable.view.WearableRecyclerView;
import android.support.wearable.view.drawer.WearableActionDrawer;
import android.support.wearable.view.drawer.WearableDrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ned.mats.planty.R;
import ned.mats.planty.adapter.UserPlantsListAdapter;
import ned.mats.planty.data.Plant;
import ned.mats.planty.data.PlantTip;
import ned.mats.planty.data.UserPlant;

public class MainActivity extends WearableActivity {

	private static final SimpleDateFormat AMBIENT_DATE_FORMAT = new SimpleDateFormat("HH:mm", Locale.US);
	private WearableRecyclerView wearableRecyclerView;
	private List<UserPlant> userPlantList = null;
	private WearableListView plantsList;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		WearableDrawerLayout wearableDrawerLayout = (WearableDrawerLayout) findViewById(R.id.drawer_layout);
		WearableActionDrawer wearableActionDrawer = (WearableActionDrawer) findViewById(R.id.bottom_action_drawer);

		wearableActionDrawer.setOnMenuItemClickListener(
				new WearableActionDrawer.OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem menuItem) {

						Intent intent = new Intent(MainActivity.this, ned.mats.planty.activity.AddUserPlantActivity.class);
						startActivity(intent);

						return false;
					}
				});

		wearableDrawerLayout.peekDrawer(Gravity.BOTTOM);
		plantsList = (WearableListView)findViewById(R.id.user_plants_list);

		final TextView header = (TextView)findViewById(R.id.main_header);

		plantsList.addOnScrollListener(new WearableListView.OnScrollListener() {
			@Override
			public void onScroll(int scroll) {
				header.setY(header.getY() - scroll);
			}

			@Override
			public void onAbsoluteScrollChange(int i) {

			}

			@Override
			public void onScrollStateChanged(int i) {

			}

			@Override
			public void onCentralPositionChanged(int i) {

			}
		});

		plantsList.setClickListener(new WearableListView.ClickListener() {
			@Override
			public void onClick(WearableListView.ViewHolder viewHolder) {
				UserPlant userPlant = userPlantList.get(viewHolder.getPosition());
				if(userPlant == null)
					return;

				Intent intent = new Intent(MainActivity.this, ned.mats.planty.activity.ViewUserPlantActivity.class);
				intent.putExtra("user_plant_id", userPlant.getId());
				startActivity(intent);
			}

			@Override
			public void onTopEmptyRegionClick() {

			}
		});

		insertPlants();
	}

	@Override
	protected void onResume() {
		TextView noPlantsFoundText = (TextView)findViewById(R.id.plants_found_text);

		if(UserPlant.totalCount() > 0) {
			noPlantsFoundText.setVisibility(View.GONE);

			userPlantList = UserPlant.getAllPlants();
			UserPlantsListAdapter userPlantsListAdapter = new UserPlantsListAdapter(MainActivity.this, userPlantList);

			plantsList.setAdapter(userPlantsListAdapter);
		} else {
			noPlantsFoundText.setVisibility(View.VISIBLE);
		}

		super.onResume();
	}

	private void insertPlants() {
		if(Plant.totalCount() == 0) {

			Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_flower);

			// AARDBEIEN PLANT
			Plant plant = new Plant();
			plant.name = "Aardbeienplant";
			plant.img = drawable;
			plant.save();

			PlantTip plantTip = new PlantTip();
			plantTip.img = drawable;
			plantTip.tip = "Deze plant heeft veel zon nodig, zorg dus voor een zonnige plek.";
			plantTip.plant = plant;
			plantTip.save();

			plantTip = new PlantTip();
			plantTip.img = drawable;
			plantTip.tip = "Plaats niet meer dan 6 planten per m².";
			plantTip.plant = plant;
			plantTip.save();

			plantTip = new PlantTip();
			plantTip.img = drawable;
			plantTip.tip = "De plant kan worden geplaatst in een grote pot of in de tuin.";
			plantTip.plant = plant;
			plantTip.save();

			plantTip = new PlantTip();
			plantTip.img = drawable;
			plantTip.tip = "Zorg dat de plant voldoende water heeft.";
			plantTip.plant = plant;
			plantTip.save();


			// Appelboom
			plant = new Plant();
			plant.name = "Appelboom";
			plant.img = drawable;
			plant.save();

			plantTip = new PlantTip();
			plantTip.img = drawable;
			plantTip.tip = "Appelbomen kan je het beste planten van november tot en met april.";
			plantTip.plant = plant;
			plantTip.save();

			plantTip = new PlantTip();
			plantTip.img = drawable;
			plantTip.tip = "Geef een appelboom niet dagelijks water maar doe dit eenmalig wekelijks een grote hoeveelheid.";
			plantTip.plant = plant;
			plantTip.save();

			plantTip = new PlantTip();
			plantTip.img = drawable;
			plantTip.tip = "Bemest de boom jaarlijks, doe dit dan wel in februari.";
			plantTip.plant = plant;
			plantTip.save();

			plantTip = new PlantTip();
			plantTip.img = drawable;
			plantTip.tip = "Plant niet teveel bomen op elkaar, zorg voor minimaal 7 meter tussen twee bomen.";
			plantTip.plant = plant;
			plantTip.save();



			// DRUIVEN
			plant = new Plant();
			plant.name = "Druivenplant";
			plant.img = drawable;
			plant.save();

			plantTip = new PlantTip();
			plantTip.img = drawable;
			plantTip.tip = "Een druivenplant moet de vrucht laten rijpen, daarom is het belangrijk deze in de zon neer te zetten.";
			plantTip.plant = plant;
			plantTip.save();

			plantTip = new PlantTip();
			plantTip.img = drawable;
			plantTip.tip = "Vogels houden van druiven, om te voorkomen dat ze worden opgegeten kan je een net spannen.";
			plantTip.plant = plant;
			plantTip.save();

			plantTip = new PlantTip();
			plantTip.img = drawable;
			plantTip.tip = "Oogst nooit druiven tussen januari en april, dan is de boom vol in bloei en kan deze beschadigd raken.";
			plantTip.plant = plant;
			plantTip.save();


			// AARDAPPELEN
			plant = new Plant();
			plant.name = "Aardappelplant";
			plant.img = drawable;
			plant.save();

			plantTip = new PlantTip();
			plantTip.img = drawable;
			plantTip.tip = "Het beste kan je een aardappel na het plukken 2 tot 3 dagen laten rusten voordat je ze gaat eten.";
			plantTip.plant = plant;
			plantTip.save();

			plantTip = new PlantTip();
			plantTip.img = drawable;
			plantTip.tip = "Een volledige aardappelplant laten bloeien duurt gemiddeld 100 - 120 dagen.";
			plantTip.plant = plant;
			plantTip.save();

			plantTip = new PlantTip();
			plantTip.img = drawable;
			plantTip.tip = "Als je aardappelen bewaard, doe dit dan op een donker en droge plaats.";
			plantTip.plant = plant;
			plantTip.save();


			// RADIJS
			plant = new Plant();
			plant.name = "Radijsplant";
			plant.img = drawable;
			plant.save();

			plantTip = new PlantTip();
			plantTip.img = drawable;
			plantTip.tip = "Radijs kan worden gezaaid in januari-maart/april of in juli-augustus.";
			plantTip.plant = plant;
			plantTip.save();

			plantTip = new PlantTip();
			plantTip.img = drawable;
			plantTip.tip = "Radijs stelt niet veel eisen aan de grond, bemesten kan wel wat helpen.";
			plantTip.plant = plant;
			plantTip.save();

			plantTip = new PlantTip();
			plantTip.img = drawable;
			plantTip.tip = "Lang houdbaar is de radijs niet, hooguit een paar dagen als je ze in de koelkast bewaart.";
			plantTip.plant = plant;
			plantTip.save();












		}
	}

}
