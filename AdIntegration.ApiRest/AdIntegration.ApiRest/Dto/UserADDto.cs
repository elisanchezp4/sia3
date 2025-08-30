namespace AdIntegration.ApiRest.Dto
{
    /// <summary>
    /// Clase DTO que encapsulara los atributos del usuario
    /// </summary>
    public class UserADDto
    {
        /// <summary>
        /// Atributo que guardara user name
        /// </summary>
        public string? SamAccountName { get; set; }
        /// <summary>
        /// Atributo que guardara nombre de pila
        /// </summary>
        public string? GivenName { get; set; }
        /// <summary>
        /// Atributo que guardara apellido
        /// </summary>
        public string? Surname { get; set; }
        /// <summary>
        /// Atributo que guardara nombre para mostrar
        /// </summary>
        public string? DisplayName { get; set; }
        /// <summary>
        /// Atributo que guardara email
        /// </summary>
        public string? Mail { get; set; }
        /// <summary>
        /// Atributo que guardara nombre
        /// </summary>
        public string? Name { get; set; }
        /// <summary>
        /// Atributo que guardara contraseña
        /// </summary>
        public string? Password { get; set; }
        /// <summary>
        /// Atributo que guardara descripcion
        /// </summary>
        public string? Description { get; set; }
        /// <summary>
        /// Atributo que guardara estado activo/inactivo
        /// </summary>
        public bool? Enabled { get; set; }
        /// <summary>
        /// Atributo que guardara DN la secuencia de nombres distinguidos relativos (RDN)
        /// </summary>
        public string? DistinguishedName { get; set; }
    }
}
