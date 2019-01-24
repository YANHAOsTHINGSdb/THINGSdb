package stage3.engine.bean;

import java.util.List;

import stage3.engine.parse.計算庫;


public class 辞書項目DTO extends 計算庫 {

	private String 辞書ID;

	private String 項目ID;

	private 辞書項目DTO(String s辞書id, String s項目id) {
		this.辞書ID = s辞書id;
		this.項目ID = s項目id;
	}

	private 辞書項目DTO(List<辞書BeanDTO> プログラムデータList) {
		this.プログラムデータList = プログラムデータList;
	}
	/**
	 *
	 */
	private 辞書項目DTO 作成辞書項目_根据プログラム数据(int i現在の行No) {

		/**
		1 ■一行読取しカーソル移動
		2 設置辞書ID
		3 ■一行読取しカーソル移動
		4 設置項目ID
		5 返回辞書項目实体
		*/

		辞書BeanDTO o辞書Bean1 = this.指定行読取(i現在の行No);
		this.辞書ID = o辞書Bean1.getObject次();
		辞書BeanDTO o辞書Bean2 = this.指定行読取(i現在の行No+1);
		this.項目ID = o辞書Bean2.getObject次();

		return new 辞書項目DTO(this.辞書ID,this.項目ID);
	}

	/**
	 *
	 */
	private 辞書項目DTO 作成辞書項目_根据プログラム数据() {

		/**
		1 ■一行読取しカーソル移動
		2 設置辞書ID
		3 ■一行読取しカーソル移動
		4 設置項目ID
		5 返回辞書項目实体
		*/

		辞書BeanDTO o辞書Bean1 = this.一行読取しカーソル移動();
		this.辞書ID = o辞書Bean1.getObject次();
		辞書BeanDTO o辞書Bean2 = this.一行読取しカーソル移動();
		this.項目ID = o辞書Bean2.getObject次();

		return new 辞書項目DTO(this.辞書ID,this.項目ID);
	}

	public String get辞書ID() {
		return 辞書ID;
	}

	public void set辞書ID(String 辞書id) {
		辞書ID = 辞書id;
	}

	public String get項目ID() {
		return 項目ID;
	}

	public void set項目ID(String 項目id) {
		項目ID = 項目id;
	}


}
