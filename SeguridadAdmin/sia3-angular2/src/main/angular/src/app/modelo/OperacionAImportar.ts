import { Operacion } from "./Operacion";

export interface OperacionAImportar{
    idAplicacion: number;
    operacionesList: Array<Operacion>;
}