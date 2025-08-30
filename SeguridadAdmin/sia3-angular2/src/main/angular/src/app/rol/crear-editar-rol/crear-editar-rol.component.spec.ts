import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { CrearEditarRolComponent } from './crear-editar-rol.component';
import { AplicacionService } from 'app/aplicacion/aplicacion.service';
import { RolService } from 'app/rol/rol.service';
import { LoaderService } from '../../spinner/loader.service';
import {
  ConfirmationService,
  ConfirmDialogModule,
  DataTableModule, DialogModule,
  MessagesModule,
  TreeModule,
  TreeTableModule
} from 'primeng/primeng';
import { MensajesService } from 'app/services/mensajes.service';
import { Observable } from 'rxjs/Observable';
import { Message } from 'primeng/primeng';
import { Rol } from 'app/modelo/Rol';
import { Aplicacion } from 'app/modelo/Aplicacion';
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {RolRoutingModule} from "../rol-routing.module";
import {RolComponent} from "../rol.component";
import {CrearEditarOperacionesComponent} from "../crear-editar-operaciones/crear-editar-operaciones.component";

describe('CrearEditarRolComponent', () => {
  let component: CrearEditarRolComponent;
  let fixture: ComponentFixture<CrearEditarRolComponent>;

  // Mock services
  const mockAplicacionService = {
    consultarCatalogo: () => {
      return Observable.of({ status: 'ok', json: () => [{ /* estado data here */ }] });
    },
    consultarPorUsuario: () => {
      return Observable.of({ status: 'ok', json: () => [{ /* aplicaciones data here */ }] });
    }
  };

  const mockRolService = {
    guardar: () => {
      return Observable.of({ status: 'ok', json: () => ({}) });
    },
    modificar: () => {
      return Observable.of({ status: 'ok', json: () => ({}) });
    }
  };

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
      declarations: [RolComponent, CrearEditarOperacionesComponent, CrearEditarRolComponent],
      providers: [
        LoaderService,
        ConfirmationService,
        MensajesService,
        { provide: AplicacionService, useValue: mockAplicacionService },
        { provide: RolService, useValue: mockRolService }
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CrearEditarRolComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should populate estados on ngOnInit', () => {
    spyOn(mockAplicacionService, 'consultarCatalogo').
      and.returnValue(Observable.of({ status: 'ok', json: () => [{ /* estado data here */ }] }));
    component.ngOnInit();
  });

  it('should set campos and validate on guardar', () => {
    component['aplicacionId'] = '1'; // Set a valid aplicacionId
    component['rol'] = new Rol(); // Accedemos a propiedad privada para crear una instancia
    component['rol'].nombre = 'Test Rol';
    component['rol'].estado = '1';
    component['rol'].path = 'test/path';

    const guardarSpy = spyOn(component, 'guardar').and.callThrough();
    spyOn(mockRolService, 'guardar').and.returnValue(Observable.of({ status: 'ok', json: () => ({}) }));
  });
});
