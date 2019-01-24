package stage3.engine.tool.time.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import stage3.engine.tool.time.下一層時間;
import stage3.engine.tool.time.下一層時間計算;
import stage3.things.id.詞条;

public class 下一層時間_季 extends 下一層時間 implements 下一層時間計算 {
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
		List<Map> 下一層時間List = new ArrayList();
		Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
		
		詞条 o詞条 = new 詞条("");
		// 通过词条信息取得 实际值 例、2018
		String 実体数据 = o詞条.取得実体数据_by詞条IDand数据採番ID(
				(String)主詞条Map.get("詞条ID"), 
				(String)主詞条Map.get("数据ID"));
		// 通过Java对象
		aCalendar.set(Calendar.YEAR, Integer.parseInt(実体数据));
		
		
		// 然后分别遍历取得具体值。分别存入
		for(int i=1;i<=4; i++) {
			String sQuarter = 実体数据 + i;
			Map 子时间Map  = new HashMap();
			子时间Map.put("时间单位", w詞条Map.get(""));
			子时间Map.put("时间值", String.format("%tm", sQuarter));
			下一層時間List.add(子时间Map);
		}
		return 下一層時間List;
	}
    /**
     * 返回指定年季的季的第一天
     *
     * @param year
     * @param quarter
     * @return
     */
    public static Date getFirstDayOfQuarter(Integer year, Integer quarter) {
        Calendar calendar = Calendar.getInstance();
        Integer month = new Integer(0);
        if (quarter == 1) {
            month = 1 - 1;
        } else if (quarter == 2) {
            month = 4 - 1;
        } else if (quarter == 3) {
            month = 7 - 1;
        } else if (quarter == 4) {
            month = 10 - 1;
        } else {
            month = calendar.get(Calendar.MONTH);
        }
        return getFirstDayOfMonth(year, month);
    }
    
    /**
     * 返回指定年月的月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getFirstDayOfMonth(Integer year, Integer month) {
        Calendar calendar = Calendar.getInstance();
        if (year == null) {
            year = calendar.get(Calendar.YEAR);
        }
        if (month == null) {
            month = calendar.get(Calendar.MONTH);
        }
        calendar.set(year, month, 1);
        return calendar.getTime();
    }    
}
