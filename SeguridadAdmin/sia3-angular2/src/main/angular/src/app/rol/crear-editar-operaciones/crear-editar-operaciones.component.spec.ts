import { TestBed, ComponentFixture } from '@angular/core/testing';
import { CrearEditarOperacionesComponent } from './crear-editar-operaciones.component';
import { OperacionService } from 'app/operacion/operacion.service';
import {AplicacionService} from "../../aplicacion/aplicacion.service";
import {MensajesService} from "../../services/mensajes.service";
import {
  ConfirmationService,
  ConfirmDialogModule,
  DataTableModule, DialogModule,
  MessagesModule,
  TreeModule,
  TreeTableModule
} from "primeng/primeng";
import {RolService} from "../rol.service";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {RolRoutingModule} from "../rol-routing.module";
import {RolComponent} from "../rol.component";
import {CrearEditarRolComponent} from "../crear-editar-rol/crear-editar-rol.component";
import {LoaderService} from "../../spinner/loader.service";
describe('CrearEditarOperacionesComponent', () => {
  let component: CrearEditarOperacionesComponent;
  let fixture: ComponentFixture<CrearEditarOperacionesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        DataTableModule,
        TreeModule,
        MessagesModule,
        ConfirmDialogModule,
        RolRoutingModule,
        TreeTableModule,
        ReactiveFormsModule,
        DialogModule
      ],
      declarations: [RolComponent, CrearEditarRolComponent, CrearEditarOperacionesComponent],
      providers: [OperacionService, AplicacionService, MensajesService, ConfirmationService, RolService, LoaderService],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CrearEditarOperacionesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

