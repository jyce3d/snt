package be.sdlg.snt.exceptions;

public class FormDefCannotBeNullException extends Exception {
	public FormDefCannotBeNullException() {
		super ("Form def cannot be null when creating a FormData");
	}
}
