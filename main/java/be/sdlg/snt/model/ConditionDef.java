package be.sdlg.snt.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table (name="CONDITIONS_DEF")
@PrimaryKeyJoinColumn(name="CLINICAL_DEF_ID")
public class ConditionDef extends ClinicalDef {
	protected Set<FormalExpression> formalExpressionList;
	protected ClinicalRef clinicalRef;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="conditionDef", fetch=FetchType.LAZY)
	public Set<FormalExpression> getFormalExpressionList() {
		return formalExpressionList;
	}

	public void setFormalExpressionList(Set<FormalExpression> formalExpressionList) {
		this.formalExpressionList = formalExpressionList;
	}
	
	@OneToOne
	public ClinicalRef getClinicalRef() {
		return clinicalRef;
	}

	public void setClinicalRef(ClinicalRef clinicalRef) {
		this.clinicalRef = clinicalRef;
	}
	
}
