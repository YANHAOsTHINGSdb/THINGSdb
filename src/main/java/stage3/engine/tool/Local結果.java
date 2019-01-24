package stage3.engine.tool;

import java.util.List;
import java.util.Map;

import stage3.engine.parse.計算式;

public interface  Local結果 {


	List<Map> 生成新的local結果List(List<Map> 全体結果List, List<Map> 今回結果List, 計算式 o計算式);
	List<Map> 制作新結果(Map 全体結果Map, Map 今回結果Map, List<Map> local結果List);
	Map 取得差分(Map 全体結果Map, Map 今回結果Map);
	String 取得类型(Map 全体結果Map, Map 今回結果Map);
	List<Map> 生成新的local結果List(List<Map> 全体結果List, Map for_入力情報Map, 計算式 o計算式);
	List<Map> 削除関連数据(List<Map> 計算結果List, Map for_入力情報Map);
}
