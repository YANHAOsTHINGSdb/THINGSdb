package stage3.engine.statement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stage3.engine.bean.計算中間結果DTO;
import stage3.engine.parse.計算式;
import stage3.engine.tool.Local結果;
import stage3.engine.tool.implement.新的local結果List;

public class ForStatement extends Statement {
	/**
	 *
	 * @param o計算式2
	 * @param 入力情報List<>
	 * @return
	 */

	List<Map> oFOR項目;
	int i今回 = 0;

	public ForStatement(計算中間結果DTO o計算中間結果) {
		super.o計算中間結果 = o計算中間結果;
	}


	public ForStatement(List<Map> 計算結果List) {
		this.計算結果List = 計算結果List;
	}


	public ForStatement(List<Map> 計算結果List, Map<String, 計算式> 計算式Map, List<String> sKeyList) {
		this.計算結果List = 計算結果List;
		this.計算式Map = 計算式Map;
		this.sKeyList = sKeyList;
	}


	public List<Map> 計算式_計算(計算式 o計算式, Map<String, 計算式> 計算式Map, Map 入力情報Map) {
//        final Logger logger = Logger.getLogger("SampleLogging");
//        logger.info(o計算式.getSName() + " ForStatement_計算式_計算 ");
//        logger.info("計算式.运算符 = "+o計算式.getS运算符());
//        logger.info("計算式.子計算 = "+o計算式.get子計算式List());
//        logger.info("\r\n");

if(o計算式.getSName().equals("b4311")){
	o計算式 = o計算式;
}
//		計算器 o計算器 = this.取得指定计算器_根据操作符号(o計算式.get操作符号());
//
//		//2 ■程序数据執行器 .執行計算項目_根据計算項目List()
//		// 例：入力情報List = {出発駅=小伝馬町, 到着駅=浦安}
//		// Map1<>=小伝馬町 ,,,1,,,浦安
//		// Map2<>=小伝馬町 ,,,2,,,浦安
//		// Map3<>=小伝馬町 ,,,3,,,浦安
//
//		List<Map> paramList = this.執行計算項目_根据計算項目List(o計算式.get計算項目List(), 入力情報List);
//
//		//3 分別計算_根据計算項目List
//		List<Map> s結果List = new ArrayList<Map>();
//
//
//		// Map1<>=小伝馬町 ,,,1,,,浦安
//		// Map2<>=小伝馬町 ,,,2,,,浦安
//		// Map3<>=小伝馬町 ,,,3,,,浦安
//		for(Map map : paramList){
//			//3.1 ■計算器.個別計算_根据計算項目List
//			String sResult = o計算器.個別計算_根据計算項目List(map);
//			//中間結果は<現在の行No, sResult>
//			map.put(o計算式.現在の行No, sResult);
//			s結果List.add(map);
//		}
//
//		return s結果List;
//		return 入力情報List;

		/**
			b43		計算			FOR計算器
			b43		主（FOR項目）	b432
			b43		次（FOR出力）	b431
		*/
if(o計算式.getSName().equals("d43")){
	o計算式 = o計算式;
}

		String oFOR項目_計算式Name = o計算式.get子計算式名List().get(0); //主=FOR項目計算器
		String oFOR出力_計算式Name = null;
		if(o計算式.get子計算式名List().size()>1){
			oFOR出力_計算式Name = o計算式.get子計算式名List().get(1); //次=循環対象
		}

		List<Map> FOR項目List = getFOR項目(計算式Map.get(oFOR項目_計算式Name),計算式Map, new HashMap(入力情報Map));
		//【FOR項目】的計算结果也要加到【計算結果List】中，否則循环结束时会被重新計算。
//		super.計算結果List = 生成新的local結果List(super.計算結果List, FOR項目List, 計算式Map.get(oFOR項目_計算式Name));


		//1 生一个新List，用来保存每次计算的最新结果
		List<Map> s結果List = new ArrayList<Map>();

		for(Map FOR項目 : FOR項目List){
	        // map2中含有和map1中相同的key，那么执行如下方法之后
	        // map2中的值会覆盖掉map1中的值

			// 怎么才能每次取一个值呢
			// 答：将b432的key与値放入FOR項目中即可。留給后世子孙用吧。
//			String sKey = 計算式Map.get(oFOR項目_計算式Name).getSKey();
			Map for_入力情報Map = new HashMap(入力情報Map);
			for_入力情報Map.putAll(FOR項目);
if(o計算式.getSName().equals("d432")){
	o計算式 = o計算式;
}
//logger.info(o計算式.getSName()+ " FOR項目List = "+FOR項目List);
//logger.info("   今回のFOR項目 = "+FOR項目);
//logger.info("\r\n");
			//需要加一个FOR計算器结果
			for_入力情報Map.put(o計算式.getSKey(), o計算式.getS运算符());

			//打个补丁，将不足的加进入
			//List<Map> local結果List = new ArrayList();
			Local結果 local結果 = new 新的local結果List(計算式Map);
			super.計算結果List = local結果.生成新的local結果List(super.計算結果List, for_入力情報Map, o計算式);

			//将【d432】的也放入for_入力情報Map中
			//for_入力情報Map.put(計算式Map.get(oFOR項目_計算式Name).getSKey(), FOR項目.entrySet().iterator().toString());

			List<Map> 今回結果List = super.計算式_計算(計算式Map.get(oFOR出力_計算式Name),計算式Map, new HashMap(for_入力情報Map));

			//打个补丁，将不足的加进入
			//List<Map> local結果List = new ArrayList();
//			for(Map 今回結果Map:今回結果List){
//				//local結果List = 制作新結果(for_入力情報Map, 今回結果Map, local結果List);
//				今回結果Map.put(o計算式.getSKey(), o計算式.getS运算符());
//			}

/**
 * 这，属于程序的自纠错功能，如果目标计算式没有结果，该怎么办
 */

if(今回結果List == null || 今回結果List.isEmpty() || 入力情報Map.containsKey("RETURN")|| 今回結果List.get(0).containsKey("RETURN")){
	//删除 空结果关联的数据
	//无效。理由是还没有加进去
	//super.計算結果List = local結果.削除関連数据(計算結果List,for_入力情報Map);
	continue;
}
			if(! 今回結果List.equals(super.計算結果List)){
				super.計算結果List = local結果.生成新的local結果List(super.計算結果List, 今回結果List, o計算式);
			}


			//防止再次计算，所以也会把 【FOR計算器】=d43 的也放入 20180514

			//super.計算結果List = 生成新的local結果List(super.計算結果List, super.計算結果List, 計算式Map.get(oFOR出力_計算式Name));
		}

		return super.計算結果List;

	}

//
//	private List<Map> 生成新的local結果List_For(List<Map> 全体結果List, List<Map> 今回結果List, 計算式 o計算式) {
//		final Logger logger = Logger.getLogger("SampleLogging");
//		List<Map> local結果List = new ArrayList<Map>();
//		// local結果List =
//		// 結果1<k1,V1> <k2,V2> <k3,V3> <k4,V4> <k5,V5>
//		// 結果2<k1,V11><k2,V21><k3,V31><k4,V41><k5,V51>
//		// 結果3<k1,V12><k2,V22><k3,V32><k4,V42><k5,V52>
//		//
//if (o計算式.getSName().equals("b1")) {
//	o計算式 = o計算式;
//}
//if(o計算式.getSName().equals("b4311")){
//	o計算式 = o計算式;
//}
//		if (今回結果List == null) {
//			return 全体結果List;
//		}
//
//		if (全体結果List == null || 全体結果List.isEmpty()) {
//
//			// 1 循環執行【今回結果List】中每个Map
//			for (Map 今回結果Map : 今回結果List) {
//
//				local結果List = 制作新結果_For(new HashMap(), 今回結果Map, local結果List);
//			}
//
//		}else{
//
//			logger.info(o計算式.getSName() + "   生成新的local結果List ");
//			logger.info("      全体結果Map = [size=" + 全体結果List.size() + "] " + 全体結果List);
//			logger.info("      今回結果Map = [size=" + 今回結果List.size() + "] " + 今回結果List);
//
//			// 1.1 循環執行【全体結果List】中每个Map
//			for (Map 全体結果Map : 全体結果List) {
//
//				// 1 循環執行【今回結果List】中每个Map
//				for (Map 今回結果Map : 今回結果List) {
//
//					local結果List = 制作新結果_For(全体結果Map, 今回結果Map, local結果List);
//				}
//			}
//		}
//
//		return 减肥計画(local結果List);
//	}
//
//
//	private List<Map> 制作新結果_For(Map 全体結果Map, Map 今回結果Map, List<Map> local結果List) {
//		final Logger logger = Logger.getLogger("SampleLogging");
//
//		local結果List = new ArrayList(local結果List);
//
//		全体結果Map = new HashMap(全体結果Map);
//		今回結果Map = new HashMap(今回結果Map);
//
//		// logger.info(" 取得类型 = "+取得类型(全体結果Map,今回結果Map));
//		// logger.info("\r\n");
//		switch (取得类型(全体結果Map, 今回結果Map)) {
//		case "0":
//		case "1":
//		case "2":
//		case "5":
//			今回結果Map.putAll(全体結果Map);
//			local結果List.add(今回結果Map);
//			break;
//
//		case "3":
//			Map map3 = 取得差分(全体結果Map, 今回結果Map);
//			全体結果Map.putAll(map3);
//			今回結果Map.putAll(map3);
//			local結果List.add(全体結果Map);
//			local結果List.add(今回結果Map);
//			break;
//
//		case "4":
//			local結果List.add(全体結果Map);
//			local結果List.add(今回結果Map);
//			break;
//
//		}
//
//		return local結果List;
//	}


	/**
	 *
	 * @param o条件計算式
	 * @param 入力情報List
	 * @return
	 */
	private List<Map> getFOR項目(計算式 o条件計算式, Map<String, 計算式> 計算式Map, Map 入力情報Map) {

		//从入力情報Map中抽取对象的MAP

		// o条件計算式 = d3
		//"20","b432","計算","FOR項目計算器"
		//"21","b432","主","d5"

		String sb1Name = o条件計算式.get子計算式名List().get(0);
		String sb1Key = 計算式Map.get(sb1Name).getSKey();

		List<Map> for項目_結果List = null;

		if(入力情報Map.get(sb1Key) == null){
			計算式 ob1計算式 = 計算式Map.get(sb1Name);
			for項目_結果List = super.計算式_計算(ob1計算式,計算式Map, new HashMap(入力情報Map));
		}else{
			for項目_結果List = super.計算結果List;
		}

		return getFOR項目_from項目_結果List(o条件計算式,for項目_結果List, sb1Key);
	}

	/**
	 *
	 * @param o条件計算式
	 * @param for項目_結果List
	 * @param sb1Key
	 * @return
	 */
	private List<Map> getFOR項目_from項目_結果List(計算式 o条件計算式,List<Map> for項目_結果List, String sb1Key) {

		//
		List<Map> localFOR項目 = new ArrayList();
		Map new_Key_重複 = new HashMap();

		for(Map m:for項目_結果List){

			Map new_Key = new HashMap();

			String sfor項目 = (String)m.get(sb1Key);
			if(sfor項目 == null){
				sfor項目 = sfor項目;
				return localFOR項目;
			}
			String[] sfor項目配列 = sfor項目.split(",");
			if(sfor項目配列.length > 1){
				// 如果对象值是【SSS,SSS,SSS,SSS】的形式。
				// 就拆分输出MAP
				for(int i = 0; i<sfor項目配列.length; i++){
					localFOR項目.add(作成MAP_By_KeyANDValue(o条件計算式.getSKey(),sfor項目配列[i]));
				}
			}else{
				// 如果对象值是【LIST<Map>】的形式。
				// 就分别输出MAP
				if(new_Key_重複.get(m.get(sb1Key)) == null){
					new_Key_重複.put(m.get(sb1Key),sb1Key);
					new_Key.put(o条件計算式.getSKey(), m.get(sb1Key));
					localFOR項目.add(new_Key);
				}
			}
		}

		return localFOR項目;
	}

	/**
	 *
	 * @param sb1Key
	 * @param sValue
	 * @return
	 */
	private Map 作成MAP_By_KeyANDValue(String sb1Key, String sValue) {
		Map new_Key = new HashMap();
		new_Key.put(sb1Key, sValue);
		return new_Key;
	}


}
