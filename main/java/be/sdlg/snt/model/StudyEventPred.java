package be.sdlg.snt.model;
/***
 * This class is not a part of the CDISC model, but it allows to create a graph structure
 * with the StudyEventRef entities, in order to create complex StudyEvent schedule depending of 
 * several predecessors and several successors
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table (name="STUDY_EVENT_PRED")
public class StudyEventPred {
	protected Long id;
	protected StudyEventRef studyEventRef;
	protected Long predecessorId;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="STUDY_EVENT_PRED_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Long getPredecessorId() {
		return predecessorId;
	}
	public void setPredecessorId(Long predecessorId) {
		this.predecessorId = predecessorId;
	}
	@ManyToOne
	public StudyEventRef getStudyEventRef() {
		return studyEventRef;
	}
	public void setStudyEventRef(StudyEventRef studyEventRef) {
		this.studyEventRef = studyEventRef;
	}
	
}
