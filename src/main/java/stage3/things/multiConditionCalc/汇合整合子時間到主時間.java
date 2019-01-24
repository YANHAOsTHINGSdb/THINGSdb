package stage3.things.multiConditionCalc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class 汇合整合子時間到主時間 implements 整合子時間到主時間 {

	@Override
	public List<Map> 整合子時間値到主時間(List<Object> 子時間計算結果List, Map<String, String> 主時間Map) {
		// 整合结果：List<Map>
		//				   [------key= 值，value={价格1，价格2，价格3，价格4，价格5......}
		//				   [------key= 时间单位，value=年
		//			   	   [------key= 时间值，value=2018
		//  
		Map<String,String> all = new HashMap();
		
		for(Object 子時間計算結果 : 子時間計算結果List) {

			// 如果叫累計，那么这个结果应该是数值，否则无法相加。
			
			for(Map 子時間計算結果map : (List<Map>)子時間計算結果) {
/*				if(StringUtils.isNumeric((String)子時間計算結果map.get("値"))) {
					all += (Long)子時間計算結果map.get("値");
				}*/
				all.putAll(子時間計算結果map);
			}
			
			
		}
		
		Map<String,Object> resultMap = new HashMap();
		resultMap.put("値", all);

		if(主時間Map== null || 主時間Map.isEmpty()){
			
		}else {
			resultMap.putAll(主時間Map);
		}		
		
		return Arrays.asList(resultMap);
		
	}

}
