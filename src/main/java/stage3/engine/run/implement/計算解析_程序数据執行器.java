package stage3.engine.run.implement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import lombok.Data;
import stage3.engine.程序数据執行器;
import stage3.engine.bean.執行器結果DTO;
import stage3.engine.bean.辞書BeanDTO;
import stage3.engine.parse.プログラム;
import stage3.engine.parse.計算式;
import stage3.engine.statement.Statement;
import stage3.log.MyLogger;
import stage3.nosql.NOSQL;


@Data
public class 計算解析_程序数据執行器 extends Statement implements 程序数据執行器 {
	private String s出力結果Key = null;

	String sCallPath = null;
	MyLogger myLogger = new MyLogger();

	public 計算解析_程序数据執行器(String sCallPath) {
		this.sCallPath = sCallPath;
	}

	/**
	 *
	 * @param s計算数据_辞書ID
	 * @param 入力情報List
	 * @return
	 */
	public 執行器結果DTO run_根据計算辞書IDand入力情報(String s計算数据_辞書ID, Map 入力情報Map){
		myLogger.printCallMessage(sCallPath,"計算解析_程序数据執行器.run_根据計算辞書IDand入力情報(s計算数据_辞書ID, 入力情報Map)");

		List<Map> 執行結果 = null;

		//1 取得プログラム数据
		List<辞書BeanDTO> プログラム数据 = new ArrayList<辞書BeanDTO>();

		NOSQL nosql = new NOSQL(sCallPath + "run_根据計算辞書IDand入力情報");
		プログラム数据 = nosql.取得プログラム数据_根据辞書ID(s計算数据_辞書ID);

		/**
		 * 例：
		 *
		 * 入力情報Map{ 駅1, 駅2 }
		 *
		 */
		執行結果 = this.執行程序数据_根据プログラム数据and業務数据(プログラム数据, 入力情報Map);

		return new 執行器結果DTO(執行結果, this.s出力結果Key);
	}


	/**
	 *
	 * @param プログラム数据
	 * @param 入力情報Map{ 駅1, 駅2 }
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> 執行程序数据_根据プログラム数据and業務数据(List<辞書BeanDTO> プログラム数据, Map 入力情報Map) {
		myLogger.printCallMessage(sCallPath,"計算解析_程序数据執行器.執行程序数据_根据プログラム数据and業務数据(プログラム数据, 入力情報Map)");

		//2 o計算式 = ■計算式.作成計算式_根据プログラム数据(プログラム数据)
		プログラム oプログラム = new プログラム(sCallPath+"執行程序数据_根据プログラム数据and業務数据");
		oプログラム.setプログラムデータList(プログラム数据);
		oプログラム.作成_計算式List_根据程序数据(0);

		//3
		List<Map> 結果List = 作成_結果数据_根据業務数据andプログラム計算式(oプログラム, 入力情報Map);

		//6 返回结果List
		return 結果List;
	}


	private List<Map> 作成_結果数据_根据業務数据andプログラム計算式(プログラム oプログラム, Map 入力情報Map) {
		myLogger.printCallMessage(sCallPath,"計算解析_程序数据執行器.作成_結果数据_根据業務数and計算式(プログラム, 入力情報Map)");

		final Logger logger = Logger.getLogger("SampleLogging");
		//1 生一个新List，用来保存每次计算的最新结果
		List<Map> local結果List = new ArrayList();

		//
		this.set計算式Map(oプログラム.get計算式Map());
		this.setSKeyList(this.取得KeyList_From計算式Map(oプログラム.get計算式Map()));

		//2 先将入力情報Map存入到最新结果
		local結果List.add(new HashMap(入力情報Map));

		//3 循環執行每个計算式
		for(計算式 o計算式 : oプログラム.get計算式List()){
//if(o計算式.getSName().equals("b432")){
//	o計算式 = o計算式;
//}
//    将上一次计算的结果，PASS到下一次运算中。
//logger.info("  将上一次计算的结果，PASS到下一次运算中前：super.計算結果List = "+super.計算結果List);
//logger.info("  将上一次计算的结果，PASS到下一次运算中前：local結果List      = "+local結果List.size()+" , "+local結果List);
//logger.info("\r\n");
			super.計算結果List = new ArrayList<Map>(local結果List);

			//3.1 生一个新MAP，用来便于循环
			// 入力信息可以已知的条件，也可以是已知的结果。。。
			// 而结果有可能有号几组，而每组的结构都应该是一样的
			// 在这里要根据每组的情况分别计算
			for(Map f臨時計算結果Map :super.計算結果List){

				//3.2 如果已经计算过了。即在【f臨時計算結果Map】有相应的值
				if(is該当計算式_完成計算(o計算式, f臨時計算結果Map) == true){
					// 即略过。搞下一个计算式
					continue;
				}

				//3.3 如果还没有。作成_結果数据_根据業務数and計算式
				List<Map> 今回結果List = 計算式_計算(o計算式, oプログラム.get計算式Map(), f臨時計算結果Map);
//if(o計算式.getSName().equals("b43")){
//	o計算式 = o計算式;
//}
				// 出力必需品
				s出力結果Key = super.getS出力結果Key();

// 补丁
if( 今回結果List == null || 今回結果List.get(0) == null || 今回結果List.get(0).containsKey("RETURN")){
	return local結果List;
}

				//3.4 将得到的【結果List】放入每个【入力情報Map】中去。

				local結果List = 生成新的local結果List(local結果List, 今回結果List, o計算式);


//				logger.info("    将得到的【結果List】放入每个【入力情報Map】中去後：local結果List = "+local結果List.size()+" , "+local結果List);
//				logger.info("    将得到的【結果List】放入每个【入力情報Map】中去後：super.計算結果List = "+super.計算結果List.size()+" , "+super.計算結果List);
//		        logger.info("\r\n");
			}

		}
		//return super.計算結果List;
		return local結果List;

	}

	private boolean is該当計算式_完成計算(計算式 o計算式, Map f臨時計算結果Map) {
		myLogger.printCallMessage(sCallPath,"計算解析_程序数据執行器.is該当計算式_完成計算(計算式, 入力情報Map)");

		// 1.1 如果Map含有計算式中Key的相应值
		if(f臨時計算結果Map.get(o計算式.getSKey())!=null){
			//1.1.1 返回TRUE
			return true;
		}
		return false;
	}

}
