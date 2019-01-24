package stage3.engine.bean;
import lombok.Data;


@Data
public class 辞書BeanDTO {

	private String ID;

	private String Object主;

	private String CD;

	private String Object次;


	public 辞書BeanDTO(String string1, String string2, String string3, String string4) {
		this.ID 			= string1;
		this.Object主 		= string2;
		this.CD				= string3;
		this.Object次		= string4;
	}

}
