package com.simi.vo.card;

public class CardSearchVo {

	private Long cardId;
	
	private Long userId;
	
	private Long startTime;
	
	private Long endTime;
	
	private Short cardFrom;
	
	private Short userType;
	
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



}