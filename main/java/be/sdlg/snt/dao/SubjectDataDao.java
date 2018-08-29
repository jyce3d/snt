package be.sdlg.snt.dao;

import java.util.List;

import org.hibernate.Query;

import be.sdlg.snt.model.DBUser;
import be.sdlg.snt.model.Grant;
import be.sdlg.snt.model.Study;
import be.sdlg.snt.model.SubjectData;

public class SubjectDataDao extends GenericDaoImpl<SubjectData, Long> {
	
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.SubjectData sd";
	}

	public int getSubjectKey(Study study, String key) {
		Query query = getCurrentSession().createQuery("from be.sdlg.snt.model.SubjectData sd where sd.subjectKey=:key and sd.study.id = :studyId order by sd.subjectKey");
		query.setParameter("key", key);
		query.setParameter("studyId",study.getId());
		return query.list().size();
	}
	
//	public List<SubjectData> findSubjectDataByStudyByUser(Study study, DBUser user) {
//		Query query = getCurrentSession().createQuery("from be.sdlg.snt.model.SubjectData sd where sd.study.id = :studyId and sd.investigatorRef.id =:userId ");
//		query.setParameter("studyId", study.getId());
//		query.setParameter("userId",user.getId());
//		return query.list();
//
//	}
	public List<SubjectData> findSubjectDataByStudyByUser(Long studyId, Long userId) {
		Query query = getCurrentSession().createQuery("select distinct sd from be.sdlg.snt.model.SubjectData sd where (sd.study.id=:studyId and sd.siteRef.id in (select dbp.studyLocation.location.id  from be.sdlg.snt.model.DBProper dbp inner join dbp.grantList g where g.value" +
				" IN (:dataEntry, :monitor, :unblind) and dbp.studyUser.study.id = :studyId and dbp.studyUser.user.id = :userId ) or ( sd.investigatorRef.id = :userId and sd.study.id=:studyId))  order by sd.subjectKey ASC ");
		query.setParameter("studyId", studyId);
		query.setParameter("userId",userId);
		query.setParameter("dataEntry", new Long(Grant.DATA_ENTRY));
		query.setParameter("monitor", new Long(Grant.MONITOR));
		query.setParameter("unblind", new Long(Grant.UNBLIND));
		
		return query.list();

	}
	public List<SubjectData> findSubjectDataByStudyForReport(Long studyId) {
		Query query = getCurrentSession().createQuery("select distinct sd from be.sdlg.snt.model.SubjectData sd where (sd.study.id=:studyId)  order by sd.id ASC ");
		query.setParameter("studyId", studyId);
		return query.list();

	}
	
	

}
