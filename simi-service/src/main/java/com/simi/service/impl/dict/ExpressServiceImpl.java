package com.simi.service.impl.dict;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.dict.ExpressService;
import com.simi.vo.ExpressSearchVo;
import com.simi.po.dao.dict.DictExpressMapper;
import com.simi.po.model.dict.DictExpress;

@Service
public class ExpressServiceImpl implements ExpressService {

	@Autowired
	private DictExpressMapper expressMapper;

	@Override
	public DictExpress selectByPrimaryKey(Long id) {
		return expressMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public List<DictExpress> selectBySearchVo(ExpressSearchVo searchVo) {
		return expressMapper.selectBySearchVo(searchVo);
	}


}
