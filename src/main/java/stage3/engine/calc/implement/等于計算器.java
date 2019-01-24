package stage3.engine.calc.implement;

import java.util.List;
import java.util.Map;

import stage3.engine.calc.計算器;
import stage3.engine.parse.計算式;



public class 等于計算器 extends 計算器基本 implements 計算器 {

	@Override
	public String 個別計算_根据計算項目List(List<String> paramList) {

		if(paramList.size() < 2){
			return "FALSE";
		}

		if(! this.数据NULL判断(paramList)){
			return "FALSE";
		}

		return  paramList.get(0).equals(paramList.get(1)) ? "TRUE":"FALSE" + "";
	}


	@Override
	public String 個別計算_根据計算項目List(Map map) {

		if(null ==  map){
			return "FALSE";
		}

		if(map.isEmpty()){
			return "FALSE";
		}

		if(map.size()<2){
			return "FALSE";
		}

		return  ((String)map.get(0)).equals((String)map.get(1)) ? "TRUE":"FALSE" + "";
	}

	@Override
	public Object 個別計算_根据計算項目List(Map 個別計算Map, Map f臨時計算結果Map, 計算式 o計算式, Map<String, 計算式> 計算式Map) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}
