package stage3.REL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import stage3.NLP.NatureLanguageParser;
import stage3.REL.io.InputData;
import stage3.REL.io.OutputData;
import stage3.REL.program.詞条CRUD;
import stage3.engine.bean.辞書BeanDTO;
import stage3.engine.run.implement.計算解析_程序数据執行器;
import stage3.engine.tool.SDP;
import stage3.log.MyLogger;
import stage3.nosql.辞書関係識別器;
import stage3.things.multiConditionCalc.CRUDer;

/*
 * 入力对象{program,CRUD}
 * 来调取【程序执行器】和【DB】来处理数据
 * 然后，组织发牛
 *
 */

/**
 *
 *
 *
 */

public class Relation {


	String sCallPath = null;
	MyLogger myLogger = new MyLogger();

	public Relation(String sCallPath) {
		this.sCallPath = sCallPath;
	}

	// 从REACT传送过来的入力数据
	InputData inputData = new InputData("Relation");
	// 传给REACT的出力数据
	OutputData outputData = new OutputData();

	public List process(Map<String, Object> map){
		myLogger.printCallMessage(sCallPath,"Relation.process()");
		//------------------------------------------------------------------------
		// 从UI入力的暂时有以下几种情况
		// UI--->RLA
		// 		  |--->NLP
		//		  |			{NL: }						// 人机对话
		//		  |--->THINGs
		//		  |			{CRUD{{操作: },{条件: },{目標: }}}	// 画面转入
		//		  |
		//		  |--->词条CRUDs
		//		  |			{{GW: }}					// NLP转来的
		//		  |--->PRO
		//					{{SDP: },{from: },{to: }}	// 程序直接走
		//------------------------------------------------------------------------
		if(CollectionUtils.isEmpty(map)) {
			return null;
		}
		// {NL={
		if(is_NL(map)) {
			// 先按NL处理
			// 再重新由RLA处理
			return process(process_NL(map));
		}
		// {CRUD={操作=追加,
		if(is_CRUD(map)) {
			try {
				return process_CRUD(map);
			}catch(Throwable e) {
				System.out.println(e.getMessage());
			}
		}
		// {GW={
		if(is_GW(map)) {
			return process_GW(map);
		}
		// {SDP={
		if(is_SDP(map)) {
			return process_SDP(map);
		}

		return null;
	}

	private boolean is_SDP(Map<String, Object> map) {
		myLogger.printCallMessage(sCallPath,"Relation.is_SDP()");
		// 判断入力信息中是否有【SDP】
		return has_KeyWord(map, "SDP");
	}
	private boolean is_GW(Map<String, Object> map) {
		myLogger.printCallMessage(sCallPath,"Relation.is_GW()");
		// 判断入力信息中是否有【GW】
		return has_KeyWord(map, "GW");
	}
	private boolean is_CRUD(Map<String, Object> map) {
		myLogger.printCallMessage(sCallPath,"Relation.is_CRUD()");
		// 判断入力信息中是否有【CRUD】
		return has_KeyWord(map, "CRUD");
	}
	private boolean is_NL(Map<String, Object> map) {
		myLogger.printCallMessage(sCallPath,"Relation.is_NL()");
		// 判断入力信息中是否有【NL】
		return has_KeyWord(map, "NL");
	}

	/**
	 * 从UI过来的信息中，找到是否含有符合目标词条
	 * @param map
	 * @param string
	 * @return
	 */
	private boolean has_KeyWord(Map<String, Object> map_fromUI, String s目標詞条) {

		myLogger.printCallMessage(sCallPath,"Relation.has_KeyWord(s目標詞条="+s目標詞条+")");

		for(Map.Entry<String, Object> entry : map_fromUI.entrySet()) {
		    if(StringUtils.equals(entry.getKey(), s目標詞条)) {
		    	return true;
		    }
		}
		return false;
	}



	/**
	 * 针对以下内容进行
	 * 通过程序执行器进行处理
	 * {SDP, 自定义程序}
	 * SDP是自定义程序
	 * @param map
	 * @return
	 */
	private List process_SDP(Map<String, Object> map) {
		myLogger.printCallMessage(sCallPath,"Relation.process_SDP()");
		/**
		{
			SDP， {	 List<String>  }
		}
		*/

		return 処理by程序执行器_程序代码List((List)map.get("SDP"));
		//return 処理by程序执行器(map);
	}



	/**
	 * 针对{GW, 词条名}进行处理
	 * @param map
	 * @return
	 *
	 */
	private List process_GW(Map<String, Object> map) {
		myLogger.printCallMessage(sCallPath,"Relation.process_GW()");
		// 通过调用词条CRUD来处理GW性质的入力信息。
		// map中一定是以下结构
		//  {GW, 词条名}
		myLogger.printCallMessage(sCallPath,"Relation.process_GW( )");

		詞条CRUD o詞条CRUD = new 詞条CRUD(sCallPath+"process_GW()");
		List list = new ArrayList(o詞条CRUD.取得関係詞条_検索UIListMap((String)map.get("GW")));
		return list;
	}

	/**
	 * 用NLP处理，从UI传过来的消息
	 * @param map
	 * @return
	 *    {GW, sNL_名詞}
	 */
	private Map process_NL(Map<String, Object> map) {

		myLogger.printCallMessage(sCallPath,"Relation.process_NL( )");

		// {NL:XXXXXXXX}
		// 用NLP处理，从UI传过来的消息
		NatureLanguageParser natureLanguageParser = new NatureLanguageParser();
		List result_list = new ArrayList();
		return natureLanguageParser.createCRUD_byNL(map);
	}

	// 处理入口
	// 从页面出来的，或是从NLP传来的都是经由Json转换后的Map
	public List process_CRUD(Map<String, Object> map){

		myLogger.printCallMessage(sCallPath,"Relation.process_CRUD( )");

		// inputData.getRsult 取得计算结果

		//-----------------------------------------------------
		//入口 【<-----<<<<<】
		// 关系判断：词条関係的取得
		// 如果是 CRUD
		//		如果 有直接关系
		//			调用 THINGs.run
		//
		//		如果 没有直接関係
		//			调取 詞条CRUD.search
		//			如果存在代码
		//				调取程序CRUD.chk計算関係 //即要确认所有词条之间是否全部都存在計算関係
		//				如果代码可执行
		//					调用  程序执行器.run
		//						如果存在代码
		//							如果 有直接関係
		//								调用 THINGs.run
		//							如果 没有直接関係
		//								调取 詞条CRUD.search
		//						run
		//
		//					如果不存在代码
		//						调取 詞条CRUD.search
		//						run
		// 如果是 program
		//		调用 程序执行器.runProgram
		//			调取 詞条CRUD.create
		//				run
		//-----------------------------------------------------
		Object 処理結果 = null;

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map_convert = new HashMap();

		try {
			Object crud = map.get("CRUD");
			if (crud instanceof String) {
				// {CRUD={	开盘价=3.920, 成交量=44817843, 卖四股数=0, 卖四报价=0.000, 成交股票数=44817843, 最高价=4.380, 成交金额=192773239.000, 买五股数=36700, 买五报价=4.340, 卖一报价=0.000, 卖一股数=0, 买三报价=4.360, 买二报价=4.370, 买三股数=7100, 昨日收盘价=3.980, 买二股数=26700, 日期=2018-12-21, 买一报价=4.380, 收盘价=4.380, 股票名称=游久游戏, 买一股数=1738104, 最低价=3.890, 卖五报价=0.000, 买四股数=50000, 买四报价=4.350, 竞卖价=0.000, 竞买价=4.380, 时间=15:00:00, 成交额=192773239.000, 卖三股数=0, 卖五股数=0, 卖三报价=0.000, 卖二股数=0, 卖二报价=0.000}}
				//map_convert = mapper.readValue((String)crud, Map.class);
				map_convert.put("CRUD",  mapper.readValue((String)crud, Map.class));
			}else if (crud instanceof Map) {
				//map_convert = (Map)crud;
				map_convert.put("CRUD",  crud);
			}

			// map_convert = mapper.readValue((Object)map.get("CRUD"), Map.class);
		} catch (JsonParseException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}

		辞書関係識別器 o辞書関係識別器 = new 辞書関係識別器(sCallPath+"Relation.process_CRUD()");

		// 判断这个map_convert是【CRUD】还是【SDP】，再决定用什么方式处理
		String s情報Type = o辞書関係識別器.取得入力情報Type((Map)map_convert.get("CRUD"));
		switch (s情報Type) {
		case "CRUD":
			// 画面入力处理
			// 画面对话处理（部分：查询、指令、定义时）
			処理結果 = 処理CRUD(map_convert);
			break;
		case "SDP":
			// 画面对话处理（部分：逻辑、方法描述时）
			処理結果 = 処理program(map_convert);
			break;
		}

		// outputData.getRsult
		// return outputData.getRsultData(inputData.getRsultData(map));
		return outputData.getRsultData(処理結果);
	}

	/**
	 *
	 * @param map
	 * @return
	 */
	private List 処理program(Map<String, Object> programMap) {
		myLogger.printCallMessage(sCallPath,"Relation.処理program( )");
		//------------------------------------
		//	调用 程序执行器.runProgram
		//		调取 詞条CRUD.保存程序代码_byProgram【<-----<<<<<】
		//			run
		//------------------------------------

		詞条CRUD o詞条CRUD = new 詞条CRUD(sCallPath+"処理program");
		List<String> 程序代码List =  o詞条CRUD.保存程序代码_byProgram(programMap);


		// 直接对程序代码进行处理
		return 処理by程序执行器_程序代码List(toListObject(程序代码List));

	}

	// 纯纯的一个转换器
	private List<Object> toListObject(List<String> 程序代码List) {
		List<Object> 程序代码ListObject = new ArrayList();
		for(String s : 程序代码List) {
			程序代码ListObject.add(s.split(","));
		}
		return 程序代码ListObject;
	}

	/**
	 *
	 * @param crudMap
	 * @return
	 */
	private Object 処理CRUD(Map<String, Object> crudMap) {
		/* crudMap={
		"操作":"追加",
		"目标":"股票实时信息",
		"条件":
		{
		"开盘价":"3.920",
		"成交量":"44817843",
		"卖四股数":"0",
		"卖四报价":"0.000",
		"成交股票数":"44817843",
		"最高价":"4.380",
		"成交金额":"192773239.000",
		"买五股数":"36700",
		"买五报价":"4.340",
		"卖一报价":"0.000",
		"卖一股数":"0",
		"买三报价":"4.360",
		"买二报价":"4.370",
		"买三股数":"7100",
		"昨日收盘价":"3.980",
		"买二股数":"26700",
		"日期":"2018-12-21",
		"买一报价":"4.380",
		"收盘价":"4.380",
		"股票名称":"游久游戏",
		"买一股数":"1738104",
		"最低价":"3.890",
		"卖五报价":"0.000",
		"买四股数":"50000",
		"买四报价":"4.350",
		"竞卖价":"0.000",
		"竞买价":"4.380",
		"时间":"15:00:00",
		"成交额":"192773239.000",
		"卖三股数":"0",
		"卖五股数":"0",
		"卖三报价":"0.000",
		"卖二股数":"0",
		"卖二报价":"0.000"
		}
		}
		*/
		myLogger.printCallMessage(sCallPath,"Relation.処理CRUD( )");

		//------------------------------------------------------------
		// 如果是 CRUD 【<-----<<<<<】
		//		如果 有直接关系
		//			调用 THINGs.run
		//
		//		如果 没有直接関係
		//			调取 詞条CRUD.search
		//			如果存在代码
		//				调取程序CRUD.chk計算関係 //即要确认所有词条之间是否全部都存在計算関係
		//				如果代码可执行
		//					调用  程序执行器.run
		//						如果存在代码
		//							如果 有直接関係
		//								调用 THINGs.run
		//							如果 没有直接関係
		//								调取 詞条CRUD.search
		//						run
		//
		//					如果不存在代码
		//						调取 詞条CRUD.search
		//						run
		//------------------------------------------------------------
		//辞書関係識別器 o辞書関係識別器 = new 辞書関係識別器();
		InputData inputData = new InputData(sCallPath+"Relation.処理CRUD( )");
		Map 结果map = inputData.取得辞書関係_byCRUDmap((Map)crudMap.get("CRUD"));
		switch ((String)结果map.get("辞書関係")) {
		case "无非直接关系":
		case "全直接关系":
			// CRUD中，当【主目标】与【条件】，【主目标】与【子目标】，有直接关系时，可以直接调用数据库进行处理
			return 処理byTHINGsDB(crudMap);

		case "有非直接关系":

			// CRUD中，当【主目标】与【条件】，【主目标】与【子目标】，只要有一个没有直接关系时，
			// 要调用程序执行器，进行处理
			return 処理程序执行器byCRUDMap(crudMap);
		}

		return null;
	}

	/**
	 *
	 * @param crudMap
	 * @return
	 */
	private List 処理程序执行器byCRUDMap(Map<String, Object> crudMap) {

		myLogger.printCallMessage(sCallPath,"Relation.処理by程序执行器( )");

		//------------------------------------------------------------
		//			调取詞条CRUD.search
		//			如果存在代码
		//				调取程序CRUD.chk計算関係 //即要确认所有词条之间是否全部都存在計算関係
		//				如果代码可执行
		//					调用 程序执行器.run
		//						-----------------------------------
		//						如果存在代码
		//							如果 有直接関係
		//								调用 THINGs.run
		//							如果 没有直接関係
		//								调取 詞条CRUD.search
		//						run
		//						-----------------------------------
		//					如果不存在代码
		//						调取 程序CRUD.search
		//						run
		//------------------------------------------------------------
		詞条CRUD o詞条CRUD = new 詞条CRUD(sCallPath+"処理CRUD");
		List<String> 程序代码List =  o詞条CRUD.取得程序代码_byCRUD(crudMap);

		return 処理by程序执行器_程序代码List(toListObject(程序代码List));

	}

	private List 処理by程序执行器_程序代码List(List<Object> 程序代码List) {

		myLogger.printCallMessage(sCallPath,"Relation.処理by程序执行器_程序代码List( )");

		//------------------------------------
		//	调用 程序执行器.runProgram
		//		调取 詞条CRUD.create
		//			run 			【<-----<<<<<】
		//------------------------------------

		// 詞条CRUD o詞条CRUD = new 詞条CRUD("処理by程序执行器_程序代码List");
		// CHK 程序代码List
		if(! CollectionUtils.isEmpty(程序代码List)) {
			// 如果存在代码
			//if (o詞条CRUD.chk程序代码中計算関係完整(程序代码List)) {
			//	if (chk程序代码中計算関係完整(程序代码List)) {
				// 如果代码可执行
				計算解析_程序数据執行器 o計算解析_程序数据執行器 = new 計算解析_程序数据執行器(sCallPath+"処理by程序执行器_程序代码List");

				SDP sdp = new SDP(sCallPath+"処理by程序执行器_程序代码List");
				Map 入力情報Map = sdp.取得入力对象Map_bySDPList(程序代码List);

				List<辞書BeanDTO> 程序代码_辞書BeanDTO_List = sdp.get辞書BeanDTOList_by程序代码List(程序代码List);
				return o計算解析_程序数据執行器.執行程序数据_根据プログラム数据and業務数据(
						程序代码_辞書BeanDTO_List, 入力情報Map);
			//}
		}else {
			return null;
		}
	}

	private boolean chk程序代码中計算関係完整(List<String> 程序代码List) {

		myLogger.printCallMessage(sCallPath,"Relation.chk程序代码中計算関係完整( )");

		詞条CRUD o詞条CRUD = new 詞条CRUD(sCallPath+"処理by程序执行器_程序代码List");
		Map map = o詞条CRUD.chk程序代码中計算関係完整(程序代码List);
		String sResult = (String)map.get("result");
		if(StringUtils.equals(sResult, "有孤立词条")) {
			return false;
		}else {
			return true;
		}
	}



	/**
	 * CRUD中，当【主目标】与【条件】，【主目标】与【子目标】，有直接关系时
	 * 可以直接调用数据库进行处理。
	 * @param crudMap
	 * @return
	 */
	private Object 処理byTHINGsDB(Map<String, Object> crudMap) {

		myLogger.printCallMessage(sCallPath,"Relation.処理byTHINGsDB( )");

		//------------------------------------------------------------
		// CRUD中，当【主目标】与【条件】，【主目标】与【子目标】，有直接关系时，
		// 可以直接调用数据库进行处理。
		//------------------------------------------------------------

		辞書関係識別器 o辞書関係識別器 = new 辞書関係識別器(sCallPath+"Relation.処理byTHINGsDB");

		// 调用 THINGs.run
		CRUDer cRUDer = new CRUDer(sCallPath+"Relation.処理byTHINGsDB");
		String sCRUD処理Type = o辞書関係識別器.取得CRUD処理Type((Map)crudMap.get("CRUD"));
		switch (sCRUD処理Type) {
		case "ADD":
			// 当操作为【ADD】时、则调用CRUDer的追加处理
			return cRUDer.add(crudMap);
			//break;

		case "SEARCH":
			// 当操作为【SEARCH】时、则调用CRUDer的检索处理
			return cRUDer.search(crudMap);
			//break;
		}
		return cRUDer;

	}
}
