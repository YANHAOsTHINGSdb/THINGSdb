package stage3.things.id;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import lombok.Data;
import net.oschina.j2cache.CacheObject;
import net.oschina.j2cache.NullObject;
import stage3.cache.CacheForThingsDB;
import stage3.log.MyLogger;
import stage3.things.dto.DTO;
import stage3.things.dto.対象数据DTO;
import stage3.things.dto.数据DTO;
import stage3.things.file.文件全路径;
import stage3.things.file.文件記録;
import stage3.things.relations.業者;
import stage3.things.relations.顧客;
import stage3.things.search.区間検索Plan;
import stage3.things.search.模糊検索Plan;
import stage3.things.search.impl.区間検索Plan期間Impl;
import stage3.things.search.impl.模糊検索Plan文字列Impl;

@Data
public class 詞条 extends DTO {
	String sCallPath = null;
	MyLogger myLogger = new MyLogger();

	public 詞条(String sCallPath) {
		this.sCallPath = sCallPath;
	}

	// 为了节省计算速度，将本次已处理的词条记录在Map里，方便以后调用
	static Map<String, String> 詞条Map = new HashMap<String, String>();

	/**
	 *
	 * @param s詞条名
	 * @param string
	 * @return
	 */
	public List<String> 検索顧客詞条数据採番idList_by業者詞条IDand業者実体数据and顧客詞条名(String s業者詞条id,
			String s業者実体数据, String s顧客詞条名) {
		String s函数方法名 = "詞条.検索顧客詞条数据採番idList_by業者詞条IDand業者実体数据and顧客詞条名"; // 用来统一函数名，避免出错
		myLogger.printCallMessage(sCallPath, s函数方法名 + "( 業者詞条id="+ s業者詞条id+", "
				+ "顧客名="+ s顧客詞条名+")");

		// 缓存机制
		try {
			CacheObject o结果 = (CacheObject) CacheForThingsDB.
					取得Cache的Value_by函数名_param(s函数方法名,
					new String[] {s業者詞条id,s業者実体数据,s顧客詞条名});
			if (o结果 == null || o结果.getValue() == null || o结果.getValue() instanceof NullObject) {
			}else {
				return (List<String>) o结果.getValue();
			}
		}catch(Throwable e) {
			System.out.println(e.getMessage());
		}
		/**
			例如，取得年龄词条的保存方式。
			詞条id=0000000001		顧客詞条id
			実体数据=年齢			顧客実体数据		詞条名
			保存方式=保存方式		業者詞条名			業者詞条名
		 */
//		List<String> s結果List = new ArrayList<String>();
		List<String> s数据採番idList = new ArrayList<String>();

		// FORLOG_START
		String org_sCallPath = new String(sCallPath);
		sCallPath += "取得対象詞条的指定情報_by対象詞条名and業者名";

		ID 数据採番id = new ID(sCallPath);
		String s顧客詞条id = this.取得詞条ID_by詞条名(s顧客詞条名);
		List<String> 業者数据IDList = 数据採番id.検索数据采番ID_by詞条IDand実体数据(s業者詞条id, s業者実体数据);
		if(CollectionUtils.isEmpty(業者数据IDList)) {
			return null;
		}
		sCallPath = org_sCallPath;
		// FORLOG_END

		// FORLOG_START
		org_sCallPath = new String(sCallPath);
		sCallPath += "取得対象詞条的指定情報_by対象詞条名and業者名";



		sCallPath = org_sCallPath;
		// FORLOG_END

		// FORLOG_START
		org_sCallPath = new String(sCallPath);
		sCallPath += "取得対象詞条的指定情報_by対象詞条名and業者名";

		for(String s業者数据id : 業者数据IDList) {

			s数据採番idList.addAll(this.検索顧客詞条数据採番idList_by顧客詞条IDand業者詞条IDand業者数据ID(
					s顧客詞条id,
					s業者詞条id,
					s業者数据id));

		}
//		sCallPath = org_sCallPath;
//		// FORLOG_END
//
//		for(String s数据採番id : s数据採番idList){
//
//			// FORLOG_START
//			org_sCallPath = new String(sCallPath);
//			sCallPath += "取得対象詞条的指定情報_by対象詞条名and業者名";
//
//			String s実体数据 = 取得実体数据_by詞条IDand数据採番ID(s業者詞条id, s数据採番id);
//
//			sCallPath = org_sCallPath;
//			// FORLOG_END
//
//			s結果List.add(s実体数据);
//		}
		CacheForThingsDB.设置Cache的Value_by函数名_param(s数据採番idList,
				s函数方法名, s業者詞条id, s業者実体数据, s顧客詞条名);
		return s数据採番idList;
	}

	public List<String> 検索顧客詞条数据採番idList_by顧客詞条IDand業者詞条IDand業者数据ID(String s顧客詞条id, String s業者詞条id,
			String s業者数据id) {
		String s函数方法名 = "詞条.検索顧客詞条数据採番idList_by顧客詞条IDand業者詞条IDand業者数据ID"; // 用来统一函数名，避免出错
		myLogger.printCallMessage(sCallPath, s函数方法名 + "( 顧客詞条ID="+ s顧客詞条id+", 業者詞条ID="+ s業者詞条id+", "
				+ "業者数据ID="+ s業者数据id+")");
		/**
		 * 	一个顾客，来到银行（）
			说，给我取钱。

			先取得顾客id数据文件的全路径
			詞条ID（文件夹）				例、0000000007
				GUEST（文件夹）				例、	｜－－GUEST
					顧客詞条id（文件夹）	例、			｜－－0000000005
						顧客数据id （文件）	例、					｜－－0000000001.data
							该詞条的数据采番id1	例、							｜－－「00000000070000000001」
							该詞条的数据采番id2
							该詞条的数据采番id2
							该詞条的数据采番id2
							该詞条的数据采番id3
			缺一个【顧客数据id】
			取得该文件的全部记录
		 */
		// 缓存机制
		try {
			CacheObject o结果 = (CacheObject) CacheForThingsDB.取得Cache的Value_by函数名_param(
					s函数方法名,
					new String[] {s顧客詞条id,s業者詞条id,s業者数据id});
			if (o结果 == null || o结果.getValue() == null || o结果.getValue() instanceof NullObject) {
			}else {
				return (List<String>) o结果.getValue();
			}
		}catch(Throwable e) {
			System.out.println(e.getMessage());
		}

		// 就是指定文件下的所有文件内容
		List<String> 数据采番idList = new ArrayList<String>();
		// 遍历路径下的所有文件
		文件全路径 o文件全路径 = new 文件全路径();
		文件記録 o文件記録 = new 文件記録(sCallPath+"取得数据採番idList_by詞条IDand顧客詞条IDand顧客数据ID");
		String s類型 = "顧客id数据路径";

		String s顧客id数据文件的全路径 = o文件全路径.取得対象文件全路径_by類型and詞条IDand数据ID(s類型, Arrays.asList(
				s業者詞条id, s顧客詞条id));

		long l開始地址 = 0;
		int i単位記録固定長度 = o文件記録.取得単位記録固定長度_by類型("顧客id数据一覧表");


		List<String> o文件名List = 文件全路径.getAllFile(s顧客id数据文件的全路径, false);
		if(CollectionUtils.isEmpty(o文件名List)) {
			return null;
		}

		for(String s文件名 : o文件名List) {
			long l文件SIZE = o文件記録.取得文件SIZE_by文件全路径(s文件名);
			for (int i = 0; i * i単位記録固定長度 < l文件SIZE; i++) {

				String s文件内容 = o文件記録.取得対象文件内容_by文件全路径and開始地址and単位記録長度(s文件名,
						l開始地址 + (i * i単位記録固定長度), (i+1) * i単位記録固定長度);
				//String s数据采番id = s文件内容.substring(i * i単位記録固定長度, (i + 1) * i単位記録固定長度);
				数据DTO 数据dto = to数据DTO(s文件内容);

				// 比对文件中的【业者实体数据采番ID】与【業者数据ID】
				if(StringUtils.equals(数据dto.get数据ID(), s業者数据id)) {
					// 如果匹配。就返回此【顾客实体数据采番ID】、填加找到结果。
			        File 顧客id数据一覧表File =new File(s文件名);
					数据采番idList.add(StringUtils.replace(顧客id数据一覧表File.getName(), ".data", ""));
				}
			}
		}

		CacheForThingsDB.设置Cache的Value_by函数名_param(数据采番idList,
				s函数方法名, s顧客詞条id,  s業者詞条id,
				 s業者数据id);
		return 数据采番idList;
	}

	/**
	 *
	 * @param s顧客詞条ID
	 * @param s実体数据ID
	 * @param s業者詞条ID
	 * @return
	 */
	public List<String> 検索業者詞条的実体数据_by顧客詞条IDand顧客数据IDand本詞条ID(String s顧客詞条ID,
			String s顧客数据ID, String s本詞条ID) {
		String s函数方法名 = "詞条.検索業者詞条的実体数据_by顧客詞条IDand顧客数据IDand本詞条ID"; // 用来统一函数名，避免出错
	try {
		CacheObject o结果 = (CacheObject) CacheForThingsDB.取得Cache的Value_by函数名_param(
				s函数方法名,
				new String[] {s顧客詞条ID,s顧客数据ID,s本詞条ID});
		if (o结果 == null || o结果.getValue() == null || o结果.getValue() instanceof NullObject) {
		}else {
			return (List<String>) o结果.getValue();
		}
	}catch(Throwable e) {
		System.out.println(e.getMessage());
	}
		sCallPath += "取得対象詞条的指定情報_by対象詞条名and業者名";
		/**
		例如，取得年龄词条的保存方式。
		詞条id=0000000001		顧客詞条id
		実体数据=年齢			顧客実体数据		詞条名
		保存方式=保存方式		業者詞条名			業者詞条名
		 */
		List<String> s結果List = new ArrayList<String>();

		List<String> s数据採番idList = 検索業者数据採番idList_by本詞条IDand顧客詞条IDand顧客数据ID(s本詞条ID, s顧客詞条ID,
				s顧客数据ID);

		// FORLOG_START
		String org_sCallPath = new String(sCallPath);
		sCallPath = org_sCallPath;
		// FORLOG_END

		for(String s数据採番id : s数据採番idList){

			// FORLOG_START
			org_sCallPath = new String(sCallPath);
			sCallPath += "取得対象詞条的指定情報_by対象詞条名and業者名";

			String s実体数据 = (String)取得実体数据_by詞条IDand数据採番ID(s本詞条ID, s数据採番id);

			//-------------------------------------------------------------------

			//-------------------------------------------------------------------
			sCallPath = org_sCallPath;
			// FORLOG_END

			s結果List.add(s実体数据);
		}
		CacheForThingsDB.设置Cache的Value_by函数名_param(s結果List,
				s函数方法名, s顧客詞条ID,  s顧客数据ID,  s本詞条ID);
		return s結果List;
	}

	/**
	 * 取得数据採番ID_by詞条IDand実体数据
	 * @param s詞条id
	 * @param s実体数据param
	 * @return
	 */
/*	private List<String> 検索数据採番ID_by詞条IDand実体数据(String s詞条id, String s実体数据param) {

		ID id = new ID("取得数据採番ID_by詞条IDand実体数据");
		return id.検索数据采番ID_by詞条IDand実体数据(s詞条id, s実体数据param);
	}*/

	/**
	 * 取得数据採番ID_by詞条IDand実体数据_模糊検索
	 * @param s詞条id
	 * @param s実体数据param
	 * @return
	 */
	public List<Map> 検索数据採番ID_by詞条IDand実体数据_模糊検索(String s詞条id, String s実体数据param) {
		String s函数方法名 = "詞条.検索数据採番ID_by詞条IDand実体数据_模糊検索"; // 用来统一函数名，避免出错
		myLogger.printCallMessage(sCallPath, s函数方法名 + "( 詞条ID="+ s詞条id+", "
				+ "実体数据="+ s実体数据param+")");

		// 缓存机制
		try {
			CacheObject o结果 = (CacheObject) CacheForThingsDB.取得Cache的Value_by函数名_param(s函数方法名,
					new String[] {s詞条id,s実体数据param});
			if (o结果 == null || o结果.getValue() == null || o结果.getValue() instanceof NullObject) {
			}else {
				return (List<Map>) o结果.getValue();
			}
		}catch(Throwable e) {
			System.out.println(e.getMessage());
		}

		List<Map> 数据採番IDList = new ArrayList<Map>();

		文件全路径 o文件全路径 = new 文件全路径();
		文件記録 o文件記録 = new 文件記録(sCallPath+"取得数据採番ID_by詞条IDand実体数据");

		String s採番ID文件全路径 =o文件全路径.取得対象文件全路径_by類型and詞条IDand数据ID("採番ID文件",
				Arrays.asList(s詞条id));
		String s実体数据文件全路径 = o文件全路径.取得対象文件全路径_by類型and詞条IDand数据ID("実体数据文件",
				Arrays.asList(s詞条id));
		String s実体数据索引文件全路径 = o文件全路径.取得対象文件全路径_by類型and詞条IDand数据ID("実体数据索引文件",
				Arrays.asList(s詞条id));

		int i単位記録固定長度_採番ID文件 = o文件記録.取得単位記録固定長度_by類型("採番ID文件");
		int i単位記録固定長度_索引文件 = o文件記録.取得単位記録固定長度_by類型("実体数据索引文件");

		long l文件SIZE = o文件記録.取得文件SIZE_by文件全路径(s採番ID文件全路径);

		模糊検索Plan 模糊検索plan = new 模糊検索Plan文字列Impl();

		for(long i当前采番id = 1; i当前采番id*i単位記録固定長度_採番ID文件 < l文件SIZE; i当前采番id++){
			String s索引内容 = o文件記録.取得対象文件内容_by文件全路径and開始地址and単位記録長度(s実体数据索引文件全路径,
					(i当前采番id - 1) * i単位記録固定長度_索引文件, i単位記録固定長度_索引文件);
			long l開始地址_索引内容 = o文件記録.取得開始地址_by索引内容(s索引内容);
			long l終了地址_索引内容 = o文件記録.取得終了地址_by索引内容(s索引内容);
			String s実体数据 = o文件記録.取得対象文件内容_by文件全路径and開始地址and単位記録長度(s実体数据文件全路径,
					l開始地址_索引内容, (int)(l終了地址_索引内容 - l開始地址_索引内容));
			if(StringUtils.isEmpty(s実体数据)) continue;
			if(模糊検索plan.模糊chk(s実体数据, s実体数据param)){
				Map<String, String> 検索結果 = new HashMap();

				String 数据採番ID = o文件記録.做成文件記録_by類型and記録内容("采番ID文件",i当前采番id + "");
				//検索結果.put("数据採番ID", o文件記録.做成文件記録_by類型and記録内容("采番文件",s実体数据));
				検索結果.put("数据採番ID", 数据採番ID);
				数据採番IDList.add(検索結果);
			}
		}

		CacheForThingsDB.设置Cache的Value_by函数名_param(数据採番IDList,
				s函数方法名, s詞条id, s実体数据param);
		return 数据採番IDList;

	}

	public List<Map> 検索数据採番ID_by詞条IDand実体数据_区間検索(String s詞条id, String s開始値, String s終了値,
			String s期間Format) {

		String s函数方法名 = "詞条.検索数据採番ID_by詞条IDand実体数据_区間検索"; // 用来统一函数名，避免出错
		myLogger.printCallMessage(sCallPath, s函数方法名 + "( 詞条ID="+ s詞条id+", "
				+ "開始値="+ s開始値 + ", 開始値="+ s終了値+")");
try {
		CacheObject o结果 = (CacheObject) CacheForThingsDB.取得Cache的Value_by函数名_param(s函数方法名,
				new String[] {s詞条id,s開始値,s終了値,s期間Format});
		if (o结果 == null || o结果.getValue() == null || o结果.getValue() instanceof NullObject) {
		}else {
			return (List<Map>) o结果.getValue();
		}
}catch(Throwable e) {
	System.out.println(e.getMessage());
}
		List<Map> 数据採番IDList = new ArrayList<Map>();

		文件全路径 o文件全路径 = new 文件全路径();
		文件記録 o文件記録 = new 文件記録(sCallPath + s函数方法名);

		String s採番ID文件全路径 =o文件全路径.取得対象文件全路径_by類型and詞条IDand数据ID("採番ID文件",
				Arrays.asList(s詞条id));
		String s実体数据文件全路径 = o文件全路径.取得対象文件全路径_by類型and詞条IDand数据ID("実体数据文件",
				Arrays.asList(s詞条id));
		String s実体数据索引文件全路径 = o文件全路径.取得対象文件全路径_by類型and詞条IDand数据ID("実体数据索引文件",
				Arrays.asList(s詞条id));

		int i単位記録固定長度_採番ID文件 = o文件記録.取得単位記録固定長度_by類型("採番ID文件");
		int i単位記録固定長度_索引文件 = o文件記録.取得単位記録固定長度_by類型("実体数据索引文件");

		long l文件SIZE = o文件記録.取得文件SIZE_by文件全路径(s採番ID文件全路径);

		区間検索Plan 区間検索plan = new 区間検索Plan期間Impl(s期間Format);

		for(long l = 0; l*i単位記録固定長度_採番ID文件 < l文件SIZE; l++){
			String s索引内容 = o文件記録.取得対象文件内容_by文件全路径and開始地址and単位記録長度(s実体数据索引文件全路径,
					l* i単位記録固定長度_索引文件, i単位記録固定長度_索引文件);
			long l開始地址_索引内容 = o文件記録.取得開始地址_by索引内容(s索引内容);
			long l終了地址_索引内容 = o文件記録.取得終了地址_by索引内容(s索引内容);
			String s実体数据 = o文件記録.取得対象文件内容_by文件全路径and開始地址and単位記録長度(s実体数据文件全路径,
					l開始地址_索引内容, (int)(l終了地址_索引内容 - l開始地址_索引内容));

			if(区間検索plan.区間chk(s実体数据, s開始値, s終了値)){
				Map<String, String> 検索結果 = new HashMap();
				検索結果.put("数据採番ID", o文件記録.做成文件記録_by類型and記録内容("采番文件",s実体数据));
				数据採番IDList.add(検索結果);
			}
		}

		CacheForThingsDB.设置Cache的Value_by函数名_param(数据採番IDList,
				s函数方法名, s詞条id, s開始値, s終了値,
				s期間Format);
		return 数据採番IDList;
	}

	/**
	 *
	 * @param s詞条id
	 * @param s数据採番id
	 * @return
	 */
	public String 取得実体数据_by詞条IDand数据採番ID(String s詞条id, String s数据採番id) {

		String s函数方法名 = "詞条.取得実体数据_by詞条IDand数据採番ID"; // 用来统一函数名，避免出错
		myLogger.printCallMessage(sCallPath, s函数方法名 + "( 詞条ID="+ s詞条id+", "
				+ "数据採番ID="+ s数据採番id+")");

		// 缓存机制
		try {
			CacheObject o结果 = (CacheObject) CacheForThingsDB.取得Cache的Value_by函数名_param(s函数方法名,
					new String[] {s詞条id,s数据採番id});
			if (o结果 == null || o结果.getValue() == null || o结果.getValue() instanceof NullObject) {
			}else {
				return (String) o结果.getValue();
			}
		}catch(Throwable e) {
			System.out.println(e.getMessage());
		}

		文件全路径 o文件全路径 = new 文件全路径();
		文件記録 o文件記録 = new 文件記録(sCallPath + s函数方法名);

		String s実体数据文件全路径 =
			o文件全路径.取得対象文件全路径_by類型and詞条IDand数据ID("実体数据文件", Arrays.asList(s詞条id, s数据採番id));
		String s実体数据索引文件全路径 = o文件全路径.取得対象文件全路径_by類型and詞条IDand数据ID("実体数据索引文件",
				Arrays.asList(s詞条id));
		int i単位記録长度_実体数据索引 = o文件記録.取得単位記録固定長度_by類型("実体数据索引文件");

		String s索引内容 = o文件記録.取得対象文件内容_by文件全路径and開始地址and単位記録長度(s実体数据索引文件全路径,
				(Long.parseLong(s数据採番id)-1) * i単位記録长度_実体数据索引, i単位記録长度_実体数据索引);
		if(StringUtils.isEmpty(s索引内容) || StringUtils.isBlank(s索引内容)){return null;}
		long l開始地址 = o文件記録.取得開始地址_by索引内容(s索引内容);
		long l終了地址 = o文件記録.取得終了地址_by索引内容(s索引内容);

		String s実体数据 = o文件記録.取得対象文件内容_by文件全路径and開始地址and単位記録長度(s実体数据文件全路径, l開始地址,
				(int)(l終了地址 - l開始地址));

		// 缓存机制
		CacheForThingsDB.设置Cache的Value_by函数名_param(s実体数据,
				s函数方法名, s詞条id, s数据採番id);
		return s実体数据;
	}

	/**
	 *
	 * @param s詞条id
	 * @param s顧客詞条id
	 * @param s顧客数据id
	 * @return
	 */
	public List<String> 検索業者数据採番idList_by本詞条IDand顧客詞条IDand顧客数据ID(String s本詞条id, String s顧客詞条id,
			String s顧客数据id) {
		String s函数方法名 = "詞条.検索業者数据採番idList_by本詞条IDand顧客詞条IDand顧客数据ID"; // 用来统一函数名，避免出错
		myLogger.printCallMessage(sCallPath, s函数方法名 + "( 詞条ID="+ s本詞条id+", 顧客詞条ID="+ s顧客詞条id+", "
				+ "顧客詞条ID="+ s顧客数据id+")");
		/**
		 * 	一个顾客，来到银行（）
			说，给我取钱。

			先取得顾客id数据文件的全路径
			詞条ID（文件夹）
				顧客（文件夹）
					顧客詞条id（文件夹）
						顧客数据id （文件）
							该詞条的数据采番id1
							该詞条的数据采番id2
							该詞条的数据采番id2
							该詞条的数据采番id2
							该詞条的数据采番id3
			缺一个【顧客数据id】
			取得该文件的全部记录
		 */
try {
		CacheObject o结果 = (CacheObject) CacheForThingsDB.取得Cache的Value_by函数名_param(
				s函数方法名,
				new String[] {s本詞条id,s顧客詞条id,s顧客数据id});
		if (o结果 == null || o结果.getValue() == null || o结果.getValue() instanceof NullObject) {
		}else {
			return (List<String>) o结果.getValue();
		}
	}catch(Throwable e) {
		System.out.println(e.getMessage());
	}
		文件全路径 o文件全路径 = new 文件全路径();
		文件記録 o文件記録 = new 文件記録(sCallPath + s函数方法名);
		String s類型 = "顧客id数据一覧表";

		String s顧客id数据文件的全路径 = o文件全路径.取得対象文件全路径_by類型and詞条IDand数据ID(s類型,
				Arrays.asList(s本詞条id, s顧客詞条id, s顧客数据id));
		long l開始地址 = 0L;
		int i単位記録固定長度 = o文件記録.取得単位記録固定長度_by類型(s類型);
		long l文件SIZE = o文件記録.取得文件SIZE_by文件全路径(s顧客id数据文件的全路径);

		List<String> 数据采番idList = new ArrayList<String>();
		for (int i = 0; i * i単位記録固定長度 < l文件SIZE; i++) {

			String s文件内容 = o文件記録.取得対象文件内容_by文件全路径and開始地址and単位記録長度(s顧客id数据文件的全路径,
					l開始地址 + (i * i単位記録固定長度), (i+1) * i単位記録固定長度);
			//String s数据采番id = s文件内容.substring(i * i単位記録固定長度, (i + 1) * i単位記録固定長度);
			数据DTO 数据dto = to数据DTO(s文件内容);
			数据采番idList.add(数据dto.get数据ID());

		}

		CacheForThingsDB.设置Cache的Value_by函数名_param(数据采番idList,
				s函数方法名, s本詞条id, s顧客詞条id,
				s顧客数据id);
		return 数据采番idList;

	}

	public String 取得詞条ID_by詞条名_Primeval(String s詞条名){

		String s函数方法名 = "詞条.取得詞条ID_by詞条名_Primeval"; // 用来统一函数名，避免出错
		if(null == s詞条名){
			return null;
		}
try {
		CacheObject o结果 = (CacheObject) CacheForThingsDB.取得Cache的Value_by函数名_param(s函数方法名,
				new String[] {s詞条名});
		if (o结果 == null || o结果.getValue() == null || o结果.getValue() instanceof NullObject) {
		}else {
			return (String) o结果.getValue();
		}
}catch(Throwable e) {
	System.out.println(e.getMessage());
}
		// String s詞条id= 取得詞条顧客ID_by詞条IDand実体数据("0000000002", s詞条名);
		顧客 o顧客 = new 顧客(sCallPath + s函数方法名);


		// 从第二条数据开始，每一条都是它的顾客信息
		List<数据DTO> 数据DTOList = o顧客.取得顧客数据DTOList_by詞条名and実体数据("詞条", s詞条名);

		if(CollectionUtils.isEmpty(数据DTOList)){
			// FORLOG_START
			String org_sCallPath = new String(sCallPath);
			sCallPath += s函数方法名;

			String sResult = 追加詞条_by詞条名( s詞条名);
			詞条Map.put(sResult, s詞条名);

			sCallPath = org_sCallPath;
			// FORLOG_END
			CacheForThingsDB.设置Cache的Value_by函数名_param(sResult,
					s函数方法名, s詞条名);
			return sResult;
		}

		CacheForThingsDB.设置Cache的Value_by函数名_param(数据DTOList.get(0).get数据ID(),
				s函数方法名, s詞条名);
		return 数据DTOList.get(0).get数据ID();
	}
	/**
	 *
	 * @param s詞条名
	 * @return
	 */
	public String 取得詞条ID_by詞条名(String s詞条名){
		String s函数方法名 = "詞条.取得詞条ID_by詞条名"; // 用来统一函数名，避免出错
		myLogger.printCallMessage(sCallPath, s函数方法名 + "( 詞条名="+ s詞条名+")");
		/**
		 *  String s詞条id=取得该詞条的客户ID_by詞条idand実体数据（00000000003，词条名）
			//如果作为别名，其对应的詞条已经存在。则返回所属詞条名
			if（s詞条id！=null）｛

			//如果作为别名，其对应的詞条不存在
			else｛
				s詞条id=■新建词条_by该詞条名

			｝
			rerurn s詞条id；
		 *
		 */

		/**
		   詞条名称				詞条id      詞条
		   -----------------------------------------
		   	詞条				0000000001  詞条
		    別名				0000000002  別名
			保存方式			0000000003  直存/転存
			保存形式			0000000004  数据/文件
		 */

		/**
		 * 这个要变成Java缓存了，因为实在是太需要缓存来减少数据的读写操作了。
		 *
		 */

		if(StringUtils.isEmpty(s詞条名)){
			return null;
		}

		// 缓存机制
		try {
			CacheObject o结果 = (CacheObject) CacheForThingsDB.取得Cache的Value_by函数名_param(s函数方法名,
					new String[] {s詞条名});
			if (o结果 == null || o结果.getValue() == null || o结果.getValue() instanceof NullObject) {
			}else {
				return (String) o结果.getValue();
			}
		}catch(Throwable e) {
			System.out.println(e.getMessage());
		}
//		// String s詞条id= 取得詞条顧客ID_by詞条IDand実体数据("0000000002", s詞条名);
//		顧客 o顧客 = new 顧客(sCallPath + "取得詞条ID_by詞条名");

		switch (s詞条名) {

		case "詞条":
			return "0000000001";
		case "別名":
			return "0000000002";
		case "保存方式":
			return "0000000003";
		case "保存形式":
			return "0000000004";

		}

		/*=================================
		 *  如果在詞条Map中存在，就直接返回。
		 =================================*/
		for (Map.Entry<String, String> entry今 : 詞条Map.entrySet()) {
			if(entry今.getValue().equals(s詞条名)){
				CacheForThingsDB.设置Cache的Value_by函数名_param(entry今.getKey(),
						s函数方法名, s詞条名);
				return entry今.getKey();
			}
		}
		String s詞条ID = 取得詞条ID_by詞条名_Primeval(s詞条名);
		CacheForThingsDB.设置Cache的Value_by函数名_param(s詞条ID,
				s函数方法名, s詞条名);
		return s詞条ID;
	}


	/**
	 *
	 * @param s詞条名
	 * @return
	 */
	public String 追加詞条_by詞条名( String s詞条名) {

		myLogger.printCallMessage(sCallPath,"詞条.追加詞条_by詞条名( 詞条名="+ s詞条名+")");

		文件全路径	o文件全路径 = new 文件全路径();
		文件記録 o文件記録 = new 文件記録(sCallPath+"追加詞条_by詞条名");

		String s採番文件全路径 = o文件全路径.取得対象文件全路径_by類型and詞条IDand数据ID(
				"采番ID文件",
				Arrays.asList("0000000001"));
		String s種類 = "采番ID文件";

		ID id = new ID(sCallPath + "追加詞条_by詞条名");

		String s数据採番ID =id.採番_by詞条名and実体数据("詞条", s詞条名);

		// 缓存机制
		String s函数方法名 = "詞条.取得詞条ID_by詞条名"; // 用来统一函数名，避免出错
		CacheForThingsDB.设置Cache的Value_by函数名_param(s詞条名, s函数方法名);

		return s数据採番ID;

	}


	/**
	 *
	 * @param 処理対象dto
	 * @param 已処理数据
	 */
	public 数据DTO 追加処理_単条数据(対象数据DTO 処理対象dto, List<数据DTO> 已処理数据){


		myLogger.printCallMessage(sCallPath,"詞条.追加処理_単条数据( "+ 処理対象dto.toString() + 已処理数据.toString()+")");

		/**
		 * //chk_词条
			■取得对象詞条的指定情報_by詞条and業者【存储方式】

			如果是【转存】
				■取得对象词条的【转存】对象
				■計算【转存对象】的计算结果
				■ 追加记录_by追加对象数据and计算结果( 追加处理对象DTO, 计算结果， 已处理数据==我 )

			如果是【直存】
				■设置计算结果					//list.add（）；
				■ 追加记录_by追加对象数据and计算结果( 追加处理对象DTO, 计算结果， 已处理数据==我 )

			■追加当前词条与已处理完词条的关系记录
				如果不存在已处理完词条
					无处理
				如果存在已处理完词条
					//把已处理完词条作为客户加到当前词条
					追加客户_by本词条and客户词条
		 */

		// FORLOG_START
		String org_sCallPath = new String(sCallPath);
		sCallPath += "追加処理_単条数据";

		数据DTO 数据dto = 追加直接記録_by処理対象DTO(処理対象dto,已処理数据);

		sCallPath = org_sCallPath;
		// FORLOG_END

		return 数据dto;

	}

	/**
	 *
	 * @param 処理対象dto
	 * @param 已処理数据
	 */
	private 数据DTO 追加直接記録_by処理対象DTO(対象数据DTO 対象数据dto, List<数据DTO> 已処理数据) {

		myLogger.printCallMessage(sCallPath,
				"詞条.追加直接記録_by処理対象DTO( "+ 対象数据dto.toString() + 已処理数据.toString()+")");

		/**
		 * ■設置計算結果
		   ■ 追加记录_by追加对象数据and計算结果( 追加处理对象DTO, 計算結果， 已処理数据==我 )
		 */
		// FORLOG_START
		String org_sCallPath = new String(sCallPath);
		sCallPath += "追加直接記録_by処理対象DTO";

		数据DTO 数据dto = 追加記録_by対象数据and已処理数据(対象数据dto, 已処理数据);

		sCallPath = org_sCallPath;
		// FORLOG_END

		return 数据dto;
	}

	/**
	 *
	 * @param 処理対象dto
	 * @param 已処理数据
	 */
	/*
	private 数据DTO 追加転存記録_by処理対象DTO(対象数据DTO 処理対象dto, List<数据DTO> 已処理数据){

		myLogger.printCallMessage(sCallPath,
		"詞条.追加転存記録_by処理対象DTO( "+ 処理対象dto.toString() + 已処理数据.toString()+")");

		List<数据DTO> resultList =new ArrayList();
		*//**
		   ■取得对象词条的【転存】対象
		   ■計算【転存对象】的計算结果
		   ■ 追加记录_by追加对象数据and计算结果( 追加处理对象DTO, 計算結果， 已処理数据==我 )
		 *//*
		// 取得対象词条的【转存】対象
		詞条 o詞条 = new 詞条(sCallPath +"追加転存記録_by処理対象DTO");

		// 这里计算的其实是 id="0000000001"的词条的，数据=処理対象dto.getS詞条()，的一个属性叫做"転存対象"的具体值。
		//List<String> s転存対象List = o詞条.取得対象詞条的指定情報_by対象詞条IDand実体数据and業者名("0000000001",
		 * 処理対象dto.getS詞条(), "転存対象");
		List<String> s転存対象List = o詞条.検索業者詞条的実体数据_by顧客詞条IDand顧客数据IDand業者詞条ID("0000000001",
		処理対象dto.getS詞条(), "転存対象");

		if(s転存対象List == null){
			return null;
		}

		//計算【転存対象】的計算结果
		Map<String,String> 条件 = new HashMap<String,String>();
		条件.put("入力1", 処理対象dto.getS実体数据());

		NOSQL nosql = new NOSQL();
		List<Map> 計算結果MapList = nosql.取得指定値_根据入力字典and出力字典and入力数据(
		処理対象dto.getS詞条(),s転存対象List.get(0), 条件);

		for(Map 計算結果map : 計算結果MapList){

			数据DTO 数据dto = 追加記録_by対象数据and已処理数据( new 対象数据DTO(s転存対象List.get(0),
			(String)計算結果map.get(s転存対象List.get(0))), 已処理数据 );

			resultList.add(数据dto);

		}

		return resultList.size()>0?resultList.get(0):null;
	}*/

	/**
	 *
	 * @param 処理対象dto：詞条=生年月日；実体数据=計算出的生年月日的数据；已処理数据
	 * @param 已処理数据=年齢的詞条；年齢的数据的采番ID
	 */
	private 数据DTO 追加記録_by対象数据and已処理数据(対象数据DTO 対象数据dto, List<数据DTO> 已処理数据){

		myLogger.printCallMessage(sCallPath,
				"詞条.追加記録_by対象数据and已処理数据( "+ 対象数据dto.toString() + 已処理数据.toString()+")");

		ID id = new ID(sCallPath + "追加記録_by対象数据and已処理数据");

		String s詞条名 = 対象数据dto.getS詞条();
		String s実体数据 = 対象数据dto.getS実体数据();

		// FORLOG_START
		String org_sCallPath = new String(sCallPath);
		sCallPath += "追加記録_by対象数据and已処理数据";

		String s詞条id =  取得詞条ID_by詞条名(s詞条名);

		sCallPath = org_sCallPath;
		// FORLOG_END

		String s数据採番id = id.採番_by詞条名and実体数据(s詞条名, s実体数据);

		数据DTO 主体数据 = new 数据DTO(s詞条id, s数据採番id);

		for(数据DTO 数据dto : 已処理数据){

			数据DTO 顧客数据 = new 数据DTO(数据dto);

			顧客 o顧客 = new 顧客(sCallPath + "追加記録_by対象数据and已処理数据");
			o顧客.追加顧客関係_by主体数据and顧客数据(主体数据, 顧客数据);

			業者 o業者 = new 業者(sCallPath + "追加記録_by対象数据and已処理数据");
			o業者.追加業者関係_by主体数据and業者数据(顧客数据, 主体数据);
		}

		// 缓存机制
		String s函数方法名 = "詞条.取得実体数据_by詞条IDand数据採番ID"; // 用来统一函数名，避免出错
		CacheForThingsDB.设置Cache的Value_by函数名_param(s実体数据,
				s函数方法名, s詞条id, s数据採番id);

		return new 数据DTO(s詞条id, s数据採番id);

	}


}
