package ned.mats.planty.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.wearable.view.WearableListView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ned.mats.planty.R;
import ned.mats.planty.data.Plant;
import ned.mats.planty.views.PlantsListView;

/**
 * Created by Mats on 17-4-2017.
 */
public class PlantsListAdapter extends WearableListView.Adapter {

	private final Context context;
	private final List<Plant> items;

	public PlantsListAdapter(Context context, List<Plant> items) {
		this.context = context;
		this.items = items;
	}

	@Override
	public WearableListView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		return new WearableListView.ViewHolder(new PlantsListView(context));
	}

	@Override
	public void onBindViewHolder(WearableListView.ViewHolder viewHolder, final int position) {
		PlantsListView plantsItemView = (PlantsListView) viewHolder.itemView;
		final Plant item = items.get(position);

		TextView textView = (TextView) plantsItemView.findViewById(R.id.text);
		textView.setText(item.name);

		String color = String.format("FF%06X", (0xFFFFFF & item.name.hashCode()));

		ImageView imgView = (ImageView) plantsItemView.findViewById(R.id.img);
		imgView.setImageResource(R.drawable.ic_flower);
		imgView.setColorFilter(Color.parseColor("#" + color));
		imgView.setImageAlpha(100);
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

}
