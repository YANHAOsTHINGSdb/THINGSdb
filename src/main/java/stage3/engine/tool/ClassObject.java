package stage3.engine.tool;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

public class ClassObject {

	/**
	 * 取得指定对象的Class名
	 * @param object
	 * @return
	 */
	public static String getClassNameByClassObject(Object object){

		Class<?> enclosingClass = object.getClass().getEnclosingClass();

		if (enclosingClass != null) {

		  //System.out.println(enclosingClass.getName());
		  return enclosingClass.getName();

		} else {

		  //System.out.println(object.getClass().getName());
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

	/**
	 * 判断一个Bean对象是不是空
	 * @param object
	 * @return
	 */
	public static boolean get对象Object是否为空(Object object ){

		for (Field f : object.getClass().getDeclaredFields()) {
		    f.setAccessible(true);
		    try {
				if (f.get(object) != null) {
					// 判断字段是否为空，并且对象属性中的基本都会转为对象类型来判断
					// 有一个不为空就返回不为空false
				    return false;
				}
			} catch (IllegalArgumentException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		// 全部为空，才返回true
		return true;
	}

	/**
	 * 判断一个Object对象是不是空
	 * @param object
	 * @return
	 */
	public static boolean checkObjectIsEmpty(Object object) {

		if(StringUtils.equals(取得对象Object的Type(object), "List")){
			// 1-2  如果Entry的Value是一个List
			// 数据ID = 追加W词条的数据_文件流程(entry.getKey(), entry.getValue(), 詞条信息map);
			return CollectionUtils.isEmpty((List)object);
		}
		else if(StringUtils.equals(取得对象Object的Type(object), "Bean")){
			// 1-4 如果Entry的Value是一个Bean
			// 数据ID = 追加W词条的Bean数据_by构造词条信息Map(entry.getKey(), entry.getValue()).get(数据ID);
			return ClassObject.get对象Object是否为空(object);
		}
		else if(StringUtils.equals(取得对象Object的Type(object), "Map")){
			// 1-3  如果Entry的Value是一个非List
			// 数据ID = 追加W词条的数据_正常流程(entry.getKey(), entry.getValue());
			return CollectionUtils.isEmpty((Map)object);

		}else if(StringUtils.equals(取得对象Object的Type(object), "String")){
			// is a String
			return StringUtils.isEmpty((String)object);
		}

		return object == null;
	}

	/**
	 * 取得一个对象的TYPE
	 * @param valueObject
	 * @return
	 */
	public static String 取得对象Object的Type(Object valueObject) {
		//myLogger.printCallMessage(sCallPath,"詞条CRUD.取得Map_value的Type( )");

		String sType= null;

		if(valueObject instanceof List) {
			sType = "List";
		}
		else if(valueObject instanceof Map) {
			sType = "Map";
		}
		else if(valueObject instanceof String) {
			sType = "String";
		}
		else {
			sType = "Bean";
		}

		return sType;
	}

	/**
	 * 我只把Bean的第一层转成Map
	 * 为什么
	 * 	1 会把所有的子CLASS都转成Map形式
	 *    	//		ObjectMapper m = new ObjectMapper();
			//		Map<String,Object> nlpWhatMap = m.convertValue(nlpWhatList1.get(0), Map.class);
		2
	 * @param value
	 * @return
	 */
	public static Map 把Bean的第一层转成Map(Object object) {
		Map<String, Object> resultMap = new HashMap();

        if (object == null) {
            return null;
        }

        Class<?> cls = object.getClass();
        Method[] methods = cls.getDeclaredMethods();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            try {
                String fieldGetName = parGetName(field.getName());
                if (!checkGetMet(methods, fieldGetName)) {
                    continue;
                }
                Method fieldGetMet = cls.getMethod(fieldGetName, new Class[]{});
                Object fieldVal = fieldGetMet.invoke(object, new Object[]{});
                if (fieldVal != null) {
                    if ("".equals(fieldVal)) {
                       // result = true;
                    } else {
                        // result = false;
                        resultMap.put(get对象名ByFieldName(field.getName()), fieldVal);
                    }
                }
            } catch (Exception e) {
                continue;
            }
        }
		return resultMap;
	}

	/**
	 *
	 * @param name
	 * @return
	 */
    private static String get对象名ByFieldName(String name) {
		switch(name){
		case "listWhat":
			return "什么";

		case "太子":
			return "值";

		case "nlpVerb":
		case "brotherNLPverbList":
			return "怎么样";

		default :
			return null;
		}
	}

	/**
     * 拼接某属性的 get方法
     *
     * @param fieldName
     * @return String
     */
    public static String parGetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        int startIndex = 0;
        if (fieldName.charAt(0) == '_')
            startIndex = 1;
        return "get"
                + fieldName.substring(startIndex, startIndex + 1).toUpperCase()
                + fieldName.substring(startIndex + 1);
    }

    /**
     * 判断是否存在某属性的 get方法
     *
     * @param methods
     * @param fieldGetMet
     * @return boolean
     */
    public static boolean checkGetMet(Method[] methods, String fieldGetMet) {
        for (Method met : methods) {
            if (fieldGetMet.equals(met.getName())) {
                return true;
            }
        }
        return false;
    }
}
