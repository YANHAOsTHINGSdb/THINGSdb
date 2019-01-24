package stage3.engine.tool.implement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import lombok.Data;
import stage3.engine.parse.計算式;
import stage3.engine.tool.Local結果;

@Data
public class Local結果Base  implements Local結果{
	protected Map<String, 計算式> 計算式Map = null;
	protected List<String> sKeyList = null;

//	public Local結果Base(Map<String, 計算式> 計算式Map2) {
//		this.計算式Map = 計算式Map2;
//
//	}
	/**
	 * 取得KeyList_From計算式Map
	 * @param 計算式Map2
	 * @return
	 */
	protected List<String> 取得KeyList_From計算式Map(Map<String, 計算式> 計算式Map2){
		List<String> sKeyList_local = new ArrayList();
		for (Map.Entry<String, 計算式> entry今 : 計算式Map2.entrySet()) {
			if( ! entry今.getValue().getS运算符().equals("出力")){
				sKeyList_local.add(entry今.getValue().getSKey());
			}

		}
		return sKeyList_local;

	}

	/**
	 * 判断是否可以覆結果
	 * @param sKeyList
	 * @param 今回結果Map
	 * @return
	 */
	protected boolean 判断是否可以覆結果(List<String> sKeyList, Map 今回結果Map,List<Map> 差分MapList, List<Map> 処理不要MapList){
		List<String> sKeyList_local = new ArrayList();

		boolean bReault= true;
		if(sKeyList == null){
			sKeyList = sKeyList;
		}
		for (String sKey : sKeyList) {
			if( ! 今回結果Map.containsKey(sKey)){
				bReault = false;
				差分MapList.add(new HashMap(今回結果Map));
				return bReault;
			}
		}


		処理不要MapList.add(new HashMap(今回結果Map));
		return bReault;

	}
	/**
	 *
	 * @param 計算項目結果List
	 * @return
	 */
	protected List<Map> 减肥計画(List<Map> 計算項目結果List) {
		final Logger logger = Logger.getLogger("SampleLogging");
		List<Map> new計算項目結果List = new ArrayList();

		Map<String, Integer> res = new HashMap<>();

		for (Map map : 計算項目結果List) {
			String sKey = Map排序出力結果(map);
			if (res.containsKey(sKey)) {

			} else {
				res.put(sKey, 1);
				new計算項目結果List.add(map);
			}
		}
//		logger.info("      减肥計画結果List = [size = " + new計算項目結果List.size() + "]  " + new計算項目結果List);
//		logger.info("\r\n");
		return new計算項目結果List;
	}

	/**
	 *
	 * @param map
	 * @return
	 */
	String Map排序出力結果(Map<String, String> map) {
		Map<String, String> result = new LinkedHashMap<>();

		// String sKeys= null;
		// String sValues= null;
		// for(Entry<String, String> m : map.entrySet()){
		// sKeys += m.getKey();
		// sValues += m.getValue();
		// }
		// 昇順
		map.entrySet().stream().sorted(java.util.Map.Entry.comparingByKey())
				.forEach(x -> result.put(x.getKey(), x.getValue()));

		return result.toString();

	}

	@Override
	public List<Map> 生成新的local結果List(List<Map> 全体結果List, List<Map> 今回結果List, 計算式 o計算式) {
		final Logger logger = Logger.getLogger("SampleLogging");
		List<Map> local結果List = new ArrayList<Map>();
		// local結果List =
		// 結果1<k1,V1> <k2,V2> <k3,V3> <k4,V4> <k5,V5>
		// 結果2<k1,V11><k2,V21><k3,V31><k4,V41><k5,V51>
		// 結果3<k1,V12><k2,V22><k3,V32><k4,V42><k5,V52>
		//
if (o計算式.getSName().equals("b1")) {
	o計算式 = o計算式;
}
if(o計算式.getSName().equals("b4311")){
	o計算式 = o計算式;
}
		if (今回結果List == null) {
			return 全体結果List;
		}

		if (全体結果List == null || 全体結果List.isEmpty()) {

			// 1 循環執行【今回結果List】中每个Map
			for (Map 今回結果Map : 今回結果List) {

				local結果List = 制作新結果(new HashMap(), 今回結果Map, local結果List);
			}

		}else{

			/**
			 *
			 */
			boolean sb = true;
			// 1 循環執行【今回結果List】中每个Map
			List<Map> 差分MapList = new ArrayList();
			List<Map> 処理不要MapList = new ArrayList();

			for (Map 今回結果Map : 今回結果List){

				// 1 循環執行【今回結果List】中每个Map
				sb = 判断是否可以覆結果(sKeyList, 今回結果Map, 差分MapList, 処理不要MapList);

			}
			if(sb){
				return 今回結果List;
			}
			if( ! 処理不要MapList.isEmpty()){
				return 処理不要MapList;
			}
//			logger.info(o計算式.getSName() + "   生成新的local結果List ");
//			logger.info("      全体結果Map = [size=" + 全体結果List.size() + "] " + 全体結果List);
//			logger.info("      今回結果Map = [size=" + 今回結果List.size() + "] " + 今回結果List);

			// 1.1 循環執行【全体結果List】中每个Map
			for (Map 全体結果Map : 全体結果List) {

				// 1 循環執行【今回結果List】中每个Map
				for (Map 今回結果Map : 差分MapList) {

					local結果List = 制作新結果(全体結果Map, 今回結果Map, local結果List);
				}
			}

			if( ! 処理不要MapList.isEmpty()){
				local結果List.addAll(処理不要MapList);
			}

		}

		return 减肥計画(local結果List);
	}

	@Override
	public List<Map> 制作新結果(Map 全体結果Map, Map 今回結果Map, List<Map> local結果List) {
		final Logger logger = Logger.getLogger("SampleLogging");

		local結果List = new ArrayList(local結果List);

		全体結果Map = new HashMap(全体結果Map);
		今回結果Map = new HashMap(今回結果Map);

		// logger.info(" 取得类型 = "+取得类型(全体結果Map,今回結果Map));
		// logger.info("\r\n");
		switch (取得类型(全体結果Map, 今回結果Map)) {
		case "0":
		case "1":
		case "2":
		case "5":
			今回結果Map.putAll(全体結果Map);
			local結果List.add(今回結果Map);
			break;

		case "3":
			Map map3 = 取得差分(全体結果Map, 今回結果Map);
			全体結果Map.putAll(map3);
			今回結果Map.putAll(map3);
			local結果List.add(全体結果Map);
			local結果List.add(今回結果Map);
			break;

		case "4":
			local結果List.add(全体結果Map);
			local結果List.add(今回結果Map);
			break;

		}

		return local結果List;
	}

	@Override
	public Map 取得差分(Map 全体結果Map, Map 今回結果Map) {

		Map<String, String> f全体結果Map = new HashMap<String, String>(全体結果Map);
		Map<String, String> f今回結果Map = new HashMap<String, String>(今回結果Map);
		List<String> keyList今 = new ArrayList();
		List<String> keyList全 = new ArrayList();
		List<String> 相同的KEYList = new ArrayList();
		Map<String, String> 差分resullt = new HashMap<>();

		for (Map.Entry<String, String> entry今 : f今回結果Map.entrySet()) {
			keyList今.add(entry今.getKey());
		}

		for (Map.Entry<String, String> entry全 : f全体結果Map.entrySet()) {
			keyList全.add(entry全.getKey());
		}

		for (String s全 : keyList全) {
			for (String s今 : keyList今) {
				if (s全.equals(s今)) {
					相同的KEYList.add(s今);
				}
			}
		}

		for (Map.Entry<String, String> entry今 : f今回結果Map.entrySet()) {
			if (!相同的KEYList.contains(entry今.getKey())) {
				差分resullt.put(entry今.getKey(), entry今.getValue());
			}
		}

		for (Map.Entry<String, String> entry全 : f全体結果Map.entrySet()) {
			if (!相同的KEYList.contains(entry全.getKey())) {
				差分resullt.put(entry全.getKey(), entry全.getValue());
			}
		}

		return 差分resullt;
	}

	public String 取得类型(Map 全体結果Map, Map 今回結果Map) {

		Map<String, String> f全体結果Map = new HashMap<String, String>(全体結果Map);
		if (全体結果Map == null || 全体結果Map.isEmpty()) {
			return "5";
		}
		Map<String, String> f今回結果Map = new HashMap<String, String>(今回結果Map);
		List<String> keyList今 = new ArrayList();
		List<String> keyList全 = new ArrayList();
		List<String> 相同的KEYList = new ArrayList();
		for (Map.Entry<String, String> entry今 : f今回結果Map.entrySet()) {
			keyList今.add(entry今.getKey());
		}

		for (Map.Entry<String, String> entry全 : f全体結果Map.entrySet()) {
			keyList全.add(entry全.getKey());
		}

		boolean b相同的KEY有相同的值 = false;
		boolean b相等的KEY = keyList今.equals(keyList全);
		boolean b無相同的KEY = false;
		int i相同的KEY数 = 0;
		int i相同的KEY中相同的值数 = 0;

		for (String s全 : keyList全) {
			for (String s今 : keyList今) {
				if (s全.equals(s今)) {
					i相同的KEY数 = i相同的KEY数 + 1;
					相同的KEYList.add(s今);
				}
			}
		}

		if (i相同的KEY数 == 0) {

			b無相同的KEY = true;
			return "5";
		}

		for (String s全 : 相同的KEYList) {

			if (f今回結果Map.get(s全) == null) {

				continue;

			}

			if (f全体結果Map.get(s全).equals(f今回結果Map.get(s全))) {

				i相同的KEY中相同的值数 = i相同的KEY中相同的值数 + 1;

			}
		}

		if (i相同的KEY中相同的值数 == i相同的KEY数) {

			if (keyList今.size() > keyList全.size()) {
				return "1";
			}
			if (keyList今.size() == keyList全.size()) {
				return "2";
			}
			if (keyList今.size() < keyList全.size()) {
				return "0";
			}
		}

		if (i相同的KEY中相同的值数 < i相同的KEY数 && b相等的KEY) {

			return "4";

		} else if (i相同的KEY中相同的值数 < i相同的KEY数 && !b相等的KEY) {

			return "3";

		}

		return null;
	}

	@Override
	public List<Map> 生成新的local結果List(List<Map> 全体結果List, Map 今回結果Map, 計算式 o計算式) {
		final Logger logger = Logger.getLogger("SampleLogging");
		List<Map> local結果List = new ArrayList<Map>();
		// local結果List =
		// 結果1<k1,V1> <k2,V2> <k3,V3> <k4,V4> <k5,V5>
		// 結果2<k1,V11><k2,V21><k3,V31><k4,V41><k5,V51>
		// 結果3<k1,V12><k2,V22><k3,V32><k4,V42><k5,V52>
		//
if (o計算式.getSName().equals("b1")) {
	o計算式 = o計算式;
}
if(o計算式.getSName().equals("b4311")){
	o計算式 = o計算式;
}
		if (今回結果Map == null) {
			return 全体結果List;
		}

		if (全体結果List == null || 全体結果List.isEmpty()) {

				local結果List = 制作新結果(new HashMap(), 今回結果Map, local結果List);

		}else{

//			logger.info(o計算式.getSName() + "   生成新的local結果List ");
//			logger.info("      全体結果Map = [size=" + 全体結果List.size() + "] " + 全体結果List);
//			logger.info("      今回結果Map = [size=" + 今回結果List.size() + "] " + 今回結果List);

			// 1.1 循環執行【全体結果List】中每个Map
			for (Map 全体結果Map : 全体結果List) {


					local結果List = 制作新結果(全体結果Map, 今回結果Map, local結果List);

			}
		}

		return 减肥計画(local結果List);
	}

	@Override
	public List<Map> 削除関連数据(List<Map> 計算結果List, Map for_入力情報Map) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
