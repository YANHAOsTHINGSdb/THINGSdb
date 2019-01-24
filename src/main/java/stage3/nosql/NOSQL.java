package stage3.nosql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import lombok.Data;
import stage3.REL.program.詞条CRUD;
import stage3.consts.PublicName;
import stage3.engine.bean.執行器結果DTO;
import stage3.engine.bean.辞書BeanDTO;
import stage3.engine.bean.辞書項目DTO;
import stage3.engine.run.implement.計算解析_程序数据執行器;
import stage3.engine.tool.SDP;
import stage3.log.MyLogger;
import stage3.things.dto.数据DTO;
import stage3.things.id.ID;
import stage3.things.id.詞条;
import stage3.things.multiConditionCalc.CRUDer;
import stage3.things.relations.業者;

@Data
public class NOSQL {
	String sCallPath = null;
	MyLogger myLogger = new MyLogger();

	public NOSQL(String sCallPath) {
		this.sCallPath = sCallPath;
	}

	private List IDList = new ArrayList<String>(Arrays.asList("1", "2", "3", "4"));
	/**
	 * 与関係
	 * |-----------------大関係
	 * |					|--------------------年齢
	 * |					|--------------------入力1
	 * |
	 * |-----------------小関係
	 * |					|--------------------年齢
	 * |					|--------------------入力2
	 * |
	 * |-----------------等関係
	 * 						|--------------------資格取得年月日
	 * 						|--------------------入力3
	 *
	 *
	 */
	private Map<String, String> map入力 = new HashMap<String, String>() {
		{
			put("入力1", "18");
			put("入力2", "40");
			put("入力3", "20170101");
			put("入力4", "");
		}
	};
	private Map<String, String> map年齢 = new HashMap<String, String>() {
		{
			put("1", "39");
			put("2", "29");
			put("3", "49");
			put("4", "19");
		}
	};
	private Map<String, String> map生年月日 = new HashMap<String, String>() {
		{
			put("1", "19800704");
			put("2", "19900704");
			put("3", "19700704");
			put("4", "20000704");
		}
	};

	private Map<String, String> map名前 = new HashMap<String, String>() {
		{
			put("1", "Aさん");
			put("2", "Bさん");
			put("3", "Cさん");
			put("4", "Dさん");
		}
	};

	private Map<String, String> map性別 = new HashMap<String, String>() {
		{
			put("1", "男");
			put("2", "男");
			put("3", "女");
			put("4", "女");
		}
	};

	private Map<String, String> map資格取得年月日 = new HashMap<String, String>() {
		{
			put("3", "20150101");
			put("4", "20170101");
		}
	};

	/**
	 *
	 */
	public void NOSQL() {

	}

	/**
	 *
	 */
	public void NOSQL(int map入力条件, int map対象数据) {

	}

	/**
	 *
	 */
	public List<辞書BeanDTO> 取得プログラム数据_根据辞書ID(String 辞書ID) {

		return IDList;

/*		switch (辞書ID) {

		case "路線検索辞書":
			return 取得プログラム数据_路線検索辞書();

		case "辞書1":
			return 取得プログラム数据_1();

		case "辞書2":
			return 取得プログラム数据_1();

		case "乘車路線計算辞書":
			return 取得プログラム数据_路線検索辞書();

		case "基站検索辞書":
			return 取得プログラム数据_基站検索辞書();

		default:
			return 取得プログラム数据_1();
		}*/
	}

	/**
	 * 通过开始，终点两词条ID来取得指定的程序
	 * @param s起点辞書ID
	 * @param s終点辞書ID
	 * @param iFlg
	 * @return
	 */
	public List<辞書BeanDTO> 取得プログラム数据_by起点辞書ID_終点辞書ID(String s起点辞書ID, String s終点辞書ID, int iFlg) {

		/*		List<String> 采番IDList = 取得实体数据采番ID_根据词条名and实体数据("程序开始词条", 起点词条名)；
						遍历采番IDList : 取得每个采番ID{

							List<数据DTO> 业者数据DTOList = 业者.取得业者数据_根据词条名and实体数据采番ID("程序开始词条", 采番ID)；
							if (判断是否存在对象(业者数据DTOList, 终点词条)) {
								return 业者数据DTOList;
							}
						}
						程序词条id = 取得词条ID_根据词条名（“程序”）；
						取得程序词条实体数据ID_根据业者数据(业者数据DTOList)；
							//在业者数据中，根据词条ID（程序），找到对应的实体数据
							遍历业者数据DTOList : 取得每个数据DTO{
								if(数据DTO.get词条ID（）. Equals（程序词条id）){
									程序数据id = 数据DTO.get数据ID();
								}
							}
						取得程序数据_根据程序数据采番ID(程序数据id);
							//程序数据属于上一个版本的实体书据，需要特别提取
		*/
		ID oID = new ID(sCallPath+"取得プログラム数据_根据辞書ID()");
		詞条 o詞条 = new 詞条(PublicName.KEY_EMPTYBLANK);
		String s詞条ID_程序開始詞条 = o詞条.取得詞条ID_by詞条名(PublicName.KEY_程序開始詞条);
		String s詞条ID_程序終点詞条 = o詞条.取得詞条ID_by詞条名(PublicName.KEY_程序終点詞条);
		//String 采番ID_程序開始 = oID.取得数据采番ID_by詞条IDand実体数据(s詞条ID_程序開始詞条, s起点辞書ID);

		業者 o業者 = new 業者(sCallPath+"取得プログラム数据_根据辞書ID()");
		List<数据DTO> 業者数据DTOList = o業者.取得業者数据DTOList_by詞条名and実体数据(PublicName.KEY_程序開始詞条, s起点辞書ID);

		if (判断業者数据中是否存在対象(業者数据DTOList, s詞条ID_程序終点詞条, s終点辞書ID)) {

			return null;
		}

		String s詞条ID_程序 = o詞条.取得詞条ID_by詞条名(PublicName.KEY_程序);
		List<String> s采番ID_程序List = 取得業者数据中指定対象_根据業者詞条ID(業者数据DTOList, s詞条ID_程序);
		//在業者数据中，根据詞条ID（程序），找到对应的实体数据

		return 取得程序数据_根据程序数据采番ID(s采番ID_程序List.get(0));

	}

	/**
	 * 首先程序是以文件的形式存储
	 * 其文件全路径被存在【程序】詞条下
	 * 通过ID可以取得
	 * @param s程序数据采番ID
	 * @return
	 */
	private List<辞書BeanDTO> 取得程序数据_根据程序数据采番ID(String s程序数据采番ID) {

		List<辞書BeanDTO> 辞書BeanDTOList = new ArrayList<辞書BeanDTO>();
		詞条 o詞条 = new 詞条(sCallPath+"取得程序数据_根据程序数据采番ID()");
		String s詞条ID_程序 = o詞条.取得詞条ID_by詞条名("程序");
		ID oID = new ID(sCallPath+"取得程序数据_根据程序数据采番ID()");

		String s程序文件全路径 = o詞条.取得実体数据_by詞条IDand数据採番ID(s詞条ID_程序, s程序数据采番ID);

		try {

			File file = new File(s程序文件全路径);

			if (checkBeforeReadfile(file)) {
				BufferedReader br = new BufferedReader(new FileReader(file));

				String str;
				while ((str = br.readLine()) != null) {
					String s程序[] = str.split(",");
					辞書BeanDTO 辞書Beandto = new 辞書BeanDTO(s程序[0], s程序[1], s程序[2], s程序[3]);
					辞書BeanDTOList.add(辞書Beandto);
				}

				br.close();
			} else {
				System.out.println("ファイルが見つからないか開けません");
			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}

		return 辞書BeanDTOList;
	}

	private static boolean checkBeforeReadfile(File file) {
		if (file.exists()) {
			if (file.isFile() && file.canRead()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * (以下加入[業者Class])
	 * @param 業者数据dtoList
	 * @param s詞条ID_程序
	 * @return
	 */
	private List<String> 取得業者数据中指定対象_根据業者詞条ID(List<数据DTO> 業者数据dtoList, String s詞条ID_程序) {
		List<String> 程序IDList = new ArrayList();
		for (数据DTO 業者数据dto : 業者数据dtoList) {
			if (業者数据dto.get詞条ID().equals(s詞条ID_程序)) {
				程序IDList.add(業者数据dto.get数据ID());
			}
		}
		return 程序IDList;
	}

	/**
	 * (以下加入[業者Class])
	 * 判断業者数据中是否存在対象
	 * @param 業者数据dtoList
	 * @param s対象辞書id
	 * @param s対象実体数据id
	 * @return
	 */
	private boolean 判断業者数据中是否存在対象(List<数据DTO> 業者数据dtoList, String s対象辞書id, String s対象実体数据id) {
		//已经取出对象信息：業者数据dtoList
		// 遍历 業者数据dtoList

		for (数据DTO 業者数据dto : 業者数据dtoList) {
			if (業者数据dto.get詞条ID().equals(s対象辞書id) && 業者数据dto.get数据ID().equals(s対象実体数据id)) {
				return true;
			}
		}
		return false;
	}

	/**
	 *
	 * @return
	 */
/*	public List<辞書BeanDTO> 取得プログラム数据_1() {
		List<辞書BeanDTO> プログラム数据List = new ArrayList();
		//		プログラム数据List.add(new 辞書Bean("","技術者検索辞書","与項目","入力1"));
		//		プログラム数据List.add(new 辞書Bean("","技術者検索辞書","与項目","入力2"));
		//		プログラム数据List.add(new 辞書Bean("","技術者検索辞書","与項目","入力3"));
		//		プログラム数据List.add(new 辞書Bean("","入力1","辞書","入力辞書"));
		//		プログラム数据List.add(new 辞書Bean("","入力2","辞書","入力辞書"));
		//		プログラム数据List.add(new 辞書Bean("","入力3","辞書","入力辞書"));
		//
		//		プログラム数据List.add(new 辞書Bean("","技術者検索辞書","検索条件","b1"));

		プログラム数据List.add(new 辞書BeanDTO("1", "b1", "計算", "与関係辞書"));
		プログラム数据List.add(new 辞書BeanDTO("2", "b1", "主", "c1"));
		プログラム数据List.add(new 辞書BeanDTO("3", "b1", "次", "c2"));
		プログラム数据List.add(new 辞書BeanDTO("4", "b1", "次", "c3"));

		プログラム数据List.add(new 辞書BeanDTO("5", "c1", "計算", "大関係辞書"));
		プログラム数据List.add(new 辞書BeanDTO("6", "c1", "主", "d1"));
		プログラム数据List.add(new 辞書BeanDTO("7", "c1", "次", "d2"));

		プログラム数据List.add(new 辞書BeanDTO("8", "d1", "辞書", "技術者辞書"));
		プログラム数据List.add(new 辞書BeanDTO("9", "d1", "項目", "年齢"));

		プログラム数据List.add(new 辞書BeanDTO("10", "d2", "辞書", "技術者検索辞書"));
		プログラム数据List.add(new 辞書BeanDTO("11", "d2", "項目", "入力1"));

		プログラム数据List.add(new 辞書BeanDTO("12", "c2", "計算", "小関係辞書"));
		プログラム数据List.add(new 辞書BeanDTO("13", "c2", "主", "e1"));
		プログラム数据List.add(new 辞書BeanDTO("14", "c2", "次", "e2"));

		プログラム数据List.add(new 辞書BeanDTO("15", "e1", "辞書", "技術者辞書"));
		プログラム数据List.add(new 辞書BeanDTO("16", "e1", "項目", "年齢"));

		プログラム数据List.add(new 辞書BeanDTO("17", "e2", "辞書", "技術者検索辞書"));
		プログラム数据List.add(new 辞書BeanDTO("18", "e2", "項目", "入力2"));

		プログラム数据List.add(new 辞書BeanDTO("19", "c3", "計算", "等関係辞書"));
		プログラム数据List.add(new 辞書BeanDTO("20", "c3", "主", "f1"));
		プログラム数据List.add(new 辞書BeanDTO("21", "c3", "次", "f2"));

		プログラム数据List.add(new 辞書BeanDTO("22", "f1", "辞書", "技術者辞書"));
		プログラム数据List.add(new 辞書BeanDTO("23", "f1", "項目", "資格取得年月日"));

		プログラム数据List.add(new 辞書BeanDTO("24", "f2", "辞書", "技術者検索辞書"));
		プログラム数据List.add(new 辞書BeanDTO("25", "f2", "項目", "入力3"));

		return プログラム数据List;
	}*/

	/**
	 *
	 * @return
	 */

/*	public List<辞書BeanDTO> 取得プログラム数据_路線検索辞書() {
		List<辞書BeanDTO> プログラム数据List = new ArrayList();
		//		プログラム数据List.add(new 辞書Bean("","技術者検索辞書","与項目","入力1"));
		//		プログラム数据List.add(new 辞書Bean("","技術者検索辞書","与項目","入力2"));
		//		プログラム数据List.add(new 辞書Bean("","技術者検索辞書","与項目","入力3"));
		//		プログラム数据List.add(new 辞書Bean("","入力1","辞書","入力辞書"));
		//		プログラム数据List.add(new 辞書Bean("","入力2","辞書","入力辞書"));
		//		プログラム数据List.add(new 辞書Bean("","入力3","辞書","入力辞書"));
		//
		//		プログラム数据List.add(new 辞書Bean("","技術者検索辞書","検索条件","b1"));

		プログラム数据List.add(new 辞書BeanDTO("1", "a1", "字典", "駅"));
		プログラム数据List.add(new 辞書BeanDTO("1.1", "a1", "入力项目", "駅名"));
		プログラム数据List.add(new 辞書BeanDTO("1.2", "a1", "外部入力", "入力1"));

		プログラム数据List.add(new 辞書BeanDTO("2", "a2", "字典", "駅"));
		プログラム数据List.add(new 辞書BeanDTO("2.1", "a2", "入力项目", "駅名"));
		プログラム数据List.add(new 辞書BeanDTO("2.2", "a2", "外部入力", "入力2"));

		プログラム数据List.add(new 辞書BeanDTO("3", "b1", "字典", "基站"));
		プログラム数据List.add(new 辞書BeanDTO("4", "b1", "入力字典", "駅"));
		プログラム数据List.add(new 辞書BeanDTO("5", "b1", "入力", "a1"));

		プログラム数据List.add(new 辞書BeanDTO("6", "b2", "字典", "基站"));
		プログラム数据List.add(new 辞書BeanDTO("7", "b2", "入力字典", "駅"));
		プログラム数据List.add(new 辞書BeanDTO("8", "b2", "入力", "a2"));

		プログラム数据List.add(new 辞書BeanDTO("9", "b3", "計算", "交集判断計算器"));
		プログラム数据List.add(new 辞書BeanDTO("10", "b3", "主", "b1"));
		プログラム数据List.add(new 辞書BeanDTO("11", "b3", "次", "b2"));

		プログラム数据List.add(new 辞書BeanDTO("12", "b4", "計算", "IF計算器"));
		//		プログラム数据List.add(new 辞書Bean("13","b4","主","b31"));						//主（条件）
		プログラム数据List.add(new 辞書BeanDTO("13", "b4", "主", "b3")); //主（条件）
		//		プログラム数据List.add(new 辞書Bean("14","b4","次","EXIT"));  					//EXIT=退出程序
		プログラム数据List.add(new 辞書BeanDTO("14", "b4", "次", "DONE")); //（TRUE）DONE=找到一条
		プログラム数据List.add(new 辞書BeanDTO("15", "b4", "次", "b43")); //（FALSE）

		プログラム数据List.add(new 辞書BeanDTO("17", "b43", "計算", "FOR計算器")); //処理完。会回退到原点，才能叫FOR
		プログラム数据List.add(new 辞書BeanDTO("18", "b43", "主", "b432"));
		プログラム数据List.add(new 辞書BeanDTO("19", "b43", "次", "b4311"));

		プログラム数据List.add(new 辞書BeanDTO("20", "b432", "計算", "FOR項目計算器")); //每次会从b1里取一个值，反退給FOR計算器
		プログラム数据List.add(new 辞書BeanDTO("21", "b432", "主", "b1"));
		//		プログラム数据List.add(new 辞書Bean("22","b431","計算","FOR出力計算器"));		//循環処理的对象
		//		プログラム数据List.add(new 辞書Bean("23","b431","主","b4311"));
		プログラム数据List.add(new 辞書BeanDTO("24", "b4311", "計算", "FOR計算器"));
		プログラム数据List.add(new 辞書BeanDTO("25", "b4311", "主", "b433"));
		プログラム数据List.add(new 辞書BeanDTO("26", "b4311", "次", "b45"));

		プログラム数据List.add(new 辞書BeanDTO("27", "b433", "計算", "FOR項目計算器"));
		プログラム数据List.add(new 辞書BeanDTO("28", "b433", "主", "b2"));

		//		プログラム数据List.add(new 辞書Bean("29","b4351","計算","FOR出力計算器"));
		//		プログラム数据List.add(new 辞書Bean("30","b4351","主","b45"));

		プログラム数据List.add(new 辞書BeanDTO("31", "b45", "計算", "List相加計算器"));
		プログラム数据List.add(new 辞書BeanDTO("32", "b45", "主", "b44"));
		//		プログラム数据List.add(new 辞書BeanDTO("33","b45","次","b433"));
		プログラム数据List.add(new 辞書BeanDTO("33", "b45", "次", "a1"));

		プログラム数据List.add(new 辞書BeanDTO("31", "b44", "計算", "List相加計算器"));
		プログラム数据List.add(new 辞書BeanDTO("32", "b44", "主", "b451"));
		//		プログラム数据List.add(new 辞書BeanDTO("33","b44","次","b432"));
		プログラム数据List.add(new 辞書BeanDTO("33", "b44", "次", "a2"));

		プログラム数据List.add(new 辞書BeanDTO("34", "b451", "字典", "基站路線"));
		プログラム数据List.add(new 辞書BeanDTO("35", "b451", "入力字典", "基站"));
		プログラム数据List.add(new 辞書BeanDTO("36", "b451", "入力字典", "基站"));
		プログラム数据List.add(new 辞書BeanDTO("37", "b451", "入力", "b432"));
		プログラム数据List.add(new 辞書BeanDTO("38", "b451", "入力", "b433"));
		//
		//		プログラム数据List.add(new 辞書Bean("39","b5","計算","List相加計算器"));
		//		プログラム数据List.add(new 辞書Bean("40","b5","主","b451"));
		//		プログラム数据List.add(new 辞書Bean("41","b5","次","b432"));
		//
		//		プログラム数据List.add(new 辞書Bean("39","b6","計算","List相加計算器"));
		//		プログラム数据List.add(new 辞書Bean("40","b6","主","b5"));
		//		プログラム数据List.add(new 辞書Bean("41","b6","次","b433"));

		プログラム数据List.add(new 辞書BeanDTO("42", "b5", "出力", "b45"));

		return プログラム数据List;
	}

	public List<辞書BeanDTO> 取得プログラム数据_基站検索辞書() {
		List<辞書BeanDTO> プログラム数据List = new ArrayList();
		プログラム数据List.add(new 辞書BeanDTO("1", "c1", "字典", "基站"));
		プログラム数据List.add(new 辞書BeanDTO("1.1", "c1", "入力项目", "ID"));
		プログラム数据List.add(new 辞書BeanDTO("1.2", "c1", "外部入力", "入力1"));

		プログラム数据List.add(new 辞書BeanDTO("2", "c2", "字典", "基站"));
		プログラム数据List.add(new 辞書BeanDTO("2.1", "c2", "入力项目", "ID"));
		プログラム数据List.add(new 辞書BeanDTO("2.2", "c2", "外部入力", "入力2"));

		プログラム数据List.add(new 辞書BeanDTO("3", "d1", "字典", "基站"));
		プログラム数据List.add(new 辞書BeanDTO("3.1", "d1", "出力項目", "相臨基站"));
		プログラム数据List.add(new 辞書BeanDTO("3.2", "d1", "入力項目", "ID"));
		プログラム数据List.add(new 辞書BeanDTO("3.3", "d1", "入力", "c1"));

		プログラム数据List.add(new 辞書BeanDTO("6", "d2", "字典", "基站"));
		プログラム数据List.add(new 辞書BeanDTO("7", "d2", "出力項目", "相臨基站"));
		プログラム数据List.add(new 辞書BeanDTO("7", "d2", "入力項目", "ID"));
		プログラム数据List.add(new 辞書BeanDTO("8", "d2", "入力", "c2"));

		//		プログラム数据List.add(new 辞書Bean("17","b43","計算","FOR計算器"));		//処理完。会回退到原点，才能叫FOR
		//		プログラム数据List.add(new 辞書Bean("18","b43","主",  "b432"));
		//		プログラム数据List.add(new 辞書Bean("19","b43","次",  "b4311"));
		//
		//		プログラム数据List.add(new 辞書Bean("20","b432","計算","FOR項目計算器"));	//每次会从b1里取一个值，反退給FOR計算器
		//		プログラム数据List.add(new 辞書Bean("21","b432","主",  "b1"));
		//
		//		プログラム数据List.add(new 辞書Bean("24","b4311","計算","FOR計算器"));
		//		プログラム数据List.add(new 辞書Bean("25","b4311","主",  "b433"));
		//		プログラム数据List.add(new 辞書Bean("26","b4311","次",  "b3"));
		//
		//		プログラム数据List.add(new 辞書Bean("27","b433","計算","FOR項目計算器"));
		//		プログラム数据List.add(new 辞書Bean("28","b433","主",  "b2"));

		//		プログラム数据List.add(new 辞書Bean("34",  "b451","字典","基站路線"));
		//
		//		プログラム数据List.add(new 辞書Bean("35",  "b451","入力字典","基站"));
		//		プログラム数据List.add(new 辞書Bean("36",  "b451","入力字典","基站"));
		//		プログラム数据List.add(new 辞書Bean("37",  "b451","入力","b432"));
		//		プログラム数据List.add(new 辞書Bean("38",  "b451","入力","b433"));

		プログラム数据List.add(new 辞書BeanDTO("9", "d3", "計算", "交集取得計算器"));
		プログラム数据List.add(new 辞書BeanDTO("10", "d3", "主", "d1"));
		プログラム数据List.add(new 辞書BeanDTO("11", "d3", "次", "d2"));

		プログラム数据List.add(new 辞書BeanDTO("12", "d21", "計算", "IF計算器"));
		プログラム数据List.add(new 辞書BeanDTO("13", "d21", "主", "d211")); //主（条件
		//		プログラム数据List.add(new 辞書Bean("13","b4","主",  "b3"));				//主（条件）
		プログラム数据List.add(new 辞書BeanDTO("14", "d21", "次", "d22")); //（TRUE）DONE=找到一条
		プログラム数据List.add(new 辞書BeanDTO("14", "d21", "次", "RETURN")); //（TRUE）RETURNE=放弃本次计算。返回

		プログラム数据List.add(new 辞書BeanDTO("12", "d22", "計算", "IF計算器"));
		プログラム数据List.add(new 辞書BeanDTO("13", "d22", "主", "d221")); //主（条件
		//		プログラム数据List.add(new 辞書Bean("13","b4","主",  "b3"));				//主（条件）
		プログラム数据List.add(new 辞書BeanDTO("14", "d22", "次", "d4")); //（TRUE）DONE=找到一条
		プログラム数据List.add(new 辞書BeanDTO("14", "d22", "次", "RETURN")); //（TRUE）DONE=找到一条

		プログラム数据List.add(new 辞書BeanDTO("15", "d211", "計算", "非空計算器"));
		プログラム数据List.add(new 辞書BeanDTO("16", "d211", "主", "d1"));

		プログラム数据List.add(new 辞書BeanDTO("15", "d221", "計算", "非空計算器"));
		プログラム数据List.add(new 辞書BeanDTO("16", "d221", "主", "d2"));

		プログラム数据List.add(new 辞書BeanDTO("12", "d4", "計算", "IF計算器"));
		プログラム数据List.add(new 辞書BeanDTO("13", "d4", "主", "d31")); //主（条件
		//		プログラム数据List.add(new 辞書Bean("13","b4","主",  "b3"));				//主（条件）
		プログラム数据List.add(new 辞書BeanDTO("14", "d4", "次", "d43")); //（TRUE）DONE=找到一条
		//		プログラム数据List.add(new 辞書Bean("15","b4","次",  "b43"));				//（FALSE）

		プログラム数据List.add(new 辞書BeanDTO("15", "d31", "計算", "非空計算器"));
		プログラム数据List.add(new 辞書BeanDTO("16", "d31", "主", "d3"));

		プログラム数据List.add(new 辞書BeanDTO("17", "d43", "計算", "FOR計算器")); //処理完。会回退到原点，才能叫FOR
		プログラム数据List.add(new 辞書BeanDTO("18", "d43", "主", "d432"));
		プログラム数据List.add(new 辞書BeanDTO("19", "d43", "次", "d5"));

		プログラム数据List.add(new 辞書BeanDTO("20", "d432", "計算", "FOR項目計算器")); //每次会从b1里取一个值，反退給FOR計算器
		プログラム数据List.add(new 辞書BeanDTO("21", "d432", "主", "d3"));

		プログラム数据List.add(new 辞書BeanDTO("39", "d5", "計算", "List相加計算器"));
		プログラム数据List.add(new 辞書BeanDTO("40", "d5", "主", "c1"));
		プログラム数据List.add(new 辞書BeanDTO("41", "d5", "次", "d6"));

		プログラム数据List.add(new 辞書BeanDTO("39", "d6", "計算", "List相加計算器"));
		プログラム数据List.add(new 辞書BeanDTO("40", "d6", "主", "c2"));
		プログラム数据List.add(new 辞書BeanDTO("41", "d6", "次", "d432"));

		プログラム数据List.add(new 辞書BeanDTO("42", "d7", "出力", "d5"));

		return プログラム数据List;
	}*/

	/**
	 *
	 */
	public List<Map> 取得指定項目数据_根据条件値and出力項目List(Map 入力情報Map, List<辞書項目DTO> o出力項目List) {

		// o条件List : 入力1 = 小伝馬町;入力2 = 浦安;
		//
		// 結果List =
		// Map1<>=小伝馬町 ,,,1,,,浦安
		// Map2<>=小伝馬町 ,,,2,,,浦安
		// Map3<>=小伝馬町 ,,,3,,,浦安

		//		String 入力字典ID = (String)入力情報List.get(0).get("入力辞書");
		String 入力字典ID = "";
		String 出力字典ID = o出力項目List.get(0).get辞書ID();
		//		List<Map>入力数据 = new ArrayList();
		//		for(Map map : 入力情報List){
		//			入力数据.add((String)map.get("入力辞書"));
		//		}

		//		List<Map> 結果List = new ArrayList<Map>();
		//		Map map結果 = new HashMap();
		//		Map map条件 = 入力情報List.get(0);
		//		辞書項目 o辞書項目_目標 = o出力項目List.get(0);
		//
		//		switch(o辞書項目_目標.get項目ID()){
		//		case "年齢":
		//			map結果.put("年齢", map年齢.get(map条件.get("ID")));
		//			結果List.add(map結果);
		//			break;
		//		case "入力1":
		//			結果List.add(map入力);
		//			break;
		//		case "入力2":
		//			結果List.add(map入力);
		//			break;
		//		case "入力3":
		//			結果List.add(map入力);
		//			break;
		//		case "資格取得年月日":
		//			map結果.put("資格取得年月日", map資格取得年月日.get(map条件.get("ID")));
		//			結果List.add(map結果);
		//			break;
		//		}

		return 取得指定値_根据入力字典and出力字典and入力数据(入力字典ID, 出力字典ID, 入力情報Map);
	}

	/**
	 *
	 */
	public List<String> 取得指定項目数据_根据条件値and出力項目List1(List<Map> o条件List, List<辞書項目DTO> o出力項目List) {
		List<String> 結果List = new ArrayList();
		Map map結果 = new HashMap();
		Map map条件 = o条件List.get(0);
		辞書項目DTO o辞書項目_目標 = o出力項目List.get(0);

		switch (o辞書項目_目標.get項目ID()) {
		case "年齢":
			map結果.put("年齢", map年齢.get(map条件.get("ID")));
			結果List.add(map年齢.get(map条件.get("ID")));
			break;
		case "入力1":
			結果List.add(map入力.get("入力1"));
			break;
		case "入力2":
			結果List.add(map入力.get("入力2"));
			break;
		case "入力3":
			結果List.add(map入力.get("入力3"));
			break;
		case "資格取得年月日":
			map結果.put("資格取得年月日", map資格取得年月日.get(map条件.get("ID")));
			結果List.add(map資格取得年月日.get(map条件.get("ID")));
			break;
		}

		return 結果List;
	}

	/**
	 *
	 * @param s辞書ID
	 * @return
	 */
	private List<String> 取得採番IDList_根据辞書ID(String s辞書ID) {

		return this.IDList;

	}

	/**
	 *
	 * @param s辞書ID
	 * @return
	 */
	public List<Map> 取得全業務数据_根据辞書ID(String s辞書ID) {
		List<Map> 業務数据List = new ArrayList();
		//		for(String sID : 取得指定辞書IDList(s辞書ID)){
		//			Map 業務数据map = new HashMap();
		//			業務数据map.put("ID", sID);
		//			業務数据map.put("生年月日", map生年月日.get(sID));
		//			業務数据map.put("名前", map名前.get(sID));
		//			業務数据map.put("性別", map性別.get(sID));
		//			業務数据map.put("資格取得年月日", map資格取得年月日.get(sID));
		//			業務数据List.add(業務数据map);
		//		}

		for (String sID : 取得採番IDList_根据辞書ID(s辞書ID)) {
			Map 業務数据map = new HashMap();
			業務数据map.put("ID", sID);
			業務数据map.put("辞書ID", s辞書ID);
			業務数据List.add(業務数据map);
		}
		return 業務数据List;

	}

	public Map 取得業務数据_根据辞書IDand採番ID(Map m業務数据) {

		Map 業務数据map = new HashMap();
		業務数据map.put("ID", m業務数据.get("ID"));
		業務数据map.put("生年月日", map生年月日.get(m業務数据.get("ID")));
		業務数据map.put("名前", map名前.get(m業務数据.get("ID")));
		業務数据map.put("性別", map性別.get(m業務数据.get("ID")));
		業務数据map.put("資格取得年月日", map資格取得年月日.get(m業務数据.get("ID")));

		return 業務数据map;
	}

	/**
	 *
	 * @param 入力字典ID
	 * @param 出力字典ID
	 * @param 入力数据
	 * @return
	 */
	public List<Map> 取得指定値_根据入力字典and出力字典and入力数据(String 入力字典ID, String 出力字典ID, Map 入力情報Map) {
		final Logger logger = Logger.getLogger("SampleLogging");
		/**	   1 ■【辞書関係識別器】.取得两辞書関係_根据辞書Aand辞書B
		    	//例：取得【駅】与【乘车路线】的关系
		   2 ■ 取得结果值_根据两字典关系
		        switch（两字典关系）
		        case 家族：return ■【辞書Service】.取得实体数据_根据字典IDand項目IDand入力数据
		        case 朋友：return ■【NOSQL朋友圈】.取得实体数据_根据字典Aand字典Band
		*/

		// o条件List : 入力1 = 小伝馬町;入力2 = 浦安;
		//
		// 結果List =
		// Map1<>=小伝馬町 ,,,1,,,浦安 (出力字典ID, v1)(入力字典ID, v2)(入力字典IDvalue1, x1)(出力字典IDvalue1, value1.1)(出力字典IDvalue2, value1.2)
		// Map2<>=小伝馬町 ,,,2,,,浦安 (出力字典ID, v1)(入力字典ID, v2)(入力字典IDvalue1, x1)(出力字典IDvalue1, value2.1)(出力字典IDvalue2, value2.2)
		// Map3<>=小伝馬町 ,,,3,,,浦安 (出力字典ID, v1)(入力字典ID, v2)(入力字典IDvalue1, x1)(出力字典IDvalue1, value3.1)(出力字典IDvalue2, value3.2)

		執行器結果DTO 計算結果数据 = null;
		List<Map> 計算出力数据 = new ArrayList<Map>();

		辞書関係識別器 o辞書関係識別器 = new 辞書関係識別器(sCallPath+"NOSQL.取得指定値_根据入力字典and出力字典and入力数据(入力字典ID="+入力字典ID+",出力字典ID="+出力字典ID+")");

		String s両辞書関係 = o辞書関係識別器.取得两辞書関係_根据辞書Aand辞書B(入力字典ID, 出力字典ID);
		switch (s両辞書関係) {
		case PublicName.KEY_直接:
		case PublicName.KEY_家族:

			return 取得実体数据_根据字典Aand字典Band入力数据(入力字典ID, 出力字典ID, 入力情報Map);

		case PublicName.KEY_非直接:
		case PublicName.KEY_朋友:
			NOSQL朋友圈 oNOSQL朋友圈 = new NOSQL朋友圈();
			計算結果数据 = oNOSQL朋友圈.取得実体数据_根据字典Aand字典Band入力数据(入力字典ID, 出力字典ID, 入力情報Map);

		}

		for (Map<String, String> 結果数据 : 計算結果数据.get計算結果()) {
			Map<String, String> 業務数据map = new HashMap<String, String>();
			業務数据map.put(出力字典ID, 結果数据.get(計算結果数据.getS出力結果Key()));
			計算出力数据.add(業務数据map);
		}
		logger.info("  NOSQL計算結果出力：計算出力数据 = " + 計算出力数据);
		return 計算出力数据;
	}

	public List<Map> 取得指定値_根据入力字典and出力字典and入力情報Map(辞書項目DTO 入力字典, 辞書項目DTO 出力字典, Map 入力情報Map) {

		/**	   1 ■【辞書関係識別器】.取得两辞書関係_根据辞書Aand辞書B
				//例：取得【駅】与【乘车路线】的关系
			   2 ■ 取得结果值_根据两字典关系
			        switch（两字典关系）
			        case 家族：return ■【辞書Service】.取得实体数据_根据字典IDand項目IDand入力数据
			        case 朋友：return ■【NOSQL朋友圈】.取得实体数据_根据字典Aand字典Band
		*/

		// o条件List : 入力1 = 小伝馬町;入力2 = 浦安;
		//
		// 結果List =
		// Map1<>=小伝馬町 ,,,1,,,浦安 (出力字典ID, v1)(入力字典ID, v2)(入力字典IDvalue1, x1)(出力字典IDvalue1, value1.1)(出力字典IDvalue2, value1.2)
		// Map2<>=小伝馬町 ,,,2,,,浦安 (出力字典ID, v1)(入力字典ID, v2)(入力字典IDvalue1, x1)(出力字典IDvalue1, value2.1)(出力字典IDvalue2, value2.2)
		// Map3<>=小伝馬町 ,,,3,,,浦安 (出力字典ID, v1)(入力字典ID, v2)(入力字典IDvalue1, x1)(出力字典IDvalue1, value3.1)(出力字典IDvalue2, value3.2)

		辞書関係識別器 o辞書関係識別器 = new 辞書関係識別器(sCallPath+"NOSQL.取得指定値_根据入力字典and出力字典and入力情報Map()");

		String s両辞書関係 = o辞書関係識別器.取得两辞書関係_根据辞書Aand辞書B(入力字典.get辞書ID(), 出力字典.get辞書ID());
		switch (s両辞書関係) {
		case PublicName.KEY_家族:

			// 如果是【家族】关系的话，直接通过THINGSdb取得吧
			return 取得実体数据_根据字典Aand字典Band入力数据(入力字典.get辞書ID(), 出力字典.get辞書ID(), 入力情報Map);

		case PublicName.KEY_朋友:

			// 如果是【朋友】关系的话，先找到SDP，再用该SDP取得数据

			return 用SDP方式取得実体数据_byFrom词条ID_To词条ID_入力情報Map(入力字典, 出力字典, 入力情報Map);
		}

		return null;

	}

	private List<Map> 用SDP方式取得実体数据_byFrom词条ID_To词条ID_入力情報Map(辞書項目DTO 入力字典, 辞書項目DTO 出力字典, Map 入力情報Map) {
		SDP sdpObject = new SDP("");

		List<String> 取得SDP的SDP代码List = sdpObject.先通过固定方式取的_取得目标数据的计算方法SDP的SDP();
		List<String> SDP代码List = 取得SDP代码List_byFrom词条ID_To词条ID(取得SDP的SDP代码List, 入力字典.get辞書ID(), 出力字典.get辞書ID());

		return 取得目标值_通过执行_SDP代码List_入力情報Map(SDP代码List, 入力情報Map);
	}

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
 *
 * @param 取得sdp的sdp代码List
 * @param From词条ID
 * @param To条ID
 * @return
 */
	public List<String> 取得SDP代码List_byFrom词条ID_To词条ID(List<String> 取得sdp的sdp代码List, String From词条ID, String To条ID) {
		List<Map> SDP代码List =取得SDP代码List_通过执行_取得目标数据的计算方法SDP的SDP(取得sdp的sdp代码List, From词条ID, To条ID);
		return 提取SDP代码List_bySDP代码MapList(SDP代码List);
	}

	private List<String> 提取SDP代码List_bySDP代码MapList(List<Map> sDP代码List) {
		// 先取得【出口】的对象词条ID
		// 再取得出口的值（Map<String, List>）
		// 取出Map中的List

		return null;
	}

	public List<Map> 取得SDP代码List_通过执行_取得目标数据的计算方法SDP的SDP(List<String> sDP代码List, String from词条ID, String to词条ID) {
		詞条CRUD o詞条CRUD = new 詞条CRUD("");
		Map 入力情報Map =new HashMap();
		入力情報Map.put("from词条ID", from词条ID);
		入力情報Map.put("to词条ID", to词条ID);
		入力情報Map.put("from词条", o詞条CRUD.取得詞条名_by詞条ID(from词条ID));
		入力情報Map.put("to词条", o詞条CRUD.取得詞条名_by詞条ID(to词条ID));

		return 取得目标值_通过执行_SDP代码List_入力情報Map(sDP代码List, 入力情報Map);
	}

	// 纯纯的一个转换器
	private List<Object> toListObject(List<String> 程序代码List) {
		List<Object> 程序代码ListObject = new ArrayList();
		for(String s : 程序代码List) {
			程序代码ListObject.add(s.split(","));
		}
		return 程序代码ListObject;
	}

	/**
	 * 查找了一下，类似的Relation中也有一个类似的功能，
	 * 就是通过执行SDP来检索数据
	 * 但是，Relation是面向用户UI的
	 *      NOSQL是 PRO 与 THINGSdb之间的桥梁啊！
	 * 功能类似，，暂时采用分开写。因为设计还没有稳定下来。
	 * @param sSDP代码List
	 * @param to词条ID
	 * @param from词条ID
	 * @return
	 */
	private List<Map> 取得目标值_通过执行_SDP代码List_入力情報Map(List<String> sSDP代码List, Map 入力情報Map) {
		計算解析_程序数据執行器 o計算解析_程序数据執行器 = new 計算解析_程序数据執行器(sCallPath+"取得目标值_通过执行_取得目标数据的计算方法SDP的SDP");

		SDP sdp = new SDP("取得目标值_通过执行_取得目标数据的计算方法SDP的SDP");
		List<辞書BeanDTO> 程序代码_辞書BeanDTO_List = sdp.get辞書BeanDTOList_by程序代码List(toListObject(sSDP代码List));

		// TODO 返回值可能包含原始的计算数据，这个还没有弄
		return o計算解析_程序数据執行器.執行程序数据_根据プログラム数据and業務数据(
				程序代码_辞書BeanDTO_List, 入力情報Map);

	}

	/**
	 * 字典内部检索。例。用字典项目的具体值找到其采番ID。
	 *
	 */
	public List<Map> 取得辞書IDList_根据入出力字典and入力項目and入力値List(String 入出力字典ID, String 入力項目名, Map 入力情報Map) {
		List<Map> 結果数据List = new ArrayList<Map>();

		Map<String, String> map = new HashMap<String, String>();
		map.put(入出力字典ID, (String) 入力情報Map.get(入力項目名));

		結果数据List.add(map);
		return 結果数据List;
	}
	/**
	 *
	 * @param 出力字典ID
	 * @param 入口名
	 * @param 入力情報Map
	 * @return
	 */
	public List<Map> 取得实体采番IDList_根据出力字典and计算式名and入力情報Map(String 出力字典ID, String 计算式名, Map 入力情報Map) {
		List<Map> 結果数据List = new ArrayList<Map>();

		/*
		 * 1， C1， 出力词条， 基站    //计算式之后两种：1，字典计算
		 *  【计算式名】     【入力值】
		 * 2， C1， 入口，    入力1   //每个SDP必须有最少一个入口
		 */

		Map<String, String> map = new HashMap<String, String>();
		map.put(出力字典ID, (String) 入力情報Map.get(计算式名));

		結果数据List.add(map);
		return 結果数据List;
	}
	/**
	 * 字典内部检索。例。用字典ID的具体值找到其它指定项目。
	 *
	 */
	public List<Map> 取得辞書IDList_根据入出力字典and出力項目andIDList(String 入出力字典ID, String 入力項目, Map<String, String> 入力情報Map) {
		List<Map> 結果数据List = new ArrayList<Map>();

		//TODO 根据入力项目取得采番ID
		Map<String, String> map = new HashMap<String, String>();

		String sValue = null;
		String 基站 = 入力情報Map.get(PublicName.KEY_ID);
		switch (基站) {
		case "上野":
		case "西船桥":
		case "西船橋":
		case "茅場町":
		case "大手町":
		case "日本橋":
		case "銀座":
		case "秋葉原":
			sValue = 取得可到駅(基站);
			break;
		}
		map.put(入出力字典ID, sValue);

		結果数据List.add(map);
		return 結果数据List;
	}

	/**
	 *
	 * @param 基站
	 * @return
	 */
	private String 取得可到駅(String 基站) {
		String sResult = null;
		for (List<String> 基站line : 基站lines) {

			if (基站line.contains(基站)) {
				if (sResult == null) {
					sResult = 取得基站駅(基站, 基站line);
				} else {
					sResult = sResult + "," + 取得基站駅(基站, 基站line);
				}
			}
		}
		return 去除重複項目(sResult);
	}

	/**
	 *
	 * @param s基站List
	 * @return
	 */
	private String 去除重複項目(String s基站List) {
		Map<String, Integer> res = new HashMap<>();

		String sResult = null;

		String[] 個別values = s基站List.split(",");
		if (個別values.length > 1) {
			for (int i = 0; i < 個別values.length; i++) {
				if (res.containsKey(個別values[i])) {

				} else {
					res.put(個別values[i], 1);
					if (sResult == null) {
						sResult = 個別values[i];
					} else {
						sResult = sResult + "," + 個別values[i];
					}
				}
			}
		} else {
			return s基站List;
		}
		return sResult;
	}

	/**
	 *
	 * @param from基站
	 * @param 基站line
	 * @return
	 */
	private String 取得基站駅(String from基站, List<String> line基站) {
		String sResult = null;
		for (String to基站 : line基站) {
			if (sResult == null) {
				sResult = to基站;
			} else if (!to基站.equals(from基站)) {
				sResult = sResult + "," + to基站;
			}
		}
		return sResult;
	}

	List<String> 基站line1 = Arrays.asList("渋谷", "表参道", "赤坂見附", "溜池山王", "新橋", "銀座", "日本橋", "三越前", "神田", "上野広小路", "上野",
			"浅草");
	List<String> 基站line2 = Arrays.asList("新宿", "新宿三丁目", "四ツ谷", "赤坂見附", "国会議事堂前", "霞ヶ関", "銀座", "東京", "大手町", "淡路町",
			"御茶ノ水", "本郷三丁目", "後楽園", "池袋");
	List<String> 基站line3 = Arrays.asList("中目黒", "恵比寿", "六本木", "霞ヶ関", "日比谷", "東銀座", "八丁堀", "茅場町", "人形町", "秋葉原", "仲御徒町",
			"上野", "南千住", "北千住");
	List<String> 基站line4 = Arrays.asList("中野", "高田馬場", "飯田橋", "九段下", "大手町", "日本橋", "茅場町", "門前仲町", "西船橋");
	List<String> 基站line5 = Arrays.asList("押上", "浅草", "蔵前", "浅草橋", "東日本橋", "人形町", "日本橋", "東銀座", "新橋", "大門", "三田", "五反田");
	List<String> 基站line6 = Arrays.asList("渋谷", "恵比寿", "目黒", "五反田", "大崎", "品川", "浜松町", "新橋", "有楽町", "東京", "神田", "秋葉原",
			"上野", "日暮里", "西日暮里", "駒込", "巣鴨", "大塚", "池袋", "高田馬場", "新宿", "代々木", "原宿");

	List<List> 基站lines = Arrays.asList(
			基站line1, 基站line2, 基站line3, 基站line4, 基站line5, 基站line6);


	/**
	 *
	 * 単純な辞書の実体データから検索する。
	 *
	 * @param 入力字典ID
	 * @param 出力字典ID
	 * @param 入力数据List
	 * @return
	 */
	private List<Map>取得実体数据_根据字典Aand字典Band入力数据(String 入力字典ID, String 出力字典ID, Map 入力情報Map){

		List<Map> resultListMap = new ArrayList();
		Map map = new HashMap();

		//------------------------------------------------------
		//【计算结果】 = 取得目标词条_by条件词条
		// 如果【计算结果】为空，
		// 调用 程序来尝试解决问题
		//    先通过条件与目标词条来查询既存程序
		//    再通过程序执行器来计算结果
		//
		//    如果条件词条中有时间值这样的词条
		//    (到底实用程序来弄，还是用程序来弄以下逻辑)
		//------------------------------------------------------
		//【计算结果】 = 取得目标词条_by条件词条

		// 如果【计算结果】为空

		// 调用 程序来尝试解决问题

//		//	    先通过条件与目标词条来查询既存程序
//		詞条CRUD o詞条CRUD =  new 詞条CRUD(sCallPath+"取得実体数据_根据字典Aand字典Band入力数据");
//		Map<String, Object> crudMap = null;
//		if(! CollectionUtils.isEmpty(o詞条CRUD.取得程序代码_byCRUD(crudMap))){
//			// 如果没有搞到程序
//			return null;
//		}
//		// 搞到程序
//		//	    再通过程序执行器来计算结果
//		程序数据執行器 o程序数据執行器 = new 計算解析_程序数据執行器(sCallPath+"取得実体数据_根据字典Aand字典Band入力数据");
//		String s計算辞書名 = null;
//		o程序数据執行器.run_根据計算辞書IDand入力情報(s計算辞書名, 入力情報Map);

		Map 条件1Map = new HashMap();
		条件1Map.put(PublicName.KEY_值, 入力情報Map	.get(入力字典ID));
		条件1Map.put(PublicName.KEY_目标词条ID, 出力字典ID);
		条件1Map.put(PublicName.KEY_词条ID, 入力字典ID);
		条件1Map.put(PublicName.KEY_计算符号, PublicName.KEY_等于);

		Map searchMap = new HashMap();
		searchMap.put(PublicName.KEY_操作, PublicName.KEY_检索);
		searchMap.put(PublicName.KEY_目标词条ID, 出力字典ID);
		searchMap.put(PublicName.KEY_条件, 条件1Map);

		CRUDer crudER = new CRUDer("");

		return crudER.search(searchMap);
	}
}
