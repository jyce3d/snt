package be.sdlg.snt.exceptions;


public class ItemGroupDefCannotBeNullException extends Exception {
	public ItemGroupDefCannotBeNullException() {
		super ("Item group def cannot be null when updating/saving a formData");
	} 

}
