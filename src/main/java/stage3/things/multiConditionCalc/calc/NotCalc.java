package stage3.things.multiConditionCalc.calc;

import java.util.ArrayList;
import java.util.List;

import stage3.things.multiConditionCalc.Calc;

public class NotCalc implements Calc{

	/**
	 * 返回两个List的差集
	 */
	@Override
	public List 論理計算(List list1, List list2) {

		List local_List1 = new ArrayList(list1);
		List local_List2 = new ArrayList(list2);

		local_List1.removeAll(local_List2);

		return local_List1;
	}

}
