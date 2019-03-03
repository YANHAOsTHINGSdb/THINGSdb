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

	/**
	 * 根据CLASS名来判断到底该用什么【词条名】
	 * 因为你无法一个一个跟进去，所以也不见可能一个指定该是什么【词条名】
	 * 所以只能在外部映射好。
	 * @param object
	 * @return
	 */
	public static String get对象名ByClassName(Object object ){

		switch(getClassNameByClassObject(object)){
			case "stage3.NLP.NLPreader.NLPwhat":
				return "什么";

			case "stage3.NLP.NLPreader.NLPverb":
				return "怎么样";

			default :
				return null;
		}
	}
}
