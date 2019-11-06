package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cy.pj.sys.entity.SysLog;

@Mapper
public interface SysLogDao {
	/**
	 * 根据条件查询分页信息
	 */
	List<SysLog> findPageLogs(
			@Param("username")String username, 
			@Param("startIndex")Integer startIndex, 
			@Param("pageSize")Integer pageSize);
	
	/**
	 * 根据username获取总记录数
	 */
//	@Select("select count(*) from sys_logs where username like concat('%',#{username},'%')")
	int getRowCount(@Param("username")String username);
	
	/**
	 * 删除log记录的方法
	 */
	int deleteLogs(@Param("ids")Integer... ids);
}
