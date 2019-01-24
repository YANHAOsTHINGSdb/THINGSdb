package stage3.engine.tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import stage3.REL.program.詞条CRUD;
import stage3.engine.bean.辞書BeanDTO;
import stage3.log.MyLogger;

public class SDP {
	String sCallPath = null;
	MyLogger myLogger = new MyLogger();

	public SDP(String sCallPath) {
		this.sCallPath = sCallPath;
	}
	private Map toMap(List<Object> SDPList) {
		myLogger.printCallMessage(sCallPath,"SDP.toMap( )");
		/*------入力：SDPList--------------
			[
			["a1","外部入力", "入力1" ],
			["a2","外部入力","入力2"],
			["b1","入力","a1"],
			["b2","入力","a2"],
			["b1","詞条","時間"],
			["b2","詞条","股号"],
			["b31","計算","sumEvery計算器"],
			["b31","主(条件)","b311"],
			["b311","every","成交价"],
			["b311","sum","成交量"],
			["b311","主","b1"],
			["b311","次","b2"]
			]
		 */

		/*------出力：SDPMap-------------
		 *  1级---------------------------
		 *             2级----------------
		 *
			a1——map  （外部入力，入力1)
			a2——map  （外部入力，入力2)
			b1——map  （入力，a1)
			         （詞条，時間)
			b2——map  （入力，a2)
			         （詞条，股票代码)
			b31——map （計算，sumEvery計算器)
			         （主(条件)，b311)
			b311——Map（every，成交价))
		             （sum，成交量))
		             （主，b1))
		              (次，b2))
		 */
		/*
		   生成【1级Map】（例、toMap（SDPList））
		   |----在Map【1级Map】中取得对应值（例、key="a1"）针对一条(["a1","外部入力", "入力1"])
		        |----如果存在
		        |         |----取得key对应的Value(例，【2级Map】)
		        |         |----追加信息toMap_by指定值(map=2级Map，key="外部入力"，value="入力1")
		        |               |----在Map【2级Map】中取得对应值（例、key="外部入力"）
		        |                      |----如果存在，覆盖还是放弃？
		        |                      |----如果不存在，则取得Value（例，"外部入力"，"入力1"）
		        |
		        |----如果不存在
		                  |----2级Map = 生成Map并追加【2级Map】_by指定值（例，["a1","外部入力", "入力1" ]）
		                  |       |----则取得key  （例，"a1"）
		                  |       |----则取得Value（例，"外部入力"，"入力1"）
		                  |----追加信息toMap_by指定值(map=1级Map， key="a1"，value=2级Map)
		                        |----在Map【1级Map】中取得对应值（例、key="a1"）
		                               |----如果不存在，则取得Value（例，"a1"，2级Map）
		*/
		Map map1級 = new HashMap();
		for (Object s : SDPList) {
			List<String> listOne行SDP = null;
			if(s instanceof List){
				listOne行SDP = (List) s;
			}else if(s instanceof String){
				listOne行SDP = Arrays.asList(((String) s).split(","));
			}

			Object map2級 = 取得Value_byMap_key(map1級, listOne行SDP.get(0));    // 在Map【1级Map】中取得对应值（例、key="a1"）针对一条(["a1","外部入力", "入力1"])
			                                                        // |
			if (map2級 != null) {                                  // |----如果存在
				if (!(map2級 instanceof Map)) {                   // |         判断取出的【2级Map】是不是Map型
					return null;                                   // |          |----如果不是 就直接退出
				}                                                   // |
				追加信息toMap_by指定值((Map) map2級, listOne行SDP.get(1), listOne行SDP.get(2));
                                                                    // |        追加信息toMap_by指定值(map=1级Map， key="a1"，value=2级Map)
				                                                    // |
			} else {                                                // |----如果不存在

				Map map2級_ = 生成Map并追加到2級Map(listOne行SDP.get(0), listOne行SDP.get(1), listOne行SDP.get(2));
                                                                     //          2级Map = 生成Map并追加【2级Map】_by指定值（例，["a1","外部入力", "入力1" ]）
				                                                     //
				追加信息toMap_by指定值((Map) map1級, listOne行SDP.get(0), map2級_);
                                                                     //          追加信息toMap_by指定值(map=1级Map， key="a1"，value=2级Map)
			}
		}

		return map1級;
	}

	private Map 生成Map并追加到2級Map(String string, String key, String value) {
		myLogger.printCallMessage(sCallPath,"SDP.生成Map并追加到2級Map");
		//       |----则取得key  （例，"a1"）
		//       |----则取得Value（例，"外部入力"，"入力1"）
		Map map2級 = new HashMap();
		map2級.put(key, value);
		return map2級;
	}

	private void 追加信息toMap_by指定值(Map mapN級, String key, Object value) {
		myLogger.printCallMessage(sCallPath,"SDP.追加信息toMap_by指定值");
		if (mapN級 == null) {
			mapN級 = new HashMap();
		}
		mapN級.put(key, value);
	}

	private Object 取得Value_byMap_key(Map map1級, String skey) {
		myLogger.printCallMessage(sCallPath,"SDP.取得Value_byMap_key");
		if (CollectionUtils.isEmpty(map1級)) {
			return null;
		}

		return map1級.get(skey);
	}

	/**
	 * key1級List = [a1,a2,b1,b2,b31,b311]
	 */
	List<Entry<String, Map>> key1級List_Entry;

	List<String> 入力对象List = new ArrayList();

	public List<String> 取得入力对象(List<Object> SPDlist) {
		myLogger.printCallMessage(sCallPath,"SDP.取得入力对象(SPDlist)");
		Map map1級 = toMap(SPDlist);

		return 取得入力对象(map1級);

	}

	/*------入力：map1級-------------
	 *  1级---------------------------
	 *             2级----------------
	 *
		a1——map  （外部入力，入力1)
		a2——map  （外部入力，入力2)
		b1——map  （入力，a1)
		         （詞条，時間)
		b2——map  （入力，a2)
		         （詞条，股票代码)
		b31——map （計算，sumEvery計算器)
		         （主(条件)，b311)
		b311——Map（every，成交价))
	             （sum，成交量))
	             （主，b1))
	              (次，b2))
	 */
	public List<String> 取得入力对象(Map map1級) {
		myLogger.printCallMessage(sCallPath,"SDP.取得入力对象(map1級)");
		/*
		1、在2级Map中找Key=外部入力的对象
		2、在1级中找到其对应的Key
		3、在2级Map中找value={2}的对象
		4、返回对象{3}的Key=词条的值
		*/

		/*
		 * key1級List = [a1,a2,b1,b2,b31,b311]
		 */
		key1級List_Entry = 取得KeyList_by対象Map(map1級);

		int index = 0;
		for (Entry entry : key1級List_Entry) {
			// 1、在2级Map中找Key=外部入力的对象
			if (Is存在_by対象MapAnd対象key名((Map) entry.getValue(), "外部入力")) {

				// (用【外部入力】找到其对应的Key)---> a1
				// ["a1","外部入力", "入力1" ]
				// (用a1找到其所在Map)---> Map(b1)
				// ["b1","入力","a1"]
				// (在Map(b1)中找到[key=词条]的对应值)
				// ["b1","詞条","時間"]
				String s入力对象 = 取得取得入力对象_byMapList2級andIndex(key1級List_Entry, index);
				入力对象List.add(s入力对象);
			}
            // 计数用
			index++;
		}

		return 入力对象List;
	}

	public Map 取得入力对象Map_bySDPList(List<Object> SDPlist) {
		myLogger.printCallMessage(sCallPath,"SDP.取得入力对象Map_bySDPList(SDPlist)");

		Map map1級 = toMap(SDPlist);

		return 取得入力对象Map_byMap1級(map1級);

	}

	public Map 取得入力对象Map_byMap1級(Map map1級) {
		myLogger.printCallMessage(sCallPath,"SDP.取得入力对象Map_byMap1級(map1級)");

		Map outPutMap = new HashMap();
		key1級List_Entry = 取得KeyList_by対象Map(map1級);
		int index = 0;
		for (Entry entry : key1級List_Entry) {
			// 1、在2级Map中找Key=外部入力的对象
			if (Is存在_by対象MapAnd対象key名((Map) entry.getValue(), "入口")) {

				// (用【外部入力】找到其对应的Key)---> a1
				// ["a1","外部入力", "入力1" ]
				// (用a1找到其所在Map)---> Map(b1)
				// ["b1","入力","a1"]
				// (在Map(b1)中找到[key=词条]的对应值)
				// ["b1","詞条","時間"]
				Map 入力对象Map = 取得取得入力对象Map_byMapList2級andIndex(key1級List_Entry, index);
				outPutMap.putAll(入力对象Map);
			}
            // 计数用
			index++;
		}

		return outPutMap;
	}

	/*------入力：map1級-------------
	 *  1级---------------------------
	 *             2级----------------
	 *
		a1——map  （外部入力，入力1)
		a2——map  （外部入力，入力2)
		b1——map  （入力，a1)
		         （詞条，時間)
		b2——map  （入力，a2)
		         （詞条，股票代码)
		b31——map （計算，sumEvery計算器)
		         （主(条件)，b311)
		b311——Map（every，成交价))
	             （sum，成交量))
	             （主，b1))
	              (次，b2))
	 */
	private Map 取得取得入力对象Map_byMapList2級andIndex(List<Entry<String, Map>> key1級List_Entry2, int index) {
		myLogger.printCallMessage(sCallPath,"SDP.取得取得入力对象Map_byMapList2級andIndex(key1級List_Entry2)");
		Map 入力对象map = new HashMap();

		if (CollectionUtils.isEmpty(key1級List_Entry2)) {
			return null;
		}

		// 先搞定value
		Entry entry = (Entry) key1級List_Entry2.get(index);
		String sValue = (String)((Map) entry.getValue()).get("入口");


		// 再搞定key
		// (用【外部入力】找到其对应的Key)---> a1
		// a1——map  （外部入力，入力1)
		String a1 = key1級List_Entry2.get(index).getKey();
		Map<String, String> 対象Map = 取得対象MapListAnd対象value(key1級List_Entry2, a1);
		String sKey = 対象Map.get("詞条");

		// 整合key和value
		入力对象map.put(sKey, sValue);

		return 入力对象map;
	}

	// 用index在mapList2級中找到目标词条值
	/**
	 *
	 * @param key1級List_Entry2  [a1,a2,b1,b2,b31,b311]
	 * @param index              计数用
	 * @return
	 */
	private String 取得取得入力对象_byMapList2級andIndex(List<Entry<String, Map>> key1級List_Entry2, int index) {
		myLogger.printCallMessage(sCallPath,"SDP.取得取得入力对象_byMapList2級andIndex(key1級List_Entry2)");
		if (CollectionUtils.isEmpty(key1級List_Entry2)) {
			return null;
		}

		// (用【外部入力】找到其对应的Key)---> a1
		// ["a1","外部入力", "入力1" ]
		String a1 = key1級List_Entry2.get(index).getKey();

		// (用a1找到其所在Map)---> Map(b1)
		// ["b1","入力","a1"]
		Map<String, String> 対象Map = 取得対象MapListAnd対象value(key1級List_Entry2, a1);

		// (在Map(b1)中找到[key=詞条]的对应值)
		// ["b1","詞条","時間"]
		return 対象Map.get("詞条");
	}

	private Map 取得対象MapListAnd対象value(List<Entry<String, Map>> key1級List_Entry2, String value) {
		myLogger.printCallMessage(sCallPath,"SDP.取得対象MapListAnd対象value(key1級List_Entry2)");

		/*------入力：map1級-------------
		 *  1级---------------------------
		 *             2级----------------
		 *
			a1——map  （外部入力，入力1)
			a2——map  （外部入力，入力2)
			b1——map  （入力，a1)
			         （詞条，時間)
			b2——map  （入力，a2)
			         （詞条，股票代码)
			b31——map （計算，sumEvery計算器)
			         （主(条件)，b311)
			b311——Map（every，成交价))
		             （sum，成交量))
		             （主，b1))
		              (次，b2))
		 */

		// 在对象Map中，查找是否存在值为value的对象map
		// 如果存在，就返回所在Map
		for (Entry<String, Map> entry : key1級List_Entry2) {
			//if (Is存在_by対象MapAnd対象value(entry.getValue(), value)) {
			//	return entry.getValue();
			//}
			if(StringUtils.equals(entry.getKey(), value)) {
				return (Map)entry.getValue();
			}
		}
		return null;
	}

	private boolean Is存在_by対象MapAnd対象value(Map<String, String> 対象map, String s対象key) {
		myLogger.printCallMessage(sCallPath,"SDP.Is存在_by対象MapAnd対象value(対象map)");
		for (Entry<String, String> entry : 対象map.entrySet()) {
			if (entry.getValue().equals(s対象key)) {
				return true;
			}
		}
		return false;
	}

	private boolean Is存在_by対象MapAnd対象key名(Map<String, String> 対象map, String s対象key) {
		myLogger.printCallMessage(sCallPath,"SDP.Is存在_by対象MapAnd対象key名(対象map)");
		for (Entry<String, String> entry : 対象map.entrySet()) {
			if (entry.getKey().equals(s対象key)) {
				return true;
			}
		}
		return false;
	}

	private List<Entry<String, Map>> 取得KeyList_by対象Map(Map 対象Map) {
		myLogger.printCallMessage(sCallPath,"取得KeyList_by対象Map(対象map)");
		// 取得対象Map中所有Key
		List<Entry<String, Map>> list = new ArrayList<Map.Entry<String, Map>>(対象Map.entrySet());
		return list;
	}

	public List<String> 先通过固定方式取的_取得目标数据的计算方法SDP的SDP() {

		//
		//
		// 詞条 o詞条 = new 詞条(sCallPath+"取得程序IDList__byCRUD");
		詞条CRUD 词条crud = new 詞条CRUD("先通过固定方式取的_取得目标数据的计算方法SDP的SDP");

		return 词条crud.取得实体数据StringList_by词条名_数据ID("SDP代码","0000000001");
	}

	/**
	 *. 转换 from List<String> to List<辞書BeanDTO>
	 * @param 程序代码List
	 * @return
	 */
	public List<辞書BeanDTO> get辞書BeanDTOList_by程序代码List(List<Object> 程序代码List) {

		myLogger.printCallMessage(sCallPath,"Relation.get辞書BeanDTOList_by程序代码List( )");

		List<辞書BeanDTO> 辞書BeanDTOList_by程序代码List = new ArrayList();

		int i = 0;
		for(Object o程序代码: 程序代码List) {
			List<String> s代码 = null;
			if(o程序代码 instanceof String) {
				String s程序代码 = (String) o程序代码;
				s代码 = Arrays.asList(s程序代码.split("\\s*,\\s*"));
			}else if(o程序代码 instanceof List) {
				s代码 = (List)o程序代码;
			}

			辞書BeanDTO 辞書Beandto = new 辞書BeanDTO(i+"" ,s代码.get(0),s代码.get(1),s代码.get(2));
			辞書BeanDTOList_by程序代码List.add(辞書Beandto);

			i++;
		}
		return 辞書BeanDTOList_by程序代码List;
	}


}
