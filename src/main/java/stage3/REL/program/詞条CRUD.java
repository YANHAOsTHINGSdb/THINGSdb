package stage3.REL.program;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import stage3.REL.io.MyFileReader;
import stage3.REL.io.file.FileReader_BufferedReader;
import stage3.engine.tool.ClassObject;
import stage3.engine.tool.SDP;
import stage3.log.MyLogger;
import stage3.nosql.NOSQL;
import stage3.things.dto.数据DTO;
import stage3.things.file.文件全路径;
import stage3.things.id.ID;
import stage3.things.id.詞条;
import stage3.things.relations.業者;
import stage3.things.relations.顧客;
//
/*---------------------------------------------------------------
詞条信息map（CRUD）
|		key		value（Object）
|-------操作		追加/检索	 String			注，只有两种：追加/删除
|-------目标		例，SDP	 String			注，随便一个词条名。对！是词条名，不是实体数据
|-------条件		W词条	List<Map>		注，描述这个目标词条的各种子词条信息
							|（例）
							|-------Map		key		value
							|		|-------程序名	例，累计成交量SDP			注：这个SDP的实际名称
							|		|-------詞条A1	例，股票代码				注：入口1
							|		|-------詞条A2	例，时间					注：入口2
							|		|-------詞条B1	例，累计成交量				注：出口1，暂时一个SDP就只有一个出口
							|		|-------说明		例，这是一个通过股票代码和时间来计算累计成交量的SDP
							|		|-------SDP		例，	{a1,,,}
							|							{a2,,,}
							|							{a3,,,}
							|				注，如果是SDP的CRUD，只对条件List<Map>的第一个Map进行出力
							|				问，如果判定是SDP。答，只要目标是【SDP】即可
							|-------Map
							|		|		key		value
							|		|-------姓名		A桑
							|		|-------性别		男
							|
							|				注，如果是普通词条就再说
							|-------Map
									|		key		value
									|-------案件名	某system基盘构筑
									|-------开始时间	2019-2-1
									|-------地点		神田
									|-------担当会社	株式会社
注， 这里W词条会使用List<Map>的形式也是为了保险起见
    到现在为止的设计，我只用到了一个-Map
---------------------------------------------------------------*/
public class 詞条CRUD {

	String sCallPath = null;
	MyLogger myLogger = new MyLogger();

	public 詞条CRUD(String sCallPath) {
		this.sCallPath = sCallPath;
	}

	public Map<String, String> 追加詞条信息map(Map<String, Object> 詞条信息map){
		/**
		 *  詞条信息map = {CRUD={操作=追加, 目标=股票实时情报, 条件={ ...
		 */

		myLogger.printCallMessage(sCallPath,"詞条CRUD.追加詞条信息map( )");

		if(CollectionUtils.isEmpty(詞条信息map)) {
			return null;
		}

		// I 处理条件词条List
		List<Map> W词条词条List = 追加詞条信息_处理W词条List(詞条信息map);

		// 要是没有处理结果，就算了吧
		if(CollectionUtils.isEmpty(W词条词条List)) {return null;}

		// II 处理主词条【目标】
		return 追加詞条信息_处理主词条(詞条信息map, W词条词条List);

	}

	private Map<String, String> 追加詞条信息_处理主词条(Map<String, Object> 詞条信息map, List<Map> w词条词条List) {
		/** （以下仅限追加ADD模式）
		 * I 处理主词条【目标】
		 *    1-1 为主词条采番。如果该主词条都还未存在，就干脆交给THINGSdb处理吧
		 *        注，这个主词条，一般是不带数据的，例，【社员】
		 *            它的信息都在W词条中， 例如，【社员】的【姓名】，只要建立起【社员】和【姓名】的关系即可
		 *
		 *    1-2 建立【主词条】与【条件词条】的关系（仅限追加模式）
		 *     	1-2-1 为主词条追加W词条（条件词条）的记录
		 *      1-2-2 为条件词条追加G词条（主词条）的记录️
		 */
		// 1-1 为主词条采番
		詞条 o詞条 = new 詞条(sCallPath + "追加子詞条");
		ID idObject = new ID(sCallPath + "追加子詞条");
		String 主詞条名 = (String) 詞条信息map.get("目标");
		if(StringUtils.isEmpty(主詞条名)) {return null;}

		// 如果词条不存在是可以词条采番的。
		String 主詞条数据ID = idObject.採番_by詞条名and実体数据(主詞条名, null);
		String 主詞条ID = o詞条.取得詞条ID_by詞条名(主詞条名);
		Map<String, String> 主詞条信息Map = new HashMap();
		主詞条信息Map.put("詞条ID", 主詞条ID);
		主詞条信息Map.put("数据ID", 主詞条数据ID);

		// 1-2 建立【主词条】与【条件词条】的关系（仅限追加模式）
		为主词条追加W词条关系信息(主詞条信息Map, w词条词条List);
		为W词条追加主词条关系信息(主詞条信息Map, w词条词条List);

		return 主詞条信息Map;
	}

	private void 为W词条追加主词条关系信息(Map<String, String> 主詞条信息Map, List<Map> w词条词条List) {
		// 首先，你要有主词条
		// 遍历每个W词条
		for(Map w词条 : w词条词条List){
			// 调用 追加Guest関係（）
			追加Guest関係(主詞条信息Map, w词条);
		}
	}

	private void 为主词条追加W词条关系信息(Map<String, String> 主詞条信息Map, List<Map> w词条词条List) {
		// 首先，你要有主词条
		// 遍历每个W词条
		for(Map w词条 : w词条词条List){
			// 调用 追加Waiter关系（）
			追加Waiter関係(主詞条信息Map, w词条);
		}
	}

	private List<Map> 追加詞条信息_处理W词条List(Map<String, Object> 詞条信息map) {
		// 首先W词条，就是所谓的条件词条，它是List<Map>的类型
//		if(CollectionUtils.isEmpty((Map<String, Object>)詞条信息map.get("条件"))) {return null;}

		if(ClassObject.checkObjectIsEmpty(詞条信息map.get("条件"))) {return null;}
		List<Map> W词条List = null;
		if(詞条信息map.get("条件") instanceof Map) {
			W词条List = Arrays.asList((Map)詞条信息map.get("条件"));
		}else if(詞条信息map.get("条件") instanceof List) {
			W词条List = (List<Map>) 詞条信息map.get("条件");
		}else {
			// 是一个BEAN时，该怎么办。
			W词条List = Arrays.asList((Map)詞条信息map.get("条件"));
		}

		List<Map> W词条信息MapList = new ArrayList();

		for(Map W词条 : W词条List) {
			// 导入W词条的数据
			// 注， 对于W词条List，会使用List<Map>的形式也是为了保险起见
		    //      到现在为止的设计，我只用到了一个-Map
			W词条信息MapList.addAll(追加W词条的数据_处理每一个w词条Map(W词条, 詞条信息map));
		}
		// 返回List<W词条信息Map>
		return W词条信息MapList;
	}

	/**
	 *
	 * @param w词条
	 * @param 詞条信息map
	 * @return
	 */
	private List<Map> 追加W词条的数据_处理每一个w词条Map(Map<String, Object> w词条, Map<String, Object> 詞条信息map) {
		// 1-1 	取得Map下的每一个Entry
		// 1-2  如果Entry的Value是一个List
		// 1-3  如果Entry的Value是一个非List
		// 1-4  做成W词条的词条信息Map<词条ID，数据ID>

		List<Map> W词条信息MapList = new ArrayList();
		for(Map.Entry<String, Object> entry:w词条.entrySet()) {
			// 1-1 	取得Map下的每一个Entry
			Map W词条信息Map = new HashMap();
			詞条 o詞条 = new 詞条(sCallPath + "处理每一个w词条Map");
			String 数据ID = null;

			if(StringUtils.equals(ClassObject.取得对象Object的Type(entry.getValue()), "List")){
				// 1-2  如果Entry的Value是一个List
				//数据ID = 追加W词条的数据_文件流程(entry.getKey(), entry.getValue(), 詞条信息map);
				/*
				 * 因为【NLPwhat】下的【listWhat】是个list形式。
				 * 这个List代表修饰其主【NLPwhat】的
				 * 所以，于此既存设计冲突了。
				 * 对策，就是分别对应，返回List<词条DTO>
				 *                           |----词条ID
				 *                           |----数据ID
				 */
				return 追加W词条的数据_by辅臣List(entry.getKey(), entry.getValue(), 詞条信息map);
			}
			else if(StringUtils.equals(ClassObject.取得对象Object的Type(entry.getValue()), "Bean")){
				// 1-4 如果Entry的Value是一个Bean
				数据ID = 追加W词条的Bean数据_by构造词条信息Map(entry.getKey(), entry.getValue()).get("数据ID");
			}
			else {
				// 1-3  如果Entry的Value是一个非List
				数据ID = 追加W词条的数据_正常流程(entry.getKey(), entry.getValue());
			}
			// 1-4  做成W词条的词条信息Map<词条ID，数据ID>
			W词条信息Map.put("詞条ID", o詞条.取得詞条ID_by詞条名(entry.getKey()));
			W词条信息Map.put("数据ID", 数据ID);
			W词条信息MapList.add(W词条信息Map);
		}
		return W词条信息MapList;
	}
	/**
	 * 这个是专门针对辅臣List做的对应处理
	 *
	 * @param key
	 * @param value
	 * @param 詞条信息map
	 * @return
	 */
	private List<Map> 追加W词条的数据_by辅臣List(String key, Object value, Map<String, Object> 詞条信息map) {
		// ----------------------------------
		// 先把每一个辅臣做好词条追加的处理
		// 然后把每一个辅臣的信息都放入一个【Map】中，
		// 最后返回信息集合即可。
		// ----------------------------------
		List<Map> resultMapList = new ArrayList();
		List<Object> W辅臣信息ObjectList = (ArrayList)value;
		詞条 o詞条 = new 詞条(sCallPath + "追加W词条的数据_by辅臣List");
		String s词条ID = o詞条.取得詞条ID_by詞条名(key);
		for(Object W辅臣信息Object : W辅臣信息ObjectList) {
			String 数据ID = 追加W词条的Bean数据_by构造词条信息Map(key, W辅臣信息Object).get("数据ID");
			Map<String, String> resultMap = new HashMap();
			resultMap.put("词条ID", s词条ID);
			resultMap.put("数据ID", 数据ID);
			resultMapList.add(resultMap);
		}
		return resultMapList;
	}

	/**
	* 将bean内的信息登陆THINGSdb数据库
	* @param key    句子
	* @param value  Bean
	* @param 詞条信息map
	* @return
	*/
	private Map<String, String> 追加W词条的Bean数据_by构造词条信息Map(String 詞条名, Object value) {
		/*===========================================
		 *	  2.1 构造 词条信息Map
		 *
		 *	---------------词条信息Map的构造---------------------
		 *	词条信息Map< key,   value>
		 *	   |-------条件    条件Map< key,   value>
		 *	                   |------什么    NlpWhat
		 *	---------------------------------------------------
		 *
		 *	  2.2 2.3 递归调用【词条CRUD.追加词条信息Map】，返回其结果值
		 ===========================================*/

		// 2.1 构造 词条信息Map
		// 取得Bean的Class名
		// 取得Bean的属性名列表
		// 取得Bean的属性列表下的每个属性的值
		Map<String, Object> 詞条信息map = new HashMap();
//		ObjectMapper oMapper = new ObjectMapper();

		詞条信息map.put("操作", "追加");
		詞条信息map.put("对象", ClassObject.get对象名ByClassName(value));
		詞条信息map.put("条件", ClassObject.把Bean的第一层转成Map(value));


		// 2.2 2.3 递归调用【词条CRUD.追加词条信息Map】，返回其结果值

		return 追加詞条信息map(詞条信息map);
	}

	private String 追加W词条的数据_文件流程(String 詞条名, Object 値value, Map g詞条信息) {
		return 追加List数据到文件_返回数据ID( 詞条名, (List<String>) 値value,  g詞条信息);
	}

	private String 追加W词条的数据_正常流程(String s词条名, Object o实际数据) {
		// 这一块有些混乱了，想不起THINGSdb的追加_by数据()有什么特别的了
		ID idObject = new ID(sCallPath + "处理每一个w词条Map");
		String s实际数据=null;
//		if( o实际数据 instanceof Long) {
//			s实际数据 = Long.toString((Long)o实际数据);
//		}
//		if( o实际数据 instanceof Integer) {
//			s实际数据 = Integer.toString((int)o实际数据);
//		}
//		if( o实际数据 instanceof Double) {
//			s实际数据 = Double.toString((Double)o实际数据);
//		}
//		if( o实际数据 instanceof Float) {
//			s实际数据 = Float.toString((Float)o实际数据);
//		}
//		if( o实际数据 instanceof String) {
//			s实际数据 = (String)o实际数据;
//		}
		s实际数据 = o实际数据.toString();
		if(StringUtils.isEmpty(s实际数据)) {
			return null;
		}
		return idObject.採番_by詞条名and実体数据(s词条名, s实际数据);
	}

	private String 追加List数据到文件_返回数据ID(String 詞条名, List<String> 値valueList, Map g詞条信息) {
		// 程序数据ID = 词条. 采番数据ID_by词条ID(程序词条ID);
		// 需要以文件形式保存
		// 需要有文件名、路径
		// 真正的值是文件的全路径啊
		// String 全路径 = 做成全路径(文件名, 路径)
		// 追加文件(String 全路径, (List)Map.value)

		/*---------------------------------------------------------------
		// 内容
		// |
		// |-----文件名
		// |-----路径
		// |-----程序代码
		 * |
		---------------------------------------------------------------*/
		myLogger.printCallMessage(sCallPath,"詞条CRUD.追加文件(詞条名="+詞条名+",g詞条信息"+g詞条信息+" )");
		ID id = new ID(sCallPath + "追加子詞条");

		// 处理过程
		// 1 取得即将要存入数据的对象文件的全路径
		// 2 将上步取得的文件全路径作为【实体数据】与【目标词条ID】进行采番
		// 3 将具体的值List，存入上步取得的文件全路径
		// 4 返回采番ID

		// 1 取得即将要存入数据的对象文件的全路径
		詞条ListData o詞条ListData = new 詞条ListData(g詞条信息);
		String s文件全路径 = o詞条ListData.取得ListData数据文件全路径(g詞条信息);

		// 2 将上步取得的文件全路径作为【实体数据】与【目标词条ID】进行采番
		String 数据ID = id.採番_by詞条名and実体数据(詞条名, s文件全路径);

		// 3 将具体的值List，存入上步取得的文件全路径
		//   目前策略就是，{根路径}/{词条ID}/{数据ID}.ListData
		o詞条ListData.保存数据DataList(g詞条信息, 値valueList);

		// 4 返回采番ID
		return 数据ID;

	}

//	private String 追加实体数据(String 詞条名, String valueObject, Map g詞条信息2) {
//		myLogger.printCallMessage(sCallPath,"詞条CRUD.追加实体数据(詞条名="+詞条名+",valueObject"+valueObject+" )");
//		// G词条信息 = 设置词条信息
//		// 程序数据ID = 追加实体数据(G词条信息, Map.value)，且返回数据采番ID
//
//		ID id = new ID(sCallPath + "追加子詞条");
//
//		String 数据ID = id.採番_by詞条名and実体数据(詞条名, valueObject);
//
//		return 数据ID;
//	}

//	private String 追加子詞条(String 主詞条名, String s主词条id, List<Map> 条件信息MapList, Map 詞条信息Map) {
//		myLogger.printCallMessage(sCallPath,"詞条CRUD.追加子詞条(詞条名="+主詞条名+" )");
//
//		// 詞条 o詞条 = new 詞条(sCallPath + "追加子詞条");
//		ID idObject = new ID(sCallPath + "追加子詞条");
//
//		// 程序数据ID = 词条. 采番数据ID_by词条ID(程序词条ID);
//		// (如果实体数据为空，则采空番)
//		String 主詞条数据ID = idObject.採番_by詞条名and実体数据(主詞条名, null);
//
//		// 设置G词条信息(程序词条ID,  程序数据ID);
//		Map<String,String> 主詞条信息Map = 設置詞条信息(s主词条id, 主詞条数据ID, 詞条信息Map);
//
//		// 追加GW关系信息(List<Map>,  G词条信息)
//		追加GW关系信息(条件信息MapList, 主詞条信息Map);
//
//		return 主詞条数据ID;
//	}

	/*---------------------------------------------------------------
	詞条信息map（CRUD）
	|		key		value（Object）
	|-------操作		追加/检索	 String			注，只有两种：追加/删除
	|-------目标		例，SDP	 String			注，随便一个词条名。对！是词条名，不是实体数据
	|-------条件		W词条	List<Map>		注，描述这个目标词条的各种子词条信息
								|（例）
								|-------Map		key		value
								|		|-------程序名	例，累计成交量SDP			注：这个SDP的实际名称
								|		|-------詞条A1	例，股票代码				注：入口1
								|		|-------詞条A2	例，时间					注：入口2
								|		|-------詞条B1	例，累计成交量				注：出口1，暂时一个SDP就只有一个出口
								|		|-------说明		例，这是一个通过股票代码和时间来计算累计成交量的SDP
								|		|-------SDP		例，	{a1,,,}
								|							{a2,,,}
								|							{a3,,,}
								|				注，如果是SDP的CRUD，只对条件List<Map>的第一个Map进行出力
								|				问，如果判定是SDP。答，只要目标是【SDP】即可
								|-------Map
								|		|		key		value
								|		|-------姓名		A桑
								|		|-------性别		男
								|
								|				注，如果是普通词条就再说
								|-------Map
										|		key		value
										|-------案件名	某system基盘构筑
										|-------开始时间	2019-2-1
										|-------地点		神田
										|-------担当会社	株式会社
	---------------------------------------------------------------*/
//	private void 追加GW关系信息(List<Map> 条件信息MapList, Map<String, String> 主詞条信息Map) {
//		myLogger.printCallMessage(sCallPath,"詞条CRUD.追加GW关系信息( )");
//		// 分别取得Map.value.List的每个值，进行处理 {
//		for(Map 詞条map : 条件信息MapList) {
//			// W词条名 = 取得子词条名（词条A(W)，词条B(W)，说明(W)，文件(W)）
//			//String s対象詞条名 = (String)詞条map.get("詞条名");
//
//			//-------------------------------------------------------------------
//			// W词条数据ID = (递归调用)追加程序(Map<key, vaule> 子程序信息)
//			// Map<String, String> W词条数据信息 = 追加詞条信息map(詞条map);
//			//-------------------------------------------------------------------
//
//			// 主词条信息 = 设置词条信息(词条ID=, 数据ID=程序（数据ID）
//			Map<String,String> 主詞条信息result = 設置詞条信息((String)主詞条信息Map.get("詞条ID"), W词条数据信息.get("数据ID"),W词条数据信息);
//			// W词条信息 = 设置词条信息(词条ID=, 数据ID=词条A（词条ID）)
//			Map<String,String> W詞条信息result = 設置詞条信息((String)主詞条信息Map.get("詞条ID"), W词条数据信息.get("数据ID"),W词条数据信息);
//			// 追加Waiter关系(主词条信息, W词条信息)；
//			追加Waiter関係(主詞条信息Map, 主詞条信息result);
//			// 追加Guest关系(主词条信息, W词条信息)；
//			追加Guest関係(主詞条信息Map, W詞条信息result);
//		}
//
//	}

	private void 追加Guest関係(Map<String, String> G詞条信息, Map<String, String> W詞条信息) {
		myLogger.printCallMessage(sCallPath,"詞条CRUD.追加Guest関係( )");

		顧客 o顧客 = new 顧客(sCallPath + "追加Guest関係");
		数据DTO G数据dto = new 数据DTO(G詞条信息.get("詞条ID"), G詞条信息.get("数据ID"));
		数据DTO W数据dto = new 数据DTO(W詞条信息.get("詞条ID"), W詞条信息.get("数据ID"));
		o顧客.追加顧客関係_by主体数据and顧客数据(W数据dto, G数据dto);
	}

	private void 追加Waiter関係(Map<String, String> G詞条信息, Map<String, String> W詞条信息) {
		myLogger.printCallMessage(sCallPath,"詞条CRUD.追加Waiter関係( )");

		業者 o業者 = new 業者(sCallPath + "追加Waiter関係");
		数据DTO G数据dto = new 数据DTO(G詞条信息.get("詞条ID"), G詞条信息.get("数据ID"));
		数据DTO W数据dto = new 数据DTO(W詞条信息.get("詞条ID"), W詞条信息.get("数据ID"));
		o業者.追加業者関係_by主体数据and業者数据(G数据dto, W数据dto);
	}



	private Map<String,String> 設置詞条信息(String 詞条id, String 数据id, Map g詞条信息) {
		myLogger.printCallMessage(sCallPath,"詞条CRUD.設置詞条信息(詞条id="+詞条id+",数据id="+数据id+" )");

		Map<String,String> 詞条信息map = new HashMap<String,String>();
		詞条信息map.putAll(g詞条信息);
		詞条信息map.put("詞条ID",詞条id);
		詞条信息map.put("数据ID",数据id);

		return 詞条信息map;
	}


	public Map chk程序代码中計算関係完整(List<String> 程序代码List) {

		myLogger.printCallMessage(sCallPath,"詞条CRUD.chk程序代码中計算関係完整( )");

		// 结果Map
		Map map = new HashMap();
		// 就是寻找是否有孤立词条
		// 如果有：return true;

		// 把 所有b3为【字典】【项目】的所有值取出来。


		// 取得程序中【字典】与【项目】所对应的值
		// 去除重复项
		//     将项目放入【对象List】
		//     如果已经存在，则不再追加
		// 取得其中每两个词条的关系

		// 将有关系的两个词条放入【处理后】
		// List<String = "A, B" >
		//     <String = "A, C" >
		//     <String = "A, D" >
		// 梳理出词条间的关系
		// （以备一旦发现孤立词条、便于判断）
		//   将孤立的词条加入 孤立词条List
		//   进行N*N次的判断后，即可确认
		//   结果Map
		//       |-----result:有/无 孤立词条
		//       |-----孤立词条:孤立词条List

		return map;

	}

	public List<String> 取得程序代码_byCRUD(Map<String, Object> crudMap) {

	/*---------------------------------------------------------------
	crudMap（CRUD）
	|		key		value（Object）
	|-------操作		检索	 	 String			注，只有两种：追加/删除
	|-------目标		例，SDP	 String			注，随便一个词条名。对！是词条名，不是实体数据
	|-------条件		W词条	List<Map>		注，描述这个目标词条的各种子词条信息
								|（例）
								|-------Map		key		value
										|-------程序名	例，累计成交量SDP			注：这个SDP的实际名称
										|-------詞条A1	例，股票代码				注：入口1
										|-------詞条A2	例，时间					注：入口2
										|-------詞条B1	例，累计成交量				注：出口1，暂时一个SDP就只有一个出口
										|-------说明		例，这是一个通过股票代码和时间来计算累计成交量的SDP

	return 代码List<String>
	---------------------------------------------------------------*/

		myLogger.printCallMessage(sCallPath,"詞条CRUD.取得程序代码_byCRUD( )");

		List<String> 程序代码_List = new ArrayList(){
            {
                add("9,b3,計算,交集即相加計算器");
                add("10,b3,主,b1");
                add("11,b3,次,b2");
                add("12,b4,計算,IF計算器");
                add("13,b4,主（条件）,b31");
                add("14,b4,次（TRUE）,b43");
                add("15,b31,計算,非空計算器");
                add("16,b31,主,b3");
                add("17,b43,計算,FOR計算器");
                add("18,b43,主（FOR項目）,b432");
                add("19,b43,次（FOR出力）,b431");
                add("20,b432,計算,FOR項目計算器");
                add("21,b432,主,b1");
                add("22,b431,計算,FOR出力計算器");
                add("23,b431,主,b4311");
                add("24,b4311,計算,FOR計算器");
                add("25,b4311,主（FOR項目）,b433");
                add("26,b4311,次（FOR出力）,b4351");
                add("27,b433,計算,FOR出力計算器");
                add("28,b433,主,b2");
                add("29,b4351,計算,FOR出力計算器");
                add("30,b4351,主,b45");
                add("31,b45,計算,List追加計算器");
                add("32,b45,主,b45");
                add("33,b45,次,b452");
                add("34,b45,字典,基站路径");
                add("35,b45,項目,駅");
                add("36,b45,項目,駅");
                add("37,b45,入力,b432");
                add("38,b45,入力,b433");
                add("39,b5,計算,List相加計算器");
                add("40,b5,主,b3");
                add("41,b5,次,b4");
            }
        };

		//--------------------------------------------
		// crudMap
		//    |-------操作:检索
		//    |-------目标  :程序
		//    |-------条件:from/to/程序词条ID
		//--------------------------------------------
		//--------------------------------------------
		// 程序
		//    |-------id
		//    |-------GUEST
		//             |-------from
		//             |-------to
		//--------------------------------------------

        // 先取得程序ID
        // 再通过程序ID来取得程序代码
        詞条 o詞条 = new 詞条(sCallPath+"取得程序代码_byCRUD");
        MyFileReader oMyFileReader = new FileReader_BufferedReader();

        List<String> 程序代码文件地址List = new ArrayList();
        String 程序詞条ID = o詞条.取得詞条ID_by詞条名((String) crudMap.get("目标"));
        NOSQL nosql = new NOSQL("");
        /**
		 * NOSQL.用SDP方式取得実体数据_byFrom词条ID_To词条ID_入力情報Map
		 * |
		 * |---NOSQL.先通过固定方式取的_取得目标数据的计算方法SDP的SDP
		 * |---NOSQL.取得SDP代码List_byFrom词条ID_To词条ID
		 * |	|---NOSQL.取得SDP代码List_通过执行_取得目标数据的计算方法SDP的SDP
		 * |	|	|---NOSQL.取得目标值_通过执行_SDP代码List_入力情報Map
		 * |	|
		 * |	|---NOSQL.提取SDP代码List_bySDP代码MapList
		 * |
		 * |---NOSQL.取得目标值_通过执行_SDP代码List_入力情報Map
         */
        Map 条件Map = (Map) crudMap.get("条件");
        SDP sdpObject = new SDP("");
        List<String> 取得SDP的SDP代码List = sdpObject.先通过固定方式取的_取得目标数据的计算方法SDP的SDP();
        return nosql.取得SDP代码List_byFrom词条ID_To词条ID(取得SDP的SDP代码List, (String)条件Map.get("from词条ID"), (String)条件Map.get("to词条ID"));
	}

//	private List<String> 取得程序IDList__byCRUD(Map<String, Object> crudMap) {
//		//--------------------------------------------
//		// crudMap
//		//    |-------操作:检索
//		//    |-------目标:SDP代码
//		//    |-------条件:from/to/SDP_ID（通过SDP的ID，取得SDP的具体代码）
//		//--------------------------------------------
//		myLogger.printCallMessage(sCallPath,"詞条CRUD.取得程序IDList__byCRUD( )");
//
//        詞条 o詞条 = new 詞条(sCallPath+"取得程序IDList__byCRUD");
//        ID oID = new ID("取得程序IDList__byCRUD");
//        String 程序詞条ID = o詞条.取得詞条ID_by詞条名((String) crudMap.get("目标"));
//
//        // 1 先通过多条件检索，取得要去的对象（通过条件，取得 <目标词条ID，目标数据ID>）
//        // 2 再判断这些值的类型(<目标词条=SDP代码，目标数据ID=XX>)
//        //   2.1 取得W词条数据(<主词条=SDP代码，数据ID=XX，W词条=保存形式>，取得W词条数据ID)
//        // 3 如果目标词条的实体数据是文件全路径，且其属性中有文件存储字样
//        //   （当保存处理的时候，会加一个W词条，名字叫【保存形式】，
//        //     这个词条只为【List】类型的数据ID而设置）
//        // 4 就尝试通过读取指定文件取得其中内容（需要 词条ID，数据ID）
//
//        // 1 先通过多条件检索，取得要取的对象（通过条件，取得 <目标词条ID，目标数据ID>）
//        Map 目标词条Map = 取得目标词条Map_by多条件检索();
//
//        // 2 再判断这些值的类型(<目标词条=SDP代码，目标数据ID=XX>)
//        //   2.1 取得W词条数据(<主词条=SDP代码，数据ID=XX，W词条=保存形式>，取得W词条数据ID)
//        String s实体数据取得方式 = 判断目标词条的实体数据取得方式();
//
//        if(StringUtils.equals(s实体数据取得方式, "")) {
//        	// 4 就尝试通过读取指定文件取得其中内容（需要 词条ID，数据ID）
//
//        }else {
//        	// 正常取值就可以了
//        }
//
//        // 返回值采用List是有远见的。就算只有一个值，也最好用List，方便以后扩展
//	}



	public List<String> 保存程序代码_byProgram(Map<String, Object> programMap) {

		myLogger.printCallMessage(sCallPath,"詞条CRUD.保存程序代码_byProgram( )");

		//--------------------------------------------
		// 需要：一个from、to或者 直接用ID
		// 针对【程序】词条
		// 用from、to找代码的话，就会找到一堆代码
		// 用ID找代码的话，就能找到位的代码
		// 录入的CRUD信息中、需要有from、to
		// 最少要有个to
		// 最少在程序的Guest里面找到to词条。
		//--------------------------------------------
		//--------------------------------------------
		// programMap
		//    |-------from:String
		//    |-------to:String
		//    |-------code:List<String>
		//--------------------------------------------
		//--------------------------------------------
		// 程序
		//    |-------id
		//    |-------GUEST
		//             |-------from
		//             |-------to
		//--------------------------------------------

		追加詞条信息map(programMap);

		return null;
	}


	/**
	 *
	 * @param s詞条
	 * @return
	 */
	public List 取得関係詞条_検索UIListMap(String s詞条){

		myLogger.printCallMessage(sCallPath,"詞条CRUD.取得関係詞条_検索UIListMap(s詞条="+s詞条+" )");

		List 関係詞条_検索UIListMap = new ArrayList();

		関係詞条_検索UIListMap.add(new HashMap() {
            {
                put("edit", false);
                put("value", s詞条);
                put("key", "目標詞条");
            }
        });
		/*------------------------------------------------------------
		// 取得该词条的所有【顧客詞条】、【業者詞条】
		// 例、入力：s詞条=社員
		//     出力：社員的顧客詞条List、業者詞条List
		//			 Map{
		//					key=顧客, value=顧客詞条List
		//					key=業者, value=業者詞条List
		//           }
		------------------------------------------------------------*/
		// 取得这个词条的顧客一览
		// 取得这个词条的業者一览
		// 把所有信息都放入map
		詞条 o詞条 = new 詞条(sCallPath+"取得関係詞条_検索UIListMap");
		/*
		String s文件全路径 = 文件全路径.取得対象文件全路径_by類型and詞条ID("詞条路径",
				Arrays.asList(o詞条.取得詞条ID_by詞条名(s詞条)));
		if(StringUtils.isEmpty(s文件全路径)) {
			return null;
		}
*/		// 取得【社員Bean】下所有【顧客】与【業者】的詞条

		String s詞条ID = o詞条.取得詞条ID_by詞条名(s詞条);
		String s文件全路径 = 文件全路径.取得対象文件全路径_by類型and詞条ID("詞条路径",
				Arrays.asList(s詞条ID));
		/**
		 * 社員Bean
		 * 		  |
		 *        |-----GUEST
		 *        |		   |-----<...>
		 *        |
		 *        |-----WAITER
		 *       		   |-----<...>
		 *
		 */
		顧客 o顧客 = new 顧客(sCallPath+"詞条CRUD.取得関係詞条");
		List<String> 顧客詞条IDList = o顧客.取得所有Guest詞条IDList_by本詞条名(s詞条);
		業者 o業者 = new 業者(sCallPath+"詞条CRUD.取得関係詞条");
		List<String> 業者詞条IDList =  o業者.取得業者詞条IDList_by目標詞条ID(s詞条ID);

		if(! CollectionUtils.isEmpty(顧客詞条IDList)) {

			関係詞条_検索UIListMap.addAll(取得検索UIListMap_by詞条名List(取得詞条名List_by詞条IDList(顧客詞条IDList)));
		}
		if(! CollectionUtils.isEmpty(業者詞条IDList)) {

			関係詞条_検索UIListMap.addAll(取得検索UIListMap_by詞条名List(取得詞条名List_by詞条IDList(業者詞条IDList)));
		}

		return 関係詞条_検索UIListMap;
	}


	private List<Map> 取得検索UIListMap_by詞条名List(List<String> 詞条名list) {

		myLogger.printCallMessage(sCallPath,"詞条CRUD.取得検索UIListMap_by詞条名List( )");

		List<Map> 検索UIListMap = new ArrayList();

		for(String s詞条名 : 詞条名list) {

			Map 検索UIMap = new HashMap();
			検索UIMap.put("key", s詞条名);
			検索UIMap.put("value", "");
			検索UIMap.put("edit", true);
			検索UIListMap.add(検索UIMap);

		}
		return 検索UIListMap;
	}

	public List<String> 取得詞条名List_by詞条IDList(List<String> 業者詞条idList) {

		myLogger.printCallMessage(sCallPath,"詞条CRUD.取得詞条名List_by詞条IDList( )");

		List<String> 詞条名List = new ArrayList();

		for(String s詞条ID : 業者詞条idList) {

			詞条名List.add(取得詞条名_by詞条ID(s詞条ID));
		}
		return 詞条名List;
	}

	public String 取得詞条名_by詞条ID(String g詞条id) {

		myLogger.printCallMessage(sCallPath,"詞条CRUD.取得詞条名_by詞条ID( )");

		詞条 o詞条 = new 詞条(sCallPath+"取得詞条名_by詞条ID");

		return o詞条.取得実体数据_by詞条IDand数据採番ID("0000000001", g詞条id);

	}

	public List<String> 取得関係詞条IDList(String s詞条){

		myLogger.printCallMessage(sCallPath,"詞条CRUD.取得関係詞条IDList(s詞条="+s詞条+" )");

		List<String> 関係詞条IDList = new ArrayList();

		詞条 o詞条 = new 詞条(sCallPath+"詞条CRUD.取得関係詞条IDList");

		String s詞条ID = o詞条.取得詞条ID_by詞条名(s詞条);
		String s文件全路径 = 文件全路径.取得対象文件全路径_by類型and詞条ID("詞条路径",
				Arrays.asList(s詞条ID));

		顧客 o顧客 = new 顧客(sCallPath+"詞条CRUD.取得関係詞条IDList");
		List<String> 顧客詞条IDList = o顧客.取得所有Guest詞条IDList_by本詞条名(s詞条);
		業者 o業者 = new 業者(sCallPath+"詞条CRUD.取得関係詞条IDList");
		List<String> 業者詞条IDList =  o業者.取得業者詞条IDList_by目標詞条ID(s詞条ID);

		if(!CollectionUtils.isEmpty(顧客詞条IDList)) {
			関係詞条IDList.addAll(顧客詞条IDList);
		}
		if(!CollectionUtils.isEmpty(業者詞条IDList)) {
			関係詞条IDList.addAll(業者詞条IDList);
		}

		return 関係詞条IDList;
	}


	/**
	 * 数据可用文件的形式来取得，
	 * 所以，你要现对此进行区分，
	 * 用 文件方式
	 * 用 数据方式
	 * @param s词条名
	 * @param s数据ID
	 * @return
	 */
	public List<String> 取得实体数据StringList_by词条名_数据ID(String s词条名, String s数据ID) {
		 /*
		 * 1 取得对象数据的【保存方式】 取得指定数据的保存方式信息_by词条名_数据ID
		 * 2 如果是【文件方式】     文件方式取得目标数据_by_词条名_数据ID
		 * 3 如果不是             数据方式取得目标数据_by_词条名_数据ID
		 */
		List<String> 保存方式信息 = 取得指定数据的保存方式信息_by词条名_数据ID(s词条名, s数据ID);
		switch(保存方式信息.get(0)) {
		case("文件方式"):
			/**
			 * 需要先判断实体数据是否是文件的完成路径
			 * 是  返回取到的文件内容
			 * 否  返回。。该实体数据
			 */
			return 文件方式取得目标数据_by_词条名_数据ID(s词条名, s数据ID);

		default:
			/**
			 * 直接用词条CLASS取得具体值就可以了
			 * 之所以还要重新起一个函数，是因为需要【思路清晰】
			 */
			return 数据方式取得目标数据_by_词条名_数据ID(s词条名, s数据ID);
		}
	}

	/**
	 * 当发现实体数据是【】时，才会使用文件方式取得数据，就是说
	 *    实体数据其实是个文件的全路径
	 *    真实数据存储在指定文件中，
	 *    【暂】如果路径存上不存在指定文件，那就返回该全路径吧
	 * @param s词条名
	 * @param s数据id
	 * @return 真实数据
	 */
	private List<String> 文件方式取得目标数据_by_词条名_数据ID(String s词条名, String s数据id) {
		// 先取得词条与数据ID指定的实体数据
		List<String> s実体数据List = 数据方式取得目标数据_by_词条名_数据ID(s词条名, s数据id);
		詞条 o詞条 = new 詞条("");

		// 再判断这个实体数据是不是一个文件全路径
		文件全路径 o文件全路径 = new 文件全路径();
		if(o文件全路径.is文件全路径真实有效(s実体数据List.get(0))) {
			// 如果是文件路径，就用路径取得文件内容，化为List
			// 1 取得即将要存入数据的对象文件的全路径
			Map g詞条信息 = new HashMap();
			g詞条信息.put("词条ID", o詞条.取得詞条ID_by詞条名(s词条名));
			g詞条信息.put("数据ID", s数据id);
			詞条ListData o詞条ListData = new 詞条ListData(g詞条信息);

			return o詞条ListData.取得数据DataList(g詞条信息);
		}else {
			// 如果是不是文件路径，就直接返回该数据吧
			return s実体数据List;
		}

	}

	private List<String> 数据方式取得目标数据_by_词条名_数据ID(String s词条名, String s数据採番id) {
		詞条 o詞条 = new 詞条("");
		String s実体数据 = o詞条.取得実体数据_by詞条IDand数据採番ID(o詞条.取得詞条ID_by詞条名(s词条名), s数据採番id);
		return Arrays.asList(s実体数据);
	}

	private List<String> 取得指定数据的保存方式信息_by词条名_数据ID(String s词条名, String s数据ID) {
		詞条 o詞条 = new 詞条("");
		List<String> 保存方式信息List = o詞条.検索業者詞条的実体数据_by顧客詞条IDand顧客数据IDand本詞条ID(o詞条.取得詞条ID_by詞条名(s词条名), s数据ID, o詞条.取得詞条ID_by詞条名("保存方式"));
		return 保存方式信息List;
	}


}
