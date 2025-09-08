package co.gov.mineducacion.seguridad.modelo.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USUARIOS_EXTERNOS")
@AttributeOverrides({
        @AttributeOverride(name = "usuarioId", column = @Column(name = "USUARIO_ID")),
        @AttributeOverride(name = "estado", column = @Column(name = "ESTADO"))
})

public class UsuarioExterno extends Usuario implements Serializable {

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

    @Column(name = "EMAIL", unique = true, length = 50)
    private String email;

    @Column(name = "CONTRASENA_HASH", length = 2000)
    private String passwordHash;

    @Column(name = "ESTADO", length = 10)
    private String status;

    @Column(name = "ESTA_MIGRADO")
    private Boolean isMigrated;

    @Column(name = "FECHA_CAMBIO_CONTRASENA")
    private LocalDateTime passwordChangedAt;

    @Column(name = "USUARIO_CREACION")
    private String createdUser;

    @Column(name = "FECHA_CREACION")
    private LocalDateTime createdDate;

    @Column(name = "USUARIO_ACTUALIZACION")
    private String updatedUser;

    @Column(name = "FECHA_ACTUALIZACION")
    private LocalDateTime updatedDate;

    private static final long serialVersionUID = -5109453595226091900L;

    @PrePersist
    private void beforePersist(){
        this.createdDate = LocalDateTime.now();
    }
    @PreUpdate
    private void beforeUpdate(){
        this.updatedDate = LocalDateTime.now();
        this.passwordChangedAt = LocalDateTime.now();
    }
}
