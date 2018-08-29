package be.sdlg.snt.reports;

import java.util.Locale;


import be.sdlg.snt.ControllerAdvice;
import be.sdlg.snt.model.ItemData;
import be.sdlg.snt.model.ItemDataAudit;

public class ItemDataAuditItem {
	public String timeStamp;
	public String userName;
	public String locationName;
	public String itemName;
	
	public String codeValueNew;
	public String valueNew;
	public String codeValueOld;
	public String valueOld;
	
	public ItemDataAuditItem (ItemDataAudit ida, Locale locale) {
		this.timeStamp = ControllerAdvice.getFormattedDate(ida.getDateTimeStamp(), locale, true);
		this.userName = ida.getUserName();
		this.locationName = ida.getLocationName();
		this.codeValueNew = ida.getNewCodeListValue();
		this.valueNew = ida.getNewValue();
		this.codeValueOld = ida.getOldCodeListValue();
		this.valueOld = ida.getOldValue();
		ItemData  id = (ItemData) ida.getClinicalData();
		this.itemName = id.getItemDef().getName();

	}
	public ItemDataAuditItem() {
		
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getCodeValueNew() {
		return codeValueNew;
	}

	public void setCodeValueNew(String codeValueNew) {
		this.codeValueNew = codeValueNew;
	}

	public String getValueNew() {
		return valueNew;
	}

	public void setValueNew(String valueNew) {
		this.valueNew = valueNew;
	}

	public String getCodeValueOld() {
		return codeValueOld;
	}

	public void setCodeValueOld(String codeValueOld) {
		this.codeValueOld = codeValueOld;
	}

	public String getValueOld() {
		return valueOld;
	}

	public void setValueOld(String valueOld) {
		this.valueOld = valueOld;
	}
	

}
