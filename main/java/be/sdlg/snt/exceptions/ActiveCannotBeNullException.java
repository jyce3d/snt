package be.sdlg.snt.exceptions;

public class ActiveCannotBeNullException extends Exception {
	public ActiveCannotBeNullException() {
		super("Active field cannot be null");
	}

}