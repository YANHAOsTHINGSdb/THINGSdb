package stage3.nosql;

import java.util.ArrayList;
import java.util.List;

import stage3.engine.bean.辞書BeanDTO;

public class NOSQL朋友{


	private List<辞書BeanDTO> 取得朋友記録_根据字典Aand字典B(String string, String string2) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	/**
	 *
	 * @param string
	 * @return
	 */
	public List<辞書BeanDTO> 取得朋友記録_根据字典ID(String string) {
		/**
		1 ■【NOSQL朋友】取得朋友記録_根据字典ID
		---------------------------------
		id， 主项              ，CD              ，次项
		1，  乘車路線      ，1 (关系1)    ，駅
		2，  線内路線      ，1 (关系1)    ，駅
		3，  基站          ，1 (关系1)    ，駅
		4，  基站路线      ，1 (关系1)    ，基站
		---------------------------------
		*/

		辞書BeanDTO o辞書Bean1 = new 辞書BeanDTO("1","乘車路線","1","駅");
		辞書BeanDTO o辞書Bean2 = new 辞書BeanDTO("2","線内路線","1","駅");
		辞書BeanDTO o辞書Bean3 = new 辞書BeanDTO("3","基站","1","駅");
		辞書BeanDTO o辞書Bean4 = new 辞書BeanDTO("4","基站路线","1","基站");

		List<辞書BeanDTO> 朋友記録 = new ArrayList();
		朋友記録.add(o辞書Bean1);
		朋友記録.add(o辞書Bean2);
		朋友記録.add(o辞書Bean3);
		朋友記録.add(o辞書Bean4);

		return 朋友記録;
	}
}
