package be.sdlg.snt.dao;

import be.sdlg.snt.model.StudyEventRef;

public class StudyEventRefDao extends GenericDaoImpl<StudyEventRef, Long>{
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.StudyEventRef sr";
	}

}
