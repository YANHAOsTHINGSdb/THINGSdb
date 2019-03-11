package stage3.things.relations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Stream;

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

public class 業者  extends 詞業顧三者親{
	String sCallPath = null;
	MyLogger myLogger = new MyLogger();

	public 業者(String sCallPath) {
		this.sCallPath = sCallPath;
	}

	public void 追加業者関係_by主体数据and業者数据(数据DTO 主体数据, 数据DTO 業者数据) {
//myLogger.printCallMessage(this.sCallPath,"業者.追加業者関係_by主体数据and業者数据()");

		追加_業者詞条id一覧表記録(主体数据, Arrays.asList(業者数据));

	}



	/**
	 *
	 * @param s詞条名
	 * @param s実体数据
	 * @return
	 */
	public List<数据DTO> 取得業者数据DTOList_by詞条名and実体数据(String s詞条名, String s実体数据){

//myLogger.printCallMessage(this.sCallPath,"業者.取得業者数据DTOList_by詞条名and実体数据(s詞条名="+s詞条名+",s実体数据="+s実体数据+")");

		詞条 o詞条 = new 詞条(sCallPath + "取得業者数据DTOList_by詞条名and実体数据");

		List<数据DTO> result数据DTOList = new ArrayList<数据DTO>();

		String s詞条id = o詞条.取得詞条ID_by詞条名(s詞条名);
		ID id = new ID(sCallPath + "取得業者数据DTOList_by詞条名and実体数据");
		List<String> s数据id_List = id.検索数据采番ID_by詞条IDand実体数据(s詞条id, s実体数据);

		for(String s数据id : s数据id_List) {

			List<数据DTO> 数据DTOList = 取得業者数据DTOList_by詞条IDand数据采番ID(s詞条id, s数据id);
			//result数据DTOList.addAll(数据DTOList);
			//List<Object> newList = new ArrayList<>();
			Stream.of(数据DTOList, result数据DTOList).forEach(result数据DTOList::addAll);
		}

		return result数据DTOList;

	}


	/**
	 *
	 * @param main目標詞条名
	 * @return
	 */
	public List<String> 取得業者詞条IDList_by目標詞条名(String main目標詞条名) {

//myLogger.printCallMessage(this.sCallPath,"業者.取得業者詞条IDList_by目標詞条名(main目標詞条名="+main目標詞条名+")");


		詞条 o詞条 = new 詞条(sCallPath + "取得結果List_by目標_数据采番IdList_目標List");
		String s詞条ID = o詞条.取得詞条ID_by詞条名(main目標詞条名);

		return 取得業者詞条IDList_by目標詞条ID(s詞条ID);
	}

	/**
	 *
	 * @param main目標詞条ID
	 * @return
	 */
	public List<String> 取得業者詞条IDList_by目標詞条ID(String main目標詞条ID) {
//myLogger.printCallMessage(this.sCallPath,"業者.取得業者詞条IDList_by目標詞条ID(main目標詞条ID="+main目標詞条ID+")");


		String s文件全路径 = 文件全路径.取得対象文件全路径_by類型and詞条ID(PublicName.KEY_詞条路径, Arrays.asList(main目標詞条ID));
		// List<String> 業者詞条IDList =  文件全路径.getDirectorys(s文件全路径+"\\WAITER");

		s文件全路径 += "\\WAITER";
		if(StringUtils.isEmpty(s文件全路径)) {
			return null;
		}

/*		// 取得该词条所有的采番ID
		List<String> 采番IDList = 文件全路径.getAllFile(s文件全路径+"\\WAITER", false);

		// 根据采番ID取得所有的業者数据DTOList
		List<数据DTO> 数据DTOList = new ArrayList();
		for(String 采番ID:采番IDList) {
			数据DTOList.addAll(取得業者数据DTOList_by詞条IDand数据采番ID(main目標詞条ID, 采番ID));
		}

		// 从中取得关联的詞条ID
		List<String> 業者詞条IDList = new ArrayList();
		for(数据DTO 数据dto : 数据DTOList) {
			業者詞条IDList.add(数据dto.get詞条ID());
		}
		return 業者詞条IDList;*/

		/**
		 *
		   取得文件夹下的所有文件
			|
			|--文件List
				|
				|--取得文件内容
				|
				|--将内容转成List
				|
				|--把List与另一个List相结合，顺便排除重复的ID
		 *
		 */
		文件記録 o文件記録 = new 文件記録(sCallPath + "取得業者詞条IDList_by目標詞条ID()");

		String s類型 = PublicName.KEY_業者詞条id一覧表;
		int 単位記録固定長度 = o文件記録.取得単位記録固定長度_by類型(s類型);

		文件夹 o文件夹 = new 文件夹(sCallPath + "取得所有Guest詞条IDList_by本詞条名");

		List<String> 文件List = o文件夹.取得路径下所有的文件名称List(s文件全路径);

		if(CollectionUtils.isEmpty(文件List)) {
			return null;
		}
		List<String> 業者詞条IDList = new ArrayList();
		for(String s文件FullPath : 文件List) {

			List<String> listA = o文件記録.取得全IDList_by文件全路径and単位記録長度(s文件FullPath, 単位記録固定長度+"");
			業者詞条IDList.addAll(listA);
			業者詞条IDList = new ArrayList<String>(new LinkedHashSet<>(業者詞条IDList));
		}

		return 業者詞条IDList;
	}

	/*	// 将所有为之服务的W詞条ID数据ID一起取出
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

	}*/
}
