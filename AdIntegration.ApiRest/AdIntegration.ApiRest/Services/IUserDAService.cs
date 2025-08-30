using AdIntegration.ApiRest.Dto;

namespace AdIntegration.ApiRest.Services
{
    public interface IUserDAService
    {
        /// <summary>
        /// Validar si la cuenta y el password existen en el DA
        /// </summary>
        /// <param name="cuentaUsuario"></param>
        /// <param name="password"></param>
        /// <returns>ResponseDataDto</returns>
        public ResponseDataDto validarUsuario(string cuentaUsuario, string password);
        /// <summary>
        /// Validar si la cuenta existe en el DA
        /// </summary>
        /// <param name="cuentaUsuario"></param>
        /// <returns>ResponseDataDto</returns>
        public ResponseDataDto validarExisteUsuario(string cuentaUsuario);
        /// <summary>
        /// Editar los datos del usuario en el DA
        /// </summary>
        /// <param name="cuentaUsuario"></param>
        /// <param name="userAD"></param>
        /// <returns>ResponseDataDto</returns>
        public ResponseDataDto editarUsuario(string cuentaUsuario, UserADDto userAD);
        /// <summary>
        /// Crear los datos del usuario en el DA
        /// </summary>
        /// <param name="userAD"></param>
        /// <returns>ResponseDataDto</returns>
        public ResponseDataDto crearUsuario(UserADDto userAD);
        /// <summary>
        /// Buscar un usuario por el nombre de usuario
        /// </summary>
        /// <param name="cuentaUsuario"></param>
        /// <returns>ResponseDataDto</returns>
        public ResponseDataDto buscarUsuario(string cuentaUsuario);
        /// <summary>
        /// Listar todos los usuarios del DA
        /// </summary>
        /// <param name="paraemtros"></param>
        /// <returns>ResponseDataDto</returns>
        public ResponseDataDto listarUsuarios(UserEntradaDto userEntradaDto);
        /// <summary>
        /// Editar los datos del usuario en el DA
        /// </summary>
        /// <param name="cuentaUsuario"></param>
        /// <param name="userAD"></param>
        /// <returns>ResponseDataDto</returns>
        public ResponseDataDto editarPassword(string cuentaUsuario, string password);
    }
}
