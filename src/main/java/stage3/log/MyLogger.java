package stage3.log;

import org.apache.commons.lang3.StringUtils;

import stage3.consts.PublicName;

public class MyLogger {
	/**
	 * 自定义log出力工具
	 * 由于市面上的工具我都不太满意
	 * 主要是因为无法自动输出SQUENCE图
	 * 所以，log的意义不大。
	 * 所以就自己写了一个。
	 *             2019-01-14 追记
	 *
	 * @param sCallPath
	 * @param sMethodName
	 */
	public static void printCallMessage(String sCallPath, String sMethodName) {

		// 根据从PROPERY文件(sys.property)中取得的指定信息(outputLog)来判断是否输出LOG。
		if (PublicName.outputLog.equals("true")){
		}else {
			return;
		}

		if (StringUtils.isEmpty(sCallPath) || StringUtils.isEmpty(sMethodName)) {
//			System.out.println(sMethodName);
			return;
		}

		String sBlank = "";
		for (int i = 0; i < sCallPath.length() / 2; i++) {
			sBlank += " ";
		}

//		System.out.println(sBlank + sMethodName);
	}

}
