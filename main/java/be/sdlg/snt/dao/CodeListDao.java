package be.sdlg.snt.dao;

import be.sdlg.snt.model.CodeList;

public class CodeListDao extends GenericDaoImpl<CodeList, Long>{
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.CodeList cl";
	}

}
