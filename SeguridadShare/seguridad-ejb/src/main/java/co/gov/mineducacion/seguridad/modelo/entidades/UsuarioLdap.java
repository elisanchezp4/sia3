package co.gov.mineducacion.seguridad.modelo.entidades;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

/**
 * Clase usuarioLDAP contiene todos los atributos de un directorio activo
 * 
 * @author Michael Murgueitio
 *
 */
public class UsuarioLdap {

	/**
	 * Country: e.g GB for Great Britain.
	 */
	private String c;
	
	/**
	 * Country: e.g GB for Great Britain.
	 */
	private String distinguishedName;

	/**
	 * CN=Guy Thomas. Actually, this LDAP attribute can be made up from
	 * givenName joined to SN.
	 */
	private String cn;

	/**
	 * What you see in Active Directory Users and Computers. Not to be confused
	 * with displayName on the Users property sheet.
	 */
	private String description;

	/**
	 * displayName = Guy Thomas. If you script this property, be sure you
	 * understand which field you are configuring. DisplayName can be confused
	 * with CN or description.
	 */
	private String displayName;

	/**
	 * DN is simply the most important LDAP attribute. CN=Jay Jamieson, OU=
	 * Newport,DC=cp,DC=com
	 */
	private String dn;

	/**
	 * Firstname also called Christian name
	 */
	private String givenName;

	/**
	 * Home Folder : connect. Tricky to configure
	 */
	private String homeDrive;

	/**
	 * Useful in some cultures.
	 */
	private String initials;

	/**
	 * name = Guy Thomas. Exactly the same as CN.
	 */
	private String name;

	/**
	 * Defines the Active Directory Schema category. For example, objectCategory
	 * = Person
	 */
	private String objectCategory;

	/**
	 * objectClass = User. Also used for Computer, organizationalUnit, even
	 * container. Important top level container.
	 */
	private List<String> objectClass;

	/**
	 * Office! on the user's General property sheet
	 */
	private String physicalDeliveryOfficeName;

	/**
	 * P.O. box.
	 */
	private String postOfficeBox;

	/**
	 * Roaming profile path: connect. Trick to set up
	 */
	private String profilePath;

	/**
	 * This is a mandatory property, sAMAccountName = guyt. The old NT 4.0 logon
	 * name, must be unique in the domain.
	 */
	private String sAMAccountName;

	/**
	 * SN = Thomas. This would be referred to as last name or surname
	 */
	private String sn;

	/**
	 * Job title. For example Manager.
	 */
	private String title;

	/**
	 * Used to disable an account. A value of 514 disables the account, while
	 * 512 makes the account ready for logon.
	 */
	private String userPrincipalName;

	/**
	 * User's home page.
	 */
	private String wWWHomePage;

	/**
	 * Here is where you set the MailStore
	 */
	private String homeMDB;

	/**
	 * Legacy distinguished name for creating Contacts. In the following
	 * example, Guy Thomas is a Contact in the first administrative group of
	 * GUYDOMAIN: /o=GUYDOMAIN/ou=first administrative
	 * group/cn=Recipients/cn=Guy Thomas
	 */

	private String legacyExchangeDN;
	/**
	 * An easy, but important attribute. A simple SMTP address is all that is
	 * required billyn@ourdom.com
	 */
	private String mail;

	/**
	 * Indicates that a contact is not a domain user.
	 */
	private String mailNickname;

	/**
	 * Another straightforward field, just the value to:True
	 */
	private String mDBUseDefaults;

	/**
	 * Exchange needs to know which server to deliver the mail. Example:
	 * /o=YourOrg/ou=First Administrative
	 * Group/cn=Configuration/cn=Servers/cn=MailSrv
	 */
	private String msExchHomeServerName;

	/**
	 * As the name 'proxy' suggests, it is possible for one recipient to have
	 * more than one email address. Note the plural spelling of proxyAddresses
	 */
	private String proxyAddresses;

	/**
	 * SMTP:@ e-mail address. Note that SMTP is case sensitive. All capitals
	 * means the default address.
	 */
	private String targetAddress;

	/**
	 * Displays the contact in the Global Address List.
	 */
	private String showInAddressBook;

	/**
	 * Company or organization name
	 */
	private String company;

	/**
	 * Useful category to fill in and use for filtering
	 */
	private String department;

	/**
	 * Home Phone number, (Lots more phone LDAPs)
	 */
	private String homephone;

	/**
	 * L = Location. City ( Maybe Office
	 */
	private String l;

	/**
	 * Important, particularly for printers and computers.
	 */
	private String location;

	/**
	 * Boss, manager
	 */
	private String manager;

	/**
	 * Mobile Phone number
	 */
	private String mobile;

	/**
	 * Organizational unit. See also DN
	 */
	private String ou;

	/**
	 * Force users to change their passwords at next logon
	 */
	private String pwdLastSet;

	/**
	 * Zip or post code
	 */
	private String postalCode;

	/**
	 * State, Province or County
	 */
	private String st;

	/**
	 * First line of address
	 */
	private String streetAddress;

	/**
	 * Office Phone
	 */
	private String telephoneNumber;

	/**
	 * Office Phone
	 */
	private String uid;

	/**
	 * userPrincipalName = guyt@CP.com Often abbreviated to UPN, and looks like
	 * an email address. Very useful for logging on especially in a large
	 * Forest. Note UPN must be unique in the forest.
	 */
	private String userAccountControl;
	
	/**
	 * whenCreated = create the date of registry of user
	 */
	private String whenCreated;
	
	/**
	 * whenChanged = create the date of registry of user
	 */
	private String whenChanged;
	
	/**
	 * accountExpires = create the date of registry of user
	 */
	private String accountExpires;

	/**
	 * URL
	 */
	private String url;

	/**
	 * pasword usuario
	 */
	private String userPassword;

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cN) {
		cn = cN;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDn() {
		return dn;
	}

	public void setDn(String dN) {
		dn = dN;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getHomeDrive() {
		return homeDrive;
	}

	public void setHomeDrive(String homeDrive) {
		this.homeDrive = homeDrive;
	}

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getObjectCategory() {
		return objectCategory;
	}

	public void setObjectCategory(String objectCategory) {
		this.objectCategory = objectCategory;
	}

	public void setObjectClass(List<String> objectClass) {
		this.objectClass = objectClass;
	}

	public String getPhysicalDeliveryOfficeName() {
		return physicalDeliveryOfficeName;
	}

	public void setPhysicalDeliveryOfficeName(String physicalDeliveryOfficeName) {
		this.physicalDeliveryOfficeName = physicalDeliveryOfficeName;
	}

	public String getPostOfficeBox() {
		return postOfficeBox;
	}

	public void setPostOfficeBox(String postOfficeBox) {
		this.postOfficeBox = postOfficeBox;
	}

	public String getProfilePath() {
		return profilePath;
	}

	public void setProfilePath(String profilePath) {
		this.profilePath = profilePath;
	}

	public String getsAMAccountName() {
		return sAMAccountName;
	}

	public void setsAMAccountName(String sAMAccountName) {
		this.sAMAccountName = sAMAccountName;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sN) {
		sn = sN;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserPrincipalName() {
		return userPrincipalName;
	}

	public void setUserPrincipalName(String userPrincipalName) {
		this.userPrincipalName = userPrincipalName;
	}

	public String getwWWHomePage() {
		return wWWHomePage;
	}

	public void setwWWHomePage(String wWWHomePage) {
		this.wWWHomePage = wWWHomePage;
	}

	public String getHomeMDB() {
		return homeMDB;
	}

	public void setHomeMDB(String homeMDB) {
		this.homeMDB = homeMDB;
	}

	public String getLegacyExchangeDN() {
		return legacyExchangeDN;
	}

	public void setLegacyExchangeDN(String legacyExchangeDN) {
		this.legacyExchangeDN = legacyExchangeDN;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMailNickname() {
		return mailNickname;
	}

	public void setMailNickname(String mailNickname) {
		this.mailNickname = mailNickname;
	}

	public String getmDBUseDefaults() {
		return mDBUseDefaults;
	}

	public void setmDBUseDefaults(String mDBUseDefaults) {
		this.mDBUseDefaults = mDBUseDefaults;
	}

	public String getMsExchHomeServerName() {
		return msExchHomeServerName;
	}

	public void setMsExchHomeServerName(String msExchHomeServerName) {
		this.msExchHomeServerName = msExchHomeServerName;
	}

	public String getProxyAddresses() {
		return proxyAddresses;
	}

	public void setProxyAddresses(String proxyAddresses) {
		this.proxyAddresses = proxyAddresses;
	}

	public String getTargetAddress() {
		return targetAddress;
	}

	public void setTargetAddress(String targetAddress) {
		this.targetAddress = targetAddress;
	}

	public String getShowInAddressBook() {
		return showInAddressBook;
	}

	public void setShowInAddressBook(String showInAddressBook) {
		this.showInAddressBook = showInAddressBook;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getHomephone() {
		return homephone;
	}

	public void setHomephone(String homephone) {
		this.homephone = homephone;
	}

	public String getL() {
		return l;
	}

	public void setL(String l) {
		this.l = l;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOu() {
		return ou;
	}

	public void setOu(String oU) {
		ou = oU;
	}

	public String getPwdLastSet() {
		return pwdLastSet;
	}

	public void setPwdLastSet(String pwdLastSet) {
		this.pwdLastSet = pwdLastSet;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getSt() {
		return st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getUserAccountControl() {
		return userAccountControl;
	}

	public void setUserAccountControl(String userAccountControl) {
		this.userAccountControl = userAccountControl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<String> getObjectClass() {
		return objectClass;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getDistinguishedName() {
		return distinguishedName;
	}

	public void setDistinguishedName(String distinguishedName) {
		this.distinguishedName = distinguishedName;
	}

	public String getWhenCreated() {
		return whenCreated;
	}

	public void setWhenCreated(String whenCreated) {
		this.whenCreated = whenCreated;
	}

	public String getWhenChanged() {
		return whenChanged;
	}

	public void setWhenChanged(String whenChanged) {
		this.whenChanged = whenChanged;
	}

	public String getAccountExpires() {
		return accountExpires;
	}

	public void setAccountExpires(String accountExpires) {
		this.accountExpires = accountExpires;
	}

	public Map<String, String> toMap() {
		ObjectMapper oMapper = new ObjectMapper();
		return oMapper.convertValue(this, Map.class);
	}
	
	
}
