package be.sdlg.snt.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table (name="SNT_GRANTS")
public class Grant {
	public static final int INVESTIGATOR =1;
	public static final int DATA_ENTRY = 2;
	public static final int MONITOR = 3;
	public static final int UNBLIND = 4;
	
	protected Long id;
	protected Long value;
	protected DBProper proper;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="GRANT_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Basic
	public Long getValue() {
		return value;
	}
	public void setValue(Long value) {
		this.value = value;
	}
	@ManyToOne
	public DBProper getProper() {
		return proper;
	}
	public void setProper(DBProper proper) {
		this.proper = proper;
	}
	

}
