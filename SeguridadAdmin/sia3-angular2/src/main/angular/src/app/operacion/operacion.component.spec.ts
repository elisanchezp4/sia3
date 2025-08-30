import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { OperacionComponent } from './operacion.component';
import { OperacionService } from './operacion.service';
import { AplicacionService } from 'app/aplicacion/aplicacion.service';
import { ContextMenuService } from 'app/services/context-menu.service';
import {
  ConfirmationService, ConfirmDialogModule,
  ContextMenuModule,
  DataTableModule,
  DialogModule,
  GrowlModule, MessagesModule,
  TreeModule
} from 'primeng/primeng';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import {of} from 'rxjs/observable/of';
import {BrowserModule} from "@angular/platform-browser";
import {HttpModule} from "@angular/http";
import {OperacionRoutingModule} from "./operacion-routing.module";
import {LoaderService} from "../spinner/loader.service";

describe('OperacionComponent', () => {
  let component: OperacionComponent;
  let fixture: ComponentFixture<OperacionComponent>;

  beforeEach(async () => {
    console.log('Prueba cargada y lista para ejecutarse operacion.');

    await TestBed.configureTestingModule({
      imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        DataTableModule,
        TreeModule,
        ContextMenuModule,
        GrowlModule,
        DialogModule,
        MessagesModule,
        ConfirmDialogModule,
        OperacionRoutingModule,
        ReactiveFormsModule
      ],
      declarations: [OperacionComponent],
      providers: [
        OperacionService,
        AplicacionService,
        ConfirmationService,
        ContextMenuService,
        LoaderService,
      ],
      schemas: [NO_ERRORS_SCHEMA],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OperacionComponent);
    component = fixture.componentInstance;

    // Espía los métodos de los servicios
    spyOn(component['operacionService'], 'consultarOperaciones').and.returnValue(of({ status: 200, json: () => [] }));
    spyOn(component['aplicacionService'], 'consultarPorUsuario').and.returnValue(of({ status: 200, json: () => [] }));
    spyOn(component['confirmationService'], 'confirm').and.callFake(options => options.accept());
    spyOn(component['loaderService'], 'display');

    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should call loaderService.display(false) during ngOnInit', () => {
    component.ngOnInit();
    expect(component['loaderService'].display).toHaveBeenCalledWith(false);
  });
});
