package com.simi.service.impl.partners;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.partners.PartnerServicePriceDetailMapper;
import com.simi.po.dao.partners.PartnerServicePriceDetailMapper;
import com.simi.po.model.partners.PartnerServicePriceDetail;
import com.simi.service.partners.PartnerServicePriceDetailService;
import com.simi.service.partners.PartnerServicePriceService;
import com.simi.vo.partners.PartnerServicePriceVo;


@Service
public class PartnerServicePriceDetailServiceImpl implements PartnerServicePriceDetailService {

	@Autowired
	private PartnerServicePriceDetailMapper partnerServicePriceDetailMapper;
	
	@Override
	public int deleteByPrimaryKey(Long servicePriceId) {
		return partnerServicePriceDetailMapper.deleteByPrimaryKey(servicePriceId);
	}

	@Override
	public Long insert(PartnerServicePriceDetail record) {
		return partnerServicePriceDetailMapper.insert(record);
	}

	@Override
	public Long insertSelective(PartnerServicePriceDetail record) {
		return partnerServicePriceDetailMapper.insertSelective(record);
	}

	@Override
	public PartnerServicePriceDetail selectByPrimaryKey(Long servicePriceId) {
		return partnerServicePriceDetailMapper.selectByPrimaryKey(servicePriceId);
	}

	@Override
	public int updateByPrimaryKeySelective(PartnerServicePriceDetail record) {
		return partnerServicePriceDetailMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(PartnerServicePriceDetail record) {
		return partnerServicePriceDetailMapper.updateByPrimaryKey(record);
	}

	@Override
	public PartnerServicePriceDetail initPartnerServicePriceDetail() {
		PartnerServicePriceDetail record = new PartnerServicePriceDetail();
		
		record.setId(0L);
		record.setServicePriceId(0L);
		record.setServiceTitle("");
		record.setImgUrl("");
		record.setPrice(new BigDecimal(0));
		record.setDisPrice(new BigDecimal(0));
		record.setContentStandard("");
		record.setContentDesc("");
		record.setContentFlow("");
		record.setAddTime(TimeStampUtil.getNowSecond());
		return record;
	}
	
	

}