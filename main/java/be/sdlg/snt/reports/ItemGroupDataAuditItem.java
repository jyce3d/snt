package be.sdlg.snt.reports;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import be.sdlg.snt.ControllerAdvice;
import be.sdlg.snt.model.ItemDataAudit;
import be.sdlg.snt.model.ItemGroupData;
import be.sdlg.snt.model.ItemGroupDataAudit;


	public class ItemGroupDataAuditItem {
		
		public String timeStamp;
		public String userName;
		public String locationName;
		public String itemGroupRepeatNewKey;
		public String itemGroupRepeatOldKey;
		public String itemGroupName;
		public List<ItemDataAuditItem> itemDataAuditList;
		public ItemGroupDataAuditItem(ItemGroupDataAudit itga, Map<String, String> formHeaders, Map<String, String> itemHeaders, Locale locale ) {
			this.timeStamp = ControllerAdvice.getFormattedDate(itga.getDateTimeStamp(), locale, true);
			this.userName = itga.getUserName();
			this.locationName = itga.getLocationName();
			
			this.itemGroupRepeatNewKey = itga.getNewItemGroupRepeatKey();
			this.itemGroupRepeatOldKey = itga.getOldItemGroupRepeatKey();
			
			ItemGroupData itg = (ItemGroupData) itga.getClinicalData();
			this.itemGroupName = itg.getItemGroupDef().getName();
			
			// Populate item
			ItemDataAuditItem idah = new ItemDataAuditItem();
			idah.timeStamp = formHeaders.get("timeStamp");
			idah.userName = formHeaders.get("userName");
			idah.locationName = formHeaders.get("locationName");
			
			idah.itemName = itemHeaders.get("itemName");
			idah.codeValueNew = itemHeaders.get("codeValue");
			idah.valueNew = itemHeaders.get("value");
			idah.codeValueOld = itemHeaders.get("oldCodeValue");
			idah.valueOld = itemHeaders.get("oldValue");
			
			this.itemDataAuditList = new ArrayList<ItemDataAuditItem>();
			this.itemDataAuditList.add(idah);
			for (ItemDataAudit ida : itga.getItemDataAuditList()) {
				ItemDataAuditItem idaItem = new ItemDataAuditItem(ida, locale);
				this.itemDataAuditList.add(idaItem);
			}
			
			
			
		}
		public ItemGroupDataAuditItem() {
			
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
		public String getItemGroupRepeatNewKey() {
			return itemGroupRepeatNewKey;
		}
		public void setItemGroupRepeatNewKey(String itemGroupRepeatNewKey) {
			this.itemGroupRepeatNewKey = itemGroupRepeatNewKey;
		}
		public String getItemGroupRepeatOldKey() {
			return itemGroupRepeatOldKey;
		}
		public void setItemGroupRepeatOldKey(String itemGroupRepeatOldKey) {
			this.itemGroupRepeatOldKey = itemGroupRepeatOldKey;
		}
		
	}


