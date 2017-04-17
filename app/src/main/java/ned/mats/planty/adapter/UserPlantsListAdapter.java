package ned.mats.planty.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.wearable.view.WearableListView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

import ned.mats.planty.R;
import ned.mats.planty.data.Plant;
import ned.mats.planty.data.UserPlant;
import ned.mats.planty.views.UserPlantsListView;

/**
 * Created by Mats on 17-4-2017.
 */

public class UserPlantsListAdapter extends WearableListView.Adapter {

	private final Context context;
	private final List<UserPlant> items;

	public UserPlantsListAdapter(Context context, List<UserPlant> items) {
		this.context = context;
		this.items = items;
	}

	@Override
	public WearableListView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		return new WearableListView.ViewHolder(new UserPlantsListView(context));
	}

	@Override
	public void onBindViewHolder(WearableListView.ViewHolder viewHolder, final int position) {
		UserPlantsListView plantsItemView = (UserPlantsListView) viewHolder.itemView;
		final UserPlant item = items.get(position);

		TextView nameView = (TextView) plantsItemView.findViewById(R.id.name);
		nameView.setText(item.name);

		DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm");

		TextView createdView = (TextView) plantsItemView.findViewById(R.id.created);
		createdView.setText(dtfOut.print(item.created));
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

}
