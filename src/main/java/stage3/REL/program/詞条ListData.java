package stage3.REL.program;

import java.io.File;
import java.util.List;
import java.util.Map;

import stage3.REL.io.MyFileReader;
import stage3.REL.io.MyFileWriter;
import stage3.REL.io.file.FileReader_BufferedReader;
import stage3.REL.io.file.FileReader_MappedByteBuffer;
import stage3.REL.io.file.FileWriter_BufferedWriter;
import stage3.REL.io.file.FileWriter_MappedByteBuffer;

public class 詞条ListData {

	private String 程序保存路径;
	private String 程序保存路径fullpath;
	private String 根路径="/Users/haoyan/Desktop/things_db/files";

	public 詞条ListData(Map g詞条信息) {
		程序保存路径 = 取得程序保存路径by詞条信息(g詞条信息);
	}

	/**
	 *
	 * @param g詞条信息
	 * @param 程序SourceCode
	 */
	public void 保存数据DataList(Map g詞条信息, List 数据DataList){
		MyFileWriter myFileWriter;

		if(数据DataList.toArray().length > 10000000) {
			myFileWriter = new FileWriter_MappedByteBuffer();
		}else {
			myFileWriter = new FileWriter_BufferedWriter();
		}

		程序保存路径fullpath=取得ListData数据文件全路径(g詞条信息);
		myFileWriter.write(程序保存路径fullpath, 数据DataList);
	}

	private String 取得程序保存路径by詞条信息(Map g詞条信息) {
		// 首先以文件为单位的数据，可以被视为一个文件Server，专门用来处理文件流的存储
		// 这里，我也只是象征性的留下接口

		// 目前策略就是，{根路径}/{词条ID}/{数据ID}.ListData
		// 这里只提供到  {根路径}/{词条ID}
		return 根路径 + "/" + g詞条信息.get("词条ID");
	}


	/**
	 *
	 * @param s程序数据ID
	 * @return
	 */
	public List 取得数据DataList(Map g詞条信息){

		程序保存路径fullpath = 取得ListData数据文件全路径(g詞条信息);

		MyFileReader myFileReader;
		File file = new File(程序保存路径fullpath);
		if(file.length() > 10000000) {
			myFileReader = new FileReader_MappedByteBuffer();
		}else {
			myFileReader = new FileReader_BufferedReader();
		}

		return (List)myFileReader.read(程序保存路径fullpath);
	}

	public String 取得ListData数据文件全路径(Map g詞条信息) {

		return 取得程序保存路径by詞条信息(g詞条信息)+"/"+g詞条信息.get("数据ID")+".ListData";
	}
}
