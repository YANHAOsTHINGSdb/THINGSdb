package stage3.listening;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import stage3.things.id.ID;
import stage3.things.id.詞条;
import stage3.things.multiConditionCalc.CRUDer;

public class ThinkAndProgrammer {
	String sType = null; // 检索、追加、删除、更新
	String 動詞key = null;
	List<String> URL1_list = new ArrayList();
	List<String> FilePath = new ArrayList();
	
	// 0 处理主处理
	public Object thinking_byJSONMap(Map<String, Object> map){
		//-----------------------------------------------
		//HashMap<
		//		    key1 = 動詞 
		//		    value1 = List<
		//		                HashMap<
		//		                       key2 = 名詞1
		//		                       value2 = object
		//		                >
		//		        >
		//>
		//-----------------------------------------------
		
		//map<
		//  追加 {
		//     案件{  java  :{ 开始值:3       } , 
		//            日本语:{ 开始值:流畅    } ,
		//            技术者:{ java:{ 开始值:3} , 日本语:{ 开始值:流畅} } 
		//     }
		//	}
		//>
		
		//map<
		//  检索 {
		//     技术者{ 对象:[姓名,性别,会社 } ，  
		//     技术者{ java:{ 开始值:3}, 日本语:{ 开始值:流畅} } 
		//  }
		//>
		
		// 【1】哪一个是程序执行器
		String sHasError = getURL1_byWhichIsProgrammer(map);
		
		if(!StringUtils.isEmpty(sHasError)) {
			return sHasError;
		}
		
		// 【2】取得一个有程序的词条
		sHasError = getFilePath2_byAskedInfo(map);
		
		if(!StringUtils.isEmpty(sHasError)) {
			return sHasError;
		}
		
		// 【3】用2来测试是否满足执行的条件
		sHasError = isChkOk_byProgrammer2(map);
		
		if(!StringUtils.isEmpty(sHasError)) {
			return sHasError;
		}
		
		// 【4】用1来满足你的愿望
		return accessForAsk_byURL1(map);

	}
	
	// 【1】 取得执 行器地址
	private String getURL1_byWhichIsProgrammer(Map<String, Object> map){
		
		String sHasError = null;
		int iCount = 0;
		
		//-----------------------------------------------
		//HashMap<
		//		    key1 = 動詞 
		//		    value1 = List<
		//		                HashMap<
		//		                       key2 = 名詞1
		//		                       value2 = object
		//		                >
		//		        >
		//>
		//-----------------------------------------------
		
		for (Map.Entry<String, Object> entry :map.entrySet()) {
			
			動詞key = get本名_by別名((String)entry.getKey());
			
			String sURL = getProgrammerURL_by動詞key(動詞key);
			
			// 1.1 是不是程序执行器
			if(!StringUtils.isEmpty(sURL)) {
				// 1.2 如果是
				iCount ++;
				URL1_list.add(sURL);
			}else {
				// 1.3 如果不是	
				continue;
			}
			
			//取得操作类型(前提：具备程序执行器的属性)
			this.sType = get本名_by別名(動詞key);// 检索、追加、删除、更新
		}
		
		// NG：没有程序执行器
		if(iCount == 0) {
			sHasError = "NG：没有程序执行器";
		}
		// NG：超过一个程序执行器
		if(iCount > 1) {
			sHasError = "NG：超过一个程序执行器";
		}
		
		// OK：程序执行器的Access地址
		return sHasError;
	}
	
	private String get本名_by別名(String 動詞key) {
		//  【程序】
		//		|----------ID
		//		|----------VALUE（URL1，URL2，URL3）
		
		詞条 o詞条 = new 詞条("get本名_by別名()");
		ID id = new ID("");
		String s業者詞条ID = id.検索数据采番ID_by詞条IDand実体数据("0000000001", "別名").get(0);
		String s顧客詞条ID = "0000000001";
		String s顧客数据ID = id.検索数据采番ID_by詞条IDand実体数据("0000000001", 動詞key).get(0);
		List<String>業者数据採番idList = o詞条.検索業者数据採番idList_by本詞条IDand顧客詞条IDand顧客数据ID(
				s業者詞条ID,
				s顧客詞条ID,
				s顧客数据ID
				);
		if(CollectionUtils.isEmpty(業者数据採番idList)){
			return null;
		}
		return o詞条.取得実体数据_by詞条IDand数据採番ID(s業者詞条ID, 業者数据採番idList.get(0));
	}

	private String getProgrammerURL_by動詞key(String 動詞key) {
		
		
		// 在程序执行器中用 词条以GUSET的身份用【動詞key】找到ID，再
		// ---------------------------------------------------------------
		// 【程序执行器】
		//		  |----------ID
		//		  |----------VALUE（URL1，URL2，URL3）
		//        |----------GUEST（ 例，词条 ）
		//                     |
		//                     |-----------ID ........【動詞key】
		//                                  |
		//                                  |--------（【程序执行器】，ID）
		// ---------------------------------------------------------------
		
		// 【1】取得 动词的词条ID
		
		// 【2】取得 【程序执行器】的词条ID
		
		// 【3】取得 【程序执行器】的ID通过 GUEST = 动词（例。追加）的词条ID
		
		// 【4】取得 【程序执行器】的ID的指定值
		
		詞条 o詞条 = new 詞条("getProgrammerURL_by動詞key()");
		ID id = new ID("");
		String s本詞条ID = id.検索数据采番ID_by詞条IDand実体数据("0000000001", "程序执行器").get(0);
		String s顧客詞条ID = "0000000001";
		String s顧客数据ID = id.検索数据采番ID_by詞条IDand実体数据("0000000001", 動詞key).get(0);
		List<String>業者数据採番idList = o詞条.検索業者数据採番idList_by本詞条IDand顧客詞条IDand顧客数据ID(
				s本詞条ID,
				s顧客詞条ID,
				s顧客数据ID
				);
		if(CollectionUtils.isEmpty(業者数据採番idList)){
			return null;
		}
		return o詞条.取得実体数据_by詞条IDand数据採番ID(s本詞条ID, 業者数据採番idList.get(0));
	}


	// 【2】 这两个词条间有【程序】吗
	private String getFilePath2_byAskedInfo(Map<String, Object> map){
		
		//-----------------------------------------------
		//HashMap<
		//		    key1 = 動詞 
		//		    value1 = List<
		//		                HashMap<
		//		                       key2 = 名詞1
		//		                       value2 = object
		//		                >
		//		        >
		//>
		//-----------------------------------------------
		
		//map<
		//  追加 {
		//     案件{  java  :{ 开始值:3       } , 
		//            日本语:{ 开始值:流畅    } ,
		//            技术者:{ java:{ 开始值:3} , 日本语:{ 开始值:流畅} } 
		//     }
		//	}
		//>
		
		//map<
		//  检索 {
		//     技术者{ 对象:[姓名,性别,会社 } ，  
		//     技术者{ java:{ 开始值:3}, 日本语:{ 开始值:流畅} } 
		//  }
		//>
		
		String sFilePath = "NG：没有程序";
		
		for (Map.Entry<String, Object> entry :map.entrySet()) {
			
			動詞key = get本名_by別名((String)entry.getKey());
			List<Map> list = new ArrayList((List)entry.getValue());
			List<String> 名詞対象List = get名詞対象List_byList(list);
			switch(this.sType){
				
			case "検索":
				//名詞対象List = get検索対象_byMap((Map)entry.getValue());
				// 检索的时候，不需要程序来CHK入力信息。
				
				break;
			default:
				//名詞対象List = get一般対象_byMap((Map)entry.getValue());
				// 程序文件名
				sFilePath = getFilePath_by動詞And名詞(動詞key, 名詞対象List);
			}
		
		}
		
		// OK：程序信息
		return sFilePath;
	}
	
	
	private List<String> get名詞対象List_byList(List<Map> list) {
		//-----------------------------------------------
		//HashMap<
		//		    key1 = 動詞 
		//		    value1 = List<
		//		                HashMap<
		//		                       key2 = 名詞1
		//		                       value2 = object
		//		                >
		//		        >
		//>
		//-----------------------------------------------
		List<String> 名詞対象List = new ArrayList<String>();
		
		for(Map<String, Object> map:list) {
			for (Map.Entry<String, Object> entry :map.entrySet()) {
				
				名詞対象List.add(entry.getKey());
			}
			
		}
		return 名詞対象List;
	}

	// 取得程序文件路径。通过动词与名词list
	private String getFilePath_by動詞And名詞(String 動詞s, List<String> 名詞対象List) {
		
		// ---------------------------------------------------------------
		//     【程序】
		//		  |----------ID
		//		  |----------VALUE（FilePath1，FilePath2，FilePath3）
		//        |----------GUEST（ 例，追加、删除、更新，检索 ）
		//        |            |
		//        |            |-----------追加 ........ 例，社员的程序、案件的程序
		//        |            |            |
		//        |            |            |---------追加ID
		//        |            |            |           |---------{程序, ID}
		//        |            |            |---------追加ID
		//        |            |                        |---------{程序, ID}
		//        |            |
		//        |            |-----------更新
		//        |            |            |
		//        |            |            |---------更新ID
		//        |            |            |           |---------{程序, ID}
		//        |            |            |---------更新ID
		//        |            |                        |---------{程序, ID}
		//        |            |            
		//        |            |
		//        |            |-----------删除
		//        |            |            |
		//        |            |            |---------删除ID
		//        |            |            |           |---------{程序, ID}
		//        |            |            |---------删除ID
		//        |            |                        |---------{程序, ID}		
		//        |            |
		//        |            |
		//        |            |-----------检索
		//        |            |            |
		//        |            |            |---------检索ID
		//        |            |            |           |---------{程序, ID}
		//        |            |            |---------检索ID
		//        |            |                        |---------{程序, ID}		
		//        |            |
		//        |            |
		//        |            |-----------做成者
		//        |            |
		//        |            |-----------做成时间
		//        |
		//        |
		//        |----------GUEST
		//                     |
		//                     |-----------ID{追加, 做成者，做成时间}
		//                     |-----------ID{更新, 做成者，做成时间}
		//                     |-----------ID{删  除, 做成者，做成时间}
		//                     |-----------ID{检 索, 做成者，做成时间}		
		//
		// ---------------------------------------------------------------
		
		
		
		// 【1】取得 对象= 动词的词条ID
		
		// 【2】取得 GUEST= 词条ID
		
		// 【3】取得 GUEST的ID= 名词（例，技术者）的词条ID
		
		// 【4】取得 对象的ID的指定值
		
		詞条 o詞条 = new 詞条("getFilePath_by動詞And名詞()");
		ID id = new ID("");
		String s業者詞条ID = id.検索数据采番ID_by詞条IDand実体数据("0000000001", "程序").get(0);
		String s顧客詞条ID = id.検索数据采番ID_by詞条IDand実体数据("0000000001", 動詞s).get(0);
		
		//	【追加】
		//     |----------ID
		//     |----------VALUE
		//     |----------GUEST
		//     |            |----------詞条
		//     |                         |----------ID：技术者
		//     |                                     |----------{追加, ID}
		//     |
		//     |
		//     |----------WAITER

		
		List<String> s顧客数据idList = o詞条.検索業者数据採番idList_by本詞条IDand顧客詞条IDand顧客数据ID(
				o詞条.取得詞条ID_by詞条名(動詞s),
				"0000000001",
				id.検索数据采番ID_by詞条IDand実体数据("0000000001", 名詞対象List.get(0)).get(0)
				);
		if(CollectionUtils.isEmpty(s顧客数据idList)){
			return null;
		}
		List<String>業者数据採番idList = o詞条.検索業者数据採番idList_by本詞条IDand顧客詞条IDand顧客数据ID(
				s業者詞条ID,
				s顧客詞条ID,
				s顧客数据idList.get(0)
				);
		if(CollectionUtils.isEmpty(業者数据採番idList)){
			return null;
		}
		return o詞条.取得実体数据_by詞条IDand数据採番ID(s業者詞条ID, 業者数据採番idList.get(0));

	}
	
	
//	// 取得追加、更新、删除处理的对象词条名
//	private List<String> get一般対象_byMap(Map value) {
//		//-----------------------------------------------
//		//HashMap<
//		//		    key1 = 動詞 
//		//		    value1 = List<
//		//		                HashMap<
//		//		                       key2 = 名詞1
//		//		                       value2 = object
//		//		                >
//		//		        >
//		//>
//		//-----------------------------------------------
//		return null;
//	}
//	// 取得検索处理的对象词条名
//	private List<String> get検索対象_byMap(Map value) {
//		//-----------------------------------------------
//		//HashMap<
//		//		    key1 = 動詞 
//		//		    value1 = List<
//		//		                HashMap<
//		//		                       key2 = 名詞1
//		//		                       value2 = object
//		//		                >
//		//		        >
//		//>
//		//-----------------------------------------------
//
//		return null;
//	}

	// 【3】 用2来测试是否满足执行的条件
	private String isChkOk_byProgrammer2(Map<String, Object> map){
		//-----------------------------------------------
		//HashMap<
		//		    key1 = 動詞 
		//		    value1 = List<
		//		                HashMap<
		//		                       key2 = 名詞1
		//		                       value2 = object
		//		                >
		//		        >
		//>
		//-----------------------------------------------
		String sHasError = null;
		
		// NG：没有结果
		sHasError = "NG：没有结果";
		
		// NG：结果NG
		sHasError = "NG：結果NG";
		
		// OK：结果OK
		
		return sHasError;
	}
	
	// 【4】 用1来满足你的愿望
	private List<Object> accessForAsk_byURL1(Map<String, Object> map){
		//-----------------------------------------------
		//HashMap<
		//		    key1 = 動詞 
		//		    value1 = List<
		//		                HashMap<
		//		                       key2 = 名詞1
		//		                       value2 = object
		//		                >
		//		        >
		//>
		//-----------------------------------------------
		String sHasError = null;
		List thinking_Result = new ArrayList();
		CRUDer cRUDer = new CRUDer("accessForAsk_byURL1()");
		
		
		switch(sType) {
		
		case "追加":
			return cRUDer.add(map);

		case "更新":
			
			return cRUDer.add(map);
		case "削除":
			
			return cRUDer.add(map);
			
		case "検索":
			
			return cRUDer.search(map);
			
		default :
		
		}
		// NG：没有结果
		sHasError = "NG：没有结果";
		
		thinking_Result.add(sHasError);
		
		// OK：结果OK 
		
		return thinking_Result;
	}
	
}
