import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ParametrosUnivalorComponent } from './univalor.component';
import { AplicacionService } from '../../aplicacion/aplicacion.service';
import { ConfirmationService, ConfirmDialogModule, DataTableModule, DialogModule, MessagesModule, TreeModule } from 'primeng/primeng';
import { ParametrosService } from '../parametros.service';
import { MensajesService } from '../../services/mensajes.service';
import { LoaderService } from '../../spinner/loader.service';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { ParametrosRoutingModule } from '../parametros-routing.module';
import { of } from "rxjs/observable/of";

describe('ParametrosUnivalorComponent', () => {
  let component: ParametrosUnivalorComponent;
  let fixture: ComponentFixture<ParametrosUnivalorComponent>;
  let parametrosService: ParametrosService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ParametrosUnivalorComponent],
      providers: [
        AplicacionService,
        ConfirmationService,
        ParametrosService,
        MensajesService,
        LoaderService,
      ],
      imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        DataTableModule,
        TreeModule,
        MessagesModule,
        ConfirmDialogModule,
        ParametrosRoutingModule,
        DialogModule
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ParametrosUnivalorComponent);
    component = fixture.componentInstance;
    parametrosService = TestBed.get(ParametrosService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call consultarAplicaciones and populate aplicaciones during ngOnInit', () => {
    const mockAplicaciones = [{ id: 1, nombre: 'App1' }, { id: 2, nombre: 'App2' }];
    spyOn(parametrosService, 'consultarAplicaciones').and.returnValue(of({ status: 200, json: () => mockAplicaciones }));
  });
});
