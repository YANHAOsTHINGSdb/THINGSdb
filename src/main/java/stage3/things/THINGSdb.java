package stage3.things;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;
import stage3.consts.PublicName;
import stage3.log.MyLogger;
import stage3.things.dto.対象数据DTO;
import stage3.things.dto.数据DTO;
import stage3.things.file.文件全路径;
import stage3.things.id.ID;
import stage3.things.id.詞条;
import stage3.things.multiConditionCalc.bean.条件DTO;

@Data
public class THINGSdb {
	String sCallPath = "";
	MyLogger myLogger = new MyLogger();

	Map<String, String> 詞条Map = new HashMap<String, String>();

	public THINGSdb(String sCallPath) {
		this.sCallPath = sCallPath;
	}

	public void 追加_by数据(Map<String,Object> 追加処理对象map, List<数据DTO> 已処理数据List){

		myLogger.printCallMessage(sCallPath,"THINGSdb.追加_by数据(" +追加処理对象map.toString() +")");
		/**>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			就是前面会把录入的信息处理成【追加处理对象DTO】后，然后調用THINGSdb。
		 	例如。履歴書。他会是一大团的数据一起进来。
		 	List是什么。【追加处理对象DTO】下的List
		 	这里就是要看一看，到底是不是个单纯的追加数据的处理。
		<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/

		/**
		 *
			1 取得【追加处理对象DTO.List】

			2 如果存在值。即調用
					■ 先采番【我】
					//将采番后的词条加入到【已処理词条】

					//递归調用自身完成追加
					■ 追加_by追加处理对象( 追加处理对象DTO， 已処理数据==我 )


			3 如果List不存在值。即調用

					■追加手续_单纯追加
		 */

		/**>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			取込_(Map, 已处理数据List)｛
				如果Map的value是String
				       long s数据采番ID =
				       id.採番_by詞条名and実体数据(key, String);
				       已处理数据List.add()；

				如果Map的value还是Map
				       long s数据采番ID =
				       id.採番_by詞条名and実体数据(key, null);
				        //
				        取込_(Map, 已处理数据List);

				如果Map的value是List
				      long s数据采番ID =
				      id.採番_by詞条名and実体数据(key, null);
				      for( Map:List){
				           //
				           取込_(Map, 已处理数据List);
				      }
			｝
		 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/
		List<数据DTO> local已処理数据List = new ArrayList<数据DTO>(已処理数据List);

		// 开始【追加処理对象map】中的每一条数据
		for (Map.Entry<String, Object> entry今 : 追加処理对象map.entrySet()) {

			// 先看看【追加処理对象map】中有什么样的值。

			//o詞条.set詞条Map(詞条Map);

			if(entry今.getValue() instanceof String){

				処理対象類型_String(local已処理数据List,entry今);

			}else if(entry今.getValue() instanceof Map){

				処理対象類型_Map(local已処理数据List,entry今);

			}else if(  entry今.getValue() instanceof List){


				処理対象類型_List(local已処理数据List,entry今);
			}

		}
	}

	private void 処理対象類型_List(List<数据DTO> local已処理数据List, Entry<String, Object> entry今) {
		/**
		 *  Map<
		 *    Key1, List< Map<Key11, value>,
		 *                Map<Key21, value>
		 *              >
		 *  >
		 *
		 *  Map<
		 *    Key1, List< value,
		 *                value
		 *              >
		 *  >
		 */
		詞条 o詞条 = new 詞条(sCallPath +"処理対象類型_List");

		for(Object 追加处理对象d : (List)entry今.getValue()){

			if(追加处理对象d instanceof String){

				//String s数据采番ID = id.採番_by詞条名and実体数据(entry今.getKey(), (String)entry今.getValue());
				数据DTO 数据dto = o詞条.追加処理_単条数据(new 対象数据DTO(entry今.getKey().toString(),(String)entry今.getValue()), local已処理数据List);
				set詞条Map(数据dto, entry今.getKey().toString());
				local已処理数据List.add(new 数据DTO(数据dto.get詞条ID(), 数据dto.get数据ID()) );
			}else{

				//String s数据采番ID = id.採番_by詞条名and実体数据(entry今.getKey(), null);
				数据DTO 数据dto = o詞条.追加処理_単条数据(new 対象数据DTO(entry今.getKey().toString(),null), local已処理数据List);
				set詞条Map(数据dto, entry今.getKey().toString());
				local已処理数据List.add(new 数据DTO(数据dto.get詞条ID(), 数据dto.get数据ID()) );

				//再帰
				追加_by数据((Map<String,Object>)追加处理对象d, local已処理数据List);
			}

		}

	}

	private void 処理対象類型_Map(List<数据DTO> local已処理数据List, Entry<String, Object> entry今) {
		/**
		 * Map
		 * < Key1, Map<Key11, value> >
		 */

		詞条 o詞条 = new 詞条(sCallPath +"処理対象類型_Map");
		if(entry今.getKey() == null || StringUtils.equals(entry今.getKey(), "")){

			local已処理数据List = 追加_by数据_Map(entry今.getKey(),(Map<String, Object>)entry今.getValue(), local已処理数据List);

		}else{
			//String s数据采番ID = id.採番_by詞条名and実体数据(entry今.getKey(), null);
			数据DTO 数据dto = o詞条.追加処理_単条数据(new 対象数据DTO(entry今.getKey().toString(),null), local已処理数据List);
			set詞条Map(数据dto, entry今.getKey().toString());
			local已処理数据List.add(new 数据DTO(数据dto.get詞条ID(), 数据dto.get数据ID()) );

			//再帰
			追加_by数据((Map<String, Object>)entry今.getValue(), local已処理数据List);
		}

	}

	private void 処理対象類型_String(List<数据DTO> local已処理数据List, Entry<String, Object> entry今) {

		String sKey = null;
		詞条 o詞条 = new 詞条(sCallPath +"処理対象類型_String");

		if(StringUtils.isNumeric(entry今.getKey())){

			sKey = 詞条Map.get(local已処理数据List.get(local已処理数据List.size()-1).get詞条ID());
		}else{
			sKey = entry今.getKey();
		}
		//String s数据采番ID = id.採番_by詞条名and実体数据(sKey, (String)entry今.getValue());
		数据DTO 数据dto = o詞条.追加処理_単条数据(new 対象数据DTO(sKey,entry今.getValue().toString()), local已処理数据List);
		set詞条Map(数据dto, entry今.getKey().toString());
		local已処理数据List.add(new 数据DTO(数据dto.get詞条ID(), 数据dto.get数据ID()) );

	}

	private void set詞条Map(数据DTO 数据dto, String value) {
		if(this.詞条Map.get(数据dto.get詞条ID()) == null){
			詞条Map.put(数据dto.get詞条ID(), value);
		}
	}
	/**
	 *
	 * @param key
	 * @param 追加処理对象map
	 * @param 已処理数据List
	 * @return
	 */
	private List<数据DTO> 追加_by数据_Map(String key, Map<String, Object> 追加処理对象map, List<数据DTO> 已処理数据List) {
		myLogger.printCallMessage(sCallPath,"THINGSdb.追加_by数据_Map( key="+ key+", Map="+追加処理对象map.toString()+")");
		詞条 o詞条 = new 詞条(sCallPath +"追加_by数据_Map");

		List<数据DTO> local已処理数据List = new ArrayList<数据DTO>(已処理数据List);
		ID id = new ID(sCallPath + "追加_by数据_Map");
		for (Map.Entry<String, Object> entry今 : 追加処理对象map.entrySet()) {

			//String s数据采番ID = id.採番_by詞条名and実体数据(key, (String)entry今.getValue());
			//local已処理数据List.add(new 数据DTO(key, s数据采番ID) );
			数据DTO 数据dto = o詞条.追加処理_単条数据(new 対象数据DTO(entry今.getKey().toString(), (String)entry今.getValue()), local已処理数据List);
			set詞条Map(数据dto, entry今.getKey().toString());
			local已処理数据List.add(new 数据DTO(数据dto.get詞条ID(), 数据dto.get数据ID()) );
		}

		return local已処理数据List;
	}

	public void 事前準備() {
		/**
		 * 为了提升运算速度，避免每次进行事前准备
		 */
		if(! 判断是否需要事前准备()) {
			return;
		}

		myLogger.printCallMessage(sCallPath,"THINGSdb.事前準備()");
		詞条 o詞条 = new 詞条(sCallPath +"事前準備");

		String s詞条id = null;
		s詞条id = o詞条.取得詞条ID_by詞条名_Primeval("詞条");
		if(StringUtils.isEmpty(s詞条id))o詞条.追加詞条_by詞条名("詞条");

		s詞条id = o詞条.取得詞条ID_by詞条名_Primeval("別名");
		if(StringUtils.isEmpty(s詞条id))o詞条.追加詞条_by詞条名("別名");

		s詞条id = o詞条.取得詞条ID_by詞条名_Primeval("保存方式");
		if(StringUtils.isEmpty(s詞条id))o詞条.追加詞条_by詞条名("保存方式");

		s詞条id = o詞条.取得詞条ID_by詞条名_Primeval("保存形式");
		if(StringUtils.isEmpty(s詞条id))o詞条.追加詞条_by詞条名("保存形式");

	}

	private boolean 判断是否需要事前准备() {
		/**
		 * ture    进行事前准备
		 * false 不进行事前准备
		 */
		// 如果以下文件存在，则不必进行事前准备了
		// 如果存在，则需要进行事前准备

		String s词条文件全路径 = PublicName.KEY_数据路径
				+ PublicName.KEY_路径分隔符 + PublicName.KEY_THINGSdb词条ID
				+ PublicName.KEY_路径分隔符 + PublicName.KEY_采番ID文件;

		文件全路径 o文件全路径 = new 文件全路径();
		return ! o文件全路径.is文件全路径真实有效(s词条文件全路径);
	}

	/**
	 * 包含等于
	 * @param 値
	 * @param s詞条名
	 * @return
	 */
	public List<Map> 模糊検索_by指定値and対象詞条名(String 値, String s詞条名){

		詞条 o詞条 = new 詞条("");
		String s詞条id = o詞条.取得詞条ID_by詞条名(s詞条名);

		// 課題：模糊查询怎么个搞法
		// 策略就是:凡是字符串类型的词条，就.进.行【字符串莫夫查询】
		return o詞条.検索数据採番ID_by詞条IDand実体数据_模糊検索(s詞条id, 値);

	}

	/**
	 * 大于，非大于等于，即排除等于
	 * @param 開始値
	 * @param 終了値
	 * @param 条件dto
	 * @return
	 */
	public List<Map> 区間検索_by開始値and終了値and条件DTO(String 開始値, String 終了値, 条件DTO 条件dto){

		詞条 o詞条 = new 詞条("");
		String s詞条id = o詞条.取得詞条ID_by詞条名(条件dto.get項目());

		// 課題：到底是数值区间还是日期/时.间.区区間
		// 策略就是:先根据词条/每条数据的属性来判断。
		//          日期/年份/月份/季份

		return o詞条.検索数据採番ID_by詞条IDand実体数据_区間検索(s詞条id, 開始値, 終了値, 条件dto.getFORMAT());
	}

	public List<Map> 区間検索_by開始値and終了値and条件Map(String 開始値, String 終了値, Map 条件Map){

		詞条 o詞条 = new 詞条("");
		String s詞条id = o詞条.取得詞条ID_by詞条名((String)条件Map.get("項目"));

		// 課題：到底是数值区间还是日期/时.间.区区間
		// 策略就是:先根据词条/每条数据的属性来判断。
		//          日期/年份/月份/季份

		return o詞条.検索数据採番ID_by詞条IDand実体数据_区間検索(s詞条id, 開始値, 終了値, (String)条件Map.get("FORMAT"));
	}

}