import { Component, OnInit, OnDestroy } from '@angular/core';
import { LoaderService } from './loader.service';

@Component({
  selector: 'app-spinner',
  templateUrl: './spinner.component.html',
  styleUrls: ['./spinner.component.css']
})
export class SpinnerComponent implements OnInit {

  private isDelayedRunning: boolean = false;

  constructor(private loaderService: LoaderService) {
  }

  ngOnInit() {
    this.loaderService.status.subscribe((val: boolean) => {
      this.isDelayedRunning = val;
    });
  }

  ngOnDestroy() {
    this.isDelayedRunning = false;
  }

}
