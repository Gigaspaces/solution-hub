package com.gigaspaces.droolsintegration.model.facts;

import java.io.Serializable;
import java.util.Date;

public class Application implements Serializable {

    private static final long serialVersionUID = 2270264128413117612L;
	
	private Date dateApplied;
	private Date dateApproved;
    private Boolean processed;
	private Integer applicantId;
    private String applicantName;
    private Boolean applicantApproved;

    public Application() {}
    
    public Application(Boolean processed, Date dateApplied) {
    	this.processed = processed;
    	this.dateApplied = dateApplied;
    }
    
	public Date getDateApplied() {
		return dateApplied;
	}

	public void setDateApplied(Date dateApplied) {
		this.dateApplied = dateApplied;
	}

	public Integer getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(Integer applicantId) {
		this.applicantId = applicantId;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public Boolean getProcessed() {
		return processed;
	}

	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}

	public Boolean getApplicantApproved() {
		return applicantApproved;
	}

	public void setApplicantApproved(Boolean applicantApproved) {
		this.applicantApproved = applicantApproved;
	}

	public Date getDateApproved() {
		return dateApproved;
	}

	public void setDateApproved(Date dateApproved) {
		this.dateApproved = dateApproved;
	}

}