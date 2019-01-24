package stage3.engine.calc.implement;

import java.util.List;

public class 計算器基本 {

	/**
	 * 数据类型判断
	 */
	protected boolean 数据类型判断(String sNumber) {

		try{
			Integer.parseInt(sNumber);
			return true;

		}catch(NumberFormatException e){
			return false;
		}
	}

	protected boolean 数据NULL判断(List<String> paramList) {

		if(null == paramList){
			return false;
		}

		for(String sResult : paramList){

			if(null == sResult){
				return false;
			}

		}

		return true;

	}

}
