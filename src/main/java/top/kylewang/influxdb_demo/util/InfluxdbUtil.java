package top.kylewang.influxdb_demo.util;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author KyleWang
 * @version 1.0
 * @date 2019年05月19日
 */
public class InfluxdbUtil {

	private static final Logger logger = LoggerFactory.getLogger(InfluxdbUtil.class);

	private InfluxDB influxDB;

	public void setInfluxDB(InfluxDB influxDB) {
		this.influxDB = influxDB;
	}

	public void createDataBase(String dbName) {
		influxDB.query(new Query("CREATE DATABASE " + dbName));
	}

	public void createRetentionPolicy(String dbName, String rpName, String retentionPolicy) {
		influxDB.setDatabase(dbName);
		influxDB.query(new Query("CREATE RETENTION POLICY \"" + rpName + "\" ON \"" + dbName + "\" DURATION "
				+ retentionPolicy + " REPLICATION 1 DEFAULT"));
	}

	public void write(String dbName, String rpName, String measurement, Map<String, String> tags,
			Map<String, Object> fields) {
		influxDB.setDatabase(dbName);
		influxDB.setRetentionPolicy(rpName);
		Point.Builder builder = Point.measurement(measurement).time(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
		for (Map.Entry<String, String> tagEntry : tags.entrySet()) {
			builder.tag(tagEntry.getKey(), tagEntry.getValue());
		}
		for (Map.Entry<String, Object> fieldEntry : fields.entrySet()) {
			Object value = fieldEntry.getValue();
			if (value instanceof Number) {
				if (value instanceof Byte) {
					builder.addField(fieldEntry.getKey(), ((Byte) value));
				} else if (value instanceof Short) {
					builder.addField(fieldEntry.getKey(), ((Short) value));
				} else if (value instanceof Integer) {
					builder.addField(fieldEntry.getKey(), ((Integer) value));
				} else if (value instanceof Long) {
					builder.addField(fieldEntry.getKey(), ((Long) value));
				} else if (value instanceof BigInteger) {
					builder.addField(fieldEntry.getKey(), ((BigInteger) value));
				}
			} else if (value instanceof String) {
				builder.addField(fieldEntry.getKey(), ((String) value));
			} else {
				logger.error("unsupport field type!");
				return;
			}
		}
		influxDB.write(builder.build());
	}

}
