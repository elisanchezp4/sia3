import { Rol } from './Rol';

export class Usuario {
    public logonName: string;
    public ubicacion: string;
    public tipo: string;
    public email: string;
    public ruta: string;
    public roles: Rol[];
    public nombres: string;
    public apellidos: string;
    public adressLocal: string;
    public portLocal: string;
    public usuarioId: number;
    public userId: number;
    public fechaVinculacion:string;
    public estadoVinculacion: string;
    constructor() {
        this.roles = [];
    }
}
