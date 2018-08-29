package be.sdlg.snt.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table (name="STUDIES")
public class Study {
	protected static final int UNIT_DAY =1;
	protected static final int UNIT_WEEK =2;
	protected static final int UNIT_MONTH = 3;
	
	protected Long id;

	protected Set<MetadataVersion> metadataVersionList;
	protected Set<ClinicalData> clinicalDataList;
	protected Set<AdminData> adminDataList;
	// Non CDISC
	protected Date studyStartDate;
	protected String studyName;
	protected String studyDescription;
	protected Long studyEventUnit;
	
	//Transient
	protected int subjectCount;
	
	@Column(name="STUDYEVENT_UNIT")
	public Long getStudyEventUnit() {
		return studyEventUnit;
	}

	public void setStudyEventUnit(Long studyEventUnit) {
		this.studyEventUnit = studyEventUnit;
	}

	@Transient
	public int getSubjectCount() {
		return subjectCount;
	}

	public void setSubjectCount(int subjectCount) {
		this.subjectCount = subjectCount;
	}

	@Temporal(TemporalType.DATE)
	public Date getStudyStartDate() {
		return studyStartDate;
	}

	public void setStudyStartDate(Date studyStartDate) {
		this.studyStartDate = studyStartDate;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="STUDY_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@OneToMany (cascade = CascadeType.ALL, mappedBy="study", fetch=FetchType.LAZY)
	public Set<MetadataVersion> getMetadataVersionList() {
		if (metadataVersionList == null) metadataVersionList = new HashSet<MetadataVersion>(0);
		return metadataVersionList;
	}

	public void setMetadataVersionList(Set<MetadataVersion> metadataVersionList) {
		this.metadataVersionList = metadataVersionList;
	}
	@Basic
	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}
	@Basic
	public String getStudyDescription() {
		return studyDescription;
	}

	public void setStudyDescription(String studyDescription) {
		this.studyDescription = studyDescription;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="study", fetch=FetchType.LAZY)
	public Set<ClinicalData> getClinicalDataList() {
		if (clinicalDataList == null)
			clinicalDataList = new HashSet<ClinicalData>(0);
		return clinicalDataList;
	}

	public void setClinicalDataList(Set<ClinicalData> clinicalDataList) {
		this.clinicalDataList = clinicalDataList;
	}
	@OneToMany(cascade=CascadeType.ALL, mappedBy="study", fetch=FetchType.LAZY)
	public Set<AdminData> getAdminDataList() {
		if (adminDataList == null) adminDataList = new HashSet<AdminData>(0);
		return adminDataList;
	}

	public void setAdminDataList(Set<AdminData> adminDataList) {
		this.adminDataList = adminDataList;
	}

	

}
