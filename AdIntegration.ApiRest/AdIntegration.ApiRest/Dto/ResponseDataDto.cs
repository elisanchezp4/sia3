namespace AdIntegration.ApiRest.Dto
{
    /// <summary>
    /// Clase DTO que encapsulara la respuesta de los servicios
    /// </summary>
    public class ResponseDataDto
    {
        /// <summary>
        /// Atributo que guardara valor de la respuesta
        /// </summary>
        public int codigoRespuesta { get; set; } = 200;
        /// <summary>
        /// Atributo que guardara si hay error en la respuesta
        /// </summary>
        public bool error { get; set; }
        /// <summary>
        /// Atributo que guardara mensaje de la respuesta
        /// </summary>
        public string mensaje { get; set; }
        /// <summary>
        /// Atributo que guardara objeto con la informacion de la respuesta o mensaje mas detallado de error
        /// </summary>
        public Object? data { get; set; }
    }
}
