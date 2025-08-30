import {Tree, TreeNode, MenuItem, Message,  ContextMenu, DialogModule, Dialog} from 'primeng/primeng';
import { AplicacionService } from 'app/aplicacion/aplicacion.service';
import { OperacionService } from 'app/operacion/operacion.service';
import { Aplicacion } from 'app/modelo/Aplicacion';
import { RESPONSE_OK } from 'app/util/Constants';
export class arbolOperaciones {
private operacionService:OperacionService;
private aplicacionService:AplicacionService;
constructor() {}

    //listado de Aplicaciones
        private aplicaciones: Aplicacion[] = [];
        arbolOperaciones: TreeNode[];    

/* Permite convertir el json de operaciones a un arreglo de tipo TreeNode */

/**Permite consultar el árbol de operaciones */
    // public   consultarOperaciones(){ 


    //   this.aplicacionService.consultar().subscribe(
    //   data => {
    //       if (data.status == RESPONSE_OK) {
    //           this.aplicaciones = data.json(); 
    //       }
    //   },
    //   error => {
    //      return error.json();
    //   });  

          
          

    //  this.operacionService.consultarOperaciones().subscribe(
    //   data => {
    //       if (data.status == RESPONSE_OK) {
    //            this.arbolOperaciones = this.getTree(data.json());  
    //       }
    //   },
    //   error => {
    //      return error.json();
        
    //   }); 


    //   return this.arbolOperaciones;
    // }


// getTree(json): TreeNode[] { 
     
//    this.aplicaciones.forEach(element => {  
//         delete  element['minutosVigenciaCodigo'];
//         delete  element['minutosVigenciaToken'];        
//         delete  element['ultimaModificacion'];
//         delete  element['urlInicioExitoso'];
//         delete  element['urlInicioFallido'];
//         delete  element['usuarioModificacion'];
//         delete  element['descripcion'];
//         element['nombreObjeto'] = element.nombre;
//         element['tipo'] = "APLICACION"
        
//   });
 
     
   /** se remueven los items duplicados del arreglo de operaciones */
//    var seenNames = {};
//    let array = json.filter(function(currentObject) {
//       if (currentObject.name in seenNames) {
//           return false;
//       } else {
//           seenNames[currentObject.name] = true;
//           return true;
//       }

//     });
   
//     this.aplicaciones.forEach(element => {      
//          element['listadoOperaciones'] =  json.filter(operacion => operacion.aplicacionId == element.aplicacionId);
          
//      }); 

     
//     this.aplicaciones.forEach(element => {      
//           this.crearNodo(element);  
//      }); 
    
//    console.log(this.aplicaciones)
//    return this.aplicaciones; 
// }


// /* Permite convertir un nodo de operación a un nodo de tipo TreeNode*/
// private crearNodo(item){  
 
//   delete  item['aplicaciones'];
//   delete  item['fechaCreacion'];
//   delete  item['ultimaModificacion'];
//   delete  item['opcionPadre'];
//   delete  item['parent'];
//   delete  item['usuarioModificacion']; 
//     item['label'] = item.nombreObjeto; 
//     if(item.listadoOperaciones != null){ 
//          this.crearIcono(item);
//         if(item.listadoOperaciones.length > 0){
//           item['children'] = []; 
//             item.listadoOperaciones.forEach(element => {                
//               item.children.push(element);              
//                 this.crearIcono(item);  
//                 this.crearNodo(element); 
//             }); 
//         }
//     }else{ 
//        this.crearIcono(item);  
//     }  
//  }

/** Permite crear un ícono dependiendo del tipo de operación */
private crearIcono(item){

    switch (item.tipo) {
         case "APLICACION":
            item['data'] = "APLICACION"; 
            item['expandedIcon'] = "fa-folder-open";
            item['collapsedIcon'] = "fa-folder"; 
         break;
         case "MENU":
            item['data'] = "MENU"; 
            item['expandedIcon'] = "fa fa-caret-square-o-down";
            item['collapsedIcon'] = "fa fa-caret-square-o-right";     
         break;
         case "SUBMENU":
            item['data'] = "SUBMENU"; 
            item['expandedIcon'] = "fa fa-chevron-down";
            item['collapsedIcon'] = "fa fa-chevron-right";
        break;    
        case "VINCULO":
            item['data'] = "VINCULO"; 
            item['expandedIcon'] = "fa fa-long-arrow-down";
            item['collapsedIcon'] = "fa fa-long-arrow-right";  
         break;
         case "BOTON":
            item['data'] = "BOTON";  
            item['icon'] = "fa fa-dot-circle-o";
         break;    
        default:
            item['data'] = "SINTIPO";  
            item['icon'] = "fa fa-file-o";
            break;
    }



    }

}