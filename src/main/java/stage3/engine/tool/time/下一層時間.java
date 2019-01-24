package stage3.engine.tool.time;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import stage3.REL.program.詞条CRUD;
import stage3.engine.tool.time.impl.下一層時間_分;
import stage3.engine.tool.time.impl.下一層時間_周;
import stage3.engine.tool.time.impl.下一層時間_季;
import stage3.engine.tool.time.impl.下一層時間_日;
import stage3.engine.tool.time.impl.下一層時間_時;
import stage3.engine.tool.time.impl.下一層時間_月;
import stage3.things.id.詞条;

/*---------------------------------------------------------------------
 * 詞条
 * |--------W
 * |		|--------文件：文件名为本詞条的数据ID(例，時間対象:2018)
 * |				       文件内容为W詞条ID
 * |
 * |--------G
 * 			|--------【文件夹】：文件夹名为G詞条ID
 * 						|--------文件：文件名为G数据ID
 * 								 	   文件内容为本数据ID
 * ---------------------------------------------------------------------
 */
public class 下一層時間 {
	public List<Map> 取得下一層時間IDList(Map<String, String> 主時間Map){
		
		String sW詞条= 取得下一層時間詞条ID_byG詞条Map(主時間Map);
		Map W詞条Map = new HashMap();
		W詞条Map.put("詞条名", sW詞条);
		
		
		return 取得下一層時間詞条ID_by主詞条Map_W詞条Map(主時間Map, W詞条Map);
		
	}
	


	public List<Map> 取得下一層時間IDList(String G詞条ID, String G数据ID){
		
		String sW詞条= 取得下一層時間詞条ID_byG詞条Map(G詞条ID, G数据ID);
		
		Map 主詞条Map = new HashMap();
		主詞条Map.put("詞条ID", G詞条ID);
		主詞条Map.put("数据ID", G数据ID);
		Map W詞条Map = new HashMap();
		W詞条Map.put("詞条名", sW詞条);
		
		return 取得下一層時間詞条ID_by主詞条Map_W詞条Map(主詞条Map, W詞条Map);
		
	}
	
	private String 取得下一層時間詞条ID_byG詞条Map(Map<String, String> 主時間Map) {
		
		String G詞条ID = 主時間Map.get("詞条ID");
		String G数据ID = 主時間Map.get("数据ID");
		return 取得下一層時間詞条ID_byG詞条Map(G詞条ID, G数据ID);
		
	}
	
	private String 取得下一層時間詞条ID_byG詞条Map(String G詞条ID, String G数据ID){
		// 用年去找W，他的下一层詞条，所对应的ID
		// 配置Path取到文件、文件名为本詞条的数据ID（2018年）
		//				     文件内容为（包括，下一層時間詞条ID的众多词条）
		/*---------------------------------------------------------------------
		 * 詞条（下一層時間詞条）
		 *	|--------G
		 *			 |--------【年】詞条ID
		 *						|--------文件：文件名为G数据ID（2018的数据ID）
		 *									   文件内容为本数据ID
		 * ---------------------------------------------------------------------
		 */
		// 打开文件取出内容
		// 用下一層時間詞条所对应的数据ID、取出指定值
		//	1年
		//  2月
		//  3日
		//  4周
		//  5時
		
		詞条 o詞条 = new 詞条("");
		String s本詞条ID = o詞条.取得詞条ID_by詞条名("下一層時間");
		List<String> 本詞条数据採番idList = o詞条.検索業者数据採番idList_by本詞条IDand顧客詞条IDand顧客数据ID(
				s本詞条ID, G詞条ID, G数据ID);
		
		// 用上面取到的指定值去取词条
		List<String> s詞条IDList = new ArrayList();
		for(String s数据 : 本詞条数据採番idList) {
			
			s詞条IDList.add(o詞条.取得詞条ID_by詞条名(s数据));
		}
		
		// G詞条ID=年的词条ID
		// G数据ID=年的数据ID
		詞条CRUD o詞条CRUD = new 詞条CRUD("");
		return CollectionUtils.isEmpty(s詞条IDList)? 用Java取得下一層時間单位(o詞条CRUD.取得詞条名_by詞条ID(G詞条ID)) : s詞条IDList.get(0);
	}
	

	
	private String 用Java取得下一層時間单位(String s時間单位) {
		// 例，用Java取得年下面的时间单位
		switch(s時間单位) {
		case "年":
			return "月";
		case "月":
			return "日";
		case "日":
			return "日";
		case "周":
			return "時";
		case "季":
			return "月";
		case "時":
			return "分";
		case "分":
			return "秒";
		}
		
		return null;
	}

	// 取得下一層時間的词条ID()
	private List<Map> 取得下一層時間詞条ID_by主詞条Map_W詞条Map(Map 主詞条Map, Map w詞条Map) {
		// 主詞条Map
		//		詞条ID = 年
		//		数据ID = 2018
		
		// w詞条Map
		//		詞条ID = 月
		
		// 运用Java计算得出每一年有哪几个月
		// 基本思路是，取得当年下的最大月，当日下的最大值日，以此类推。
		// 如果是月，最少需要年
		// 如果是日，最少需要月
		// 如果是周，最少需要年或月
		// 如果是季, 最少需要年
		詞条 o詞条 = new 詞条("");
		
		下一層時間計算 o下一層時間= null;
		switch((String)w詞条Map.get("詞条名")) {
		
		case "月":
			o下一層時間 = new 下一層時間_月();
		case "日":
			o下一層時間 = new 下一層時間_日();
		case "周":
			o下一層時間 = new 下一層時間_周();
		case "季":
			o下一層時間 = new 下一層時間_季();
		case "時":
			o下一層時間 = new 下一層時間_時();
		case "分":
			o下一層時間 = new 下一層時間_分();
		}
		
		return o下一層時間.取得下一層時間(主詞条Map, w詞条Map);
	}


	// 取得下一層時間与本词条本数据的相关联数据()
	
	// 例，入口={词条=年，值=2018}
	//     出口={词条=月，值={01，02，03，04，05，06，07，08，09，10，11，12}}
	
	//下一層時間
	//     [----------1:年
	//     [----------2:月
	//     [----------3:日
	//     [----------4:時
	//     [----------5:分
	//     [----------6:秒
	//     [--------[GUEST]
	//	 			  [--------年:2
	//	 			  [--------月:3
	//	 			  [--------日:4
	//	 			  [--------時:5
	//	 			  [--------分:6
	//	 			  [--------秒:7
}
