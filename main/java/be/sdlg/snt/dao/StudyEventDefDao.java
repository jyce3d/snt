package be.sdlg.snt.dao;

import be.sdlg.snt.model.StudyEventDef;

public class StudyEventDefDao extends GenericDaoImpl<StudyEventDef, Long> {
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.StudyEventDef sd";
	}


}
