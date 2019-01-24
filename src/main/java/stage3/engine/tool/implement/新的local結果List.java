package stage3.engine.tool.implement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import lombok.Data;
import stage3.engine.parse.計算式;
import stage3.engine.tool.Local結果;

@Data
public class 新的local結果List extends Local結果Base implements Local結果 {

	public 新的local結果List(Map<String, 計算式> 計算式Map2) {
		this.計算式Map = 計算式Map2;
		this.sKeyList = 取得KeyList_From計算式Map(計算式Map2);
	}

	//	public 新的local結果List(Map<String, 計算式> 計算式Map2) {
	//		super(計算式Map2);
	//	}

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
		if (o計算式.getSName().equals("b4311")) {
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

		} else {

			if (o計算式.getSName().equals("b43")) {
				logger.info(o計算式.getSName() + "   生成新的local結果List ");
				//logger.info("      全体結果Map = [size=" + 全体結果List.size() + "] " + 全体結果List);
				//logger.info("      今回結果Map = [size=" + 今回結果List.size() + "] " + 今回結果List);
				logger.info("      全体結果Map = [size=" + 全体結果List.size() + "] " + 全体結果List.get(0));
				logger.info("      今回結果Map = [size=" + 今回結果List.size() + "] " + 今回結果List.get(0));

				if (今回結果List.size() == 80) {
					今回結果List = 今回結果List;
				}

				//				else{
				//					return 今回結果List;
				//				}

			}

			/**
			 *
			 */
			boolean sb = true;
			List<Map> 差分MapList = new ArrayList();
			List<Map> 処理不要MapList = new ArrayList();

			// 1 循環執行【今回結果List】中每个Map
			for (Map 今回結果Map : 今回結果List) {

				// 1 循環執行【今回結果List】中每个Map
				sb = 判断是否可以覆結果(sKeyList, 今回結果Map, 差分MapList, 処理不要MapList);

			}
			//			if(sb){
			//				return 今回結果List;
			//			}
			if (!処理不要MapList.isEmpty()) {
				return 処理不要MapList;
			}

			/**
			 *
			 */
			// 1.1 循環執行【全体結果List】中每个Map
			for (Map 全体結果Map : 全体結果List) {

				// 1 循環執行【今回結果List】中每个Map
				for (Map 今回結果Map : 差分MapList) {

					local結果List = 制作新結果(全体結果Map, 今回結果Map, local結果List);
				}

			}

			if (!処理不要MapList.isEmpty()) {
				local結果List.addAll(処理不要MapList);
			}

		}

		List<Map> result = 减肥計画(local結果List);

		if (o計算式.getSName().equals("b43")) {
			logger.info("      减肥計画結果List = [size = " + result.size() + "]  " + result);
			logger.info("\r\n");
		}
		if (result.size() == 431) {
			result = result;
		}
		return result;
	}

	@Override
	public List<Map> 制作新結果(Map 全体結果Map, Map 今回結果Map, List<Map> local結果List) {
		final Logger logger = Logger.getLogger("SampleLogging");

		local結果List = new ArrayList(local結果List);

		全体結果Map = new HashMap(全体結果Map);
		今回結果Map = new HashMap(今回結果Map);
		Map 差分 = null;

		// logger.info(" 取得类型 = "+取得类型(全体結果Map,今回結果Map));
		// logger.info("\r\n");
		switch (取得类型_For項目(全体結果Map, 今回結果Map)) {
		case "0"://相等
			差分 = 取得差分(全体結果Map, 今回結果Map);
			差分.putAll(今回結果Map);
			local結果List.add(差分);
			break;

		case "2"://相等
			差分 = 取得差分(全体結果Map, 今回結果Map);
			差分.putAll(今回結果Map);
			local結果List.add(差分);

			local結果List.add(全体結果Map);
			break;
		case "1"://不相等

			local結果List.add(全体結果Map);
			break;
		}

		return local結果List;
	}

	public List<Map> 制作新結果_FOR前COPY(Map 全体結果Map, Map 今回結果Map, List<Map> local結果List) {
		final Logger logger = Logger.getLogger("SampleLogging");

		local結果List = new ArrayList(local結果List);

		全体結果Map = new HashMap(全体結果Map);
		今回結果Map = new HashMap(今回結果Map);
		Map 差分 = null;

		// logger.info(" 取得类型 = "+取得类型(全体結果Map,今回結果Map));
		// logger.info("\r\n");
		switch (super.取得类型(全体結果Map, 今回結果Map)) {
		case "0"://相等
		case "1"://相等
		case "2"://相等
			差分 = 取得差分(全体結果Map, 今回結果Map);
			差分.putAll(今回結果Map);
			local結果List.add(差分);
			break;

		default://不相等

			local結果List.add(全体結果Map);
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
		for (String s全 : 相同的KEYList) {

			if (f今回結果Map.get(s全) == null) {

				continue;

			}

			if (f全体結果Map.get(s全).equals(f今回結果Map.get(s全))) {

				i相同的KEY中相同的值数 = i相同的KEY中相同的值数 + 1;

			}
		}
		if (i相同的KEY数 == i相同的KEY中相同的值数) {
			return "0";
		} else {
			return "1";
		}
	}

	private String 取得类型_For項目(Map 全体結果Map, Map 今回結果Map) {

		List<計算式> 計算式List = 取得全部指定項目計算式_根据指定运算符(計算式Map, "FOR項目計算器");

		List<String> keyList = 取得KeyList_根据計算式List(計算式List);

		Map 全体結果比較用map = 作成Map_根据KeyList(keyList, 全体結果Map);

		Map 今回結果比較用map = 作成Map_根据KeyList(keyList, 今回結果Map);

		String 全体結果比較用str = Map排序出力結果(全体結果比較用map);
		String 今回結果比較用str = Map排序出力結果(今回結果比較用map);

		if (全体結果比較用map.isEmpty()) {
			// 之前已经把Key放入了啊！！
			return "1";

		}
		if (全体結果比較用str.equals(今回結果比較用str)) {
			return "0";
		} else {
			return "2";
		}
	}

	@Override
	public List<Map> 削除関連数据(List<Map> 計算結果List, Map for_入力情報Map) {
		List<Map> 出力結果 = new ArrayList();
		for (Map 計算結果map : 計算結果List) {
			if (取得类型_For項目(計算結果map, for_入力情報Map).equals("0")) {

			} else {
				出力結果.add(計算結果map);
			}
		}

		return 出力結果;
	}

	private Map 作成Map_根据KeyList(List<String> keyList, Map<String, String> 結果Map) {
		Map 結果map = new HashMap();
		for (Entry oEntry : 結果Map.entrySet()) {
			for (String skey : keyList) {
				if (skey.equals(oEntry.getKey())) {
					結果map.put(oEntry.getKey(), oEntry.getValue());
				}
			}
		}
		return 結果map;
	}

	private List<String> 取得KeyList_根据計算式List(List<計算式> 計算式List) {
		List<String> KeyList = new ArrayList();
		for (計算式 o計算式 : 計算式List) {
			//if(o計算式.get子計算式List() != null && !o計算式.get子計算式List().isEmpty()){
			//	for(String sName : o計算式.get子計算式List()){

			KeyList.add(o計算式.getSKey());
			//	}
			//}
		}
		return KeyList;
	}

	private List<計算式> 取得全部指定項目計算式_根据指定运算符(Map<String, 計算式> 計算式Map, String s指定項目) {
		List<計算式> 結果List = new ArrayList();
		for (Entry oEntry : 計算式Map.entrySet()) {

			計算式 o計算式 = (計算式) oEntry.getValue();
			if (o計算式.getS运算符().equals(s指定項目)) {
				結果List.add(o計算式);
			}

		}
		return 結果List;
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
		if (o計算式.getSName().equals("b4311")) {
			o計算式 = o計算式;
		}
		if (今回結果Map == null) {
			return 全体結果List;
		}

		if (全体結果List == null || 全体結果List.isEmpty()) {

			local結果List = 制作新結果_FOR前COPY(new HashMap(), 今回結果Map, local結果List);

		} else {

			//			logger.info(o計算式.getSName() + "   生成新的local結果List ");
			//			logger.info("      全体結果Map = [size=" + 全体結果List.size() + "] " + 全体結果List);
			//			logger.info("      今回結果Map = [size=" + 今回結果List.size() + "] " + 今回結果List);

			// 1.1 循環執行【全体結果List】中每个Map
			for (Map 全体結果Map : 全体結果List) {

				local結果List = 制作新結果_FOR前COPY(全体結果Map, 今回結果Map, local結果List);

			}
		}

		return 减肥計画(local結果List);
	}
}
