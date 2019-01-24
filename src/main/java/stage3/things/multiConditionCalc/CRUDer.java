package stage3.things.multiConditionCalc;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.CollectionUtils;

import stage3.REL.program.詞条CRUD;
import stage3.log.MyLogger;
import stage3.things.THINGSdb;

public class CRUDer {
	String sCallPath = null;
	MyLogger myLogger = new MyLogger();

	public CRUDer(String sCallPath) {
		this.sCallPath = sCallPath;
	}
	/*------------------------計算_无条件関係------------------------------------
		{"操作":"検索",
		"目標":{
				"社員Bean":["番号","姓名","性別","生年月日","入社年月日","契約種類"]
				}
		}
	 ----------------------------------------------------------------------------------*/

	/*------------------------計算_多条件関係------------------------------------
		{
		"操作":"検索",
		"目標":{
				"社員Bean":["番号","姓名","性別","生年月日","入社年月日","契約種類"]},
		"条件":[{"値":"2790001","FORMAT":"0","目標":"社員Bean","項目":"番号","ID":"0","計算符号":"等于"},
				{"値":"sss","FORMAT":"0","目標":"社員Bean","項目":"姓名","ID":"1","計算符号":"like"},
				{"値":"女","FORMAT":"0","目標":"社員Bean","項目":"性別","ID":"2","計算符号":"like"},
				{"値":"役員","FORMAT":"0","目標":"社員Bean","項目":"契約種類","ID":"3","計算符号":"等于"}],
		"条件関係":"0 and 1 and 2 and 3 "
}
	 ----------------------------------------------------------------------------------*/
	public List search(Map<String, Object> map){

		myLogger.printCallMessage(sCallPath,"CRUDer.search()");


		MultiConditionCalc multiConditionCalc = new MultiConditionCalc(sCallPath+"search()");


		//if(map.get("条件")== null || map.get("条件関係") == null) {
		if(! check条件(map)) {
			return multiConditionCalc.計算_无条件関係(map);
		}else
		if(map.get("条件関係")== null) {
			//単条件检索
			return multiConditionCalc.計算_単条件関係(map);
		}else {
			//多条件检索
			return multiConditionCalc.計算_多条件関係(map, Long.parseLong("0"));
		}
	}

	boolean check条件(Map<String, Object> 計算情報Map){

		myLogger.printCallMessage(sCallPath,"CRUDer.check条件()");

		LinkedHashMap 条件LinkedHashMap = null;
		List 条件List = null;

		if (計算情報Map.get("条件") instanceof LinkedHashMap) {
			条件LinkedHashMap = (LinkedHashMap)計算情報Map.get("条件");
			if(CollectionUtils.isEmpty(条件LinkedHashMap)) {
				return false;
			}
		}

		if (計算情報Map.get("条件") instanceof ArrayList) {
			条件List = (ArrayList)計算情報Map.get("条件");
			if(CollectionUtils.isEmpty(条件List)) {
				return false;
			}
		}

		return true;

	}

	public List add(Map<String, Object> crudMap){
		/**
		 * crudMap = {CRUD={操作=追加, 目标=股票实时情报, 条件={ ...
		 */

		myLogger.printCallMessage(sCallPath,"CRUDer.add()");

		THINGSdb db = new THINGSdb(sCallPath + "add()");
		詞条CRUD 詞条crud = new 詞条CRUD(sCallPath + "add()");
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		try {
			db.事前準備();
			//db.追加_by数据(map, new ArrayList());
			詞条crud.追加詞条信息map((Map)crudMap.get("CRUD"));
			jsonObj.put("结果", "OK");
			//return Arrays.asList(jsonObj.toString());
		} catch (Exception e) {
			jsonObj.put("结果", "NG");
			//return Arrays.asList(jsonObj.toString());
		}catch(Throwable e) {
			System.out.println(e.getMessage());
		}finally {
			jsonArr.put(jsonObj);
			return jsonArr.toList();
		}
	}


}

