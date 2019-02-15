package stage3.things.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import stage3.log.MyLogger;

public class 文件夹 {
	String sCallPath = null;
	MyLogger myLogger = new MyLogger();

	public 文件夹(String sCallPath) {
		this.sCallPath = sCallPath;
	}

	public List<String>  取得路径下所有的文件夹名称List(String root_path){

		this.myLogger.printCallMessage(this.sCallPath,"文件夹.取得路径下所有的文件夹名称List(root_path="+root_path+")");

		List<String> s文件夹名List = new ArrayList();

	    File root_file = new File(root_path);
	    if (root_file.exists()) {
	        File[] files = root_file.listFiles();//当前文件夹下所有文件和文件夹名
	        if (files.length == 0) {
	            System.out.println("文件夹是空的!");
	            return null;
	        }
	        else {
	            for (File file : files) {
	                //判断是文件夹还是文件
	                if (file.isDirectory()) {
	                    System.out.println("文件夹:" + file.getAbsolutePath());
	                    //searchFiles(file.getAbsolutePath());

	                    //s文件夹名List.add(file.getAbsolutePath());
	                    if(NumberUtils.isDigits(file.getName())) {
	                    	s文件夹名List.add(file.getName());
	                    }

	                }
	                //找到jpg文件，或文件file.isFile()
	                else if (file.toString().endsWith("jpg")) {
	                    System.out.println("文件:" + file.getAbsolutePath());
	                }
	            }
	        }
	    }
	    else {
	        System.out.println("文件不存在!");
	        return null;
	    }

		return s文件夹名List;
	}

	/**
	 *
	 * @param root_path
	 * @return
	 */
	public List<String>  取得路径下所有的文件名称List(String root_path){
		myLogger.printCallMessage(this.sCallPath,"文件夹.取得路径下所有的文件夹名称List(root_path="+root_path+")");
		List<String> s文件名List = new ArrayList();

	    File root_file = new File(root_path);
	    if (root_file.exists()) {
	        File[] files = root_file.listFiles();//当前文件夹下所有文件和文件夹名
	        if (files.length == 0) {
	            System.out.println("文件夹是空的!");
	            return null;
	        }
	        else {
	            for (File file : files) {
	                //判断是文件夹还是文件
	                if (file.isDirectory()) {
	                    System.out.println("文件夹:" + file.getAbsolutePath());
	                    //searchFiles(file.getAbsolutePath());

	                }
	                //找到jpg文件，或文件file.isFile()
	                else {
	                    System.out.println("文件:" + file.getAbsolutePath());
	                    s文件名List.add(file.getAbsolutePath());
	                }
	            }
	        }
	    }
	    else {
	        System.out.println("文件不存在!");
	        return null;
	    }

		return s文件名List;
	}

	//非递归，遍历获取文件的一般方法
	public void searchFiles2(String root_path){
		myLogger.printCallMessage(this.sCallPath,"文件夹.searchFiles2(root_path="+root_path+")");

	    File root_file = new File(root_path);
	    File[] files = root_file.listFiles();
	    for(int i = 0;i < files.length;i++){
	        String each_path = root_path +"\\"+ files[i].getName();//getName()获取最后一级文件夹名
	        System.out.println(each_path);
	    }
	}

}
