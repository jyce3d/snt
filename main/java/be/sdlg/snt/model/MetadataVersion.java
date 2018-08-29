package be.sdlg.snt.model;
/***
 * MetadataVersion packages PROTOCOL XML entity and its content = {ALIASes, StudyEventDefs, description}
 */
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
@Table (name="METADATAVERSIONS")
public class MetadataVersion {
	protected Long id;
	protected Study study;
	protected Set<MetadataVersionRef> metadataVersionRefList;
	protected String name;	
	protected Long version; // Not required by CDISC but usefull to determine which is the current version of the metadataversion.
	protected Protocol protocol;
	protected Set<ClinicalDef> clinicalDefList;


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="METADATAVERSION_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@ManyToOne
	public Study getStudy() {
		return study;
	}
	public void setStudy(Study study) {
		this.study = study;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="metadataVersion", fetch=FetchType.LAZY)
	public Set<MetadataVersionRef> getMetadataVersionRefList() {
		if (metadataVersionRefList == null) metadataVersionRefList = new HashSet<MetadataVersionRef>(0);
		return metadataVersionRefList;
	}
	public void setMetadataVersionRefList(
			Set<MetadataVersionRef> metadataVersionRefList) {
		this.metadataVersionRefList = metadataVersionRefList;
	}
	@Basic
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	@OneToOne(cascade = CascadeType.ALL, mappedBy="metadataVersion", fetch=FetchType.LAZY)
	public Protocol getProtocol() {
		return protocol;
	}
	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="metadataVersion", fetch=FetchType.LAZY)
	public Set<ClinicalDef> getClinicalDefList() {
		if (clinicalDefList==null) clinicalDefList = new HashSet<ClinicalDef>(0);
		return clinicalDefList;
	}
	public void setClinicalDefList(Set<ClinicalDef> clinicalDefList) {
		this.clinicalDefList = clinicalDefList;
	}
	

	
	

}
