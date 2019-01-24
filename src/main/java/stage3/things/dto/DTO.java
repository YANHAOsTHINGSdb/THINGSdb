package stage3.things.dto;

import stage3.things.file.文件記録;

public class DTO {

	public 数据DTO to数据DTO(String s詞条ID_数据采番id) {

		文件記録 o文件記録 = new 文件記録("s詞条ID_数据采番id");
		int i単位記録固定長度 = o文件記録.取得単位記録固定長度_by類型("采番ID文件");

		//数据DTO 数据dto = new 数据DTO(s顧客詞条ID数据ID.substring(0, 10), s顧客詞条ID数据ID.substring(10, 単位記録固定長度1));

		数据DTO 数据dto = new 数据DTO(
				s詞条ID_数据采番id.substring(0, i単位記録固定長度),
				s詞条ID_数据采番id.substring(i単位記録固定長度, s詞条ID_数据采番id.length())
				);
		return 数据dto;
	}
}
