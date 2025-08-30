namespace AdIntegration.ApiRest.Infraestructure
{
    /// <summary>
    /// Clase de configuracion de la aplicacion
    /// </summary>
    public class LdapConfig
    {
        /// <summary>
        /// Atributo que guardara ruta
        /// </summary>
        public string Path { get; set; }
        /// <summary>
        /// Atributo que guardara puerto
        /// </summary>
        public string Port { get; set; }
        /// <summary>
        /// Atributo que guardara nombre de usuario de dominio
        /// </summary>
        public string UserDomainName { get; set; }
        /// <summary>
        /// Atributo que guardara contraseña de usuario de dominio
        /// </summary>
        public string UserDomainPassword { get; set; }
        /// <summary>
        /// Atributo que guardara Nombre Dominio
        /// </summary>
        public string Dn { get; set; }
        /// <summary>
        /// Atributo que guardara Unidad Organizativa
        /// </summary>
        public string Ou { get; set; }
        /// <summary>
        /// Atributo que guardara api key
        /// </summary>
        public string KeyApi { get; set; }
        /// <summary>
        /// Atributo que guardara dominio de usuario logeado
        /// </summary>
        public string UserLogonDomain { get; set; }
    }
}
