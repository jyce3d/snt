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
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.OneToOne;


@Entity
@Table(name="tblCMLNKPtoO")
@PrimaryKeyJoinColumn(name="ADMIN_DATA_ID")
public class StudyLocation extends AdminData {
	
	protected Location location;
	protected Set<DBProper> properList;
	protected MetadataVersionRef metadataVersionRef;

	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="studyLocation", fetch=FetchType.LAZY)
	public Set<DBProper> getProperList() {
		if (properList == null) properList = new HashSet<DBProper>(0);
		return properList;
	}
	public void setProperList(Set<DBProper> properList) {
		this.properList = properList;
	}

	@ManyToOne
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	@OneToOne(cascade=CascadeType.ALL, mappedBy="location")
	public MetadataVersionRef getMetadataVersionRef() {
		return metadataVersionRef;
	}
	public void setMetadataVersionRef(MetadataVersionRef metadataVersionRef) {
		this.metadataVersionRef = metadataVersionRef;
	}
	
	
	

}
