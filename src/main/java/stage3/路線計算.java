package stage3;


import java.util.HashMap;
import java.util.Map;

import stage3.nosql.NOSQL;

public class 路線計算 {
	/**
	 *
	 */
	public static void main(String args[]){

		//List<Map> 駅=new ArrayList<Map>();
		Map 駅 = new HashMap();
		駅.put("入力1", "小伝馬町");
		駅.put("入力2", "浦安");


		NOSQL nosql = new NOSQL("main()");
		nosql.取得指定値_根据入力字典and出力字典and入力数据("駅","路線",駅);

		System.out.println("計算終了。");
	}
}
