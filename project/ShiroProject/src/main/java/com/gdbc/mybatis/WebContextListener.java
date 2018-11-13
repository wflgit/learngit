package com.gdbc.mybatis;

import javax.servlet.ServletContext;

import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class WebContextListener extends org.springframework.web.context.ContextLoaderListener {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
		WebApplicationContext initWebApplicationContext = super.initWebApplicationContext(servletContext);
		System.setProperty("org.terracotta.quartz.skipUpdateCheck", "true");
		try {
			autoStartScheduler(servletContext);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return initWebApplicationContext;
	}

	private void autoStartScheduler(ServletContext servletContext) throws Exception {
		System.setProperty("org.terracotta.quartz.skipUpdateCheck", "true");
		WebApplicationContext springContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);
		Scheduler scheduler = (Scheduler) springContext.getBean("scheduler");
		scheduler.start();
		try {
			scheduler.pauseTrigger("notify_IllegalRecord_MsgTrigger", "carInfo");
			scheduler.pauseTrigger("illegalRecordLogTrigger", "carInfo");
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("定时器启动成功！");
	}

}
