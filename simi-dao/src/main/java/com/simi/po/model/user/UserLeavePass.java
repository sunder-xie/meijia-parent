package com.simi.po.model.user;

public class UserLeavePass {
    private Long id;

    private Long leaveId;

    private Long companyId;

    private Long userId;

    private Long passUserId;

    private String remarks;

    private Short passStatus;

    private Long addTime;

    private Long updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(Long leaveId) {
        this.leaveId = leaveId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPassUserId() {
        return passUserId;
    }

    public void setPassUserId(Long passUserId) {
        this.passUserId = passUserId;
    }

    public Short getPassStatus() {
        return passStatus;
    }

    public void setPassStatus(Short passStatus) {
        this.passStatus = passStatus;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}