package be.sdlg.snt.dao;

import be.sdlg.snt.model.CodeListItem;

public class CodeListItemDao extends GenericDaoImpl<CodeListItem, Long> {
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.CodeListItem cli";
	}

}
