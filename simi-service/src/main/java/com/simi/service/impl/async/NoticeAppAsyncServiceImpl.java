package com.simi.service.impl.async;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meijia.utils.MathBigDecimalUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.push.PushUtil;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.user.UserPushBind;
import com.simi.po.model.user.Users;
import com.simi.service.async.NoticeAppAsyncService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrdersService;
import com.simi.service.user.UserPushBindService;
import com.simi.service.user.UsersService;

@Service
public class NoticeAppAsyncServiceImpl implements NoticeAppAsyncService {

	@Autowired
	public UsersService usersService;
	
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private UserPushBindService userPushBindService;
	
	@Autowired
	private OrderPricesService orderPricesService;
	
	
	// 发送推送消息
	@Async
	@Override
	public Future<Boolean> pushMsgToDevice(Long userId, String msgTitle, String msgContent) {

		// 发送推送消息（接受者）
		UserPushBind userPushBind = userPushBindService.selectByUserId(userId);

		if (userPushBind == null)
			return new AsyncResult<Boolean>(true);
		if (StringUtil.isEmpty(userPushBind.getClientId()))
			return new AsyncResult<Boolean>(true);

		HashMap<String, String> tranParams = new HashMap<String, String>();

		tranParams.put("is_show", "true");
		tranParams.put("action", "msg");
		tranParams.put("card_id", "0");
		tranParams.put("card_type", "0");
		tranParams.put("service_time", "");
		tranParams.put("remind_time", "");
		tranParams.put("remind_title", msgTitle);
		tranParams.put("remind_content", msgContent);
		
		String clientId = userPushBind.getClientId();
		String deviceType = userPushBind.getDeviceType();
		pushUtilMsg(tranParams, clientId, deviceType);
		

		return new AsyncResult<Boolean>(true);
	}	
	
	// 发送推送消息-速通宝
	@Async
	@Override
	public Future<Boolean> pushMsgToExpr(Long orderId, String carNo, String carColor, String capImg) {	
		
		Orders order = ordersService.selectByPrimaryKey(orderId);
		if (order == null) return new AsyncResult<Boolean>(true);
		
		Long userId = order.getUserId();
		// 发送推送消息（接受者）
		UserPushBind userPushBind = userPushBindService.selectByUserId(userId);
		if (userPushBind == null) return new AsyncResult<Boolean>(true);
		if (StringUtil.isEmpty(userPushBind.getClientId())) return new AsyncResult<Boolean>(true);
		
		
		Users u = usersService.selectByPrimaryKey(userId);
		String mobile = u.getMobile();
		BigDecimal restMoney = u.getRestMoney();
		String restMoneyStr = MathBigDecimalUtil.round2(restMoney);
		
		OrderPrices orderPrice = orderPricesService.selectByOrderId(orderId);
		if (orderPrice == null) return new AsyncResult<Boolean>(true);
		
		BigDecimal orderPay = orderPrice.getOrderPay();
		String orderPayStr = MathBigDecimalUtil.round2(orderPay);
		
		String timeStr = TimeStampUtil.timeStampToDateStr(order.getAddTime() * 1000, "MM-dd HH:mm");
		HashMap<String, String> tranParams = new HashMap<String, String>();
		tranParams.put("is_show", "false");
		tranParams.put("action", "car-msg");
		tranParams.put("car_no", carNo);
		tranParams.put("car_color", carColor);
		tranParams.put("mobile", mobile);
		tranParams.put("ocx_time", timeStr);
		tranParams.put("order_money", orderPayStr);
		tranParams.put("rest_money", restMoneyStr);
		tranParams.put("user_id", userId.toString());
		tranParams.put("cap_img", capImg);
		tranParams.put("remind_content", "");
		
		String clientId = userPushBind.getClientId();
		String deviceType = userPushBind.getDeviceType();
		pushUtilMsg(tranParams, clientId, deviceType);
		
		
		return new AsyncResult<Boolean>(true);
	}
	
	
	private void pushUtilMsg(HashMap<String, String> tranParams, String clientId, String deviceType) {
		HashMap<String, String> params = new HashMap<String, String>();
		
		ObjectMapper objectMapper = new ObjectMapper();

		String jsonParams = "";
		try {
			jsonParams = objectMapper.writeValueAsString(tranParams);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		params.put("transmissionContent", jsonParams);
		params.put("cid", clientId);

		if (deviceType.equals("ios")) {
			try {
				PushUtil.IOSPushToSingle(params, "notification");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (deviceType.equals("android")) {
			try {
				PushUtil.AndroidPushToSingle(params);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}