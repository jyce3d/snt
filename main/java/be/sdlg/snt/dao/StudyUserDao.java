package be.sdlg.snt.dao;

import org.hibernate.Query;

import be.sdlg.snt.model.DBUser;
import be.sdlg.snt.model.Study;
import be.sdlg.snt.model.StudyUser;

public class StudyUserDao extends GenericDaoImpl<StudyUser, Long> {
	
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.StudyUser s";
	}
	public StudyUser get(Study study, DBUser user) throws Exception {
		Query query = getCurrentSession().createQuery("from be.sdlg.snt.model.StudyUser su  where su.user.id = :userId and su.study.id = :studyId");
		query.setParameter("userId",user.getId());
		query.setParameter("studyId",study.getId());
		int size = query.list().size();
		if (size == 0) return null;
		if (size>1) throw new Exception("Study user must be unique for given user and study.");
		return (StudyUser) query.list().get(0);		
	}

}
