package be.sdlg.snt;

import java.util.Date;
import java.util.List;
import java.util.Map;


import be.sdlg.snt.model.FormDataAudit;
import be.sdlg.snt.model.ItemData;
import be.sdlg.snt.model.ItemDataAudit;

import be.sdlg.snt.model.ItemGroupData;
import be.sdlg.snt.model.DBUser;
import be.sdlg.snt.model.FormData;
import be.sdlg.snt.model.ItemGroupDataAudit;

import be.sdlg.snt.model.AuditRecord;
import be.sdlg.snt.model.Location;
import be.sdlg.snt.model.Signature;
import be.sdlg.snt.model.Study;
import be.sdlg.snt.model.StudyEventData;
import be.sdlg.snt.model.StudyEventDataAudit;
import be.sdlg.snt.model.SubjectData;
import be.sdlg.snt.model.SubjectDataAudit;

public interface EDCSvc {
		// Transactional methods
		public SubjectData createSubjectData(String key,Long studyId, Long locationId, Long userId, Long siteRefId, Long investigatorId  ) throws Exception;
		public void updateSubject(Long subjectDataId, String key, Long active, Long status,  String reasonForUpdate, Long studyId, Long locationId, Long userId, Long siteRefId, Long investigatorId  ) throws Exception;
		public SubjectData getSubjectDataById(Long id);
		public StudyEventData createStudyEventData(Long studyId, Long subjectDataId, Long studyEventDefId, String studyEventRepeatKey, Date effectiveDate, Long active, 
				Long locationId, Long userId) throws Exception;
		public void updateStudyEventData(Long studyId, Long studyEventDataId,Long subjectDataId, Long studyEventDefId, String studyEventRepeatKey, Date effectiveDate, Long active, 
				Long locationId, Long userId,
				String reasonForUpdate, int operationType ) throws Exception;
	//	public FormData createFormData(Long studyId, Long studyEventDataId, Long formDefId,
	//		String formRepeatKey, Long status, Long active, Long locationId, Long userId ) throws Exception;
	//	public void updateFormData (Long studyId, Long formDataId,  Long studyEventDataId, Long formDefId,
	//			String formRepeatKey, Long status, Long active, Long locationId, Long userId, String reasonForUpdate, int operationType) throws Exception;

		public void signFormData(Long formDataId, Long signatureDefId, Long locationId, Long userId,  AuditRecord auditRecord) throws Exception;

		// Non transactionall (SELECT or part of a more complex operation)
		public void signClinicalData(Long clinicalDataId, Long signatureDefId, Long locationId,Long userId, AuditRecord auditRecord) throws Exception;

		public Study getStudy(Long id);
		public DBUser getUserById(Long id);
		public Location getLocation(Long id);
		public List<SubjectDataAudit> getSubjectDataAuditRecords(Long subjectDataId);
		public Signature getCurrentSignature(Long subjectDataId);
		
		public List<StudyEventDataAudit> getStudyEventDataAuditRecords(Long studyEventDataId);
		public List<FormDataAudit> getFormDataAuditRecords(Long formDataId);
		public List<ItemGroupDataAudit> getItemGroupDataAuditRecords(Long itemGroupDataId);
		public List<ItemDataAudit> getItemDataAuditRecords(Long itemDataId);
		public List<SubjectData> getSubjectDataByStudyByUser(Study study, DBUser user);
		public List<SubjectData> getSubjectDataByStudyByUser(Long studyId, Long userId);
		public List<StudyEventData> getStudyEventDataBySubject( Long subjectDataId);
		public FormData getFormDataById(Long id);
		public List<StudyEventData> getStudyEventDataBySubjectIdByStudyEventDefId(Long studyId, Long subjectId,  Long studyEventDefId);
		public List<FormData> getFormDataBySubjectIdByFormDefId(Long studyId, Long studyEventDefId, Long formDefId, Long subjectId);
		public void backupItemData(ItemData id);
		public void backupItemGroupData(ItemGroupData igd);
		public void addUpdateFormData( Long studyId, Long locationId, Long userId, Long subjectDataId, Long studyEventDefId, Long studyEventDataId, Long formDefId, Long formDataId, Long status, String reasonForUpdate, Map<String, String> itemsDef, Map<String, String> itemsDisp, Map<String, String> itemsDirty,Long signatorId, Long signatureDefId  ) throws Exception;
		public List<SubjectData> getSubjectDataByStudyForReport(Long studyId);
}

