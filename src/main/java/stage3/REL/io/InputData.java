package stage3.REL.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;
/*
{
"操作":"検索",
"目標":{"社員Bean":["番号","姓名","性別","生年月日","入社年月日","契約種類"]},
"条件":[
{"値":"2790001","FORMAT":"0","目標":"社員Bean","項目":"番号","ID":"0","計算符号":"等于"},
{"値":"AAA","FORMAT":"0","目標":"社員Bean","項目":"姓名","ID":"1","計算符号":"like"},
{"値":"女","FORMAT":"0","目標":"社員Bean","項目":"性別","ID":"2","計算符号":"like"},
{"値":"正社員","FORMAT":"0","目標":"社員Bean","項目":"契約種類","ID":"3","計算符号":"等于"}
],
"条件関係":"0 and 1 and 2 and 3 "}


{
"操作":"検索",
"目標":{"社員Bean":["番号","姓名","性別","生年月日","入社年月日","契約種類"]}
}

 */
import org.springframework.util.StringUtils;

import stage3.REL.Relation;
import stage3.log.MyLogger;
import stage3.nosql.辞書関係識別器;

public class InputData {
	String sCallPath = null;
	MyLogger myLogger = new MyLogger();

	public InputData(String sCallPath) {
		this.sCallPath = sCallPath;
	}
//	private List getRsultData(Map<String, Object> map) {
//		InputDataType inputDataType = new InputDataType();
//
//		if (inputDataType.getDataType(map).equals("program")) {
//			return call程序執行器(map);
//
//		} else if (inputDataType.getDataType(map).equals("CRUD")) {
//			if (chk直接关系(map)) {
//				return callTHINGSDB(map);
//			}
//			List 孤立詞条List = get孤立詞条(map);
//
//			if (CollectionUtils.isEmpty(孤立詞条List)) {
//				// 返回错误信息。
//				// error, 存在不明确的程序。
//			} else {
//				// 问题。是非直接CRUD怎么办
//				// 非直接只能由DB强推。如果中间有不明关系的，即返回错误信息。
//				// 如果找到程序，即，调取程序。
//				// 这样就把压力抛给了DB了。
//				return callTHINGSDB(map);
//			}
//
//		}
//
//		// 既不是【】又不是【】时，返回错误信息。
//		// error, 您输入的数据格式不正确。
//		return null;
//	}

	// 调用数据库来直接进行处理
//	private List callTHINGSDB(Map<String, Object> crudParam) {
//		// 操作:检索数据
//		// 操作:检索词条
//		// 主目标A------子目标A B C D
//		// 条件   ------条件A B C D
//		return null;
//	}

	// 调用程序执行器来直接进行处理
//	private List call程序執行器(Map<String, Object> programParam) {
//		// key = program
//		// value = list<Map>
//		//               key=, value=, 演算子=，关系式=
//		//               key=, value=, 演算子=，关系式=
//		//               key=, value=, 演算子=，关系式=
//		//               key=, value=, 演算子=，关系式=
//
//		return null;
//	}

	// 判断【主目标】与【子目标】与【条件词条】是不是全部都是客户或业者的关系
/*	private boolean chk直接关系(Map<String, Object> crudParam) {
		// 主目标
		//    |------子目标  直接关系
		//    |------子目标  直接关系
		//    |------子目标  直接关系
		//    |------条件    直接关系
		//    |------条件    直接关系
		//    |------条件    直接关系

		String s主目标詞条 = get主目標詞条(crudParam);

		List<String> 子目标詞条List = get子目標詞条List(crudParam);

		List<String> 条件詞条List = get条件詞条List(crudParam);

		if (is直接关系_by主目标_子目标(s主目标詞条, 子目标詞条List) &&
				is直接关系_by主目标_条件(s主目标詞条, 条件詞条List)) {
			return true;
		}

		return false;

	}*/

	private List<String> get条件詞条List(Map<String, Object> crudParam) {

		myLogger.printCallMessage(sCallPath,"InputData.get条件詞条List()");

		List<String> 条件詞条List = null;
		/*
		{
			"操作":"検索",
			"目標":{"社員Bean":["番号","姓名","性別","生年月日","入社年月日","契約種類"]},
			"条件":[
			{"値":"2790001","FORMAT":"0","目標":"社員Bean","項目":"番号","ID":"0","計算符号":"等于"},
			{"値":"AAA","FORMAT":"0","目標":"社員Bean","項目":"姓名","ID":"1","計算符号":"like"},
			{"値":"女","FORMAT":"0","目標":"社員Bean","項目":"性別","ID":"2","計算符号":"like"},
			{"値":"正社員","FORMAT":"0","目標":"社員Bean","項目":"契約種類","ID":"3","計算符号":"等于"}
			],
			"条件関係":"0 and 1 and 2 and 3 "}
		*/
		Map 条件map = null;
		List<Map> 条件List = null;

		if(crudParam.get("条件") instanceof Map) {
			条件map = (Map) crudParam.get("条件");
			if(CollectionUtils.isEmpty(条件map)) {
				return null;
			}
		}else if(crudParam.get("条件") instanceof List){
			条件List = (List) crudParam.get("条件");
			if(CollectionUtils.isEmpty(条件List)) {
				return null;
			}
		}

		for (Map map : 条件List) {

			条件詞条List.add((String) map.get("項目"));

		}

		return 条件詞条List;
	}

	private String get主目標詞条(Map<String, Object> crudParam) {

		myLogger.printCallMessage(sCallPath,"InputData.get主目標詞条()");

		/*
		{
			"操作":"検索",
			"目標":{"社員Bean":["番号","姓名","性別","生年月日","入社年月日","契約種類"]},
			"条件":[
			{"値":"2790001","FORMAT":"0","目標":"社員Bean","項目":"番号","ID":"0","計算符号":"等于"},
			{"値":"AAA","FORMAT":"0","目標":"社員Bean","項目":"姓名","ID":"1","計算符号":"like"},
			{"値":"女","FORMAT":"0","目標":"社員Bean","項目":"性別","ID":"2","計算符号":"like"},
			{"値":"正社員","FORMAT":"0","目標":"社員Bean","項目":"契約種類","ID":"3","計算符号":"等于"}
			],
			"条件関係":"0 and 1 and 2 and 3 "}

			{
			"操作":"検索",
			"目標":{"社員Bean":["番号","姓名","性別","生年月日","入社年月日","契約種類"]}
			}
		*/

		String s目標 = null;
		if (crudParam.get("目標") instanceof Map) {
			//A: [B,C,D]
			Map<String,Object> map = (Map)crudParam.get("目標");
			for(String skey: map.keySet()) {
				s目標 = skey;
			}

		} else if (crudParam.get("目標") instanceof List) {
			// [A,B,C]
			s目標 = ((List) crudParam.get("目標")).get(0).toString();
		}
		return s目標;
	}

	private List<String> get子目標詞条List(Map<String, Object> crudParam, String s主目標) {

		myLogger.printCallMessage(sCallPath,"InputData.get子目標詞条List(s主目標="+s主目標+")");

		Map map = (Map) crudParam.get("目標");
		//String s目標 = get主目標詞条(crudParam);

		/*
		{
			"操作":"検索",
			"目標":{"社員Bean":["番号","姓名","性別","生年月日","入社年月日","契約種類"]},
			"条件":[
			{"値":"2790001","FORMAT":"0","目標":"社員Bean","項目":"番号","ID":"0","計算符号":"等于"},
			{"値":"AAA","FORMAT":"0","目標":"社員Bean","項目":"姓名","ID":"1","計算符号":"like"},
			{"値":"女","FORMAT":"0","目標":"社員Bean","項目":"性別","ID":"2","計算符号":"like"},
			{"値":"正社員","FORMAT":"0","目標":"社員Bean","項目":"契約種類","ID":"3","計算符号":"等于"}
			],
			"条件関係":"0 and 1 and 2 and 3 "}

			{
			"操作":"検索",
			"目標":{"社員Bean":["番号","姓名","性別","生年月日","入社年月日","契約種類"]}
			}
		*/

		List 子目標詞条List = null;
		if (crudParam.get("目標") instanceof Map) {
			//A: [B,C,D]
			子目標詞条List = (List) ((Map) crudParam.get("目標")).get(s主目標);
			if(CollectionUtils.isEmpty(子目標詞条List)){
				Map m = (Map)crudParam.get("目標");
				Object o1 = m.get(s主目標);
				子目標詞条List = (List) ((List) m.get(s主目標)).get(0);

			}
		} else if (crudParam.get("目標") instanceof List) {
			// [A,B,C]
			子目標詞条List = (List) crudParam.get("目標");
		}
		return 子目標詞条List;
	}

/*	// 关系判断
	private boolean is直接关系_by主目标_条件(String s主目标詞条, List<String> 条件詞条List) {
		Relation relation = new Relation();
		for (String s条件詞条 : 条件詞条List) {
			//			Map map = relation.getRelation_by_A_B(s主目标詞条, s条件詞条);
			//			//key=1  	直接关系
			//			//Key=2		程序关系
			//			if(!map.containsKey("1")) {
			//				return false;
			//			}
		}
		return true;
	}*/

/*	// 关系判断
	private boolean is直接关系_by主目标_子目标(String s主目标詞条, List<String> 子目标詞条List) {
		return is直接关系_by主目标_条件(s主目标詞条, 子目标詞条List);
	}*/

	// 与其他【主目标】与【子目标】与【条件詞条】都不存在【客户 或 业者 或 程序】关系的詞条
	private List get孤立詞条(Map<String, Object> crudParam) {

		myLogger.printCallMessage(sCallPath,"InputData.get孤立詞条()");
		// 主目标
		//    |------子目标		程序 直接关系
		//    |------子目标		程序 直接关系
		//    |------子目标		程序 直接关系
		//    |------条件		程序 直接关系
		//    |------条件		程序 直接关系
		//    |------条件		程序 直接关系
		//----------------------------------------
		// 根据CRUD
		// 判断【主词条】与每个子词条
		// 判断【主词条】与条件中的每个词条
		//----------------------------------------
		String s主目标詞条 = get主目標詞条(crudParam);

		List<String> 子目标詞条List = get子目標詞条List(crudParam, s主目标詞条);

		List<String> 条件詞条List = get条件詞条List(crudParam);

		List<String> resultList = new ArrayList();
		Relation relation = new Relation("InputData.get孤立詞条()");
		for (String s条件詞条 : 条件詞条List) {

			//Map map = relation.getRelation_by_A_B(s主目标詞条, s条件詞条);

			辞書関係識別器 o辞書関係識別器 = new 辞書関係識別器(sCallPath+"InputData.get孤立詞条()");
			String s両辞書関係 = o辞書関係識別器.取得両辞書関係(s主目标詞条, s条件詞条);
			switch (s両辞書関係) {
			case "直接关系":
				// CRUD中，当【主目标】与【条件】，【主目标】与【子目标】，有直接关系时，可以直接调用数据库进行处理
				// return 処理byTHINGsDB(crudParam);

			case "非直接关系":

			}

			return resultList;

		}
		return resultList;

	}

	public Map 取得辞書関係_byCRUDmap(Map<String, Object> crudMap) {

		myLogger.printCallMessage(sCallPath,"InputData.取得辞書関係_byCRUDmap()");
		Map<String, Object> 结果 = new HashMap();

		/**
		 * 如果是CRUD（追加）的情况
		 * 就直接放行吧（全直接关系）
		 */
		if(! StringUtils.isEmpty(crudMap.get("操作"))) {
			String s = (String)crudMap.get("操作");
			if(s.equals("追加")) {
				结果.put("孤岛词条", null);
				结果.put("辞書関係", "全直接关系");
				return 结果;
			}
		}

		// 【主目标】与【条件】，【主目标】与【子目标】
		/**
		switch (o辞書関係識別器.取得両辞書関係(s主目标詞条, s条件詞条)) {
		case "无非直接关系":
		case "全直接关系":
		case "有非直接关系":
		*/
		/*------------------------------------------------------------
		 * crudMap{
		 * 		主目标{项目1、项目2。。。}
		 * 		条件{项目。。。}
		 * }
		 ------------------------------------------------------------*/
		/*------------------------------------------------------------
		 * 找到主目标
		 * 找到子目标
		 * 找到条件
		 * for(子目标){
		 *    把 每个子目标与主目标作为对象
		 * 		|-----【取得两辞書関係_根据辞書Aand辞書B】
		 * 如果全部都有值，则返回其【全部直接关系】
		 * 如果没有结果的时候，返回【非全部直接】，并返回孤岛词条List
		 * 返回结果如下
		 * Map
		 * 		结果：OBJECT(String)
		 *      孤岛词条：OBJECT(List)
		 ------------------------------------------------------------*/
		//InputData inputData = new InputData();
		String s主目标 = get主目標詞条(crudMap);
		List<String> s子目標詞条List = get子目標詞条List(crudMap, s主目标);
		List<String> s条件詞条List = get条件詞条List(crudMap);
		List<String> 孤岛詞条List = new ArrayList();


		for(String s子目標 : s子目標詞条List) {
			//詞条CRUD 詞条crud = new 詞条CRUD("取得辞書関係_byCRUDmap");
			辞書関係識別器 o辞書関係識別器 = new 辞書関係識別器(sCallPath+"InputData.取得辞書関係_byCRUDmap()");
			String s関係 = o辞書関係識別器.取得两辞書関係_根据辞書Aand辞書B(s子目標, s主目标);
			if(StringUtils.isEmpty(s関係)) {
				孤岛詞条List.add(s子目標);
			}
		}
		if(CollectionUtils.isEmpty(孤岛詞条List)) {
			结果.put("孤岛词条", null);
			结果.put("辞書関係", "全直接关系");

		}else {
			结果.put("孤岛词条", 孤岛詞条List);
			结果.put("辞書関係", "有非直接关系");
		}
		return 结果;
	}

}
