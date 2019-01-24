package stage3.things.multiConditionCalc.calc;

import java.util.ArrayList;
import java.util.List;

import stage3.things.multiConditionCalc.Calc;

public class OrCalc implements Calc{

	/**
	 * 返回两个List的并集
	 */
	@Override
	public List 論理計算(List list1, List list2) {

		List local_List1 = new ArrayList(list1);
		List local_List2 = new ArrayList(list2);

		// 取两个结果集的共同部分
		// 例, A ={1,2,3} B={2,3,4,5}
        //     計算結果 = {1, 2，3, 4，5}
		local_List2.removeAll(local_List1);
		local_List1.addAll(local_List2);

		return local_List1;
	}

}
