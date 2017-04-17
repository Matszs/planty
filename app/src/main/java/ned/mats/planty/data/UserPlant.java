package ned.mats.planty.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

/**
 * Created by Mats on 16-4-2017.
 */
@Table(name = "UserPlant")
public class UserPlant extends Model {
	@Column(name = "name")
	public String name;

	@Column(name = "lat")
	public double lat;
	@Column(name = "lng")
	public double lng;

	@Column(name = "created")
	public DateTime created;

	@Column(name = "Plant")
	public Plant plant;

	public static int totalCount() {
		return new Select().from(UserPlant.class).count();
	}

	public static List<UserPlant> getAllPlants() {
		return new Select().from(UserPlant.class).orderBy("created DESC").execute();
	}

	public static UserPlant getPlant(long id) {
		return new Select().from(UserPlant.class).where("id = ?", id).executeSingle();
	}
}
