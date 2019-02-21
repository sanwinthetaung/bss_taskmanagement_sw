package com.bss.tm.model;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2018289148322407090L;

	private Long id;
	private String title;
	private Integer status; // 0: not complete, 1: complete
	private Boolean isDeleted;
	private Long taskListId;
	private Date created_dt;
	private Date updated_dt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Long getTaskListId() {
		return taskListId;
	}

	public void setTaskListId(Long taskListId) {
		this.taskListId = taskListId;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getCreated_dt() {
		return created_dt;
	}

	public void setCreated_dt(Date created_dt) {
		this.created_dt = created_dt;
	}

	public Date getUpdated_dt() {
		return updated_dt;
	}

	public void setUpdated_dt(Date updated_dt) {
		this.updated_dt = updated_dt;
	}
	
	

}
