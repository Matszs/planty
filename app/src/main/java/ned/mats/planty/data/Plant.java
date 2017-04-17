package ned.mats.planty.data;

import android.graphics.drawable.Drawable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mats on 16-4-2017.
 */

/*
	aardbeienplant
	appelboom
	druiven
	aardappelen
	Radijs
 */
@Table(name = "Plant")
public class Plant extends Model {
	@Column(name = "name")
	public String name;
	@Column(name = "img")
	public Drawable img;

	public List<PlantTip> tips() {
		return getMany(PlantTip.class, "Plant");
	}

	public static int totalCount() {
		return new Select().from(Plant.class).count();
	}
	public static List<Plant> getAllPlants() {
		return new Select().from(Plant.class).orderBy("name ASC").execute();
	}
}
