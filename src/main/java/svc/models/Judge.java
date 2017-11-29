package svc.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


public class Judge 
{
	private Long id;
	private String judge;
	private Long court_id;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getJudge() {
		return judge;
	}
	public void setJudge(String judge) {
		this.judge = judge;
	}
	public long getCourt_id() {
		return court_id;
	}
	public void setCourt_id(Long court_id) {
		this.court_id = court_id;
	}
}
