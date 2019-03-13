package stage3.things.relations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import stage3.consts.PublicName;
import stage3.log.MyLogger;
import stage3.things.dto.数据DTO;
import stage3.things.file.文件全路径;
import stage3.things.file.文件夹;
import stage3.things.file.文件記録;
import stage3.things.id.ID;
import stage3.things.id.詞条;

public class 顧客  extends 詞業顧三者親{

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
		//追加_id顧客数据一覧表記録(主体数据, Arrays.asList(顧客数据));
		追加_顧客id数据一覧表記録(Arrays.asList(主体数据), 顧客数据);
		追加_業者詞条id一覧表記録(主体数据, Arrays.asList(顧客数据));
	}

/*
|---id_GUEST
		|-----0000000001.data
				|-------000000001600000000010000000017000000000100000000160000000002000000001700000000020000000016000000000300000000170000000003
*/

	/**
	 * 就是把数据填到GUEST下。
	 * 每个文件夹代表一个词条ID
	 * 每个文件名为顾客数据ID
	 * 每个文件的内容为【主体数据LIST】
	 * @param 主体数据List
	 * @param 顧客数据dto
	 */
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
		String s追加内容 = super.数据DTOList出力(主体数据List);

		sCallPath = org_sCallPath;
		// FORLOG_END

		文件記録 o文件記録 = new 文件記録(sCallPath + "追加_顧客id数据一覧表記録");
		o文件記録.追加記録_by類型and追加内容and文件全路径(s類型, s追加内容, s文件全路径_顧客id数据一覧表);

	}

	/**
	 *
	 * @param s本詞条名
	 * @param s実体数据
	 * @return
	 */
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

			//List<数据DTO> 数据DTOlist = 取得W数据DTOList_by詞条IDand数据采番ID(s本詞条id, s本数据id);
			List<数据DTO> 数据DTOlist = 取得業者数据DTOList_by詞条IDand数据采番ID(s本詞条id, s本数据id);
			sCallPath = org_sCallPath;
			// FORLOG_END

			if (! CollectionUtils.isEmpty(数据DTOlist)) {
				//从第二条数据开始，每一条都是它的顾客信息
				result数据DTOlist.addAll(数据DTOlist);

			}
		}
		return result数据DTOlist;
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
