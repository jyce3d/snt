package be.sdlg.snt.dao;

import be.sdlg.snt.model.ItemGroupData;

public class ItemGroupDataDao extends GenericDaoImpl<ItemGroupData, Long> {
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.ItemGroupData igd";
	}

}
