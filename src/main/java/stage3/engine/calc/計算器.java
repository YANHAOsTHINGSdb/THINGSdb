package stage3.engine.calc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stage3.engine.parse.計算式;

public interface 計算器 {

	static Map map条件数据 = new HashMap();

	static Map map業務数据 = new HashMap();

	/**
	 *
	 */
	public Object 個別計算_根据計算項目List(List<String> paramList);

	/**
	 *
	 * @param map
	 * 例：
	 * 	   Map<主, value><次1, value><次2, value><次3, value>
	 *
	 * @return
	 */
	public Object 個別計算_根据計算項目List(Map 入力情報Map);

	public Object 個別計算_根据計算項目List(Map 個別計算Map, Map f臨時計算結果Map, 計算式 o計算式, Map<String, 計算式> 計算式Map);
}
