package co.gov.mineducacion.seguridad.modelo.entidades;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "INFORMACION_ADICIONAL_USUARIO")
@Builder(toBuilder = true)
public class InformacionAdicionalUsuario implements Serializable {


    @Id
    @Column(name = "USUARIO_ID")
    private String usuarioId;

    @Column(name = "NUMERO_IDENTIFICACION", length = 50, unique = true)
    private String identificationNumber;

    @Column(name = "PRIMER_NOMBRE", length = 50)
    private String firstName;

    @Column(name = "SEGUNDO_NOMBRE", length = 50)
    private String secondName;

    @Column(name = "APELLIDO", length = 50)
    private String lastName;

    @Column(name = "SEGUNDO_APELLIDO", length = 50)
    private String secondLastName;

    @Column(name = "CORREO_ELECTRONICO", unique = true, length = 50)
    private String email;

    @OneToOne
    @MapsId
    @JoinColumn(name = "USUARIO_ID")
    private Usuario usuario;

    private static final long serialVersionUID = -5109453595226091900L;
}
