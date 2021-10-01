import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
// eslint-disable-next-line @typescript-eslint/no-var-requires
const cookieParser = require('cookie-parser');
const express = require('express');
const session = require('express-session');
const FileStore = require('session-file-store')(session);
async function bootstrap() {
  const app: any = await NestFactory.create(AppModule);
  app.set('view engine', 'ejs');
  app.use(express.static('publico')); //servidor web estatico
  app.use(cookieParser('Me agradan los poliperros')); //secreto cookies
  app.use(
    //session
    session({
      name: 'server-session-id',
      secret: 'No sera de tomar un traguito',
      resave: true,
      saveUnitilized: true,
      cookie: { secure: false },
      store: new FileStore(),
    }),
  );
  await app.listen(3000);
}

bootstrap();
/*
abstract class Nombre {
  public nombrePropiedad?: string; //undefinied
  private apellidoPropiedad = 'Eguez';
  protected edad = 1; //number duck typing
  static comun = 10;
  propiedadPublica: string;

  constructor(
    propiedadPublicaParametro: string,
    public propiedadRapido: string, //transfomra una propiedad
  ) {
    this.propiedadPublica = propiedadPublicaParametro;
    this.propiedadRapido;
  }
  public funcionPuvlica(parametroString: string): void {
    //no es necesrio poner el void
    //no hay return
    //no se pone en propiedaddes y métodos, no es necesario
    //por defecto todos los métodos que no tengan un modificador de acceso, son públicos
  }
  private funcionPrivada(parametroString: string, parametroNumber?: number) {}

  protected funcionPulbica(): number{
    return 1;
  }

  static funcionEstatica(): string{
    return 'string';
  }
}
*/
/*
//VARIABLES PRIMITIVAS
//TIPOS DE VARIABLES
//mutables (reasignan -> = )
let variableUno = 1; //no usar var!!
let varaibleDos = 2;
variableUno = 3;
varaibleDos = 4;
//inmutables (noe se pueden reasignar X -> !=)
const variableTres = 5;
//variableTres = 2; //error

//VARIABLES PRIMITIVAS (Typescript)
//no es necesario declarar la variable con el tipo de dato
//string
const texto = ''; //""
//number
const numeroEntero = 1;
const numeroFlotante = 1.2;
const soyEstudiante = true; //false
const noDefinido = undefined; // más común que el null
const noHayNada = null;
const fecha: Date = new Date();

//DUCK TYPING
const textoDos = 'Adrian';
let cualquierCosa: any = 'Vicente';
cualquierCosa = 1;
cualquierCosa = true;
cualquierCosa = new Date();

//CLASES
class Usuario {
  constructor(public nombre: string, public apellido: string) {}
}
const usuario: Usuario = new Usuario('Adrian', 'Eguez');
usuario.nombre;
usuario.apellido;

//INTERFACES
//las interfaces pueden ser usadas como un tipo de datos
interface UsuarioInterface {
  nombre: string;
  apellido: string;
  edad?: number; // ? => Opcional //valor por defecto es undefined
}
//PUNTEROS REFERENCIAS

//PRIMITIVAS
let edadAntigua = 22;
let otraEdad = edadAntigua; //copio el valor
edadAntigua += 1; //23
otraEdad -= 1;

//OBJETO
const objetoEdad = {
  edad: 22,
};
const otraEdadObjeto = objetoEdad; //REFERENCIA
otraEdadObjeto.edad = otraEdadObjeto.edad + 1; //23
console.log(otraEdadObjeto.edad);
objetoEdad.edad; //23
console.log(otraEdadObjeto.edad);
objetoEdad.edad = objetoEdad.edad + 1; //24
otraEdad.edad; //24
const otraEdadObjetoClonado = { ...objetoEdad }; //clonación objetos
const arregloEjemplo = [1, 2, 3];
const arregloClonado = [...arregloEjemplo]; //Clonación arreglos

const arregloNumeros: number[] = [1, 2, 3, 4, 5];
function funcionConNombre() {}

const indice = arregloNumeros.findIndex(
  (
    numero: number, //funcion anonima flecha gorda
  ) => {
    const elValorEsIgualAtres: boolean = numero === 3;
    return elValorEsIgualAtres;
  },

  //function () {} --> funcion anonima
);

arregloNumeros[indice] = 6;
//agregar al final
arregloNumeros.push(6);
//agregar al principio
arregloNumeros.unshift(0);

//CONDICIONES -> Truty y falsy
//no necesariamente debe haber un booleano lo que ponga dentro de los condicionales
const numeroOrden = 0;
if (numeroOrden) {
  console.log('Truty'); //FALSY
} else {
  console.log('Falsy');
}
if (1) {
  console.log('Truty'); //TRUTY
} else {
  console.log('Falsy');
}
if (-1) {
  console.log('Truty'); //TRUTY
} else {
  console.log('Falsy');
}
//Si el string está vacio es falsy
//si el string esta lleno es truty
if (' ') {
  console.log('Falsy'); //TRUTY
} else {
  console.log('Truty');
}
//si el objeto es vacio -> falsy
// si el objeto tiene algo -> truty
if ({ a: 1 }) {
  console.log('Truty'); //TRUTY
} else {
  console.log('Falsy');
}
//si el arreglo es vacio -> falsy
//si el arreglo es nulo -> falsy
//si el arreglo es undefinied -> falsy
// si el arreglo tiene algo -> truty
if ([]) {
  console.log('Falsy');
} else {
  console.log('Truty');
}
if (null) {
  console.log('Falsy');
} else {
  console.log('Truty');
}
if (undefined) {
  console.log('Falsy');
} else {
  console.log('Truty');
}
*/
