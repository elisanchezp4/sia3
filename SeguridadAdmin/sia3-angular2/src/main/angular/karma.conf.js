module.exports = function(config) {
  config.set({
    basePath: '',
    frameworks: ['jasmine', '@angular/cli'], // Agrega '@angular/cli' al array de frameworks
    plugins: [
      require('karma-jasmine'),
      require('karma-chrome-launcher'),
      require('@angular/cli/plugins/karma') // Agrega el plugin de Angular CLI
    ],
    client: {
      clearContext: false // Deja esto como false
    },
    files: [
      { pattern: './src/test.ts', watched: false } // Agrega el archivo test.ts
    ],
    preprocessors: {
      './src/test.ts': ['@angular/cli'], // Usa el preprocess @angular/cli
    },
    mime: {
      'text/x-typescript': ['ts', 'tsx'] // Agrega este mime type para los archivos .ts
    },
    coverageIstanbulReporter: {
      dir: require('path').join(__dirname, './coverage/angular'), // Verifica que el directorio de cobertura sea correcto
      reports: ['html', 'lcovonly'],
      fixWebpackSourcePaths: true
    },
    angularCli: {
      environment: 'dev'
    },
    reporters: config.angularCli && config.angularCli.codeCoverage
      ? ['progress', 'coverage-istanbul'] // Agrega 'coverage-istanbul' como reporter si tienes code coverage habilitado
      : ['progress'],
    port: 9876,
    colors: true,
    logLevel: config.LOG_INFO,
    autoWatch: true,
    browsers: ['Chrome'], // O utiliza otro navegador compatible con Karma
    singleRun: false,
    concurrency: Infinity
  });
};
