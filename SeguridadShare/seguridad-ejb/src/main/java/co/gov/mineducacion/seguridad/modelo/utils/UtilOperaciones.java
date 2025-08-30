package co.gov.mineducacion.seguridad.modelo.utils;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Utilidades de conversión entre objetos o listas.
 * 
 * @author jsoto
 */
public class UtilOperaciones {

	private UtilOperaciones() {
	}

	/**
	 * Convierte una lista de objetos a una lista de su representación en String
	 * 
	 * @param objetos
	 *            Lista de objetos a convertir a String
	 * @return Lista de cadena de caracteres. La lista es vacia si la lista del
	 *         parametro no contiene objetos o es nula.
	 */
	public static List<String> convertirListaObjetosAString(List<Object> objetos) {
		List<String> lista = new ArrayList<>();
		if (objetos != null && !objetos.isEmpty()) {
			for (Object objeto : objetos) {
				lista.add(objeto.toString());
			}
		}

		return lista;
	}

	/**
	 * Genera una cadena aleatoria de numeros y letras 
	 * @param len longitud de la cadena
	 * @return
	 */
	public static String randomString(int len) {
		String ab = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		SecureRandom rnd = new SecureRandom();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(ab.charAt(rnd.nextInt(ab.length())));
		return sb.toString();
	}

	/**
	 * convierte el objeto Timestamp recibido por parametro en un objeto tipo XMLGregorianCalendar
	 * @param time
	 * @return
	 * @throws DatatypeConfigurationException
	 */
	public static XMLGregorianCalendar convertirTimestampAXMLGregorian(
			Timestamp time) throws DatatypeConfigurationException {
		GregorianCalendar c = new GregorianCalendar();
		Date date = new Date();
		date.setTime(time.getTime());
		c.setTime(date);
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
	}
}
