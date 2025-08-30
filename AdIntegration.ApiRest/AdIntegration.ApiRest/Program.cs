using AdIntegration.ApiRest.Infraestructure;
using AdIntegration.ApiRest.Services;

var builder = WebApplication.CreateBuilder(args);


var configuration = builder.Configuration;

// Agregar los servicios al contenedor de la aplicacion.
var ldapSection = configuration.GetSection("Ldap");
builder.Services.AddScoped<IUserDAService, UserDAService>();
builder.Services.Configure<LdapConfig>(ldapSection);
builder.Services.AddScoped<SimpleFiltreRules>();

builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen(c =>
{
       
});

var app = builder.Build();

// Se configura la respuesta de las peticiones HTTP.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseMiddleware(typeof(GlobalErrorHandlingMiddleware));// Se activa el middleware para el manejo de las excepciones a nivel general
//app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();
