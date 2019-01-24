package stage3.engine.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import stage3.engine.bean.辞書BeanDTO;
import stage3.log.MyLogger;

@Data
public class プログラム {

	Map<String, 計算式> 計算式Map = new HashMap<String, 計算式>();
	List<計算式> 計算式List = new ArrayList<計算式>();
	List<辞書BeanDTO> プログラムデータList;

	String sCallPath = null;
	MyLogger myLogger = new MyLogger();

	public プログラム(String sCallPath) {
		this.sCallPath = sCallPath;
	}
	
	/**
	 * 作成_計算式List_根据程序数据
	 * @param i現在行No
	 */
	public void 作成_計算式List_根据程序数据(int i現在行No){
		myLogger.printCallMessage(sCallPath,"プログラム.作成_計算式List_根据程序数据(i現在行No)");

		/**
			作成計算式List_根据グログラム数据(現在行番号)
			   1 計算式 = new 計算式(現在行番号);
			   2 ■計算式.作成_計算式_根据程序数据();
			   3 計算式List.add( 計算式 );
			   4 取得現在行番号();
			   5 作成計算式List_根据グログラム数据(現在行番号)
		 */
		計算式 o計算式 = new 計算式(sCallPath+"作成_計算式List_根据程序数据");
		
		o計算式.setプログラムデータList(プログラムデータList);
		o計算式.set現在の行No(i現在行No);
		o計算式.作成_計算式_根据程序数据();
System.out.println(o計算式.getSName()+":"+o計算式.getSKey()+"   ,   "+o計算式.getS运算符()+"   ,   "+o計算式.getTYPE()+"   ,   "+o計算式.get子計算式名List().toString());
		this.計算式Map.put(o計算式.getSName(), o計算式 );
		計算式List.add(o計算式);

		int ii現在行No = o計算式.get現在の行No();
		if(ii現在行No>=プログラムデータList.size()){
			return;
		}
		作成_計算式List_根据程序数据(ii現在行No);
	}

//	public プログラム(List<辞書BeanDTO> プログラム数据) {
//		this.プログラムデータList = プログラム数据;
//	}

}
