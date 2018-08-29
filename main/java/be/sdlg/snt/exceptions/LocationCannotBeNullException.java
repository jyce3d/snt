package be.sdlg.snt.exceptions;

public class LocationCannotBeNullException extends Exception {
	public LocationCannotBeNullException() {
		super("Location cannot be null and must exist");
	}

}
