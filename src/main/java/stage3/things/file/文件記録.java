package stage3.things.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import net.oschina.j2cache.CacheObject;
import net.oschina.j2cache.NullObject;
import stage3.cache.CacheForThingsDB;
import stage3.log.MyLogger;
import sun.misc.Cleaner;

public class 文件記録 {
	static String sCallPath = null;
	static MyLogger myLogger = new MyLogger();

	public 文件記録(String sCallPath) {
		this.sCallPath = sCallPath;
	}

//	private static Logger LOGGER = null;
//	  static {
//	      Logger mainLogger = Logger.getLogger("com.logicbig");
//	      mainLogger.setUseParentHandlers(false);
//	      ConsoleHandler handler = new ConsoleHandler();
//	      handler.setFormatter(new SimpleFormatter() {
//	          private static final String format = "[%1$tF %1$tT] [%2$-7s] %3$s %n";
//
//	          @Override
//	          public synchronized String format(LogRecord lr) {
//	              return String.format(format,
//	                      new Date(lr.getMillis()),
//	                      lr.getLevel().getLocalizedName(),
//	                      lr.getMessage()
//	              );
//	          }
//	      });
//	      mainLogger.addHandler(handler);
//	      LOGGER = Logger.getLogger(文件記録.class.getName());
//	  }


	public static int 取得単位記録固定長度_by類型(String s類型) {

		myLogger.printCallMessage(sCallPath,"文件記録.取得単位記録固定長度_by類型( 類型="+ s類型 +")");
		/**
		 * 取得单条記録固定长度_by類型
			也许只有以下几种：
		取得单条記録固定长度_by類型
		也许只有以下几种：
		1、采番id		采番id（10）
		2、索引内容		该数据id的实体数据的开始地址（10）				id就是地址
		3、客户词条id		该数据ID的客户词条id（10）				该数据的采番id就是地址
		4、客户数据id		该数据id的客户数据id（10）				该数据采番id就是地址，不用特别记录，每条记录都会有客户？（）
		5、客户id数据	该词条的业者数据id（10）					该客户在该词条所有的业者数据idList
		6、业者词条id		该词条的业者词条id（10）					该词条所需要的业者词条idList

		 */
		switch(s類型){
		case "採番ID文件":
		case "采番ID文件":
			//用采番id単位記録固定長度格式化输出ID，前端不足补零
			return 10;

		case "実体数据索引文件":		//用索引记录固定长度来格式化输出
			return 20;					//開始地址終了地址

		case "索引地址":				//用索引记录固定长度来格式化输出
			return 10;					//開始地址終了地址
//		|---id_GUEST
//			|-----0000000018.data
//					|---------000000001600000000010000000017000000000100000000160000000002000000001700000000020000000016000000000300000000170000000003

		case "id顧客数据一覧表":		//id顧客数据一覧表
			return 20;          		//左面是顾客词条id，右边是顾客数据id

//		case "顧客詞条id":				//用索引记录固定长度来格式化输出
//			return 10;
//
//		case "顧客数据id":				//用索引记录固定长度来格式化输出
//			return 10;
//			0000000018
//				|----GUEST
//				|		|-----0000000016
//				|				|-----0000000001.data
//				|			          	|---00000000180000000001

		case "顧客id数据一覧表":		//用索引记录固定长度来格式化输出
			return 20;

		case "業者詞条id一覧表":		//用索引记录固定长度来格式化输出
			return 10;

		}
		return 0;
	}

	/**
	 *
	 * @param s対象文件全路径
	 * @return
	 */
	public static Long 取得文件SIZE_by文件全路径(String s対象文件全路径) {

		myLogger.printCallMessage(sCallPath,"文件記録.取得文件SIZE_by文件全路径( 対象文件全路径="+ s対象文件全路径+")" );

		// 上网去查、或者找一找以前的代码
		// バイト数が返されます
		// 戻り値はlong型で、ファイルの種類はアスキーでもバイナリーでも構いません。

		if(StringUtils.isEmpty(s対象文件全路径)){
			try {
				throw new Exception();
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				return 0L;
			}
		}
        //文件不存在的话还是先退出吧
        if (!existsFile(s対象文件全路径)) {
        	createFile(s対象文件全路径);
        	return 0L;
        }

		File file = new File(s対象文件全路径);

		myLogger.printCallMessage(sCallPath+"取得文件SIZE_by文件全路径" ,"file.Length = "+ (file != null ? file.length():0));

		return file.length();

	}


	/**
	 *
	 * @param s類型
	 * @param s追加内容
	 * @param s文件全路径
	 */
	public void 追加記録_by類型and追加内容and文件全路径(String s類型, String s追加内容, String s文件全路径) {

		myLogger.printCallMessage(sCallPath,"文件記録.追加記録_by類型and追加内容and文件全路径( 文件全路径="+ s文件全路径 +", 類型="+ s類型+", 追加内容="+ s追加内容+")");

		String org_sCallPath = new String(sCallPath);
		sCallPath += "追加記録_by類型and追加内容and文件全路径";

		写入文件_by写入内容and文件全路径(s追加内容, s文件全路径);

		sCallPath = org_sCallPath;
	}

	/**
	 *
	 * @param s書込内容
	 * @param 文件全路径
	 */
	public static void 写入文件_by写入内容and文件全路径(String s写入内容, String 文件全路径) {

		myLogger.printCallMessage(sCallPath,"文件記録.写入文件_by写入内容and文件全路径( 文件全路径="+ 文件全路径 +", 写入内容="+ s写入内容+")");

		// 可能是数据文件
		// 也可能是索引文件

        //文件不存在的话还是先退出吧
        if (!existsFile(文件全路径)) {
        	createFile(文件全路径);
        }

		String org_sCallPath = new String(sCallPath);
		sCallPath += "写入文件_by写入内容and文件全路径";

        long i開始地址 = 取得文件SIZE_by文件全路径(文件全路径);

        sCallPath = org_sCallPath;


        FileChannel fileChannel = null;
        try {
            RandomAccessFile randomAccessFile
                    = new RandomAccessFile(文件全路径, "rw");
            fileChannel = randomAccessFile.getChannel();
            MappedByteBuffer mappedByteBuffer
                    = fileChannel.map(MapMode.READ_WRITE, i開始地址, s写入内容.getBytes().length);
            Channels.newChannel(System.out).write(mappedByteBuffer);
            mappedByteBuffer.clear();
            mappedByteBuffer.put(s写入内容.getBytes());
            mappedByteBuffer.force();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileChannel.close();
            } catch (Exception e) {
            }
        }
	}

    /**
     * ファイル存在チェック
     *
     * @param _path ファイルパス.
     * @return true:存在する
     */
    public static final Boolean existsFile(String _path) {

        // 親フォルダ取得
        File file_ = new File(_path);

        // ファイル存在チェック
        return file_.exists() && file_.isFile();

    }

    public static boolean createFile(String 文件全路径){


		Boolean bool = false;

		File file = new File(文件全路径);
		try {

			file.createNewFile();

		} catch (IOException e) {
			File fileParent = file.getParentFile();
			if (!fileParent.exists()) {
				fileParent.mkdirs();
			}
			createFile(文件全路径);
		}

		return bool;
    }

	private void 追加索引記録_by類型and文件全路径and追加内容(String s類型, String s文件全路径, String s追加内容) {


		myLogger.printCallMessage(sCallPath,"文件記録.取得索引開始地址_by類型and数据ID( 文件全路径="+ s文件全路径 +", 類型="+ s類型+", 追加内容="+ s追加内容+")");

		String s索引内容 = null;
		String s文件全路径_索引文件 = null;

		switch (s類型) {

//		case "采番ID文件_索引文件":
//			s索引内容 = "";
//			s文件全路径_索引文件 = "";
//			break;
//		case "顧客词条id一覧表_索引文件":
//			s索引内容 = "";
//			s文件全路径_索引文件 = "";
//			break;
//		case "顧客数据id一覧表_索引文件":
//			s索引内容 = "";
//			s文件全路径_索引文件 = "";
//			break;
//		case "顧客id数据一覧表_索引文件":
//			s索引内容 = "";
//			s文件全路径_索引文件 = "";
//			break;
//		case "業者词条id一覧表_索引文件":
//			s索引内容 = "";
//			s文件全路径_索引文件 = "";
//			break;
		case "実体数据索引文件":
		case "実体数据文件":

			//s索引内容 =【开始地址】【终止地址】;
			//String s文件開始地址 = 取得文件開始地址_by文件全路径(s文件全路径);
			Long l文件SIZE = 取得文件SIZE_by文件全路径(s文件全路径);
			Long l开始地址 = l文件SIZE ;
			Long l終了地址 = l开始地址 + s追加内容.length();
			s索引内容 = l开始地址 + "" + l終了地址;
			s文件全路径_索引文件 = s文件全路径;
			break;

		}
		String org_sCallPath = new String(sCallPath);
		sCallPath += "追加索引記録_by類型and文件全路径and追加内容";

		写入文件_by写入内容and文件全路径(s索引内容,s文件全路径_索引文件);

		sCallPath = org_sCallPath;
	}


	public String 取得索引開始地址_by類型and数据ID(String s類型, String s数据采番id) {


		myLogger.printCallMessage(sCallPath,"文件記録.取得索引開始地址_by類型and数据ID( 数据ID="+ s数据采番id +", 類型="+ s類型+")");

		// 取得詞条ID_by詞条名
		// 取得業者数据DTOList_by数据采番IDand詞条ID
		String org_sCallPath = new String(sCallPath);
		sCallPath += "取得索引開始地址_by類型and数据ID";

		//"業者詞条id一覧表"的时候，整个文件的记录都是这条数据的地址才对。
		switch(s類型){
		case "業者詞条id一覧表":
			return "0";

		}
		int i単位記録固定長度 = 取得単位記録固定長度_by類型(s類型);

		sCallPath = org_sCallPath;

		return ((Long.parseLong(s数据采番id)-1) * i単位記録固定長度) + "";
	}

	public String 取得実体数据開始地址_by類型ands詞条ID(String s類型, String s詞条id) {

		myLogger.printCallMessage(sCallPath,"文件記録.取得実体数据開始地址_by類型and数据ID( 数据ID="+ s詞条id +", 類型="+ s類型+")");

		文件全路径 o文件全路径 = new 文件全路径();
		文件記録 o文件記録 = new 文件記録(sCallPath + "取得実体数据開始地址_by類型and数据ID");
		String s対象文件全路径 = o文件全路径.取得対象文件全路径_by類型and詞条IDand数据ID(s類型, Arrays.asList(s詞条id));

		String org_sCallPath = new String(sCallPath);
		sCallPath += "取得実体数据開始地址_by類型ands詞条ID";

		String sResult = o文件記録.取得文件SIZE_by文件全路径(s対象文件全路径)+"";

		sCallPath = org_sCallPath;

		return sResult;
	}

	public String 做成文件記録_by類型and記録内容(String s類型, String s追加内容) {


		myLogger.printCallMessage(sCallPath,"文件記録.做成文件記録_by類型and記録内容( 追加内容="+ s追加内容 +", 類型="+ s類型+")");
		/**
			例如，根据类型可以知道每条记录的固定长度。
			只适用：
				采番文件：记录了数据采番ID
				索引文件：记录了实体数据的开始地址和数据长度
				顧客詞条id：记录了该数据对应的客户词条id
				顧客数据id：记录了该数据对应的客户数据id
				顧客id数据：记录了该客户对应的数据idList
				業者詞条id：记录了该数据对应的业者词条idList（具体业者数据id，需要去业者词条中去找）


			不适用：	实体数据文件。
				理由是：实体数据不可能是固定长度。
					需要实事求是的追加记录。
		 */
		String org_sCallPath = new String(sCallPath);
		sCallPath += "做成文件記録_by類型and記録内容";

		int i単位記録固定長度  = 取得単位記録固定長度_by類型(s類型);

		sCallPath = org_sCallPath;

		switch(s類型){
		case "実体数据索引文件":		//用索引记录固定长度来格式化输出
	        return s追加内容;

		case "索引地址":		//用索引记录固定长度来格式化输出
		case "采番ID文件":

			String ss追加内容 = null;
			if(NumberUtils.isDigits(s追加内容)){

				org_sCallPath = new String(sCallPath);
				sCallPath += "做成文件記録_by類型and記録内容";

				ss追加内容 = 做成文件記録_by追加内容and単位記録固定長度(s追加内容, i単位記録固定長度);

				sCallPath = org_sCallPath;

			}else if(StringUtils.isEmpty(s追加内容)){
				String ss追加内容_blank = " ";
				//如果没事实体数据，而采番的话，索引需要占空位。
				for(int i=0;i<i単位記録固定長度;i++){
					ss追加内容_blank += " ";
				}
				ss追加内容 = ss追加内容_blank.substring(0, i単位記録固定長度);
			}

	        return ss追加内容;


//		case "顧客詞条id":		//用索引记录固定长度来格式化输出
//		case "顧客数据id":		//用索引记录固定长度来格式化输出
		case "顧客id数据一覧表":
		case "業者詞条id一覧表":

		}
		return s追加内容;
	}


	private String 做成文件記録_by追加内容and単位記録固定長度(String s追加内容, int i単位記録固定長度) {
		myLogger.printCallMessage(sCallPath,"文件記録.做成文件記録_by追加内容and単位記録固定長度( 追加内容="+ s追加内容 +", 単位記録固定長度="+ i単位記録固定長度+")");

		//用采番id単位記録固定長度来格式化输出ID，前端不足补零
        //得到一个NumberFormat的实例
        NumberFormat nf = NumberFormat.getInstance();
        //设置是否使用分组
        nf.setGroupingUsed(false);
        //设置最大整数位数
        nf.setMaximumIntegerDigits(i単位記録固定長度);
        //设置最小整数位数
        nf.setMinimumIntegerDigits(i単位記録固定長度);
        //输出测试语句
        //System.out.println(nf.format(Long.parseLong(s追加内容)));
		//return String.format(String.format("%"+i単位記録固定長度+"d", s追加内容));
        return nf.format(Long.parseLong(s追加内容));
	}

	public long 取得開始地址_by索引内容(String s索引内容)  {

		myLogger.printCallMessage(sCallPath,"文件記録.取得開始地址_by索引内容( 索引内容="+ s索引内容+")");
		/*
		索引内容 = "[开始地址][終了地址]"

		开始地址 = 逗号之前的信息
		*/
		if(s索引内容 == null){
			try {
				throw new Exception();
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		return Long.parseLong(s索引内容.substring(0, 取得単位記録固定長度_by類型("索引地址")).trim());
	}

	public long 取得終了地址_by索引内容(String s索引内容){


		myLogger.printCallMessage(sCallPath,"文件記録.取得終了地址_by索引内容( 索引内容="+ s索引内容+")");

		/*
		索引内容 = "[开始地址][終了地址]"

		开始地址 = 逗号之后的信息
		*/
		if(s索引内容 == null){
			try {
				throw new Exception();
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		return Long.parseLong(s索引内容.substring(取得単位記録固定長度_by類型("索引地址"), s索引内容.length()));
	}

	/**
	 *
	 * @param s対象文件全路径
	 * @param l開始地址
	 * @param i単位記録長度
	 * @return
	 */
	public String 取得対象文件内容_by文件全路径and開始地址and単位記録長度(String s対象文件全路径, long l開始地址, long i単位記録長度) {

		// 缓存机制
		String s函数方法名 = "文件记录.取得対象文件内容_by文件全路径and開始地址and単位記録長度"; // 用来统一函数名，避免出错
		try {
			CacheObject o结果 = (CacheObject) CacheForThingsDB.取得Cache的Value_by函数名_param(s函数方法名,
					new String[] {s対象文件全路径,l開始地址+"",i単位記録長度+""});
			if (o结果 == null || o结果.getValue() == null || o结果.getValue() instanceof NullObject) {
			}else {
				return (String) o结果.getValue();
			}
		}catch(Throwable e) {
			System.out.println(e.getMessage());
		}

		myLogger.printCallMessage(sCallPath,"文件記録.取得対象文件内容_by文件全路径and開始地址and単位記録長度( 対象文件全路径="+ s対象文件全路径+", 開始地址="+ l開始地址+", 単位記録長度="+ i単位記録長度+")");

        // 文件不存在的话还是先退出吧
        if (!existsFile(s対象文件全路径)) {
            return null;
        }

        // 如果i単位記録長度是【0】还是先退出吧
        if (i単位記録長度 ==0L) {
            return null;
        }
        File file = new File(s対象文件全路径);
        long len = file.length();
        byte[] ds = new byte[(int) i単位記録長度];
        String sResult = null;

        //补丁20180803
        if(l開始地址 < 0){
        	l開始地址 = 0;
        }

        if(l開始地址 >= len){
        	return sResult;
        }

        RandomAccessFile randomAccessFile = null;
        FileChannel chan = null;
        try {
//            MappedByteBuffer mappedByteBuffer = new RandomAccessFile(file, "r")
//                    .getChannel().map(FileChannel.MapMode.READ_ONLY, 0,
//                    		len);// 读取大文件

        	// 创建从中读取和向其中写入（可选）的随机访问文件流，
        	// 该文件由 File 参数指定。将创建一个新的 FileDescriptor 对象来表示此文件的连接。
        	randomAccessFile = new RandomAccessFile(file, "r");
        	chan = randomAccessFile.getChannel();
        	// 映射的字节缓冲区是通过 FileChannel.map 方法创建的。
        	// 此类用特定于内存映射文件区域的操作扩展 ByteBuffer 类。
            MappedByteBuffer mappedByteBuffer = chan.map(FileChannel.MapMode.READ_ONLY, 0, len);

            int i=0;
            for (long offset = l開始地址; offset < l開始地址 + i単位記録長度 && offset<len; offset++,i++){
            	byte b = mappedByteBuffer.get((int)offset);
            	ds[i] = b;
            }
            sResult = new String(ds);

            // 回收缓冲区
            // 每次都会回收缓冲区，虽然更加安全了，但也大大降低了速度。
            // 需要一个回收的方案
            Cleaner cleaner = ((sun.nio.ch.DirectBuffer) mappedByteBuffer).cleaner();
            if(cleaner != null) {
            	cleaner.clean();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	try {
				if(chan != null) {chan.close();}
				if(randomAccessFile != null) {randomAccessFile.close();}
//				System.out.println("File Closed");
			} catch (IOException e) {
				System.out.println("我擦，尝试关闭文件时，出了点问题！");
				e.printStackTrace();
			}

        }
		// 缓存机制
		CacheForThingsDB.设置Cache的Value_by函数名_param(sResult,
				s函数方法名, s対象文件全路径, l開始地址+"", i単位記録長度+"");
        return sResult;
	}

	public List<String> 取得全IDList_by文件全路径and単位記録長度(String s対象文件全路径, String s単位記録長度){

		List<String> s全IDList = new ArrayList();
		Long fileSize = 取得文件SIZE_by文件全路径(s対象文件全路径);
		//long l開始地址 = 0L;
		for(long l開始地址=0L; l開始地址 <= fileSize; l開始地址=l開始地址 + Long.parseLong(s単位記録長度)) {
			String sID = 取得対象文件内容_by文件全路径and開始地址and単位記録長度(
					s対象文件全路径,l開始地址,Long.parseLong(s単位記録長度));
			if(! StringUtils.isEmpty(sID)) {
				s全IDList.add(sID);
			}
		}
		return s全IDList;

	}
}
