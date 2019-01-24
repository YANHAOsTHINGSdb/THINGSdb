package stage3.things.multiConditionCalc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class 累計整合子時間到主時間 implements 整合子時間到主時間 {

	@Override
	public List<Map> 整合子時間値到主時間(List<Object> 子時間計算結果List, Map<String, String> 主時間Map) {
		// 如果能找到
		//     对于每一个子时间值、取得上一级时间单位的值
		
		Long all = 0L;
		
		for(Object 子時間計算結果 : 子時間計算結果List) {

			// 如果叫累計，那么这个结果应该是数值，否则无法相加。
			
			for(Map 子時間計算結果map : (List<Map>)子時間計算結果) {
				if(StringUtils.isNumeric((String)子時間計算結果map.get("値"))) {
					all += (Long)子時間計算結果map.get("値");
				}
			}
		}
		
		Map<String,String> resultMap = new HashMap();
		resultMap.put("値", all+"");
		
		if(主時間Map== null || 主時間Map.isEmpty()){
			
		}else {
			resultMap.putAll(主時間Map);
		}
		
		
		return Arrays.asList(resultMap);
	}

}
