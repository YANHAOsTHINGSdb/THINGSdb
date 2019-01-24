package stage3.engine.calc.implement;

import java.util.List;

import stage3.engine.calc.計算器;



public class 小于等于計算器 extends 小于計算器 implements 計算器 {


	@Override
	public String 個別計算_根据計算項目List(List<String> paramList) {

		if(paramList.size() < 2){
			return null;
		}

		計算器 o等于計算器 = new 等于計算器();
		if (o等于計算器.個別計算_根据計算項目List(paramList).equals("TRUE") || super.個別計算_根据計算項目List(paramList).equals("TRUE")){
			return "TRUE";
		}
		return "FASLE";
	}

}
