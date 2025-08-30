package co.gov.mineducacion.seguridad.web.servicio.utils;

import org.junit.Test;
import org.mockito.InjectMocks;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.Assert.*;

public class ObjectFactoryUtilsTest {

    @InjectMocks
    private ObjectFactoryUtils mockObjectFactoryUtils = new ObjectFactoryUtils();

    @Test
    public void testCreateAnyURI() {
        String uriValue = "https://example.com/resource";

        JAXBElement<String> result = mockObjectFactoryUtils.createAnyURI(uriValue);

        assertNotNull(result);
        assertEquals("anyURI", result.getName().getLocalPart());
        assertEquals(uriValue, result.getValue());
        assertEquals("http://schemas.microsoft.com/2003/10/Serialization/", result.getName().getNamespaceURI());
    }

    @Test
    public void testCreateChar() {
        int charValue = 65;
        JAXBElement<Integer> result = mockObjectFactoryUtils.createChar(charValue);

        assertNotNull(result);
        assertEquals("char", result.getName().getLocalPart());
        assertEquals((Integer) charValue, result.getValue());
        assertEquals("http://schemas.microsoft.com/2003/10/Serialization/", result.getName().getNamespaceURI());
    }


    @Test
    public void testCreateDateTime() throws DatatypeConfigurationException {
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        XMLGregorianCalendar dateTimeValue = datatypeFactory.newXMLGregorianCalendar("2023-08-11T15:30:00");

        JAXBElement<XMLGregorianCalendar> result = mockObjectFactoryUtils.createDateTime(dateTimeValue);

        assertNotNull(result);
        assertEquals("dateTime", result.getName().getLocalPart());
        assertEquals(dateTimeValue, result.getValue());
        assertEquals("http://schemas.microsoft.com/2003/10/Serialization/", result.getName().getNamespaceURI());
    }

    @Test
    public void testCreateQName() {
        QName qNameValue = new QName("http://example.com/namespace", "elementName");

        JAXBElement<QName> result = mockObjectFactoryUtils.createQName(qNameValue);

        assertNotNull(result);
        assertEquals("QName", result.getName().getLocalPart());
        assertEquals(qNameValue, result.getValue());
        assertEquals("http://schemas.microsoft.com/2003/10/Serialization/", result.getName().getNamespaceURI());
    }

    @Test
    public void testCreateUnsignedShort() {
        int unsignedShortValue = 12345;
        JAXBElement<Integer> result = mockObjectFactoryUtils.createUnsignedShort(unsignedShortValue);

        assertNotNull(result);
        assertEquals("unsignedShort", result.getName().getLocalPart());
        assertEquals((Integer) unsignedShortValue, result.getValue());
        assertEquals("http://schemas.microsoft.com/2003/10/Serialization/", result.getName().getNamespaceURI());
    }

    @Test
    public void testCreateFloat() {
        float floatValue = 123.456f;
        JAXBElement<Float> result = mockObjectFactoryUtils.createFloat(floatValue);

        assertNotNull(result);
        assertEquals("float", result.getName().getLocalPart());
        assertEquals(floatValue, result.getValue(), 0.0f);
        assertEquals("http://schemas.microsoft.com/2003/10/Serialization/", result.getName().getNamespaceURI());
    }

    @Test
    public void testCreateLong() {
        long longValue = 1234567890L;
        JAXBElement<Long> result = mockObjectFactoryUtils.createLong(longValue);

        assertNotNull(result);
        assertEquals("long", result.getName().getLocalPart());
        assertEquals(longValue, (long) result.getValue());
        assertEquals("http://schemas.microsoft.com/2003/10/Serialization/", result.getName().getNamespaceURI());
    }

    @Test
    public void testCreateShort() {
        short shortValue = 12345;
        JAXBElement<Short> result = mockObjectFactoryUtils.createShort(shortValue);

        assertNotNull(result);
        assertEquals("short", result.getName().getLocalPart());
        assertEquals(shortValue, (short) result.getValue());
        assertEquals("http://schemas.microsoft.com/2003/10/Serialization/", result.getName().getNamespaceURI());
    }

    @Test
    public void testCreateBase64Binary() {
        byte[] byteArrayValue = "Hello, World!".getBytes();

        JAXBElement<byte[]> result = mockObjectFactoryUtils.createBase64Binary(byteArrayValue);

        assertNotNull(result);
        assertEquals("base64Binary", result.getName().getLocalPart());
        assertArrayEquals(byteArrayValue, result.getValue());
        assertEquals("http://schemas.microsoft.com/2003/10/Serialization/", result.getName().getNamespaceURI());
    }

    @Test
    public void testCreateByte() {
        byte byteValue = 42;
        JAXBElement<Byte> result = mockObjectFactoryUtils.createByte(byteValue);

        assertNotNull(result);
        assertEquals("byte", result.getName().getLocalPart());
        assertEquals((Byte) byteValue, result.getValue());
        assertEquals("http://schemas.microsoft.com/2003/10/Serialization/", result.getName().getNamespaceURI());
    }

    @Test
    public void testCreateBoolean() {
        boolean booleanValue = true;
        JAXBElement<Boolean> result = mockObjectFactoryUtils.createBoolean(booleanValue);

        assertNotNull(result);
        assertEquals("boolean", result.getName().getLocalPart());
        assertEquals(booleanValue, result.getValue());
        assertEquals("http://schemas.microsoft.com/2003/10/Serialization/", result.getName().getNamespaceURI());
    }

    @Test
    public void testCreateUnsignedByte() {
        short unsignedByteValue = 200;
        JAXBElement<Short> result = mockObjectFactoryUtils.createUnsignedByte(unsignedByteValue);

        assertNotNull(result);
        assertEquals("unsignedByte", result.getName().getLocalPart());
        assertEquals(unsignedByteValue, (short) result.getValue());
        assertEquals("http://schemas.microsoft.com/2003/10/Serialization/", result.getName().getNamespaceURI());
    }

    @Test
    public void testCreateAnyType() {
        Object anyTypeValue = new Object();
        JAXBElement<Object> result = mockObjectFactoryUtils.createAnyType(anyTypeValue);

        assertNotNull(result);
        assertEquals("anyType", result.getName().getLocalPart());
        assertEquals("http://schemas.microsoft.com/2003/10/Serialization/", result.getName().getNamespaceURI());
    }

    @Test
    public void testCreateUnsignedInt() {
        long unsignedIntValue = 4294967295L;
        JAXBElement<Long> result = mockObjectFactoryUtils.createUnsignedInt(unsignedIntValue);

        assertNotNull(result);
        assertEquals("unsignedInt", result.getName().getLocalPart());
        assertEquals(unsignedIntValue, (long) result.getValue());
        assertEquals("http://schemas.microsoft.com/2003/10/Serialization/", result.getName().getNamespaceURI());
    }

    @Test
    public void testCreateInt() {
        int intValue = 1234567890;
        JAXBElement<Integer> result = mockObjectFactoryUtils.createInt(intValue);

        assertNotNull(result);
        assertEquals("int", result.getName().getLocalPart());
        assertEquals(intValue, (int) result.getValue());
        assertEquals("http://schemas.microsoft.com/2003/10/Serialization/", result.getName().getNamespaceURI());
    }

    @Test
    public void testCreateDecimal() {
        BigDecimal decimalValue = new BigDecimal(123);
        JAXBElement<BigDecimal> result = mockObjectFactoryUtils.createDecimal(decimalValue);

        assertNotNull(result);
        assertEquals("decimal", result.getName().getLocalPart());
        assertEquals(decimalValue, result.getValue());
        assertEquals("http://schemas.microsoft.com/2003/10/Serialization/", result.getName().getNamespaceURI());
    }

    @Test
    public void testCreateDouble() {
        double doubleValue = 3.14159265359;
        JAXBElement<Double> result = mockObjectFactoryUtils.createDouble(doubleValue);

        assertNotNull(result);
        assertEquals("double", result.getName().getLocalPart());
        assertEquals(doubleValue, (double) result.getValue(), 0.0);
        assertEquals("http://schemas.microsoft.com/2003/10/Serialization/", result.getName().getNamespaceURI());
    }

    @Test
    public void testCreateGuid() {
        String guidValue = "123e4567-e89b-12d3-a456-426614174000";
        JAXBElement<String> result = mockObjectFactoryUtils.createGuid(guidValue);

        assertNotNull(result);
        assertEquals("guid", result.getName().getLocalPart());
        assertEquals(guidValue, result.getValue());
        assertEquals("http://schemas.microsoft.com/2003/10/Serialization/", result.getName().getNamespaceURI());
    }

    @Test
    public void testCreateDuration() throws Exception {
        Duration durationValue = DatatypeFactory.newInstance().newDurationDayTime(true, 1, 2, 30, 15);

        JAXBElement<Duration> result = mockObjectFactoryUtils.createDuration(durationValue);

        assertNotNull(result);
        assertEquals("duration", result.getName().getLocalPart());
        assertEquals(durationValue, result.getValue());
        assertEquals("http://schemas.microsoft.com/2003/10/Serialization/", result.getName().getNamespaceURI());
    }

    @Test
    public void testCreateString() {
        String stringValue = "Hello, World!";
        JAXBElement<String> result = mockObjectFactoryUtils.createString(stringValue);

        assertNotNull(result);
        assertEquals("string", result.getName().getLocalPart());
        assertEquals(stringValue, result.getValue());
        assertEquals("http://schemas.microsoft.com/2003/10/Serialization/", result.getName().getNamespaceURI());
    }

    @Test
    public void testCreateUnsignedLong() {
        BigInteger unsignedLongValue = new BigInteger("12345678901234567890"); // Valor de ejemplo de un unsigned long
        JAXBElement<BigInteger> result = mockObjectFactoryUtils.createUnsignedLong(unsignedLongValue);

        assertNotNull(result);
        assertEquals("unsignedLong", result.getName().getLocalPart());
        assertEquals(unsignedLongValue, result.getValue());
        assertEquals("http://schemas.microsoft.com/2003/10/Serialization/", result.getName().getNamespaceURI());
    }


}