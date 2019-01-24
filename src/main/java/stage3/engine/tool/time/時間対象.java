package stage3.engine.tool.time;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import stage3.log.MyLogger;

public class 時間対象 {
	String sCallPath = null;
	MyLogger myLogger = new MyLogger();

	public 時間対象(String sCallPath) {
		this.sCallPath = sCallPath;
	}
	public boolean is時間対象by単位(String s時間単位) {
		
		myLogger.printCallMessage(sCallPath,"時間対象.is時間対象by単位(s時間単位="+s時間単位+")");
		
		// 如果对象的项目名是【年月日】，或者是【XX开始】时、【终了时】之类的
		// 時間対象
		//     [--------[GUEST]
		//	  			   |--------年
		//				   |--------月
		//				   |--------日
		//				   |--------开始日
		
		
		// 这个只能用自定义程序取实现
		// 但是就目前而言
		// 还是先写死方便
		
		switch( s時間単位) {
		case "年":
		case "月":
		case "日":
		case "时":
		case "時":
		case "季":
		case "分":
		case "秒":
		case "周":
			return true;
		}
		return false;
	}

	public boolean is時間対象by値(String s時間值, String s時間単位) {
		
		myLogger.printCallMessage(sCallPath,"時間対象.is時間対象by値(s時間值="+s時間值+",s時間単位="+s時間単位+")");
		
		// 如果对象的値是【年月日】，或者是【XX开始】时、【终了时】之类的
		SimpleDateFormat format = null;
		switch( s時間単位) {
		case "年":
			// 例 2018 2018年
			format = new SimpleDateFormat("yyyy");
			break;
		case "月":
			// 例 201812 2018年12月
			format = new SimpleDateFormat("yyyyMM");
			break;
		case "日":
			// 例 20181231 2018年12月31日
			format = new SimpleDateFormat("yyyyMMdd");
			break;
		case "时":			
		case "時":
			// 例 2018123109 2018年12月31日9点
			format = new SimpleDateFormat("yyyyMMddHH");
			break;
		case "季":
			// 例 201802 2018年第2季
			format = new SimpleDateFormat("yyyyMM");
			break;
		case "分":
			// 例 201812310914 2018年12月31日9点14分
			format = new SimpleDateFormat("yyyyMMddHHmm");
			break;
		case "秒":
			// 例 20181231091458 2018年12月31日9点14分58秒
			format = new SimpleDateFormat("yyyyMMddHHmmss");
			break;
		case "周":
			// 例 20180401 2018年4月第4周
			format = new SimpleDateFormat("yyyyMMdd");
			break;
		}
		
		try {
			format.parse(s時間值);
			return true;
		}catch(Exception e) {
			
		}
		
		return false;
	}

	// 分解时间词条
	// 時間Map
	//    |------key=时间单位 ,value=年
	//	  |------key=时间值 ,value=2018
	public List<Map> 分解時間詞条_by主時間Map(Map<String, String> 主時間Map) {
		
		myLogger.printCallMessage(sCallPath,"時間対象.分解時間詞条_by主時間Map()");

		
		// 主時間Map
		// key=项目 value=年
		// key=值    value=2018
		
		// key=项目 value=月
		// key=值    value=201801
		
		// key=项目 value=开始日
		// key=值    value=2018/10/10
		
		下一層時間 o下一層時間 = new 下一層時間();
		
		// 转成 下一级的子信息
		// 取得下一级时间阶层
		// 時間Map
		//    |------key=时间单位 ,value=月
		//	  |------key=时间值 ,value=201801
		//		。。。。·
		//    |------key=时间单位 ,value=月
		//	  |------key=时间值 ,value=201812
		
		// 根据 主時間
		return o下一層時間.取得下一層時間IDList(主時間Map);
	}
	
}
