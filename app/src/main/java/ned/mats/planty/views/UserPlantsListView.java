package ned.mats.planty.views;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import ned.mats.planty.R;

/**
 * Created by Mats on 17-4-2017.
 */

public class UserPlantsListView extends FrameLayout implements WearableListView.OnCenterProximityListener {
	final ImageView img;
	final TextView name;
	final TextView created;

	public UserPlantsListView(Context context) {
		super(context);
		View.inflate(context, R.layout.userplantslistview_item, this);
		img = (ImageView) findViewById(R.id.img);
		name = (TextView) findViewById(R.id.name);
		created = (TextView) findViewById(R.id.created);
	}


	@Override
	public void onCenterPosition(boolean b) {
		//Animation example to be ran when the view becomes the centered one
		name.animate().scaleX(1f).scaleY(1f).alpha(1);
		created.animate().scaleX(1f).scaleY(1f).alpha(1);
	}

	@Override
	public void onNonCenterPosition(boolean b) {
		//Animation example to be ran when the view is not the centered one anymore
		name.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f);
		created.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f);
	}
}
