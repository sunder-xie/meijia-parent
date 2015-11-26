package com.simi.po.dao.xcloud;

import com.simi.po.model.xcloud.XcompanyDept;

public interface XcompanyDeptMapper {
    int deleteByPrimaryKey(Long deptId);

    int insert(XcompanyDept record);

    int insertSelective(XcompanyDept record);

    XcompanyDept selectByPrimaryKey(Long deptId);

    int updateByPrimaryKeySelective(XcompanyDept record);

    int updateByPrimaryKey(XcompanyDept record);
}