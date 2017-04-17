package ned.mats.planty.data;

import android.graphics.drawable.Drawable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Mats on 16-4-2017.
 */
@Table(name = "PlantTip")
public class PlantTip extends Model {
	@Column(name = "img")
	public Drawable img;

	@Column(name = "tip")
	public String tip;

	@Column(name = "Plant")
	public Plant plant;
}
