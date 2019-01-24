package stage3.engine;

import java.util.Map;

import stage3.engine.bean.執行器結果DTO;
import stage3.engine.parse.計算式;
import stage3.nosql.NOSQL;

public interface 程序数据執行器 {

	//List<Map> 結果List = new ArrayList();

	計算式 o計算式 = new 計算式();

	NOSQL nosql = new NOSQL("程序数据執行器");

	public 執行器結果DTO run_根据計算辞書IDand入力情報(String s計算数据_辞書ID, Map 入力情報Map);
}
