package stage3.things.json;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Map2Json {

	public void test() {
		// JSONに変換するオブジェクトを作成
		Map<String, Object> o = createObject();

		// オブジェクトをJSON文字列に変換
		String json = toJSON(o);
		System.out.println("---- Create JSON Result ----");
		System.out.println(json);

		// JSON文字列をオブジェクトに戻す
		InputStream is = new ByteArrayInputStream(json.getBytes());
		Map<String, Object> _o = null;
		try {
			_o = parseJSON(is);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 戻したオブジェクトを確認する
		System.out.println("---- Parse JSON Result ----");
		System.out.println("Title:"+_o.get("Title"));
		System.out.println("Note:"+_o.get("Note"));
		List<Person> persons = (List<Person>)_o.get("Persons");
		for(Person person : persons) {
			System.out.println("name:"+person.name);
			System.out.println("age:"+person.age);
			if(person.phone != null) {
				for(String phone : person.phone) {
					System.out.println("phone:"+phone);
				}
			}
		}
	}

	/**
	 * サンプルオブジェクトを生成する
	 * @return
	 */
	private static Map<String, Object> createObject() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Title", "PersonList");
		map.put("Note", "For JSON TEST");

		List<Person> persons = new ArrayList<Person>();
		Person person1 = new Person();
		person1.name = "Abe Taro";
		person1.age = 3;
		List<String> phone1 = new ArrayList<String>();
		phone1.add("XXX-XXXX");
		phone1.add("YYY-YYYY");
		person1.phone = phone1;
		persons.add(person1);

		Person person2 = new Person();
		person2.name = "Okawa Hiroyuki";
		person2.age = 41;
		List<String> phone2 = new ArrayList<String>();
		phone2.add("ZZZ-ZZZZ");
		phone2.add("AAA-AAAA");
		person2.phone = phone2;
		persons.add(person2);

		map.put("Persons", persons);

		return map;
	}

	/**
	 * オブジェクトからJSON文字列に変換する
	 * @param o
	 * @return
	 */
	private static String toJSON(Map<String, Object> o) {

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = null;

		try {
			jsonString = objectMapper.writeValueAsString(o);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonString;
	}

	/**
	 * JSON文字列からオブジェクトに変換する
	 * @param is
	 * @return
	 * @throws JsonParseException
	 * @throws IOException
	 */
	private static Map<String, Object> parseJSON(InputStream is) throws JsonParseException, IOException {
		Map<String,Object> o = new HashMap<String,Object>();
		List<Person> persons = null;
		JsonFactory factory = new JsonFactory();
		JsonParser jp = factory.createJsonParser(is);
		if(jp.nextToken() == JsonToken.START_OBJECT) {
			while (jp.nextToken() != JsonToken.END_OBJECT) {
				String _name = jp.getCurrentName();
				jp.nextToken();
				if(_name.equals("Title")) {
					o.put("Title", jp.getText());
				} else if(_name.equals("Note")) {
					o.put("Note", jp.getText());
				} else if(_name.equals("Persons")) {
					persons = parsePersons(jp);
					o.put("Persons", persons);
				} else {
					jp.skipChildren();
				}
			}
		} else {
			jp.skipChildren();
		}
		return o;
	}

	private static List<Person> parsePersons(JsonParser jp) throws JsonParseException, NumberFormatException, IOException {
		List<Person> persons = new ArrayList<Person>();
		Person person = null;
		while (jp.nextToken() != JsonToken.END_ARRAY) {
			String _name = jp.getCurrentName();
			if(_name != null) {
				jp.nextToken();
				if(_name.equals("name")) {
					person = new Person();
					persons.add(person);
					person.name = jp.getText();
				} else if(_name.equals("age")) {
					person.age = new Integer(jp.getText());
				} else if(_name.equals("phone")) {
					person.phone = parsePhones(jp);
				} else {
					jp.skipChildren();
				}
			}
		}
		return persons;
	}

	private static List<String> parsePhones(JsonParser jp) throws JsonParseException, IOException {
		List<String> phones = new ArrayList<String>();
		while (jp.nextToken() != JsonToken.END_ARRAY) {
			phones.add(jp.getText());
		}
		return phones;
	}

	private static class Person {
		public String name;
		public Integer age;
		public List<String> phone;
	}

	public static void main(String args[]){
		Map2Json s = new Map2Json();
		s.test();
	}

}
