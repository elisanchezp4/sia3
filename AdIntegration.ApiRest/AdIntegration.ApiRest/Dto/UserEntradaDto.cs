namespace AdIntegration.ApiRest.Dto
{
    /// <summary>
    /// Clase DTO que encapsulara los atributos de busqueda de los usuarios
    /// </summary>
    public class UserEntradaDto
    {
        /// <summary>
        /// Atributo que guardara user name
        /// </summary>
        public string? accountName { get; set; }
        /// <summary>
        /// Atributo que guardara primer nombre
        /// </summary>
        public string? firstName { get; set; }
        /// <summary>
        /// Atributo que guardara apellido
        /// </summary>
        public string? lastName { get; set; }
        /// <summary>
        /// Atributo que guardara email
        /// </summary>
        public string? email { get; set; }
        /// <summary>
        /// Atributo que guardara estado activo/inactivo
        /// </summary>
        public string? enabled { get; set; }
    }
}
