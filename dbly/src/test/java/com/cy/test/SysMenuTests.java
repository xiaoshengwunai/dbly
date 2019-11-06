package com.cy.test;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cy.pj.sys.dao.SysMenuDao;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysMenuTests {

	@Autowired
	private SysMenuDao dao;
	
	@Test
	public void testDao() {
//		System.out.println(dao.findMenus1());
		System.out.println(dao.findMenus2());
	}
	
	@Test
	public void testService() {
		/**
		* 判断要删除的订单有没有子菜单
		 * 其实这里要是否有子菜单也可以通过"先拿到表中所有parentId,再看所要删除的id 是否有作为parentId存在其中"
		 * 这是与课件原有的思路少有不同的地方,课件原有思路是"从dao操作"
		 */
		
//		dao.deleteMenuById(10);
		List<Map<String, Object>> list = dao.findMenus1();
		for (Map map : list) {
			Object object = map.get("parentId");
		}
	}
}