package stage3.things.dto;

import lombok.Data;

@Data
public class 数据DTO {

	String 詞条ID;
	String 数据ID;

	public 数据DTO(String s詞条ID, long l数据采番id) {
		this.詞条ID = s詞条ID;
		this.数据ID = l数据采番id+"";
	}
	public 数据DTO(String s対象詞条ID, String s数据采番id) {
		this.詞条ID = s対象詞条ID;
		this.数据ID = s数据采番id;
	}
	public 数据DTO(数据DTO 数据dto) {
		this.詞条ID = 数据dto.get詞条ID();
		this.数据ID = 数据dto.get数据ID();
	}


}
