package be.sdlg.snt.dao;

import be.sdlg.snt.model.FormDef;

public class FormDefDao extends GenericDaoImpl<FormDef, Long> {
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.FormDef fd";
	}

}
