package stage3.engine.parse;

import java.util.List;

import lombok.Data;
import stage3.engine.bean.辞書BeanDTO;
@Data
public class 計算庫 {

	protected List<辞書BeanDTO> プログラムデータList;

	protected int 現在の行No;
	/**
	 *
	 */
	public 辞書BeanDTO 一行読取しカーソル移動() {

		辞書BeanDTO s返す値 = null;

		if(プログラムデータList.isEmpty()){
			return null;//ERROR
		}

		if((this.現在の行No + 1) > プログラムデータList.size()){
			//ERROR
		}else{

			s返す値= プログラムデータList.get(this.現在の行No);
		}

		this.現在の行No++;

		return s返す値;

	}
	public 辞書BeanDTO 指定行読取(int 行No) {

		return 行No>=プログラムデータList.size()?null:プログラムデータList.get(行No);

	}
	/**
	 *
	 */
	public 辞書BeanDTO 指定行読取しカーソル移動(int 行No) {

		this.現在の行No = 行No;

		return 一行読取しカーソル移動();

	}

	/**
	 *
	 */
	public void カーソル指定位置移動(int 行No) {

		this.現在の行No = 行No;

	}

	/**
	 *
	 */
	public void カーソル初期化() {

		this.現在の行No = -1;

	}

	public boolean 判断是否为字典项目_根据プログラム数据(int i現在の行No){

		辞書BeanDTO o辞書Bean1 = this.指定行読取(i現在の行No);
		辞書BeanDTO o辞書Bean2 = this.指定行読取(i現在の行No+1);
		if(o辞書Bean1.getCD().equals("辞書") && o辞書Bean2.getCD().equals("項目")){
			return true;
		}
		return false;
	}

	public boolean 判断是否为字典项目_根据プログラム数据(){
		辞書BeanDTO o辞書Bean1 = this.指定行読取(this.現在の行No);
		辞書BeanDTO o辞書Bean2 = this.指定行読取(this.現在の行No+1);
		if(o辞書Bean1.getCD().equals("辞書") && o辞書Bean2.getCD().equals("項目")){
			return true;
		}
		return false;
	}

	public List<辞書BeanDTO> getプログラムデータList() {
		return プログラムデータList;
	}

	public void setプログラムデータList(List<辞書BeanDTO> プログラムデータList) {
		this.プログラムデータList = プログラムデータList;
	}

	/**
	 * 根据主項目,取得現在の行No
	 *
	 * @param 主項目
	 * @return
	 */
	public int 取得現在の行No_根据主項目(String 主項目) {

		int i現在の行No = 0;
		for(辞書BeanDTO o辞書Bean : プログラムデータList){

			if(o辞書Bean.getObject主().equals(主項目)){
				return i現在の行No;
			}
			i現在の行No++;
		}
		return 0;
	}
}
