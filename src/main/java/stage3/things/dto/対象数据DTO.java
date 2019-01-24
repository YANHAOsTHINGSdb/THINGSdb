package stage3.things.dto;

import lombok.Data;

@Data
public class 対象数据DTO {
	public 対象数据DTO(String s詞条param, String sValue) {
		this.s詞条 = s詞条param;
		this.s実体数据 = sValue;
	}
	String s詞条;
	String s実体数据;
}
