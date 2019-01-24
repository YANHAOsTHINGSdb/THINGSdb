package stage3.engine.calc.implement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stage3.engine.calc.計算器;
import stage3.engine.parse.計算式;

public class List追加計算器 extends 計算器基本 implements 計算器 {

	@Override
	public String 個別計算_根据計算項目List(List<String> paramList) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public String 個別計算_根据計算項目List(Map map) {
		//統計其中有相同value的key的个数
		//如果两个以上	"TRUE"
		//否則			"FALSE"
		Map<String, String> 個別計算map = new HashMap(map);

		String result = null;
		;

		for (Map.Entry<String, String> entry : 個別計算map.entrySet()) {
			if (entry.getValue() == null) {
				continue;
			}
			if (result != null) {
				result = result.concat(",".concat(entry.getValue()));
			} else {
				result = entry.getValue();
			}
		}

		return result;
	}

	@Override
	public String 個別計算_根据計算項目List(Map 個別計算Map, Map f臨時計算結果Map, 計算式 o計算式, Map<String, 計算式> 計算式Map) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}



}
