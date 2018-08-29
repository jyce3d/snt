package be.sdlg.snt.dao;

import be.sdlg.snt.model.Question;

public class QuestionDao extends GenericDaoImpl<Question, Long>{
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.Question q";
	}


}
