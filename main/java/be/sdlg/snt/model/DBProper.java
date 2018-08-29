package be.sdlg.snt.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="tblTMlnkPtoP")
public class DBProper {
	protected Long id;
	protected StudyUser studyUser;
	protected StudyLocation studyLocation;
	protected Set<Grant> grantList;
	@OneToMany (cascade=CascadeType.ALL, mappedBy="proper", orphanRemoval=true, fetch=FetchType.LAZY)
	public Set<Grant> getGrantList() {
		if (grantList == null) grantList = new HashSet<Grant>(0);
		return grantList;
	}
	public void setGrantList(Set<Grant> grantList) {
		this.grantList = grantList;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="PROPER_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@ManyToOne
	public StudyUser getStudyUser() {
		return studyUser;
	}

	public void setStudyUser(StudyUser studyUser) {
		this.studyUser = studyUser;
	}
	@ManyToOne
	public StudyLocation getStudyLocation() {
		return studyLocation;
	}
	public void setStudyLocation(StudyLocation studyLocation) {
		this.studyLocation = studyLocation;
	}
	
	
}
