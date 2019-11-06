package com.cy.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cy.pj.sys.dao.SysLogDao;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SysLogTests {

	@Autowired
	private SysLogDao dao;
	@Test
	public void testDao() {
		String username = "admin";
		System.out.println("测试dao1" + dao.getRowCount(username));
		System.out.println("测试dao2" + dao.findPageLogs("admin", 1, 2));
	}
}
