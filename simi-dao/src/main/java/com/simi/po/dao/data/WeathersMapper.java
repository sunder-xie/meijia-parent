package com.simi.po.dao.data;

import java.util.Date;

import com.simi.po.model.data.Weathers;

public interface WeathersMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Weathers record);

    int insertSelective(Weathers record);

    Weathers selectByPrimaryKey(Long id);
    
    Weathers selectByCityIdAndDate(Long cityId, Date weahterDate);

    int updateByPrimaryKeySelective(Weathers record);

    int updateByPrimaryKey(Weathers record);

	int deleteByDate(Date weatherDate);
}