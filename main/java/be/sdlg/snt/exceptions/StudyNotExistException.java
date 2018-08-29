package be.sdlg.snt.exceptions;

public class StudyNotExistException extends Exception {
	public StudyNotExistException() {
		super("Study does not exist or cannot be null when creating SubjectData");
	}

}
