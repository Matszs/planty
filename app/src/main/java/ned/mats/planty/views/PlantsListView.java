package ned.mats.planty.views;

import android.content.Context;
import android.media.Image;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import ned.mats.planty.R;

/**
 * Created by Mats on 17-4-2017.
 */
public class PlantsListView extends FrameLayout implements WearableListView.OnCenterProximityListener {
	final ImageView img;
	final TextView text;

	public PlantsListView(Context context) {
		super(context);
		View.inflate(context, R.layout.plantslistview_item, this);
		img = (ImageView) findViewById(R.id.img);
		text = (TextView) findViewById(R.id.text);

	}


	@Override
	public void onCenterPosition(boolean b) {

		//Animation example to be ran when the view becomes the centered one
		img.animate().scaleX(1f).scaleY(1f).alpha(1);
		text.animate().scaleX(1f).scaleY(1f).alpha(1);

	}

	@Override
	public void onNonCenterPosition(boolean b) {

		//Animation example to be ran when the view is not the centered one anymore
		img.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f);
		text.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f);

	}
}
