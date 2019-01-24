package stage3.nosql;

import java.util.Map;

import stage3.engine.bean.執行器結果DTO;

public class NOSQL朋友圈 {

//	String 入力字典ID;
//	String 出力字典ID;
//
//	public NOSQL朋友圈(String 入力字典id, String 出力字典id) {
//		this.入力字典ID = 入力字典id;
//		this.出力字典ID = 出力字典id;
//	}

	public 執行器結果DTO 取得実体数据_根据字典Aand字典Band入力数据(String 入力字典ID, String 出力字典ID, Map 入力情報Map){

		// o条件List : 入力1 = 小伝馬町;入力2 = 浦安;
		//
		// 結果List =
		// Map1<>=小伝馬町 ,,,1,,,浦安 (出力字典ID, v1)(入力字典ID, v2)(入力字典IDvalue1, x1)(出力字典IDvalue1, value1.1)(出力字典IDvalue2, value1.2)
		// Map2<>=小伝馬町 ,,,2,,,浦安 (出力字典ID, v1)(入力字典ID, v2)(入力字典IDvalue1, x1)(出力字典IDvalue1, value2.1)(出力字典IDvalue2, value2.2)
		// Map3<>=小伝馬町 ,,,3,,,浦安 (出力字典ID, v1)(入力字典ID, v2)(入力字典IDvalue1, x1)(出力字典IDvalue1, value3.1)(出力字典IDvalue2, value3.2)

		/**
		 ■取得实体数据_根据字典Aand字典Band入力数据
		 1 ■NOSQL朋友.取得朋友关系记录文件名_根据字典Aand字典B
		 2 ■NOSQL朋友关系.取得实体数据_根据朋友关系记录文件名and入力数据
		*/

//		入力字典ID = "";
//		出力字典ID = "";

		NOSQL朋友关系 oNOSQL朋友关系 = new NOSQL朋友关系(入力字典ID, 出力字典ID);

		return oNOSQL朋友关系.取得実体数据_根据入力数据(入力情報Map);

	}



}
