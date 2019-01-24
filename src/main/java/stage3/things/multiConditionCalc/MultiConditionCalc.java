package stage3.things.multiConditionCalc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.CollectionUtils;

import stage3.REL.io.MyFileReader;
import stage3.REL.io.file.FileReader_BufferedReader;
import stage3.REL.io.file.FileReader_MappedByteBuffer;
import stage3.consts.PublicName;
import stage3.engine.tool.time.時間対象;
import stage3.log.MyLogger;
import stage3.nosql.NOSQL;
import stage3.things.THINGSdb;
import stage3.things.dto.数据DTO;
import stage3.things.file.文件全路径;
import stage3.things.id.ID;
import stage3.things.id.詞条;
import stage3.things.multiConditionCalc.bean.条件DTO;
import stage3.things.multiConditionCalc.bean.計算情報DTO;
import stage3.things.multiConditionCalc.calc.AndCalc;
import stage3.things.multiConditionCalc.calc.NotCalc;
import stage3.things.multiConditionCalc.calc.OrCalc;
import stage3.things.relations.業者;
import stage3.things.relations.顧客;
/**
 * 多条件検索
 * @author beihai
 *
 */
public class MultiConditionCalc {
	/**
	 *
	 * @param 計算情報dto
	 * @param ipos
	 * @return
	 */

	String sCallPath = null;
	MyLogger myLogger = new MyLogger();

	public MultiConditionCalc(String sCallPath) {
		this.sCallPath = sCallPath;
	}
/*	public List 計算_多条件関係(計算情報DTO 計算情報dto, long ipos) {
		//多条件

		String s条件关系 = 計算情報dto.get条件関係();
		if(StringUtils.isEmpty(s条件关系)) { return null;}

		String sTokens[] = s条件关系.split(" ");
		List<Map> 計算結果List = new ArrayList();
		Map<String, List> 計算結果map = new HashMap();
		Calc calc = null;

		if (StringUtils.isNumeric(sTokens[0])) {

			計算結果List = this.計算_根据条件信息(計算情報dto.get条件().get(NumberUtils.toInt(sTokens[0])));
			//将結果存入Map中
			計算結果map.put("1", 計算結果List);

		}

		switch (sTokens[0]) {

		case "And": //And計算の準備
			calc = new AndCalc();
			break;

		case "Or": //Or計算の準備
			calc = new OrCalc();
			break;

		case "Not": //Not計算の準備
			calc = new NotCalc();
			break;

		case "(": //自分を呼ぶで再帰計算する
			計算結果List = this.計算_多条件関係(計算情報dto, ipos);
			計算結果map.put("1", 計算結果List);
			break;

		case ")": //計算結果
			return calc.論理計算(計算結果map.get(0), 計算結果map.get(1));
		}

		return 計算結果List;
	}*/

	/**
	 *
	 * @param 計算情報Map
	 * @param ipos
	 * @return
	 */
	public List 計算_多条件関係(Map 計算情報Map, long ipos) {

		myLogger.printCallMessage(sCallPath,"MultiConditionCalc.計算_多条件関係()");

		List<Map> 論理結果List = this.取得論理結果List_by論理計算(計算情報Map, ipos);
		List<String> 数据采番IdList = new ArrayList();
		for(Map 論理結果 : 論理結果List) {
			数据采番IdList.add((String)論理結果.get(PublicName.KEY_数据採番ID));
		}

		return this.取得目標List_by数据采番IdList_計算情報Map(数据采番IdList, 計算情報Map);
	}

	/**
	 * 論理計算
	 * @param 計算情報Map
	 * @param ipos
	 * @return
	 */
	private  List<Map> 取得論理結果List_by論理計算(Map 計算情報Map, long ipos){

		myLogger.printCallMessage(sCallPath,"MultiConditionCalc.取得論理結果List_by論理計算()");
		//多条件

		String s条件关系 = (String) 計算情報Map.get(PublicName.KEY_条件関係);
		String sTokens[] = s条件关系.split(" ");
		List<Map> 計算結果List = new ArrayList();
		Map<String, List> 計算結果map = new HashMap();
		Calc calc = null;

		// 取第一个条件式
		if (StringUtils.isNumeric(sTokens[0])) {

			Map 条件Map = (Map)((List)計算情報Map.get(PublicName.KEY_条件)).get(NumberUtils.toInt(sTokens[0]));
			計算結果List = this.計算結果_根据条件信息Map_地(条件Map);
			//将結果存入Map中
			計算結果map.put(PublicName.KEY_0, 計算結果List);

		}

		switch (sTokens[1]) {
		case PublicName.KEY_and:
		case PublicName.KEY_And: //And計算の準備
			calc = new AndCalc();
			break;
		case PublicName.KEY_or:
		case PublicName.KEY_Or: //Or計算の準備
			calc = new OrCalc();
			break;
		case PublicName.KEY_not:
		case PublicName.KEY_Not: //Not計算の準備
			calc = new NotCalc();
			break;

		case PublicName.KEY_左括号: //自分を呼ぶで再帰計算する
			計算結果List = this.計算_多条件関係(計算情報Map, ipos);
			計算結果map.put(PublicName.KEY_1, 計算結果List);
			break;

		case PublicName.KEY_右括号: //計算結果
			return calc.論理計算(計算結果map.get(PublicName.KEY_0), 計算結果map.get(PublicName.KEY_1));
		}

		// 取第二个条件式
		if (StringUtils.isNumeric(sTokens[2])) {

			Map 条件Map = (Map)((List)計算情報Map.get(PublicName.KEY_条件)).get(NumberUtils.toInt(sTokens[2]));
			計算結果List = this.計算結果_根据条件信息Map_地(条件Map);
			//将結果存入Map中
			計算結果map.put("1", 計算結果List);

		}

		return calc.論理計算(計算結果map.get(PublicName.KEY_0), 計算結果map.get(PublicName.KEY_1));
	}

	/**
	 * 計算每个条件式
	 * @param 条件dto
	 * @return
	 */
	private List<Map> 計算_根据条件信息(条件DTO 条件dto) {

		myLogger.printCallMessage(sCallPath,"MultiConditionCalc.計算_根据条件信息()");

		// 計算每个条件式
		String s計算符 = null;
		String s値 = null;

		String result = this.取得関係Str_by目標and条件項目(条件dto);

		if (result == PublicName.KEY_直接关系){

				return this.取得結果_根据直接関係(条件dto);

		}else if (result == PublicName.KEY_程序关系){

				return this.取得結果_Call程序数据執行器(条件dto);
		}else{
				// 告.诉.他.们：还.没有两个词条之间的计算方法。
				return null;
		}
	}

	/**
	 * 主要是点、線、面的计算
	 * @param 条件Map
	 * @return
	 */
	private List<Map> 計算結果_根据条件信息Map_地(Map 条件Map) {

		// 計算每个条件式
		List<Map> 計算結果List = 計算結果_根据条件信息Map(条件Map);

		// 如果没有取到结果
		if(CollectionUtils.isEmpty(計算結果List)) {
			計算結果List = 計算結果_根据条件信息Map_天(条件Map);
		}

		return 計算結果List;
	}

	/**
	 * 主要是点、線、面、時空的计算
	 * @param 条件Map
	 * @return
	 */
	private List<Map> 計算結果_根据条件信息Map_天(Map 条件Map) {
		myLogger.printCallMessage(sCallPath,"MultiConditionCalc.計算結果_根据条件信息Map_天()");

		List<Map> 計算結果List = new ArrayList();

		// 如果 条件中有时间词条
		// 条件:{
		// 		値:
		// 		FORMAT:
		// 		目標:
		// 		項目:
		// 		ID:
		// 		計算符号:
		// }

		// 分解时间词条
		// 時間Map
		//    |------key=时间单位 ,value=年
		//	  |------key=时间值 ,value=2018

		Map<String, String> 主時間Map = 取得主時間Map_by条件Map(条件Map);
		if(CollectionUtils.isEmpty(主時間Map)) {
			return null;
		}
		時間対象 o時間対象 = new 時間対象(sCallPath+"計算結果_根据条件信息Map_天()");

		List<Map> 時間子詞条 = o時間対象.分解時間詞条_by主時間Map(主時間Map);

		// 子時間Map
		//    |------key=时间单位 ,value=月  key=时间值 ,value=201801
		//    |------key=时间单位 ,value=月  key=时间值 ,value=201802
		//    |------key=时间单位 ,value=月  key=时间值 ,value=201803
		//    |------key=时间单位 ,value=月  key=时间值 ,value=201804
		//    |------key=时间单位 ,value=月  key=时间值 ,value=201805
		//    |------key=时间单位 ,value=月  key=时间值 ,value=201806
		//    |------key=时间单位 ,value=月  key=时间值 ,value=201807
		//    |------key=时间单位 ,value=月  key=时间值 ,value=201808
		//    |------key=时间单位 ,value=月  key=时间值 ,value=201809
		//    |------key=时间单位 ,value=月  key=时间值 ,value=201810
		//    |------key=时间单位 ,value=月  key=时间值 ,value=201811
		//    |------key=时间单位 ,value=月  key=时间值 ,value=201812

		List<Object> 子時間計算結果List = new ArrayList();
		// 递归调用
		for(Map 子時間map : 時間子詞条) {
			// 重置条件Map
			// 条件Map:[
			//  {},{主時間Map},{}
			//
			// ]
			Map 子条件Map = set子条件Map(条件Map, 主時間Map, 子時間map);
			/*
			 * 計算結果 = List<Map>
			 * Map1:  价格1
			 * Map2:  价格2
			 * Map3:  价格3
			 * Map4:  价格4
			 * Map5:  价格5
			 */
			子時間計算結果List.add(計算結果_根据条件信息Map(子条件Map));
		}

		// 整合这些分解的时间所对应的值到主干上
		// 课题：1、该把什么对象整合：在条件的目标中可以找到
		//       2、该如何整合：利用自定义程序解决：主要是累积还是汇合
		計算結果List = 整合这些分解的子時間計算結果List_To_主干上(子時間計算結果List, 主時間Map, 条件Map);

/*		// 再次取得对象目标值
		計算結果List = 計算結果_根据条件信息Map(条件Map);*/

		return 計算結果List;
	}

	/**
	 *
	 * @param 子時間計算結果List
	 * 		   List:子時間。所以是·List形式
	 *              List:每个子时间都有一堆的价格。所以是·List形式
	 *                     Map:其中具体内容是Map
	 * @param 主時間Map
	 * @param 条件Map
	 * @return
	 */
	public List<Map> 整合这些分解的子時間計算結果List_To_主干上(List<Object> 子時間計算結果List, Map<String, String> 主時間Map, Map 条件Map) {

		myLogger.printCallMessage(sCallPath,"MultiConditionCalc.整合这些分解的子時間計算結果List_To_主干上()");

		// 将以下值加入到key=时间值 ,value=2018上

		// 子時間計算結果List
		//    |
		//    |------key=时间单位 ,value=月  key=时间值 ,value=201801
		//	  |			計算結果 = List<Map>
		//	  |						Map1:  价格1
		// 	  |						Map2:  价格2
		// 	  |						Map3:  价格3
		// 	  |						Map4:  价格4
		// 	  |						Map5:  价格5
		//    |------key=时间单位 ,value=月  key=时间值 ,value=201802
		//    |------key=时间单位 ,value=月  key=时间值 ,value=201803
		//    |------key=时间单位 ,value=月  key=时间值 ,value=201804
		//    |------key=时间单位 ,value=月  key=时间值 ,value=201805
		//    |------key=时间单位 ,value=月  key=时间值 ,value=201806
		//    |------key=时间单位 ,value=月  key=时间值 ,value=201807
		//    |------key=时间单位 ,value=月  key=时间值 ,value=201808
		//    |------key=时间单位 ,value=月  key=时间值 ,value=201809
		//    |------key=时间单位 ,value=月  key=时间值 ,value=201810
		//    |------key=时间单位 ,value=月  key=时间值 ,value=201811
		//    |------key=时间单位 ,value=月  key=时间值 ,value=201812

		//-------------------------------------------------------------------
		// 整合这些分解的时间所对应的值到主干上
		// 课题：1、该把什么对象整合：在条件的目标中可以找到
		//       2、该如何整合：利用自定义程序解决：主要是累积还是汇合（累计就是1+2+3；汇合就是{1,2,3} ）
		//-------------------------------------------------------------------
		// 取得目标
		String s目標词条 = (String)条件Map.get(PublicName.KEY_目標);

		// 取得目标词条例，价格的整合方法
		詞条 o詞条 = new 詞条("整合这些分解的子時間計算結果List_To_主干上");
		//----------------------------------------------
		// 	            整合方法
		//		            [----------1:累积
		//      			[----------2: 汇合
		//					[----------[GUEST]
		//		            			  [----------价格
		//----------------------------------------------
		String s業者詞条ID = o詞条.取得詞条ID_by詞条名(s目標词条);
		String s業者数据ID = PublicName.KEY_0;
		String s顧客詞条ID = o詞条.取得詞条ID_by詞条名(PublicName.KEY_整合方法);
		List<String> 整合方法idList = o詞条.検索顧客詞条数据採番idList_by顧客詞条IDand業者詞条IDand業者数据ID(
				s顧客詞条ID,
				s業者詞条ID,
				s業者数据ID); // 累积还是汇合（累计就是1+2+3；汇合就是{1,2,3} ）
		String s整合方法 = CollectionUtils.isEmpty(整合方法idList)?PublicName.KEY_累积:整合方法idList.get(0);
		整合子時間到主時間 o整合子時間到主時間 = null;
		switch (s整合方法) {
		case PublicName.KEY_累积:
			// 整合结果：List<Map>
			//				   [------key= 值，value=XXXX
			//				   [------key= 时间单位，value=年
			//			   	   [------key= 时间值，value=2018

			o整合子時間到主時間 = new 累計整合子時間到主時間();
		case PublicName.KEY_汇合:
			// 整合结果：List<Map>
			//				   [------key= 值，value={价格1，价格2，价格3，价格4，价格5......}
			//				   [------key= 时间单位，value=年
			//			   	   [------key= 时间值，value=2018
			o整合子時間到主時間 = new 汇合整合子時間到主時間();
		}

		return o整合子時間到主時間.整合子時間値到主時間(子時間計算結果List, 主時間Map);
	}

	/**
	 * 用子時間map替换主時間Map
	 *
	 * @param 条件Map
	 * @param 主時間Map
	 * @param 子時間map
	 * @return
	 */
	private Map set子条件Map(Map 条件Map, Map<String, String> 主時間Map, Map 子時間map) {

		myLogger.printCallMessage(sCallPath,"MultiConditionCalc.set子条件Map()");

		// 【用子時間map替换主時間Map】
		// 在【条件Map】中，把【主時間map】之外的条件加入【新Map】
		// 然后，把【子時間map】也加入【新Map】

		Map local条件Map = new HashMap(条件Map);

		List 条件List = (List)条件Map.get(PublicName.KEY_条件);
		List new条件List = new ArrayList();

		for(Map 条件map : (List<Map>)条件List) {

			if(条件map == 主時間Map) {
				new条件List.add(子時間map);
			}else {
				new条件List.add(条件map);
			}
		}

		local条件Map.put(PublicName.KEY_条件, new条件List);
		return local条件Map;
	}

	// 就是从条件中把时间对象取出来
	private Map<String, String> 取得主時間Map_by条件Map(Map 条件Map) {

		myLogger.printCallMessage(sCallPath,"MultiConditionCalc.取得主時間Map_by条件Map()");

		//------------------------------------------
		// 任务从下面一堆条件中找到属于时间的条件
		//------------------------------------------
		// 条件Map:[
		//		{
		// 			値:
		// 			FORMAT:
		// 			目標:日期，XXX日，XXX时间
		// 			項目:
		// 			ID:
		// 			計算符号:
		// 		},
		//		{
		//			値:	2018-10-10 // 2018/10/10 // 2018/5/1 //
		//		},
		//		{
		//			...
		//		}
		時間対象 o時間対象 = new 時間対象(sCallPath+"取得主時間Map_by条件Map()");

		List 条件List = (List)条件Map.get(PublicName.KEY_条件);
		for(Map 条件map : (List<Map>)条件List) {
			// 以下情况都可以被视为时间对象
			// 1、其项目是时间项目（例，年月日）
			// 2、其值是时间值（例，20180515）

			String s時間単位 = (String)条件map.get(PublicName.KEY_項目);
			String s時間値 = (String)条件map.get(PublicName.KEY_値);
			if(o時間対象.is時間対象by単位(s時間単位) ||
					o時間対象.is時間対象by値(s時間単位, s時間値)) {
				return 条件map;
			}
		}
		// 否则、就是没找到
		return null;
	}

	private List<Map> 計算結果_根据条件信息Map(Map 条件Map) {

		myLogger.printCallMessage(sCallPath,"MultiConditionCalc.計算結果_根据条件信息Map()");

		// 計算每个条件式
		String s計算符 = null;
		String s値 = null;
		List<Map> 計算結果List = new ArrayList();

		String result = this.取得関係Str_by目標and条件項目(条件Map);

		if (result == PublicName.KEY_直接关系){

			計算結果List = this.取得結果_根据直接関係(条件Map);

		}else if (result == PublicName.KEY_程序关系){

			計算結果List = this.取得結果_Call程序数据執行器(条件Map);
		}else{
				// 告.诉.他.们：还.没有两个词条之间的计算方法。
				// return null;
		}

		return CollectionUtils.isEmpty(計算結果List)? null : 計算結果List;

	}
	/**
	 * 通.过.程序来计算结果
	 * @param 条件dto
	 * @return
	 */
	private List<Map> 取得結果_Call程序数据執行器(条件DTO 条件dto) {

		myLogger.printCallMessage(sCallPath,"MultiConditionCalc.取得結果_Call程序数据執行器()");

		// 通.过.程序来计算结果

		// 目標 例，目標=社员
		String s目標 = 条件dto.get目標();

		// 条件項目 例，条件項目=出国记录
		String s条件項目 = 条件dto.get項目();

		Map 検索条件 = new HashMap();
		検索条件.put(PublicName.KEY_入力1, 条件dto.get値());

		NOSQL nosql = new NOSQL(sCallPath+"取得結果_Call程序数据執行器()");

		return nosql.取得指定値_根据入力字典and出力字典and入力数据(s条件項目, s目標, 検索条件);
	}

	/**
	 *
	 * @param 条件Map
	 * @return
	 */
	private List<Map> 取得結果_Call程序数据執行器(Map 条件Map) {

		myLogger.printCallMessage(sCallPath,"MultiConditionCalc.取得結果_Call程序数据執行器()");

		// 通.过.程序来计算结果

		// 目標 例，目標=社员
		String s目標 = (String)条件Map.get(PublicName.KEY_目標);

		// 条件項目 例，条件項目=出国记录
		String s条件項目 = (String)条件Map.get(PublicName.KEY_項目);

		Map 検索条件 = new HashMap();
		検索条件.put(PublicName.KEY_入力1, (String)条件Map.get(PublicName.KEY_値));

		NOSQL nosql = new NOSQL(sCallPath+"取得結果_Call程序数据執行器()");

		return nosql.取得指定値_根据入力字典and出力字典and入力数据(s条件項目, s目標, 検索条件);
	}

	/**
	 *
	 * @param 条件dto
	 * @return
	 */
	private List<Map> 取得結果_根据直接関係(条件DTO 条件dto) {

		myLogger.printCallMessage(sCallPath,"MultiConditionCalc.取得結果_根据直接関係()");

		// 如果从条件到目标是直接的客户与业者的关系
		// 那么就直接计算结果

		String s計算符 = 条件dto.get計算符号();
		List<Map> 計算結果List = new ArrayList();
		String s開始値 = null;
		String s終了値 = null;

		switch(s計算符){

		case PublicName.KEY_等于:
						計算結果List = this.取得指定信息_根据詞条名and条件DTO(条件dto);
						break;
		case PublicName.KEY_大于	:
						s開始値 = 条件dto.get値();
						s終了値 = null;
						計算結果List.addAll(this.取得指定信息_根据詞条名and開始値and終了値(条件dto, s開始値,  s終了値));
						break;
		case PublicName.KEY_小于:
						s開始値 = null;
						s終了値 = 条件dto.get値();
						計算結果List.addAll(this.取得指定信息_根据詞条名and開始値and終了値(条件dto, s開始値,  s終了値));
						break;
		}
		return 計算結果List;

	}
	private List<Map> 取得結果_根据直接関係(Map 条件Map) {

		myLogger.printCallMessage(sCallPath,"MultiConditionCalc.取得結果_根据直接関係()");

		// 如果从条件到目标是直接的客户与业者的关系
		// 那么就直接计算结果

		String s計算符 = (String)条件Map.get(PublicName.KEY_計算符号);
		List<Map> 計算結果List = new ArrayList();
		String s開始値 = null;
		String s終了値 = null;

		switch(s計算符){

		case PublicName.KEY_等于:
						計算結果List = this.取得指定信息_根据条件Map(条件Map);
						break;
		case PublicName.KEY_like:
						THINGSdb db = new THINGSdb(PublicName.KEY_EMPTYBLANK);
						計算結果List =db.模糊検索_by指定値and対象詞条名((String)条件Map.get(PublicName.KEY_値), (String)条件Map.get(PublicName.KEY_項目));
						break;
		case PublicName.KEY_大于	:
						s開始値 = (String)条件Map.get(PublicName.KEY_値);
						s終了値 = null;
						計算結果List.addAll(this.取得指定信息_根据詞条名and開始値and終了値(条件Map, s開始値,  s終了値));
						break;
		case PublicName.KEY_小于	:
						s開始値 = null;
						s終了値 = (String)条件Map.get(PublicName.KEY_値);
						計算結果List.addAll(this.取得指定信息_根据詞条名and開始値and終了値(条件Map, s開始値,  s終了値));
						break;
		}
		return 計算結果List;

	}
	/**
	 * 区.間.値.的判断，需要在词条层独立一个逻辑了
	 * @param 条件dto
	 * @param s目標
	 * @param s終了値
	 * @return
	 */
	private List<Map> 取得指定信息_根据詞条名and開始値and終了値(条件DTO 条件dto, String s開始値, String s終了値) {

		myLogger.printCallMessage(sCallPath,"MultiConditionCalc.取得指定信息_根据詞条名and開始値and終了値(s開始値="+s開始値+",s終了値="+s終了値+")");

		// 区.間.値.的判断，需要在词条层独立一个逻辑了。
		// 例：取得 【生日】> 2000/01/01 AND【生日】< 2000/01/01 的技術者ID
		// (这个需要调取THINGSdb的基本功能)
		THINGSdb db = new THINGSdb(sCallPath + "取得指定信息_根据詞条名and開始値and終了値()");

		return db.区間検索_by開始値and終了値and条件DTO(s開始値, s終了値, 条件dto);
	}
	private List<Map> 取得指定信息_根据詞条名and開始値and終了値(Map 条件Map, String s開始値, String s終了値) {

		myLogger.printCallMessage(sCallPath,"MultiConditionCalc.取得指定信息_根据詞条名and開始値and終了値(s開始値="+s開始値+",s終了値="+s終了値+")");

		// 区.間.値.的判断，需要在词条层独立一个逻辑了。
		// 例：取得 【生日】> 2000/01/01 AND【生日】< 2000/01/01 的技術者ID
		// (这个需要调取THINGSdb的基本功能)
		THINGSdb db = new THINGSdb(sCallPath + "取得指定信息_根据詞条名and開始値and終了値()");

		return db.区間検索_by開始値and終了値and条件Map(s開始値, s終了値, 条件Map);
	}

	/**
	 * 例：取得 【生日】=2000/01/01 的技術者ID
	 * @param 条件dto
	 * @return
	 */
	private List<Map> 取得指定信息_根据詞条名and条件DTO(条件DTO 条件dto) {

		myLogger.printCallMessage(sCallPath,"MultiConditionCalc.取得指定信息_根据詞条名and条件DTO()");


		// 例：取得 【生日】= 2000/01/01 的技術者ID
		List<Map> sResultID_ListMap = new ArrayList();

		詞条 o詞条 = new 詞条(sCallPath + "取得指定信息_根据詞条名and条件DTO()");
		String s詞条id = o詞条.取得詞条ID_by詞条名(条件dto.get項目());
		// 这里计算的其实是 id = [s詞条id]的词条的，数据=[条件dto.getS値()]，的一个属性叫做[条件dto.getS項目()]的具体值。
		// 返回的是满足条件（項目的实体数据 == 値）的目標词条的实体数据ID的List
		List<String> sResultID_List = o詞条.検索顧客詞条数据採番idList_by業者詞条IDand業者実体数据and顧客詞条名(s詞条id, 条件dto.get値(), 条件dto.get目標());

		for(String id : sResultID_List){
			Map map = new HashMap();
			map.put(PublicName.KEY_ID, id);
			map.put(PublicName.KEY_詞条名, 条件dto.get目標());
			sResultID_ListMap.add(map);
		}
		return sResultID_ListMap;
	}
	private List<Map> 取得指定信息_根据条件Map(Map 条件Map) {

		myLogger.printCallMessage(sCallPath,"MultiConditionCalc.取得指定信息_根据条件Map()");


		// 例：取得 【生日】= 2000/01/01 的技術者ID
		List<Map> sResultID_ListMap = new ArrayList();

		詞条 o詞条 = new 詞条(sCallPath + "取得指定信息_根据条件Map()");
		String s詞条id = o詞条.取得詞条ID_by詞条名((String)条件Map.get(PublicName.KEY_項目));
		// 这里计算的其实是 id = [s詞条id]的词条的，数据=[条件dto.getS値()]，的一个属性叫做[条件dto.getS項目()]的具体值。
		// 返回的是满足条件（項目的实体数据 == 値）的目標词条的实体数据ID的List
		List<String> sResultID_List =
				o詞条.検索顧客詞条数据採番idList_by業者詞条IDand業者実体数据and顧客詞条名(
						s詞条id, (String)条件Map.get(PublicName.KEY_値), (String)条件Map.get(PublicName.KEY_目標));

		for(String id : sResultID_List){
			Map map = new HashMap();
			map.put(PublicName.KEY_ID, id);
			map.put(PublicName.KEY_詞条名, (String)条件Map.get(PublicName.KEY_目標));
			sResultID_ListMap.add(map);
		}
		return sResultID_ListMap;
	}
	/**
	 *
	 * @param 条件dto
	 * @return
	 */
	private String 取得関係Str_by目標and条件項目(条件DTO 条件dto) {

		myLogger.printCallMessage(sCallPath,"MultiConditionCalc.取得関係Str_by目標and条件項目()");

		// 例：取得 【技術者】 与 【生日】的关系
		//     如果 【技術者】的.业.者中有【生日】
		//	   如果 【生日】的业者中有【技術者】
		// 或
		//     如果 【技術者】To【生日】的程序存在
		//     如果 【生日】To【技術者】的程序存在
		//     满.足以上条件
		// 则.判断为有关系，可以计算。（前者为直接。后者为间接）
		//

		String s目標 = 条件dto.get目標();
		String s条件項目 = 条件dto.get項目();
		String s値 = 条件dto.get値();
		詞条 o詞条 = new 詞条(sCallPath + "取得関係Str_by目標and条件項目()");
		String s詞条id = o詞条.取得詞条ID_by詞条名(条件dto.get目標());
		// 原理：符合条件項目的词条例，满足条件的（实体数据 = s値）的ID对象的顧客情報List中，如有要找的目標词条的话就可以直接计算
		// 例，我把生日=【20170101】的所有使用它的词条全部取出来。
		//      因为一个词条与其他词条有所有可能性，但是，落实到具体一个数据就没那么放反了。
		// 返回[直接关系]即可
		顧客 o顧客 = new 顧客(sCallPath + "取得関係Str_by目標and条件項目()");
		List<数据DTO> 顧客情報List = o顧客.取得顧客数据DTOList_by詞条名and実体数据(s条件項目, s値);
		for(数据DTO 数据dto : 顧客情報List){
			if(数据dto.get詞条ID().equals(s詞条id)){
				return PublicName.KEY_直接关系;
			}
		}
		// 原理：符合条件項目的词条例，满足条件的（实体数据 = s値）的ID对象的顧客情報List中，如有要找的目標词条的话就可以直接计算
		// 返回[直接关系]即可
		業者 o業者 = new 業者(sCallPath + "取得関係Str_by目標and条件項目()");
		List<数据DTO> 業者情報List = o業者.取得業者数据DTOList_by詞条名and実体数据(s条件項目, s値);
		for(数据DTO 数据dto : 業者情報List){
			if(数据dto.get詞条ID().equals(s詞条id)){
				return PublicName.KEY_直接关系;
			}
		}

		//THINGSdb.取得客户业者信息_根据词条名(s目標)；
		//THINGSdb.取得客户业者信息_根据词条名(s条件项目)；

		return null;
	}
	private String 取得関係Str_by目標and条件項目(Map 条件Map) {

		myLogger.printCallMessage(sCallPath,"MultiConditionCalc.取得関係Str_by目標and条件項目()");


		// 例：取得 【技術者】 与 【生日】的关系
		//     如果 【技術者】的.业.者中有【生日】
		//	   如果 【生日】的业者中有【技術者】
		// 或
		//     如果 【技術者】To【生日】的程序存在
		//     如果 【生日】To【技術者】的程序存在
		//     满.足以上条件
		// 则.判断为有关系，可以计算。（前者为直接。后者为间接）
		//

		String s目標 = (String)条件Map.get(PublicName.KEY_目標);
		String s条件項目 = (String)条件Map.get(PublicName.KEY_項目);
		String s値 = (String)条件Map.get(PublicName.KEY_値);
		詞条 o詞条 = new 詞条(sCallPath+"取得関係Str_by目標and条件項目()");
		String s目標詞条id = o詞条.取得詞条ID_by詞条名((String)条件Map.get(PublicName.KEY_目標));
		String s業者詞条id = o詞条.取得詞条ID_by詞条名((String)条件Map.get(PublicName.KEY_項目));

		// 原理：符合条件項目的词条例，满足条件的（实体数据 = s値）的ID对象的顧客情報List中，如有要找的目標词条的话就可以直接计算
		// 例，我把生日=【20170101】的所有使用它的词条全部取出来。
		//      因为一个词条与其他词条有所有可能性，但是，落实到具体一个数据就没那么放反了。
		// 返回[直接关系]即可

//		顧客 o顧客 = new 顧客("");
//		List<数据DTO> 顧客情報List = o顧客.取得顧客数据DTOList_by詞条名and実体数据(s条件項目, s値);
//		if(顧客情報List == null) {
//			return null;
//		}
//
//		for(数据DTO 数据dto : 顧客情報List){
//			if(数据dto.get詞条ID().equals(s詞条id)){
//				return "直接关系";
//			}
//		}
//		// 原理：符合条件項目的词条例，满足条件的（实体数据 = s値）的ID对象的顧客情報List中，如有要找的目標词条的话就可以直接计算
//		// 返回[直接关系]即可
//		業者 o業者 = new 業者("");
//		List<数据DTO> 業者情報List = o業者.取得業者数据DTOList_by詞条名and実体数据(s条件項目, s値);
//		for(数据DTO 数据dto : 業者情報List){
//			if(数据dto.get詞条ID().equals(s詞条id)){
//				return "直接关系";
//			}
//		}

		// <20180806>
		// 不可重复检索。
		// 优化策略：如果在项目词条的客户中存在目标词条，即判断为直接关系。
		文件全路径 o文件全路径 = new 文件全路径();
		String s類型 = PublicName.KEY_顧客路径;
		String s顧客路径 = o文件全路径.取得対象文件全路径_by類型and詞条IDand数据ID(s類型, Arrays.asList(s業者詞条id, s目標詞条id));

		List<String> o文件夹List = 文件全路径.getDirectorys(s顧客路径);
		if(CollectionUtils.isEmpty(o文件夹List)) {
			return null;
		}

		for(String s文件夹 : o文件夹List) {
			File 顧客id数据一覧表File =new File(s文件夹);

			if(顧客id数据一覧表File.getName().indexOf(".")>0){
				continue;
			}
			if(StringUtils.equals(顧客id数据一覧表File.getName(), s目標詞条id)){
				return PublicName.KEY_直接关系;
			}
		}

		//THINGSdb.取得客户业者信息_根据词条名(s目標)；
		//THINGSdb.取得客户业者信息_根据词条名(s条件项目)；

		return null;
	}


	private List 取得目標_by数据采番IdList_計算情報DTO(List<String> 数据采番IdList, 計算情報DTO 計算情報dto) {

		myLogger.printCallMessage(sCallPath,"MultiConditionCalc.取得目標_by数据采番IdList_計算情報DTO()");

		List<Map> resultList = new ArrayList();
		/*
		社員Bean:id						数据采番IdList(1,2,3,4)			計算情報dto.目標.key = 社員Bean
			|
			|-----------id_List:姓名
			|-----------id_List:性別
			|-----------id_List:生年月日
			|-----------id_List:入社年月日
			|-----------id_List:契約種別
		*/

		String main目標 = 取得main目標Key_by計算情報DTO(計算情報dto);
		List<String> sub目標List = 取得sub目標List_by計算情報DTO(計算情報dto);

		// 暂时。假设它是1对1的关系。
		//     就是
		//		1个社員只有一个名字
		//		1个社員只有一个性別
		//		1个社員只有一个生年月日
		//		1个社員只有一个入社年月日
		//		1个社員只有一个契約種別
		return 取得結果List_by目標詞条名_数据采番IdList_sub目標List(main目標, 数据采番IdList, sub目標List);
	}

	private List 取得目標List_by数据采番IdList_計算情報Map(List<String> 数据采番IdList, Map 計算情報Map) {

		myLogger.printCallMessage(sCallPath,"MultiConditionCalc.取得目標List_by数据采番IdList_計算情報Map()");

		if(CollectionUtils.isEmpty(数据采番IdList)) {
			return null;
		}
		if(CollectionUtils.isEmpty(計算情報Map)) {
			return null;
		}
		List<Map> resultList = new ArrayList();

		String main目標 = 取得main目標Key_by計算情報Map(計算情報Map);
		List<String> sub目標List = 取得sub目標List_by計算情報DTO(計算情報Map);

		return 取得結果List_by目標詞条名_数据采番IdList_sub目標List(main目標, 数据采番IdList, sub目標List);
	}

	private List<Map> 取得結果List_by目標詞条名_数据采番IdList_sub目標List(String main目標詞条名, List<String> 数据采番IdList, List<String> sub目標List) {

		myLogger.printCallMessage(sCallPath,"MultiConditionCalc.取得結果List_by目標詞条名_数据采番IdList_sub目標List(main目標詞条名="+main目標詞条名+")");

		// 假设它是1对1的关系。
		List<Map> resultList = new ArrayList();
		詞条 o詞条 = new 詞条(sCallPath+"取得結果List_by目標_数据采番IdList_目標List");
		業者 o業者 = new 業者(sCallPath+"取得結果List_by目標_数据采番IdList_目標List");

		String s詞条ID = o詞条.取得詞条ID_by詞条名(main目標詞条名);
		for(String 数据采番Id : 数据采番IdList) {
			Map m = new HashMap();
			for(String sub目標 : sub目標List) {
				String sub目標詞条ID = o詞条.取得詞条ID_by詞条名(sub目標);

				List<数据DTO> 数据DTOlist = o業者.取得業者数据DTOList_by詞条IDand数据采番ID(s詞条ID, 数据采番Id);
				for(数据DTO 数据dto : 数据DTOlist) {
					if(数据dto.get詞条ID().equals(sub目標詞条ID)) {
						if(StringUtils.isEmpty(数据dto.get詞条ID())|| StringUtils.isEmpty(数据dto.get数据ID())) {
							continue;
						}
						String sValue = o詞条.取得実体数据_by詞条IDand数据採番ID(数据dto.get詞条ID(), 数据dto.get数据ID());
						if(StringUtils.isEmpty(sValue)) {
							continue;
						}
						// 如果是文件路径、即取其值，并返回采到值
						m.put(sub目標, 取得真実値Object_byValue(sValue));
					}
				}
			}
			resultList.add(m);
		}
		return resultList;
	}

	/**
	 * 如果是文件路径、即取其值，并返回采到值。20181016 追加
	 * @param sValue
	 * @return
	 */
	private Object 取得真実値Object_byValue(String sValue){

		myLogger.printCallMessage(sCallPath,"MultiConditionCalc.取得真実値Object_byValue(sValue="+sValue+")");


		// chk 是否文件
		File file = new File(sValue);
		if(file.exists()) {
			// 如果是
			MyFileReader myFileReader;
			if(file.length() > 1000000L) {
				myFileReader = new FileReader_BufferedReader();
			}else {
				myFileReader = new FileReader_MappedByteBuffer();
			}

			Scanner s = null;
			try {
				s = new Scanner(new File(sValue));
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}
			ArrayList<String> list = new ArrayList<String>();

			while (s.hasNext()){
			    list.add(s.next());
			}
			s.close();

			return list;

		}else {
			// 如果不是
			return sValue;
		}

	}

	private String 取得main目標Key_by計算情報DTO(計算情報DTO 計算情報dto) {

		myLogger.printCallMessage(sCallPath,"MultiConditionCalc.取得main目標Key_by計算情報DTO()");


		String sKey = null;
		for (Object key : 計算情報dto.get目標().keySet()) {
			sKey = (String) key;
		}
		return sKey;
	}

	private String 取得main目標Key_by計算情報Map(Map 計算情報Map) {

		myLogger.printCallMessage(sCallPath,"MultiConditionCalc.取得main目標Key_by計算情報Map()");

		String sKey = null;
		if(計算情報Map.get(PublicName.KEY_目標) instanceof Map) {
			for (Object key : ((Map)計算情報Map.get(PublicName.KEY_目標)).keySet()) {
				sKey = (String) key;
			}
		}
		if(計算情報Map.get(PublicName.KEY_目標) instanceof List) {
			for (Object key : (List)計算情報Map.get(PublicName.KEY_目標)) {
				sKey = (String) key;
			}
		}

		return sKey;
	}


	private List<String> 取得sub目標List_by計算情報DTO(計算情報DTO 計算情報dto) {

		myLogger.printCallMessage(sCallPath,"MultiConditionCalc.取得sub目標List_by計算情報DTO()");

		Map map = 計算情報dto.get目標();
		List<String> resultList = new ArrayList();

		for (Object value : map.values()) {
			System.out.println("value:"+value);
			if( value instanceof java.util.ArrayList) {
				for(Object subvalue : (java.util.ArrayList)value) {
					if( subvalue instanceof String) {
						resultList.add(subvalue.toString());
					}
				}
			}
		}

		return resultList;
	}

	private List<String> 取得sub目標List_by計算情報DTO(Map 計算情報Map) {

		myLogger.printCallMessage(sCallPath,"MultiConditionCalc.取得sub目標List_by計算情報DTO()");

		List<String> resultList = new ArrayList();

		if(計算情報Map.get(PublicName.KEY_目標) instanceof Map) {
			Map map = (Map)計算情報Map.get(PublicName.KEY_目標);

			for (Object value : map.values()) {
				System.out.println("value:"+value);
				if( value instanceof java.util.ArrayList) {
					for(Object subvalue : (java.util.ArrayList)value) {
						if( subvalue instanceof String) {
							resultList.add(subvalue.toString());
						}
					}
				}
			}
		}else if(計算情報Map.get(PublicName.KEY_目標) instanceof List) {
			resultList = new ArrayList((List)計算情報Map.get(PublicName.KEY_目標));
		}

		return resultList;
	}

/*	private List 計算_无条件関係(計算情報DTO 計算情報dto) {
		//无条件检索
		ID id = new ID("計算_无条件関係");
		詞条 o詞条 = new 詞条("計算_无条件関係");

		String sKey = 取得main目標_by計算情報DTO(計算情報dto);

		String sMain目標詞条id = o詞条.取得詞条ID_by詞条名(sKey);

		if (計算情報dto.get目標() == null || 計算情報dto.get目標().isEmpty()) {
		} else {
			return 取得目標_byIDList(id.検索数据采番ID_by詞条IDand実体数据(sMain目標詞条id, null), 計算情報dto);
		}

		return id.検索数据采番ID_by詞条IDand実体数据(sMain目標詞条id, null);

	}*/

	public List 計算_无条件関係(Map 計算情報Map) {

		myLogger.printCallMessage(sCallPath,"MultiConditionCalc.計算_无条件関係()");

		//无条件检索
		ID id = new ID(sCallPath+"計算_无条件関係");
		詞条 o詞条 = new 詞条(sCallPath+"計算_无条件関係");

		String sKey = 取得main目標Key_by計算情報Map(計算情報Map);

		String sMain目標詞条id = o詞条.取得詞条ID_by詞条名(sKey);

		String s目標 = null;
		if(計算情報Map.get(PublicName.KEY_目標) instanceof Map) {
			s目標 = ((Map)計算情報Map.get(PublicName.KEY_目標)).keySet().toString();
		}else if(計算情報Map.get(PublicName.KEY_目標) instanceof List) {
			s目標 = ((List)計算情報Map.get(PublicName.KEY_目標)).get(0).toString();
		}

		if (StringUtils.isEmpty(s目標)) {
		} else {
			return 取得目標List_by数据采番IdList_計算情報Map(id.検索数据采番ID_by詞条IDand実体数据(sMain目標詞条id, null), 計算情報Map);
		}

		return id.検索数据采番ID_by詞条IDand実体数据(sMain目標詞条id, null);

	}

	public List 計算_単条件関係(Map 計算情報Map) {

		myLogger.printCallMessage(sCallPath,"MultiConditionCalc.計算_単条件関係()");

		Map 条件Map = null;
		List 条件List = null;

		if (計算情報Map.get(PublicName.KEY_条件) instanceof Map) {
			条件Map = (Map)計算情報Map.get(PublicName.KEY_条件);
			if(CollectionUtils.isEmpty(条件Map)) {
				return null;
			}
		}

		if (計算情報Map.get(PublicName.KEY_条件) instanceof ArrayList) {
			条件List = (ArrayList)計算情報Map.get(PublicName.KEY_条件);
			if(CollectionUtils.isEmpty(条件List)) {
				return null;
			}
		}

		CRUDer oCRUDer = new CRUDer(sCallPath + "計算_単条件関係()");
		if(! oCRUDer.check条件(計算情報Map)) {
			return null;
		}

		List<Map> 計算結果List = this.計算結果_根据条件信息Map((Map)条件List.get(0));
		if(CollectionUtils.isEmpty(計算結果List)) {
			return null;
		}
		List<String> 数据采番IdList = new ArrayList();
		for(Map 計算結果 : 計算結果List) {
			数据采番IdList.add((String)計算結果.get(PublicName.KEY_ID));
		}

		return this.取得目標List_by数据采番IdList_計算情報Map(数据采番IdList, 計算情報Map);
	}
}
