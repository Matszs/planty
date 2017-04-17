package ned.mats.planty.serializers;

import com.activeandroid.serializer.TypeSerializer;

import org.joda.time.DateTime;

/**
 * Created by Mats on 17-4-2017.
 */
public class DateTimeSerializer extends TypeSerializer {
	public Class<?> getDeserializedType() {
		return DateTime.class;
	}

	public Class<?> getSerializedType() {
		return long.class;
	}

	public Long serialize(Object data) {
		if (data == null) {
			return null;
		}
		return ((DateTime) data).getMillis();
	}

	public DateTime deserialize(Object data) {
		if (data == null) {
			return null;
		}
		return new DateTime(data);
	}
}
