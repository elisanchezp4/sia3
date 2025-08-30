export class RolesAsociados {
    public aplicacion: string;
    public rol: string;
    public fechaVinculacion: string;
    public fechaDesVinculacion: string;
    public estadoVinculacion: string;
    constructor(aplicacion, rol, estadoVinculacion, fechaVinculacion, fechaDesVinculacion) {
        this.aplicacion = aplicacion;
        this.rol = rol;
        this.fechaVinculacion = fechaVinculacion;
        this.fechaDesVinculacion = fechaDesVinculacion;
        this.estadoVinculacion = estadoVinculacion;
    }
}