package stage3.engine.tool.time;

import java.util.List;
import java.util.Map;

public interface 下一層時間計算 {
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
	
	List<Map> 取得下一層時間(Map 主詞条Map, Map w詞条Map);

}
