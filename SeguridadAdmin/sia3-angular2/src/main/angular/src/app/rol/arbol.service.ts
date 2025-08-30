import { Injectable } from '@angular/core';
import {TreeNode} from "primeng/primeng"; 
import { Headers, Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import { Observable }     from 'rxjs/Observable';
import 'rxjs/add/operator/map';


@Injectable()
export class NodeService {

getFiles(): TreeNode[] {
    return [
        {
            "label": "SIGCE",
            "data": "Documents Folder",
            "expandedIcon": "fa-folder-open",
            "collapsedIcon": "fa-folder",
            "children": [{
                "label": "PMI",
                "data": "Work Folder",
                "expandedIcon": "fa-folder-open",
                "collapsedIcon": "fa-folder",
                "children": [{"label": "Mejora tus resultados", "icon": "fa-file-code-o", "data": "Expenses Document"}, {"label": "Seguimiento PMI", "icon": "fa-file-code-o", "data": "Resume Document"}]
            }, 
                {
                    "label": "PAM",
                    "data": "Home Folder",
                    "expandedIcon": "fa-folder-open",
                    "collapsedIcon": "fa-folder",
                    "children": [{"label": "Operación", "icon": "fa-file-code-o", "data": "Invoices for this month"}]
                }]
        },
        {
            "label": "SIGAA",
            "data": "Pictures Folder",
            "expandedIcon": "fa-folder-open",
            "collapsedIcon": "fa-folder",
            "children": [
                {"label": "Operación", "icon": "fa-file-code-o", "data": "Barcelona Photo"},
                {"label": "Operación", "icon": "fa-file-code-o", "data": "PrimeFaces Logo"},
                {"label": "Operación", "icon": "fa-file-code-o", "data": "PrimeUI Logo"}]
        },
        {
            "label": "RIEL",
            "data": "Home Folder",
            "expandedIcon": "fa-folder-open",
            "collapsedIcon": "fa-folder",
            "children": [
                {"label": "Operación", "icon": "fa-file-code-o", "data": "Barcelona Photo"},
                {"label": "Operación", "icon": "fa-file-code-o", "data": "PrimeFaces Logo"},
                {"label": "Operación", "icon": "fa-file-code-o", "data": "PrimeUI Logo"}
        ]
        }


    ];
}
}