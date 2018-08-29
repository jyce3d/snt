package be.sdlg.snt.exceptions;


public class StudyEventDataCannotBeNullException extends Exception{
	public StudyEventDataCannotBeNullException() {
		super ("StudyEvent Data cannot be null when creating Form Data");
	}
}
