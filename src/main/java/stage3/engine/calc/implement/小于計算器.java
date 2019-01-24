package stage3.engine.calc.implement;

import java.util.List;
import java.util.Map;

import stage3.engine.calc.計算器;
import stage3.engine.parse.計算式;

public class 小于計算器 extends 計算器基本 implements 計算器 {

	@Override
	public String 個別計算_根据計算項目List(List<String> paramList) {

		if (!this.数据NULL判断(paramList)) {
			return null;
		}

		if (paramList.size() < 2) {
			return null;
		}
		if (!this.数据类型判断(paramList.get(0))) {
			return null;
		}
		if (!this.数据类型判断(paramList.get(1))) {
			return null;
		}
		return Double.parseDouble(paramList.get(0)) < Double.parseDouble(paramList.get(1)) ? "TRUE" : "FALSE" + "";
	}

	@Override
	public String 個別計算_根据計算項目List(Map map) {

		if (null == map) {
			return null;
		}

		if (map.isEmpty()) {
			return null;
		}

		if (map.size() < 2) {
			return null;
		}

		if (!this.数据类型判断((String) map.get(0))) {
			return null;
		}
		if (!this.数据类型判断((String) map.get(1))) {
			return null;
		}
		return Double.parseDouble((String) map.get(0)) < Double.parseDouble((String) map.get(1)) ? "TRUE"
				: "FALSE" + "";
	}

	@Override
	public String 個別計算_根据計算項目List(Map 個別計算Map, Map f臨時計算結果Map, 計算式 o計算式, Map<String, 計算式> 計算式Map) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}
