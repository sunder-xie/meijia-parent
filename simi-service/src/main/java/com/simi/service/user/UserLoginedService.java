package com.simi.service.user;

import java.util.List;

import com.simi.po.model.user.UserLogined;

public interface UserLoginedService {
    int deleteByPrimaryKey(Long id);

    int insert(UserLogined record);

    int insertSelective(UserLogined record);

    UserLogined selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserLogined record);

    int updateByPrimaryKey(UserLogined record);
    
	UserLogined initUserLogined();

	int selectByCount(Long userId);
	
	//最近一个月 登录过的 用户（不区分用户类型）
	List<Long> selectUserIdsLastMonth();
}