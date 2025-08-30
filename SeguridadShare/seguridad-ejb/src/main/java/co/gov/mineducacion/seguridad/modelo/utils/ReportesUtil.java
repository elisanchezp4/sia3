package co.gov.mineducacion.seguridad.modelo.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Map;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

/**
 * Utilidad que permite generar reportes con jasper reports
 *
 * @author John Fonseca <jfonseca@heinsohn.com.co>
 *
 */
public class ReportesUtil {

	/**
	 * formatos de reportes que se pueden generar
	 */
	public enum Formato {
		PDF, EXCEL
	}

	/**
	 * genera un reporte en formato pdf
	 * 
	 * @param archivoJasper
	 *            archivo jasper con el diseño del pdf
	 * @param parametros
	 *            mapa de parámetros necesarios para general el pdf
	 * @param dataSource
	 *            fuente de donde se obtienen los datos
	 * @return array de bytes con el contenido del reporte
	 * @throws JRException
	 * @throws IOException
	 */
	public static byte[] generarPDF(URL archivoJasper, Map<String, Object> parametros, JRDataSource dataSource)
			throws JRException, IOException {
		return generarReporte(archivoJasper, parametros, dataSource, Formato.PDF);
	}

	/**
	 * genera un reporte en formato exccel
	 * 
	 * @param archivoJasper
	 *            archivo jasper con el diseño del pdf
	 * @param parametros
	 *            mapa de parámetros necesarios para general el pdf
	 * @param dataSource
	 *            fuente de donde se obtienen los datos
	 * @return array de bytes con el contenido del reporte
	 * @throws JRException
	 * @throws IOException
	 */
	public static byte[] generarExcel(URL archivoJasper, Map<String, Object> parametros, JRDataSource dataSource)
			throws JRException, IOException {
		return generarReporte(archivoJasper, parametros, dataSource, Formato.EXCEL);
	}

	/**
	 * genera un reporte en formato exccel
	 * 
	 * @param archivoJasper
	 *            archivo jasper con el diseño del pdf
	 * @param parametros
	 *            mapa de parámetros necesarios para general el pdf
	 * @param dataSource
	 *            fuente de donde se obtienen los datos
	 * @param tipo
	 *            formato en el que se desea el reporte
	 * @return array de bytes con el contenido del reporte
	 * @throws JRException
	 * @throws IOException
	 */
	private static byte[] generarReporte(URL archivoJasper, Map<String, Object> parametros, JRDataSource dataSource,
			Formato tipo) throws JRException, IOException {
		/*
		 * en caso de que la fuente de datos se pase en los paramatros para una
		 * tabla o subreporte, se crea un datasource con solo una iteración
		 */
		if (dataSource == null) {
			dataSource = new JRDataSource() {
				int contador = 0;

				@Override
				public boolean next() throws JRException {
					return (contador++) < 1;
				}

				@Override
				public Object getFieldValue(JRField jrf) throws JRException {
					return null;
				}
			};
		}
		// se crea el flujo de bytes de salida
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(archivoJasper);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
		if (tipo == Formato.PDF) {
			JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
		} else {
			JRXlsxExporter xlsExporter = new JRXlsxExporter();
			xlsExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
			SimpleXlsxReportConfiguration xlsReportConfiguration = new SimpleXlsxReportConfiguration();
			xlsReportConfiguration.setOnePagePerSheet(false);
			xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
			xlsReportConfiguration.setDetectCellType(true);
			// se configura para que las celdas sean´el doble de grandes en
			// excell
			xlsReportConfiguration.setColumnWidthRatio(2.0f);
			xlsReportConfiguration.setWhitePageBackground(false);
			xlsExporter.setConfiguration(xlsReportConfiguration);
			xlsExporter.exportReport();
		}

		baos.close();
		byte[] bytes = baos.toByteArray();
		return bytes;

	}
	public static Calendar obtenerFechaActual() {
		Calendar fechaSistema = Calendar.getInstance();
		return fechaSistema;
	}
}