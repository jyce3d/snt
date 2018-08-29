package be.sdlg.snt;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.security.core.Authentication;

import be.sdlg.snt.model.Authority;
import be.sdlg.snt.model.CodeList;
import be.sdlg.snt.model.DBProper;
import be.sdlg.snt.model.Decode;
import be.sdlg.snt.model.Description;
import be.sdlg.snt.model.FormDef;
import be.sdlg.snt.model.FormRef;
import be.sdlg.snt.model.ItemDef;
import be.sdlg.snt.model.ItemGroupDef;
import be.sdlg.snt.model.ItemGroupRef;
import be.sdlg.snt.model.ItemRef;
import be.sdlg.snt.model.Location;
import be.sdlg.snt.model.MetadataVersion;
import be.sdlg.snt.model.Question;
import be.sdlg.snt.model.SignatureDef;
import be.sdlg.snt.model.Study;
import be.sdlg.snt.model.Grant;
import be.sdlg.snt.model.DBUser;
import be.sdlg.snt.model.StudyEventDef;
import be.sdlg.snt.model.StudyEventRef;
import be.sdlg.snt.model.StudyLocation;

public interface AdministrationSvc {
	public DBUser createUser(String name, String password, boolean enabled, String email, List<String> authorities) throws Exception;
	public void updateUserProperties(Long userId, String password, String signature,  boolean enabled, String email) throws Exception;
	public void updateAuthorities(Long userId, List<String> authorities) throws Exception;
	public void deleteUser(DBUser user);
	 public DBUser getUserById(Long id);
	 public DBUser getUserByLogin(String login);
	 public List<DBUser> getUserList();
	 public boolean isValidPassword(DBUser user, String password);
	public Study createStudy(String studyName, String studyDescription, Date effectiveDate, String metadataVersionName, String metadataVersionDescr, Long metadataVersionId, Locale locale) throws Exception;
	public List<Study> getStudiesByUserId(Long userId);
	public SignatureDef createSignatureDef(Long studyId, String meaning, String legalReason, Long methodology, Long signatureUsage) throws Exception;

	public Study getStudy(Long id);
	public Location getLocation(Long id);
	public void saveStudy(Study study);
	public void updateGrants(Long studyId, Long userId, Long locationId, List<Grant> grants) throws Exception;
	public MetadataVersion getCurrentMetadataVersion(Study study);
	
	public StudyEventDef createStudyEventDef(Long studyId, String category, Long type, Long repeating, String studyEventName, String studyEventDescription, Locale locale ) throws Exception;
 
	public StudyEventRef createStudyEventRef(Long protocolId, StudyEventDef studyEventDef, StudyEventRef predecessor, Long gapWithPredecessor, Long toleranceMin, Long toleranceMax, Long mandatory ) throws Exception;
	public void addStudyEventRefPredecessor(StudyEventRef current, StudyEventRef predecessor) throws Exception;
	public FormDef createFormDef(Long studyId, String name, String description, Long repeating, Locale locale ) throws Exception;
	public FormRef createFormRef(Long studyEventDefId, FormDef formDef, Long orderNumber, Long mandatory) throws Exception;
	public ItemGroupDef createItemGroupDef(Long studyId, String name, String description,
			String domain, String comment, String origin, String purpose, Long repeating, 
			String sasDatasetName, Locale locale) throws Exception;
	public ItemGroupRef createItemGroupRef(Long formDefId, ItemGroupDef itemGroupDef,
			Long mandatory, Long orderNumber ) throws Exception;
	public CodeList createCodeList(Long studyId, String name, String description,
			Long dataType, String sasFormatName, Locale locale ) throws Exception;
	public void addCodeListItem(Long codeListId, String codedValue, String decodeValue, Float rank, Locale locale) throws Exception;
	public ItemDef createItemDef(Long studyId, String comment, Long dataType,
			String name, String description, String origin, String sasFieldName, String sdsVarName,
			Long significantDigits, String question, Long codeListId, Locale locale) throws Exception;
	public ItemRef createItemRef(Long itemGroupDefId, ItemDef itemDef, Long keySequence,
			Long mandatory, Long orderNumber, String role	) throws Exception ;
	public List<Authority> getAuthorityList(boolean retrieveUser);
	public boolean isPasswordEquals(DBUser user, String password);
	public boolean isValidSignature(DBUser user, String signature);
	public List<Study> getStudies();
	public boolean hasRole(Authentication auth, String role);
	public List<Location> getLocations();
	public List<DBProper> getProperList(Long studyId, Long locationId);
	public List<Location> getStudyLocationByStudyByUser(Long studyId, Long userId);

	public List<DBUser> getUserByStudyByLocation(Long studyId, Long locationId, Long grant);
	public boolean hasGrant(Long studyId, Long locationId, Long userId, Long g);
	public String getDescription(Description description, Locale locale);
	public String getMessage(String message, Locale locale);
	public StudyEventDef getStudyEventDefById (Long sdId);
	public FormDef getFormDefById(Long fdId);
	
	public String getDecode(Decode decode, Locale locale);
	public String getQuestion(Question question, Locale locale);
	
	public SignatureDef getSignatureDef(Long studyId, Long signatureUsage);
	// signature
	public boolean isMatchingSignature(Long studyId, Long locationId, String signature, DBUser user );
	public boolean isSignatureEquals(DBUser user, String password);




}
