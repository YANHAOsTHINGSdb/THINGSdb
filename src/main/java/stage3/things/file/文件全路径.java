package stage3.things.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class 文件全路径 {
	// private static final String KEY_路径分隔符 = null;
	//static String s数据路径= "F:\\things_db";
	static String s数据路径 = stage3.consts.PublicName.KEY_数据路径;
	static String KEY_路径分隔符 = stage3.consts.PublicName.KEY_路径分隔符;
	static String s采番ID文件 = stage3.consts.PublicName.KEY_采番ID文件;
	static String sWAITER = stage3.consts.PublicName.KEY_WAITER;
	static String s文件后缀data = stage3.consts.PublicName.KEY_文件后缀data;
	static String sGUEST = stage3.consts.PublicName.KEY_GUEST;
	static String s実体数据文件名 = stage3.consts.PublicName.KEY_実体数据文件名;
	static String s実体数据索引文件名 = stage3.consts.PublicName.KEY_実体数据索引文件名;
	static String s業者詞条id一覧表路径 = stage3.consts.PublicName.KEY_業者詞条id一覧表路径;

	public static String 取得対象文件全路径_by類型and詞条IDand数据ID(String s類型, List<String> 数据idList) {

		String s文件全路径 = null;
		switch(s類型){
		case "採番ID文件":
		case "采番ID文件":				//詞条ID（文件夹）
			s文件全路径 = s数据路径 + KEY_路径分隔符  + 数据idList.get(0) + KEY_路径分隔符 + s采番ID文件 ;
			break;
		case "id顧客数据一覧表":
			// id顧客 = WAITER
			// 本词条 的 具体 实体数据的id 对应有哪些顾客的哪些id（要求1个.实.体数据id有一个专门文件。该文件中，左面是顾客词条id，右面是顾客数据id）
			// 本词条ID    = 数据idList.get(0)
			// 本词条数据ID = 数据idList.get(1)
			s文件全路径 = s数据路径 +KEY_路径分隔符 + 数据idList.get(0) + KEY_路径分隔符 + sWAITER + KEY_路径分隔符 + 数据idList.get(1) + s文件后缀data ;
			break;

		case "顧客路径":
			s文件全路径 = s数据路径 +KEY_路径分隔符 + 数据idList.get(0) + KEY_路径分隔符 + sGUEST ;
			break;
		case "顧客id数据路径":
			// 什么顾客的什么id对应着本词条的哪些实体数据的id
			s文件全路径 = s数据路径 +KEY_路径分隔符 + 数据idList.get(0) + KEY_路径分隔符 + sGUEST + KEY_路径分隔符 + 数据idList.get(1) ;
			break;
		case "顧客id数据一覧表":
			// 什么顾客的什么id对应着本词条的哪些实体数据的id
			// 顧客id = GUEST
			// 本词条ID   = 数据idList.get(0)
			// 顾客词条ID = 数据idList.get(1)
			// 顾客数据ID = 数据idList.get(2)
			s文件全路径 = s数据路径 +KEY_路径分隔符 + 数据idList.get(0) + KEY_路径分隔符 + sGUEST + KEY_路径分隔符 + 数据idList.get(1) + KEY_路径分隔符 +数据idList.get(2) + s文件后缀data ;
			break;
		case "実体数据文件":
			s文件全路径 = s数据路径 + KEY_路径分隔符 + 数据idList.get(0) + KEY_路径分隔符 + s実体数据文件名 ;
			break;

		case "実体数据索引文件":
			s文件全路径 = s数据路径 + KEY_路径分隔符 + 数据idList.get(0) + KEY_路径分隔符 + s実体数据索引文件名 ;
			break;

		case "業者詞条id一覧表":
			s文件全路径 = s数据路径 + KEY_路径分隔符 + 数据idList.get(0) + s業者詞条id一覧表路径 + 数据idList.get(1) + s文件后缀data ;
			break;
		}

	return s文件全路径;

	}

    /**
     * 获取路径下的所有文件/文件夹
     * @param directoryPath 需要遍历的文件夹路径
     * @param isAddDirectory 是否将子文件夹的路径也添加到list集合中
     * @return
     */
    public static List<String> getAllFile(String directoryPath, boolean isAddDirectory) {
        List<String> list = new ArrayList<String>();
        File baseFile = new File(directoryPath);
        if (baseFile.isFile() || !baseFile.exists()) {
            return list;
        }
        File[] files = baseFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                if(isAddDirectory){
                    list.add(file.getAbsolutePath());
                }
                list.addAll(getAllFile(file.getAbsolutePath(),isAddDirectory));
            } else {
                list.add(file.getAbsolutePath());
            }
        }
        return list;
    }

    public static ArrayList<String> getDirectorys(String path) {
        ArrayList<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
//                  System.out.println("文     件：" + tempList[i]);
            	// WAITER 下只会出现文件
            	//        文件名为存在ID
            	//        文件内容会出现对应的词条ID
            	//
            }
            if (tempList[i].isDirectory()) {
//                  System.out.println("文件夹：" + tempList[i]);
            	// GUEST 下只会出现文件夹
            	//       文件名为对应的词条ID

            	files.add(tempList[i].toString());
            }
        }
        return files;
    }

    public static String 取得対象文件全路径_by類型and詞条ID(String s類型, List<String> 数据idList) {

		String s文件全路径 = null;
		switch(s類型){
		case "詞条路径":
			//詞条ID（文件夹）
			s文件全路径 = s数据路径 + KEY_路径分隔符 + 数据idList.get(0);
			break;

		}

	return s文件全路径;

    }


    public boolean is文件全路径真实有效(String s文件全路径) {
		File file = new File(s文件全路径);
		return file.exists();
	}
}
