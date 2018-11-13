package com.gdbc.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类  
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})  
public class TestMyBatis {

	/*private static Logger logger = Logger.getLogger(TestMyBatis.class);  
	@Resource  
    private SysHttplogsService sysHttplogsService;
	@Test  
    public void test1() {  
		SysTttplogs sysTttplogs = sysHttplogsService.findSysTttplogsByBusinessId("a3d01a05-8b46-42e0-91fe-c724056df29a");
		System.out.println(sysTttplogs.getRequestUrl());
	}*/
}
