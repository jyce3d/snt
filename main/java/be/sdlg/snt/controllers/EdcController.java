package be.sdlg.snt.controllers;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import be.sdlg.snt.AdministrationSvc;
import be.sdlg.snt.ControllerAdvice;
import be.sdlg.snt.EDCSvc;
import be.sdlg.snt.ScheduledEvent;
import be.sdlg.snt.ScheduledEventBuilder;

import be.sdlg.snt.controllers.FormDataController.ItemGroupDefMVC;
import be.sdlg.snt.model.ClinicalData;
import be.sdlg.snt.model.ClinicalDef;
import be.sdlg.snt.model.ClinicalRef;
import be.sdlg.snt.model.CodeList;
import be.sdlg.snt.model.DBUser;
import be.sdlg.snt.model.FormData;
import be.sdlg.snt.model.FormDataAudit;
import be.sdlg.snt.model.FormDef;
import be.sdlg.snt.model.FormRef;
import be.sdlg.snt.model.Grant;
import be.sdlg.snt.model.ItemDef;
import be.sdlg.snt.model.ItemGroupDef;
import be.sdlg.snt.model.ItemGroupRef;
import be.sdlg.snt.model.ItemRef;
import be.sdlg.snt.model.Location;
import be.sdlg.snt.model.MetadataVersion;
import be.sdlg.snt.model.Signature;
import be.sdlg.snt.model.SignatureDef;
import be.sdlg.snt.model.Study;
import be.sdlg.snt.model.StudyEventData;
import be.sdlg.snt.model.StudyEventDef;
import be.sdlg.snt.model.StudyEventRef;
import be.sdlg.snt.model.StudyLocation;
import be.sdlg.snt.model.SubjectData;
import be.sdlg.snt.reports.FormDataAuditReport;


/**
 * Handles requests for the application home page.
 */
@Controller
@Transactional
public class EdcController {
	public class FormDataDescr {
		protected String formName;
		protected Long status;
		protected Long formDefId;
		protected Long order;
		public String getFormName() {
			return formName;
		}
		public void setFormName(String formName) {
			this.formName = formName;
		}
		public Long getStatus() {
			return status;
		}
		public void setStatus(Long status) {
			this.status = status;
		}
		public Long getFormDefId() {
			return formDefId;
		}
		public void setFormDefId(Long formDefId) {
			this.formDefId = formDefId;
		}
		public Long getOrder() {
			return order;
		}
		public void setOrder(Long order) {
			this.order = order;
		}
	
		
	}
	public class SubjectEventDataDisplay {
		protected String studyEventName;
		protected String studyEventDescr;
		protected String effectiveDate;
		protected String scheduledDate;
		protected String minTolerance;
		protected String maxTolerance;
		protected Date orderDate;
		protected Long studyEventDefId;
		
		protected List<FormDataDescr> formDescrList;
		
		public Date getOrderDate() {
			return orderDate;
		}

		public void setOrderDate(Date orderDate) {
			this.orderDate = orderDate;
		}

		public SubjectEventDataDisplay(ScheduledEvent se, Locale locale) {
			this.setStudyEventName(se.getStudyEventRef().getStudyEventDef().getName());
			this.setScheduledDate(ControllerAdvice.getFormattedDate(se.getScheduledDate(), locale, false));
			this.setStudyEventDefId(se.getStudyEventRef().getStudyEventDef().getId());
			this.setMinTolerance(se.getStudyEventRef().getMinTolerance().toString());
			this.setMaxTolerance(se.getStudyEventRef().getMaxTolerance().toString());
			String descr = administrationService.getDescription(se.getStudyEventRef().getStudyEventDef().getDescription(), locale);
			this.setStudyEventDescr(descr);
			if (se.getStudyEventData() != null && se.getStudyEventData().getEffectiveDate() !=null) {
				this.setEffectiveDate(ControllerAdvice.getFormattedDate(se.getStudyEventData().getEffectiveDate(), locale, false));
				this.orderDate = se.getStudyEventData().getEffectiveDate();
			}
			else {
				this.orderDate = se.getScheduledDate();
				this.setEffectiveDate(administrationService.getMessage("snt.nav", locale));
			}
			formDescrList = new ArrayList<FormDataDescr>();
			for (FormRef fr : se.getStudyEventRef().getStudyEventDef().getFormRefList()) {
				FormDataDescr fdd = new FormDataDescr();
				fdd.setFormName(fr.getFormDef().getName());
				fdd.setFormDefId(fr.getFormDef().getId());
				fdd.setOrder(fr.getOrderNumber());
				if (se.getStudyEventData() != null && se.getStudyEventData().getFormDataList() !=null ) {
					for (FormData fd : se.getStudyEventData().getFormDataList()) {
						if (fd.getFormDef().getId().equals(fr.getFormDef().getId()))
							fdd.setStatus(fd.getStatus());
					}
				}
				formDescrList.add(fdd);
			}
			Collections.sort(formDescrList, new Comparator<FormDataDescr>() {
				public int compare(FormDataDescr f1, FormDataDescr f2) {
					if (f1.order == f2.order) return 0;
					return f1.order< f2.order ? -1 : 1;
				}
			});
		}

		public String getStudyEventName() {
			return studyEventName;
		}

		public void setStudyEventName(String studyEventName) {
			this.studyEventName = studyEventName;
		}

	

		public String getEffectiveDate() {
			return effectiveDate;
		}

		public void setEffectiveDate(String effectiveDate) {
			this.effectiveDate = effectiveDate;
		}

		public String getScheduledDate() {
			return scheduledDate;
		}

		public void setScheduledDate(String scheduledDate) {
			this.scheduledDate = scheduledDate;
		}

		public List<FormDataDescr> getFormDescrList() {
			return formDescrList;
		}

		public void setFormDescrList(List<FormDataDescr> formDescrList) {
			this.formDescrList = formDescrList;
		}

		public Long getStudyEventDefId() {
			return studyEventDefId;
		}

		public void setStudyEventDefId(Long studyEventDefId) {
			this.studyEventDefId = studyEventDefId;
		}

		public String getMinTolerance() {
			return minTolerance;
		}

		public void setMinTolerance(String minTolerance) {
			this.minTolerance = minTolerance;
		}

		public String getMaxTolerance() {
			return maxTolerance;
		}

		public void setMaxTolerance(String maxTolerance) {
			this.maxTolerance = maxTolerance;
		}

		public String getStudyEventDescr() {
			return studyEventDescr;
		}

		public void setStudyEventDescr(String studyEventDescr) {
			this.studyEventDescr = studyEventDescr;
		}

	}
	
	private static final Logger logger = LoggerFactory.getLogger(EdcController.class);
	@Autowired
	private AdministrationSvc administrationService;
	@Autowired	
	private EDCSvc edcService;
	@Autowired
	private MappingJacksonHttpMessageConverter jsonConverter;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.debug("Entering login!");


		return "login";
	}
	@RequestMapping(value="secure/users.htm", method=RequestMethod.GET) 
	public String ctrlUserList(Locale locale, Model model) {
			List<DBUser> userList= administrationService.getUserList();
		model.addAttribute("userList", userList);
		return "userList";

	}
	@RequestMapping(value="secure/studyMenu.htm", method = RequestMethod.GET)
	public String ctrlStudyMenu(Locale locale, Model model, HttpServletRequest request) {
		
		Long locationId;
		if (request.getParameter("locationId") !=null )
		 locationId = new Long(request.getParameter("locationId"));
		else
	     locationId = (Long)request.getSession().getAttribute("locationId");
		Location location = null;
		if (locationId != null)
		 location = administrationService.getLocation(locationId);
		else
			return "technicalError";
		
		
		Long studyId = (Long) request.getSession().getAttribute("studyId");
		
		Study study = administrationService.getStudy(studyId);
		model.addAttribute("studyId", study.getId());
		model.addAttribute("studyName", study.getStudyName());
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		boolean isAdmin = false;
		for (GrantedAuthority autho : auth.getAuthorities())
			if (autho.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		model.addAttribute("isAdmin", isAdmin ? "t" : "f");
		if (location != null) {
			request.getSession().setAttribute("locationId", location.getId());
			request.getSession().setAttribute("locationName", location.getName());
			request.getSession().setAttribute("locationShortName", location.getShortName());
		}
		return "studyMenu";
	}
	
	@RequestMapping(value="secure/technicalError.htm", method=RequestMethod.GET) 
	public String technicalError(Locale locale, Model model) {
		return "technicalError";

	}
	@RequestMapping(value = "secure/newParticipants.htm", method = RequestMethod.GET)
	public String ctrlLocationList(Locale locale, Model model, HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		boolean isAdmin = administrationService.hasRole(auth, "ROLE_ADMIN");
		if (isAdmin) {
			List<Location> locationList = administrationService.getLocations();
			model.addAttribute("locationList", locationList);
			model.addAttribute("studyName", request.getSession().getAttribute("studyName"));
			return "newParticipants";
			
		} else return "technicalError";
	
	}	
	
	@RequestMapping(value = "secure/studyList.htm", method = RequestMethod.GET)
	public String ctrlStudyList(Locale locale, Model model,  HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		boolean isAdmin = administrationService.hasRole(auth, "ROLE_ADMIN");

		model.addAttribute("isAdmin", isAdmin ? "t" : "f");
		DBUser user = administrationService.getUserByLogin(auth.getName());
		request.getSession().setAttribute("userId", user.getId());
		request.getSession().setAttribute("userFirstName", user.getFirstName());
		request.getSession().setAttribute("userLastName", user.getLastName());
		
		
		List<Study> studyList = null;
		if (isAdmin == false )
				studyList = administrationService.getStudiesByUserId(user.getId());
		else
				studyList = administrationService.getStudies();
		for (Study study : studyList) {
			List<SubjectData> sd = edcService.getSubjectDataByStudyByUser(study, user);
			study.setSubjectCount(sd.size());
		}
		model.addAttribute("studyList", studyList);
		model.addAttribute("user", user);
		// The AOP mechanism has been activated before the data where stored in session
		// So, we have to update them manually.
		model.addAttribute("userFirstName", user.getFirstName());
		model.addAttribute("userLastName", user.getLastName());
		
	

		return "studyList";

	}
	@RequestMapping(value = "secure/subjectList.htm", method = RequestMethod.GET)
	public String ctrlSubjectList(Locale locale, Model model,  HttpServletRequest request) {
		Long studyId = (Long) request.getSession().getAttribute("studyId");
		Long userId = (Long)request.getSession().getAttribute("userId");

		List<SubjectData> subjectList = edcService.getSubjectDataByStudyByUser(studyId, userId);
		Collections.sort(subjectList, new Comparator<SubjectData>() {
			public int compare(SubjectData ig1, SubjectData ig2) {
				if (ig1.getId() == ig2.getId()) return 0;
				return ig1.getId()< ig2.getId() ? -1 : 1;
			}
		});

		model.addAttribute("subjectList", subjectList);
		//model.addAttribute("studyName", request.getSession().getAttribute("studyName"));
		return "subjectList";
	}
	private void recurStudyEvents(ScheduledEvent curSe,List<SubjectEventDataDisplay> responseObject, Locale locale) {
		for (ScheduledEvent se : curSe.getNextEvents() )
			if (se.isVisited() == false) {
				se.setVisited(true);
				SubjectEventDataDisplay sedd = new SubjectEventDataDisplay(se, locale);
				responseObject.add(sedd);				
				recurStudyEvents(se, responseObject, locale);
			}
	}
	@RequestMapping(value = "secure/studyEvents.htm", method = RequestMethod.GET)
	public String ctrlStudyEvents(Locale locale, Model model, HttpServletRequest request) {
		
		Long studyId = (Long) request.getSession().getAttribute("studyId");
		Long subjectId = new Long( request.getParameter("id"));
		
		Study study = administrationService.getStudy(studyId);
		MetadataVersion mv = administrationService.getCurrentMetadataVersion(study);
		SubjectData sd = edcService.getSubjectDataById(subjectId);
		
		List<StudyEventData> studyEventDataList = edcService.getStudyEventDataBySubject( sd.getId());
		Set<StudyEventData> studyEventDataSet = new HashSet<StudyEventData>(0);
		for (StudyEventData sed : studyEventDataList)
			studyEventDataSet.add(sed);
		ScheduledEventBuilder scheduledEventBuilder = new ScheduledEventBuilder();
		//TODO: add a check on the Status Enrolled otherwise refuse to compute the visit Plan.
		Set<ScheduledEvent> scheduledEvents = scheduledEventBuilder.build(mv.getProtocol(), sd.getStatusDate(), studyEventDataSet);
		
		// Prepare responseObject
		
		List<SubjectEventDataDisplay> responseObject = new ArrayList<SubjectEventDataDisplay>();
		for (ScheduledEvent se: scheduledEvents) {
			SubjectEventDataDisplay sedd = new SubjectEventDataDisplay(se, locale);
			responseObject.add(sedd);
			se.setVisited(true);
			recurStudyEvents(se, responseObject, locale);
		}
		Collections.sort(responseObject, new Comparator<SubjectEventDataDisplay>() {
			public int compare(SubjectEventDataDisplay s1, SubjectEventDataDisplay s2) {
				if (s1.getOrderDate() == s2.getOrderDate()) return 0;
				return s1.getOrderDate().before(s2.getOrderDate()) ? -1 : 1;
			}
		});
		model.addAttribute("studyEventList", responseObject);
		model.addAttribute("subjectData", sd);
		return "studyEventDataList";
		

	}
	@RequestMapping(value="secure/reports.htm", method = RequestMethod.GET)
	public String ctrlReports(Locale locale, Model model, HttpServletRequest request) {
		Long studyId = (Long) request.getSession().getAttribute("studyId");

		List<SubjectData> subjectDataList = edcService.getSubjectDataByStudyForReport(studyId);
		Map<String, Long> countMonths = new HashMap<String, Long>();
		Calendar cal = Calendar.getInstance();
		int month;
		int year;
		String key;
		Long counts;
		for (SubjectData sd : subjectDataList) {
			cal.setTime(sd.getStatusDate());
			month = cal.get(Calendar.MONTH)+1;
			year = cal.get(Calendar.YEAR);
			
			key = month+"/"+year;
			counts = countMonths.get(key);
			if (counts ==null) counts=new Long(0);
			counts++;
			countMonths.put(key, counts);			
		}
		String xAxisContent="";
		String serieContent="";
		
		for (Map.Entry<String, Long> entry : countMonths.entrySet()) {
			if (xAxisContent.length()>0)
				xAxisContent+=",";
			xAxisContent+="'"+entry.getKey()+"'";
			if (serieContent.length()>0)
				serieContent+=",";
			serieContent+=entry.getValue();
		}
		
		model.addAttribute("xAxis", xAxisContent);
		model.addAttribute("serie", serieContent);
		
		return "enrollReport";
		
	}
	@RequestMapping(value = "secure/studyLocations.htm", method = RequestMethod.GET)
	public String ctrlStudyLocations(Locale locale, Model model, HttpServletRequest request) {
		Long studyId;
		
		if (request.getParameter("studyId") !=null )
		 studyId = new Long(request.getParameter("studyId"));
		else
	     studyId = (Long)request.getSession().getAttribute("studyId");
		Study study = administrationService.getStudy(studyId);
		
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		DBUser user = administrationService.getUserByLogin(auth.getName());
		boolean isAdmin = administrationService.hasRole(auth, "ROLE_ADMIN");
		model.addAttribute("isAdmin", isAdmin ? "t" : "f");

		List<Location> studyLocations = administrationService.getStudyLocationByStudyByUser(studyId, user.getId());			
		
		request.getSession().setAttribute("studyId", study.getId());
		request.getSession().setAttribute("studyName", study.getStudyName());
		
		if (studyLocations.size() == 0  ) 

			model.addAttribute("isLocations","f");
	    else
			model.addAttribute("isLocations","t");
		
		model.addAttribute("studyName", study.getStudyName());
		model.addAttribute("studyLocations", studyLocations);

		return "studyLocations";
	}
	//@Transactional
	@RequestMapping(value = "login.htm", method = RequestMethod.POST)
	public String ctrlLogin(@RequestParam("login_error") String loginError,Locale locale, Model model) {
		model.addAttribute("loginError",loginError);		
		return "login";
	}	
	@RequestMapping(value="secure/formAuditPdf.htm", method = RequestMethod.GET)
	public ModelAndView ctrlFormAudit(Locale locale, Model model, HttpServletRequest request) {
		Long studyId = (Long) request.getSession().getAttribute("studyId");
		Long locationId = (Long) request.getSession().getAttribute("locationId");
		Long userId = (Long) request.getSession().getAttribute("userId");
		Long formDataId = new Long(request.getParameter("form-data-id"));
		
		FormData formData = edcService.getFormDataById(formDataId);
		DBUser user = administrationService.getUserById(userId);
		Location location = administrationService.getLocation(locationId);
		
		
		
		model.addAttribute("titleReport",administrationService.getMessage("snt.report.formDataAuditReport", locale)+" - "+formData.getFormDef().getName());
		model.addAttribute("info", ControllerAdvice.getFormattedName(user.getFirstName(),user.getLastName(), user.getName())+" - "+ControllerAdvice.getFormattedDate(new Date(), locale, true)+" - "+formData.getStudy().getStudyName()
				+" - "+ControllerAdvice.getFormattedLocation(location.getShortName(), location.getName())+" - "+formData.getStudyEventData().getSubjectData().getSubjectKey()
				+" - "+formData.getStudyEventData().getStudyEventDef().getName());
		Map<String, String> formHeaders = new HashMap<String, String>();
		formHeaders.put("timeStamp", administrationService.getMessage("snt.report.date", locale));
		formHeaders.put("userName", administrationService.getMessage("snt.report.user", locale));
		formHeaders.put("locationName", administrationService.getMessage("snt.location", locale));
		formHeaders.put("reasonForChange", administrationService.getMessage("snt.report.reason", locale));
		formHeaders.put("signatorName", administrationService.getMessage("snt.report.signatorName", locale));
		formHeaders.put("signLocation", administrationService.getMessage("snt.report.signLocation", locale));
		formHeaders.put("signStatus", administrationService.getMessage("snt.report.signStatus", locale));
		formHeaders.put("oldStatus", administrationService.getMessage("snt.report.oldStatus", locale));
		formHeaders.put("newStatus", administrationService.getMessage("snt.report.newStatus", locale));
		
		
		Map<Long, String> statuses = new HashMap<Long, String>();
		statuses.put(new Long(FormData.STATUS_DRAFT), administrationService.getMessage("snt.draft", locale));
		statuses.put(new Long(FormData.STATUS_READY_TO_SIGN), administrationService.getMessage("snt.rts", locale));
		statuses.put(new Long(FormData.STATUS_SIGNED), administrationService.getMessage("snt.final", locale));

		Map<Long, String> actives = new HashMap<Long, String>();
		actives.put(new Long(ClinicalData.ACTIVE_YES), administrationService.getMessage("snt.report.active", locale));
		actives.put(new Long(ClinicalData.ACTIVE_NO), administrationService.getMessage("snt.report.inactive", locale));

		Map<Long, String> signStatuses = new HashMap<Long, String>();
		signStatuses.put(new Long(Signature.SIGNED), administrationService.getMessage("snt.report.signed", locale));
		signStatuses.put(new Long(Signature.REVOKED), administrationService.getMessage("snt.report.revoked", locale));
		
		List<FormDataAudit> formDataAuditList = edcService.getFormDataAuditRecords(formDataId);
		
		Map<String, String> itemGroupDataHeaders = new HashMap<String, String>();
		itemGroupDataHeaders.put("itemGroupRepeatOldKey", administrationService.getMessage("snt.report.itemGroupDataOldKey", locale));
		itemGroupDataHeaders.put("itemGroupRepeatNewKey", administrationService.getMessage("snt.report.itemGroupDataNewKey", locale));
		
		Map<String, String> itemHeaders = new HashMap<String, String>();
		itemHeaders.put("itemName", administrationService.getMessage("snt.report.itemName", locale));
		itemHeaders.put("codeValue", administrationService.getMessage("snt.report.codeValue", locale));
		itemHeaders.put("value", administrationService.getMessage("snt.report.value", locale));
		itemHeaders.put("oldCodeValue", administrationService.getMessage("snt.report.oldCodeValue", locale));
		itemHeaders.put("oldValue", administrationService.getMessage("snt.report.oldValue", locale));
		
		String itemGroupSection = administrationService.getMessage("snt.report.itemGroupDataAudit", locale);
		String itemSection = administrationService.getMessage("snt.report.itemDataAudit", locale);
		String formSection = administrationService.getMessage("snt.report.formDataAudit", locale)+" - "+formData.getFormDef().getName();
		FormDataAuditReport freport = new FormDataAuditReport(formDataAuditList, locale, statuses, actives, signStatuses, formHeaders,itemGroupDataHeaders,
				itemHeaders, formSection, itemGroupSection, itemSection);
		model.addAttribute("formDataAuditReport", freport);
		
		return new ModelAndView("pdfFormAuditReport", "reportModel", model);
	}
	
	@RequestMapping(value ="secure/ajaxSubjectInvestigators.htm", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map getSubjectInvestigators(HttpServletRequest request) {
		Long studyId = (Long) request.getSession().getAttribute("studyId");
		Long locationId = new Long(request.getParameter("locationId"));
		List<DBUser> userList = administrationService.getUserByStudyByLocation(studyId, locationId, new Long(Grant.INVESTIGATOR));
		Map<String, String> users = new HashMap<String, String>();
		for (DBUser user : userList ) 
			users.put(user.getName(), user.getId().toString());
		return users;
	}
	
	@RequestMapping(value ="secure/ajaxVerifySignature.htm", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map getValidSignature(HttpServletRequest request) {
		Long studyId = (Long) request.getSession().getAttribute("studyId");
		Long locationId = (Long) request.getSession().getAttribute("locationId");
	
		String result="f";

		DBUser user = administrationService.getUserByLogin(request.getParameter("login"));
		Map<String, String> response = new HashMap<String, String>();
		String signature =  request.getParameter("signature");
		// Check the user is investigator where he wants to sign
		if (administrationService.isMatchingSignature(studyId, locationId,  signature, user)) 
			result="t";
		
		response.put("result", result );
		
		return response;
	}

/*	@RequestMapping( value="createUser", method=RequestMethod.GET)
	public String createUsers(Locale locale, Model model, HttpServletRequest request) {
		List<String> authorities = new ArrayList<String>();
		authorities.add("ROLE_ADMIN");
		authorities.add("ROLE_ADMIN");
		try {
		 DBUser user = administrationService.createUser("jyce", "123456", true, "t@t.com", authorities);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return "technicalError";
	}*/
	@RequestMapping(value = "secure/praetorian.htm", method = RequestMethod.GET)
	public String ctrlPraetorian(Locale locale, Model model, HttpServletRequest request) {

	
	


		try {
/*			// 1. Create the study in order to create metadateversion and Protocol
		 Study study = administrationService.createStudy("Demo-GHC","A phase III randomized, blinded trial to demonstrate the usability of snt (Sibyl is Not TrialXS) project.", new Date(), "amendment","amendment0",new Long(0), locale);	
		
		 MetadataVersion mv = administrationService.getCurrentMetadataVersion(study);
			// 2. Task : Import the visit schedules
	    StudyEventDef visit1 = administrationService.createStudyEventDef(study.getId(), "screening", new Long(StudyEventDef.TYPE_SCHEDULED), new Long(StudyEventDef.REPEATING_NO),
				"Screening Visit", "screening subject", locale);
		StudyEventRef visit1ref = administrationService.createStudyEventRef(mv.getProtocol().getId(), visit1, null, new Long(0), new Long(0), new Long(0), new Long(ClinicalRef.MANDATORY_YES));
		StudyEventDef visit2 = administrationService.createStudyEventDef(study.getId(),"pre-treatment", new Long(StudyEventDef.TYPE_SCHEDULED), new Long(StudyEventDef.REPEATING_NO),
				"Enrollment Visit", "get inform consent and enroll subject", locale);
		StudyEventRef visit2ref = administrationService.createStudyEventRef(mv.getProtocol().getId(), visit2, visit1ref, new Long(3), new Long(0), new Long(7), new Long(StudyEventRef.MANDATORY_YES));
		StudyEventDef visit3 = administrationService.createStudyEventDef(study.getId(), "treatment", new Long(StudyEventDef.TYPE_SCHEDULED), new Long(StudyEventDef.REPEATING_NO),
				"Treatment allocation","Treatment allocation Dose 1", locale);
		StudyEventRef visit3ref = administrationService.createStudyEventRef(mv.getProtocol().getId(), visit3, visit2ref, new Long(3), new Long(2), new Long(4), new Long(StudyEventRef.MANDATORY_YES));
		StudyEventDef visit4 = administrationService.createStudyEventDef(study.getId(),"post-treatment", new Long(StudyEventDef.TYPE_SCHEDULED), new Long(StudyEventDef.REPEATING_NO),
				"Contact phone", "contact phone with subject after Dose 1", locale);
		StudyEventRef visit4ref = administrationService.createStudyEventRef(mv.getProtocol().getId(), visit4, visit3ref, new Long(3), new Long(0), new Long(14), new Long(StudyEventDef.REPEATING_NO));
		StudyEventDef visit5 = administrationService.createStudyEventDef(study.getId(), "follow-up", new Long(StudyEventDef.TYPE_SCHEDULED), new Long(StudyEventDef.REPEATING_NO),
				"Follow up 1 week", "Follow up visit week 1", locale);
		StudyEventRef visit5ref = administrationService.createStudyEventRef(mv.getProtocol().getId(), visit5, visit3ref, new Long(5), new Long(2), new Long(7), new Long(StudyEventRef.MANDATORY_YES));
		StudyEventDef visit6 = administrationService.createStudyEventDef(study.getId(), "treatment", new Long(StudyEventDef.TYPE_SCHEDULED),new Long(StudyEventDef.REPEATING_NO), 
				"Treatment allocation" , "Treatment allocation Dose 2", locale);
		StudyEventRef visit6ref = administrationService.createStudyEventRef(mv.getProtocol().getId(), visit6, visit4ref, new Long(3), new Long(0), new Long(5), new Long(StudyEventRef.MANDATORY_YES));
		administrationService.addStudyEventRefPredecessor(visit6ref, visit5ref);
		StudyEventDef visit7 = administrationService.createStudyEventDef(study.getId(), "follow-up", new Long(StudyEventDef.TYPE_SCHEDULED), new Long(StudyEventDef.REPEATING_NO), 
				"Follow up visit 1 month", "Follow up visit after 1 month", locale);
		StudyEventRef visit7ref = administrationService.createStudyEventRef(mv.getProtocol().getId(), visit7, visit6ref, new Long(30), new Long(25), new Long(35), new Long(StudyEventRef.MANDATORY_YES));*/
/*		Study study = administrationService.getStudy(new Long(5));
		StudyEventDef visit1 = administrationService.getStudyEventDefById(new Long(10));
		FormDef form1 = administrationService.createFormDef(study.getId(), "Demography", "Demographic form", new Long(ClinicalDef.REPEATING_NO),locale);
		FormRef form1Ref = administrationService.createFormRef(visit1.getId(), form1, new Long(1), new Long(ClinicalRef.MANDATORY_YES));
		FormDef form2 = administrationService.createFormDef(study.getId(), "Disease History", "Is this subject in the conditions to participate to the trial?", new Long(ClinicalDef.REPEATING_NO), locale);
		FormRef form2Ref = administrationService.createFormRef(visit1.getId(), form2, new Long(2), new Long(ClinicalRef.MANDATORY_YES));
		
*/					
			Study study = administrationService.getStudy(new Long(5));
		// Create Code List
/*			CodeList genderCl = administrationService.createCodeList(study.getId(), "Gender", "Gender Code List", new Long(CodeList.DATATYPE_INT), "GENDER", locale);
			administrationService.addCodeListItem(genderCl.getId(), "1", "Male", new Float(1), locale);
			administrationService.addCodeListItem(genderCl.getId(), "2", "Female", new Float(2), locale);
			CodeList ethnicityCl = administrationService.createCodeList(study.getId(), "Ethnicity", "Ethnicity Code List", new Long(CodeList.DATATYPE_INT), "ETHNICITY", locale);
			administrationService.addCodeListItem(ethnicityCl.getId(), "1", "American Hispanic or Latino", new Float(1), locale);
			administrationService.addCodeListItem(ethnicityCl.getId(), "2", "Not American Hispanic or Latino", new Float(2), locale);
			
			FormDef form1 = administrationService.getFormDefById(new Long(53));

			ItemGroupDef itg1 = administrationService.createItemGroupDef(study.getId(),"DM", "Demography","DM","no-comment","CRF","Test", new Long(ClinicalDef.REPEATING_NO),"DM", locale);
			ItemGroupRef itg1ref = administrationService.createItemGroupRef(form1.getId(), itg1,new Long(ClinicalRef.MANDATORY_YES), new Long(1));
			ItemDef id11 = administrationService.createItemDef(study.getId(),"date of birth", new Long(ItemDef.DATATYPE_DATE), "DOB", "DOB item def", "CRF" , "DOB", "DOB", new Long(0),"Date of Birth", null,locale);
			ItemRef id11ref = administrationService.createItemRef(itg1.getId(), id11, new Long(2), new Long(ClinicalRef.MANDATORY_YES), new Long(1), "entry-field");
			ItemDef id12 = administrationService.createItemDef(study.getId(),"gender", new Long(ItemDef.DATATYPE_TEXT), "GENDER", "gender item def", "CRF" , "GENDER", "GENDER", new Long(0),"Gender", genderCl.getId(), locale);
			ItemRef id12ref = administrationService.createItemRef(itg1.getId(), id12, new Long(1), new Long(ClinicalRef.MANDATORY_YES), new Long(2), "entry-field");
			ItemDef id13 = administrationService.createItemDef(study.getId(),"ethnicity", new Long(ItemDef.DATATYPE_TEXT), "ETHNIC", "ethnicity item def", "CRF" , "ETHNIC", "ETHNIC", new Long(0),"Ethnicity", ethnicityCl.getId(), locale);
			ItemRef id13ref = administrationService.createItemRef(itg1.getId(), id13, new Long(1), new Long(ClinicalRef.MANDATORY_YES), new Long(3), "entry-field");
*/
			
			//administrationService.createSignatureDef(study.getId(), "Form signature", "I certify this data is accurate to the best of my knowledge.", new Long(SignatureDef.Electronic), new Long(SignatureDef.CRF_SIGNATURE));
			

			}
		catch (Exception e) {
			e.printStackTrace();
		}
		return "technicalError";
	}
	
	
}
