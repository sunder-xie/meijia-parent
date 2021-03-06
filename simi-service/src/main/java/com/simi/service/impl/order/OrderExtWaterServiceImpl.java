package com.simi.service.impl.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.order.OrderExtWaterService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrdersService;
import com.simi.service.partners.PartnerServicePriceService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UsersService;
import com.simi.utils.OrderUtil;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.order.OrderExtWaterListVo;
import com.simi.vo.order.OrderExtWaterXcloudVo;
import com.simi.vo.partners.PartnerServicePriceSearchVo;
import com.simi.vo.partners.PartnerServiceTypeSearchVo;
import com.simi.po.dao.order.OrderExtWaterMapper;
import com.simi.po.model.order.OrderExtWater;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.partners.PartnerServicePrice;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.user.UserAddrs;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.TimeStampUtil;

@Service
public class OrderExtWaterServiceImpl implements OrderExtWaterService{
    @Autowired
    private OrderExtWaterMapper orderExtWaterMapper;
    
    @Autowired
    private UsersService usersService;
    
    @Autowired
    private UserAddrsService userAddrsService;
    
    @Autowired
    private OrdersService ordersService;
    
    @Autowired
    private OrderPricesService orderPricesService;

    
    @Autowired
    private PartnerServiceTypeService partnerServiceTypeService;
    
    @Autowired
    private PartnerServicePriceService partnerServicePriceService;
    
	@Override
	public int deleteByPrimaryKey(Long id) {
		return orderExtWaterMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(OrderExtWater record) {
		return orderExtWaterMapper.insert(record);
	}

	@Override
	public int insertSelective(OrderExtWater record) {
		return orderExtWaterMapper.insertSelective(record);
	}

	@Override
	public OrderExtWater selectByPrimaryKey(Long id) {
		return orderExtWaterMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public OrderExtWater selectByOrderId(Long orderId) {
		return orderExtWaterMapper.selectByOrderId(orderId);
	}	

	@Override
	public int updateByPrimaryKey(OrderExtWater record) {
		return orderExtWaterMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(OrderExtWater record) {
		return orderExtWaterMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public OrderExtWater initOrderExtWater() {
		
		OrderExtWater record = new OrderExtWater();
		record.setId(0L);
		record.setOrderId(0L);
		record.setOrderNo("");
		record.setOrderExtStatus((short) 0);
		record.setUserId(0L);
		record.setServicePriceId(0L);
		record.setServiceNum(0);
		record.setLinkMan("");
		record.setLinkTel("");
		record.setIsDone((short) 0);
		record.setIsDoneTime(0L);
		record.setAddTime(TimeStampUtil.getNowSecond());
		return record;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByListPage(OrderSearchVo orderSearchVo, int pageNo, int pageSize) {

		 PageHelper.startPage(pageNo, pageSize);
         List<OrderExtWater> list = orderExtWaterMapper.selectByListPage(orderSearchVo);
         PageInfo result = new PageInfo(list);
        return result;
    }

	@Override
	public List<OrderExtWater> selectByUserId(Long userId) {
		return orderExtWaterMapper.selectByUserId(userId);
	}
	
	@Override
	public OrderExtWaterXcloudVo getListXcloudVo(OrderExtWater item) {
		OrderExtWaterXcloudVo  vo = new OrderExtWaterXcloudVo();
		vo.setOrderId(item.getOrderId());
		vo.setOrderNo(item.getOrderNo());
		vo.setUserId(item.getUserId());
		vo.setOrderExtStatus(item.getOrderExtStatus());
		vo.setOrderExtStatusName("");
		if (item.getOrderExtStatus() == 0) {
			vo.setOrderExtStatusName("运营人员处理中");
		}
		if (item.getOrderExtStatus() == 1) {
			vo.setOrderExtStatusName("已转派服务商");
		}
		if (item.getOrderExtStatus() == 2) {
			vo.setOrderExtStatusName("已签收");
		}
		//服务地址
		vo.setAddrName("");
		Orders order = ordersService.selectByOrderNo(item.getOrderNo());
		OrderPrices orderPrice = orderPricesService.selectByOrderId(item.getOrderId());
		
		vo.setOrderStatus(order.getOrderStatus());
		vo.setOrderMoney(new BigDecimal(0));
		vo.setOrderPay(new BigDecimal(0));
		//订单价格
		if (orderPrice != null) {
			vo.setOrderMoney(orderPrice.getOrderMoney());
			vo.setOrderPay(orderPrice.getOrderPay());
		}
		
		Long addrId = order.getAddrId();
		UserAddrs userAddr = userAddrsService.selectByPrimaryKey(addrId);
		if (userAddr != null) {
			vo.setAddrName(userAddr.getName() + " " + userAddr.getAddr());
		}

		//服务大类名称
		vo.setServiceTypeName("");
		Long serviceTypeId = order.getServiceTypeId();
		PartnerServiceType serviceType = partnerServiceTypeService.selectByPrimaryKey(serviceTypeId);
		if (serviceType != null) {
			vo.setServiceTypeName(serviceType.getName());
		}

		//服务价格商品名称
		vo.setServicePriceName("");
		vo.setImgUrl("");
		vo.setDisPrice(new BigDecimal(0));
		
		Long servicePriceId = item.getServicePriceId();
		PartnerServicePrice servicePrice = partnerServicePriceService.selectByPrimaryKey(servicePriceId);
		if (servicePrice != null) {
			vo.setServicePriceName(servicePrice.getName());
			vo.setImgUrl(servicePrice.getImgUrl());
			vo.setDisPrice(servicePrice.getDisPrice());
		}
		
		vo.setServiceNum(item.getServiceNum());
		vo.setOrderStatusName(OrderUtil.getOrderStausNameWater(order.getOrderStatus(), vo.getOrderExtStatus()));
		vo.setAddTimeStr(TimeStampUtil.fromTodayStr(order.getAddTime() * 1000));
		
		vo.setIsDone(item.getIsDone());
		
		vo.setIsDoneTimeStr("");
		if (item.getIsDoneTime() > 0L) {
			vo.setIsDoneTimeStr(TimeStampUtil.fromTodayStr(item.getIsDoneTime() * 1000));
		}	
		vo.setOrderExtStatus(item.getOrderExtStatus());
		
		return vo;
	}
	@Override
	public OrderExtWaterListVo getListVo(OrderExtWater item) {
		OrderExtWaterListVo  vo = new OrderExtWaterListVo();
		vo.setOrderId(item.getOrderId());
		vo.setOrderNo(item.getOrderNo());
		vo.setUserId(item.getUserId());
		
		//服务地址
		vo.setAddrName("");
		Orders order = ordersService.selectByOrderNo(item.getOrderNo());
		OrderPrices orderPrice = orderPricesService.selectByOrderId(item.getOrderId());
		
		vo.setOrderStatus(order.getOrderStatus());
		vo.setOrderMoney(new BigDecimal(0));
		vo.setOrderPay(new BigDecimal(0));
		//订单价格
		if (orderPrice != null) {
			vo.setOrderMoney(orderPrice.getOrderMoney());
			vo.setOrderPay(orderPrice.getOrderPay());
		}
		
		Long addrId = order.getAddrId();
		UserAddrs userAddr = userAddrsService.selectByPrimaryKey(addrId);
		if (userAddr != null) {
			vo.setAddrName(userAddr.getName() + " " + userAddr.getAddr());
		}

		//服务大类名称
		vo.setServiceTypeName("");
		Long serviceTypeId = order.getServiceTypeId();
		PartnerServiceType serviceType = partnerServiceTypeService.selectByPrimaryKey(serviceTypeId);
		if (serviceType != null) {
			vo.setServiceTypeName(serviceType.getName());
		}

		//服务价格商品名称
		vo.setServicePriceName("");
		vo.setImgUrl("");
		vo.setDisPrice(new BigDecimal(0));
		
		Long servicePriceId = item.getServicePriceId();

		PartnerServicePrice servicePrice = partnerServicePriceService.selectByPrimaryKey(servicePriceId);
		if (servicePrice != null) {
			vo.setServicePriceName(servicePrice.getName());
			vo.setImgUrl(servicePrice.getImgUrl());
			vo.setDisPrice(servicePrice.getDisPrice());
		}
		
		vo.setServiceNum(item.getServiceNum());
		vo.setOrderStatusName(OrderUtil.getOrderStausName(order.getOrderStatus()));
		vo.setAddTimeStr(TimeStampUtil.fromTodayStr(order.getAddTime() * 1000));
		
		vo.setIsDone(item.getIsDone());
		
		vo.setIsDoneTimeStr("");
		if (item.getIsDoneTime() > 0L) {
			vo.setIsDoneTimeStr(TimeStampUtil.fromTodayStr(item.getIsDoneTime() * 1000));
		}	
		vo.setOrderExtStatus(item.getOrderExtStatus());
		
		return vo;
	}
	
	@Override
	public List<OrderExtWaterListVo> getListVos(List<OrderExtWater> list) {
		List<OrderExtWaterListVo>  result = new ArrayList<OrderExtWaterListVo>();
		
		List<Long> orderIds = new ArrayList<Long>();
		
		List<Long> serivcePriceIds = new ArrayList<Long>();
		//批量查找userAddrs;
		for (OrderExtWater item : list) {
			if (!orderIds.contains(item.getOrderId())) {
				orderIds.add(item.getOrderId());
			}
			
			if (!serivcePriceIds.contains(item.getServicePriceId())) {
				serivcePriceIds.add(item.getServicePriceId());
			}
		}
		
		if (orderIds.isEmpty()) return result;
		List<Orders> orders = ordersService.selectByOrderIds(orderIds);
		
		List<OrderPrices> orderPrices = orderPricesService.selectByOrderIds(orderIds);
		
		List<Long> servicePriceIds = new ArrayList<Long>();
		List<Long> userAddrIds = new ArrayList<Long>();
		
		for (Orders item : orders) {
			if (!servicePriceIds.contains(item.getServiceTypeId())) {
				servicePriceIds.add(item.getServiceTypeId());
			}
			
			if(!userAddrIds.contains(item.getAddrId())) {
				if (item.getAddrId() > 0L) {
					userAddrIds.add(item.getAddrId());
				}
			}
		}
		

		PartnerServicePriceSearchVo searchVo1 = new PartnerServicePriceSearchVo();
		searchVo1.setServicePriceIds(servicePriceIds);
		List<PartnerServicePrice> servicePrices = partnerServicePriceService.selectBySearchVo(searchVo1);
		List<UserAddrs> userAddrs = new ArrayList<UserAddrs>();
		if (!userAddrIds.isEmpty())
			userAddrsService.selectByIds(userAddrIds);
		
		for (int i = 0; i < list.size(); i++) {
			OrderExtWater item = list.get(i);
			OrderExtWaterListVo vo = new OrderExtWaterListVo();
			
			vo.setOrderId(item.getOrderId());
			vo.setOrderNo(item.getOrderNo());
			vo.setUserId(item.getUserId());
			
			Orders order = null;
			for (Orders tmpOrder : orders) {
				if (tmpOrder.getOrderId().equals(item.getOrderId())) {
					order = tmpOrder;
					break;
				}
			}
			
			if (order != null) {
				vo.setOrderStatus(order.getOrderStatus());
			}
			
			OrderPrices orderPrice = null;
			for (OrderPrices tmpOrderPrice : orderPrices) {
				if (tmpOrderPrice.getOrderId().equals(item.getOrderId())) {
					orderPrice = tmpOrderPrice;
					break;
				}
			}
			
			vo.setOrderMoney(new BigDecimal(0));
			vo.setOrderPay(new BigDecimal(0));
			//订单价格
			if (orderPrice != null) {
				vo.setOrderMoney(orderPrice.getOrderMoney());
				vo.setOrderPay(orderPrice.getOrderPay());
			}
			
			//服务地址
			vo.setAddrName("");
			Long addrId = order.getAddrId();
			UserAddrs userAddr = null;
			for (UserAddrs tmpUserAddr : userAddrs) {
				if (tmpUserAddr.getId().equals(addrId)) {
					userAddr = tmpUserAddr;
					break;
				}
			}
			
			if (userAddr != null) {
				vo.setAddrName(userAddr.getName() + " " + userAddr.getAddr());
			}
			
			
			//服务大类名称
			vo.setServiceTypeName("");
			Long serviceTypeId = order.getServiceTypeId();
			if (serviceTypeId > 0L) {
				PartnerServiceType serviceType = partnerServiceTypeService.selectByPrimaryKey(serviceTypeId);
				if (serviceType != null) {
					vo.setServiceTypeName(serviceType.getName());
				}
			}

			//服务价格商品名称
			vo.setServicePriceName("");
			vo.setImgUrl("");
			vo.setDisPrice(new BigDecimal(0));
			
			Long servicePriceId = item.getServicePriceId();
			PartnerServicePrice servicePrice = null;
			for (PartnerServicePrice tmpServicePrice : servicePrices) {
				if (tmpServicePrice.getServicePriceId().equals(servicePriceId)) {
					servicePrice = tmpServicePrice;
					break;
				}
			}
			
			if (servicePrice != null) {
				vo.setServicePriceName(servicePrice.getName());
				vo.setImgUrl(servicePrice.getImgUrl());
				vo.setDisPrice(servicePrice.getDisPrice());
			}
			
			vo.setServiceNum(item.getServiceNum());
//			System.out.println("order_id =" + order.getOrderId().toString() + " status = " + order.getOrderStatus().toString());
			vo.setOrderStatusName(OrderUtil.getOrderStausName(order.getOrderStatus()));
			vo.setAddTimeStr(TimeStampUtil.fromTodayStr(order.getAddTime() * 1000));
			
			vo.setIsDone(item.getIsDone());
			
			vo.setIsDoneTimeStr("");
			if (item.getIsDoneTime() > 0L) {
				vo.setIsDoneTimeStr(TimeStampUtil.fromTodayStr(item.getIsDoneTime() * 1000));
			}
			vo.setOrderExtStatus(item.getOrderExtStatus());
			result.add(vo);
		}
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByPage(OrderSearchVo searchVo,int pageNo, int pageSize) {
		 PageHelper.startPage(pageNo, pageSize);
		 
         List<OrderExtWater> list = orderExtWaterMapper.selectByListPage(searchVo);
         OrderExtWater item = null;
         for (int i = 0; i < list.size(); i++) {
        	 item = list.get(i);
        	 OrderExtWaterXcloudVo vo = this.getListXcloudVo(item);
        	 list.set(i, vo);
		}
         PageInfo result = new PageInfo(list);
        return result;
	}
	
	@Override
	public String getOrderExtra(Long orderId) {
		String orderExtra = "";
		OrderExtWater orderExtWater = selectByOrderId(orderId);
		if (orderExtWater != null) {
			Long servicePriceId = orderExtWater.getServicePriceId();
			
			PartnerServiceType servicePrice = partnerServiceTypeService.selectByPrimaryKey(servicePriceId);
			
			orderExtra+= servicePrice.getName() + ", ";
			orderExtra+= "数量:"+ orderExtWater.getServiceNum().toString();
			
		}
		
		return orderExtra;
	}
}