package com.jt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 注意:
 * 	@SpringBootApplication 如果项目不需要配置"数据源",为了避免加载时要求加载数据源,可以用此注解属性来标识!
 * 		告诉Spring不需要加载数据源也就别来报错了!
 */
@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
public class SpringBootRun {
	
	public static void main(String[] args) {
		
		SpringApplication.run(SpringBootRun.class,args);
	}
}
