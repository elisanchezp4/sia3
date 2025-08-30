import { ComponentFixture, TestBed } from '@angular/core/testing';
import { InicioComponent } from './inicio.component';
import { LoaderService } from '../spinner/loader.service';

// Importa los módulos de PrimeNG necesarios
import { MessagesModule } from 'primeng/primeng';
import { ConfirmDialogModule } from 'primeng/primeng';
import { DataTableModule } from 'primeng/primeng';

describe('InicioComponent', () => {
  let component: InicioComponent;
  let fixture: ComponentFixture<InicioComponent>;
  let loaderService: LoaderService;

  beforeEach(async () => {
    console.log('Prueba cargada y lista para ejecutarse inicio.');
    await TestBed.configureTestingModule({
      declarations: [InicioComponent],
      providers: [LoaderService], // Agrega el servicio a los providers
      imports: [
        // Agrega los módulos de PrimeNG necesarios aquí
        MessagesModule,
        ConfirmDialogModule,
        DataTableModule
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InicioComponent);
    component = fixture.componentInstance;
    loaderService = TestBed.get(LoaderService); // Usa TestBed.get para obtener una instancia del servicio
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should call loaderService.display(false) during ngOnInit', () => {
    spyOn(loaderService, 'display'); // Crea un spy para el método display del servicio

    component.ngOnInit();

    expect(loaderService.display).toHaveBeenCalledWith(false); // Verifica que el método fue llamado con el valor false
  });
});
