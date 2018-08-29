package be.sdlg.snt.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table (name="PROTOCOL")
@PrimaryKeyJoinColumn(name="CLINICAL_DEF_ID")
public class Protocol extends ClinicalDef {
	protected MetadataVersion metadataVersion;
	protected Set<StudyEventRef> studyEventRefList;

	@OneToOne
	public MetadataVersion getMetadataVersion() {
		return metadataVersion;
	}
	public void setMetadataVersion(MetadataVersion metadataVersion) {
		this.metadataVersion = metadataVersion;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="protocol", fetch=FetchType.LAZY)	
	public Set<StudyEventRef> getStudyEventRefList() {
		if (studyEventRefList == null) studyEventRefList = new HashSet<StudyEventRef>(0);
		return studyEventRefList;
	}
	public void setStudyEventRefList(Set<StudyEventRef> studyEventRefList) {
		this.studyEventRefList = studyEventRefList;
	}
	
	
}
