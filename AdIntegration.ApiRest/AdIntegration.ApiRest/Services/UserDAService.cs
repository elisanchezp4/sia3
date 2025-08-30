using AdIntegration.ApiRest.Dto;
using AdIntegration.ApiRest.Exceptions;
using AdIntegration.ApiRest.Infraestructure;
using Microsoft.Extensions.Options;
using System.DirectoryServices.Protocols;
using System.Net;

namespace AdIntegration.ApiRest.Services
{
    /// <summary>
    /// Clase con los servicios de acceso a la data 
    /// </summary>
    public class UserDAService : IUserDAService
    {
        private LdapDirectoryIdentifier servidor;
        private LdapConnection ldapConnection;
        private readonly ILogger<UserDAService> _logger;
        private readonly LdapConfig config;
        private const string USUARIOS_INTERNOS = "Ministerio_Educacion";
        private const string USUARIOS_EXTERNOS = "usuarios externos";
        private const string INTERNOS = "Internos";

        public UserDAService(ILogger<UserDAService> logger, IOptions<LdapConfig> config)
        {
            _logger = logger;
            this.config = config.Value;
            try
            {
                servidor = new LdapDirectoryIdentifier(
                    this.config.Path,
                    int.Parse(this.config.Port),
                    fullyQualifiedDnsHostName: true,
                    connectionless: false);
                var credencial = new NetworkCredential(
                    this.config.UserDomainName,
                    this.config.UserDomainPassword);
                ldapConnection = new LdapConnection(servidor, credencial)
                {
                    AuthType = AuthType.Basic
                };
                ldapConnection.SessionOptions.ProtocolVersion = 3;
            }
            catch (Exception e)
            {
                _logger.LogError("Error al conectarse al DA " + e.Message);
                throw new LdapApplicationException("Error al conectarse al DA");
            }

        }
        /// <summary>
        /// Metodo que busca los datos de un usuario
        /// </summary>
        public ResponseDataDto buscarUsuario(string cuentaUsuario)
        {
            try
            {
                var usuario = buscarUsuarioInternoExterno(cuentaUsuario);
                UserADDto userADDto = new UserADDto();
                if (usuario != null)
                {

                    //var Username = uid[0] as string;
                    userADDto.Name = usuario.Attributes["name"] == null ? string.Empty : usuario.Attributes["DisplayName"][0] as string;
                    userADDto.GivenName = usuario.Attributes["givenName"] == null ? string.Empty : usuario.Attributes["givenName"][0] as string;
                    userADDto.Mail = usuario.Attributes["mail"] == null ? string.Empty : usuario.Attributes["mail"][0] as string;
                    userADDto.DistinguishedName = usuario.DistinguishedName;
                    userADDto.Description = usuario.Attributes["description"] == null ? string.Empty : usuario.Attributes["description"][0] as string;
                    userADDto.Surname = usuario.Attributes["sn"] == null ? string.Empty : usuario.Attributes["sn"][0] as string;
                    userADDto.DisplayName = usuario.Attributes["name"] == null ? string.Empty : userADDto.GivenName + " " + userADDto.Surname;
                    userADDto.SamAccountName = usuario.Attributes["samaccountname"] == null ? string.Empty : usuario.Attributes["samaccountname"][0] as string;
                    if (usuario.Attributes["userAccountControl"] != null)
                    {
                        var userAccountControl = usuario.Attributes["userAccountControl"][0] as string;
                        if (!string.IsNullOrEmpty(userAccountControl))
                        {
                            userADDto.Enabled = false;
                            if (Convert.ToDouble(userAccountControl) % 8 == 0)
                            {
                                userADDto.Enabled = true;
                            }
                        }
                    }
                }
                else
                {
                    return new ResponseDataDto { codigoRespuesta = (int)InfoCode.UsuarioNoExiste, error = false, mensaje = "El usuario no existe ", data = Enum.GetName(typeof(InfoCode), 1) };
                }
                return new ResponseDataDto { codigoRespuesta = (int)InfoCode.Exitoso, error = false, mensaje = "El usuario es valido ", data = userADDto };
            }
            catch (Exception e)
            {
                _logger.LogError("El usuario no es valido " + e.Message);
                throw new LdapApplicationException("El usuario no es valido: " + e.Message);
            }
        }


        public SearchResultEntry buscarUsuarioInternoExterno(string cuentaUsuario)
        {
            var usuario = buscarUsuarioLDAP(cuentaUsuario, USUARIOS_INTERNOS);
            if (usuario == null)
            {
                usuario = buscarUsuarioLDAP(cuentaUsuario, USUARIOS_EXTERNOS);
            }
            return usuario;
        }

        public SearchResultEntry buscarUsuarioLDAP(string cuentaUsuario, string ou)
        {
            var peticion = new SearchRequest(
                    "ou=" + ou + "," + config.Dn,
                    "(samaccountname=" + cuentaUsuario + ")",
                    SearchScope.Subtree);
            peticion.SizeLimit = 1;

            var resultado = (SearchResponse)ldapConnection.SendRequest(peticion);
            var usuario = resultado.Entries.Count > 0 ? resultado.Entries[0] : null;
            return usuario;
        }

        /// <summary>
        /// Metodo que modifica los datos de un usuario incluyendo la contraseña
        /// </summary>
        public ResponseDataDto editarUsuario(string cuentaUsuario, UserADDto userAD)
        {
            try
            {
                var usuario = buscarUsuarioInternoExterno(cuentaUsuario);
                long codeActive = 0;
                UserADDto userADDto = new UserADDto();
                if (usuario != null)
                {
                    userADDto.Name = usuario.Attributes["name"] == null ? string.Empty : usuario.Attributes["name"][0] as string;
                    userADDto.Surname = usuario.Attributes["sn"] == null ? string.Empty : usuario.Attributes["sn"][0] as string;
                    userADDto.Mail = usuario.Attributes["mail"] == null ? string.Empty : usuario.Attributes["mail"][0] as string;
                    userADDto.DistinguishedName = usuario.DistinguishedName;
                    userADDto.DisplayName = usuario.Attributes["displayname"] == null ? string.Empty : usuario.Attributes["displayname"][0] as string;
                    userADDto.Description = usuario.Attributes["description"] == null ? string.Empty : usuario.Attributes["description"][0] as string;
                    userADDto.GivenName = usuario.Attributes["givenName"] == null ? string.Empty : usuario.Attributes["givenName"][0] as string;
                    userADDto.SamAccountName = usuario.Attributes["samaccountname"] == null ? string.Empty : usuario.Attributes["samaccountname"][0] as string;
                    if (usuario.Attributes["userAccountControl"] != null)
                    {
                        var userAccountControl = usuario.Attributes["userAccountControl"][0] as string;
                        codeActive = Convert.ToInt64(userAccountControl);
                        userADDto.Enabled = false;
                        if (Convert.ToDouble(userAccountControl) % 8 == 0)
                        {
                            userADDto.Enabled = true;
                        }
                        else
                        {
                            codeActive = codeActive - 2;
                        }
                    }
                    else userAD.Enabled = null;
                }
                else
                {
                    return new ResponseDataDto { codigoRespuesta = (int)InfoCode.NoSeEncontraronRegistros, error = true, mensaje = "El usuario no existe ", data = "El usuario no existe " };
                }

                userAD.GivenName = string.IsNullOrEmpty(userAD.GivenName) ? userADDto.GivenName : userAD.GivenName;
                userAD.Name = string.IsNullOrEmpty(userAD.Name) ? userADDto.GivenName + " " + userADDto.Surname : userAD.Name;
                userAD.Surname = string.IsNullOrEmpty(userAD.Surname) ? userADDto.Surname : userAD.Surname;
                userAD.DistinguishedName = string.IsNullOrEmpty(userAD.DistinguishedName) ? userADDto.DistinguishedName : userAD.DistinguishedName;
                userAD.Description = string.IsNullOrEmpty(userAD.Description) ? userADDto.Description : userAD.Description;
                userAD.DisplayName = string.IsNullOrEmpty(userAD.Name) ? userADDto.Name : userAD.Name;
                userAD.SamAccountName = string.IsNullOrEmpty(userAD.SamAccountName) ? userADDto.SamAccountName : userAD.SamAccountName;
                userAD.Mail = string.IsNullOrEmpty(userAD.Mail) ? userADDto.Mail : userAD.Mail;


                DirectoryRequest peticionEditar = new ModifyRequest();
                DirectoryResponse resultadorEditar;
                if (!string.IsNullOrEmpty(userAD.Surname))
                {
                    peticionEditar = new ModifyRequest(usuario.DistinguishedName, DirectoryAttributeOperation.Replace, "sn", new string[] { userAD.Surname });
                    resultadorEditar = ldapConnection.SendRequest(peticionEditar);
                }


                if (!string.IsNullOrEmpty(userAD.GivenName))
                {
                    peticionEditar = new ModifyRequest(usuario.DistinguishedName, DirectoryAttributeOperation.Replace, "givenName", new string[] { userAD.GivenName });
                    resultadorEditar = ldapConnection.SendRequest(peticionEditar);
                }

                if (!string.IsNullOrEmpty(userAD.Name) || !string.IsNullOrEmpty(userAD.DisplayName))
                {
                    peticionEditar = new ModifyRequest(usuario.DistinguishedName, DirectoryAttributeOperation.Replace, "displayName", new string[] { userAD.Name });
                    resultadorEditar = ldapConnection.SendRequest(peticionEditar);
                }

                if (!string.IsNullOrEmpty(userAD.Mail))
                {
                    peticionEditar = new ModifyRequest(usuario.DistinguishedName, DirectoryAttributeOperation.Replace, "mail", new string[] { userAD.Mail });
                    resultadorEditar = ldapConnection.SendRequest(peticionEditar);

                }

                if (!string.IsNullOrEmpty(userAD.Description))
                {
                    peticionEditar = new ModifyRequest(usuario.DistinguishedName, DirectoryAttributeOperation.Replace, "description", new string[] { userAD.Description });
                    resultadorEditar = ldapConnection.SendRequest(peticionEditar);
                }

                if (userAD.Enabled != null)
                {
                    var valorActivo = codeActive.ToString();
                    if ((bool)!userAD.Enabled)
                    {
                        valorActivo = (codeActive + 2).ToString();
                    }
                    try
                    {
                        peticionEditar = new ModifyRequest(usuario.DistinguishedName, DirectoryAttributeOperation.Replace, "userAccountControl", new string[] { valorActivo });
                        resultadorEditar = ldapConnection.SendRequest(peticionEditar);
                    }
                    catch (Exception ex)
                    {
                        Console.WriteLine(ex.Message);
                    }
                }

                userAD.DisplayName = userAD.GivenName + " " + userAD.Surname;

                return new ResponseDataDto { codigoRespuesta = (int)InfoCode.Exitoso, error = false, mensaje = "El usuario editado correctamente ", data = userAD };
            }
            catch (Exception e)
            {
                _logger.LogError("Algunos datos no se editaron correctamente " + e.Message);
                throw new LdapApplicationException("Algunos datos no se editaron correctamente: " + e.Message);
            }

        }

        /// <summary>
        /// Metodo que lista todos los usuarios de una unidad organizativa
        /// </summary>
        public ResponseDataDto listarUsuarios(UserEntradaDto userEntradaDto)
        {
            try
            {
                //
                List<SearchResponse> result = new List<SearchResponse>();
                SearchResponse response = null;
                int maxResultsToRequest = 100;

                PageResultRequestControl pageRequestControl = new PageResultRequestControl(maxResultsToRequest);

                // used to retrieve the cookie to send for the subsequent request
                string filter = $"(|(sAMAccountName=*{userEntradaDto.accountName}*))";
                if (!string.IsNullOrEmpty(userEntradaDto.email))
                {
                    filter = $"(&(objectClass=user)(sAMAccountName=*{userEntradaDto.accountName}*)(mail=*{userEntradaDto.email}*))";
                }
                if (!string.IsNullOrEmpty(userEntradaDto.lastName))
                {
                    filter = $"(&(objectClass=user)(sAMAccountName=*{userEntradaDto.accountName}*)(sn=*{userEntradaDto.lastName}*))";
                }
                if (!string.IsNullOrEmpty(userEntradaDto.firstName))
                {
                    filter = $"(&(objectClass=user)(sAMAccountName=*{userEntradaDto.accountName}*)(givenName=*{userEntradaDto.firstName}*))";
                }
                if (!string.IsNullOrEmpty(userEntradaDto.firstName) && !string.IsNullOrEmpty(userEntradaDto.lastName))
                {
                    filter = $"(&(objectClass=user)(sAMAccountName=*{userEntradaDto.accountName}*)(givenName=*{userEntradaDto.firstName}*)(sn=*{userEntradaDto.lastName}*))";
                }
                if (!string.IsNullOrEmpty(userEntradaDto.firstName) && !string.IsNullOrEmpty(userEntradaDto.lastName) && !string.IsNullOrEmpty(userEntradaDto.email))
                {
                    filter = $"(&(objectClass=user)(sAMAccountName=*{userEntradaDto.accountName}*)(givenName=*{userEntradaDto.firstName}*)(sn=*{userEntradaDto.lastName}*)(mail=*{userEntradaDto.email}*))";
                }

                string ldapFilter = $"(&(objectClass=user)(sAMAccountName=*{userEntradaDto.accountName}*)(givenName=*{userEntradaDto.firstName}*)(sn=*{userEntradaDto.lastName}*)(mail=*{userEntradaDto.email}*))";


                search(filter, result, USUARIOS_INTERNOS);
                search(filter, result, USUARIOS_EXTERNOS);

                //
                List<UserADDto> usuarios = new List<UserADDto>();
                List<UserADDto> usuariosAuxiliar = new List<UserADDto>();
                List<UserADDto> usuariosBusqueda = new List<UserADDto>();

                foreach (var itemDA in result)
                {
                    var resultado = itemDA;
                    if (resultado.Entries.Count > 0)
                    {
                        for (int i = 0; i < resultado.Entries.Count; i++)
                        {
                            var usuario = resultado.Entries[i];

                            UserADDto userADDto = new UserADDto();
                            userADDto.Name = usuario.Attributes["displayname"] == null ? string.Empty : usuario.Attributes["displayname"][0] as string;
                            userADDto.GivenName = usuario.Attributes["givenName"] == null ? string.Empty : usuario.Attributes["givenName"][0] as string;
                            userADDto.Mail = usuario.Attributes["mail"] == null ? string.Empty : usuario.Attributes["mail"][0] as string;
                            userADDto.DistinguishedName = usuario.DistinguishedName;
                            userADDto.Description = usuario.Attributes["description"] == null ? string.Empty : usuario.Attributes["description"][0] as string;
                            userADDto.Surname = usuario.Attributes["sn"] == null ? string.Empty : usuario.Attributes["sn"][0] as string;
                            userADDto.DisplayName = usuario.Attributes["name"] == null ? string.Empty : userADDto.GivenName + " " + userADDto.Surname;
                            userADDto.SamAccountName = usuario.Attributes["samaccountname"] == null ? string.Empty : usuario.Attributes["samaccountname"][0] as string;
                            userADDto.Enabled = false;

                            if (userADDto.SamAccountName != null && userADDto.SamAccountName.Length > 0)
                            {
                                if (usuario.Attributes["userAccountControl"] != null)
                                {
                                    var userAccountControl = usuario.Attributes["userAccountControl"][0] as string;
                                    if (!string.IsNullOrEmpty(userAccountControl))
                                    {
                                        if (Convert.ToDouble(userAccountControl) % 8 == 0)
                                        {
                                            userADDto.Enabled = true;
                                        }
                                    }
                                }

                                usuarios.Add(userADDto);
                            }
                        }

                    }
                }
                bool bTodos = true;
                if (!string.IsNullOrEmpty(userEntradaDto.accountName))
                {
                    usuariosAuxiliar = usuarios.Where(p => p.SamAccountName.ToLower().Contains(userEntradaDto.accountName.ToLower())).ToList();

                    foreach (var item in usuariosAuxiliar)
                    {
                        usuariosBusqueda.Add(item);
                    }
                    bTodos = false;
                }
                if (!string.IsNullOrEmpty(userEntradaDto.firstName))
                {
                    usuariosAuxiliar = usuarios.Where(p => p.GivenName.ToLower().Contains(userEntradaDto.firstName.ToLower())).ToList();

                    foreach (var item in usuariosAuxiliar)
                    {
                        if (usuariosBusqueda.Find(p => p.SamAccountName == item.SamAccountName) == null)
                        {
                            usuariosBusqueda.Add(item);
                        }
                    }
                    bTodos = false;
                }
                if (!string.IsNullOrEmpty(userEntradaDto.lastName))
                {
                    usuariosAuxiliar = usuarios.Where(p => p.Surname.ToLower().Contains(userEntradaDto.lastName.ToLower())).ToList();

                    foreach (var item in usuariosAuxiliar)
                    {
                        if (usuariosBusqueda.Find(p => p.SamAccountName == item.SamAccountName) == null)
                        {
                            usuariosBusqueda.Add(item);
                        }
                    }
                    bTodos = false;
                }
                if (!string.IsNullOrEmpty(userEntradaDto.email))
                {
                    usuariosAuxiliar = usuarios.Where(p => p.Mail.ToLower().Contains(userEntradaDto.email.ToLower())).ToList();

                    foreach (var item in usuariosAuxiliar)
                    {
                        if (usuariosBusqueda.Find(p => p.SamAccountName == item.SamAccountName) == null)
                        {
                            usuariosBusqueda.Add(item);
                        }
                    }
                    bTodos = false;
                }
                if (bTodos == true)
                {
                    usuariosBusqueda = usuarios;
                }
                if (!string.IsNullOrEmpty(userEntradaDto.enabled))
                {
                    bool status = false;
                    if (userEntradaDto.enabled == "true")
                    {
                        status = true;
                    }
                    usuariosBusqueda = usuariosBusqueda.Where(p => p.Enabled == status).ToList();
                }

                return new ResponseDataDto { codigoRespuesta = (int)(usuariosBusqueda.Count > 0 ? InfoCode.Exitoso : InfoCode.NoSeEncontraronRegistros), error = false, mensaje = "Listados de usuarios", data = usuariosBusqueda };
            }
            catch (Exception e)
            {
                _logger.LogError("El listados de usuarios no es valido " + e.Message);
                throw new LdapApplicationException("El listados de usuarios no es valido: " + e.Message);
            }

        }

        private void search(string filter, List<SearchResponse> result, string ou)
        {
            SearchResponse response = null;
            int maxResultsToRequest = 100;
            PageResultRequestControl pageRequestControl = new PageResultRequestControl(maxResultsToRequest);
            PageResultResponseControl pageResponseControl;
            SearchRequest searchRequest = new SearchRequest(
                        "ou="+ou+","+config.Dn,
                        filter,
                        SearchScope.Subtree);
            searchRequest.Controls.Add(pageRequestControl);


            while (true)
            {
                response = (SearchResponse)ldapConnection.SendRequest(searchRequest);
                result.Add(response);
                pageResponseControl = (PageResultResponseControl)response.Controls[0];
                if (pageResponseControl.Cookie.Length == 0)
                    break;

                pageRequestControl.Cookie = pageResponseControl.Cookie;
            }
        } 

        /// <summary>
        /// Metodo que valida si existe un usuario
        /// </summary>
        public ResponseDataDto validarExisteUsuario(string cuentaUsuario)
        {
            try
            {
                var usuario = buscarUsuarioInternoExterno(cuentaUsuario);
                var existeCuenta = usuario != null;
                return new ResponseDataDto { codigoRespuesta = existeCuenta ? (int)InfoCode.Exitoso : (int)InfoCode.UsuarioNoExiste, error = false, mensaje = existeCuenta ? "Cuenta de usuario si existe" : "Cuenta de usuario no existe", data = existeCuenta };
            }
            catch (Exception e)
            {
                _logger.LogError("El usuario no es valido " + e.Message);
                throw new LdapApplicationException("El usuario no es valido: " + e.Message);
            }
        }

        /// <summary>
        /// Metodo que valida si existe un usuario y la constraseña es valida
        /// </summary>
        public ResponseDataDto validarUsuario(string cuentaUsuario, string password)
        {
            try
            {
                var usuario = buscarUsuarioInternoExterno(cuentaUsuario);
                UserADDto userADDto = new UserADDto();
                var esValido = false;
                if (usuario != null)
                {
                    if (usuario.Attributes["userAccountControl"] != null)
                    {
                        var userAccountControl = usuario.Attributes["userAccountControl"][0] as string;

                        if (!string.IsNullOrEmpty(userAccountControl))
                        {
                            userADDto.Enabled = false;
                            if (Convert.ToDouble(userAccountControl) % 8 == 0)
                            {
                                userADDto.Enabled = true;
                            }
                            else
                            {
                                return new ResponseDataDto { codigoRespuesta = (int)InfoCode.UsuarioInactivo, error = false, mensaje = "Usuario Inactivo ", data = Enum.GetName(typeof(InfoCode), 2) };
                            }
                        }
                    }

                    if (usuario.Attributes["userPassword"] != null)
                    {
                        userADDto.Password = usuario.Attributes["userPassword"][0] as string;
                        if (password.Equals(userADDto.Password))
                        {
                            esValido = true;
                            return new ResponseDataDto { codigoRespuesta = (int)InfoCode.Exitoso, error = false, mensaje = esValido ? "El usuario es valido " : "El usuario no es valido ", data = Enum.GetName(typeof(InfoCode), 0) };
                        }
                    }

                    try
                    {
                        var servidor2 = new LdapDirectoryIdentifier(
                                                        this.config.Path,
                                                        int.Parse(this.config.Port),
                                                        fullyQualifiedDnsHostName: true,
                                                        connectionless: false);

                        var credencial = new NetworkCredential(
                        cuentaUsuario + config.UserLogonDomain,
                        password);

                        var ldapConnection2 = new LdapConnection(servidor2, credencial)
                        {
                            AuthType = AuthType.Basic
                        };
                        ldapConnection2.AutoBind = true;
                        ldapConnection2.Bind(credencial);
                        esValido = true;
                    }
                    catch
                    {
                        userADDto.Password = usuario.Attributes["userPassword"] == null ? string.Empty : usuario.Attributes["userPassword"][0] as string;
                        if (password.Equals(userADDto.Password))
                        {
                            esValido = true;
                        }
                        else
                        {
                            return new ResponseDataDto { codigoRespuesta = (int)InfoCode.PasswordInvalido, error = false, mensaje = "Contraseña invalida ", data = Enum.GetName(typeof(InfoCode), 3) };
                        }
                    }

                }
                else
                {
                    return new ResponseDataDto { codigoRespuesta = (int)InfoCode.UsuarioNoExiste, error = false, mensaje = "El usuario no existe ", data = Enum.GetName(typeof(InfoCode), 1) };
                }
                return new ResponseDataDto { codigoRespuesta = (int)InfoCode.Exitoso, error = false, mensaje = esValido ? "El usuario es valido " : "El usuario no es valido ", data = Enum.GetName(typeof(InfoCode), 0) };

            }
            catch (Exception e)
            {
                _logger.LogError("El usuario no es valido " + e.Message);
                throw new LdapApplicationException("El usuario no es valido: " + e.Message);
            }

        }

        /// <summary>
        /// Metodo que edita la contraseña
        /// </summary>
        public ResponseDataDto editarPassword(string cuentaUsuario, string password)
        {
            try
            {
                DirectoryRequest peticionEditar = new ModifyRequest();
                DirectoryResponse resultadorEditar;

                var usuario = buscarUsuarioInternoExterno(cuentaUsuario);

                if (usuario == null)
                {
                    return new ResponseDataDto { codigoRespuesta = (int)InfoCode.UsuarioNoExiste, error = false, mensaje = "El usuario no existe ", data = Enum.GetName(typeof(InfoCode), 1) };
                }

                peticionEditar = new ModifyRequest(usuario.DistinguishedName, DirectoryAttributeOperation.Replace, "userPassword", new string[] { password });
                resultadorEditar = ldapConnection.SendRequest(peticionEditar);

                return new ResponseDataDto { codigoRespuesta = (int)InfoCode.Exitoso, error = false, mensaje = "Contraseña modificada correctamente ", data = Enum.GetName(typeof(InfoCode), 0) };
            }
            catch (Exception e)
            {
                _logger.LogError("Error al modificar contraseña" + e.Message);
                throw new LdapApplicationException("Error al modificar contraseña: " + e.Message);
            }


        }

        /// <summary>
        /// Metodo que crea los datos de un usuario incluyendo la contraseña
        /// </summary>
        public ResponseDataDto crearUsuario(UserADDto userAD)
        {
            try
            {
                DirectoryRequest peticionEditar = new ModifyRequest();

                string dirClasstype = "user";
                string nameFull = userAD.Name + " " + userAD.GivenName;
                string ou = config.Ou;
                if (userAD.DistinguishedName.Split(",").Count() > 0)
                {
                    ou = userAD.DistinguishedName.Split(",")[1].Contains(INTERNOS) ? "ou=" + USUARIOS_INTERNOS : "ou=" + USUARIOS_EXTERNOS;
                }
                string userDN = string.Format("CN={0},{1}", userAD.SamAccountName, ou + "," + config.Dn);
                AddRequest addNewUserRequest = new AddRequest(userDN, dirClasstype);
                AddResponse response = (AddResponse)this.ldapConnection.SendRequest(addNewUserRequest);

                if (response.ResultCode == ResultCode.Success)
                {

                    DirectoryAttributeModification attFullName = new DirectoryAttributeModification();
                    attFullName.Name = "givenName";
                    attFullName.Operation = DirectoryAttributeOperation.Add;
                    attFullName.Add(userAD.GivenName);

                    DirectoryAttributeModification attSurname = new DirectoryAttributeModification();
                    attSurname.Name = "sn";
                    attSurname.Operation = DirectoryAttributeOperation.Add;
                    attSurname.Add(userAD.Surname);

                    DirectoryAttributeModification attDisplayname = new DirectoryAttributeModification();
                    attDisplayname.Name = "displayname";
                    attDisplayname.Operation = DirectoryAttributeOperation.Add;
                    attDisplayname.Add(userAD.DisplayName);

                    DirectoryAttributeModification attDescription = new DirectoryAttributeModification();
                    attDescription.Name = "description";
                    attDescription.Operation = DirectoryAttributeOperation.Add;
                    attDescription.Add(userAD.Description);

                    DirectoryAttributeModification attMail = new DirectoryAttributeModification();
                    attMail.Name = "mail";
                    attMail.Operation = DirectoryAttributeOperation.Add;
                    attMail.Add(userAD.Mail);

                    DirectoryAttributeModification attLogonName = new DirectoryAttributeModification();
                    attLogonName.Name = "samaccountname";
                    attLogonName.Operation = DirectoryAttributeOperation.Replace;
                    attLogonName.Add(userAD.SamAccountName);

                    DirectoryAttributeModification attPassword = new DirectoryAttributeModification();
                    attDescription.Name = "userPassword";
                    attDescription.Operation = DirectoryAttributeOperation.Add;
                    attDescription.Add(userAD.Password);

                    DirectoryAttributeModification attSn = new DirectoryAttributeModification();
                    attSn.Name = "givenName";
                    attSn.Operation = DirectoryAttributeOperation.Add;
                    attSn.Add(userAD.GivenName);

                    userAD.DistinguishedName = userDN;

                    ModifyRequest modRequest = new ModifyRequest(userDN, attFullName, attSurname, attDisplayname, attDescription, attMail, attLogonName);
                    ModifyResponse modResponse = (ModifyResponse)this.ldapConnection.SendRequest(modRequest);

                    if (modResponse.ResultCode == ResultCode.Success)
                    {
                        var responsePassword = this.editarPassword(userAD.SamAccountName, userAD.Password);
                        userAD.Enabled = true;
                        var responseEditar = this.editarUsuario(userAD.SamAccountName, userAD);
                    }

                }
                else
                {
                    return new ResponseDataDto { codigoRespuesta = (int)InfoCode.ErrorCrearUsuario, error = true, mensaje = "El usuario no fue creado correctamente ", data = Enum.GetName(typeof(InfoCode), 5) };
                }

                return new ResponseDataDto { codigoRespuesta = (int)InfoCode.Exitoso, error = false, mensaje = "El usuario creado correctamente ", data = userAD };
            }
            catch (Exception e)
            {
                _logger.LogError("Algunos datos no se crearon correctamente " + e.Message);
                throw new LdapApplicationException("Algunos datos no se crearon correctamente: " + e.Message);
            }

        }
    }
}
