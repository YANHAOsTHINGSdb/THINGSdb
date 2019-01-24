package stage3.consts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public  class PublicName {
	static Properties properties = new Properties();
	//static String spath="/Users/ai1/Desktop/SpringRestfulWebServicesCRUDExample/sys.property" ;
	//static String spath="/Users/haoyan/Desktop/history/20190110/SpringRestfulWebServicesCRUDExample/sys.property" ;
	static String spath="/sys.property";
	/**
	 * 将数据库地址的信息存入property文件
	 * @param s指定信息
	 * @return
	 */
	public static String 取得指定信息(String s指定信息){
		InputStreamReader inputStreamReader = null;
		//FileInputStream fileInputStream = null;
		/**
		 * SpringRestfulWebServicesCRUDExample
		 * |
		 * |----stockInfo.property
		 */
		// 这个是从主函数所在地址参照的吗？？
		//String spath="/Users/ai1/Desktop/StockDataCaller/stockInfo.property" ;

		try {
			// fileInputStream = new FileInputStream(spath);//加载Java项目根路径下的配置文件
			InputStream inputSream = PublicName.class.getResourceAsStream(spath);
			// inputStreamReader = new InputStreamReader(fileInputStream, "UTF8");
			inputStreamReader = new InputStreamReader(inputSream, "UTF8");

			BufferedReader in = new BufferedReader(inputStreamReader);
			properties.load(in);// 加载属性文件
 			return properties.getProperty(s指定信息);

		}catch (IOException io) {
			System.out.println(io.getMessage());
		}catch(Throwable e) {
			System.out.println(e.getMessage());
		} finally {
			if (inputStreamReader != null) {
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static final String KEY_条件关系="条件关系";// 场所=CRUDer.Search，场所=MultiConditionCalc.取得伦理结果List_by伦理计算
	public static final String KEY_条件関係 = KEY_条件关系;

	public static final String KEY_条件="条件";// 场所=CRUDer.check条件，场所=MultiConditionCalc.取得伦理结果List_by伦理计算，场所=MultiConditionCalc.set子条件Map，场所=取得主时间Map_by条件Map
												// 场所=NOSQL.取得実体数据_根据字典Aand字典Band入力数据
	public static final String KEY_OK="OK" ;// 场所=CRUDer.add
	public static final String KEY_NG="NG" ;// 场所=CRUDer.add
	public static final String KEY_数据采番ID="数据采番ID" ;// 场所=MultiConditionCalc.计算_多条件关系
	public static final String KEY_数据採番ID = KEY_数据采番ID;

	public static final String KEY_and="and" ;// 场所=MultiConditionCalc.取得伦理结果List_by伦理计算
	public static final String KEY_And="And" ;// 场所=MultiConditionCalc.取得伦理结果List_by伦理计算
	public static final String KEY_or="or" ;// 场所=MultiConditionCalc.取得伦理结果List_by伦理计算
	public static final String KEY_Or="Or" ;// 场所=MultiConditionCalc.取得伦理结果List_by伦理计算
	public static final String KEY_not="not" ;// 场所=MultiConditionCalc.取得伦理结果List_by伦理计算
	public static final String KEY_Not="Not" ;// 场所=MultiConditionCalc.取得伦理结果List_by伦理计算

	public static final String KEY_左括号="(" ;// 场所=MultiConditionCalc.取得伦理结果List_by伦理计算
	public static final String KEY_右括号=")" ;// 场所=MultiConditionCalc.取得伦理结果List_by伦理计算

	public static final String KEY_0="0" ;// 场所=MultiConditionCalc.取得伦理结果List_by伦理计算
	public static final String KEY_1="1" ;// 场所=MultiConditionCalc.取得伦理结果List_by伦理计算

	public static final String KEY_直接关系="直接关系" ;// 场所=MultiConditionCalc.计算_根据条件信息，场所=MultiConditionCalc.计算结果_根据条件信息Map，场所=MultiConditionCalc.取得关系Str_by目标and条件项目
	public static final String KEY_程序关系="程序关系" ;// 场所=MultiConditionCalc.计算_根据条件信息，场所=MultiConditionCalc.计算结果_根据条件信息Map，场所=MultiConditionCalc.计算_单条件关系

	public static final String KEY_目标="目标" ;// 场所=MultiConditionCalc.整合这些分解的子时间计算结果List_To_主干上，场所=MultiConditionCalc.取得结果_Call程序数据执行器，场所=MultiConditionCalc.取得指定信息_根据条件Map
	                       // 场所=MultiConditionCalc.取得关系Str_by目标and条件项目，场所=MultiConditionCalc.取得main目标Key_by计算情报DTO，场所=MultiConditionCalc.取得main目标Key_by计算情报Map
	                       // 场所=MultiConditionCalc.取得sub目标_by计算情报DTO，场所=MultiConditionCalc.计算_无条件关系
	public static final String KEY_目標 = KEY_目标;
	public static final String KEY_整合方法="整合方法" ;// 场所=MultiConditionCalc.整合这些分解的子时间计算结果List_To_主干上
	public static final String KEY_累积 = "累积";// 场所=MultiConditionCalc.整合这些分解的子时间计算结果List_To_主干上
	public static final String KEY_汇合="汇合" ;// 场所=MultiConditionCalc.整合这些分解的子时间计算结果List_To_主干上

	public static final String KEY_项目="项目" ;// 场所=MultiConditionCalc.取得主时间Map_by条件Map，场所=MultiConditionCalc.取得结果_Call程序数据执行器，场所=MultiConditionCalc.取得关系Str_by目标and条件项目
	public static final String KEY_項目 = KEY_项目;
	public static final String KEY_值="值" ; // 场所=MultiConditionCalc.取得主时间Map_by条件Map，场所=MultiConditionCalc.取得结果_Call程序数据执行器，场所=MultiConditionCalc.取得指定信息_根据条件Map
                        // 场所=MultiConditionCalc.取得关系Str_by目标and条件项目,NOSQL.取得実体数据_根据字典Aand字典Band入力数据
	public static final String KEY_値 = KEY_值;
	public static final String KEY_入力1="入力1" ;// 场所=MultiConditionCalc.取得结果_Call程序数据执行器

	public static final String KEY_等于="等于" ;// 场所=MultiConditionCalc.取得结果_根据直接关系, NOSQL.取得実体数据_根据字典Aand字典Band入力数据
	public static final String KEY_大于="大于" ;// 场所=MultiConditionCalc.取得结果_根据直接关系
	public static final String KEY_小于="小于" ;// 场所=MultiConditionCalc.取得结果_根据直接关系
	public static final String KEY_like="like" ;// 场所=MultiConditionCalc.取得结果_根据直接关系

	public static final String KEY_ID="ID" ;// 场所=MultiConditionCalc.取得指定信息_根据词条名and条件DTO，场所=MultiConditionCalc.取得指定信息_根据条件Map，场所=MultiConditionCalc.计算_单条件关系
	                                        // 取得辞書IDList_根据入出力字典and出力項目andIDList
	public static final String KEY_词条名="词条名" ;// 场所=MultiConditionCalc.取得指定信息_根据词条名and条件DTO，场所=MultiConditionCalc.取得指定信息_根据条件Map
	public static final Object KEY_詞条名 = KEY_词条名;
	public static final String KEY_顾客路径="顾客路径" ;// 场所=MultiConditionCalc.取得指定信息_根据词条名and条件DTO，场所=MultiConditionCalc.取得关系Str_by目标and条件项目
	public static final String KEY_顧客路径 = KEY_顾客路径;
	public static final Object KEY_計算符号 = "计算符号";// 场所=MultiConditionCalc.取得結果_根据直接関係
	public static final String KEY_EMPTYBLANK = "";// NOSQL.取得プログラム数据_根据辞書ID
	public static final Object KEY_目标词条ID = "目标词条ID";// NOSQL.取得実体数据_根据字典Aand字典Band入力数据
	public static final Object KEY_词条ID = "词条ID"; // NOSQL.取得実体数据_根据字典Aand字典Band入力数据
	public static final Object KEY_计算符号 = "计算符号";// NOSQL.取得実体数据_根据字典Aand字典Band入力数据
	public static final Object KEY_操作 = "操作";// NOSQL.取得実体数据_根据字典Aand字典Band入力数据
	public static final Object KEY_检索 = "检索";// NOSQL.取得実体数据_根据字典Aand字典Band入力数据
	public static final String KEY_朋友 = "朋友";// NOSQL.取得指定値_根据入力字典and出力字典and入力情報Map, NOSQL.取得指定値_根据入力字典and出力字典and入力数据
	public static final String KEY_家族 = "家族";// NOSQL.取得指定値_根据入力字典and出力字典and入力情報Map, NOSQL.取得指定値_根据入力字典and出力字典and入力数据
	public static final String KEY_非直接 = "非直接"; // NOSQL.取得指定値_根据入力字典and出力字典and入力数据
	public static final String KEY_直接 = "直接"; // NOSQL.取得指定値_根据入力字典and出力字典and入力数据
	public static final String KEY_程序開始詞条 = "程序開始詞条";// NOSQL.取得プログラム数据_根据辞書ID
	public static final String KEY_程序終点詞条 = "程序終点詞条";// NOSQL.取得プログラム数据_根据辞書ID
	public static final String KEY_程序 = "SDP";// NOSQL.取得プログラム数据_根据辞書ID

	public static final String KEY_数据路径 = 取得指定信息("数据路径");//文件全路径.取得対象文件全路径_by類型and詞条IDand数据ID ="/Users/ai1/Desktop/things_db";
	public static final String KEY_路径分隔符 ="/";//文件全路径.取得対象文件全路径_by類型and詞条IDand数据ID
	public static final String KEY_采番ID文件 = "id.data";
	public static final String KEY_WAITER = "WAITER";
	public static final String KEY_文件后缀data = ".data";
	public static final String KEY_GUEST = "GUEST";
	public static final String KEY_実体数据文件名 = "Data.data";
	public static final String KEY_実体数据索引文件名 = "Data_index.data";
	public static final String KEY_業者詞条id一覧表路径 = "/WAITER/";
	public static final String KEY_THINGSdb词条ID = "0000000001";

	public static final String outputLog = 取得指定信息("outputLog"); // MyLogger.printCallMessage
}
