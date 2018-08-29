package be.sdlg.snt.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import be.sdlg.snt.ControllerAdvice;
import be.sdlg.snt.EDCSvc;
import be.sdlg.snt.dao.ClinicalDataDao;
import be.sdlg.snt.dao.DBUserDao;
import be.sdlg.snt.dao.FormDataAuditDao;
import be.sdlg.snt.dao.FormDataDao;
import be.sdlg.snt.dao.FormDefDao;
import be.sdlg.snt.dao.ItemDataAuditDao;
import be.sdlg.snt.dao.ItemDataDao;
import be.sdlg.snt.dao.ItemGroupDataAuditDao;
import be.sdlg.snt.dao.ItemGroupDataDao;
import be.sdlg.snt.dao.LocationDao;
import be.sdlg.snt.dao.SignatureDao;
import be.sdlg.snt.dao.SignatureDefDao;
import be.sdlg.snt.dao.StudyDao;
import be.sdlg.snt.dao.StudyEventDataAuditDao;
import be.sdlg.snt.dao.StudyEventDataDao;
import be.sdlg.snt.dao.StudyEventDefDao;
import be.sdlg.snt.dao.SubjectDataAuditDao;
import be.sdlg.snt.dao.SubjectDataDao;
import be.sdlg.snt.exceptions.FormDefCannotBeNullException;
import be.sdlg.snt.exceptions.ItemGroupDefCannotBeNullException;
import be.sdlg.snt.exceptions.ItemGroupRepeatKeyCannotBeNullException;
import be.sdlg.snt.exceptions.LocationCannotBeNullException;
import be.sdlg.snt.exceptions.ReasonCannotBeNullException;
import be.sdlg.snt.exceptions.StudyEventDataCannotBeNullException;
import be.sdlg.snt.exceptions.StudyEventDefCannotBeNullException;
import be.sdlg.snt.exceptions.StudyNotExistException;
import be.sdlg.snt.exceptions.SubjectDataCannotBeNullException;
import be.sdlg.snt.exceptions.UserCannotBeNullException;
import be.sdlg.snt.exceptions.ActiveCannotBeNullException;
import be.sdlg.snt.exceptions.StatusCannotBeNullException;

import be.sdlg.snt.model.AuditRecord;
import be.sdlg.snt.model.ClinicalData;
import be.sdlg.snt.model.DBUser;
import be.sdlg.snt.model.FormData;
import be.sdlg.snt.model.FormDataAudit;
import be.sdlg.snt.model.FormDef;
import be.sdlg.snt.model.ItemData;
import be.sdlg.snt.model.ItemDataAudit;
import be.sdlg.snt.model.ItemDef;
import be.sdlg.snt.model.ItemGroupData;
import be.sdlg.snt.model.ItemGroupDataAudit;
import be.sdlg.snt.model.ItemGroupDef;
import be.sdlg.snt.model.ItemGroupRef;
import be.sdlg.snt.model.ItemRef;
import be.sdlg.snt.model.Location;
import be.sdlg.snt.model.Signature;
import be.sdlg.snt.model.SignatureDef;
import be.sdlg.snt.model.Study;
import be.sdlg.snt.model.StudyEventData;
import be.sdlg.snt.model.StudyEventDataAudit;
import be.sdlg.snt.model.StudyEventDef;
import be.sdlg.snt.model.SubjectData;
import be.sdlg.snt.model.SubjectDataAudit;

@Service
public class EDCSvcImpl implements EDCSvc {
	protected static final String AUDIT_SUBJECT_CREATION = "SUBJECT_CREATION";
	protected static final String AUDIT_SUBJECT_UPDATE = "SUBJECT_UPDATE";
	protected static final String AUDIT_STUDYEVENT_CREATION ="STUDY_EVENT_DATA_CREATION";
	protected static final String AUDIT_FORM_CREATION = "FORM_DATA_CREATION";
	protected static final String AUDIT_ITEMGROUP_CREATION = "ITEM_GROUP_DATA_CREATION";
	protected static final String AUDIT_ITEM_CREATION = "ITEM_DATA_CREATION";
	private static final Logger logger = LoggerFactory.getLogger(EDCSvcImpl.class);

	public class InvalidSubjectKeyException extends Exception {
		public InvalidSubjectKeyException() {
			super("InvalidSubjectKeyException");
		}

		public InvalidSubjectKeyException(String msg) {
			super(msg);
		}
	}

	public class DuplicateSubjectKeyException extends Exception {
		public DuplicateSubjectKeyException() {
			super("DuplicateSubjectKeyException");

		}

		public DuplicateSubjectKeyException(String msg) {
			super(msg);
		}
	}
	@Autowired
	protected StudyDao studyDao;
	@Autowired
	protected LocationDao locationDao;
	@Autowired
	protected DBUserDao dbUserDao;
	@Autowired
	protected SubjectDataDao subjectDataDao;
	@Autowired
	protected SignatureDefDao signatureDefDao;
	@Autowired
	protected SignatureDao signatureDao;
	@Autowired
	protected ClinicalDataDao clinicalDataDao;
	@Autowired
	protected SubjectDataAuditDao subjectDataAuditDao;
	@Autowired
	protected StudyEventDataDao studyEventDataDao;
	@Autowired
	protected StudyEventDataAuditDao studyEventDataAuditDao;
	@Autowired
	protected FormDataAuditDao formDataAuditDao;
	@Autowired
	protected ItemGroupDataAuditDao itemGroupDataAuditDao;
	@Autowired
	protected ItemDataAuditDao itemDataAuditDao;
	@Autowired
	protected StudyEventDefDao studyEventDefDao;
	@Autowired
	protected FormDataDao formDataDao;
	@Autowired 
	protected FormDefDao formDefDao;
	@Autowired
	protected ItemGroupDataDao itemGroupDataDao;
	@Autowired
	protected ItemDataDao itemDataDao;

	public void checkSubject(String subjectKey, Study study, Location location,
			DBUser dbUser) throws Exception {
		// TODO: activate the checks
		// List<Grant> grantList = grantDao.findGrants(study, dbUser, location);
		if (subjectDataDao.getSubjectKey(study, subjectKey) > 0)
			throw new DuplicateSubjectKeyException();
		// if (grantList.indexOf(1)>0 || grantList.indexOf(2)>0 ) {
		// if (key.match("pattern")) {
		//
		// } else
		// throw new InvalidSubjectKeyException();
		//
		// } else
		// throw new InsufficientAccessRightsException();

	}

	protected void populateAudit(AuditRecord auditRecord, ClinicalData target,
			DBUser userRef, Location locationRef, String reason,
			String sourceId, int operation) {
		auditRecord.setClinicalData(target);
		auditRecord.setDateTimeStamp(new Date());
		auditRecord.setEditPoint(new Long(AuditRecord.DBAudit));
		auditRecord.setLocationName(ControllerAdvice.getFormattedLocation(locationRef.getShortName(), locationRef.getName()));
		auditRecord.setUserName(ControllerAdvice.getFormattedName(userRef.getFirstName(), userRef.getLastName(), userRef.getName()));
		auditRecord.setUsedImputationMethod(new Long(AuditRecord.No));
		auditRecord.setReasonForChange(reason);
		auditRecord.setSourceId(sourceId);
		auditRecord.setOperation(new Long(operation));
	}
	
	/***
	 * Verify that the minimum information for audit trail are provided
	 * @param studyId
	 * @param locationId
	 * @param userId
	 * @param reasonForUpdate
	 * @throws Exception
	 */
	protected void populateAuditChecks( Long locationId, Long userId, 
			String reasonForUpdate) throws Exception {
		if (reasonForUpdate ==null  ) throw new ReasonCannotBeNullException();
		if (reasonForUpdate.length()<1 ) throw new ReasonCannotBeNullException();
		if (userId == null) throw new UserCannotBeNullException();
		if (locationId == null) throw new LocationCannotBeNullException();
		
	}
	protected void populateFormData(FormData formData, Long studyId, Long studyEventDataId, Long formDefId,
			String formRepeatKey, Long status, Long active, String reasonForUpdate, Long locationId, Long userId, int operationType, FormDataAudit fa) throws Exception {
		DBUser user =null;
		Location location = null;
		StudyEventData studyEventData = null;
		Study study = null;
		try {
			populateAuditChecks( locationId, userId, 
					reasonForUpdate);
			user = this.getUserById(userId);
			location = this.getLocation(locationId);			
		} catch (Exception e) {
			throw e;
		}
		// connect with parent link
		if (studyEventDataId == null && formData.getStudyEventData() !=null)
			studyEventData = studyEventDataDao.get(formData.getStudyEventData().getId());
		else {
			if (studyEventDataId !=null)
				studyEventData = studyEventDataDao.get(studyEventDataId);
			else
				 throw new StudyEventDataCannotBeNullException();
		}
		if ( formData.getStudyEventData() !=null && formData.getStudyEventData().getId()==studyEventData.getId() )
			;
		else {
		 formData.setStudyEventData(studyEventData);
		 studyEventData.getFormDataList().add(formData);
		}
		if (studyId == null && formData.getStudy() !=null)
			study = getStudy(formData.getStudy().getId());
		else {
			if (studyId != null)
				study = getStudy(studyId);
			else 
				throw new StudyNotExistException();
		}
		if ( formData.getStudy() !=null && formData.getStudy().getId()==study.getId() ) ; else {
			formData.setStudy(study);
			study.getClinicalDataList().add(formData);
		}

		//----
		// Connect to def link
		FormDef formDef = null;
		if (formDefId == null && formData.getFormDef()!=null)
			formDef = formDefDao.get(formData.getFormDef().getId());
		else {
			if (formDefId !=null) 
				formDef = formDefDao.get(formDefId);
			else throw new FormDefCannotBeNullException();
		}
		if (formData.getFormDef() != null && formData.getFormDef().getId()==formDef.getId() );
		else
		{
			formData.setFormDef(formDef);
			formDef.getFormDataList().add(formData);
		}
		//------------------------
		String oldFormRepeatKey = formData.getFormRepeatKey();
		Long oldActive = formData.getActive();
		Long oldStatus = formData.getStatus();
		
		if (formRepeatKey !=null)
			formData.setFormRepeatKey(formRepeatKey);
		if (active != null) 
			formData.setActive(active);
		
		if (status !=null) {
			formData.setStatus(status);
			formData.setStatusDate(new Date());
		}
		// add audit trail
		//fa = new FormDataAudit();
		
		fa.setNewFormRepeatKey(formData.getFormRepeatKey());
		fa.setOldFormRepeatKey(oldFormRepeatKey);
						
		fa.setOldActive(oldActive);
		fa.setNewActive(formData.getActive());
		
		fa.setOldStatus(oldStatus);
		fa.setNewStatus(formData.getStatus());
		
		populateAudit(fa, formData, user, location, reasonForUpdate, "",
				operationType);

		formData.getAuditRecordList().add(fa);
		
			
	}
	protected void populateStudyEventData(StudyEventData studyEventData, Long studyId, Long subjectDataId, Long studyEventDefId, String studyEventRepeatKey, Date effectiveDate, Long active,
			Long locationId, Long userId,
			String reasonForUpdate, int operationType) throws Exception {
		
		DBUser user = null;
		Location location = null;
		SubjectData subjectData= null;
		Study study =null;
		
		try {
			populateAuditChecks( locationId, userId, 
					reasonForUpdate);
			user = this.getUserById(userId);
			location = this.getLocation(locationId);
		
		} catch (Exception e) {
			throw e;
		}
		// connect parent links
		if (subjectDataId == null && studyEventData.getSubjectData() !=null)
			subjectData = subjectDataDao.get(studyEventData.getSubjectData().getId());
		else {
			if (subjectDataId != null)
				subjectData = subjectDataDao.get(subjectDataId); else
					throw new SubjectDataCannotBeNullException();
		}
		if (studyEventData.getId() == null) {
			studyEventData.setSubjectData(subjectData);
			subjectData.getStudyEventDataList().add(studyEventData);
		}
		if (studyId == null && studyEventData.getStudy() !=null)
			study = getStudy(studyEventData.getStudy().getId());
		else {
			if (studyId != null)
				study = getStudy(studyId);
			else 
				throw new StudyNotExistException();
		}
		studyEventData.setStudy(study);
		study.getClinicalDataList().add(studyEventData);
		//-----
		// connect to the Def links
		StudyEventDef studyEventDef = null;
		if (studyEventDefId == null && studyEventData.getStudyEventDef()!=null)
			studyEventDef = studyEventDefDao.get(studyEventData.getStudyEventDef().getId());
		else {
			if (studyEventDefId != null)
				studyEventDef = studyEventDefDao.get(studyEventDefId);
			else 
				throw new StudyEventDefCannotBeNullException();
		}
		studyEventData.setStudyEventDef(studyEventDef);
		studyEventDef.getStudyEventDataList().add(studyEventData);
		// getting the previous information
		String oldStudyEventRepeatKey = studyEventData.getStudyEventRepeatKey();
		Date oldEffectiveDate = studyEventData.getEffectiveDate();
		Long oldActive = studyEventData.getActive();
		
		if (studyEventRepeatKey !=null)
			studyEventData.setStudyEventRepeatKey(studyEventRepeatKey);
		if (effectiveDate !=null)
			studyEventData.setEffectiveDate(effectiveDate);
		if (active != null) 
			studyEventData.setActive(active);
		
		// TODO: maybe add those one. Access the location and user's subject
		// data without those one, if it works let like otherwise add them and
		// test
		// keep in mind that in such a situation we probably have to save, the
		// location and the user
		// user.getSubjectDataList().add(subjectData);
		// location.getSubjectDataList().add(subjectData);

		// add audit trail
		StudyEventDataAudit sda = new StudyEventDataAudit();
		
		sda.setNewStudyEventRepeatKey(studyEventData.getStudyEventRepeatKey());
		sda.setOldStudyEventRepeatKey(oldStudyEventRepeatKey);
		
		sda.setNewEffectiveDate(studyEventData.getEffectiveDate());
		sda.setOldEffectiveDate(oldEffectiveDate);
				
		sda.setOldActive(oldActive);
		sda.setNewActive(studyEventData.getActive());
		
		
		populateAudit(sda, studyEventData, user, location, reasonForUpdate, "",
				operationType);

		studyEventData.getAuditRecordList().add(sda);
		
			
	}
	protected void populateSubject(SubjectData subjectData, String key, Long active, Long status,
			String reasonForUpdate, Long studyId, Long locationId, Long userId, Long siteRefId, Long investigatorId, int operationType, SubjectDataAudit sda)
			throws Exception {
//  Integrity Checks

		Study study =null;
		DBUser user = null;
		Location location = null;
		Location siteRef =null;
		DBUser investigatorRef = null;
		try {
			populateAuditChecks( locationId, userId, 
					reasonForUpdate);
			if (studyId == null && subjectData.getStudy() !=null)
				study = this.getStudy(subjectData.getStudy().getId());
			else {
				if (studyId !=null)
					study = this.getStudy(studyId); else
						throw new StudyNotExistException();
			}
			user = this.getUserById(userId);
			location = this.getLocation(locationId);
		
			if (siteRefId != null)
				siteRef = this.getLocation(siteRefId);
				if (investigatorId !=null )
			investigatorRef = this.getUserById(investigatorId);
		
		} catch (Exception e) {
			throw e;
		}
		try {
			if (subjectData.getSubjectKey()==null)
				checkSubject(key, study, location, user);
		} catch (Exception e) {
			throw e;
		}
		Location oldSiteRef = subjectData.getSiteRef();
		DBUser oldInvestigatorRef = subjectData.getInvestigatorRef();
		if (investigatorRef !=null) {
			subjectData.setInvestigatorRef(investigatorRef);
			investigatorRef.getSubjectDataList().add(subjectData);
		}
		if (siteRef !=null) {
			subjectData.setSiteRef(siteRef);
			siteRef.getSubjectDataList().add(subjectData);
		}
		
		if (study !=null) {
			subjectData.setStudy(study);
			study.getClinicalDataList().add(subjectData);
		}
		String oldKey = subjectData.getSubjectKey();
		Long oldActive = subjectData.getActive();
		Long oldStatus = subjectData.getStatus();
		if (key!=null)
			subjectData.setSubjectKey(key);
		if (active !=null )
			subjectData.setActive(active);
		if (status !=null) {
			subjectData.setStatus(status);
			subjectData.setStatusDate(new Date());
		}
		
	
		// TODO: maybe add those one. Access the location and user's subject
		// data without those one, if it works let like otherwise add them and
		// test
		// keep in mind that in such a situation we probably have to save, the
		// location and the user
		// user.getSubjectDataList().add(subjectData);
		// location.getSubjectDataList().add(subjectData);

		// add audit trail
		
		sda.setNewSubjectKey(subjectData.getSubjectKey());
		sda.setOldSubjectKey(oldKey);
		
		sda.setOldActive(oldActive);
		sda.setNewActive(subjectData.getActive());
		
		sda.setOldStatus(oldStatus);
		sda.setNewStatus(subjectData.getStatus());
		if (oldInvestigatorRef !=null)
			sda.setOldInvestigatorRefId(oldInvestigatorRef.getId());
		sda.setNewInvestigatorRefId(subjectData.getInvestigatorRef().getId());
		
		if (oldSiteRef != null)
			sda.setOldSiteRefId(oldSiteRef.getId());
		sda.setNewSiteRefId(subjectData.getSiteRef().getId());
		
		populateAudit(sda, subjectData, user, location, reasonForUpdate, "",
				operationType);

		subjectData.getAuditRecordList().add(sda);

	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public SubjectData createSubjectData(String key, Long studyId, Long locationId,
			Long userId, Long siteRefId, Long investigatorId) throws Exception {

		SubjectData subjectData = null;
		try {
			subjectData = new SubjectData();
			SubjectDataAudit sda = new SubjectDataAudit();
			populateSubject(subjectData, key, new Long(SubjectData.ACTIVE_YES), new Long(SubjectData.SUBJECT_SCREENING), AUDIT_SUBJECT_CREATION, studyId,
					locationId, userId,  siteRefId,  investigatorId, AuditRecord.CREATE_SUBJECT, sda);
			subjectDataDao.add(subjectData);
		} catch (Exception e) {
			throw e;
		}
		return subjectData;

	}

	/***
	 * update the subject data properties, null value means that the previous value is kept
	 * in the other hand, reasonForUpdate, userId and locationId cannot be null, due to the audit trail
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateSubject(Long id, String key, Long active, Long status, String reasonForUpdate,
			Long studyId, Long locationId, Long userId, Long siteRefId, Long investigatorId) throws Exception {
		SubjectData subjectData = subjectDataDao.get(id);
		try {
			SubjectDataAudit sda = new SubjectDataAudit();
			populateSubject(subjectData, key,active, status, reasonForUpdate, studyId,
					locationId, userId, siteRefId,  investigatorId, AuditRecord.UPDATE_SUBJECT, sda);
			revokeSignature(subjectData, sda);
			subjectDataDao.update(subjectData);
		} catch (Exception e) {
			throw e;
		}

	}
	
	
	public FormData createFormData(Long studyId, Long studyEventDataId, Long formDefId,
			String formRepeatKey, Long status, Long active, Long locationId, Long userId, FormDataAudit formDataAudit)
			throws Exception {
		FormData formData = new FormData();
		try {
			populateFormData(formData, studyId, studyEventDataId, formDefId, formRepeatKey,
					status, active, AUDIT_FORM_CREATION, locationId, userId, AuditRecord.CREATE_FORM, formDataAudit);
			//createFormItemGroupData(formData, studyId, status, active, locationId, userId);
			formDataDao.add(formData);
		} catch(Exception e) {
			throw e;
		}
		return formData;
	}
	
	
	/***
	 * Ensure transactional saving of the item groups and item defs of the form
	 * @param formData
	 * @param itemGroupDataList
	 * @param status
	 * @param active
	 * @param reasonForUpdate
	 * @param locationId
	 * @param userId
	 * @param operationType
	 * @throws Exception
	 */
//	protected void populateFormItemGroupData(FormData formData,
//			 Long status, Long active,
//			String reasonForUpdate, Long locationId, Long userId,
//			int operationType) throws Exception {
//		DBUser user = null;
//		Location location = null;
//		Study study =formData.getStudy();
//		try {
//			populateAuditChecks(locationId, userId, reasonForUpdate);
//			user = getUserById(userId);
//			location = getLocation(locationId);
//		} catch (Exception e) {
//			throw e;
//		}
//		for (ItemGroupData igd : formData.getItemGroupDataList()) {


//		}
		
//	}
//	@Override
//	public void createFormItemGroupData(FormData formData,
//			 Long studyId, Long status, Long active,
//			Long locationId, Long userId) throws Exception {
//		try {
//		populateFormItemGroupData(formData,  status, active,
//				AUDIT_FORM_CREATION, locationId, userId, AuditRecord.CREATE_FORM);
//		} catch(Exception e) {
//			throw e;
//		}
//	}

//	@Override
//	public void updateFormItemGroupData(FormData formData,
//			 Long studyId, Long status, Long active,
//			String reasonForUpdate, Long locationId, Long userId,
//			int operationType) throws Exception {
//		try {
//		populateFormItemGroupData(formData, status, active,
//				reasonForUpdate, locationId, userId, operationType);
//		} catch(Exception e) {
//			throw e;
//		}
//		
//	}

	
	public void updateFormData(Long studyId, Long formDataId, Long studyEventDataId,
			Long formDefId, String formRepeatKey, Long status, Long active,
			Long locationId, Long userId, String reasonForUpdate,
			int operationType, FormDataAudit formDataAudit)
			throws Exception {
			
		FormData formData = formDataDao.get(formDataId);
		try {
			populateFormData(formData, studyId, studyEventDataId, formDefId, formRepeatKey,
					status, active, reasonForUpdate, locationId, userId, AuditRecord.UPDATE_FORM, formDataAudit );
		//	updateFormItemGroupData(formData, studyId, status, active, reasonForUpdate, locationId, userId, operationType);
			formDataDao.update(formData);
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public StudyEventData createStudyEventData(Long studyId, Long subjectDataId, Long studyEventDefId, String studyEventRepeatKey, Date effectiveDate, Long active, 
			Long locationId, Long userId) throws Exception {
		
		StudyEventData sed = new StudyEventData();
		try {
			populateStudyEventData(sed, studyId, subjectDataId, studyEventDefId, studyEventRepeatKey, effectiveDate, active,
					locationId, userId, AUDIT_STUDYEVENT_CREATION, AuditRecord.CREATE_STUDYEVENT);
			studyEventDataDao.add(sed);
		} catch (Exception e) {
			throw e;
		}
		return sed;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateStudyEventData(Long studyId, Long studyEventDataId, Long subjectDataId,
			Long studyEventDefId, String studyEventRepeatKey,
			Date effectiveDate, Long active, Long locationId, Long userId,
			String reasonForUpdate, int operationType) throws Exception {
		StudyEventData studyEventData = studyEventDataDao.get(studyEventDataId);
		try {
			populateStudyEventData(studyEventData, studyId, subjectDataId, studyEventDefId, studyEventRepeatKey, effectiveDate, active,
					locationId, userId, reasonForUpdate, operationType);
			studyEventDataDao.update(studyEventData);
		} catch(Exception e) {
			throw e;
		}
	}

	// TODO:Implements the signatureCheck
	protected void checkSignature(Study study, Location location, DBUser user,
			String sSignature) throws Exception {
		// check user authorities
		// check user grants
		// compare signature
		// if (!user.signature.equals(sSignature)) throw new
		// InvalidSignatureException();
	}
	protected void revokeSignature(ClinicalData clinicalData, AuditRecord auditRecord) {

		Signature signature = signatureDao.findCurrentSignature(clinicalData);
		if (signature !=null && signature.getStatus() == Signature.SIGNED) {
			SignatureDef signatureDef = signature.getSignatureDef();
			Signature revokedSignature = new Signature();
			revokedSignature.setClinicalData(clinicalData);
			revokedSignature.setDateTimeStamp(new Date());
			revokedSignature.setSignatureDef(signatureDef);
			revokedSignature.setStatus(new Long(Signature.REVOKED));
			if (auditRecord != null) {
				auditRecord.setSignator("");
				auditRecord.setSignLocation("");
				auditRecord.setSignStatus(new Long(Signature.REVOKED));
			}
		
			clinicalData.getSignatureList().add(revokedSignature);
		}	
	}
	/**
	 * sign a clinical data. Before calling this function, the signature must be already checked.
	 * @param clinicalDataId
	 * @param signatureDefId
	 * @param locationId
	 * @param userId
	 * @throws Exception
	 */
	public void signClinicalData(Long clinicalDataId, Long signatureDefId,
			Long locationId, Long userId, AuditRecord auditRecord) throws Exception {
		try {
			DBUser user = this.getUserById(userId);
			Location location = this.getLocation(locationId);
			SignatureDef signatureDef = signatureDefDao.get(signatureDefId);
			ClinicalData clinicalData = clinicalDataDao.get(clinicalDataId);

			Signature signature = new Signature();
			signature.setClinicalData(clinicalData);
			signature.setDateTimeStamp(new Date());
			signature.setLocationRef(location);
			signature.setSignatureDef(signatureDef);
			signature.setStatus(new Long(Signature.SIGNED));
			signature.setUserRef(user);

			clinicalData.getSignatureList().add(signature);

			// TODO: maybe add those one. Access the location and user's subject
			// data without those one, if it works let like otherwise add them
			// and test
			// keep in mind that in such a situation we probably have to save,
			// the location and the user
			// user.getSubjectDataList().add(subjectData);
			// location.getSubjectDataList().add(subjectData);
			if (auditRecord != null) {
				auditRecord.setSignator(ControllerAdvice.getFormattedName(user.getFirstName(), user.getLastName(), user.getName()));
				auditRecord.setSignLocation(ControllerAdvice.getFormattedLocation(location.getShortName(), location.getName()));
				auditRecord.setSignStatus(new Long(Signature.SIGNED));
			}
				
			clinicalDataDao.add(clinicalData);
			
		} catch (Exception e) {
			throw e;
		}

	}
	/***
	 * Ensure the transactional signature of the form item groupq/items
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void signFormData(Long formDataId, Long signatureDefId, Long locationId, Long userId, AuditRecord auditRecord) throws Exception{
		FormData formData = formDataDao.get(formDataId);
//		for (ItemGroupData igd : formData.getItemGroupDataList()) {
//			for (ItemData id : igd.getItemDataList()) 
//				signClinicalData(id.getId(), signatureDefId, locationId, userId);
//			signClinicalData(igd.getId(), signatureDefId, locationId, userId );
//		}
		signClinicalData(formDataId, signatureDefId, locationId, userId, auditRecord);
	}
	public Study getStudy(Long id) {
		return studyDao.get(id);
	}

	public DBUser getUserById(Long id) {
		return dbUserDao.get(id);
	}

	public Location getLocation(Long id) {
		return locationDao.get(id);
	}
	@Override
	public List<SubjectDataAudit> getSubjectDataAuditRecords(Long subjectDataId) {
		return subjectDataAuditDao.findSubjectDataAuditRecords(subjectDataId);
	}
	@Override
	public List<StudyEventDataAudit> getStudyEventDataAuditRecords(Long studyEventDataId) {
		return studyEventDataAuditDao.findStudyEventDataAuditRecords(studyEventDataId);
	}
	@Override
	public List<FormDataAudit> getFormDataAuditRecords(Long formDataId) {
		return formDataAuditDao.findFormDataAuditRecords(formDataId);
	}
	@Override 
	public List<ItemGroupDataAudit> getItemGroupDataAuditRecords(Long itemGroupDataId) {
		return itemGroupDataAuditDao.findItemGroupDataAuditRecords(itemGroupDataId);
	}
	@Override
	public List<ItemDataAudit> getItemDataAuditRecords(Long itemDataId) {
		return itemDataAuditDao.findItemDataAuditRecords(itemDataId);
	}
	public Signature getCurrentSignature(Long clinicalDataId) {
		return signatureDao.findCurrentSignature(clinicalDataId);
	}
	public void populateItemGroupData(FormDataAudit formDataAudit, ItemGroupData igd, String itemGroupRepeatKey, Long status, Long userId, Long locationId, String reasonForUpdate, int operationType, ItemGroupDataAudit igda) throws Exception {
		DBUser user = null;
		Location location = null;
		try {
			populateAuditChecks( locationId, userId, 
					reasonForUpdate);
			user = this.getUserById(userId);
			location = this.getLocation(locationId);			
		} catch (Exception e) {
			throw e;
		}
		if (igd.getItemGroupDef() == null) throw new ItemGroupDefCannotBeNullException();
		if (igd.getItemGroupRepeatKey() == null) throw new ItemGroupRepeatKeyCannotBeNullException();
		this.backupItemGroupData(igd);
		igd.setActive(new Long(ClinicalData.ACTIVE_YES));
		igd.setStatusDate(new Date());
		igd.setItemGroupRepeatKey(itemGroupRepeatKey);
		// if it does not exist in the form => add it
			
		//igd.getItemGroupDef().getItemGroupDataList().add(igd);

		// TODO: maybe add those one. Access the location and user's subject
		// data without those one, if it works let like otherwise add them and
		// test
		// keep in mind that in such a situation we probably have to save, the
		// location and the user
	
		
		igda.setFormDataAudit(formDataAudit);
		formDataAudit.getItemGroupDataAuditList().add(igda);
		
		igda.setNewItemGroupRepeatKey(igd.getItemGroupRepeatKey());

		igda.setOldItemGroupRepeatKey(igd.getOldItemGroupRepeatKey());
		igda.setOldActive(igd.getOldActive());
		igda.setOldStatus(igd.getOldStatus());
		
		igda.setNewActive(igd.getActive());
		igda.setNewStatus(status);
		
		populateAudit(igda, igd, user, location, reasonForUpdate, "", operationType);
		igd.getAuditRecordList().add(igda);
		
	}
	
	protected ItemGroupData addItemGroupData(FormData formData, FormDataAudit formDataAudit, String itemGroupRepeatKey, ItemGroupDef itemGroupDef, Long status, Long userId, Long locationId, ItemGroupDataAudit itemGroupDataAudit ) throws Exception {
		ItemGroupData igd = new ItemGroupData();
		igd.setItemGroupRepeatKey(itemGroupRepeatKey);
		igd.setItemGroupDef(itemGroupDef);
		itemGroupDef.getItemGroupDataList().add(igd);
		igd.setFormData(formData);
		formData.getItemGroupDataList().add(igd);
		igd.setFormData(formData);
		igd.setStudy(formData.getStudy());
	
		formData.getStudy().getClinicalDataList().add(igd);
		try {
			populateItemGroupData(formDataAudit, igd, itemGroupRepeatKey, status, userId, locationId, AUDIT_ITEMGROUP_CREATION, AuditRecord.CREATE_ITEMGROUP, itemGroupDataAudit);
		}catch (Exception e) {
			throw e;
		}
		
		return igd;
	}
	
	protected void updateItemGroupData(FormDataAudit formDataAudit, ItemGroupData igd, String itemGroupRepeatKey, Long status, Long userId, Long locationId, String reasonForUpdate, int operationType, ItemGroupDataAudit itemGroupDataAudit ) throws Exception {
		try {
			igd.setStatusDate(new Date());
			populateItemGroupData(formDataAudit, igd, itemGroupRepeatKey, status, userId, locationId, reasonForUpdate,operationType, itemGroupDataAudit);
		}catch (Exception e) {
			throw e;
		}
		
	}

	protected void updateItemData( ItemGroupDataAudit itemGroupDataAudit, ItemData itemData,  Long isNull, String codeListValue, String value, Long status, Long userId, Long locationId, String reasonForUpdate, int operationType) throws Exception {
		try {
			itemData.setStatusDate(new Date());
			populateItemData( itemGroupDataAudit, itemData, isNull, codeListValue, value, status, userId, locationId, reasonForUpdate, operationType);
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@Override
	public void backupItemData(ItemData id) {
		id.setOldActive(id.getActive());
		id.setOldCodeListValue(id.getCodeListValue());
		id.setOldStatus(id.getStatus());
		id.setOldStatusDate(id.getStatusDate());
		id.setOldIsNull(id.getIsNull());
		id.setOldValue(id.getValue());
	}

	@Override
	public void backupItemGroupData(ItemGroupData igd) {
		igd.setOldActive(igd.getActive());
		igd.setOldStatus(igd.getStatus());
		igd.setOldStatusDate(igd.getStatusDate());
		igd.setOldItemGroupRepeatKey(igd.getItemGroupRepeatKey());
		
	}

	protected void populateItemData( ItemGroupDataAudit itemGroupDataAudit, ItemData id, Long isNull, String codeListValue, String value, Long status, Long userId, Long locationId, String reasonForUpdate, int operationType  ) throws Exception {
		// Store the old value
		DBUser user = null;
		Location location = null;
		try {
			populateAuditChecks( locationId, userId, 
					reasonForUpdate);
			user = this.getUserById(userId);
			location = this.getLocation(locationId);			
		} catch (Exception e) {
			throw e;
		}
		backupItemData(id);
		id.setActive(new Long(ClinicalData.ACTIVE_YES));
		id.setStatusDate(new Date());

		id.setDirty(true);
		
		id.setIsNull(isNull);
		id.setCodeListValue(codeListValue);
		id.setValue(value);
		
		ItemDataAudit ida = new ItemDataAudit();
		ida.setItemGroupDataAudit(itemGroupDataAudit);
		itemGroupDataAudit.getItemDataAuditList().add(ida);
		
		ida.setOldActive(id.getOldActive());
		ida.setOldCodeListValue(id.getOldCodeListValue());
		ida.setOldIsNull(id.getOldIsNull());
		ida.setOldStatus(id.getOldStatus());
		ida.setOldValue(id.getOldValue());
		
		ida.setNewActive(id.getActive());
		ida.setNewCodeListValue(id.getCodeListValue());
		ida.setNewIsnull(id.getIsNull());
		ida.setNewStatus(status);
		ida.setNewValue(id.getValue());
		populateAudit(ida, id, user, location, reasonForUpdate, "", operationType);
		id.getAuditRecordList().add(ida);

		
	}

	protected void addItemData(ItemGroupDataAudit itemGroupDataAudit, ItemGroupData itemGroupData, ItemDef itemDef,  Long isNull, String codeListValue, String value, Long status, Long userId, Long locationId) throws Exception {
		ItemData id = new ItemData();
		id.setItemDef(itemDef);
		itemDef.getItemDataList().add(id);
		id.setItemGroupData(itemGroupData);	
		itemGroupData.getItemDataList().add(id);
		
		try {
			populateItemData(itemGroupDataAudit, id, isNull, codeListValue, value, status, userId, locationId, AUDIT_ITEM_CREATION, AuditRecord.CREATE_ITEM);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<SubjectData> getSubjectDataByStudyByUser(Study study,
			DBUser user) {
		return subjectDataDao.findSubjectDataByStudyByUser(study.getId(), user.getId());
	}

	@Override
	public SubjectData getSubjectDataById(Long id) {
		return subjectDataDao.get(id);
	}

	@Override
	public List<SubjectData> getSubjectDataByStudyByUser(Long studyId,
			Long userId) {
		return subjectDataDao.findSubjectDataByStudyByUser(studyId, userId);
	}

	@Override
	public List<StudyEventData> getStudyEventDataBySubject(
			Long subjectDataId) {
		return studyEventDataDao.findStudyEventDataBySubject( subjectDataId);
	}

	@Override
	public FormData getFormDataById(Long id) {
	
		return formDataDao.get(id);
	}

	@Override
	public List<StudyEventData> getStudyEventDataBySubjectIdByStudyEventDefId(
			Long studyId, Long subjectId, Long studyEventDefId) {
	
		return studyEventDataDao.findStudyEventDataBySubjectIdByStudyEventDefId(studyId, subjectId, studyEventDefId);
	}

	@Override
	public List<FormData> getFormDataBySubjectIdByFormDefId(Long studyId,
			Long studyEventDefId, Long formDefId, Long subjectId) {
		
		return formDataDao.findFormDataBySubjectIdByFormDefId(studyId, studyEventDefId, formDefId, subjectId);
	}
	
	protected ItemData getExistingItemData(ItemGroupData itgdata, ItemDef idef) {
		for (ItemData id : itgdata.getItemDataList() ) {
			if (id.getItemDef().getId()==idef.getId()) 
				return id;
		}
		return null;
		
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addUpdateFormData(Long studyId, Long locationId, Long userId,
			Long subjectDataId, Long studyEventDefId, Long studyEventDataId, Long formDefId, Long formDataId,
			Long status, String reasonForUpdate,  Map<String, String> itemsDef, Map<String, String> itemsDisp, Map<String, String> itemsDirty,  Long signatorId, Long signatureDefId) throws Exception {
		StudyEventData studyEventData = null;
		FormData formData= null;
		ItemData itemData = null;
		FormDataAudit formDataAudit = new FormDataAudit();
		FormDef formDef = formDefDao.get(formDefId);
		
		if (formDataId != null)
			formData = getFormDataById(formDataId);

		
		try {
			if (studyEventDataId == null) {
				studyEventData = createStudyEventData(studyId, subjectDataId, studyEventDefId, "1", new Date(),  new Long(ClinicalData.ACTIVE_YES), locationId, userId);
				studyEventDataId = studyEventData.getId();	
			}
			if (formData == null ) {
				formData = createFormData(studyId, studyEventDataId, formDefId, "1", status, new Long(ClinicalData.ACTIVE_YES), locationId, userId, formDataAudit);
				Long itemGroupRepeatKeyInt = new Long(1);
				// create item group
				for (ItemGroupRef itgr : formDef.getItemGroupRefList()) {
					ItemGroupDef itg = itgr.getItemGroupDef();
					ItemGroupDataAudit itemGroupDataAudit = new ItemGroupDataAudit();
					// Even if there is no field defined for the ItemGroupData, as we are in the form creation, we already create the itemgroupdata
					// In the other hand, if we change the status 
					ItemGroupData itgdata = addItemGroupData(formData, formDataAudit, String.valueOf(itemGroupRepeatKeyInt), itg, status, userId, locationId, itemGroupDataAudit);
					itemGroupRepeatKeyInt++;
					for (ItemRef ir : itg.getItemRefList()) {
						ItemDef id = ir.getItemDef();
						itemData = getExistingItemData( itgdata, id);		
						String value="";
						String codedValue= "";
						if (itemsDirty.get("item-dirty-"+itg.getId()+"-"+id.getId()).equals("t")) {			
							if (id.getCodeList() !=null) {
								codedValue =itemsDef.get("item-def-"+itg.getId()+"-"+id.getId());
								value = itemsDisp.get("item-display-"+itg.getId()+"-"+id.getId()+"-"+codedValue);

							} else
								value = itemsDef.get("item-def-"+itg.getId()+"-"+id.getId());
							addItemData( itemGroupDataAudit,itgdata, id, new Long(ItemData.ISNULL_NO),codedValue, value, status, userId, locationId);

						} else // new case
							addItemData( itemGroupDataAudit, itgdata, id, new Long(ItemData.ISNULL_YES),"", "",  status, userId, locationId);

					} // for

				} // end for
			} else { // form data is not null
				// Study event is not null as form data exists
	
				boolean statusChanged = formData.getStatus()==status ? false : true;
				boolean itemGroupDirty=false;
				updateFormData(studyId, formDataId, studyEventDataId, formDefId, null, status, null, locationId, userId, reasonForUpdate,AuditRecord.UPDATE_FORM, formDataAudit);
				
				for (ItemGroupRef itgr : formDef.getItemGroupRefList()) {
					ItemGroupDef itg = itgr.getItemGroupDef();
					if (! statusChanged) {
					itemGroupDirty = false;
					for (ItemRef ir : itg.getItemRefList()) {
						if (itemsDirty.get("item-dirty-"+itg.getId()+"-"+ir.getItemDef().getId()).equals("t")) {
							itemGroupDirty =true;
							break;
						}
					} 
					} else itemGroupDirty = true;
					
					if (itemGroupDirty) {
						ItemGroupData curItg = null;
						for (ItemGroupData itgdata : formData.getItemGroupDataList()) {
							if (itgdata.getItemGroupDef().getId()==itg.getId()) {
							 curItg = itgdata;
							 break;
							}
						}
						ItemGroupDataAudit itemGroupDataAudit = new ItemGroupDataAudit();
						updateItemGroupData(formDataAudit, curItg, curItg.getItemGroupRepeatKey(), status, userId, locationId, reasonForUpdate, AuditRecord.UPDATE_ITEMGROUP, itemGroupDataAudit);
						for (ItemRef ir : itg.getItemRefList()) {
							ItemDef id = ir.getItemDef();
							itemData = getExistingItemData( curItg, id);		
							String value="";
							String codedValue= "";
							if (itemsDirty.get("item-dirty-"+itg.getId()+"-"+id.getId()).equals("t")) {			
								if (id.getCodeList() !=null) {
									codedValue =itemsDef.get("item-def-"+itg.getId()+"-"+id.getId());
									value = itemsDisp.get("item-display-"+itg.getId()+"-"+id.getId()+"-"+codedValue);

								} else
									value = itemsDef.get("item-def-"+itg.getId()+"-"+id.getId());
								if (itemData == null ) 
									addItemData(itemGroupDataAudit, curItg, id, new Long(ItemData.ISNULL_NO),codedValue, value, status, userId, locationId);
								else
									updateItemData(itemGroupDataAudit, itemData, new Long(ItemData.ISNULL_NO), codedValue, value,status, userId, locationId, reasonForUpdate, AuditRecord.UPDATE_ITEM);
							} 

						} // for
					}	
					
				}
		
			}	
			if (status==FormData.STATUS_SIGNED) {
				// sign the form
				this.signFormData(formDataId, signatureDefId, locationId, userId, formDataAudit);
			}
		} catch (Exception e) {
			throw e;
		}
	
		
	}

	@Override
	public List<SubjectData> getSubjectDataByStudyForReport(Long studyId) {
		return subjectDataDao.findSubjectDataByStudyForReport(studyId);
	}
	
	
}
