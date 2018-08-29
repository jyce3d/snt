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
import javax.persistence.Table;
import javax.persistence.OneToMany;

@Entity
@Table (name="LOCATIONS")
public class Location {
//	(Sponsor | Site | CRO | Lab | Other)
	public static final int LOCATION_TYPE_SPONSOR = 1;
	public static final int LOCATION_TYPE_SITE = 2;
	public static final int LOCATION_TYPE_CRO =3;
	public static final int LOCATION_TYPE_LAB = 4;
	public static final int LOCATION_TYPE_OTHER = 5;
	
	protected Long id;
	protected String name;
	protected Long locationType;
	protected Set<StudyLocation> studyLocation;
	protected Set<SubjectData> subjectDataList;

	
	// Non CDISC data
	protected String shortName;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="siteRef", fetch=FetchType.LAZY)
	public Set<SubjectData> getSubjectDataList() {
		if (subjectDataList == null)
			subjectDataList = new HashSet<SubjectData>(0);
		return subjectDataList;
	}
	public void setSubjectDataList(Set<SubjectData> subjectDataList) {
		this.subjectDataList = subjectDataList;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="LOCATION_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getLocationType() {
		return locationType;
	}
	public void setLocationType(Long locationType) {
		this.locationType = locationType;
	}
	@OneToMany (cascade = CascadeType.ALL, mappedBy="location", fetch=FetchType.LAZY)
	public Set<StudyLocation> getStudyLocation() {
		if (studyLocation == null)
			studyLocation = new HashSet<StudyLocation>(0);
		return studyLocation;
	}
	public void setStudyLocation(Set<StudyLocation> studyLocation) {
		this.studyLocation = studyLocation;
	}
	
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	

	
	
	
	
	

}
