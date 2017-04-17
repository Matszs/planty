package ned.mats.planty;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by Mats on 16-4-2017.
 */
public class PlantyApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		ActiveAndroid.initialize(this);
		JodaTimeAndroid.init(this);
		System.out.println("START");
	}
}
