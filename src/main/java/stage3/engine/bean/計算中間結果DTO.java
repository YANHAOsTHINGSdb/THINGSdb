package stage3.engine.bean;

import java.util.List;
import java.util.Map;

import lombok.Data;
@Data
public class 計算中間結果DTO {
	protected List<Map> 計算結果List = null;

	Map 集合体相加Statement計算結果Map = null;
	Map 集合体相加StatementKeyMap = null;
}
