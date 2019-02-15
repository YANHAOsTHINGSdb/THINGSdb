package stage3.cache;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.J2Cache;

public class CacheForThingsDB {
	/**
		项目日记 2019/1/12 【J2cache】

		首先。你必须要理解：
		1、redis是一个数据库
		2、J2cache只是在调用这个数据库

		好吧
		J2cache是帮你缓存了。

		但、具体缓存方案你还得自己想办法！
		       比如、1⃣️如何将缓存和你的数据库绑定
		            2⃣️如何定制缓存的读取、写入机制。
		            3⃣️如何在缓存中区别不同目的的查询结果

		1⃣️答
		只针对几个重要的class做缓存处理
		      词条class
		      IDclass
		      顾客class
		      业者class

		2⃣️答
		外部接口不变。
		该怎么调还怎么调
		只是内部，针对每个函数追加缓存接口：
		 即、先看看有没有值、
		         如果有、就返回
		         如果没有、就让它走正常流程、
		                 正常流程之后、
		                 再把结果存入到缓存即可。
		                  (null的结果值除外)

		3⃣️答
		key=函数名+参数实体
		value=函数处理的结果值

		  ——————具体实例—————
		业务函数名(){
		   //———取得缓存———
		    // key=函数名+Param
		    结果 = 【CacheForThingsDB.取得CACHE(key)】；
		    if（结果 ！= null）｛
		          return 结果；
		    ｝

		    。。。原来的处理。。。

		    //———-设置缓存———
		    // value=原来的return值
		   【CacheForThingsDB.设置CACHE（key，value）】；
		}

		=====其实可以扩展缓存=====

		就是可以将所有处理都缓存了！
		所有函数都CACHE化。
	 */

	/**
	 * ====在调用方的应用方案====
	 * 先通过key，
	 * 在缓存中取得对应的值
	 *
	 * 如果取得的对应值为null
	 * 还需要继续调用逻辑
	 *
	 * 如果取得的对应值不为null
	 * 就直接利用取到的值好了
	 *
	 * =======================
	 */

	static CacheChannel cache = J2Cache.getChannel();
	static Map<String, Object> cacheMap = new LinkedHashMap<String, Object>();
	static LinkedList<String> linkedList_key = new LinkedList<String>();
	static Map<String, Integer> cacheMap_counter = new LinkedHashMap<String, Integer>();
	/**
	 * 例
	 *    key   = 词条.取得词条ID_by词条名,股票实时数据
	 *
	 * @param key
	 * @return
	 */
	private static Object 取得CACHE的Value_byKey(String key) {

//		CacheObject o结果 = cache.get("default", key);
//		if(o结果 == null || o结果.getValue() == null || o结果.getValue() instanceof NullObject) {
//			cache.get("default", key).setValue(cacheMap.get(key));
//		}
//		return cache.get("default", key);
		cacheMap_counter.put(key, 1);
		return cacheMap.get(key);
	}

	/**
	 * 例
	 *    key   = 词条.取得词条ID_by词条名,股票实时数据
	 *    value = 0000000011
	 *
	 * @param key
	 * @param value
	 */
	private static void 设置CACHE(String key, Object value) {

//		cache.set("default", key, value);
		if(cacheMap_counter.size() > 1024L * 1024L * 1024L) {
			clearCache();
		}
		cacheMap.put(key, value);
		linkedList_key.add(key);
		cacheMap_counter.put(key, 0);
	}

	/**
	 * 例，
	 *    s函数名 = 词条.取得词条ID_by词条名
	 *    params = 股票实时数据
	 *
	 * @param s函数名
	 * @param params
	 * @return
	 */
	private static String 取得Cache的Key_by函数名_param(String s函数名, String... params) {
		String sKey = "";
		sKey += s函数名;
		if(ArrayUtils.isEmpty(params)) {
			return sKey;
		}

		for(String param : params) {
			sKey += ",";
			sKey += param;
		}

		return sKey;
	}

	/**
	 * 例，
	 *    s函数名 = 词条.取得词条ID_by词条名
	 *    params = 股票实时数据
	 * @param s函数名
	 * @param params
	 * @return
	 */
	public  static Object 取得Cache的Value_by函数名_param(String s函数名, String... params) {
		String sKey =取得Cache的Key_by函数名_param(s函数名, params);
		return 取得CACHE的Value_byKey(sKey);
	}

	/**
	 * 例，value = 0000000011
	 *    s函数名 = 词条.取得词条ID_by词条名
	 *    params = 股票实时数据
	 *
	 * @param value
	 * @param s函数名
	 * @param params
	 */
	public static void 设置Cache的Value_by函数名_param(Object value, String s函数名, String... params) {
		// 如果是 NULL 返回 TRUE
		if(checkObjectIsNull(value)) {
			return;
		}
		String sKey =取得Cache的Key_by函数名_param(s函数名, params);
		设置CACHE(sKey, value);
	}

	/**
	 * 如果是 NULL 返回 TRUE
	 * 如果不是NULL、返回 FALSE
	 * @param value
	 * @return
	 */
	private static boolean checkObjectIsNull(Object value) {
		if( null == value) {
			return true;
		}
		if(value instanceof Collection) {
			if(CollectionUtils.isEmpty((Collection)value)) {
				return true;
			}
		}
		if(value instanceof String) {
			if(StringUtils.isEmpty(value)) {
				return true;
			}
		}
		if(value instanceof Map) {
			if(((Map)value).isEmpty()) {
				return true;
			}
		}

		return false;
	}

//	public static boolean isNull(Object o结果) {
//		CacheObject cache结果 = (CacheObject)o结果;
//		if(cache结果.getValue() instanceof NullObject) {
//			return false;
//		}
//		return true;
//	}

	/*
		项目日记 2019/2/15 【缓存策略】

		每次当缓存数，大于某个数字的时候，
		清除缓存空间。

		这样的话，
		就要记住每个记录的访问次数。

		就是追加的时候，
		同时初始化访问次数，

		每次访问的时候，
		就追加一次次数。

		【课题】
		如何取得所有的访问次数为零的记录集合。

		【黑名单策略】
		用list
		每次都要把黑名单上的key从map中删掉。

		当被查询过的时候。
		就在另一个map中追加一下记录。

		【删除内存】

		就便利黑名单，
		针对每个记录，在map中检索，
		如果有记录，
		就放过，

		如果没有记录，
		就从缓存中，除掉该记录。
	 */
	static void clearCache(){
		for(String skey : linkedList_key) {
			if(cacheMap_counter.get(skey) == 0) {
				cacheMap.remove(skey);
			}
		}
		cacheMap_counter.clear();
		linkedList_key.clear();
	}
}
