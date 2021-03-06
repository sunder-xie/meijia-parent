package com.simi.vo.card;

import java.util.List;

public class CardSearchVo {

	private Long cardId;
	
	private Long userId;
	
	private List<Long> userIds;
	
	private Short period;
	
	private List<Short> periods;
	
	private Long startTime;
	
	private Long endTime;
	
	private Short cardFrom;
	
	private Short userType;
	
	private Short cardType;
	
	private Short status;
	
	private List<Short> statusIn;
	
	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Short getCardFrom() {
		return cardFrom;
	}

	public void setCardFrom(Short cardFrom) {
		this.cardFrom = cardFrom;
	}

	public Short getUserType() {
		return userType;
	}

	public void setUserType(Short userType) {
		this.userType = userType;
	}

	public Short getCardType() {
		return cardType;
	}

	public void setCardType(Short cardType) {
		this.cardType = cardType;
	}

	public Short getPeriod() {
		return period;
	}

	public void setPeriod(Short period) {
		this.period = period;
	}

	public List<Short> getPeriods() {
		return periods;
	}

	public void setPeriods(List<Short> periods) {
		this.periods = periods;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public List<Short> getStatusIn() {
		return statusIn;
	}

	public void setStatusIn(List<Short> statusIn) {
		this.statusIn = statusIn;
	}

	public List<Long> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Long> userIds) {
		this.userIds = userIds;
	}



}
