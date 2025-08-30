using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace AdIntegration.ApiRest.Infraestructure
{
    /// <summary>
    /// Clase para la codificacion de mensajes
    /// </summary>
    public enum InfoCode: int
    {
        Exitoso = 0,
        UsuarioNoExiste = 1,
        UsuarioInactivo = 2,
        PasswordInvalido = 3,
        NoSeEncontraronRegistros = 4,
        ErrorCrearUsuario = 5,
        UsuarioNoValido = 6,
        ApikeyInvalido = 7,
        UsuarioExiste = 8
    }
}
