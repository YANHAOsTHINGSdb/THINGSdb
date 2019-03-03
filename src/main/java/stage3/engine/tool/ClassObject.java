package stage3.engine.tool;

public class ClassObject {

	/**
	 * 取得指定对象的Class名
	 * @param object
	 * @return
	 */
	public static String getClassNameByClassObject(Object object){

		Class<?> enclosingClass = object.getClass().getEnclosingClass();

		if (enclosingClass != null) {

		  System.out.println(enclosingClass.getName());
		  return enclosingClass.getName();

		} else {

		  System.out.println(object.getClass().getName());
		  return object.getClass().getName();

		}

	}
}
