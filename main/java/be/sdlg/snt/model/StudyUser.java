package be.sdlg.snt.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table (name="STUDY_USERS")
@PrimaryKeyJoinColumn(name="ADMIN_DATA_ID")
public class StudyUser extends AdminData {
	
	protected DBUser user;
	protected Set<DBProper> properList;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="studyUser", fetch=FetchType.LAZY)
	public Set<DBProper> getProperList() {
		if (properList==null) properList = new HashSet<DBProper>(0);
		return properList;
	}
	public void setProperList(Set<DBProper> properList) {
		this.properList = properList;
	}

	@ManyToOne
	public DBUser getUser() {
		return user;
	}
	public void setUser(DBUser user) {
		this.user = user;
	}
	

}
