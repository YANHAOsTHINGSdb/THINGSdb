package stage3.things.relations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import stage3.log.MyLogger;
import stage3.things.dto.DTO;
import stage3.things.dto.数据DTO;
import stage3.things.file.文件全路径;
import stage3.things.file.文件夹;
import stage3.things.file.文件記録;
import stage3.things.id.ID;
import stage3.things.id.詞条;

public class 業者  extends DTO{
	String sCallPath = null;
	MyLogger myLogger = new MyLogger();

	public 業者(String sCallPath) {
		this.sCallPath = sCallPath;
	}

	public void 追加業者関係_by主体数据and業者数据(数据DTO 主体数据, 数据DTO 業者数据) {
		myLogger.printCallMessage(this.sCallPath,"業者.追加業者関係_by主体数据and業者数据()");
		
		追加_業者詞条id一覧表記録(主体数据, Arrays.asList(業者数据));

	}

	private void 追加_業者詞条id一覧表記録(数据DTO 主体数据dto, List<数据DTO> 業者数据List) {
		myLogger.printCallMessage(this.sCallPath,"業者.追加_業者詞条id一覧表記録()");
		
		文件記録 o文件記録 = new 文件記録(sCallPath + "追加_業者詞条id一覧表記録");
		文件全路径 o文件全路径 = new 文件全路径();

		String s類型 = "業者詞条id一覧表";
		String s詞条id = 主体数据dto.get詞条ID();
		String s数据id = 主体数据dto.get数据ID();

		String s文件全路径_主体数据and顧客数据 = o文件全路径.取得対象文件全路径_by類型and詞条IDand数据ID(s類型, Arrays.asList(s詞条id, s数据id));

		//追加内容 = 顾客数据.数据ID + "," + 主体数据.词条ID
		String s追加内容= 数据DTOList出力(業者数据List);

		o文件記録.追加記録_by類型and追加内容and文件全路径(s類型,s追加内容,s文件全路径_主体数据and顧客数据);

	}

	private String 数据DTOList出力(List<数据DTO> 主体数据List) {
		
		myLogger.printCallMessage(this.sCallPath,"業者.数据DTOList出力()");
		
		String sList出力 = "";
		for(数据DTO 数据dto : 主体数据List){
			sList出力 += 数据dto.get詞条ID();
		}
		return sList出力;
	}

	public List<数据DTO> 取得業者数据DTOList_by詞条名and実体数据(String s詞条名, String s実体数据){

		myLogger.printCallMessage(this.sCallPath,"業者.取得業者数据DTOList_by詞条名and実体数据(s詞条名="+s詞条名+",s実体数据="+s実体数据+")");

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
		
		myLogger.printCallMessage(this.sCallPath,"業者.取得業者数据DTOList_by詞条IDand数据采番ID(s詞条id="+s詞条id+",s数据id="+s数据id+")");


		文件全路径 o文件全路径 = new 文件全路径();
		文件記録 o文件記録 = new 文件記録(sCallPath + "取得業者数据DTOList_by数据采番IDand詞条ID");
		List<数据DTO> 数据DTOList = new ArrayList<数据DTO>();

		String s類型 = "業者詞条id一覧表";
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

	public List<String> 取得業者詞条IDList_by目標詞条名(String main目標詞条名) {
		
		myLogger.printCallMessage(this.sCallPath,"業者.取得業者詞条IDList_by目標詞条名(main目標詞条名="+main目標詞条名+")");

		
		詞条 o詞条 = new 詞条(sCallPath + "取得結果List_by目標_数据采番IdList_目標List");
		String s詞条ID = o詞条.取得詞条ID_by詞条名(main目標詞条名);
		
		return 取得業者詞条IDList_by目標詞条ID(s詞条ID);
	}
	
	public List<String> 取得業者詞条IDList_by目標詞条ID(String main目標詞条ID) {
		myLogger.printCallMessage(this.sCallPath,"業者.取得業者詞条IDList_by目標詞条ID(main目標詞条ID="+main目標詞条ID+")");

		
		String s文件全路径 = 文件全路径.取得対象文件全路径_by類型and詞条ID("詞条路径", Arrays.asList(main目標詞条ID));		
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
		
		String s類型 = "業者詞条id一覧表";
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
}
