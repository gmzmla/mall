package com.ruobilin.mall.entity;

import java.util.Date;

public class Sequence {
	private Integer id;
	private String name;
	private Integer type;
	private Long start;
	private Long end;
	private Long step;
	private Date modified;
	private Integer version;
	private Integer oldVersion;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getStart() {
		return start;
	}
	public void setStart(Long start) {
		this.start = start;
	}
	public Long getEnd() {
		return end;
	}
	public void setEnd(Long end) {
		this.end = end;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Long getStep() {
		return step;
	}
	public void setStep(Long step) {
		this.step = step;
	}
	public Integer getOldVersion() {
		return oldVersion;
	}
	public void setOldVersion(Integer oldVersion) {
		this.oldVersion = oldVersion;
	}
	
	
}
