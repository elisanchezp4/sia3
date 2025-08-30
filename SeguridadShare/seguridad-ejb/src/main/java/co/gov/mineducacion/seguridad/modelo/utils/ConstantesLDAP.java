package co.gov.mineducacion.seguridad.modelo.utils;

public class ConstantesLDAP {
	private ConstantesLDAP(){/* Recomendacion por sonar */}
	/**
	 * Country: e.g GB for Great Britain.
	 */
	public static final String c = "c";

	/**
	 * CN=Guy Thomas. Actually, this LDAP attribute can be made up from
	 * givenName joined to SN.
	 */
	public static final String cn = "cn";

	/**
	 * What you see in Active Directory Users and Computers. Not to be confused
	 * with displayName on the Users property sheet.
	 */
	public static final String description = "description";

	/**
	 * displayName = Guy Thomas. If you script this property, be sure you
	 * understand which field you are configuring. DisplayName can be confused
	 * with CN or description.
	 */
	public static final String displayName = "displayName";

	/**
	 * DN is simply the most important LDAP attribute. CN=Jay Jamieson, OU=
	 * Newport,DC=cp,DC=com
	 */
	public static final String DN = "DN";

	/**
	 * Firstname also called Christian name
	 */
	public static final String givenName = "givenName";

	/**
	 * Home Folder : connect. Tricky to configure
	 */
	public static final String homeDrive = "homeDrive";

	/**
	 * Useful in some cultures.
	 */
	public static final String initials = "initials";

	/**
	 * name = Guy Thomas. Exactly the same as CN.
	 */
	public static final String name = "name";

	/**
	 * Defines the Active Directory Schema category. For example, objectCategory
	 * = Person
	 */
	public static final String objectCategory = "objectCategory";

	/**
	 * objectClass = User. Also used for Computer, organizationalUnit, even
	 * container. Important top level container.
	 */
	public static final String objectClass = "objectClass";

	/**
	 * Office! on the user's General property sheet
	 */
	public static final String physicalDeliveryOfficeName = "physicalDeliveryOfficeName";

	/**
	 * P.O. box.
	 */
	public static final String postOfficeBox = "postOfficeBox";

	/**
	 * Roaming profile path: connect. Trick to set up
	 */
	public static final String profilePath = "profilePath";

	/**
	 * This is a mandatory property, sAMAccountName = guyt. The old NT 4.0 logon
	 * name, must be unique in the domain.
	 */
	public static final String sAMAccountName = "sAMAccountName";

	/**
	 * SN = Thomas. This would be referred to as last name or surname
	 */
	public static final String SN = "SN";

	/**
	 * Job title. For example Manager.
	 */
	public static final String title = "title";

	/**
	 * Used to disable an account. A value of 514 disables the account, while
	 * 512 makes the account ready for logon.
	 */
	public static final String userPrincipalName = "userPrincipalName";

	/**
	 * User's home page.
	 */
	public static final String wWWHomePage = "wWWHomePage";

	/**
	 * Here is where you set the MailStore
	 */
	public static final String homeMDB = "homeMDB";

	/**
	 * Here is where you set the MailStore
	 */
	public static final String Uid = "uid";

	/**
	 * Legacy distinguished name for creating Contacts. In the following
	 * example, Guy Thomas is a Contact in the first administrative group of
	 * GUYDOMAIN: /o=GUYDOMAIN/ou=first administrative
	 * group/cn=Recipients/cn=Guy Thomas
	 */

	public static final String legacyExchangeDN = "legacyExchangeDN";
	/**
	 * An easy, but important attribute. A simple SMTP address is all that is
	 * required billyn@ourdom.com
	 */
	public static final String mail = "mail";

	/**
	 * Indicates that a contact is not a domain user.
	 */
	public static final String mailNickname = "mailNickname";

	/**
	 * Another straightforward field, just the value to:True
	 */
	public static final String mDBUseDefaults = "mDBUseDefaults";

	/**
	 * Exchange needs to know which server to deliver the mail. Example:
	 * /o=YourOrg/ou=First Administrative
	 * Group/cn=Configuration/cn=Servers/cn=MailSrv
	 */
	public static final String msExchHomeServerName = "msExchHomeServerName";

	/**
	 * As the name 'proxy' suggests, it is possible for one recipient to have
	 * more than one email address. Note the plural spelling of proxyAddresses
	 */
	public static final String proxyAddresses = "proxyAddresses";

	/**
	 * SMTP:@ e-mail address. Note that SMTP is case sensitive. All capitals
	 * means the default address.
	 */
	public static final String targetAddress = "targetAddress";

	/**
	 * Displays the contact in the Global Address List.
	 */
	public static final String showInAddressBook = "showInAddressBook";

	/**
	 * Company or organization name
	 */
	public static final String company = "company";

	/**
	 * Useful category to fill in and use for filtering
	 */
	public static final String department = "department";

	/**
	 * Home Phone number, (Lots more phone LDAPs)
	 */
	public static final String homephone = "homephone";

	/**
	 * L = Location. City ( Maybe Office
	 */
	public static final String l = "l";

	/**
	 * Important, particularly for printers and computers.
	 */
	public static final String location = "location";

	/**
	 * Boss, manager
	 */
	public static final String manager = "manager";

	/**
	 * Mobile Phone number
	 */
	public static final String mobile = "mobile";

	/**
	 * Organizational unit. See also DN
	 */
	public static final String OU = "OU";

	/**
	 * Force users to change their passwords at next logon
	 */
	public static final String pwdLastSet = "pwdLastSet";

	/**
	 * Zip or post code
	 */
	public static final String postalCode = "postalCode";

	/**
	 * State, Province or County
	 */
	public static final String st = "st";

	/**
	 * First line of address
	 */
	public static final String streetAddress = "streetAddress";

	/**
	 * Office Phone
	 */
	public static final String telephoneNumber = "telephoneNumber";

	/**
	 * Country: e.g GB for Great Britain.
	 */
	public static final String distinguishedName = "distinguishedName";

	/**
	 * userPrincipalName = guyt@CP.com Often abbreviated to UPN, and looks like
	 * an email address. Very useful for logging on especially in a large
	 * Forest. Note UPN must be unique in the forest.
	 */
	public static final String userAccountControl = "userAccountControl";

	/**
	 * URL
	 */
	public static final String url = "url";

	/**
	 * URL
	 */
	public static final String userPassword = "userPassword";
	
	/**
	 * userPrincipalName = guyt@CP.com Often abbreviated to UPN, and looks like
	 * an email address. Very useful for logging on especially in a large
	 * Forest. Note UPN must be unique in the forest.
	 */
	public static final String whenCreated = "whenCreated";
	
	/**
	 * userPrincipalName = guyt@CP.com Often abbreviated to UPN, and looks like
	 * an email address. Very useful for logging on especially in a large
	 * Forest. Note UPN must be unique in the forest.
	 */
	public static final String whenChanged = "whenChanged";
	
	/**
	 * userPrincipalName = guyt@CP.com Often abbreviated to UPN, and looks like
	 * an email address. Very useful for logging on especially in a large
	 * Forest. Note UPN must be unique in the forest.
	 */
	public static final String accountExpires = "accountExpires";
	
	public static final String unicodePwd = "unicodePwd";
	
	

	public static final String INITIAL_CONTEXT_FACTORY = "INITIAL_CONTEXT_FACTORY";
	public static final String PROVIDER_URL = "PROVIDER_URL";
	public static final String PROVIDER_URL_SSL = "PROVIDER_URL_SSL";
	public static final String SECURITY_AUTHENTICATION = "SECURITY_AUTHENTICATION";
	public static final String SECURITY_PRINCIPAL = "SECURITY_PRINCIPAL";
	public static final String SECURITY_CREDENTIALS = "SECURITY_CREDENTIALS";
	public static final String RUTA_ARBzOL_LDAT = "RUTA_ARBOL_LDAT";
	public static final String DOMINIO = "DOMINIO";
	public static final String RUTA_INACTIVOS = "RUTA_INACTIVOS";
	public static final String PATH_KEYSTORE = "PATH_KEYSTORE";
	public static final String PASSWORD_KEYSTORE = "PASSWORD_KEYSTORE";
	public static final String SSL = "ssl";
	public static final String UNIDAD_ORGANIZACIONAL = "UNIDAD_ORGANIZACIONAL";
	public static final String USUARIOS_EXTERNOS = "USUARIOS_EXTERNOS";
	public static final String PATH_DLLS = "PATH_DLLS";
	public static final String PATH_DLL_FILE = "PATH_DLL_FILE";
	public static final String TRUE = "True";
	public static final String FALSE = "False";
	//Inicio variables HBT
	public static final String PROVIDER_URL_GLOBAL_CATALOG = "PROVIDER_URL_GLOBAL_CATALOG";
	public static final long LIMITE_REGISTROS = 50;
	//Fin variables HBT
}
