package be.sdlg.snt.controllers;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import be.sdlg.snt.AdministrationSvc;
import be.sdlg.snt.EDCSvc;
import be.sdlg.snt.model.AuditRecord;
import be.sdlg.snt.model.ClinicalData;
import be.sdlg.snt.model.ClinicalRef;
import be.sdlg.snt.model.CodeList;
import be.sdlg.snt.model.CodeListItem;
import be.sdlg.snt.model.DBUser;
import be.sdlg.snt.model.FormData;
import be.sdlg.snt.model.FormDef;
import be.sdlg.snt.model.Grant;
import be.sdlg.snt.model.ItemData;
import be.sdlg.snt.model.ItemDef;
import be.sdlg.snt.model.ItemGroupData;
import be.sdlg.snt.model.ItemGroupDef;
import be.sdlg.snt.model.ItemGroupRef;
import be.sdlg.snt.model.ItemRef;
import be.sdlg.snt.model.SignatureDef;
import be.sdlg.snt.model.StudyEventData;
import be.sdlg.snt.model.StudyEventDef;
import be.sdlg.snt.model.SubjectData;

@Controller
@Transactional
@RequestMapping("secure/formData.htm")
public class FormDataController {
	private static final Logger logger = LoggerFactory
			.getLogger(FormDataController.class);
	

	public class CodeListItemMVC {
		protected Long id;
		protected String codedValue;
		protected String decode;
		
		public CodeListItemMVC(CodeListItem codeListItem, Locale locale) {
			this.id = codeListItem.getId();
			this.codedValue = codeListItem.getCodedValue();
			this.decode = administrationService.getDecode(codeListItem.getDecode(), locale);
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getCodedValue() {
			return codedValue;
		}

		public void setCodedValue(String codedValue) {
			this.codedValue = codedValue;
		}

		public String getDecode() {
			return decode;
		}

		public void setDecode(String decode) {
			this.decode = decode;
		}
		
	}
	public class CodeListMVC {
		protected Long id;
		protected String name;
		protected String description;
		protected List<CodeListItemMVC> codeListItems;
		public CodeListMVC(CodeList codeList, Locale locale) {
			this.id = codeList.getId();
			this.name = codeList.getName();
			this.description = administrationService.getDescription(codeList.getDescription(), locale);
			codeListItems = new ArrayList<CodeListItemMVC>();
			for (CodeListItem codeListItem : codeList.getCodeListItems()) {
				CodeListItemMVC cdimvc = new CodeListItemMVC(codeListItem, locale);
				codeListItems.add(cdimvc);
			}
			
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public List<CodeListItemMVC> getCodeListItems() {
			return codeListItems;
		}
		public void setCodeListItems(List<CodeListItemMVC> codeListItems) {
			this.codeListItems = codeListItems;
		}
		
		
	}
	public class ItemDefMVC {
		protected String name;
		protected String question;
		protected Long id;
		protected Long order;
		protected Long dataType;
		protected CodeListMVC codeList;
		protected String required;
		protected String value;
		public ItemDefMVC(ItemRef itemRef, ItemData itemData, Locale locale) {
			ItemDef itemDef = itemRef.getItemDef();
			this.name = itemDef.getName();
			this.id = itemDef.getId();
			this.question = administrationService.getQuestion(itemDef.getQuestion(), locale);
			if( itemDef.getCodeList() !=null)
				codeList = new CodeListMVC(itemDef.getCodeList(), locale);
			this.dataType = itemDef.getDataType();
			required = itemRef.getMandatory() == ClinicalRef.MANDATORY_YES ? "t" : "f";
			order = itemRef.getOrderNumber();
			value = "";	
			if (itemData != null )
				value = itemData.getItemDef().getCodeList() !=null ? itemData.getCodeListValue() : itemData.getValue();
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getQuestion() {
			return question;
		}
		public void setQuestion(String question) {
			this.question = question;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public CodeListMVC getCodeList() {
			return codeList;
		}
		public void setCodeList(CodeListMVC codeList) {
			this.codeList = codeList;
		}
		public Long getDataType() {
			return dataType;
		}
		public void setDataType(Long dataType) {
			this.dataType = dataType;
		}
		public Long getOrder() {
			return order;
		}
		public void setOrder(Long order) {
			this.order = order;
		}
		public String getRequired() {
			return required;
		}
		public void setRequired(String required) {
			this.required = required;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
	
	}
	public class ItemGroupDefMVC {
		protected String name;
		protected String description;
		protected Long id;
		protected Long order;
		protected List<ItemDefMVC> itemDefList;
		public ItemGroupDefMVC (ItemGroupDef itemGroupDef, ItemGroupData itemGroupData, Locale locale) {
			this.name = itemGroupDef.getName();
			this.id = itemGroupDef.getId();
			this.description = administrationService.getDescription(itemGroupDef.getDescription(), locale);
			itemDefList = new ArrayList<ItemDefMVC>();
			ItemData itemData = null;
			
			for (ItemRef ir : itemGroupDef.getItemRefList()) {
				if (itemGroupData != null) {
					for (ItemData idData : itemGroupData.getItemDataList() ) {
						if (idData.getItemDef().getId().equals(ir.getItemDef().getId())) {
							itemData = idData;
							break;
						}
					}
				}
				ItemDefMVC idmvc = new ItemDefMVC(ir, itemData, locale);
				idmvc.setOrder(ir.getOrderNumber());
				itemDefList.add(idmvc);
			}		
			Collections.sort(itemDefList, new Comparator<ItemDefMVC>() {
				public int compare(ItemDefMVC i1, ItemDefMVC i2) {
					if (i1.getOrder() == i2.getOrder()) return 0;
					return i1.order< i2.order ? -1 : 1;
				}
			});
		}
		
		public Long getOrder() {
			return order;
		}

		public void setOrder(Long order) {
			this.order = order;
		}

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public List<ItemDefMVC> getItemDefList() {
			return itemDefList;
		}
		public void setItemDefList(List<ItemDefMVC> itemDefList) {
			this.itemDefList = itemDefList;
		}
		
	}
	public class FormDefMVC { 
		public String name;
		public String description;
		public Long id;
		public boolean dirty;
		public List<ItemGroupDefMVC> itemGroupDefList;
		
		public FormDefMVC(FormDef formDef, FormData formData, Locale locale) {
			this.name = formDef.getName();
			this.description = administrationService.getDescription(formDef.getDescription(), locale);
			this.id = formDef.getId();
			this.dirty = (formData == null ) ? false : true;
			itemGroupDefList = new ArrayList<ItemGroupDefMVC>();
			ItemGroupData itemGroupData = null;
	
			for (ItemGroupRef igr : formDef.getItemGroupRefList()) {
				if (formData != null ) {
					for (ItemGroupData itgData : formData.getItemGroupDataList()) {
						if (itgData.getItemGroupDef().getId().equals(igr.getItemGroupDef().getId())) {
							itemGroupData = itgData;
		
							break;
						}
					}
				}
				ItemGroupDefMVC itdmvc = new ItemGroupDefMVC(igr.getItemGroupDef(), itemGroupData, locale);
				itdmvc.setOrder(igr.getOrderNumber());
				itemGroupDefList.add(itdmvc);
			}
			Collections.sort(itemGroupDefList, new Comparator<ItemGroupDefMVC>() {
				public int compare(ItemGroupDefMVC ig1, ItemGroupDefMVC ig2) {
					if (ig1.getOrder() == ig2.getOrder()) return 0;
					return ig1.getOrder()< ig2.getOrder() ? -1 : 1;
				}
			});
			
		}

		public boolean isDirty() {
			return dirty;
		}

		public void setDirty(boolean dirty) {
			this.dirty = dirty;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public List<ItemGroupDefMVC> getItemGroupDefList() {
			return itemGroupDefList;
		}

		public void setItemGroupDefList(List<ItemGroupDefMVC> itemGroupDefList) {
			this.itemGroupDefList = itemGroupDefList;
		}
	}
	@Autowired
	private EDCSvc edcService;
	@Autowired
	private AdministrationSvc administrationService;
	@RequestMapping(method = RequestMethod.GET)
	public String ctrlFormData( Locale locale, Model model,  HttpServletRequest request) {
		Long subjectId = new Long(request.getParameter("subjectId"));
		Long formDefId = new Long(request.getParameter("formId"));
		Long studyEventId = new Long(request.getParameter("id"));
		Long studyId = (Long) request.getSession().getAttribute("studyId");
		Long locationId = (Long) request.getSession().getAttribute("locationId");
		Long userId = (Long) request.getSession().getAttribute("userId");

		
		SubjectData subject = edcService.getSubjectDataById(subjectId);
		StudyEventDef studyEvent = administrationService.getStudyEventDefById(studyEventId);
		FormDef formDef = administrationService.getFormDefById(formDefId);
		List<StudyEventData> studyEventDataList = edcService.getStudyEventDataBySubjectIdByStudyEventDefId(studyId, subjectId, studyEventId);
		int sizeList = studyEventDataList.size();
		if (sizeList>1) return "technicalError";
		StudyEventData studyEventData = null;
		if (sizeList ==1)
			studyEventData = studyEventDataList.get(0);
		
		List<FormData> formDataList = edcService.getFormDataBySubjectIdByFormDefId(studyId, studyEventId, formDefId, subjectId);
		sizeList = formDataList.size();
		if (sizeList>1) return "technicalError";
		FormData formData = null;
		if (sizeList ==1)
			formData = formDataList.get(0);
		String isDEA = administrationService.hasGrant( studyId, locationId, userId, new Long(Grant.DATA_ENTRY) ) ? "t" : "f";
		SignatureDef signatureDef = administrationService.getSignatureDef(studyId, new Long(SignatureDef.CRF_SIGNATURE));

		FormDefMVC formDefMvc = new FormDefMVC(formDef,formData, locale);
		
		model.addAttribute("subject", subject);
		model.addAttribute("studyEvent", studyEvent);
		model.addAttribute("formDef", formDefMvc);
		model.addAttribute("formDataId", formData != null ? formData.getId() : null);
		model.addAttribute("studyEventDataId", studyEventData != null ? studyEventData.getId() : null);
		model.addAttribute("isDEA", isDEA);
		model.addAttribute("statusDraft", FormData.STATUS_DRAFT);
		model.addAttribute("statusSign", FormData.STATUS_SIGNED);
		model.addAttribute("statusRTS", FormData.STATUS_READY_TO_SIGN);
		model.addAttribute("meaning", signatureDef.getMeaning());
		model.addAttribute("legalReason", signatureDef.getLegalReason());
		model.addAttribute("signatureDefId", signatureDef.getId());
		
		return "formData";	
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String addUpdateFormData(Locale locale, Model model, HttpServletRequest request) {
		
		Long formDefId = new Long(request.getParameter("id"));
		Long studyEventId = new Long(request.getParameter("studyEventId"));
		Long studyId = (Long) request.getSession().getAttribute("studyId");
		Long locationId = (Long) request.getSession().getAttribute("locationId");
		Long userId = (Long) request.getSession().getAttribute("userId");
		Long subjectDataId = new Long(request.getParameter("subjectId"));
		Long status = null;
		if (request.getParameter("form-status") != null && request.getParameter("form-status").length()>0 )
		 status = new Long(request.getParameter("form-status"));
		else status = new Long(FormData.STATUS_DRAFT);
		String login = null;
		String signature = null;
		Long signatureDefId = null;
		DBUser user = null; 
		String reasonForUpdate= null;
		if (status == FormData.STATUS_DRAFT) 
			reasonForUpdate = request.getParameter("reasonForUpdate");
		else if (status == FormData.STATUS_SIGNED) {
			reasonForUpdate =request.getParameter("reasonForUpdateSign");
			login = request.getParameter("login");
			signature = request.getParameter("signature");
			signatureDefId = new Long(request.getParameter("signatureDefId"));
			user = administrationService.getUserByLogin(login);
			if (!administrationService.isMatchingSignature(studyId, locationId, signature, user) )
				return "technicalError";
		}
		else if (status ==FormData.STATUS_READY_TO_SIGN ) {
			reasonForUpdate =request.getParameter("reasonForUpdateRTS");
		}
		
		Long formDataId= null;
		Long studyEventDataId = null;

		if (request.getParameter("formDataId") !=null && request.getParameter("formDataId").length()>0 ) 
			formDataId = new Long(request.getParameter("formDataId"));
			
		
		if (request.getParameter("studyEventDataId") !=null && request.getParameter("studyEventDataId").length()>0 )
			studyEventDataId = new Long(request.getParameter("studyEventDataId"));
		
		Map<String, String> itemsDef = new HashMap<String, String>();
		Map<String, String> itemsDisp = new HashMap<String, String>();
		Map<String, String> itemsDirty = new HashMap<String, String>();
		
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			if (paramName.indexOf("item-def-")>=0)
				itemsDef.put(paramName, request.getParameter(paramName));
			else if (paramName.indexOf("item-display-")>=0)
				itemsDisp.put(paramName, request.getParameter(paramName));
			else if (paramName.indexOf("item-dirty-")>=0)
				itemsDirty.put(paramName, request.getParameter(paramName));
		}
		try {
			edcService.addUpdateFormData(studyId, locationId, userId, subjectDataId, studyEventId, studyEventDataId, formDefId, formDataId, status, reasonForUpdate, itemsDef, itemsDisp, itemsDirty, user !=null ? user.getId() : null, signatureDefId);
		}
		catch (Exception e) {
			return "technicalError";
		}
		return "redirect:studyMenu.htm";
	}

}
