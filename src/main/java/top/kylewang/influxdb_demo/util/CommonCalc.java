package top.kylewang.influxdb_demo.util;

/**
 * @author KyleWang
 * @version 1.0
 * @date 2019年05月19日
 */
public class CommonCalc {

	/** 取2位小数 */
	private static final int FORMAT_DIGIT = 100;

	/**
	 * 计算百分比
	 * @param source1
	 * @param source2
	 * @return
	 */
	public static double divideRatioFormat(long source1, long source2) {
		return doubleFormat((double) source1 / source2 * 100);
	}

	/**
	 * 结果位数转换
	 * @param source
	 * @return
	 */
	public static double doubleFormat(double source) {
		return (double) Math.round(source * FORMAT_DIGIT) / FORMAT_DIGIT;
	}

	/**
	 * bytes转换为M
	 * @param num
	 * @return
	 */
	public static long bytesToM(long num) {
		return num / 1024 / 1024;
	}

}
