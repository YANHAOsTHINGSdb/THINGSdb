package stage3.things.relations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import stage3.consts.PublicName;
import stage3.log.MyLogger;
import stage3.things.dto.DTO;
import stage3.things.dto.数据DTO;
import stage3.things.file.文件全路径;
import stage3.things.file.文件夹;
import stage3.things.file.文件記録;
import stage3.things.id.ID;
import stage3.things.id.詞条;

public class 顧客  extends DTO{

	String sCallPath = null;
	MyLogger myLogger = new MyLogger();

	public 顧客(String sCallPath) {
		this.sCallPath = sCallPath;
	}

	public void 追加顧客関係_by主体数据and顧客数据(数据DTO 主体数据, 数据DTO 顧客数据) {

//myLogger.printCallMessage(sCallPath,"顧客.追加顧客関係_by主体数据and顧客数据( 主体数据=" + 主体数据.toString() + ", 顧客数据=" + 顧客数据.toString() + ")");

		//		追加_顧客詞条id一覧表記録(主体数据, 顧客数据);
		//
		//		追加_顧客数据id一覧表記録(主体数据, 顧客数据);

		/*
		|---id_GUEST
				|-----0000000001.data
						|-------000000001600000000010000000017000000000100000000160000000002000000001700000000020000000016000000000300000000170000000003
		*/
		追加_id顧客数据一覧表記録(主体数据, Arrays.asList(顧客数据));


		追加_顧客id数据一覧表記録(Arrays.asList(主体数据), 顧客数据);

	}

	//	public void 追加_顧客詞条id一覧表記録(数据DTO 主体数据dto, 数据DTO 顧客数据dto) {
	//		String s類型 = "顧客詞条id一覧表";
	//		String s詞条id = 主体数据dto.get詞条ID();
	//		String s数据id = 主体数据dto.get数据ID();
	//
	//		文件全路径 o文件全路径 = new 文件全路径();
	//		String s文件全路径_顧客詞条id一覧表 = o文件全路径.取得対象文件全路径_by類型and詞条IDand数据ID(s類型, Arrays.asList(s詞条id, s数据id));
	//
	//		//追加内容 = 主体数据.数据ID + "," + 顧客数据.詞条ID
	//		String s追加内容= 顧客数据dto.get詞条ID();
	//
	//		文件記録 o文件記録 = new 文件記録();
	//		o文件記録.追加記録_by類型and追加内容and文件全路径(s類型, s追加内容, s文件全路径_顧客詞条id一覧表);
	//
	//	}
	//
	//
	//	private void 追加_顧客数据id一覧表記録(数据DTO 主体数据dto, 数据DTO 顧客数据dto) {
	//		String s類型 = "顧客数据id一覧表";
	//
	//		String s詞条id = 主体数据dto.get詞条ID();
	//		String s数据id = 主体数据dto.get数据ID();
	//		文件全路径 o文件全路径 = new 文件全路径();
	//		String s文件全路径_顧客数据id一覧表 = o文件全路径.取得対象文件全路径_by類型and詞条IDand数据ID(s類型, Arrays.asList(s詞条id, s数据id));
	//
	//		//追加内容 = 主体数据.数据ID + "," + 顧客数据.数据ID
	//		String s追加内容= 顧客数据dto.get詞条ID();
	//
	//		文件記録 o文件記録 = new 文件記録();
	//		o文件記録.追加記録_by類型and追加内容and文件全路径(s類型,s追加内容,s文件全路径_顧客数据id一覧表);
	//
	//	}
/*
|---id_GUEST
		|-----0000000001.data
				|-------000000001600000000010000000017000000000100000000160000000002000000001700000000020000000016000000000300000000170000000003
*/
	private void 追加_id顧客数据一覧表記録(数据DTO 主体数据dto, List<数据DTO> 顧客数据List) {
		//本词条 的 具体 实体数据的id 对应有哪些顾客的哪些id（要求1个id有一个专门文件。该文件中，左面是顾客词条id，右面是顾客数据id）

//myLogger.printCallMessage(sCallPath,"顧客.追加_id顧客数据一覧表記録( 主体数据dto=" + 主体数据dto.toString() + ", 顧客数据List=" + 顧客数据List.toString() + ")");

		String s類型 = PublicName.KEY_id顧客数据一覧表;

		文件全路径 o文件全路径 = new 文件全路径();
		//怕是要新建一个文件了
		String s文件全路径_id顧客数据一覧表 = o文件全路径.取得対象文件全路径_by類型and詞条IDand数据ID(s類型,
				Arrays.asList(主体数据dto.get詞条ID(), 主体数据dto.get数据ID()));
		// FORLOG_START
		String org_sCallPath = new String(sCallPath);
		sCallPath += "追加_id顧客数据一覧表記録";

		String s追加内容 = 数据DTOList出力(顧客数据List);

		sCallPath = org_sCallPath;
		// FORLOG_END

		文件記録 o文件記録 = new 文件記録(sCallPath + "追加_id顧客数据一覧表記録");
		o文件記録.追加記録_by類型and追加内容and文件全路径(s類型, s追加内容, s文件全路径_id顧客数据一覧表);
	}

	private void 追加_顧客id数据一覧表記録(List<数据DTO> 主体数据List, 数据DTO 顧客数据dto) {
		//本词条 的 具体 实体数据的id 对应有哪些顾客的哪些id（要求1个id有一个专门文件。该文件中，左面是顾客词条id，右面是顾客数据id）

//myLogger.printCallMessage(sCallPath,"顧客.追加_顧客id数据一覧表記録( 主体数据List=" + 主体数据List.toString() + ", 顧客数据dto=" + 顧客数据dto.toString() + ")");

		String s類型 = PublicName.KEY_顧客id数据一覧表;

		String s詞条id = 主体数据List.get(0).get詞条ID();

		文件全路径 o文件全路径 = new 文件全路径();
		String s文件全路径_顧客id数据一覧表 = o文件全路径.取得対象文件全路径_by類型and詞条IDand数据ID(s類型,
				Arrays.asList(s詞条id, 顧客数据dto.get詞条ID(), 顧客数据dto.get数据ID()));

		// FORLOG_START
		String org_sCallPath = new String(sCallPath);
		sCallPath += "追加_顧客id数据一覧表記録";

		//追加内容 = 格式化输出文件内容（List = 主体数据.数据ID）
		String s追加内容 = 数据DTOList出力(主体数据List);

		sCallPath = org_sCallPath;
		// FORLOG_END

		文件記録 o文件記録 = new 文件記録(sCallPath + "追加_顧客id数据一覧表記録");
		o文件記録.追加記録_by類型and追加内容and文件全路径(s類型, s追加内容, s文件全路径_顧客id数据一覧表);

	}

	/**
	 * 取出主体数据List中的数据ID，存入[G词条ID]文件
	 * @param 主体数据List
	 * @return
	 */
	private String 数据DTOList出力(List<数据DTO> 主体数据List) {
//myLogger.printCallMessage(sCallPath, "顧客.数据DTOList出力( 主体数据List=" + 主体数据List.toString());

		String sList出力 = "";

		for (数据DTO 数据dto : 主体数据List) {

			/**
			 * 原来，是要把词条ID也写入的
			 * 后来，由于把词条ID作为文件夹使用
			 *      所以就没有必要再写入了         2019-01-11
			 * 再后来，由于需要加内容，还是维持原判吧 2019-01-12
			 *
			 * [本词条ID]                          【4】
			 * |------id.data                      // 采番文件，内容为采番文件
			 * |------Data_index.data              // 索引文件，内容为索引文件
			 * |------Data.data                    // 实体数据，内容为实体数据
			 * |------[WAITER]                     // 文件夹名为WAITER，内容为所有用来解释本词条的辅助词条信息
			 * |       |------                     // 文件名为本词条ID采番ID, 内容为针对本词条采番ID所有的辅助词条ID
			 * |------[GUEST]                      // 文件夹名为GUEST，内容为所有需要本词条解释的顾客词条信息
			 *         |------[G词条ID]             // 文件夹名为GUEST词条ID，内容为该G词条与本词条的所有关联信息
			 *                |------G词条ID+G数据ID // 文件名为GUEST数据ID，内容为本词条数据ID
			 */
			sList出力 += (数据dto.get詞条ID() + 数据dto.get数据ID());
		}

		return sList出力;
	}

	public List<数据DTO> 取得顧客数据DTOList_by詞条名and実体数据(String s本詞条名, String s実体数据) {
		//根据【詞条名】和【実体数据】来找到【数据id】
		//如果有余力
		//把 这 条数据的顧客也一并找到
//myLogger.printCallMessage(sCallPath, "顧客.取得顧客数据DTOList_by詞条名and実体数据( s詞条名=" + s本詞条名 + ", 実体数据=" + s実体数据 + ")");

		詞条 o詞条 = new 詞条(sCallPath + "取得顧客数据DTOList_by詞条名and実体数据");
		String s本詞条id = o詞条.取得詞条ID_by詞条名(s本詞条名);

		ID id = new ID(sCallPath + "取得顧客数据DTOList_by詞条名and実体数据");

		List<String> s本数据id_List = id.検索数据采番ID_by詞条IDand実体数据(s本詞条id, s実体数据);

		if(CollectionUtils.isEmpty(s本数据id_List)) {
			return null;
		}else {
			return 取得_複数_顧客数据DTOList_by本詞条IDand本数据采番ID(s本詞条id,s本数据id_List);
		}

	}

	private List<数据DTO> 取得_複数_顧客数据DTOList_by本詞条IDand本数据采番ID(String s本詞条id, List<String> s本数据id_List ) {
//myLogger.printCallMessage(this.sCallPath,"顧客.取得_複数_顧客数据DTOList_by本詞条IDand本数据采番ID(s本詞条id="+s本詞条id+")");

		List<数据DTO> result数据DTOlist = new ArrayList<数据DTO>();

		for (String s本数据id : s本数据id_List) {

			if (StringUtils.isEmpty(s本数据id)) {
				return null;
			} else {
				result数据DTOlist.add(new 数据DTO(s本詞条id, s本数据id));
			}

			// FORLOG_START
			String org_sCallPath = new String(sCallPath);
			sCallPath += "取得顧客数据DTOList_by詞条名and実体数据";

			//
			//
			//
			//
			//

			List<数据DTO> 数据DTOlist = 取得W数据DTOList_by詞条IDand数据采番ID(s本詞条id, s本数据id);

			sCallPath = org_sCallPath;
			// FORLOG_END

			if (! CollectionUtils.isEmpty(数据DTOlist)) {
				//从第二条数据开始，每一条都是它的顾客信息
				result数据DTOlist.addAll(数据DTOlist);

			}
		}
		return result数据DTOlist;
	}

	// 将所有为之服务的W詞条ID数据ID一起取出
	private List<数据DTO> 取得W数据DTOList_by詞条IDand数据采番ID(String s本詞条id, String s本数据id) {

//myLogger.printCallMessage(sCallPath,"顧客.取得顧客数据DTOList_by詞条IDand数据采番ID( 詞条ID=" + s本詞条id + ", 数据采番ID=" + s本数据id + ")");

		List<数据DTO> 数据DTOList = new ArrayList<数据DTO>();
		文件全路径 o文件全路径 = new 文件全路径();
		文件記録 o文件記録 = new 文件記録(sCallPath + "取得顧客数据DTOList_by詞条IDand数据采番ID");

		if(s本詞条id.equals("0000000001") && s本数据id.equals("0000000011")) {

			s本詞条id = "0000000001";
		}
		String s類型1 = PublicName.KEY_id顧客数据一覧表;
		String s文件全路径_id顧客数据一覧表 = o文件全路径.取得対象文件全路径_by類型and詞条IDand数据ID(s類型1, Arrays.asList(s本詞条id, s本数据id));

		long l開始地址1 = 0;
		int 単位記録固定長度1 = o文件記録.取得単位記録固定長度_by類型(s類型1);
		long l文件SIZE1 = o文件記録.取得文件SIZE_by文件全路径(s文件全路径_id顧客数据一覧表);

		String s顧客詞条IDs = o文件記録.取得対象文件内容_by文件全路径and開始地址and単位記録長度(s文件全路径_id顧客数据一覧表, l開始地址1, 単位記録固定長度1);
		if (StringUtils.isEmpty(s顧客詞条IDs)) {
			return null;
		}

		for (int i = 0; i * 単位記録固定長度1 < s顧客詞条IDs.length(); i++) {
			String s顧客詞条ID数据ID = s顧客詞条IDs.substring(i * 単位記録固定長度1, (i + 1) * 単位記録固定長度1);

			数据DTO 数据dto = to数据DTO(s顧客詞条ID数据ID);

			数据DTOList.add(数据dto);
		}

		//		String s類型2 = "顧客数据id一覧表";
		//		String s文件全路径_顧客数据id一覧表 = o文件全路径.取得対象文件全路径_by類型and詞条IDand数据ID(s類型2, Arrays.asList(s詞条id, s数据id));
		//
		//		long l開始地址2 = 0;
		//		int 単位記録固定長度2 = o文件記録.取得単位記録固定長度_by類型(s類型2);
		//		long l文件SIZE2 = o文件記録.取得文件SIZE_by文件全路径(s文件全路径_顧客数据id一覧表);
		//
		//		String s顧客数据IDs = o文件記録.取得対象文件内容_by文件全路径and開始地址and単位記録長度(s文件全路径_顧客数据id一覧表, l開始地址2, l文件SIZE2);
		//		for(int i = 0; i*単位記録固定長度2 < s顧客数据IDs.length() ; i++){
		//			String s顧客数据ID =  s顧客詞条IDs.substring(i*単位記録固定長度2, (i+1)*単位記録固定長度2);
		//
		//			数据DTOList.get(i).set数据ID(s顧客数据ID);
		//		}
		return 数据DTOList;

	}

	/*
	 * 为取得本詞条的所有GUEST词条ID
	 *
	 *
	 *
	 */
	public List<String> 取得所有Guest詞条IDList_by本詞条名(String s本詞条名){
		/*
		 * 本詞条
		 * 	|-------[WAITER]
		 *  |			|-------(本詞条数据ID1)
		 *  |			|-------(本詞条数据ID2)
		 *  |			|-------(本詞条数据ID3)
		 *  |
		 *  |-------[GUEST]
		 * 				|-------[G詞条ID1]
		 * 				|			|-------(G詞条数据ID1)
		 * 				|			|-------(G詞条数据ID2)
		 * 				|
		 *				|-------[G詞条ID2]
		 *				|-------[G詞条ID3]
		 *
		 *[]:文件夹
		 *():文件按
		 */
//myLogger.printCallMessage(this.sCallPath,"顧客.取得所有Guest詞条IDList_by本詞条名(s本詞条名="+s本詞条名+")");

		詞条 o詞条 = new 詞条("取得所有Guest詞条IDList_by本詞条名");

		文件全路径 o文件全路径 = new 文件全路径();
		String s類型1 = PublicName.KEY_顧客路径;
		String s文件全路径_id顧客数据一覧表 =
				o文件全路径.取得対象文件全路径_by類型and詞条IDand数据ID(
						s類型1, Arrays.asList(o詞条.取得詞条ID_by詞条名(s本詞条名)));

		// 就是取得指定路径下的所有文件夹名称
		文件夹 o文件夹 = new 文件夹(sCallPath + "取得所有Guest詞条IDList_by本詞条名");

		return o文件夹.取得路径下所有的文件夹名称List(s文件全路径_id顧客数据一覧表);

	}
}
