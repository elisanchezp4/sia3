import { Pipe } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
//Esta clase se utiliza para convertir un string html a un codigo verdadero HTML
//para que el navegador lo entienda.
//juagomez@heinsohn.com.co
@Pipe({ name: 'safeHtml' })
export class SafeHtml {
    constructor(private sanitizer: DomSanitizer) { }

    transform(html) {
        return this.sanitizer.bypassSecurityTrustHtml(html);
    }
}