package be.sdlg.snt.dao;

import be.sdlg.snt.model.ItemGroupDef;

public class ItemGroupDefDao extends GenericDaoImpl<ItemGroupDef, Long> {
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.ItemGroupDef itg";
	}


}
