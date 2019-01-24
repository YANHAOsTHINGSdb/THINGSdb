package stage3.nosql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Yanhao
 *
 */

import lombok.Data;
import stage3.engine.程序数据執行器;
import stage3.engine.bean.執行器結果DTO;
import stage3.engine.bean.辞書BeanDTO;
import stage3.engine.run.implement.計算解析_程序数据執行器;

@Data
public class NOSQL朋友关系 {

	NOSQL朋友 oNOSQL朋友;
	String 入力字典ID;
	String 出力字典ID;
	List<辞書BeanDTO> 朋友記録;

	public NOSQL朋友关系(String 入力字典ID, String 出力字典ID) {
		/**
		---------------------------------
		id， 主項目        ，CD           ，次項目
		1，  乘車路線      ，1 (关系1)    ，駅
		2，  線内路線      ，1 (关系1)    ，駅
		3，  基站          ，1 (关系1)    ，駅
		4，  基站路线      ，1 (关系1)    ，基站
		---------------------------------
		*/
		oNOSQL朋友 = new NOSQL朋友();
		朋友記録 = oNOSQL朋友.取得朋友記録_根据字典ID(入力字典ID);
		this.set入力字典ID(入力字典ID);
		this.set出力字典ID(出力字典ID);
	}

	/**
	 *
	 * @param 入力字典_ID
	 * @param 出力字典_ID
	 * @return
	 */
	private List<辞書BeanDTO> 取得朋友関係記録_根据字典Aand字典B(String 入力字典_ID, String 出力字典_ID){
		/**
		■取得朋友关系记录_根据字典Aand字典B
		1 ■【NOSQL朋友】取得朋友記録_根据字典ID
			---------------------------------
			id， 主项              ，CD              ，次项
			1，  乘车路线      ，1 (关系1)    ，駅
			2，  线内路线      ，1 (关系1)    ，駅
			3，  基站              ，1 (关系1)    ，駅
			4，  基站路线      ，1 (关系1)    ，基站
			---------------------------------

		2 ■取得记述朋友关系文件名_根据字典Aand字典Band朋友记录
		    return "駅_乘車路线_1_dict_relations.dict";

		3 ■取得朋友关系记录_根据关系文件名
		*/

		入力字典_ID = "";
		出力字典_ID = "";

		NOSQL朋友 oNOSQL朋友 = new NOSQL朋友();
		List<辞書BeanDTO> 朋友記録 = oNOSQL朋友.取得朋友記録_根据字典ID(入力字典_ID);

		//s朋友关系文件名 = "駅_乘車路线_1_dict_relations.dict";
		String s朋友关系文件名 = 取得記述朋友関係文件名_根据字典Aand字典Band朋友记录(入力字典_ID, 出力字典_ID, 朋友記録);


		return 取得朋友関係記録_根据文件名(s朋友关系文件名);

	}

	/**
	 *
	 * @param 字典A_ID
	 * @param 字典B_ID
	 * @param 朋友記録
	 * @return
	 */
	private String 取得記述朋友関係文件名_根据字典Aand字典Band朋友记录(String 入力字典_ID, String 出力字典_ID, List<辞書BeanDTO> 朋友記録){

//		■取得记述朋友关系文件名_根据字典Aand字典Band朋友记录
//		//例：入力=駅；乘車路线；
//		      出力=駅_乘車路线_1_dict_relations.dict
//		----------駅_relations_dictList.data-----------------------
//		id， 主項          ，CD           ，次項
//		 1，  乘車路線     ，1 (关系1)    ，駅


		return "駅_乘車路线_1_dict_relations.dict";
	}

	/**
	 *
	 * @param s文件名
	 * @return
	 */
	private List<辞書BeanDTO> 取得朋友関係記録_根据文件名(String s文件名){
		s文件名 = "駅_乘車路线_1_dict_relations.dict";
//		■取得关系記録_根据关系文件名
//		//例：入力=駅_乘車路線_1_dict_relations.dict；
//        出力=
//		 ---------------------------------
//		 id，主項     ，  CD          ，次項
//		 1，S         ， 計算字典     ，乘車路線計算辞書
//		 2，S         ，   入力       ，駅
//		 3，S         ，   入力       ，駅
//		 ---------------------------------
		List<辞書BeanDTO> 朋友関係記録 = new ArrayList();
		朋友関係記録.add(new 辞書BeanDTO("1","S","計算","乘車路線計算辞書"));
		朋友関係記録.add(new 辞書BeanDTO("2","S","主","駅"));
		朋友関係記録.add(new 辞書BeanDTO("3","S","次","駅"));


		return 朋友関係記録;
	}

//	/**
//	 *
//	 * @param s朋友関係記録文件名
//	 * @param 入力数据
//	 * @return
//	 */
//	private List<Map> 取得実体数据_根据計算辞書名and入力数据(String s計算辞書名, List<String>入力数据){
//
//		s計算辞書名="";
//
//
//		//乘車路線計算辞書 を執行器結果DTO執行し、結果を返す。
//		逐個篩選_程序数据執行器 o程序数据執行器 = new 逐個篩選_程序数据執行器();
//
//		//取得全業務数据_根据辞書ID = 技術者辞書
//		//技術者検索方法            = 技術者検索辞書
//
//		return o程序数据執行器.run_逐個篩選_根据業務辞書IDand計算辞書ID("技術者辞書","技術者検索辞書");
//	}


	public 執行器結果DTO 取得実体数据_根据入力数据(Map 入力情報Map) {

		String s計算辞書名=this.取得計算辞書名();

		//乘車路線計算辞書 を執行し、結果を返す。
		程序数据執行器 o程序数据執行器 = new 計算解析_程序数据執行器("取得実体数据_根据入力数据");

		//取得全業務数据_根据辞書ID = 技術者辞書
		//技術者検索方法            = 技術者検索辞書

		return o程序数据執行器.run_根据計算辞書IDand入力情報(s計算辞書名, 入力情報Map);
	}


	private String	取得計算辞書名(){
//		 ---------------------------------
//		 id，主項     ，  CD          ，次項
//		 1，S         ， 計算字典     ，乘車路線計算辞書
//		 2，S         ，   入力       ，駅
//		 3，S         ，   入力       ，駅
//		 ---------------------------------
		List<辞書BeanDTO> 朋友関係記録 = 取得朋友関係記録_根据字典Aand字典B(this.入力字典ID, this.出力字典ID);

		//割愛
		switch (出力字典ID){
		case "基站路線":
			return "基站検索辞書";
		case "路線":
			return "乘車路線計算辞書";
		}

		return "乘車路線計算辞書";

	}


}
