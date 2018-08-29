
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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
/***
 * Clinical data is not directly linked to the CDISC ClinicalData entity. This class is a base class that manages Audit-trail and signatures
 * for all kind of subject data.
 * @author Jean-Yves CŽlis
 * @version 1.0 June 2013
 *
 */
@Entity
@Table(name="CLINICAL_DATA")
@Inheritance(strategy = InheritanceType.JOINED)
public class ClinicalData {
	public static final int ACTIVE_YES = 1;
	public static final int ACTIVE_NO = 0;

	protected Long id;
	protected Study study;
	protected Set<Annotation> annotationList;
	protected Set<Signature> signatureList;
	protected Set<AuditRecord> auditRecordList;
	// outside CDISC
	protected Long active;
	protected Long oldActive;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CLINICAL_DATA_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@OneToMany(cascade=CascadeType.ALL, mappedBy="clinicalData", fetch=FetchType.LAZY)
	public Set<Annotation> getAnnotationList() {
		if (annotationList == null) 
			annotationList = new HashSet<Annotation>(0);
		return annotationList;
	}
	public void setAnnotationList(Set<Annotation> annotationList) {
		this.annotationList = annotationList;
	}
	@ManyToOne
	public Study getStudy() {
		return study;
	}
	public void setStudy(Study study) {
		this.study = study;
	}
	@OneToMany(cascade=CascadeType.ALL, mappedBy="clinicalData", fetch=FetchType.LAZY)
	public Set<Signature> getSignatureList() {
		if (signatureList == null)
			signatureList = new HashSet<Signature>(0);
		return signatureList;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="clinicalData", fetch=FetchType.LAZY)
	public Set<AuditRecord> getAuditRecordList() {
		if (auditRecordList==null)
			auditRecordList = new HashSet<AuditRecord>(0);
		return auditRecordList;
	}
	public void setAuditRecordList(Set<AuditRecord> auditRecordList) {
		this.auditRecordList = auditRecordList;
	}
	public void setSignatureList(Set<Signature> signatureList) {
		this.signatureList = signatureList;
	}
	public Long getActive() {
		return active;
	}
	public void setActive(Long active) {
		this.active = active;
	}
	@Transient
	public Long getOldActive() {
		return oldActive;
	}
	public void setOldActive(Long oldActive) {
		this.oldActive = oldActive;
	}

	
	

}
