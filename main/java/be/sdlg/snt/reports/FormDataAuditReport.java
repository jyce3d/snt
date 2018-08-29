package be.sdlg.snt.reports;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import be.sdlg.snt.ControllerAdvice;
import be.sdlg.snt.model.FormData;
import be.sdlg.snt.model.FormDataAudit;
import be.sdlg.snt.model.ItemGroupData;
import be.sdlg.snt.model.ItemGroupDataAudit;

public class FormDataAuditReport {
	public class FormDataAuditItem {
		public String timeStamp;
		public String oldStatus;
		public String newStatus;
		public String reasonForChange;
		public String oldFormRepeatKey;
		public String newFormRepeatKey;
		public String oldActive;
		public String newActive;
		public String userName;
		public String locationName;
		public String signatorName;
		public String signLocation;
		public String signStatus;

		public List<ItemGroupDataAuditItem> itemGroupDataAuditItemList;
		public FormDataAuditItem() {
	
		}
		public String getTimeStamp() {
			return timeStamp;
		}
		public void setTimeStamp(String timeStamp) {
			this.timeStamp = timeStamp;
		}
		public String getOldStatus() {
			return oldStatus;
		}
		public void setOldStatus(String oldStatus) {
			this.oldStatus = oldStatus;
		}
		public String getNewStatus() {
			return newStatus;
		}
		public void setNewStatus(String newStatus) {
			this.newStatus = newStatus;
		}
		public String getReasonForChange() {
			return reasonForChange;
		}
		public void setReasonForChange(String reasonForChange) {
			this.reasonForChange = reasonForChange;
		}
		public String getOldFormRepeatKey() {
			return oldFormRepeatKey;
		}
		public void setOldFormRepeatKey(String oldFormRepeatKey) {
			this.oldFormRepeatKey = oldFormRepeatKey;
		}
		public String getNewFormRepeatKey() {
			return newFormRepeatKey;
		}
		public void setNewFormRepeatKey(String newFormRepeatKey) {
			this.newFormRepeatKey = newFormRepeatKey;
		}
		public String getOldActive() {
			return oldActive;
		}
		public void setOldActive(String oldActive) {
			this.oldActive = oldActive;
		}
		public String getNewActive() {
			return newActive;
		}
		public void setNewActive(String newActive) {
			this.newActive = newActive;
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
		public String getSignatorName() {
			return signatorName;
		}
		public void setSignatorName(String signatorName) {
			this.signatorName = signatorName;
		}
		public String getSignLocation() {
			return signLocation;
		}
		public void setSignLocation(String signLocation) {
			this.signLocation = signLocation;
		}
		public String getSignStatus() {
			return signStatus;
		}
		public void setSignStatus(String signStatus) {
			this.signStatus = signStatus;
		}
		
	}
	protected List<FormDataAuditItem> formDataAuditItemList;
	public String itemGroupSection;
	public String itemSection;
	public String formSection;
	public FormDataAuditReport( List<FormDataAudit> formDataAuditList, Locale locale, Map<Long, String> statuses, Map<Long, String> actives, Map<Long, String> signSatuses, Map<String, String> formHeaders,
			Map<String, String> itemGroupDataHeaders, Map<String, String> itemHeaders, String formSection, String itemGroupSection, String itemSection) {
		this.itemGroupSection = itemGroupSection;
		this.itemSection = itemSection;
		this.formSection = formSection;
		formDataAuditItemList = new ArrayList<FormDataAuditItem>();
		FormDataAuditItem fdh = new FormDataAuditItem();
		fdh.timeStamp = formHeaders.get("timeStamp");
		fdh.userName = formHeaders.get("userName");
		fdh.locationName = formHeaders.get("locationName");
		fdh.reasonForChange = formHeaders.get("reasonForChange");
		fdh.oldStatus = formHeaders.get("oldStatus");
		fdh.newStatus = formHeaders.get("newStatus");
		fdh.signatorName = formHeaders.get("signatorName");
		fdh.signLocation = formHeaders.get("signLocation");
		fdh.signStatus = formHeaders.get("signStatus");
		
		formDataAuditItemList.add(fdh);
		for(FormDataAudit formDataAudit : formDataAuditList) {
			FormDataAuditItem fdi = new FormDataAuditItem();
			FormData formData = (FormData) formDataAudit.getClinicalData();
			fdi.timeStamp = ControllerAdvice.getFormattedDate(formDataAudit.getDateTimeStamp(), locale, true);
			fdi.oldStatus= statuses.get(formDataAudit.getOldStatus());
			fdi.newStatus = statuses.get(formDataAudit.getNewStatus());
			fdi.reasonForChange = formDataAudit.getReasonForChange();
			fdi.oldFormRepeatKey = formDataAudit.getOldFormRepeatKey();
			fdi.newFormRepeatKey = formDataAudit.getNewFormRepeatKey();
			fdi.oldActive = actives.get(formDataAudit.getOldActive());
			fdi.newActive = actives.get(formDataAudit.getNewActive());
			fdi.userName = formDataAudit.getUserName();
			fdi.locationName = formDataAudit.getLocationName();
			fdi.signatorName = formDataAudit.getSignator();
			fdi.signLocation = formDataAudit.getSignLocation();
			fdi.signStatus = signSatuses.get(formDataAudit.getSignStatus());
			ItemGroupDataAuditItem igdah = new ItemGroupDataAuditItem();
			igdah.timeStamp = formHeaders.get("timeStamp");
			igdah.userName = formHeaders.get("userName");
			igdah.locationName = formHeaders.get("locationName");
			igdah.itemGroupRepeatOldKey = itemGroupDataHeaders.get("itemGroupRepeatOldKey");
			igdah.itemGroupRepeatNewKey = itemGroupDataHeaders.get("itemGroupRepeatNewKey");
			fdi.itemGroupDataAuditItemList = new ArrayList<ItemGroupDataAuditItem>();
			fdi.itemGroupDataAuditItemList.add(igdah);
			for (ItemGroupDataAudit igda : formDataAudit.getItemGroupDataAuditList()) {
				ItemGroupDataAuditItem igdaItem = new ItemGroupDataAuditItem(igda, formHeaders, itemHeaders, locale);
				fdi.itemGroupDataAuditItemList.add(igdaItem);
			}
			
			formDataAuditItemList.add(fdi);
			
		}
		
		
	}
	
	public String getItemGroupSection() {
		return itemGroupSection;
	}

	public void setItemGroupSection(String itemGroupSection) {
		this.itemGroupSection = itemGroupSection;
	}

	public String getItemSection() {
		return itemSection;
	}

	public void setItemSection(String itemSection) {
		this.itemSection = itemSection;
	}

	public List<FormDataAuditItem> getFormDataAuditItemList() {
		return formDataAuditItemList;
	}
	public void setFormDataAuditItemList(
			List<FormDataAuditItem> formDataAuditItemList) {
		this.formDataAuditItemList = formDataAuditItemList;
	}

}
