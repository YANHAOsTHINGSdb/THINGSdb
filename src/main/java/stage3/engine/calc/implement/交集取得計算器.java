package stage3.engine.calc.implement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stage3.engine.calc.計算器;
import stage3.engine.parse.計算式;

public class 交集取得計算器 extends 計算器基本 implements 計算器 {

	@Override
	public Object 個別計算_根据計算項目List(List<String> paramList) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Object 個別計算_根据計算項目List(Map 入力情報Map) {
		//統計其中有相同value的key的个数
		//如果两个以上	"TRUE"
		//否則			"FALSE"
		Map<String,String> 個別計算map =new HashMap<String,String>(入力情報Map);

		Map<String,Integer> res=new HashMap<>();
//		List<List> 個別計算List = new ArrayList<List>();

		String sResult = null;
        for (Map.Entry<String,String> entry:個別計算map.entrySet()){

            if (res.containsKey(entry.getValue())){

                res.put(entry.getValue(),res.get(entry.getValue())+1);
                Integer iCount = res.get(entry.getValue());
                if(iCount > 1){
                	if(sResult == null){
                		sResult = entry.getValue();
                	}else{
                		sResult = sResult +"," + entry.getValue();
                	}
                }
            }else{
                res.put(entry.getValue(),1);
            }
if(entry.getValue() == null){
	entry = entry;
	return sResult;
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
                        	if(sResult == null){
                        		sResult = 個別values[i];
                        	}else{
                        		sResult = sResult +"," + 個別values[i];
                        	}
                        }
        			}else{
        				res.put(個別values[i],1);
        			}
        		}
        	}
        }

		return sResult;
	}

	@Override
	public String 個別計算_根据計算項目List(Map 個別計算Map, Map f臨時計算結果Map, 計算式 o計算式, Map<String, 計算式> 計算式Map) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
