// javadoc : http://www.oracle.com/technetwork/java/javase/documentation/index-137868.html
package be.sdlg.snt.impl;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import be.sdlg.snt.AdministrationSvc;
import be.sdlg.snt.PasswordValidator;
import be.sdlg.snt.UserEx;
import be.sdlg.snt.dao.CodeListDao;
import be.sdlg.snt.dao.CodeListItemDao;
import be.sdlg.snt.dao.DBProperDao;
import be.sdlg.snt.dao.DBUserDao;
import be.sdlg.snt.dao.DecodeDao;
import be.sdlg.snt.dao.DescriptionDao;
import be.sdlg.snt.dao.ItemDefDao;
import be.sdlg.snt.dao.ItemGroupDefDao;
import be.sdlg.snt.dao.ItemGroupRefDao;
import be.sdlg.snt.dao.ItemRefDao;
import be.sdlg.snt.dao.LocationDao;
import be.sdlg.snt.dao.MetadataVersionDao;
import be.sdlg.snt.dao.ProtocolDao;
import be.sdlg.snt.dao.QuestionDao;
import be.sdlg.snt.dao.StudyDao;
import be.sdlg.snt.dao.StudyEventDefDao;
import be.sdlg.snt.dao.StudyEventRefDao;
import be.sdlg.snt.dao.StudyLocationDao;
import be.sdlg.snt.dao.StudyUserDao;
import be.sdlg.snt.dao.SignatureDefDao;
import be.sdlg.snt.dao.FormRefDao;
import be.sdlg.snt.dao.FormDefDao;

import be.sdlg.snt.exceptions.StudyNotExistException;
import be.sdlg.snt.exceptions.UserNotExistException;
import be.sdlg.snt.model.Authority;
import be.sdlg.snt.model.ClinicalDef;
import be.sdlg.snt.model.CodeList;
import be.sdlg.snt.model.CodeListItem;
import be.sdlg.snt.model.DBProper;
import be.sdlg.snt.model.DBUser;
import be.sdlg.snt.model.Decode;
import be.sdlg.snt.model.Description;
import be.sdlg.snt.model.FormDef;
import be.sdlg.snt.model.FormRef;
import be.sdlg.snt.model.Grant;
import be.sdlg.snt.model.ItemDef;
import be.sdlg.snt.model.ItemGroupDef;
import be.sdlg.snt.model.ItemGroupRef;
import be.sdlg.snt.model.ItemRef;
import be.sdlg.snt.model.Location;
import be.sdlg.snt.model.MetadataVersion;
import be.sdlg.snt.model.MetadataVersionRef;
import be.sdlg.snt.model.Protocol;
import be.sdlg.snt.model.Question;
import be.sdlg.snt.model.SignatureDef;
import be.sdlg.snt.model.Study;
import be.sdlg.snt.model.StudyEventDef;
import be.sdlg.snt.model.StudyEventPred;
import be.sdlg.snt.model.StudyEventRef;
import be.sdlg.snt.model.StudyLocation;
import be.sdlg.snt.model.StudyUser;
import be.sdlg.snt.model.TranslatedText;

import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * This class is the implementation of the Snt Administrative Service and contains all the functionalities used to manage the authorities and access
 *
 * @author Jean-Yves Celis
 * @version 1.0 - (C) SDLG, June 2013.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly=true)
public class AdministrationSvcImpl implements AdministrationSvc {
	private static final Logger logger = LoggerFactory.getLogger(AdministrationSvcImpl.class);
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	protected StudyDao studyDao;
	@Autowired
	protected LocationDao locationDao;
	@Autowired
	protected DBProperDao properDao;
	@Autowired
	protected MetadataVersionDao metadataVersionDao;
	@Autowired
	protected StudyUserDao studyUserDao;
	@Autowired
	protected StudyLocationDao studyLocationDao;
	@Autowired
	protected DBUserDao dbUserDao;	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private SaltSource saltSource;
	@Autowired
	private SignatureDefDao signatureDefDao;
	@Autowired
	private StudyEventDefDao studyEventDefDao;
	@Autowired
	private ProtocolDao protocolDao;
	@Autowired
	private StudyEventRefDao studyEventRefDao;
	@Autowired
	private FormDefDao formDefDao;
	@Autowired
	private FormRefDao formRefDao;
	@Autowired
	private DescriptionDao descriptionDao;
	@Autowired
	private ItemGroupDefDao itemGroupDefDao;
	@Autowired
	private ItemGroupRefDao itemGroupRefDao;
	@Autowired
	private CodeListDao codeListDao;
	@Autowired
	private CodeListItemDao codeListItemDao;
	@Autowired
	private DecodeDao decodeDao;
	@Autowired
	private QuestionDao questionDao;
	@Autowired
	private ItemDefDao itemDefDao;
	@Autowired
	private ItemRefDao itemRefDao;

	
	public boolean isValidPassword(DBUser user, String password) {
		// TODO add check in password history
		PasswordValidator pv = new PasswordValidator();	
		return pv.validate(password);
	}
	public boolean isValidSignature(DBUser user, String signature) {
		// TODO add check in signature history
		PasswordValidator pv = new PasswordValidator();	
		return pv.validate(signature);
	}

	@Override
	public List<DBUser> getUserList() {
		
		return dbUserDao.findAll();
	}
	@Override
	public DBUser getUserByLogin(String login) {
		 return	(DBUser) dbUserDao.findByUserName(login);
	}

	
	public DBUser getUserById(Long id) {
		return dbUserDao.get(id);
	}


	public Study getStudy(Long id) {
		return studyDao.get(id);
	}

	public Location getLocation(Long id) {
		return locationDao.get(id);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)	
	public void saveStudy(Study study) {
		studyDao.update(study);
		
	}
	
	public MetadataVersion getCurrentMetadataVersion(Study study) {
		return metadataVersionDao.findCurrentMetadataVersion(study);
	}
	/**
	 * Add and remove granted access to a user for a particular study on a particular location.
	 * @param study the current study
	 * @param user the current user
	 * @param location the target location
	 * @see DBUser, Location, Study
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)	
	public void updateGrants(Long studyId, Long userId,Long locationId,
			List<Grant> grants) throws Exception {
		// create new study location
		DBProper proper = null;
		Study study = studyDao.get(studyId);
		DBUser user = dbUserDao.get(userId);
		Location location = locationDao.get(locationId);
		
		StudyUser studyUser = null;
		StudyLocation studyLocation = null;
		
		try {
			studyUser = studyUserDao.get(study, user);
			studyLocation = studyLocationDao.get(study, location);
		
		if (studyUser == null) {
			studyUser = new StudyUser();
			studyUser.setUser(user);
			studyUser.setStudy(study);
			study.getAdminDataList().add(studyUser);
		}
		List<DBProper> properList = properDao.findDBProperList(study, location, user);
		if (properList !=null && properList.size() == 1 ) {
			proper = properList.get(0);
		} else if (properList !=null && properList.size() == 0 ){
			if (studyLocation == null ){
				studyLocation = new StudyLocation();
				studyLocation.setLocation(location);
				studyLocation.setStudy(study);
				study.getAdminDataList().add(studyLocation);
				
				MetadataVersion mv = getCurrentMetadataVersion(study);
				MetadataVersionRef mvr = new MetadataVersionRef();
				mvr.setMetadataVersion(mv);
				mvr.setLocation(studyLocation);
				mvr.setEffectiveDate(new Date());
				mv.getMetadataVersionRefList().add(mvr);
			}
		
			proper = new DBProper();
			proper.setStudyLocation(studyLocation);
			proper.setStudyUser(studyUser);
		
			proper.getStudyLocation().getProperList().add(proper);
			proper.getStudyUser().getProperList().add(proper);
			
			// Create the metadataversion ref
		}
		if (proper !=null ){
	//		for (Grant grant : proper.getGrantList() )
	//			logger.info("==>Grant = "+grant.getValue()+"; Proper="+grant.getProper().toString());
			proper.getGrantList().clear();
			for (Grant grant : grants) {
				grant.setProper(proper);		
				proper.getGrantList().add(grant);
			}
			studyDao.update(study);
		}
		else
			System.out.println("Proper cannot be null");
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	@Override
	public boolean isMatchingSignature(Long studyId, Long locationId,  String signature, DBUser user ) {
	
		if (hasGrant(studyId, locationId, user.getId(), new Long(Grant.INVESTIGATOR))) {
			String encSignature = getEncodedUserPassword(signature, user);
			if (user.getSignature().equals(encSignature))
				return true;
			
		}
	
		return false;
	}
	public String getEncodedUserPassword(String password, DBUser dbUser) {
		UserEx userEx = new UserEx(dbUser);
		Object salt = saltSource.getSalt(userEx);
		return passwordEncoder.encodePassword(password, salt);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
	public void updateUserProperties(Long userId, String password, String signature, boolean enabled, String email) throws Exception {
		DBUser dbUser = this.getUserById(userId);
		try {
			if (password != null && password.length()>1 ) {
				dbUser.setPassword(getEncodedUserPassword(password, dbUser));
			}
			if (signature != null && signature.length()>1 ) {
				dbUser.setSignature(getEncodedUserPassword(signature, dbUser));
			}

			dbUser.setEnabled(enabled);
			if (email!=null && email.length()>1)
				dbUser.setUserMail(email);
			dbUserDao.update(dbUser);					
		
		}
		catch (Exception e) {
			if (dbUser == null) throw new UserNotExistException();
		}
	}
	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
	public void updateAuthorities(Long userId, List<String> authorities) throws Exception {
		DBUser dbUser = getUserById(userId);
		try {
//			Iterator<Authority> it = dbUser.getAuthorityList().iterator();
//			Authority authority;
//			while (it.hasNext()) {
//				authority = (Authority) it.next();
//				authorityDao.delete(authority);
//			}
			dbUser.getAuthorityList().clear();
			for (String auth : authorities) {
					Authority newAuthority = new Authority();
					newAuthority.setAuthority(auth);
					//newAuthority.setAuthority(auth);
					newAuthority.setUser(dbUser);
					dbUser.getAuthorityList().add(newAuthority);					
			}
			dbUserDao.update(dbUser);

		} catch (Exception e) {
			if (dbUser == null) throw new UserNotExistException();
		}

	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
	public DBUser createUser(String name, String password, boolean enabled, String email, List<String> authorities ) throws Exception {
			DBUser user = new DBUser();
			user.setName(name);		
			user.setEnabled(enabled);
			user.setPassword("");
			user.setUserMail(email);
			user.setCreationDate(new Date());
			for(String auth : authorities) {
				Authority authority = new Authority();
				authority.setAuthority(auth);
				authority.setUser(user);
				user.getAuthorityList().add(authority);
			}
			user.setPassword(getEncodedUserPassword(password, user));
//			logger.info("User="+user.getName()+"pass="+password+"==>"+user.getPassword());
			try {
				dbUserDao.add(user);
				return user;
			} catch (Exception e) {
				throw e;
			}
		
	}
	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
	public void deleteUser(DBUser user){
		dbUserDao.delete(user);
	}
	protected void addDescription(ClinicalDef clinicalDef, String description, Locale locale) {
		Description descr = new Description();
		TranslatedText tr = new TranslatedText();
		tr.setTranslatedText(description);
		tr.setLanguageTag(locale.toLanguageTag());
		descr.getTranslatedTextList().add(tr);
		tr.setDecodable(descr);
		clinicalDef.setDescription(descr);
		descr.setClinicalDef(clinicalDef);
		descriptionDao.add(descr);
		
	} 
	
	protected void addDecode(CodeListItem cli, String decodeValue, Locale locale) {
		Decode decode = new Decode();
		TranslatedText tr = new TranslatedText();
		tr.setTranslatedText(decodeValue);
		tr.setLanguageTag(locale.toLanguageTag());
		decode.getTranslatedTextList().add(tr);
		tr.setDecodable(decode);
		cli.setDecode(decode);
		decode.setCodeListItem(cli);
		decodeDao.add(decode);		
	}
	protected void addQuestion(ItemDef itemDef, String questionText, Locale locale) {
		Question question = new Question();
		TranslatedText tr = new TranslatedText();
		tr.setTranslatedText(questionText);
		tr.setLanguageTag(locale.toLanguageTag());
		question.getTranslatedTextList().add(tr);
		tr.setDecodable(question);
		itemDef.setQuestion(question);
		question.setItemDef(itemDef);
		questionDao.add(question);
		
		
	}
	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
	public Study createStudy(String studyName, String studyDescription, Date effectiveDate, String metadataVersionName, 
			String metadataVersionDescr, Long metadataVersionVer, Locale locale) throws Exception {
		Study study = new Study();
		study.setStudyName(studyName);
		study.setStudyDescription(studyDescription);
		study.setStudyStartDate(effectiveDate);
		
		MetadataVersion mv = new MetadataVersion();
		mv.setVersion(metadataVersionVer);
		mv.setName(metadataVersionName);
		Protocol p = new Protocol();
		addDescription(p, metadataVersionDescr, locale);
//		p.setDescription(metadataVersionDescr);
		p.setName(metadataVersionName);
		p.setMetadataVersion(mv);
		mv.setProtocol(p);
		
		mv.setStudy(study);
		study.getMetadataVersionList().add(mv);
		
		try {
			studyDao.add(study);
			return study;
		} catch(Exception e) {
			throw e;
		}
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
	public Location createLocation(int locationType, String locationName) throws Exception {
		Location location = new Location();
		location.setLocationType(new Long(locationType));
		location.setName(locationName);
		
		try {
			locationDao.add(location);
			return location;
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public List<Study> getStudiesByUserId(Long userId) {
		DBUser user = getUserById(userId);
		return studyDao.findStudiesByUser(user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
	public SignatureDef createSignatureDef(Long studyId, String meaning,
			String legalReason, Long methodology, Long signatureUsage) throws Exception {
	 Study study = this.getStudy(studyId);
	 SignatureDef signatureDef = new SignatureDef();
	 try {
	 signatureDef.setLegalReason(legalReason);
	 signatureDef.setMeaning(meaning);
	 signatureDef.setMethodology(methodology);
	 signatureDef.setSignatureUsage(signatureUsage);
	 signatureDef.setStudy(study);
	 study.getAdminDataList().add(signatureDef);
	 signatureDefDao.add(signatureDef);
	 } catch (Exception e) {
		 signatureDef = null;
		 if (study==null) throw new StudyNotExistException();
		 else throw e;
	 }
	 return signatureDef;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
	public FormDef createFormDef(Long studyId, String name, String description,
			Long repeating, Locale locale) throws Exception {
		Study study = getStudy(studyId);
		MetadataVersion mv = this.getCurrentMetadataVersion(study);
		FormDef fd = null;
		try {
			fd = new FormDef();
			fd.setMetadataVersion(mv);
			this.addDescription(fd, description, locale);
//			fd.setDescription(description);
			fd.setName(name);
			fd.setRepeating(repeating);
			formDefDao.add(fd);
		}catch (Exception e) {
			throw e;
		}
		return fd;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
	public CodeList createCodeList(Long studyId, String name, String description,
			Long dataType, String sasFormatName, Locale locale ) throws Exception {
		Study study = getStudy(studyId);
		MetadataVersion mv = this.getCurrentMetadataVersion(study);
		CodeList codeList = null;
		try {
		  codeList = new CodeList();
		  codeList.setMetadataVersion(mv);
		  mv.getClinicalDefList().add(codeList);
		  codeList.setDataType(dataType);
		  this.addDescription(codeList, description, locale);
		  codeList.setName(name);
		  codeList.setSasFormatName(sasFormatName);
		  codeListDao.add(codeList);
		} catch(Exception e) {
			throw e;
		}
		return codeList;
		
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
	public ItemGroupDef createItemGroupDef(Long studyId, String name, String description,
			String domain, String comment, String origin, String purpose, Long repeating, 
			String sasDatasetName, Locale locale) throws Exception {
		Study study = getStudy(studyId);
		MetadataVersion mv = this.getCurrentMetadataVersion(study);
		ItemGroupDef itg = null;
		try {
			itg = new ItemGroupDef();
			itg.setMetadataVersion(mv);
			mv.getClinicalDefList().add(itg);
			this.addDescription(itg, description, locale);
			itg.setDomain(domain);
			itg.setComment(comment);
			itg.setName(name);
			itg.setOrigin(origin);
			itg.setPurpose(purpose);
			itg.setRepeating(repeating);
			itg.setSasDatasetName(sasDatasetName);
			itemGroupDefDao.add(itg);
			
		} catch (Exception e) {
			throw e;
		}
		return itg;
				
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
	public ItemDef createItemDef(Long studyId, String comment, Long dataType,
			String name, String description, String origin, String sasFieldName, String sdsVarName,
			Long significantDigits, String question, Long codeListId, Locale locale) throws Exception {
		Study study = getStudy(studyId);
		MetadataVersion mv  = this.getCurrentMetadataVersion(study);
		CodeList codeList = null; 
		ItemDef itemDef = null;
		try {
			if (codeListId != null  )
				codeList = codeListDao.get(codeListId);

			itemDef = new ItemDef();
			itemDef.setMetadataVersion(mv);
			mv.getClinicalDefList().add(itemDef);
			itemDef.setComment(comment);
			itemDef.setDataType(dataType);
			this.addDescription(itemDef, description, locale);
			itemDef.setName(name);
			itemDef.setOrigin(origin);
			itemDef.setSasFieldName(sasFieldName);
			itemDef.setSdsVarName(sdsVarName);
			itemDef.setSignificantDigits(significantDigits);
			this.addQuestion(itemDef, question, locale);
			if (codeList!=null)
				itemDef.setCodeList(codeList);
			itemDefDao.add(itemDef);
		} catch (Exception e) {
			throw e;
		}
		return itemDef;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
	public StudyEventDef createStudyEventDef(Long studyId, String category,
			Long type, Long repeating, String studyEventName,
			String studyEventDescription, Locale locale) throws Exception {
		Study study = getStudy(studyId);
		MetadataVersion mv = this.getCurrentMetadataVersion(study);
		StudyEventDef sd = null;
		try {
			sd = new StudyEventDef();
			sd.setMetadataVersion(mv);
			mv.getClinicalDefList().add(sd);
			sd.setCategory(category);
			sd.setRepeating(repeating);
			sd.setType(type);
			sd.setName(studyEventName);
			this.addDescription(sd, studyEventDescription, locale);
			
			studyEventDefDao.add(sd);
		} catch(Exception e) {
			throw e;
		}
		return sd;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
	public ItemGroupRef createItemGroupRef(Long formDefId, ItemGroupDef itemGroupDef,
			Long mandatory, Long orderNumber ) throws Exception {
		FormDef fd = formDefDao.get(formDefId);
		ItemGroupRef igr = null;
		try {
			igr = new ItemGroupRef();
			igr.setFormDef(fd);
			fd.getItemGroupRefList().add(igr);
			
			igr.setItemGroupDef(itemGroupDef);
			igr.setMandatory(mandatory);
			igr.setOrderNumber(orderNumber);
			
			itemGroupRefDao.add(igr);
		} catch(Exception e) {
			throw e;
		}
		return igr;
	}
	
 	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
 	public StudyEventRef createStudyEventRef(Long protocolId,
			StudyEventDef studyEventDef, StudyEventRef predecessor,
			Long gapWithPredecessor, Long toleranceMin, Long toleranceMax, Long mandatory)
			throws Exception {
		Protocol p = protocolDao.get(protocolId);
		StudyEventRef sr = null;
		try {
			sr = new StudyEventRef();
			sr.setProtocol(p);
			p.getStudyEventRefList().add(sr);
			
			sr.setGapWithPredecessor(gapWithPredecessor);
			sr.setMandatory(mandatory);
			sr.setMaxTolerance(toleranceMax);
			sr.setMinTolerance(toleranceMin);
			StudyEventPred pred = new StudyEventPred();
			if (predecessor !=null) 
				pred.setPredecessorId(predecessor.getId());
				
			pred.setStudyEventRef(sr);
			sr.getPredecessorList().add(pred);
			sr.setStudyEventDef(studyEventDef);
			
			studyEventRefDao.add(sr);
		}catch (Exception e) {
			throw e;
		}
		return sr;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
	public ItemRef createItemRef(Long itemGroupDefId, ItemDef itemDef, Long keySequence,
		Long mandatory, Long orderNumber, String role	) throws Exception {
		ItemGroupDef igd = itemGroupDefDao.get(itemGroupDefId);
		ItemRef ir = null;
		try {
			ir = new ItemRef();
			ir.setItemGroupDef(igd);
			igd.getItemRefList().add(ir);
			ir.setItemDef(itemDef);
			ir.setKeySequence(keySequence);
			ir.setMandatory(mandatory);
			ir.setOrderNumber(orderNumber);
			ir.setRole(role);
			itemRefDao.add(ir);
		} catch (Exception e) {
			throw e;
		}
		
		return ir;
	}
 	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
	public FormRef createFormRef(Long studyEventDefId, FormDef formDef, Long orderNumber,
			Long mandatory) throws Exception {
		StudyEventDef sd = studyEventDefDao.get(studyEventDefId);
		FormRef fr = null;
		try {
			fr = new FormRef();
		    fr.setStudyEventDef(sd);
		    sd.getFormRefList().add(fr);
			fr.setFormDef(formDef);
			
			fr.setMandatory(mandatory);
			fr.setOrderNumber(orderNumber);
			
			formRefDao.add(fr);
			
		} catch (Exception e) {
			throw e;
		}
		return fr;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
	public void addStudyEventRefPredecessor(StudyEventRef current,
			StudyEventRef predecessor) throws Exception {
		try {
			if (predecessor!=null) {
				StudyEventPred pred = new StudyEventPred();
				pred.setPredecessorId(predecessor.getId());
				current.getPredecessorList().add(pred);
				
				studyEventRefDao.update(current);
			}
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
	public void addCodeListItem(Long codeListId, String codedValue, String decodeValue, Float rank, Locale locale) throws Exception {
		CodeList codeList = codeListDao.get(codeListId);
		CodeListItem cli = null;
		try {
			cli = new CodeListItem();
			cli.setCodeList(codeList);
			codeList.getCodeListItems().add(cli);
			cli.setCodedValue(codedValue);
			this.addDecode(cli, decodeValue, locale);
			cli.setRank(rank);
			codeListItemDao.add(cli);
		}catch (Exception e) {
			throw e;
		}
	}
	@Override
	public List<Authority> getAuthorityList(boolean retrieveUser) {
		List<Authority> authorityList = new ArrayList<Authority>();
		Authority userAuth = new Authority();
		userAuth.setAuthority("ROLE_USER");
		
		Authority adminAuth = new Authority();
		adminAuth.setAuthority("ROLE_ADMIN");
		
		Authority modifyAuth = new Authority();
		modifyAuth.setAuthority("ROLE_MODIFY");
		
		Authority readOnlyAuth = new Authority();
		readOnlyAuth.setAuthority("ROLE_READONLY");
		
		if (retrieveUser)
			authorityList.add(userAuth);
		authorityList.add(adminAuth);
		authorityList.add(modifyAuth);
		authorityList.add(readOnlyAuth);

		return authorityList;
	}
	@Override
	public boolean isPasswordEquals(DBUser user, String password) {
		return (getEncodedUserPassword(password, user).equals(user.getPassword()));
	}
	@Override
	public boolean isSignatureEquals(DBUser user, String password) {
		return (getEncodedUserPassword(password, user).equals(user.getSignature()));
	}

	@Override
	public List<Study> getStudies() {
		return studyDao.findAll();
	}
	
	@Override
	public List<Location> getLocations() {
		return locationDao.findAll();
	}
	@Override
	public boolean hasRole(Authentication auth, String role) {
		for (GrantedAuthority autho : auth.getAuthorities())
			if (autho.getAuthority().equals(role)) return true;
		
		return false;
				
			
	}
	@Override
	public List<DBProper> getProperList(Long studyId, Long locationId) {

		return properDao.findDBProperList(studyId, locationId);
	}
	public List<Location> getStudyLocationByStudyByUser(Long studyId, Long userId) {
		return locationDao.findLocationsByStudyByUser(studyId, userId);
	}
	@Override
	public List<DBUser> getUserByStudyByLocation(Long studyId, Long locationId, Long grant) {
		return dbUserDao.findUserByStudyByLocation(studyId, locationId, grant);
	}
	
	@Override
	public boolean hasGrant(Long studyId, Long locationId, Long userId, Long g) {
		List<DBProper> propers = properDao.hasGrant(studyId, locationId, userId, g);
		return (propers.size()>0L);
	}
	@Override
	public String getDescription(Description description, Locale locale) {
		for (TranslatedText tt : description.getTranslatedTextList()) {
			if (tt.getLanguageTag().contains(locale.getLanguage())) 
				return tt.getTranslatedText();
		}
		return null;
	}
	@Override
	public String getMessage(String message, Locale locale) {
		
		return applicationContext.getMessage(message, null, locale);
	}
	@Override
	public StudyEventDef getStudyEventDefById(Long sdId) {
		
		return studyEventDefDao.get(sdId);
	}
	@Override
	public FormDef getFormDefById(Long fdId) {
		
		return formDefDao.get(fdId);
	}
	@Override
	public String getDecode(Decode decode, Locale locale) {
		for (TranslatedText tt : decode.getTranslatedTextList()) {
			if (tt.getLanguageTag().contains(locale.getLanguage())) 
				return tt.getTranslatedText();
		}
		return null;
	}
	@Override
	public String getQuestion(Question question, Locale locale) {
		for (TranslatedText tt : question.getTranslatedTextList()) {
			if (tt.getLanguageTag().contains(locale.getLanguage())) 
				return tt.getTranslatedText();
		}
		return null;
	}
	@Override
	public SignatureDef getSignatureDef(Long studyId, Long signatureUsage) {
		
		return signatureDefDao.findSignature(studyId, signatureUsage);
	}
	
	
	

	

}
