import { ModuloSeguridadAngular2Page } from './app.po';

describe('modulo-seguridad-angular2 App', function() {
  let page: ModuloSeguridadAngular2Page;

  beforeEach(() => {
    page = new ModuloSeguridadAngular2Page();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
