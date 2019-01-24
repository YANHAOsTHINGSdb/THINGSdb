package stage3.things.multiConditionCalc;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.json.JSONObject;

import lombok.Data;
/**
 * TODO
 * 第一恨，就是DTO，理由是它把程序规定的死死的
 * 就是说，如果你不知道DTO的定义，
 * 你就无法使用这段程序，
 * 这严重损害了程序的通用性
 *
 * 所以，接下来的任务就是如何把DTO转成Map！
 * TODO
 *
 * @author haoyan
 *
 */
@Data
public class 条件 {

	//几种特例需要对应
	// 1 区間条件
	// 2 条件関係

	public 条件(String s操作) {
		this.s操作 = s操作;
	}

	String s操作;

	private List<String> 作成_目標List(String s対象名) {

		Class stuClass = null;
		try {
			stuClass = Class.forName(s対象名);
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		Field[] fieldArray = stuClass.getDeclaredFields();
		//JSONArray sub目標Array = new JSONArray();
		List<String> 目標List = new ArrayList();
		for (Field f : fieldArray) {
			JSONObject obj目標 = new JSONObject();
			目標List.add(f.getName());
		}

		return 目標List;
	}

	/**
	 *
	 * @param s条件数据
	 * @return
	 */
	private String get条件(String s条件数据) {
		if (StringUtils.isEmpty(s条件数据)) {
			return "";
		}
		return "," + s条件数据;
	}

	private Map<String, Object> 作成_目標Object(String s対象名) {
		Map map = new HashMap<String, Object>();
		map.put(this.做成目標数据_根据対象名(s対象名), 作成_目標List(s対象名));

		return map;
	}



	/**
	 *
	 * @param s対象名
	 * @return
	 */
	private String 做成目標数据_根据対象名(String s対象名) {
		// "目標":社員
		try {
			Class stuClass3 = Class.forName(s対象名);// 注意此字符串必须是真实路径，就是带包名的类路径，包名.类名
			String sClassFullName = stuClass3.getName();
			String sClassFullNames[] = sClassFullName.split("\\.");

			return sClassFullNames[sClassFullNames.length - 1];

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String 做成登録数据_根据対象名(String s対象名, Object object) {

		JSONObject obj = new JSONObject();
		JSONObject subObj = new JSONObject();

		//1.获取Class对象
		Class stuClass = null;
		try {
			//Class<?> obj = Class.forName("com.withiter.test.Person");
			stuClass = Class.forName(s対象名);
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		System.out.println("************获取所有的字段(包括私有、受保护、默认的)********************");
		Field[] fieldArray = stuClass.getDeclaredFields();
		for (Field f : fieldArray) {

			try {

				Method testParamMethod = object.getClass().getDeclaredMethod("get" + WordUtils.capitalize(f.getName()));
				String str = (String) testParamMethod.invoke(object);
				subObj.put(f.getName(), str);

			} catch (IllegalArgumentException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			System.out.println(f);

		}

		// 例，s目標 =社員Bean
		String s目標 = this.做成目標数据_根据対象名(s対象名);

		obj.put(s目標, subObj);

		return obj.toString();
	}

}

