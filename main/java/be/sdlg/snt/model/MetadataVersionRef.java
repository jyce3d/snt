package be.sdlg.snt.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table (name="METADATAVERSION_REFS")
public class MetadataVersionRef {
	protected Long id;
	protected Date effectiveDate;
	protected StudyLocation location;
	protected MetadataVersion metadataVersion;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="METADATAVERSIONREF_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Temporal(TemporalType.DATE)
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	@OneToOne
	public StudyLocation getLocation() {
		return location;
	}
	public void setLocation(StudyLocation location) {
		this.location = location;
	}
	@ManyToOne
	public MetadataVersion getMetadataVersion() {
		return metadataVersion;
	}
	public void setMetadataVersion(MetadataVersion metdataVersion) {
		this.metadataVersion = metdataVersion;
	}
	
}
