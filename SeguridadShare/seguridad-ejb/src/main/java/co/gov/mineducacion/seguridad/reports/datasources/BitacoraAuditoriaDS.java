package co.gov.mineducacion.seguridad.reports.datasources;

import java.text.SimpleDateFormat;
import java.util.List;

import co.gov.mineducacion.seguridad.modelo.dtos.BitacoraAuditoriaDTO;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 * Contiene él data source para el reporte de auditoria
 *
 * @author jfonseca
 */
public class BitacoraAuditoriaDS implements JRDataSource {

    private int contador = -1;

    private List<BitacoraAuditoriaDTO> bitacoraAuditoriaDTOList;

    public BitacoraAuditoriaDS(List<BitacoraAuditoriaDTO> bitacoraAuditoriaDTO) {
        this.bitacoraAuditoriaDTOList = bitacoraAuditoriaDTO;
    }

    @Override
    public boolean next() throws JRException {
        return (++contador) < bitacoraAuditoriaDTOList.size();
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        BitacoraAuditoriaDTO bitacoraAuditoriaDTO = bitacoraAuditoriaDTOList.get(contador);
        if ("fechaEvento".equals(jrf.getName())) {
            return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(bitacoraAuditoriaDTO.getFechaEvento());
        } else if ("tipoEvento".equals(jrf.getName())) {
            return bitacoraAuditoriaDTO.getCatalogos().getDescripcion();
        } else if ("usuarioId".equals(jrf.getName())) {
            return bitacoraAuditoriaDTO.getUsuarios().getLogonName();
        } else if ("aplicacionId".equals(jrf.getName())) {
            return bitacoraAuditoriaDTO.getAplicaciones().getNombre();
        } else if ("detalle".equals(jrf.getName())) {
            return bitacoraAuditoriaDTO.getDetalle() != null ? bitacoraAuditoriaDTO.getDetalle() : "";
        } else if ("campoDirectorio".equals(jrf.getName())) {
            return bitacoraAuditoriaDTO.getCampoDirectorio() != null ? bitacoraAuditoriaDTO.getCampoDirectorio() : "";
        }
        return null;
    }
}
