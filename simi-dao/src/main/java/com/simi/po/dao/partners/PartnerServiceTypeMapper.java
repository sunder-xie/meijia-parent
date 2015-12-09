package com.simi.po.dao.partners;

import java.util.List;

import com.simi.po.model.partners.PartnerServiceType;
import com.simi.vo.partners.PartnerServiceTypeSearchVo;
import com.simi.vo.partners.PartnerUserSearchVo;

public interface PartnerServiceTypeMapper {
    int deleteByPrimaryKey(Long serviceTypeId);

    int insert(PartnerServiceType record);

    int insertSelective(PartnerServiceType record);

    PartnerServiceType selectByPrimaryKey(Long serviceTypeId);

    int updateByPrimaryKeySelective(PartnerServiceType record);

    int updateByPrimaryKey(PartnerServiceType record);
    
	List<PartnerServiceType> selectByIds(List<Long> ids);

	List<PartnerServiceType> selectBySearchVo(PartnerServiceTypeSearchVo searchVo);

}
