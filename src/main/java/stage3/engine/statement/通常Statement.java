package stage3.engine.statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import stage3.engine.calc.計算器;
import stage3.engine.parse.計算式;
import stage3.nosql.NOSQL;

public class 通常Statement extends Statement {

	public 通常Statement(List<Map> 計算結果List) {
		this.計算結果List = 計算結果List;
	}

	public 通常Statement(List<Map> 計算結果MapList, Map<String, 計算式> 計算式Map, List<String> sKeyList) {
		this.計算結果List = 計算結果MapList;
		this.計算式Map = 計算式Map;
		this.sKeyList = sKeyList;
	}

	public List<Map> 計算式_計算(計算式 o計算式, Map<String, 計算式> 計算式Map, Map f臨時計算結果Map) {

		if(StringUtils.equals(o計算式.getTYPE(), "計算計算式_字典")){
			return 計算式_計算_辞書(o計算式, 計算式Map, f臨時計算結果Map);

		}else{
			return 計算式_計算_計算式(o計算式, 計算式Map, f臨時計算結果Map);

		}
	}

	public List<Map> 計算式_計算_辞書(計算式 o計算式, Map<String, 計算式> 計算式Map, Map f臨時計算結果Map) {
        final Logger logger = Logger.getLogger("SampleLogging");
//logger.info(o計算式.getSName() + " 通常Statement_計算式_計算 ");
//logger.info("   运算符 = "+o計算式.getS运算符());
//logger.info("   子計算 = "+o計算式.get子計算式List());

		//1.2.1 ■NOSQL.初始化NOSQL_根据条件数据and一条業務数据
		NOSQL nosql = new NOSQL("計算式_計算_辞書()");
		//1.2.2 ■NOSQL.取得指定数据_根据NOSQL_根据目标字典项目
		//        例。已知技術者ID=1，取得该技術者的年齢。
		//            技術者---生年月日---年齢
		//			  【替代案】：技術者---年齢

		// 入力情報List : 入力1 = 小伝馬町;入力2 = 浦安;
		//List<Map> 結果List = nosql.取得指定項目数据_根据条件値and出力項目List(入力情報Map, 出力辞書項目List);
		// 結果List =
		// Map1<>=小伝馬町 ,,,1,,,浦安
		// Map2<>=小伝馬町 ,,,2,,,浦安
		// Map3<>=小伝馬町 ,,,3,,,浦安

		List<Map> 結果MapList = new ArrayList<Map>();

		String 入力字典 = o計算式.getNosql入力字典().getS入力字典();
		String 出力字典 = o計算式.getNosql入力字典().getS出力字典();
		//String 入力項目 = o計算式.getNosql入力字典().getS入力項目();
		String 外部入力 = o計算式.getNosql入力字典().getS外部入力();
		//String 出力項目 = o計算式.getNosql入力字典().getS出力項目();

		//if(入力項目!=null && 外部入力!=null){
		if(外部入力!=null){
			/*
			 * 例
			 * 1， C1， 出力词条， 基站    //计算式之后两种：1，字典计算
			 * 2， C1， 入口，    入力1   //每个SDP必须有最少一个入口
			 */

			/**
			 * 字典内部检索。例。用字典项目的具体值找到其采番ID。
			 *
			 */
			String value = 外部入力;
			Map<String, String> map = new HashMap<String, String>();
			map.put(出力字典, value);

			// 这里的NOSQL还得继续改造。 2018-12-11
			// List<Map> 字典出力情報MapList = nosql.取得辞書IDList_根据入出力字典and入力項目and入力値List(出力字典, 入力項目, map);
			List<Map> 字典出力情報MapList = Arrays.asList(map);
			結果MapList = 取得指定MapList_by計算式_指定字典ID_結果MapList(o計算式, 出力字典, 字典出力情報MapList);

		}else{
			/**
			 * 字典之间的暧昧检索。
			 *
			 */
			/*
			 * 例
			 * 5， D1， 出力词条， 相邻基站
			 * 6， D1， 入力词条， C1     // 入力对象的字典，让他们去子计算式中去找吧
			 *
			 * 注，比较虐心的是需要通过入力词条对应的值，例C1，找到【入力词条】和它的值到底是什么。这段代码还没有写。
			 *     都是为了建辉SDP的代码维度，及他的可读性。自己刚开始看之前写的SDP真的是看不太懂了。 2018-12-11 22:00
			 */

			String o計算式_Key = 計算式Map.get(o計算式.getSName()).getSKey();
			String sValue = (String) f臨時計算結果Map.get(o計算式_Key);

			if(sValue != null){
				結果MapList.add(f臨時計算結果Map);
			}else{
				List<Map> NOSQL出力結果 = new ArrayList();
				List<Map> 字典入力情報MapList = NOSQL计算_根据計算式_計算式Map_臨時計算結果Map_NOSQL出力結果(o計算式, 計算式Map, f臨時計算結果Map, NOSQL出力結果);

				//NOSQL出力結果.add(入力情報Map);
				for(Map 字典入力情報Map : 字典入力情報MapList){
					//字典ID，  Value1
					//字典ID，  Value2
					//字典ID，  Value3
					//字典ID，  Value4
					//字典ID，  Value5
					結果MapList = nosql.取得指定値_根据入力字典and出力字典and入力数据(入力字典, 出力字典, 字典入力情報Map);
					if(判断NOSQL是否存在返回值(結果MapList)){
						return null;
					}
					NOSQL出力結果 = 做成新的MapList_by旧的ListMap_計算式_出力字典ID_結果MapList(NOSQL出力結果, o計算式, 出力字典, 結果MapList);
				}

				結果MapList = NOSQL出力結果;

			}
		}
//logger.info("   結果List = "+結果List);

		return 結果MapList;

	}

	public List<Map> 計算式_計算_計算式(計算式 o計算式, Map<String, 計算式> 計算式Map, Map f臨時計算結果Map) {
        final Logger logger = Logger.getLogger("SampleLogging");
		//logger.info(o計算式.getSName() + " 通常Statement_計算式_計算 ");
		//logger.info("   計算式.运算符 = "+o計算式.getS运算符());
		//logger.info("   計算式.子計算 = "+o計算式.get子計算式List());

		//1 ■取得指定計算器_根据操作符号
		計算器 o計算器 = this.取得指定计算器_根据操作符号(o計算式.getS运算符());

		//2 ■程序数据執行器 .執行計算項目_根据計算項目List()
		// 例：入力情報List = {出発駅=小伝馬町, 到着駅=浦安}
		// Map1<>=小伝馬町 ,,,1,,,浦安
		// Map2<>=小伝馬町 ,,,2,,,浦安
		// Map3<>=小伝馬町 ,,,3,,,浦安

		List<Map> 子計算式結果List = this.執行計算項目_根据計算項目List(o計算式.get子計算式名List(), 計算式Map, f臨時計算結果Map);
		//logger.info("子計算式結果List = "+子計算式結果List);
		/**
		 * 这，属于程序的自纠错功能，如果目标计算式没有结果，该怎么办
		 */

		if(CollectionUtils.isEmpty(子計算式結果List)|| f臨時計算結果Map.containsKey("RETURN")|| 子計算式結果List.get(0).containsKey("RETURN")){
			//放弃本次计算。但還没完。
			f臨時計算結果Map = RETURNs(o計算式, 計算式Map, f臨時計算結果Map);
			List<Map> returnMap = new ArrayList();
			returnMap.add(f臨時計算結果Map);
			return returnMap;
		}
				// 3 分別計算_根据計算項目List
		List<Map> s結果List = new ArrayList<Map>();


		// Map1<>=小伝馬町 ,,,1,,,浦安
		// Map2<>=小伝馬町 ,,,2,,,浦安
		// Map3<>=小伝馬町 ,,,3,,,浦安
		for(Map 子計算式結果 : 子計算式結果List){
//logger.info(o計算式.getSName() + "の個別計算 ");
//logger.info("    子計算式結果List = [ size = " + 子計算式結果List.size()+" ]" + 子計算式結果List);
//logger.info("    子計算式結果 = " + 子計算式結果);
			Map 個別計算Map = 取得子計算式数据Map_根据子計算式結果and計算式(o計算式, 計算式Map, 子計算式結果, f臨時計算結果Map);

//logger.info("    個別計算运算符 = " + o計算式.getS运算符());
//logger.info("    個別計算対象 = " + 個別計算Map);
			//3.1 ■計算器.個別計算_根据計算項目List
			String s個別計算結果 = (String)o計算器.個別計算_根据計算項目List(個別計算Map, f臨時計算結果Map, o計算式, 計算式Map);
//logger.info("    個別計算結果 = " + s個別計算結果);
//logger.info("\r\n");
			//中間結果は<現在の行No, sResult>
			子計算式結果.put(o計算式.getSKey(), s個別計算結果);
			s結果List.add(new HashMap(子計算式結果));

		}
		return s結果List;
	}

}
