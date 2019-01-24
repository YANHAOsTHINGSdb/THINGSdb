package stage3.nosql;

import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import stage3.REL.program.詞条CRUD;
import stage3.log.MyLogger;
import stage3.things.id.詞条;

public class 辞書関係識別器 {
	String sCallPath = null;
	MyLogger myLogger = new MyLogger();

	public 辞書関係識別器(String sCallPath) {
		this.sCallPath = sCallPath;
	}

	public String 取得两辞書関係_根据辞書Aand辞書B(String 詞条A, String 詞条B) {

		myLogger.printCallMessage(sCallPath,"辞書関係識別器.取得两辞書関係_根据辞書Aand辞書B(詞条A="+詞条A+",詞条B="+詞条B+")");

		//		 ■取得字典关系_根据字典Aand字典B
		//		 1 ■取得两字典家族关系_根据字典Aand字典B
		//		    1.1 在字典A的定义中找字典B
		//		    1.2 在字典B的定义中找字典A
		//		    1.3 return "家族>"  or "家族<"
		//		 2 ■取得两字典的朋友关系_根据字典Aand字典B
		//		    2.1 在字典A的朋友中找字典B
		//		    2.2 在字典B的朋友中找字典A
		//		    2.3 return "朋友"
		//		 3 以上未找到两字典的关系
		//		    3.1 return "陌生人"

/*		switch (辞書B) {

		case "基站":
			return "家族";
		case "路線":
			return "朋友";
		}
		return "朋友"*/;


		//-------------20181021---------------------------
		// 取得詞条A的客户詞条List
		// 取得詞条B的客户詞条List
		// 如果詞条A的客户詞条List有詞条B
		// 或者
		// 如果詞条B的客户詞条List有詞条A
		// 返回 "直接"
		// 否则 返回  "非直接"
		//------------------------------------------------

		詞条 o詞条 = new 詞条("取得两辞書関係_根据辞書Aand辞書B");
		詞条CRUD 詞条crud = new 詞条CRUD("取得两辞書関係_根据辞書Aand辞書B");

		List 関係詞条A_ID_List = 詞条crud.取得関係詞条IDList(詞条A);
		if(関係詞条A_ID_List.contains(o詞条.取得詞条ID_by詞条名(詞条B))) {
			return "直接";
		}

		List 関係詞条B_ID_List = 詞条crud.取得関係詞条IDList(詞条B);
		if(関係詞条B_ID_List.contains(o詞条.取得詞条ID_by詞条名(詞条A))) {
			return "直接";
		}

		return "非直接";
	}


	public String 取得入力情報Type(Map<String, Object> map_convert) {

		myLogger.printCallMessage(sCallPath,"辞書関係識別器.取得入力情報Type()");

		/**
		switch (o辞書関係識別器.取得情報Type(map)) {
		case "CRUD":
		case "program":
		}
		*/

		//------------------------------------------------
		// 如何辨别入力情報是【CRUD】还是【program】
		// CRUD:
		//      {操作、目標、条件（也可能没有）}
		// program：
		//      {from、to、code}
		// 通过对map的key进行扫描
		// 如果是CRUD的情况：返回"CRUD"
		// 如果是program的情况：返回"program"
		//------------------------------------------------


		// 操作=10
		// 目標=20
		// 条件=30

		// from=1
		// to=2
		// code=3

		//List<String> keyList = new ArrayList();

		int iResult = 0;
		for (Map.Entry<String, Object> entry : map_convert.entrySet()) {
			switch(entry.getKey()) {
			case "操作":iResult = iResult + 10;
				break;
			case "目標":iResult = iResult + 20;
				break;
			case "条件":iResult = iResult + 30;
				break;
			case "from":iResult = iResult + 1;
				break;
			case "to":iResult = iResult + 2;
				break;
			case "code":iResult = iResult + 3;
				break;
			default:
				iResult = iResult + 0;
			}
		}

		if(iResult > 10){
			return "CRUD";
		}else {
			return "program";
		}

	}


	public String 取得CRUD処理Type(Map<String, Object> crudMap) {
		// crudMap=
		// {操作=追加, 目标=股票实时信息, 条件={开盘价=3.920, 成交量=44817843, 卖四股数=0, 卖四报价=0.000, 成交股票数=44817843, 最高价=4.380, 成交金额=192773239.000, 买五股数=36700, 买五报价=4.340, 卖一报价=0.000, 卖一股数=0, 买三报价=4.360, 买二报价=4.370, 买三股数=7100, 昨日收盘价=3.980, 买二股数=26700, 日期=2018-12-21, 买一报价=4.380, 收盘价=4.380, 股票名称=游久游戏, 买一股数=1738104, 最低价=3.890, 卖五报价=0.000, 买四股数=50000, 买四报价=4.350, 竞卖价=0.000, 竞买价=4.380, 时间=15:00:00, 成交额=192773239.000, 卖三股数=0, 卖五股数=0, 卖三报价=0.000, 卖二股数=0, 卖二报价=0.000}}
		myLogger.printCallMessage(sCallPath,"辞書関係識別器.取得CRUD処理Type()");
		/**
		switch (o辞書関係識別器.取得CRUD処理Type(crudMap)) {
		case "ADD":
		case "SEARCH":
		 */
		//------------------------------------------------
		// 针对 map的key="操作"进行判断
		// 如果是【検索】，返回 "SEARCH"
		// 如果是【追加】，返回 "ADD"
		//------------------------------------------------
		if(CollectionUtils.isEmpty(crudMap)) {
			return null;
		}
		switch ((String)crudMap.get("操作")) {

		case "検索":
			return "SEARCH";

		case "追加":
			return "ADD";

		}
		return null;


	}

	public String 取得両辞書関係(String s主目标詞条, String s条件詞条) {
		myLogger.printCallMessage(sCallPath,"辞書関係識別器.取得両辞書関係(s主目标詞条=+"+s主目标詞条+",s条件詞条="+s条件詞条+")");

		/**
		switch (o辞書関係識別器.取得両辞書関係(s主目标詞条, s条件詞条)) {
		case "直接关系":
		case "非直接关系":
		}
		 */
		/**==================================================
		 * 取得詞条A的关系詞条
		 * 			|----------【取得关系詞条_by詞条】
		 * 在关系词条中是否存在词条B
		 * 如果存在：return "直接关系";
		 * 如果不存在：return "非直接关系";
		 *
		 ==================================================*/

		詞条CRUD o詞条CRUD = new 詞条CRUD("処理program");
		List 関係詞条List = o詞条CRUD.取得関係詞条IDList(s主目标詞条);

		if(関係詞条List.contains(s条件詞条)) {
			return "非直接关系";
		}
		return "直接关系";
	}
}
