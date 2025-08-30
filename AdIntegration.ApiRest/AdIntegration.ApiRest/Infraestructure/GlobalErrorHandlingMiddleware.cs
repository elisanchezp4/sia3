using AdIntegration.ApiRest.Dto;
using AdIntegration.ApiRest.Exceptions;
using System.Net;
using System.Text.Json;

namespace AdIntegration.ApiRest.Infraestructure
{
    /// <summary>
    /// Clase para el manejo global de las excepciones
    /// </summary>
    public class GlobalErrorHandlingMiddleware
    {
        private readonly RequestDelegate _next;

        public GlobalErrorHandlingMiddleware(RequestDelegate next)
        {
            _next = next;
        }

        public async Task Invoke(HttpContext context)
        {
            try
            {
                await _next(context);
            }
            catch (Exception ex)
            {
                await HandleExceptionAsync(context, ex);
            }
        }

        private static Task HandleExceptionAsync(HttpContext context, Exception exception)
        {
            HttpStatusCode status = HttpStatusCode.OK;
            var stackTrace = String.Empty;
            string message = String.Empty; ;
            var exceptionType = exception.GetType();

            var responseDataDto = new ResponseDataDto();
            if (exceptionType == typeof(LdapApplicationException))
            {

                responseDataDto.error = true;
                responseDataDto.codigoRespuesta = (int)HttpStatusCode.BadRequest;
                responseDataDto.mensaje = exception.Message;
                responseDataDto.data = exception.StackTrace;
                status = HttpStatusCode.BadRequest;
                if (exception.Message.Contains("Fallo al leer el Apikey"))
                {
                    status = HttpStatusCode.Unauthorized;
                    responseDataDto.codigoRespuesta = (int)InfoCode.ApikeyInvalido;
                }
                if (exception.Message.Contains("El usuario no es valido"))
                {
                    responseDataDto.codigoRespuesta = (int)InfoCode.UsuarioNoValido;
                }
                if (exception.Message.Contains("The object exists"))
                {
                    responseDataDto.codigoRespuesta = (int)InfoCode.UsuarioExiste;
                    responseDataDto.data = Enum.GetName(typeof(InfoCode), 8);
                }

            }
            var exceptionResult = JsonSerializer.Serialize(responseDataDto);
            context.Response.ContentType = "application/json";
            context.Response.StatusCode = (int)status;
            return context.Response.WriteAsync(exceptionResult);
        }
    }
}
