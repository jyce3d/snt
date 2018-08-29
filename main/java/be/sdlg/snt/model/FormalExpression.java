package be.sdlg.snt.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name="FORMAL_EXPR")
public class FormalExpression {
	protected Long id;
	protected String formalExpression;
	protected String context;
	protected ConditionDef conditionDef;
	protected RangeCheck rangeCheck;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="FORMAL_EXPR_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Basic
	public String getFormalExpression() {
		return formalExpression;
	}
	public void setFormalExpression(String formalExpression) {
		this.formalExpression = formalExpression;
	}
	@Basic
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	@ManyToOne
	public ConditionDef getConditionDef() {
		return conditionDef;
	}
	public void setConditionDef(ConditionDef conditionDef) {
		this.conditionDef = conditionDef;
	}
	@ManyToOne
	public RangeCheck getRangeCheck() {
		return rangeCheck;
	}
	public void setRangeCheck(RangeCheck rangeCheck) {
		this.rangeCheck = rangeCheck;
	}
	
	
	
}
