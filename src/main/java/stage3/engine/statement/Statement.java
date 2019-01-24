package stage3.engine.statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.util.CollectionUtils;

import lombok.Data;
import stage3.engine.bean.計算中間結果DTO;
import stage3.engine.bean.辞書項目DTO;
import stage3.engine.calc.計算器;
import stage3.engine.calc.implement.List追加計算器;
import stage3.engine.calc.implement.SumEvery計算器;
import stage3.engine.calc.implement.与関係計算器;
import stage3.engine.calc.implement.交集判断計算器;
import stage3.engine.calc.implement.交集取得計算器;
import stage3.engine.calc.implement.加計算器;
import stage3.engine.calc.implement.大于等于計算器;
import stage3.engine.calc.implement.大于計算器;
import stage3.engine.calc.implement.小于等于計算器;
import stage3.engine.calc.implement.小于計算器;
import stage3.engine.calc.implement.等于計算器;
import stage3.engine.calc.implement.非空計算器;
import stage3.engine.parse.計算式;
import stage3.engine.tool.implement.Local結果Base;
import stage3.nosql.NOSQL;

@Data
public class Statement  extends Local結果Base{

	private NOSQL nosql = new NOSQL("Statement");

	protected List<Map> 計算結果List = null;

	private String s出力結果Key = null;

	計算中間結果DTO o計算中間結果 = new 計算中間結果DTO();

	/**
	 *
	 * @param o計算式2
	 * @param 入力情報List<>
	 * @return
	 */
	protected List<Map> 計算式_計算(
								計算式 o計算式,
								Map<String, 計算式> 計算式Map,
								Map f計算結果Map)
	{

		/**
		 * 	■計算式_計算(o計算式 : 計算式)
			1 ■取得指定計算器_根据操作符号
			2 ■程序数据執行器.執行計算項目_根据計算項目List(計算式.計算項目List) 返回List<Map>
			3 分別計算_根据計算項目List
			3.1 ■計算器.個別計算_根据計算項目List
		 */

		switch (o計算式.getS运算符()) {
		// 如果是IF控制语句
		case ("IF"):
		case ("IF計算器"):

			IfStatement oIfStatement = new IfStatement(計算結果List,計算式Map,this.getSKeyList());
			return oIfStatement.計算式_計算(o計算式, 計算式Map, f計算結果Map);

		// 如果是FOR控制语句
		case ("FOR"):
		case ("FOR計算器"):
		case ("FOR項目計算器"):
		case ("FOR出力計算器"):

			ForStatement oForStatement = new ForStatement(
												計算結果List,
												計算式Map,
												this.getSKeyList()
											);
			return oForStatement.計算式_計算(o計算式, 計算式Map, f計算結果Map);

		case ("出力"):

			s出力結果Key = 計算式Map.get(o計算式.get子計算式名List().get(0)).getSKey();
			return null;
		default:

			通常Statement o通常Statement = new 通常Statement(
													計算結果List,
													計算式Map,
													this.getSKeyList()
												);
			return o通常Statement.計算式_計算(o計算式, 計算式Map, f計算結果Map);

		}

	}

	/**
	 *
	 * @param 計算項目List
	 * @param 入力情報Map
	 * @return
	 */

	protected List<Map> 執行計算項目_根据計算項目List(
		List<String> 子計算式List,
		Map<String, 計算式> 計算式Map,
		Map 临时计算结果Map)
	{
		final Logger logger = Logger.getLogger("SampleLogging");

		// ■執行計算項目_根据計算項目List(計算式.計算項目List) 返回List<Map>
		// 1 循環計算項目List
		// 1.1 如果是計算式
		// 1.1.1 ■程序数据執行器.計算式_計算(o計算式 : 計算式) 返回List<Map>
		// 1.2 如果是字典项目
		// 1.2.1 ■NOSQL.初始化NOSQL_根据1条件数据2一条業務数据
		// 1.2.2 ■NOSQL.取得指定数据_根据NOSQL_根据目标字典项目 返回List<Map>

		// Map1<>=小伝馬町 ,,,1,,,浦安
		// Map2<>=小伝馬町 ,,,2,,,浦安
		// Map3<>=小伝馬町 ,,,3,,,浦安
		List<Map> 計算項目結果List = new ArrayList<Map>();

		// 1 循環計算項目List
		for (String o計算式Name : 子計算式List) {

			// 本次子計算式
			計算式 o計算式 = 計算式Map.get(o計算式Name);

			// 如果已经计算完毕
			if (临时计算结果Map.get(o計算式.getSKey()) != null) {
				// 将信息加入到「計算項目結果List」中
				// 将结果内部存储在【計算項目結果List】中
				更新計算項目結果List_by临时计算结果Map_計算式(計算項目結果List,
												临时计算结果Map,
												o計算式);
			}

			// 1.1 如果是計算式
			else if (計算式Map.get(o計算式Name).getTYPE().equals("計算計算式_計算器")) {
				// 利用【程序执行器】计算结果
				//     可能是【IfStatement】
				//     可能是【ForStatement】
				//     也可能是【通常Statement】
				// 将结果内部存储在【計算項目結果List】中
				執行計算項目_处理_計算式(計算項目結果List,
											計算式Map,
											o計算式,
											临时计算结果Map);

			}
			// 1.2 如果是字典项目
			else if (計算式Map.get(o計算式Name).getTYPE().equals("計算計算式_字典")) {
				// 通过NOSQL取得外部数据
				計算項目結果List = 執行計算項目_处理_字典项目(
												計算式Map,
												o計算式Name,
												o計算式 ,
												临时计算结果Map);

			}
		}

		// 减肥計画
		return 减肥計画(計算項目結果List);

	}

	/**
	 * 给子类的主函数调用的。自己并不直接调用，
	 * @param 計算式Map 保留所有计算式，用C1，C2，B1，B2来区别他们
	 * @param o計算式Name 本次的计算对象
	 * @param o計算式 本次计算对对象的实体 o計算式其实记录着计算式之间的关系的，例子计算式啥的
	 * @param 临时计算结果Map 到目前为止的计算结果
	 * @return
	 */
	private List<Map> 執行計算項目_处理_字典项目(
		Map<String, 計算式> 計算式Map,
		String o計算式Name,
		計算式 o計算式,
		Map 临时计算结果Map)
	{
		// 1.2.1 ■NOSQL.初始化NOSQL_根据条件数据and一条業務数据
		nosql = new NOSQL("執行計算項目_根据計算項目List()");
		// 1.2.2 ■NOSQL.取得指定数据_根据NOSQL_根据目标字典项目
		// 例。已知技術者ID=1，取得该技術者的年齢。
		// 技術者---生年月日---年齢
		// 【替代案】：技術者---年齢


		// 用计算式Name从计算式Map中取得取得计算式的相关信息 2018-12-12
		String 入力字典ID = 計算式Map.get(o計算式Name).getNosql入力字典().getS入力字典();
		String 出力字典ID = 計算式Map.get(o計算式Name).getNosql入力字典().getS出力字典();

		String o計算式_Key = 計算式Map.get(o計算式.getSName()).getSKey();
		String sValue = (String) 临时计算结果Map.get(o計算式_Key);
		List<Map> 結果MapList = new ArrayList<Map>();

		if (sValue != null) {
			結果MapList.add(临时计算结果Map);
		} else {
			// 入力情報Map= 入力1。。。value
			// 入力2。。。value
			// 入力3。。。value
			List<Map> NOSQL出力結果MapList = new ArrayList();

			// 用NOSQL取得的检索结果
			// NOSQL出力結果MapList【入】【出】
			List<Map> 字典入力情報MapList = NOSQL计算_根据計算式_計算式Map_臨時計算結果Map_NOSQL出力結果(
																					o計算式,
																					計算式Map,
																					临时计算结果Map,
																					NOSQL出力結果MapList);

			for (Map 字典入力情報Map : 字典入力情報MapList) {


				結果MapList = nosql.取得指定値_根据入力字典and出力字典and入力数据(
																		入力字典ID,
																		出力字典ID,
																		字典入力情報Map);
				/**
				 * 根据NOSQL所在的地点来判断影响范围。
				 * 如果在FOR循环里，且FOR循环式已经确立。那就不用强行退出。
				 * 如果在开始就找不到，那干脆退出好了。20180516
				 */
				if(判断NOSQL是否存在返回值(結果MapList)){
					return null;
				}

				// 绕开绕去。就是为了将计算的结果存入【計算項目結果List】
				NOSQL出力結果MapList = 做成新的MapList_by旧的ListMap_計算式_出力字典ID_結果MapList(
																				NOSQL出力結果MapList,
																				o計算式,
																				出力字典ID,
																				結果MapList);
			}

			結果MapList = NOSQL出力結果MapList;
		}
		return 結果MapList;

	}

	private void 執行計算項目_处理_計算式(
		List<Map> 計算項目結果List,
		Map<String, 計算式> 計算式Map,
		計算式 o計算式,
		Map 临时计算结果Map)
	{
		// 1.1.1 ■程序数据執行器.計算式_計算(o計算式 : 計算式)
		// 業務数据[辞書ID, 採番ID]
		List<Map> 結果List = this.計算式_計算(o計算式, 計算式Map, 临时计算结果Map);

		計算項目結果List = 生成新的local結果List(計算項目結果List, 結果List, o計算式);

		/**
		 * 这，属于程序的自纠错功能，如果目标计算式没有结果，该怎么办
		 */
		if(計算項目結果List.isEmpty()
			|| 临时计算结果Map.containsKey("RETURN")
			|| 計算項目結果List.get(0).containsKey("RETURN")){

			//放弃本次计算。但還没完。
			临时计算结果Map = RETURNs(o計算式, 計算式Map, 临时计算结果Map);
			List<Map> returnMap = new ArrayList();
			returnMap.add(临时计算结果Map);
		}
		// return 結果List;

	}

	private void 更新計算項目結果List_by临时计算结果Map_計算式(
									List<Map> 計算項目結果List,
									Map 临时计算结果Map,
									計算式 o計算式)
	{

		Map 入力Map = new HashMap();

		入力Map.put(o計算式.getSKey(), 临时计算结果Map.get(o計算式.getSKey()));

		if(計算項目結果List.isEmpty()){

			計算項目結果List.add(入力Map);

		}else{
			for(Map m : 計算項目結果List){
				m.putAll(入力Map);
			}
		}

		//計算項目結果List.add(入力情報Map);

	}


	/**
	 * 这个函数有两个出力。
	 *
	 * @param o計算式             【入】
	 * @param 計算式Map           【入】
	 * @param 臨時計算結果Map     【入】
	 * @param NOSQL出力結果       【入】【出】
	 * @return 字典入力情報MapList 【出】
	 */
	protected List<Map> NOSQL计算_根据計算式_計算式Map_臨時計算結果Map_NOSQL出力結果(
						計算式 o計算式, 					// 本次计算对象
						Map<String, 計算式> 計算式Map, 		// 所有计算对象
						Map 臨時計算結果Map, 				// 临时计算结果（现存条件）
						List<Map> NOSQL出力結果MapList		// 内部出力用（NOSQL出力結果）
	) {

		// 入力情報Map= 入力1。。。value
		// 入力2。。。value
		// 入力3。。。value

		List<Map> 字典入力情報MapList = new ArrayList();

		// 出力条件は唯一一個と想定する。
		Map NOSQL出力情報Map = new HashMap();

		int keyNo = 1;
		// 针对計算式每个子计算式
		for (String s子計算式Name : o計算式.get子計算式名List()) {
			// 从【本次所有计算式】中取得对应的计算时实体
			計算式 o子計算式 = 計算式Map.get(s子計算式Name);
			// 看看该计算式有没有计算过
			if (臨時計算結果Map.get(o子計算式.getSKey()) == null) {

				//【尚未计算】
				// 字典入力情報MapList 【入】【出】
				// NOSQL出力情報Map   【入】【出】
				計算式_計算_字典入力情報MapList_根据計算式_計算式Map_臨時計算結果Map_keyNo(
																		o子計算式,
																		計算式Map,
																		臨時計算結果Map,
																		字典入力情報MapList,
																		NOSQL出力情報Map,
																		keyNo);

			} else {

				//【已经计算】
				// 字典入力情報MapList 【入】【出】
				// NOSQL出力情報Map   【入】【出】
				更新MapList和Map_根据計算式_臨時計算結果Map_旧MapList_旧Map_keyNo(
																		o子計算式,
																		臨時計算結果Map,
																		字典入力情報MapList,
																		NOSQL出力情報Map,
																		keyNo);
			}
			keyNo++;
		}

		// 【NOSQL出力情報Map】计算之后，还是要和【臨時計算結果Map】相结合
		for(String s子計算式Name :o計算式.get子計算式名List()){

			if(CollectionUtils.isEmpty(計算式Map.get(s子計算式Name).get子計算式名List())) {
				break;
			}
			String s孫計算式Name = 計算式Map.get(s子計算式Name).get子計算式名List().get(0);
			if (s孫計算式Name != null){
				String skey = 計算式Map.get(s孫計算式Name).getSKey();
				NOSQL出力情報Map.put(skey, 臨時計算結果Map.get(skey));
			}
		}
		NOSQL出力結果MapList.add(NOSQL出力情報Map);
		return 字典入力情報MapList;
	}

	//【已经计算】
	/**
	 *
	 * @param o子計算式
	 * @param 臨時計算結果Map
	 * @param 字典入力情報MapList 【入】【出】
	 * @param 字典出力情報Map     【入】【出】
	 * @param keyNo
	 */
	private void 更新MapList和Map_根据計算式_臨時計算結果Map_旧MapList_旧Map_keyNo(
																			計算式 o子計算式,
																			Map 臨時計算結果Map,
																			List<Map> 字典入力情報MapList,
																			Map 字典出力情報Map,
																			int keyNo)
	{
		// 从既存结果中将对应的值取出
		String sValue = (String) 臨時計算結果Map.get(o子計算式.getSKey());
		// 合成新的【字典入力情報MapList】
		字典入力情報MapList = 做成新的MapList_by旧的MapList_keyNo_Value(字典入力情報MapList,
																		"入力" + keyNo,
																		sValue);
		// 同时将结果也同时存入【字典出力情報Map】
		if (o子計算式.getNosql入力字典().getS外部入力() != null) {
			字典出力情報Map.put(o子計算式.getNosql入力字典().getS外部入力(), sValue);
		}
		字典出力情報Map.put(o子計算式.getSKey(), sValue);

	}

	//【尚未计算】
	/**
	 *
	 * @param o子計算式
	 * @param 計算式Map
	 * @param 臨時計算結果Map
	 * @param 字典入力情報MapList 【入】【出】
	 * @param 字典出力情報Map     【入】【出】
	 * @param keyNo
	 */
	private void 計算式_計算_字典入力情報MapList_根据計算式_計算式Map_臨時計算結果Map_keyNo(
																計算式 o子計算式,
																Map<String, 計算式> 計算式Map,
																Map 臨時計算結果Map,
																List<Map> 字典入力情報MapList,
																Map 字典出力情報Map,
																int keyNo)
	{
		// 在此尝试计算吧
		List<Map> 子計算式List値 = 計算式_計算(o子計算式, 計算式Map, 臨時計算結果Map);

		// 存储计算结果:子計算式List値
		for (Map 子計算式map : 子計算式List値) {
			Map 字典入力情報Map = new HashMap();

			String sValue = (String) 子計算式map.get(o子計算式.getSKey());
			字典入力情報Map.put(keyNo, sValue);
			字典入力情報MapList = 做成新的MapList_by旧的MapList_keyNo_Value(
				字典入力情報MapList,
				"入力" + keyNo,
				sValue);

			if (o子計算式.getNosql入力字典().getS外部入力() != null) {
				字典出力情報Map.put(o子計算式.getNosql入力字典().getS外部入力(), sValue);
				字典出力情報Map.put(o子計算式.getSKey(), sValue);
				//字典出力情報MapList.add(字典出力情報Map);
			} else {
				字典出力情報Map.put(o子計算式.getSKey(), sValue);
				//字典出力情報MapList.add(字典出力情報Map);
			}
		}

	}

	/**
	 * 把Key和Val的值加到【字典入力情報MapList】中
	 * @param 字典入力情報MapList
	 * @param keyNo
	 * @param sValue
	 * @return 新的MapList
	 */
	private List<Map> 做成新的MapList_by旧的MapList_keyNo_Value(
																List<Map> 旧的MapList_,
																String keyNo,
																String sValue)
	{
		// 入力情報Map= 入力1。。。value
		// 入力2。。。value
		// 入力3。。。value1 入力3。。。value2

		List<Map> 新的MapList = new ArrayList();

		if (CollectionUtils.isEmpty(旧的MapList_)) {

			Map 子計算式map = new HashMap();
			子計算式map.put(keyNo, sValue);
			新的MapList.add(子計算式map);
			return 新的MapList;

		}
		for (Map 子計算式map : 旧的MapList_) {

			if (子計算式map.get(keyNo) == null) {

				子計算式map.put(keyNo, sValue);
				新的MapList.add(子計算式map);

			} else if (子計算式map.get(keyNo) != null) {

				Map 子計算式mapOther = new HashMap(子計算式map);
				子計算式mapOther.put(keyNo, sValue);
				新的MapList.add(子計算式map);
				新的MapList.add(子計算式mapOther);
			}

		}
		return 新的MapList;
	}

	/**
	 *
	 * @param 計算項目結果List
	 * @param o計算式
	 * @param 出力字典ID
	 * @param 結果List
	 * @return 新的ListMap
	 */
	protected List<Map> 做成新的MapList_by旧的ListMap_計算式_出力字典ID_結果MapList(
																	List<Map> 旧的ListMap,
																	計算式 o計算式,
																	String 出力字典ID,
																	List<Map> 結果MapList)
	{
		// 4 結果処理
		List<Map> 新的ListMap = new ArrayList<Map>();

		// 先买个保险
		if (CollectionUtils.isEmpty(結果MapList)) {return 新的ListMap;}

		// 如果式存储计算结果，将采用乱数Key的方式
		String sKey = o計算式.getSKey();

		// 如果【旧的ListMap】是空的
		if (CollectionUtils.isEmpty(旧的ListMap)) {
			// 就把【結果MapList】的最新结果（出力字典ID）
			// 加到【出力結果List】中完事
			for (Map 結果map : 結果MapList) {

				Map 出力結果map = new HashMap();
				String s結果 = (String) 結果map.get(出力字典ID);
				出力結果map.put(sKey, s結果);
				新的ListMap.add(new HashMap(出力結果map));

			}
			return 新的ListMap;
		}

		// 如果【計算項目結果List】不是空的
		for (Map 出力結果map : 旧的ListMap) {

			for (Map 結果map : 結果MapList) {

				String s結果 = (String) 結果map.get(出力字典ID);
				if(s結果!=null){

					出力結果map.put(sKey, s結果);

					新的ListMap.add(new HashMap(出力結果map));
				}

			}
		}

		return 新的ListMap;
	}

	/**
	 *
	 * @param o計算式
	 * @param 出力字典ID
	 * @param 結果List
	 * @return 出力結果MapList
	 */
	protected List<Map> 取得指定MapList_by計算式_指定字典ID_結果MapList(
		計算式 o計算式,
		String 出力字典ID,
		List<Map> 結果MapList)
	{
		// 4 結果処理
		List<Map> 出力結果MapList = new ArrayList<Map>();

		String sKey = o計算式.getSKey();

		for (Map 結果map : 結果MapList) {
			Map 出力結果map = new HashMap();
			String s結果 = (String) 結果map.get(出力字典ID);
			if (出力結果map.get(sKey) == null) {
				出力結果map.put(sKey, s結果);
				出力結果MapList.add(出力結果map);
			}
		}

		return 出力結果MapList;
	}

	/**
	 *
	 * @param o辞書項目
	 * @return
	 */
	protected 計算器 取得指定计算器_根据操作符号(辞書項目DTO o辞書項目) {

		switch (取得操作名称_根据操作符号(o辞書項目)) {

		case "加":
			return new 加計算器();

		case "小于":
			return new 小于計算器();

		case "小于等于":
			return new 小于等于計算器();

		case "大于":
			return new 大于計算器();

		case "大于等于":
			return new 大于等于計算器();

		case "等于":
			return new 等于計算器();

		case "与":
			return new 与関係計算器();

		case "交集判断計算器":
			return new 交集判断計算器();

		case "交集取得計算器":
			return new 交集取得計算器();

		case "非空計算器":
			return new 非空計算器();

		case "List追加計算器":
			return new List追加計算器();
		}

		return null;

	}

	protected 計算器 取得指定计算器_根据操作符号(String 計算器名) {

		switch (計算器名) {

		case "加":
			return new 加計算器();

		case "小于":
			return new 小于計算器();

		case "小于等于":
			return new 小于等于計算器();

		case "大于":
			return new 大于計算器();

		case "大于等于":
			return new 大于等于計算器();

		case "等于":
			return new 等于計算器();

		case "与":
			return new 与関係計算器();

		case "交集判断計算器":
			return new 交集判断計算器();

		case "交集取得計算器":
			return new 交集取得計算器();

		case "非空計算器":
			return new 非空計算器();

		case "List追加計算器":
		case "List相加計算器":
			return new List追加計算器();

		case "sumevery計算器":
		case "sumEvery計算器":
			return new SumEvery計算器();
		}

		return null;

	}

	/**
	 *
	 * @param o辞書項目
	 * @return
	 */
	private String 取得操作名称_根据操作符号(辞書項目DTO o辞書項目) {

		switch (o辞書項目.get辞書ID()) {

		case "加関係辞書":
			return "加";

		case "小関係辞書":
			return "小于";

		case "小于等于関係辞書":
			return "小于等于";

		case "大関係辞書":
			return "大于";

		case "大于等于関係辞書":
			return "大于等于";

		case "等関係辞書":
			return "等于";

		case "与関係辞書":
			return "与";

		}
		return null;
	}

	protected Map 取得子計算式数据Map_根据子計算式結果and計算式(
													計算式 o計算式,
													Map<String, 計算式> 計算式Map,
													Map 子計算式結果,
													Map 入力情報Map)
	{
		// 只為本次計算準備数据
		Map 取得個別計算Map = new HashMap();

		for (String s子計算式名 : o計算式.get子計算式名List()) {
			String sKey = 計算式Map.get(s子計算式名).getSKey();
			String sVaule = (String) 子計算式結果.get(sKey);
			if (sVaule == null) {
				sVaule = (String) 入力情報Map.get(sKey);
			}
			取得個別計算Map.put(sKey, sVaule);

		}

		return 取得個別計算Map;
	}

	// 做一个标识。最后用这个标识来明确是否检索完毕。
	protected Map DONE(
		計算式 o計算式,
		Map<String, 計算式> 計算式Map,
		Map 入力情報Map)
	{
		Map DONE計算Map = new HashMap(入力情報Map);
		DONE計算Map.put("DONE", "this");
		return DONE計算Map;
	}

	/**
	 *
	 * @param o計算式
	 * @param 計算式Map
	 * @param 入力情報Maps
	 * @return
	 */
	protected Map RETURNs(
		計算式 o計算式,
		Map<String, 計算式> 計算式Map,
		Map 入力情報Map)
	{
		Map RETURN計算Map = new HashMap(入力情報Map);
		RETURN計算Map.put("RETURN", "this");
		return RETURN計算Map;
	}

	/**
	 * 如果NOSQL没有给出返回值。那么接下来就没有处理的必要了是不是。
	 * @param 結果List
	 * @return
	 */
	protected boolean 判断NOSQL是否存在返回值(List<Map> 結果MapList) {
		if(CollectionUtils.isEmpty(結果MapList)){
			return true;
		}
		for(Map 結果Map : 結果MapList){
			if(結果Map.containsValue(null)){
				return true;
			}
		}
		return false;
	}

	public List<計算式> 取得子计算式的实体_by計算式_計算式Map(計算式 o計算式, Map<String, 計算式> 計算式Map) {
		// 計算式
		//  |-----計算式

		// 計算式Map
		//  key=a1,   a1的計算式
		List<計算式> 子计算式的实体List = new ArrayList();

		for (String s子計算式名 : o計算式.get子計算式名List()) {

			計算式 o子計算式 = 計算式Map.get(s子計算式名);
			子计算式的实体List.add(o子計算式);
		}

		return 子计算式的实体List;
	}
}
