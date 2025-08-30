using AdIntegration.ApiRest.Dto;
using AdIntegration.ApiRest.Exceptions;
using AdIntegration.ApiRest.Services;
using Microsoft.AspNetCore.Mvc;

namespace AdIntegration.ApiRest.Controllers
{
    /// <summary>
    /// Clase con los servicios API Rest a exponer
    /// </summary>
    [ApiController]
    [Route("[controller]")]
    public class ADIntegationController : BaseController
    {
        private readonly ILogger<ADIntegationController> _logger;
        private readonly IUserDAService _userDAService;
        private readonly string ultimaVersion = "1.10";

        public ADIntegationController(ILogger<ADIntegationController> logger, IUserDAService userDAService)
        {
            _logger = logger;
            _userDAService = userDAService;
        }

        /// <summary>
        /// Servicio rest que valida la ultima version del sistema
        /// </summary>
        /// <param name=""></param>
        /// <returns>ResponseDataDto</returns>
        [HttpGet("obtenerversion")]
        public ResponseDataDto obtenerVersion()
        {
            return new ResponseDataDto 
            {
                error = false,
                mensaje = "",
                data = ultimaVersion
            };

        }

        /// <summary>
        /// Servicio rest que valida si existe un usuario
        /// </summary>
        /// <param name="usuario"></param>
        /// <returns>ResponseDataDto</returns>
        [HttpGet("validarexisteusuario/{usuario}")]
        public ResponseDataDto validarExisteUsuario(string usuario)
        {
            return _userDAService.validarExisteUsuario(usuario);

        }


        /// <summary>
        /// Servicio rest que busca los datos de un usuario
        /// </summary>
        /// <param name="usuario"></param>
        /// <returns>ResponseDataDto</returns>
        [HttpGet("buscarusuario/{usuario}")]
        public ResponseDataDto buscarUsuario(string usuario)
        {
            return _userDAService.buscarUsuario(usuario);
        }


        /// <summary>
        /// Servicio rest que valida si existe un usuario y la constrase�a es valida
        /// </summary>
        /// <param name="userADDto"></param>
        /// <returns>ResponseDataDto</returns>
        [HttpPost("validarusuario")]
        public ResponseDataDto validarUsuario([FromBody] UserADDto userADDto)
        {
            return _userDAService.validarUsuario(userADDto.SamAccountName!, userADDto.Password!);

        }


        /// <summary>
        /// Servicio rest que lista todos los usuarios de una unidad organizativa
        /// </summary>
        /// <param name="userEntradaDto"></param>
        /// <returns>ResponseDataDto</returns>
        [HttpGet("listarusuarios")]
        public ResponseDataDto listarUsuarios([FromBody] UserEntradaDto userEntradaDto)
        {            
            return _userDAService.listarUsuarios(userEntradaDto);

        }


        /// <summary>
        /// Servicio rest que modifica los datos de un usuario
        /// </summary>
        /// <param name="userADDto"></param>
        /// <returns></returns>
        [HttpPut("editarusuario")]
        public ResponseDataDto editarUsuario([FromBody] UserADDto userADDto)
        {
            return _userDAService.editarUsuario(userADDto.SamAccountName, userADDto);

        }

        /// <summary>
        /// Servicio rest que crea los datos de un usuario
        /// </summary>
        /// <param name="userADDto"></param>
        /// <returns></returns>
        [HttpPost("crearusuario")]
        public ResponseDataDto crearUsuario([FromBody] UserADDto userADDto)
        {
            return _userDAService.crearUsuario(userADDto);

        }

        /// <summary>
        /// Servicio rest que modifica la contrase�a de un usuario
        /// </summary>
        /// <param name="userADDto"></param>
        /// <returns></returns>
        [HttpPut("editarpassword")]
        public ResponseDataDto editarPassword([FromBody] UserADDto userADDto )
        {

            return _userDAService.editarPassword(userADDto.SamAccountName!, userADDto.Password!);
        }
    }
}