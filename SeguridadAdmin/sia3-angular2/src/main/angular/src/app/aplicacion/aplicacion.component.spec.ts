import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AplicacionComponent } from './aplicacion.component';
import { AplicacionService } from './aplicacion.service';
import {ConfirmationService, DataTableModule, MessagesModule, ConfirmDialogModule} from 'primeng/primeng';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import {CrearEditarAplicacionComponent} from './crear-editar-aplicacion/crear-editar-aplicacion.component';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {AplicacionRoutingModule} from './aplicacion-routing.module';
import {LoaderService} from '../spinner/loader.service';
import {MensajesService} from '../services/mensajes.service';

describe('AplicacionComponent', () => {
  let component: AplicacionComponent;
  let fixture: ComponentFixture<AplicacionComponent>;
  let aplicacionService: AplicacionService;
  let confirmationService: ConfirmationService;
  let loaderService: LoaderService;
  let mensajesService: MensajesService;

  const mockAplicaciones = [
    { fechaCreacion: '2022-07-24', ultimaModificacion: '2022-07-24'},
    { fechaCreacion: '2022-07-24', ultimaModificacion: '2022-07-24'},
  ];

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BrowserModule,
        FormsModule,
        HttpModule,
        AplicacionRoutingModule,
        DataTableModule,
        MessagesModule,
        ConfirmDialogModule],
      declarations: [AplicacionComponent, CrearEditarAplicacionComponent],
      providers: [AplicacionService, LoaderService, ConfirmationService, MensajesService],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AplicacionComponent);
    component = fixture.componentInstance;
    aplicacionService = TestBed.get(AplicacionService);
    confirmationService = TestBed.get(ConfirmationService);
    loaderService = TestBed.get(LoaderService);
    mensajesService = TestBed.get(MensajesService);

    spyOn(aplicacionService, 'consultar').and.returnValue(
      Observable.of({ status: 200, json: () => mockAplicaciones })
    );

    spyOn(confirmationService, 'confirm').and.callFake((options) => {
      options.accept(); // Simulate user accepting the confirmation dialog
    });

    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });
});
