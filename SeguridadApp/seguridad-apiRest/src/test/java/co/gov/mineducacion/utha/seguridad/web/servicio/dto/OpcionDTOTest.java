package co.gov.mineducacion.utha.seguridad.web.servicio.dto;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.Assert.*;

public class OpcionDTOTest {

    @InjectMocks
    private OpcionDTO mockOpcionDTO = new OpcionDTO();

    @Mock
    private List<OpcionDTO> mockHijosOpcionList;

    @Test
    public void testGetterAndSetter(){
        String descripcion = "EstoNoEsUnaDescripcion";
        String nombreObjeto = "TestObjet";
        Integer opcionId = 25;
        String urlImgGif = "Image";
        String tipo = "R-Positivo";
        String nombreRolAsociado = "TesterRol";

        mockOpcionDTO.setDescripcion(descripcion);
        mockOpcionDTO.setHijosOpcion(mockHijosOpcionList);
        mockOpcionDTO.setNombreObjeto(nombreObjeto);
        mockOpcionDTO.setOpcionId(opcionId);
        mockOpcionDTO.setUrlImgGif(urlImgGif);
        mockOpcionDTO.setTipo(tipo);
        mockOpcionDTO.setNombreRolAsociado(nombreRolAsociado);

        assertEquals(descripcion, mockOpcionDTO.getDescripcion());
        assertEquals(mockHijosOpcionList, mockOpcionDTO.getHijosOpcion());
        assertEquals(nombreObjeto, mockOpcionDTO.getNombreObjeto());
        assertEquals(opcionId, mockOpcionDTO.getOpcionId());
        assertEquals(urlImgGif, mockOpcionDTO.getUrlImgGif());
        assertEquals(tipo, mockOpcionDTO.getTipo());
        assertEquals(nombreRolAsociado, mockOpcionDTO.getNombreRolAsociado());
    }
}