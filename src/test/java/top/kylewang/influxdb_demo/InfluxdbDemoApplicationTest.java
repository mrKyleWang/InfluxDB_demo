package top.kylewang.influxdb_demo;

import com.sun.management.OperatingSystemMXBean;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.junit.Test;
import top.kylewang.influxdb_demo.util.CommonCalc;

import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author KyleWang
 * @version 1.0
 * @date 2019年05月19日
 */
public class InfluxdbDemoApplicationTest {

	private InfluxDB influxDB;
	private String dbName = "test";
	private String rpName = "1_days";
	{
		influxDB = InfluxDBFactory.connect("http://47.100.173.30:8086", "root", "root");
	}

	@Test
	public void initTest() throws InterruptedException {
		influxDB.query(new Query("CREATE DATABASE " + dbName));
		influxDB.setDatabase(dbName);
		influxDB.query(new Query(
				"CREATE RETENTION POLICY \"" + rpName + "\" ON \"" + dbName + "\" DURATION 1d REPLICATION 1 DEFAULT"));
		influxDB.close();
	}

	@Test
	public void writeTest() throws InterruptedException {
		influxDB.setDatabase(dbName);
		influxDB.setRetentionPolicy(rpName);
		while (true) {
			report();
			report2();
			// Thread.sleep(1);
		}
	}

	private void report() {
		OperatingSystemMXBean bean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		double systemRatio = CommonCalc.doubleFormat(bean.getSystemCpuLoad() * 100);
		double processRatio = CommonCalc.doubleFormat(bean.getProcessCpuLoad() * 100);
		influxDB.write(Point.measurement("cpu").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
				.tag("host", "127.0.0.1").tag("port", "8891").addField("system", systemRatio)
				.addField("process", processRatio).addField("host", "127.0.0.1").addField("port", 8890).build());
	}

	private void report2() {
		OperatingSystemMXBean bean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		double systemRatio = CommonCalc.doubleFormat(bean.getSystemCpuLoad() * 100);
		double processRatio = CommonCalc.doubleFormat(bean.getProcessCpuLoad() * 100);
		influxDB.write(Point.measurement("cpu").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
				.tag("host", "127.0.0.2").tag("port", "8892").addField("system", systemRatio)
				.addField("process", processRatio).addField("host", "127.0.0.2").addField("port", 8890).build());
	}
}
