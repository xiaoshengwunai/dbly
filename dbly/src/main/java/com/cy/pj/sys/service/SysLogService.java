package com.cy.pj.sys.service;

import com.cy.pj.sys.common.vo.PageObject;
import com.cy.pj.sys.entity.SysLog;

public interface SysLogService {
	
	PageObject<SysLog> findPageLogs(
			String username,
			Integer pageCurrent);
	
	/**
	 * 删除log记录的方法
	 * @param ids
	 * @return
	 */
	int deleteLogs(Integer... ids);
}
