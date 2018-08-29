package be.sdlg.snt.dao;

import be.sdlg.snt.model.Decode;

public class DecodeDao extends GenericDaoImpl<Decode, Long> {
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.Decode d";
	}


}
