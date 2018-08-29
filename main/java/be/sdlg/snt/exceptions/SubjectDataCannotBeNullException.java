package be.sdlg.snt.exceptions;

public class SubjectDataCannotBeNullException extends Exception {
	public SubjectDataCannotBeNullException() {
		super("Subjectdata cannot be null when creating a studyeventdata entity");
	}
}
