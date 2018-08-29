package be.sdlg.snt.dao;

import be.sdlg.snt.model.FormRef;

public class FormRefDao extends GenericDaoImpl<FormRef, Long> {
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.FormRef fr";
	}


}
