package com.cy.pj.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.pj.sys.common.exception.ServiceException;
import com.cy.pj.sys.common.vo.PageObject;
import com.cy.pj.sys.dao.SysLogDao;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;
@Service
public class SysLogServiceImpl implements SysLogService{

	@Autowired
	private SysLogDao sysLogDao;

	@Override
	public PageObject<SysLog> findPageLogs(
			String username, Integer pageCurrent) {
//		System.out.println("业务层1" + username + pageCurrent);
		//验证参数合法性
		if (pageCurrent == null || pageCurrent<1) {
			throw new IllegalArgumentException("当前页码不正确");
		}
		
		//获取总记录数
		int rowCount = sysLogDao.getRowCount(username);
		if (rowCount==0) throw new ServiceException("没有查到任何数据");
		//计算几个数
		int pageSize = 5;
		int startIndex = (pageCurrent-1)*pageSize;
		List<SysLog> records = sysLogDao.findPageLogs(username, startIndex, pageSize);
		
		//处理结果
		PageObject<SysLog> pageObject = new PageObject<SysLog>();
		pageObject.setPageSise(pageSize);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageCount((rowCount-1)/pageSize + 1);
		pageObject.setRecords(records);
//		System.out.println("业务层2 : " + pageObject);
		return pageObject;
	}

	@Override
	public int deleteLogs(Integer... ids) {
		if(ids == null || ids.length == 0) throw new IllegalArgumentException("请选中至少一条记录");
		int rows;
		try {
			 rows = sysLogDao.deleteLogs(ids);
		} catch (Throwable t) {
			t.printStackTrace();
			throw new ServiceException("系统故障,正在修复!(其实不知道咋回事,只是跟顾客这么说的)");
		}
		if (rows == 0) throw new ServiceException("记录可能已经不存在了,要不然本系统怎么会只删除0条数据呢");
		return rows;
	}
	

}
