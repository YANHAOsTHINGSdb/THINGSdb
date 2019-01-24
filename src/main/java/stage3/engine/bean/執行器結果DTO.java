package stage3.engine.bean;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class 執行器結果DTO {

	List<Map> 計算結果 = null;
	String s出力結果Key = null;

	public 執行器結果DTO(List<Map> 計算結果, String s出力結果Key) {

		this.計算結果 = 計算結果;
		this.s出力結果Key = s出力結果Key;

	}
}
