package top.kylewang.influxdb_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
/**
 * @author KyleWang
 * @version 1.0
 * @date 2019年05月17日
 */
@SpringBootApplication
@EnableScheduling
public class InfluxdbDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfluxdbDemoApplication.class, args);
	}

}
