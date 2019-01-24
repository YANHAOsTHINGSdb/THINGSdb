package stage3.things.dto;

import java.util.List;

import lombok.Data;

@Data
public class 追加処理対象DTO {

	String s詞条;

	String sValue;

	List<追加処理対象DTO> 追加処理対象DTOList;
}
