package com.simi.action.app.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.dict.DictCoupons;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.user.UserCoupons;
import com.simi.po.model.user.Users;
import com.simi.service.dict.DictCouponsService;
import com.simi.service.impl.user.UserDetailPayServiceImpl;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.user.UserCouponService;
import com.simi.service.user.UsersService;
import com.meijia.utils.DateUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.vo.AppResultData;
import com.simi.vo.user.UserCouponsVo;

@Controller
@RequestMapping(value = "/app/user")
public class UserCouponController extends BaseController {

	@Autowired
	private UserCouponService userCouponService;

	@Autowired
	private DictCouponsService couponService;

	@Autowired
	private UsersService userService;

	@Autowired
	private UserDetailPayServiceImpl userDetailPayService;
	
	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;

	/*
	 * 我的优惠券接口
	 * 接口文档: http://182.92.160.194/trac/wiki/%E6%88%91%E7%9A%84%E4%BC%98%E6%83%A0%E5%88%B8%E6%8E%A5%E5%8F%A3
	 * @param
	 *  user_id 用户ID
	 */
	@RequestMapping(value = "get_coupons", method = RequestMethod.GET)
	public AppResultData<List> getCoupons(
			@RequestParam("user_id") Long userId) {

		AppResultData<List> result = new AppResultData<List>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new ArrayList());

		// 先获取用户的基本优惠券
		List<UserCoupons> listUserCoupons = userCouponService.selectByUserId(userId);

		if (listUserCoupons.isEmpty()) {
			return result;
		}
		List<UserCoupons> vUserCoupons = new ArrayList<UserCoupons>();
		UserCoupons item = null;
		List<Long> couponsIds  = new ArrayList<Long>();
		Long todayBegin = TimeStampUtil.getBeginOfToday();
		for(int i = 0; i < listUserCoupons.size(); i++) {
			item = listUserCoupons.get(i);
			//已经使用过的
			if (item.getIsUsed().equals((short)1)) continue;
			//优惠券已经过期的，都不显示
			if (item.getExpTime() == 0 || item.getExpTime() >= todayBegin  ) {
				couponsIds.add(item.getCouponId());
				vUserCoupons.add(item);
			} 
		}
		//根据coupons_id 关联对应的优惠券实体
		List<DictCoupons> listCoupons = new ArrayList<DictCoupons>();
		if (!couponsIds.isEmpty()) {
			
			listCoupons = couponService.selectByIds(couponsIds);
		}

		//循环，创建为UserCouponVo对象
		DictCoupons  dictCoupon = null;
		List<UserCouponsVo> list = new ArrayList<UserCouponsVo>();
		for(int i = 0; i < vUserCoupons.size(); i++) {
			item = vUserCoupons.get(i);

			UserCouponsVo vo = new UserCouponsVo();
			BeanUtils.copyProperties(item, vo);
			vo.setId(item.getId());
			for (int j = 0; j< listCoupons.size(); j++) {
				dictCoupon = listCoupons.get(j);
				if (item.getCouponId().equals(dictCoupon.getId())) {
					
					vo.setServiceTypeId(dictCoupon.getServiceTypeId());
					vo.setServicePriceId(dictCoupon.getServicePriceId());
					
					vo.setServiceTypeName("");
					vo.setServicePriceName("全部");
					
					if (dictCoupon.getServiceTypeId() > 0L) {
						PartnerServiceType partnerServiceType = partnerServiceTypeService.selectByPrimaryKey(dictCoupon.getServiceTypeId());
						vo.setServiceTypeName(partnerServiceType.getName());
					}
					
					if (dictCoupon.getServicePriceId() > 0L) {
						PartnerServiceType partnerServicePrice = partnerServiceTypeService.selectByPrimaryKey(dictCoupon.getServicePriceId());
						vo.setServicePriceName(partnerServicePrice.getName());
					}
					
					vo.setMaxValue(dictCoupon.getMaxValue());
					//过期时间
					Long expTime = item.getExpTime()*1000;
				//	String expTimestr = expTime.toString();
					vo.setToDate(TimeStampUtil.timeStampToDateStr(expTime, "yyyy-MM-dd"));
				//	vo.setToDate(TimeStampUtil.);
					vo.setIntroduction(dictCoupon.getIntroduction());
					vo.setDescription(dictCoupon.getDescription());
					break;
				}
			}

			list.add(vo);

		}

		result.setData(list);
		return result;
	}

	/*
	 * 兑换优惠券接口
	 * 接口文档: http://182.92.160.194/trac/wiki/%E5%85%91%E6%8D%A2%E4%BC%98%E6%83%A0%E5%88%B8%E6%8E%A5%E5%8F%A3
	 * @param
	 *    mobile   手机号
	 *    card_passwd  兑换码
	 */
	@RequestMapping(value = "post_coupon", method = RequestMethod.POST)
	public AppResultData<Object> postCoupons (
			@RequestParam("user_id") Long userId,
			@RequestParam("card_passwd") String cardPasswd) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		//验证优惠券是否有效
		AppResultData<Object> resultValidation = userCouponService.validateCoupon(userId, cardPasswd);
		//不正确直接返回
		if (resultValidation.getStatus() != Constants.SUCCESS_0) {
			return resultValidation;
		}

		//判定该优惠卷是已经兑换过，如果兑换过则不需要再添加
		UserCoupons item = userCouponService.selectByUserIdCardPwd(userId, cardPasswd);
		if (item != null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.COUPON_IS_USED_MSG);
			result.setData("");
			return result;
		}

		UserCoupons record = userCouponService.initUserCoupon();
		//获得优惠券实体类
		DictCoupons dictCoupon = couponService.selectByCardPasswd(cardPasswd);
		//获取用户实体类
		Users u = userService.selectByPrimaryKey(userId);
		record.setUserId(u.getId());
		record.setMobile(u.getMobile());
		record.setCardPasswd(cardPasswd);
		record.setCouponId(dictCoupon.getId());
		record.setValue(dictCoupon.getValue());

		String extTimeStr = DateUtil.formatDate(dictCoupon.getToDate());
		Long extTime = TimeStampUtil.getMillisOfDay(extTimeStr)/1000;
		record.setExpTime(extTime);
		
		
		Long newId = userCouponService.insert(record);

		// 如果优惠券类型为充值卡充值，则直接进行用户充值，并且返回提示信息.
		/*if (dictCoupon.getCouponType().equals(Constants.COUPON_TYPE_1)) {
			//先进行用户的账号明细记录
			UserDetailPay userDetailPay = userDetailPayService.initUserDetail();
			userDetailPay.setMobile(u.getMobile());
			userDetailPay.setUserId(u.getId());
			userDetailPay.setOrderType((short) 3);
			userDetailPay.setOrderId(dictCoupon.getId());
			userDetailPay.setOrderNo(dictCoupon.getCardPasswd());
			userDetailPay.setOrderMoney(dictCoupon.getValue());
			userDetailPay.setOrderPay(dictCoupon.getValue());
			userDetailPay.setPayType(Constants.PAY_TYPE_5);

			userDetailPayService.insert(userDetailPay);
			//最后增加用户的余额.
			BigDecimal restMoney = u.getRestMoney();
			BigDecimal couponValue = dictCoupon.getValue();
			restMoney = MathBigDeciamlUtil.add(restMoney, couponValue);
			u.setRestMoney(restMoney);
			userService.updateByPrimaryKeySelective(u);

			//优惠卷变成已使用状态
			UserCoupons newItem = userCouponService.selectByUserIdCardPwd(userId, cardPasswd);

			newItem.setIsUsed((short) 1);
			userCouponService.updateByPrimaryKeySelective(newItem);


			result.setStatus(Constants.ERROR_999);
			result.setMsg("恭喜！"+ couponValue.toPlainString() + "元已成功充入您的账户。");
			result.setData("");
			return result;
		}*/

		//如果 优惠券类型为 返回插入的数据
		UserCouponsVo vo = new UserCouponsVo();
		BeanUtils.copyProperties(record, vo);
		vo.setId(record.getId());

		vo.setServiceTypeId(dictCoupon.getServiceTypeId());
		vo.setServicePriceId(dictCoupon.getServicePriceId());
		
		if(dictCoupon.getServicePriceId() == 0){
			vo.setServiceTypeName("");
			vo.setServicePriceName("");
		}else {
			//服务类型名称
			PartnerServiceType partnerServiceType = partnerServiceTypeService.selectByPrimaryKey(dictCoupon.getServiceTypeId());
			vo.setServiceTypeName(partnerServiceType.getName());
			//服务报价名称
			partnerServiceType = partnerServiceTypeService.selectByPrimaryKey(dictCoupon.getServicePriceId());
			vo.setServicePriceName(partnerServiceType.getName());
			
		}
		
		
		vo.setMaxValue(dictCoupon.getMaxValue());
		//过期时间
		//Long expTime = item.getExpTime();
		vo.setToDate(DateUtil.formatDate(dictCoupon.getToDate()));
		
		//vo.setToDate(DateUtil.parse(dictCoupon.getToDate()));
		
		vo.setIntroduction(dictCoupon.getIntroduction());
		vo.setDescription(dictCoupon.getDescription());

		result.setData(vo);

		return result;
	}
}
