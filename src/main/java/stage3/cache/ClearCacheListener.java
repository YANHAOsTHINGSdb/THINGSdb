package stage3.cache;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.oschina.j2cache.J2Cache;

public class ClearCacheListener implements ServletContextListener{

	/**
	 * 每次重启Web服务器，都要清理缓存
	 *
	 * 由于缓存是独立于Web服务的。所以需要另外清理缓存
	 *
	 * 先在web.xml中配置Listener
	 *                               2019-1-14
	 */

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		J2Cache.getChannel().clear("default");
		System.out.println("contextInitialized 清除缓存");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		J2Cache.getChannel().close();
		System.out.println("contextDestroyed 关闭缓存");
	}
}
