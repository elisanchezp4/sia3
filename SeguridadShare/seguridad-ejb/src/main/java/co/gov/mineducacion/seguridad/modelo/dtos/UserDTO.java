package co.gov.mineducacion.seguridad.modelo.dtos;

import co.gov.mineducacion.seguridad.modelo.entidades.BitacoraAuditoria;
import co.gov.mineducacion.seguridad.modelo.entidades.InformacionAdicionalUsuario;
import co.gov.mineducacion.seguridad.modelo.entidades.TokensActivos;
import co.gov.mineducacion.seguridad.modelo.entidades.UsuariosRol;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserDTO implements Serializable {

	@JsonProperty("usuarioId")
	private String userId;

	@JsonProperty("ruta")
	private String route;

	@JsonProperty("nuevoPass")
	private String passNew;

	@JsonProperty("tipoUsuario")
	private BigDecimal userType;

	@JsonProperty("estado")
	private BigDecimal status;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonProperty("fechaCreacion")
	private LocalDateTime createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonProperty("ultimaModificacion")
	private LocalDateTime updatedDate;

	@JsonProperty("usuarioModificacion")
	private String updatedUser;

	@JsonProperty("usuarioIniciaSesion")
	private String logonName;

	@JsonProperty("bitacoraAuditoriaList")
	private List<BitacoraAuditoria> audits;

	@JsonProperty("tokensActivosList")
	private List<TokensActivos> tokens;

	@JsonProperty("usuariosRolList")
	private List<UsuariosRol> roles;

	@JsonProperty("informacionAdicional")
	private InformacionAdicionalUsuario additionalInformation;

	@JsonProperty("numeroIdentificacion")
	private String identificationNumber;

	@JsonProperty("primerNombre")
	private String firstName;

	@JsonProperty("segundoNombre")
	private String secondName;

	@JsonProperty("apellido")
	private String lastName;

	@JsonProperty("segundoNombre")
	private String secondLastName;

	@JsonProperty("correoElectronico")
	private String email;

	@JsonProperty("contrasenaHash")
	private String passwordHash;

	private static final long serialVersionUID = 650813943555061566L;
}
