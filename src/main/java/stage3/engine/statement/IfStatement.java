package stage3.engine.statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import stage3.engine.bean.計算中間結果DTO;
import stage3.engine.parse.計算式;

public class IfStatement extends Statement {

	public IfStatement(計算中間結果DTO o計算中間結果) {
		super.o計算中間結果 = o計算中間結果;
	}

	public IfStatement(List<Map> 計算結果List) {
		this.計算結果List = 計算結果List;
	}

	public IfStatement(List<Map> 計算結果List, Map<String, 計算式> 計算式Map, List<String> sKeyList) {
		this.計算結果List = 計算結果List;
		this.計算式Map = 計算式Map;
		this.sKeyList = sKeyList;
	}

	/**
	 *
	 * @param o計算式2
	 * @param 入力情報List<>
	 * @return
	 */

	public List<Map> 計算式_計算(計算式 o計算式, Map<String, 計算式> 計算式Map, Map 入力情報Map) {
        final Logger logger = Logger.getLogger("SampleLogging");
//		logger.info(o計算式.getSName() + " IFStatement_計算式_計算 ");
//        logger.info("运算符 = "+o計算式.getS运算符());
//        logger.info("子計算 = "+o計算式.get子計算式List());
//        logger.info("\r\n");
//		計算器 o計算器 = this.取得指定计算器_根据操作符号(o計算式.get操作符号());
//
//		//2 ■程序数据執行器 .執行計算項目_根据計算項目List()
//		// 例：入力情報List = {出発駅=小伝馬町, 到着駅=浦安}
//		// Map1<>=小伝馬町 ,,,1,,,浦安
//		// Map2<>=小伝馬町 ,,,2,,,浦安
//		// Map3<>=小伝馬町 ,,,3,,,浦安
//
//		List<Map> paramList = this.執行計算項目_根据計算項目List(o計算式.get計算項目List(), 入力情報List);
//
//		//3 分別計算_根据計算項目List
//		List<Map> s結果List = new ArrayList<Map>();
//
//
//		// Map1<>=小伝馬町 ,,,1,,,浦安
//		// Map2<>=小伝馬町 ,,,2,,,浦安
//		// Map3<>=小伝馬町 ,,,3,,,浦安
//		for(Map map : paramList){
//			//3.1 ■計算器.個別計算_根据計算項目List
//			String sResult = o計算器.個別計算_根据計算項目List(map);
//			//中間結果は<現在の行No, sResult>
//			map.put(o計算式.現在の行No, sResult);
//			s結果List.add(map);
//		}
//
//		return s結果List;
//		return 入力情報List;

		/**
			b4		計算			IF計算器
			b4		主（条件）		b31
			b4		次（TRUE）
		*/
        String s条件計算式= null;
        String sTRUE計算式 = null;
        String sFALSE計算式 = null;
        if(o計算式.get子計算式名List().size()>0){
        	s条件計算式 = o計算式.get子計算式名List().get(0); //主
        }
        if(o計算式.get子計算式名List().size()>1){
        	sTRUE計算式 = o計算式.get子計算式名List().get(1); //次 TRUE
        }
        if(o計算式.get子計算式名List().size()>2){
        	sFALSE計算式 = o計算式.get子計算式名List().get(2); //次 FALSE
        }
		//需要加一个IF計算器结果
		入力情報Map.put(o計算式.getSKey(), o計算式.getS运算符());

		if(this.is条件计算为真(計算式Map.get(s条件計算式), 計算式Map,入力情報Map)){

			if(sTRUE計算式 == null || sTRUE計算式.isEmpty()){
				return null;
			}
			if(sTRUE計算式.equals("EXIT")){
				//中止当前虚拟机的运行，也就是强制性的推出程序。
				System.exit(0);
			}

			return super.計算式_計算(計算式Map.get(sTRUE計算式), 計算式Map, 入力情報Map);

		}else{
			if(sFALSE計算式 == null || sFALSE計算式.isEmpty()){
				return null;
			}
			if(sFALSE計算式.equals("EXIT")){
				//中止当前虚拟机的运行，也就是强制性的退出程序。
				System.exit(0);
			}
			if(sFALSE計算式.equals("DONE")){
				//完成任务，可以回家了。
				入力情報Map = DONE(o計算式,計算式Map,入力情報Map);
			}
			if(sFALSE計算式.equals("RETURN")){
				//放弃本次计算。但還没完。
				入力情報Map = RETURNs(o計算式,計算式Map,入力情報Map);
				List<Map> returnMap = new ArrayList();
				returnMap.add(入力情報Map);
				return returnMap;
			}
			return super.計算式_計算(計算式Map.get(sFALSE計算式), 計算式Map, 入力情報Map);
		}

	}



	/**
	 *
	 * @param o条件計算式
	 * @param 入力情報List
	 * @return
	 */
	private boolean is条件计算为真(計算式 o条件計算式, Map<String, 計算式> 計算式Map, Map 入力情報Map) {

		List<String> 計算項目List = new ArrayList();
		Map 条件計算結果;

		if (入力情報Map.get(o条件計算式.getSKey()) != null) {
			条件計算結果 = 入力情報Map;
		} else {
			List<Map> 条件計算結果List = super.計算式_計算(o条件計算式, 計算式Map, 入力情報Map);
			/**
			 * 这，属于程序的自纠错功能，如果目标计算式没有结果，该怎么办
			 */
			if (条件計算結果List.isEmpty() || 入力情報Map.containsKey("RETURN")) {
				// 条件計算結果List =条件計算結果List;
				// 放弃本次计算。但還没完。
				入力情報Map = RETURNs(o条件計算式, 計算式Map, 入力情報Map);
				return false;
			}
			// 条件计算的结果的特点就是，只有一个结果
			条件計算結果 = 条件計算結果List.get(0);
		}

		if (条件計算結果.get(o条件計算式.getSKey()) == null) {
			return false;

		} else if (条件計算結果.get(o条件計算式.getSKey()).equals("TRUE")) {

			return true;

		} else {

			return false;
		}
	}
}
