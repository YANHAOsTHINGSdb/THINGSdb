package stage3.engine.calc.implement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stage3.engine.calc.計算器;
import stage3.engine.parse.計算式;



public class 交集判断計算器 extends 計算器基本 implements 計算器 {

	/**
	 *
	 * A=<a1,a2,a3......a10>
	 * B=<b1,b2,b3......a10>
	 *
	 * 計算結果：<a1,a2,a3......a10......b3,b2,b1>
	 */

	//原来以为。需要計算一下。可是。已放在一起了。
	//只是还没有找出来。
	// b1	List{<k1= 基站>，<k1= 基站>，<k1= 基站>，<k1= 基站>}
	// b2	List{<k2= 基站>，<k2= 基站>，<k2= 基站>}

	// b1b2 List{<k1= 基站1>，<k2= 基站3> }
	//			{<k1= 基站1>，<k2= 基站4> }
	//			{<k1= 基站2>，<k2= 基站3> }
	//			{<k1= 基站2>，<k2= 基站2> } ←出力

	@Override
	public String 個別計算_根据計算項目List(List<String> paramList) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public String 個別計算_根据計算項目List(Map map) {
		//統計其中有相同value的key的个数
		//如果两个以上	"TRUE"
		//否則			"FALSE"
		Map<String,String> 個別計算map =new HashMap(map);

		Map<String,Integer> res=new HashMap<>();
		List<List> 個別計算List = new ArrayList<List>();

        for (Map.Entry<String,String> entry:個別計算map.entrySet()){

            if (res.containsKey(entry.getValue())){

                res.put(entry.getValue(),res.get(entry.getValue())+1);
                Integer iCount = res.get(entry.getValue());
                if(iCount > 1){
                	return "TRUE";
                }
            }else{
                res.put(entry.getValue(),1);
            }

        	// "SSS,SSS,SSS,ssss,sss,sss,"の形式では
            // それぞれ保持する
        	String[] 個別values = entry.getValue().split(",");
        	if( 個別values.length > 1 ){
        		for(int i= 0; i<個別values.length;i++){
        			if (res.containsKey(個別values[i])){
        				res.put(個別values[i],res.get(個別values[i])+1);
                        Integer iCount = res.get(個別values[i]);
                        if(iCount > 1){
                        	return "TRUE";
                        }
        			}else{
        				res.put(個別values[i],1);
        			}
        		}
        	}
        }

		return "FALSE";
	}

	@Override
	public Object 個別計算_根据計算項目List(Map 個別計算Map, Map f臨時計算結果Map, 計算式 o計算式, Map<String, 計算式> 計算式Map) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
