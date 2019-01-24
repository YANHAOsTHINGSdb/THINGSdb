package stage3.engine.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections4.CollectionUtils;

import lombok.Data;
import stage3.engine.bean.NOSQL入力字典DTO;
import stage3.engine.bean.辞書BeanDTO;
import stage3.log.MyLogger;
/*
 * 计算式
 *
 * 对于List<辞書BeanDTO>
 * 递归调取自身
 * （进入 为某一结点读取信息的状态）
 *
 * 当遇到以下关键字时，会调取对应的信息
 *
 * 每次都会CHK是否满足结点的条件，
 * 一旦，条件满足就会退出，
 * 为下一节点服务，
 *
 * 否则就会持续的读下去。
 *
 *
 */

/**
 * 例，乘车路线计算SDP
 *
 * 1， C1， 出力词条， 基站    //计算式之后两种：1，字典计算
 * 2， C1， 入口，    入力1   //每个SDP必须有最少一个入口
 *
 * 3， C2， 出力词条， 基站
 * 4， C2， 入口，    入力1   // 字典计算式，可以是  出力词条+入口
 *                          //           也可以是 出力词条+入力词条
 * 5， D1， 出力词条， 相邻基站
 * 6， D1， 入力词条， C1     // 入力对象的字典，让他们去子计算式中去找吧
 *
 * 7， D2， 出力词条， 相邻词条
 * 8， D2， 入力词条， C2
 *
 * 9， D3， 计算，    交集计算器   //计算式之后两种：2，计算器计算
 * 10，D3， 主，      D1
 * 11，D3， 次，      D2
 *
 * 12，D4， 计算，    IF计算器
 * 13，D4.  主，      D31
 * 14，D4， 次，      D43
 *
 * 15，D5， 计算，     List相加计算
 * 16，D5， 主，      C1
 * 17，D5， 次，      D6
 *
 * 18，D21，计算，     IF计算器
 * 19，D21， 主，      D211
 * 20，D21， 次，      D22
 * 21，D21， 次，      Return //到此返回
 *
 * 22，D22，计算，     IF计算器
 * 23，D22， 主，      D221
 * 24，D22， 次，      D4
 * 25，D22， 次，      Return //到此返回
 *
 * 26, D211, 计算，   非空计算器
 * 27，D211， 主，    D1
 *
 * 26, D221, 计算，   非空计算器
 * 27，D221， 主，    D2
 *
 * 28，D43，计算，     FOR计算器    //处理完退回到原点，才叫做FOR
 * 29，D43， 主，     D432
 * 30，D43， 次，     D5
 *
 * 31，D432，计算，   FOR项目计算器
 * 32，D432， 主，    D3
 *
 * 33，D31， 计算，   非空计算器
 * 34，D31， 主，     D3
 *
 * 35，D6， 计算，    List相加计算器
 * 36，D6， 主，      C2
 * 37，D6， 次，      D432
 * 38，D7， 出口，    D5          // 每个SDP必须有最少一个出口
 *
 *
———————计算式的结构如下——————
TYPE		   // 到底是【計算計算式-計算器】还是【計算計算式-字典】
子计算式名List   // C1，C2之类
s运算符         // 字典计算时为【辞书】其，他为计算器的名字
sKey           // 自动乱数，用于区别计算式的
nosql入力字典   // 需要NOSQL时的入力信息
———————————————————————————

[
["a1","詞条","時間"],
["a1","入口","2018-12-13" ],
["a2","詞条","股票代码"],
["a2","入口","SH600734"],
["a3","詞条","Every"],
["a3","入口","成交价"],
["a4","詞条","Sum"],
["a4","入口","成交量"],
["b1","計算","sumEvery計算器"],
["b1","主","a1"],
["b1","次","a2"],
["b2","出口","b1"]
]
 */
@Data
public class 計算式 extends 計算庫 {

	//	private 計算式識別器 o計算式識別器 = new 計算式識別器();
	String sCallPath = null;
	MyLogger myLogger = new MyLogger();

	public 計算式(String sCallPath) {
		this.sCallPath = sCallPath;
	}
	private String sName = null;
	/*
	 * 儲値計算式 計算計算式_字典 計算計算式_計算器
	 */
	private String TYPE = null;

	/*
	 * 父計算式。為其父之KEY
	 */
	//	private 計算式 父計算式 = null;
	/*
	 *
	 */
	private List<String> 子計算式名List = new ArrayList<String>();
	/*
	 *
	 */
	private String s运算符 = null;
	/*
	 *
	 */
	private String sKey = null;
	/**
	 *
	 */
	// private Map<String, String> NOSQL入力字典MAP=new HashMap<String, String>();
	private NOSQL入力字典DTO nosql入力字典 = new NOSQL入力字典DTO();

	public 計算式() {

	}

	public 計算式(List<辞書BeanDTO> プログラムデータList) {
		this.setプログラムデータList(プログラムデータList);
		this.set現在の行No(0);// 初期化
	}

	public 計算式(List<辞書BeanDTO> プログラムデータList, int i現在の行No) {
		// 3.1 ■計算項目 .作成計算項目_根据プログラム数据
		// 計算式 o計算式 = new 計算式(プログラムデータList, 現在の行No-1);
		// 作成_計算式_根据程序数据();
		// List<計算式> 計算式List = new ArrayList<計算式>();
		// 計算式List.add(o計算式);
		// 3.2 ■計算式識別器.set次辞書項目()
		// o計算式識別器.get子計算式List().addAll(計算式List);

		this.プログラムデータList = プログラムデータList;
		this.現在の行No = i現在の行No;
	}

	public void 作成_計算式_根据程序数据() {
		myLogger.printCallMessage(sCallPath,"計算式.作成_計算式_根据程序数据()");
		/**
		 *
		 * 1 判断本次計算式的读取是否完成
		 *     1.1 ■計算式识别器.is計算式做成理論完成
		 *     1.2 如果下一行又是一个新【計算式？】
		 *         1.2.1 收官处理
		 *               (1) this.子計算式List = 計算式识别器.get子計算式List()
		 *               (2) this.运算符 = 計算式识别器.运算符
		 *               (3) this.key = 時間采番结果
		 *               (4) this.父計算式 = 作為引数传入的計算式
		 *         1.2.2 回跳
		 *
		 * 2 新行读入
		 *     2.1 ■計算庫.一行読取しカーソル移動
		 *
		 * 3 新行的CD。如果為【計算】 //儲値計算器
		 *     3.1 this.type = 儲値計算式
		 *     3.2 this.运算符 = 等于
		 *     3.3 this.子計算式List.add( 【次項】的実際值 ) //再帰調用
		 *
		 * 4 新行的CD。如果為【計算器】
		 *     4.1 this.type = 計算計算式-計算器
		 *     4.2 this.运算符 = 【次項】的実際值
		 *
		 * 5 新行的CD。如果為【主】
		 *     5.1 this.子計算式List.add( 【次項】的実際值 ) //再帰調用
		 *
		 * 6 新行的CD。如果為【次】
		 *     6.1 this.子計算式List.add( 【次項】的実際值 )
		 *
		 * 7 新行的CD。如果為【辞書】
		 *     7.1 this.type = 計算計算式-辞書
		 *     7.2 this.运算符 = 【次項】的実際值
		 *
		 * 8 新行的CD。如果為【入力】
		 *     8.1 this.子計算式List.add( 【次項】的実際值 ) //再帰調用
		 *
		 * 9 新行的CD。如果為【入力字典】
		 *     9.1 如果this.type = 計算計算式-辞書
		 *     9.2 this.NOSQL入力字典MAP.put( 【入力字典】,【次項】的実際值 )
		 *
		 * 10 新行的CD。如果為【入力項目】
		 *     10.1 如果this.type = 計算計算式-辞書
		 *     10.2 this.NOSQL入力字典MAP.put( 【入力項目】,【次項】的実際值 )
		 *
		 * 11 新行的CD。如果為【外部入力】
		 *     11.1 如果this.type = 計算計算式-辞書
		 *     11.2 this.NOSQL入力字典MAP.put( 【外部入力】,【次項】的実際值 )
		 *
		 * 12 再帰調用（直到計算式完整為止）//再帰調用
		 *     12.1 ■作成_計算式_根据程序数据
		 *
		 */

		// 1 如果算式已经完整。退出
		if (this.is確実完成CHK()) {

			// 生成唯一标识
			this.sKey = 作成_Key();
			// 收尾処理();
			return;

		}

		// 2
		辞書BeanDTO o辞書Bean = this.一行読取しカーソル移動();

		if (o辞書Bean.getObject主().equals("b5")) {

			this.sName = this.sName;
		}
		// 計算式の名前を計算する。
		if (this.sName == null)
			this.sName = o辞書Bean.getObject主();

		switch (o辞書Bean.getCD()) {
		case "出口":
			this.TYPE = "計算計算式_計算器";
			this.s运算符 = "出力";
			this.子計算式名List.add(o辞書Bean.getObject次());
			break;

		case "計算":
		case "計算器":
			this.TYPE = "計算計算式_計算器";
			this.s运算符 = o辞書Bean.getObject次();
			break;
		case "主":

			this.子計算式名List.add(o辞書Bean.getObject次());
			break;
		case "次":

			this.子計算式名List.add(o辞書Bean.getObject次());
			break;

		case "入力字典":
		case "入力辞書":
		case "入力词条":
		case "入力詞条":

			this.子計算式名List.add(o辞書Bean.getObject次());
			break;
		case "入口":
			this.TYPE = "計算計算式_字典";
			this.s运算符 = "辞書";
			if (this.TYPE.equals("計算計算式_字典")) {
				this.nosql入力字典.setS外部入力(o辞書Bean.getObject次());
			}
			break;

		case "出力字典":
		case "出力辞書":
		case "出力词条":
		case "出力詞条":
		case "字典":
		case "辞書":
		case "詞条":
			this.TYPE = "計算計算式_字典";
			this.s运算符 = "辞書";
			this.nosql入力字典.setS出力字典(o辞書Bean.getObject次());
		}

		// 12 再帰調用（直到計算式完整為止）//再帰調用
		作成_計算式_根据程序数据();         // ->退出//再帰で

	}

	public String 作成_Key() {
		// long seed = System.currentTimeMillis();
		// System.out.println("System.currentTimeMillis()"+":"+seed);
		Random r = new Random();
		return "" + r.nextDouble();
	}

	private boolean is確実完成CHK() {
		myLogger.printCallMessage(sCallPath,"計算式.is確実完成CHK()");
		if (is計算式做成理論完成()) {

			/**
			 *
			 * 多条子入力的对应： 如果下一行是新行，就完了吧！
			 */
			Integer i現在の行No = this.get現在の行No();

			if (i現在の行No >= プログラムデータList.size()) {
				return true;
			}

			辞書BeanDTO o辞書Bean = this.指定行読取(i現在の行No);
			if (o辞書Bean.getCD().equals("計算")
					|| o辞書Bean.getCD().equals("辞書")
					|| o辞書Bean.getCD().equals("字典")
					|| o辞書Bean.getCD().equals("詞条")
					|| o辞書Bean.getCD().equals("出口")
					) {
				return true;
			}

			if (o辞書Bean.getCD().equals("出力")) {
				return true;
			}
		}

		return false;

	}

	/**
	 *
	 * @return
	 */
	public boolean is計算式做成理論完成() {
		myLogger.printCallMessage(sCallPath,"計算式.is計算式做成理論完成()");
		if (this.TYPE == null) {
			return false;
		}
		if (this.TYPE.equals("儲値計算式")) {

			return is計算式做成理論完成_儲値計算式();

		} else {

			return is計算式做成理論完成_計算計算式();
		}
	}

	/**
	 *
	 * @return
	 */
	private boolean is計算式做成理論完成_儲値計算式() {

		return true;
	}

	/**
	 *
	 * @return
	 */
	private boolean is計算式做成理論完成_計算計算式() {
		myLogger.printCallMessage(sCallPath,"計算式.is計算式做成理論完成_計算計算式()");
		if (this.TYPE.equals("計算計算式_字典")) {

			return is計算式做成理論完成_計算計算式_字典();

		} else {

			return is計算式做成理論完成_計算計算式_計算器();
		}

	}

	/**
	 *
	 * @return
	 */
	private boolean is計算式做成理論完成_計算計算式_計算器() {
		myLogger.printCallMessage(sCallPath,"計算式.is計算式做成理論完成_計算計算式_計算器()");

		// 1 本計算式已经有【运算符】了
		if (this.s运算符 == null) {
			return false;

		}
		// 2 本計算式已经有【子】了
		if (this.子計算式名List == null) {
			return false;

		}
		// 1 本計算式已经有【运算符】了
		if (s运算符.isEmpty()) {
			return false;

		}
		// 2 本計算式已经有【子】了
		if (子計算式名List.isEmpty()) {
			return false;

		}
		return true;
	}

	/**
	 *
	 * @return
	 */
	private boolean is計算式做成理論完成_計算計算式_字典() {
		myLogger.printCallMessage(sCallPath,"計算式.is計算式做成理論完成_計算計算式_字典()");

		// 1 本計算式已经有【运算符】了
		if (this.s运算符 == null) {
			return false;

		}

		if (this.nosql入力字典.getS出力字典() == null) {
			return false;
		}

		if (this.nosql入力字典.getS出力字典() != null) {

			if (this.nosql入力字典.getS外部入力() == null
					&& this.nosql入力字典.getS入力字典() == null) {
				return false;
			}
		}

		if (this.nosql入力字典.getS入力字典() != null) {

			if (CollectionUtils.isEmpty(子計算式名List)) {
				return false;
			}
		}

		return true;
	}
}
