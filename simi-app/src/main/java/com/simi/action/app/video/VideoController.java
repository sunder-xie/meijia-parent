package com.simi.action.app.video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meijia.utils.MathBigDecimalUtil;
import com.meijia.utils.StringUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.partners.PartnerServicePriceDetail;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.service.partners.PartnerServicePriceDetailService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.partners.PartnerUserService;
import com.simi.service.partners.PartnersService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.partners.PartnerServiceTypeSearchVo;
import com.simi.vo.partners.PartnerUserServiceTypeVo;

@Controller
@RequestMapping(value = "/app/video")
public class VideoController extends BaseController {

	@Autowired
	private UsersService userService;

	@Autowired
	private PartnerUserService partnerUserService;

	@Autowired
	private PartnersService partnersService;

	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;

	@Autowired
	private PartnerServicePriceDetailService partnerServicePriceDetailService;
	
	public Long serviceTypeId = 306L;


	/**
	 * 视频文章频道列表
	 * 
	 */
	@RequestMapping(value = "channels", method = RequestMethod.GET)
	public AppResultData<Object> channels() {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		PartnerServiceTypeSearchVo searchVo = new PartnerServiceTypeSearchVo();
		searchVo.setParentId(serviceTypeId);
		searchVo.setViewType((short) 0);
		searchVo.setIsEnable((short) 1);
		List<PartnerServiceType> list = partnerServiceTypeService.selectBySearchVo(searchVo);
		
		List<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();
		
		for (PartnerServiceType item : list) {
			HashMap<String, Object> vo = new HashMap<String, Object>();
			vo.put("channelId", item.getId());
			vo.put("name", item.getName());
			datas.add(vo);
		}
		
		result.setData(datas);
		
		return result;

	}
	
	/**
	 * 视频文章频道列表
	 * 
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public AppResultData<Object> list(
			@RequestParam(value = "channel_id", required = false, defaultValue = "0") Long channelId,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword
			) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		if (channelId.equals(0L) && StringUtil.isEmpty(keyword)) {
			return result;
		}
		
		PartnerUserServiceTypeVo searchVo = new PartnerUserServiceTypeVo();
		searchVo.setParentId(serviceTypeId);
		if (channelId > 0L) {
			searchVo.setExtendId(channelId);
		}
		
		if (!StringUtil.isEmpty(keyword)) {
			searchVo.setKeyword(keyword);
		}
		
		List<PartnerServicePriceDetail> list = partnerServicePriceDetailService.selectBySearchVo(searchVo);
		
		
		List<Long> servicePriceIds = new ArrayList<Long>(); 
		for(PartnerServicePriceDetail item : list) {
			if (!servicePriceIds.contains(item.getServicePriceId())) {
				servicePriceIds.add(item.getServicePriceId());
			}
		}
		
		PartnerServiceTypeSearchVo searchVo1 = new PartnerServiceTypeSearchVo();
		searchVo1.setServiceTypeIds(servicePriceIds);
		
		List<PartnerServiceType> serviceTypeList = partnerServiceTypeService.selectBySearchVo(searchVo1);
		
		List<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();
		
		for (int i = 0 ; i < list.size(); i++) {
			PartnerServicePriceDetail item = list.get(i);
			
			String title = "";
			
			for (PartnerServiceType p : serviceTypeList) {
				if (p.getId().equals(item.getServicePriceId())) {
					title = p.getName();
					break;
				}
			}
			
			HashMap<String, Object> vo = new HashMap<String, Object>();
			vo.put("channelId", item.getId());
			vo.put("title", title);
			vo.put("imgUrl", item.getImgUrl());
			vo.put("price", MathBigDecimalUtil.round2(item.getPrice()));
			vo.put("disPrice", MathBigDecimalUtil.round2(item.getDisPrice()));
			vo.put("content", item.getContentDesc());
			vo.put("keywords", item.getContentFlow());
			vo.put("videoUrl", item.getVideoUrl());
			
			
			
		}
		
		result.setData(datas);
		
		return result;

	}
	

}