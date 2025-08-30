export class Aplicacion {
    public aplicacionId: number;
    public nombre: string;
    public urlInicioExitoso: string;
    public estado: string;
    public nombreEstado: string;
    public aplicacionActiva: boolean = true;
    public minutosVigenciaCodigo: string;
    public minutosVigenciaToken: string;
    public minVigTokenActConstrasenia: string;
    public usuarioModificacion: number;
    public fechaCreacion: Date;
    public ultimaModificacion: string;
    public recibirNotificacion: number;
    constructor() {
    }
  }
