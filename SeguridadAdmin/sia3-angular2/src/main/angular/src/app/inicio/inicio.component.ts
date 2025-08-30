import { Component, OnInit } from '@angular/core';
import { LoaderService } from '../spinner/loader.service';
@Component({
  selector: 'app-inicio',
  templateUrl: './inicio.component.html',
  styleUrls: ['./inicio.component.css']
})
export class InicioComponent implements OnInit {

  constructor(private loaderService: LoaderService) { }

  ngOnInit() {
    this.loaderService.display(false);
  }

}
