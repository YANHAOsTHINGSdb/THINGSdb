package stage3.things.multiConditionCalc.bean;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class 計算情報DTO {
	Map 目標;
	String 操作;
	List<条件DTO> 条件;
	String 条件関係;
}
