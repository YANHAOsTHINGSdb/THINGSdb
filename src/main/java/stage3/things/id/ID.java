package stage3.things.id;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import net.oschina.j2cache.CacheObject;
import net.oschina.j2cache.NullObject;
import stage3.cache.CacheForThingsDB;
import stage3.log.MyLogger;
import stage3.things.file.文件全路径;
import stage3.things.file.文件記録;

public class ID {

	String sCallPath = null;
	MyLogger myLogger = new MyLogger();
	public ID(String sCallPath) {
		this.sCallPath = sCallPath;
	}

	/**
	 *
	 * @param s対象文件全路径=ID文件全路径
	 * @param s種類(采番id, 実体数据索引文件, 索引地址, id顧客数据一覧表, 顧客id数据一覧表, 業者詞条id一覧表)
	 * @return
	 */
	public String 採番_by対象文件全路径and種類(String s対象文件全路径, String s種類) {

		myLogger.printCallMessage(sCallPath,"ID.採番_by対象文件全路径and種類( 対象文件全路径="+ s対象文件全路径+", 種類="+ s種類+")");

		Long ID = null;
		文件記録 o文件記録 = new 文件記録(sCallPath + "採番_by対象文件全路径and種類");
		Long l文件SIZE = o文件記録.取得文件SIZE_by文件全路径(s対象文件全路径);
		Long l固定SIZE = (long)o文件記録.取得単位記録固定長度_by類型(s種類);
		ID = l文件SIZE / l固定SIZE + 1;
		/*
		 * 例如，要求：6位编号自动生成，递增，格式为“000001”。 解释：0代表前面要补的字符，6代表字符串长度，d表示参数为整数类型
		 */

		String s追加内容 = o文件記録.做成文件記録_by類型and記録内容("采番ID文件", ID+"");
		o文件記録.写入文件_by写入内容and文件全路径(s追加内容, s対象文件全路径);

		return s追加内容;

	}

	/**
	 *
	 * @param s詞条名
	 * @param s実体数据
	 * @return
	 */
	public String 採番_by詞条名and実体数据(String s詞条名, String s実体数据) {
		myLogger.printCallMessage(sCallPath,"ID.採番_by詞条名and実体数据( 詞条名="+ s詞条名+", 実体数据="+ s実体数据+")");


		// 先根据词条名找到其所属的词条ID
		詞条 o詞条 = new 詞条(sCallPath +"採番_by詞条名and実体数据");
		文件記録 o文件記録 = new 文件記録(sCallPath + "採番_by詞条名and実体数据");

		String s詞条id = o詞条.取得詞条ID_by詞条名(s詞条名);
		String s類型 = "采番ID文件";

		/**
		 * 如果 已经存在同值的数据，那就干脆用同值的ID吧 19-01-06
		 */
		ID idObject = new ID(sCallPath + "採番_by詞条名and実体数据");
		List<String> s既存数据采番ID = idObject.検索数据采番ID_by詞条IDand実体数据(s詞条id, s実体数据);
		if(! CollectionUtils.isEmpty(s既存数据采番ID)) {
			return s既存数据采番ID.get(0);
		}

		文件全路径 o文件全路径 = new 文件全路径();
		String s対象文件全路径 = o文件全路径.取得対象文件全路径_by類型and詞条IDand数据ID(s類型, Arrays.asList(s詞条id));

		// FORLOG_START
		String org_sCallPath = new String(sCallPath);
		sCallPath += "採番_by詞条名and実体数据";

		String s数据采番ID = 採番_by対象文件全路径and種類(s対象文件全路径, s類型);

		sCallPath = org_sCallPath;
		// FORLOG_END

		// 数据的开始的地址，要 在 写入前就采好 added 20180622
		String s開始地址 = o文件記録.取得実体数据開始地址_by類型ands詞条ID("実体数据文件", s詞条id);

		// 如果实体数据不为空
		if ( !StringUtils.isEmpty(s実体数据)) {

			// 则在实体数据文件中追加记录
			// （不要忘记索引文件）
			String s追加内容 =  s実体数据;
			s類型 = "実体数据文件";
			s対象文件全路径 = o文件全路径.取得対象文件全路径_by類型and詞条IDand数据ID(s類型, Arrays.asList(s詞条id, s数据采番ID));


			// 追加实体数据记录
			o文件記録.追加記録_by類型and追加内容and文件全路径(s類型, s追加内容, s対象文件全路径);
			// 計算記録长度
			// 取得开始地址

		}

		// 设置要追加的索引文件的内容
		// 取得索引文件全路径

		s類型 = "実体数据索引文件";
		String s索引文件全路径 = o文件全路径.取得対象文件全路径_by類型and詞条IDand数据ID(s類型, Arrays.asList(s詞条id, s数据采番ID));
		// 索引的值是根据采番id取得的。具体方法是。每条索引记录都是固定长度的。它的开始地址就是（采番id-1）*固定长度
		// 追加记录的时候。你只需要把整理好的索引追加内容追加写入即可
		// 取得记录的时候，计算好开始地址和长度。直接取得即可

		// 关于索引内容。采番id的长度是固定的；至于开始地址的位置。需要事先给定每条地址的最大宽度。余下不足的补零。
		Long l終了地址 = Long.parseLong(s開始地址) + (s実体数据 != null ? s実体数据.getBytes().length:0);

		myLogger.printCallMessage(sCallPath+"採番_by詞条名and実体数据","s実体数据.Length = "+ (s実体数据 != null ? s実体数据.getBytes().length:0));

		s開始地址 = o文件記録.做成文件記録_by類型and記録内容("索引地址", s開始地址);
		String s終了地址 = o文件記録.做成文件記録_by類型and記録内容("索引地址", "" + l終了地址);
		String s索引内容 = s開始地址  + s終了地址;
		if(s実体数据 == null){
			s索引内容 = "";
		}

		String s追加索引内容 = o文件記録.做成文件記録_by類型and記録内容(s類型, s索引内容);

		o文件記録.追加記録_by類型and追加内容and文件全路径(s類型, s追加索引内容, s索引文件全路径);


		return s数据采番ID;
	}

	/**
	 *
	 * @param s詞条ID
	 * @param s実体数据_param
	 * @return
	 *
	 *  式样。变。更：理由是满足条件的不仅仅只有一个。
	 */
	//public String 取得数据采番ID_by詞条IDand実体数据_(String s詞条ID, String s実体数据_param) {
	public List<String> 検索数据采番ID_by詞条IDand実体数据(String s詞条ID, String s実体数据_param) {

		String s函数方法名 = "ID.検索数据采番ID_by詞条IDand実体数据"; // 用来统一函数名，避免出错
		myLogger.printCallMessage(sCallPath, s函数方法名 + "( 詞条ID="+ s詞条ID+", 実体数据="+ s実体数据_param+")");

		try {
			CacheObject o结果 = (CacheObject) CacheForThingsDB.取得Cache的Value_by函数名_param(s函数方法名,
					new String[] {s詞条ID, s実体数据_param});
			if (o结果 == null || o结果.getValue() == null || o结果.getValue() instanceof NullObject) {
			}else {
				return (List<String>) o结果.getValue();
			}
		}catch(Throwable e) {
			System.out.println(e.getMessage());
		}
		List<String> 数据采番ID_List = new ArrayList();

		文件全路径 o文件全路径 = new 文件全路径();
		文件記録 o文件記録 = new 文件記録(sCallPath + "取得数据采番ID_by詞条IDand実体数据");

		String s采番ID文件全路径 = o文件全路径.取得対象文件全路径_by類型and詞条IDand数据ID("采番ID文件", Arrays.asList(s詞条ID));
		String s実体数据文件全路径 = o文件全路径.取得対象文件全路径_by類型and詞条IDand数据ID("実体数据文件", Arrays.asList(s詞条ID));
		String s実体数据索引文件全路径 = o文件全路径.取得対象文件全路径_by類型and詞条IDand数据ID("実体数据索引文件", Arrays.asList(s詞条ID));
		long l采番ID文件Size = o文件記録.取得文件SIZE_by文件全路径(s采番ID文件全路径);
		int i単位記録长度_采番ID = o文件記録.取得単位記録固定長度_by類型("采番ID文件");
		int 単位記録长度_実体数据索引 = o文件記録.取得単位記録固定長度_by類型("実体数据索引文件");
		//for (int i当前采番id = 0; i当前采番id * i単位記録长度_采番ID < l采番ID文件Size; i当前采番id++) {
		for (int i当前采番id = 1; i当前采番id * i単位記録长度_采番ID <= l采番ID文件Size; i当前采番id++) {

			詞条 o詞条 = new 詞条(sCallPath +"取得数据采番ID_by詞条IDand実体数据");
			Object s実体数据 = o詞条.取得実体数据_by詞条IDand数据採番ID(s詞条ID, i当前采番id+"");

			if(StringUtils.isEmpty(s実体数据_param)) {
				// 如果入力的【s実体数据_param】为空。那么就是无条件查询，那么就全部取出
				//数据采番ID_List.add(o文件記録.做成文件記録_by類型and記録内容("采番ID文件",i当前采番id +1+ ""));
				数据采番ID_List.add(o文件記録.做成文件記録_by類型and記録内容("采番ID文件",i当前采番id + ""));
			}else
			if (!StringUtils.isEmpty(s実体数据) && s実体数据_param.equals(s実体数据)) {
				//数据采番ID_List.add(o文件記録.做成文件記録_by類型and記録内容("采番ID文件",i当前采番id +1+ ""));
				//数据采番ID_List.add(o文件記録.做成文件記録_by類型and記録内容("采番ID文件",i当前采番id==0 ? "1" : i当前采番id + ""));
				数据采番ID_List.add(o文件記録.做成文件記録_by類型and記録内容("采番ID文件",i当前采番id +""));
			}
		}
		List<String> l结果 = CollectionUtils.isEmpty(数据采番ID_List)? null:数据采番ID_List;

		// 缓存机制
		if(! CollectionUtils.isEmpty(l结果)) {
			CacheForThingsDB.设置Cache的Value_by函数名_param(l结果,
				s函数方法名, s詞条ID, s実体数据_param);
		}
		return l结果;

	}


}
