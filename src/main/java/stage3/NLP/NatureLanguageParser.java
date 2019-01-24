package stage3.NLP;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class NatureLanguageParser {

	/**
	 * 通过NLP，将NL转成CRUD的方法
	 *   暂时只针对 【搜索 XXXX】这样低智商的语句
	 * 		如果找不到呢。只能分开
	 * 		動詞默认为【搜索】
	 * 		名词：【找到第一个名词】
	 *
	 * @return
	 *
	 * {GW, sNL_名詞}
	 *
	 */
	public Map createCRUD_byNL(Map mapNL){

		// 查找【搜索】类似的名词
		String sNL_動詞 = get_word_Search(mapNL);

		if(! StringUtils.isEmpty(sNL_動詞)) {
			return make_CRUD_Json(mapNL, sNL_動詞);
		}
		return null;

	}

	private Map make_CRUD_Json(Map mapNL, String sNL_動詞) {
		String sNL = (String)mapNL.get("NL");
		Map<String, String> map_GW = new HashMap();

		String sNL_名詞 = sNL.replaceAll(sNL_動詞, "");
		map_GW.put("GW", sNL_名詞);
		return map_GW;
	}

	private String get_word_Search(Map mapNL) {
		String s = (String) mapNL.get("NL");

		if(s.indexOf("Search") != -1) {
			return "Search";
		}

		if(s.indexOf("搜索") != -1) {
			return "搜索";
		}

		if(s.indexOf("检索") != -1) {
			return "检索";
		}

		if(s.indexOf("查找") != -1) {
			return "查找";
		}

		if(s.indexOf("検索") != -1) {
			return "検索";
		}
		return null;
	}
	/**
	 * 比起fudanNLP的工具
	 * 我更倾向StandforNLP的工具
	 *
	 * 因为NLP的第一个工作就是输出语法树
	 *
	 * 然后，我可以继续根据语法树，设计入库方案
	 *
	 *                      2019-1-14 记
	 */
}
