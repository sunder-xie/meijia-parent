package com.simi.action.app.user;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meijia.utils.IPUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.UserBaiduBind;
import com.simi.po.model.user.UserLogined;
import com.simi.po.model.user.UserRef3rd;
import com.simi.po.model.user.UserSmsToken;
import com.simi.po.model.user.Users;
import com.simi.service.user.UserBaiduBindService;
import com.simi.service.user.UserCouponService;
import com.simi.service.user.UserLoginedService;
import com.simi.service.user.UserRef3rdService;
import com.simi.service.user.UserRefSeniorService;
import com.simi.service.user.UserSmsTokenService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.user.UserBaiduBindVo;

@Controller
@RequestMapping(value = "/app/user")
public class UserRef3rdController extends BaseController {

	@Autowired
	private UsersService userService;

	@Autowired
	private UserLoginedService userLoginedService;

	@Autowired
	private UserCouponService userCouponService;

	@Autowired
	private UserRef3rdService userRef3rdService;

	@Autowired
	private UserBaiduBindService userBaiduBindService;

	@Autowired
	private UserSmsTokenService userSmsTokenService;

	@Autowired
	private UserRefSeniorService userRefSeniorService;

	// 1、第三方登录接口
	@RequestMapping(value = "login-3rd", method = RequestMethod.POST)
	public AppResultData<Object> login3rd(
			HttpServletRequest request,
			@RequestParam("openid") String openid,
			@RequestParam("3rd_type") String thirdType,
			@RequestParam("name") String name,
			@RequestParam(value = "head_img", required = false, defaultValue = "") String headImg,
			@RequestParam("login_from") int loginFrom) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		Users users = userService.selectByOpenidAndThirdType(openid, thirdType);

		// users不为空，表示用户可能通过手机号或者第三方账号注册
		if (users != null) {
			long ip = IPUtil.getIpAddr(request);
			// 第三方登录成功，在user_logined表添加记录
			UserLogined userLogined = new UserLogined();
			userLogined.setMobile(users.getMobile());
			userLogined.setLoginFrom((short) loginFrom);
			userLogined.setLoginIp(ip);
			userLogined.setUserId(users.getId());
			userLogined.setAddTime(TimeStampUtil.getNow() / 1000);
			userLoginedService.insertSelective(userLogined);

			users.setName(name);
			users.setHeadImg(headImg);
			users.setAddFrom((short) loginFrom);
			userService.updateByPrimaryKeySelective(users);

		} else {
			users = userService.initUser(openid, (short) loginFrom);

			users.setOpenId(openid);
			users.setThirdType(thirdType);
			users.setName(name);
			users.setHeadImg(headImg);
			users.setAddFrom((short) loginFrom);
			userService.saveUser(users);

			// 第三方账号注册绑定环信账号
			String nickName = "simi-user-[" + users.getId() + "]";
			userService.genImUser(users, nickName);

			userRefSeniorService.allotSenior(users);
		}

		UserBaiduBind userBaiduBind = userBaiduBindService.selectByUserId(users
				.getId());

		UserBaiduBindVo vo = new UserBaiduBindVo();

		try {
			BeanUtils.copyProperties(vo, users);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		vo.setAppId("");
		vo.setChannelId("");
		vo.setAppUserId("");

		if (userBaiduBind != null) {
			vo.setAppId(userBaiduBind.getAppId());
			vo.setChannelId(userBaiduBind.getChannelId());
			vo.setAppUserId(userBaiduBind.getAppUserId());
		}
		result.setData(vo);

		return result;
	}
}