package com.simi.action.app.xcloud;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanyBenz;
import com.simi.po.model.xcloud.XcompanyBenzTime;
import com.simi.po.model.xcloud.XcompanyCheckin;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.po.model.xcloud.XcompanyStaffBenz;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XcompanyBenzService;
import com.simi.service.xcloud.XcompanyBenzTimeService;
import com.simi.service.xcloud.XcompanyCheckinService;
import com.simi.service.xcloud.XcompanyStaffBenzService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.AppResultData;
import com.simi.vo.xcloud.UserCompanySearchVo;

@Controller
@RequestMapping(value = "/app/company")
public class CompanyCheckinController extends BaseController {

	@Autowired
	private XCompanyService xCompanyService;

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private XcompanyStaffService xCompanyStaffService;		

	@Autowired
	private XcompanyBenzService xCompanyBenzService;	
	
	@Autowired
	private XcompanyBenzTimeService xCompanyBenzTimeService;		
	
	@Autowired
	private XcompanyStaffBenzService xCompanyStaffBenzService;

	@Autowired
	private XcompanyCheckinService xCompanyCheckinService;
	
	@RequestMapping(value = "/checkin", method = { RequestMethod.POST })
	public AppResultData<Object> getByBenz(
			@RequestParam("company_id") Long companyId,
			@RequestParam("user_id") Long userId,
			@RequestParam("poi_name") String poiName,
			@RequestParam("poi_lat") String poiLat,
			@RequestParam("poi_lng") String poiLng,
			@RequestParam(value = "remarks", required = false, defaultValue = "") String remarks,
			@RequestParam(value = "checkin_net", required = false, defaultValue = "") String checkinNet,
			@RequestParam(value = "checkin_type", required = false, defaultValue = "0") Short checkinType,
			@RequestParam(value = "checkin_from", required = false, defaultValue = "0") Short checkinFrom
			) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = usersService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		//判断员工是否为公司一员
		UserCompanySearchVo searchVo = new UserCompanySearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setUserId(userId);
		searchVo.setStatus((short) 1);
		List<XcompanyStaff> staffList = xCompanyStaffService.selectBySearchVo(searchVo);
		
		if (staffList.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("公司不存在");
			return result;
		}

		XcompanyStaff staff = staffList.get(0);
		
		Long staffId = staff.getId();
		
		Long checkinTime = TimeStampUtil.getNowSecond();
				
		//记录考勤信息
		XcompanyCheckin record = xCompanyCheckinService.initXcompanyCheckin();

		record.setCompanyId(companyId);
		record.setStaffId(staffId);
		record.setUserId(userId);
		record.setCheckinFrom(checkinFrom);
		record.setCheckinType(checkinType);
		record.setCheckinNet(checkinNet);
		record.setPoiName(poiName);
		record.setPoiLat(poiLat);
		record.setPoiLng(poiLng);
		//todo .根据公司的地址计算距离公司的距离数（单位：米）;
		record.setPoiDistance(0);
		
		record.setRemarks(remarks);
		
		record.setAddTime(checkinTime);
		
		Map<String, String> datas = new HashMap<String, String>();
		String checkinTimeStr = TimeStampUtil.timeStampToDateStr(checkinTime * 1000, "HH:mm");
		datas.put("checkinTime", checkinTimeStr);
		datas.put("poiName", poiName);
		
		result.setData(datas);
		
		return result;
	}
	
}