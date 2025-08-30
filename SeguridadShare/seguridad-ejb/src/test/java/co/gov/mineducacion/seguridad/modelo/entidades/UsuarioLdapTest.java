package co.gov.mineducacion.seguridad.modelo.entidades;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UsuarioLdapTest {

    private UsuarioLdap usuarioLdap;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        usuarioLdap = new UsuarioLdap();
    }

    @Test
    public void testGettersAndSetters() {
        // Arrange
        String c = "GB";
        String CN = "Guy Thomas";
        String description = "Description";
        String displayName = "DisplayName";
        String DN = "CN=Jay Jamieson, OU=Newport, DC=cp, DC=com";
        String givenName = "Given Name";
        String homeDrive = "Home Drive";
        String initials = "Initials";
        String name = "Name";
        String objectCategory = "Object Category";
        List<String> objectClass = Arrays.asList("Class1", "Class2");
        String physicalDeliveryOfficeName = "Office Name";
        String postOfficeBox = "Post Office Box";
        String profilePath = "Profile Path";
        String sAMAccountName = "sAMAccountName";
        String SN = "Surname";
        String title = "Title";
        String userPrincipalName = "userPrincipalName";
        String wWWHomePage = "WWW Home Page";
        String homeMDB = "Home MDB";
        String legacyExchangeDN = "Legacy Exchange DN";
        String mail = "mail";
        String mailNickname = "mailNickname";
        String mDBUseDefaults = "mDBUseDefaults";
        String msExchHomeServerName = "msExchHomeServerName";
        String proxyAddresses = "proxyAddresses";
        String targetAddress = "targetAddress";
        String showInAddressBook = "showInAddressBook";
        String company = "Company";
        String department = "Department";
        String homephone = "Home Phone";
        String l = "City";
        String location = "Location";
        String manager = "Manager";
        String mobile = "Mobile";
        String OU = "Organizational Unit";
        String pwdLastSet = "pwdLastSet";
        String postalCode = "Postal Code";
        String st = "State";
        String streetAddress = "Street Address";
        String telephoneNumber = "Telephone Number";
        String userAccountControl = "userAccountControl";
        String url = "URL";
        String userPassword = "userPassword";
        String Uid = "Uid";
        String distinguishedName = "Distinguished Name";
        String whenCreated = "whenCreated";
        String whenChanged = "whenChanged";
        String accountExpires = "accountExpires";

        usuarioLdap.setC(c);
        usuarioLdap.setCn(CN);
        usuarioLdap.setDescription(description);
        usuarioLdap.setDisplayName(displayName);
        usuarioLdap.setDn(DN);
        usuarioLdap.setGivenName(givenName);
        usuarioLdap.setHomeDrive(homeDrive);
        usuarioLdap.setInitials(initials);
        usuarioLdap.setName(name);
        usuarioLdap.setObjectCategory(objectCategory);
        usuarioLdap.setObjectClass(objectClass);
        usuarioLdap.setPhysicalDeliveryOfficeName(physicalDeliveryOfficeName);
        usuarioLdap.setPostOfficeBox(postOfficeBox);
        usuarioLdap.setProfilePath(profilePath);
        usuarioLdap.setsAMAccountName(sAMAccountName);
        usuarioLdap.setSn(SN);
        usuarioLdap.setTitle(title);
        usuarioLdap.setUserPrincipalName(userPrincipalName);
        usuarioLdap.setwWWHomePage(wWWHomePage);
        usuarioLdap.setHomeMDB(homeMDB);
        usuarioLdap.setLegacyExchangeDN(legacyExchangeDN);
        usuarioLdap.setMail(mail);
        usuarioLdap.setMailNickname(mailNickname);
        usuarioLdap.setmDBUseDefaults(mDBUseDefaults);
        usuarioLdap.setMsExchHomeServerName(msExchHomeServerName);
        usuarioLdap.setProxyAddresses(proxyAddresses);
        usuarioLdap.setTargetAddress(targetAddress);
        usuarioLdap.setShowInAddressBook(showInAddressBook);
        usuarioLdap.setCompany(company);
        usuarioLdap.setDepartment(department);
        usuarioLdap.setHomephone(homephone);
        usuarioLdap.setL(l);
        usuarioLdap.setLocation(location);
        usuarioLdap.setManager(manager);
        usuarioLdap.setMobile(mobile);
        usuarioLdap.setOu(OU);
        usuarioLdap.setPwdLastSet(pwdLastSet);
        usuarioLdap.setPostalCode(postalCode);
        usuarioLdap.setSt(st);
        usuarioLdap.setStreetAddress(streetAddress);
        usuarioLdap.setTelephoneNumber(telephoneNumber);
        usuarioLdap.setUserAccountControl(userAccountControl);
        usuarioLdap.setUrl(url);
        usuarioLdap.setUserPassword(userPassword);
        usuarioLdap.setUid(Uid);
        usuarioLdap.setDistinguishedName(distinguishedName);
        usuarioLdap.setWhenCreated(whenCreated);
        usuarioLdap.setWhenChanged(whenChanged);
        usuarioLdap.setAccountExpires(accountExpires);

        // Act
        String retrievedC = usuarioLdap.getC();
        String retrievedCN = usuarioLdap.getCn();
        String retrievedDescription = usuarioLdap.getDescription();
        String retrievedDisplayName = usuarioLdap.getDisplayName();
        String retrievedDN = usuarioLdap.getDn();
        String retrievedGivenName = usuarioLdap.getGivenName();
        String retrievedHomeDrive = usuarioLdap.getHomeDrive();
        String retrievedInitials = usuarioLdap.getInitials();
        String retrievedName = usuarioLdap.getName();
        String retrievedObjectCategory = usuarioLdap.getObjectCategory();
        List<String> retrievedObjectClass = usuarioLdap.getObjectClass();
        String retrievedPhysicalDeliveryOfficeName = usuarioLdap.getPhysicalDeliveryOfficeName();
        String retrievedPostOfficeBox = usuarioLdap.getPostOfficeBox();
        String retrievedProfilePath = usuarioLdap.getProfilePath();
        String retrievedSAMAccountName = usuarioLdap.getsAMAccountName();
        String retrievedSN = usuarioLdap.getSn();
        String retrievedTitle = usuarioLdap.getTitle();
        String retrievedUserPrincipalName = usuarioLdap.getUserPrincipalName();
        String retrievedWWWHomePage = usuarioLdap.getwWWHomePage();
        String retrievedHomeMDB = usuarioLdap.getHomeMDB();
        String retrievedLegacyExchangeDN = usuarioLdap.getLegacyExchangeDN();
        String retrievedMail = usuarioLdap.getMail();
        String retrievedMailNickname = usuarioLdap.getMailNickname();
        String retrievedMDBUseDefaults = usuarioLdap.getmDBUseDefaults();
        String retrievedMsExchHomeServerName = usuarioLdap.getMsExchHomeServerName();
        String retrievedProxyAddresses = usuarioLdap.getProxyAddresses();
        String retrievedTargetAddress = usuarioLdap.getTargetAddress();
        String retrievedShowInAddressBook = usuarioLdap.getShowInAddressBook();
        String retrievedCompany = usuarioLdap.getCompany();
        String retrievedDepartment = usuarioLdap.getDepartment();
        String retrievedHomephone = usuarioLdap.getHomephone();
        String retrievedL = usuarioLdap.getL();
        String retrievedLocation = usuarioLdap.getLocation();
        String retrievedManager = usuarioLdap.getManager();
        String retrievedMobile = usuarioLdap.getMobile();
        String retrievedOU = usuarioLdap.getOu();
        String retrievedPwdLastSet = usuarioLdap.getPwdLastSet();
        String retrievedPostalCode = usuarioLdap.getPostalCode();
        String retrievedSt = usuarioLdap.getSt();
        String retrievedStreetAddress = usuarioLdap.getStreetAddress();
        String retrievedTelephoneNumber = usuarioLdap.getTelephoneNumber();
        String retrievedUserAccountControl = usuarioLdap.getUserAccountControl();
        String retrievedUrl = usuarioLdap.getUrl();
        String retrievedUserPassword = usuarioLdap.getUserPassword();
        String retrievedUid = usuarioLdap.getUid();
        String retrievedDistinguishedName = usuarioLdap.getDistinguishedName();
        String retrievedWhenCreated = usuarioLdap.getWhenCreated();
        String retrievedWhenChanged = usuarioLdap.getWhenChanged();
        String retrievedAccountExpires = usuarioLdap.getAccountExpires();

        // Assert
        assertEquals(c, retrievedC);
        assertEquals(CN, retrievedCN);
        assertEquals(description, retrievedDescription);
        assertEquals(displayName, retrievedDisplayName);
        assertEquals(DN, retrievedDN);
        assertEquals(givenName, retrievedGivenName);
        assertEquals(homeDrive, retrievedHomeDrive);
        assertEquals(initials, retrievedInitials);
        assertEquals(name, retrievedName);
        assertEquals(objectCategory, retrievedObjectCategory);
        assertEquals(objectClass, retrievedObjectClass);
        assertEquals(physicalDeliveryOfficeName, retrievedPhysicalDeliveryOfficeName);
        assertEquals(postOfficeBox, retrievedPostOfficeBox);
        assertEquals(profilePath, retrievedProfilePath);
        assertEquals(sAMAccountName, retrievedSAMAccountName);
        assertEquals(SN, retrievedSN);
        assertEquals(title, retrievedTitle);
        assertEquals(userPrincipalName, retrievedUserPrincipalName);
        assertEquals(wWWHomePage, retrievedWWWHomePage);
        assertEquals(homeMDB, retrievedHomeMDB);
        assertEquals(legacyExchangeDN, retrievedLegacyExchangeDN);
        assertEquals(mail, retrievedMail);
        assertEquals(mailNickname, retrievedMailNickname);
        assertEquals(mDBUseDefaults, retrievedMDBUseDefaults);
        assertEquals(msExchHomeServerName, retrievedMsExchHomeServerName);
        assertEquals(proxyAddresses, retrievedProxyAddresses);
        assertEquals(targetAddress, retrievedTargetAddress);
        assertEquals(showInAddressBook, retrievedShowInAddressBook);
        assertEquals(company, retrievedCompany);
        assertEquals(department, retrievedDepartment);
        assertEquals(homephone, retrievedHomephone);
        assertEquals(l, retrievedL);
        assertEquals(location, retrievedLocation);
        assertEquals(manager, retrievedManager);
        assertEquals(mobile, retrievedMobile);
        assertEquals(OU, retrievedOU);
        assertEquals(pwdLastSet, retrievedPwdLastSet);
        assertEquals(postalCode, retrievedPostalCode);
        assertEquals(st, retrievedSt);
        assertEquals(streetAddress, retrievedStreetAddress);
        assertEquals(telephoneNumber, retrievedTelephoneNumber);
        assertEquals(userAccountControl, retrievedUserAccountControl);
        assertEquals(url, retrievedUrl);
        assertEquals(userPassword, retrievedUserPassword);
        assertEquals(Uid, retrievedUid);
        assertEquals(distinguishedName, retrievedDistinguishedName);
        assertEquals(whenCreated, retrievedWhenCreated);
        assertEquals(whenChanged, retrievedWhenChanged);
        assertEquals(accountExpires, retrievedAccountExpires);
    }

    @Test
    public void testToMap() {
        // Arrange
        String displayName = "DisplayName";
        String CN = "Guy Thomas";
        usuarioLdap.setDisplayName(displayName);
        usuarioLdap.setCn(CN);

        // Act
        Map<String, String> map = usuarioLdap.toMap();

        // Assert
        assertEquals(displayName, map.get("displayName"));
    }
}
