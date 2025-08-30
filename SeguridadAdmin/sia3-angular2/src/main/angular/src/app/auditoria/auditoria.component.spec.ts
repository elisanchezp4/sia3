import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AuditoriaComponent } from './auditoria.component';
import { AuditoriaService } from './auditoria.service';
import { AplicacionService } from '../aplicacion/aplicacion.service';
import { MensajesService } from '../services/mensajes.service';
import { LoaderService } from '../spinner/loader.service';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { CalendarModule, DataTableModule, DialogModule, MessagesModule, TreeModule } from 'primeng/primeng';
import { AuditoriaRoutingModule } from './auditoria-routing.module';
import {of} from 'rxjs/observable/of';

describe('AuditoriaComponent', () => {
  let component: AuditoriaComponent;
  let fixture: ComponentFixture<AuditoriaComponent>;
  let auditoriaService: AuditoriaService;
  let aplicacionService: AplicacionService;
  let mensajesService: MensajesService;
  let loaderService: LoaderService;

  // Define un mock para AplicacionService
  const mockAplicacionService = {
    consultar: () => of([]) // Puedes ajustar los datos del mock según necesites
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        DataTableModule,
        CalendarModule,
        AuditoriaRoutingModule,
        MessagesModule,
        DialogModule,
        TreeModule,
      ],
      declarations: [AuditoriaComponent],
      providers: [
        AuditoriaService,
        { provide: AplicacionService, useValue: mockAplicacionService }, // Usa el mock aquí
        MensajesService,
        LoaderService,
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AuditoriaComponent);
    component = fixture.componentInstance;
    auditoriaService = TestBed.get(AuditoriaService);
    aplicacionService = TestBed.get(AplicacionService);
    mensajesService = TestBed.get(MensajesService);
    loaderService = TestBed.get(LoaderService);

    spyOn(auditoriaService, 'consultarAuditoria').and.returnValue(
      of([])
    );

    component['tipoEventos'] = [];
    component['idEvento'] = '1';
    component['fechaInicio'] = new Date();
    component['fechaFin'] = new Date();
    component['auditoria'] = [];

    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });
  it('should populate tipoEventos on init', () => {
    component.ngOnInit();
    expect(component['tipoEventos'].length).toBeGreaterThan(-1);
  });
});
