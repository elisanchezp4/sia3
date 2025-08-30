using AdIntegration.ApiRest.Exceptions;
using AdIntegration.ApiRest.Services;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Filters;
using Microsoft.Extensions.Options;

namespace AdIntegration.ApiRest.Infraestructure
{
    /// <summary>
    /// Clase de filtrado de las peticiones
    /// </summary>
    public class SimpleFiltreRules : IActionFilter
    {
        private LdapConfig config;
        private ILogger<SimpleFiltreRules> _logger;

        public SimpleFiltreRules(ILogger<SimpleFiltreRules> logger, IOptions<LdapConfig> config)
        {
            this.config = config.Value;
            _logger = logger;
        }
        public void OnActionExecuted(ActionExecutedContext context)
        {

        }

        public void OnActionExecuting(ActionExecutingContext context)
        {
            // Do something before the action executes.
            var keyPost = context.HttpContext.Request.Headers["x-api-key"];
            if (keyPost.FirstOrDefault() != config.KeyApi)
            {
                _logger.LogError("Fallo al leer el Apikey de la aplicación ");
                throw new LdapApplicationException("Fallo al leer el Apikey de la aplicación");
            }

        }
    }
}
