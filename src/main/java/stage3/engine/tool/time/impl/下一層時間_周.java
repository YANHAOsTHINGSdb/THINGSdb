package stage3.engine.tool.time.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import stage3.engine.tool.time.下一層時間;
import stage3.engine.tool.time.下一層時間計算;
import stage3.things.id.詞条;

public class 下一層時間_周 extends 下一層時間 implements 下一層時間計算 {
	// 主詞条Map
	//		詞条ID = 年
	//		数据ID = 2018
	
	// w詞条Map
	//		詞条ID = 月
	
	// 运用Java计算得出每一年有哪几个月
	// 基本思路是，取得当年下的最大月，当日下的最大值日，以此类推。
	// 如果是月，最少需要年
	// 如果是日，最少需要月
	// 如果是周，最少需要年或月
	// 如果是季, 最少需要年
	@Override
	public List<Map> 取得下一層時間(Map 主詞条Map, Map w詞条Map) {

		switch((String)主詞条Map.get("詞条名")) {
		
		case "年":
			return 取得下一層時間by年(主詞条Map, w詞条Map);
		case "月":
			return 取得下一層時間by月(主詞条Map, w詞条Map);
		default:
			return null;
		}
	}
	
	
	List<Map> 取得下一層時間by年(Map 主詞条Map, Map w詞条Map) {
		List<Map> 下一層時間List = new ArrayList();
		Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
		
		詞条 o詞条 = new 詞条("");
		// 通过词条信息取得 实际值 例、20180101
		String 実体数据 = o詞条.取得実体数据_by詞条IDand数据採番ID(
				(String)主詞条Map.get("詞条ID"), 
				(String)主詞条Map.get("数据ID"));
		// 通过Java对象
		aCalendar.set(Calendar.YEAR, Integer.parseInt(実体数据.substring(0, 3)));

		
		// 取得其下一级时间的最大值
		int maxWEEK_OF_YEAR = aCalendar.getActualMaximum(Calendar.WEEK_OF_YEAR);
		
		// 然后分别遍历取得具体值。分别存入
		for(int i=1;i<=maxWEEK_OF_YEAR; i++) {
			String sWEEK_OF_YEAR= 実体数据 + i;
			Map 子时间Map  = new HashMap();
			子时间Map.put("时间单位", w詞条Map.get(""));
			子时间Map.put("时间值", String.format("%tm", sWEEK_OF_YEAR));
			下一層時間List.add(子时间Map);
		}
		return 下一層時間List;
		
	}
	
	List<Map> 取得下一層時間by月(Map 主詞条Map, Map w詞条Map) {
		List<Map> 下一層時間List = new ArrayList();
		Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
		
		詞条 o詞条 = new 詞条("");
		// 通过词条信息取得 实际值 例、20180101
		String 実体数据 = o詞条.取得実体数据_by詞条IDand数据採番ID(
				(String)主詞条Map.get("詞条ID"), 
				(String)主詞条Map.get("数据ID"));
		// 通过Java对象
		aCalendar.set(Calendar.YEAR, Integer.parseInt(実体数据.substring(0, 3)));
		aCalendar.set(Calendar.MONTH, Integer.parseInt(実体数据.substring(4, 5)));
		
		// 取得其下一级时间的最大值
		int maxWEEK_OF_MONTH = aCalendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
		
		// 然后分别遍历取得具体值。分别存入
		for(int i=1;i<=maxWEEK_OF_MONTH; i++) {
			String sWEEK_OF_MONTH = 実体数据 + i;
			Map 子时间Map  = new HashMap();
			子时间Map.put("时间单位", w詞条Map.get(""));
			子时间Map.put("时间值", String.format("%tm", sWEEK_OF_MONTH));
			下一層時間List.add(子时间Map);
		}
		return 下一層時間List;
		
	}
	

}
