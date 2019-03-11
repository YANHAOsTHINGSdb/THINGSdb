package stage3.things.relations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.CollectionUtils;

import stage3.consts.PublicName;
import stage3.things.dto.数据DTO;
import stage3.things.file.文件全路径;
import stage3.things.file.文件記録;
import stage3.things.id.詞条;

public class 詞業顧三者親 {
	String sCallPath = null;
	public 数据DTO to数据DTO(String s詞条ID_数据采番id) {

		文件記録 o文件記録 = new 文件記録("s詞条ID_数据采番id");
		int i単位記録固定長度 = o文件記録.取得単位記録固定長度_by類型(PublicName.KEY_采番ID文件_);

		//数据DTO 数据dto = new 数据DTO(s顧客詞条ID数据ID.substring(0, 10), s顧客詞条ID数据ID.substring(10, 単位記録固定長度1));

		数据DTO 数据dto = new 数据DTO(
				s詞条ID_数据采番id.substring(0, i単位記録固定長度),
				s詞条ID_数据采番id.substring(i単位記録固定長度, s詞条ID_数据采番id.length())
				);
		return 数据dto;
	}

	/**
	 *
	 * @param s詞条id
	 * @param s数据id
	 * @return
	 */
	public List<数据DTO> 取得業者数据DTOList_by詞条IDand数据采番ID(String s詞条id, String s数据id){
		//目的/意味：就是针对这个词条的这条数据，找出有哪些相关的数据
		//具体操作：1、先找到【業者詞条ID一覧文件的物理地址】，当然是根据词条ID，数据ID
		//				因為業者詞条ID一覧的文件名是每条数据的采番ID
		//			2、再把该文件中的所有值都取出。
		//				当然就算是业者一览，也是按规矩来存储的
		//				其内容也是按照ID的単位記録固定長度来存储的

//myLogger.printCallMessage(this.sCallPath,"業者.取得業者数据DTOList_by詞条IDand数据采番ID(s詞条id="+s詞条id+",s数据id="+s数据id+")");


		文件全路径 o文件全路径 = new 文件全路径();
		文件記録 o文件記録 = new 文件記録(sCallPath + "取得業者数据DTOList_by数据采番IDand詞条ID");
		List<数据DTO> 数据DTOList = new ArrayList<数据DTO>();

		String s類型 = PublicName.KEY_業者詞条id一覧表;
		String s文件全路径_業者詞条ID一覧 =
				o文件全路径.取得対象文件全路径_by類型and詞条IDand数据ID(s類型, Arrays.asList(s詞条id, s数据id));

		//"業者詞条id一覧表"的时候，整个文件的记录都是这条数据的地址才对。
		String s開始地址 = o文件記録.取得索引開始地址_by類型and数据ID(s類型, s数据id);

		//int 詞条 = o文件記録.取得単位記録固定長度_by類型(s類型);
		long l文件SIZE = o文件記録.取得文件SIZE_by文件全路径(s文件全路径_業者詞条ID一覧);
		if(l文件SIZE <= 0) {
			return new ArrayList<数据DTO>();
		}
		String s本詞条IDs = o文件記録.取得対象文件内容_by文件全路径and開始地址and単位記録長度(s文件全路径_業者詞条ID一覧, Long.parseLong(s開始地址), l文件SIZE);
		int 単位記録固定長度 = o文件記録.取得単位記録固定長度_by類型(s類型);

		詞条 o詞条 = new 詞条(sCallPath + "取得業者数据DTOList_by詞条IDand数据采番ID()");
		for(int i = 0; i*単位記録固定長度 < s本詞条IDs.trim().length() ; i++){
			String s本詞条ID =  s本詞条IDs.substring(i*単位記録固定長度, (i+1)*単位記録固定長度);
			List<String> s業者数据IDList = o詞条.検索業者数据採番idList_by本詞条IDand顧客詞条IDand顧客数据ID(s本詞条ID, s詞条id, s数据id);

			if(CollectionUtils.isEmpty(s業者数据IDList)) {
				数据DTO 数据dto = new 数据DTO(s本詞条ID, null);
				数据DTOList.add(数据dto);
				continue;
			}
			for(String 数据ID : s業者数据IDList) {
				数据DTO 数据dto = new 数据DTO(s本詞条ID, 数据ID);
				数据DTOList.add(数据dto);
			}

		}
		return 数据DTOList;
	}


	/**
	 *
	 * @param 主体数据dto
	 * @param 業者数据List
	 */
	protected void 追加_業者詞条id一覧表記録(数据DTO 主体数据dto, List<数据DTO> 業者数据List) {
//myLogger.printCallMessage(this.sCallPath,"業者.追加_業者詞条id一覧表記録()");

		文件記録 o文件記録 = new 文件記録(sCallPath + "追加_業者詞条id一覧表記録");
		文件全路径 o文件全路径 = new 文件全路径();

		String s類型 = PublicName.KEY_業者詞条id一覧表;
		String s詞条id = 主体数据dto.get詞条ID();
		String s数据id = 主体数据dto.get数据ID();

		String s文件全路径_主体数据and顧客数据 = o文件全路径.取得対象文件全路径_by類型and詞条IDand数据ID(s類型, Arrays.asList(s詞条id, s数据id));

		//追加内容 = 顾客数据.数据ID + "," + 主体数据.词条ID
		String s追加内容= 数据DTOList出力(業者数据List);

		o文件記録.追加記録_by類型and追加内容and文件全路径(s類型,s追加内容, s文件全路径_主体数据and顧客数据);

	}
	/*	private void 追加_id顧客数据一覧表記録(数据DTO 主体数据dto, List<数据DTO> 顧客数据List) {
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
	}*/
	/**
	 *
	 * @param 主体数据List
	 * @return
	 */
/*	private String 数据DTOList出力(List<数据DTO> 主体数据List) {

//myLogger.printCallMessage(this.sCallPath,"業者.数据DTOList出力()");

		String sList出力 = "";
		for(数据DTO 数据dto : 主体数据List){
			sList出力 += 数据dto.get詞条ID();
		}
		return sList出力;
	}*/

	/**
	 * 取出主体数据List中的数据ID，存入[G词条ID]文件
	 * @param 主体数据List
	 * @return
	 */
	protected String 数据DTOList出力(List<数据DTO> 主体数据List) {
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
}
