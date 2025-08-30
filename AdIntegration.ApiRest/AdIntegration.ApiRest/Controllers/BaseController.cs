using AdIntegration.ApiRest.Infraestructure;
using Microsoft.AspNetCore.Mvc;

namespace AdIntegration.ApiRest.Controllers
{
    [ServiceFilter(typeof(SimpleFiltreRules))]
    public class BaseController : ControllerBase
    {

    }
}
