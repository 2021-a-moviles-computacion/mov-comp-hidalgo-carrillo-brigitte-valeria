import {
  BadRequestException,
  Body,
  Controller,
  Get,
  Header,
  Headers,
  HttpCode,
  InternalServerErrorException,
  Param,
  Post,
  Query,
  Req,
  Res,
} from '@nestjs/common';
import { AppService } from './app.service';

@Controller()
export class AppController {
  constructor(private readonly appService: AppService) {}

  @Get() //método http //implicito al slahs raiz
  getHello(): string {
    return this.appService.getHello();
  }

  @Get('texto')
  @HttpCode(200)
  holaTexto(): string {
    return 'Hola texto';
  }
  @Get('html')
  @HttpCode(201)
  holaHTML(): string {
    return '<h1>HOla HTML</h1>';
  }
  @Get('json')
  @HttpCode(202)
  holaJSON(): string {
    return '{mensaje: "Hola json"}';
  }

  //erro bad request
  //mandar errores al cliente
  @Get('internal-error')
  internalError() {
    throw new InternalServerErrorException();
  }

  @Get('bad-request')
  badRequest() {
    throw new BadRequestException();
  }

  @Get('setear-cookie-insegura') //cuando el lciente introduzca esto
  setearCookieInsegura(
    @Req() req, //request peticion del usuario. Obtener el objeto de peticion
    @Res() res, //resopnse peticion respuesta. Obtener el objeto de respuestas
  ) {
    res.cookie(
      //responde el servidor con una cookies segura e insegura
      'galletaInsegura', //nombre
      'Tengo hambre', //valor
    );
    res.cookie('galletaSegura', 'web', {
      //para que sea segura solo hay que añadir
      secure: true, //..esto
      signed: true,
    });
    res.send('ok');
  }

  @Get('mostrar-cookies')
  mostrarCookies(@Req() req) {
    const mensaje = {
      sinFirmar: req.cookies,
      firmadas: req.signedCookies,
    };
    return mensaje;
  }

  @Get('parametros-consulta/:nombre/:apellido')
  @HttpCode(200)
  @Header('Cache-Control', 'none') //cabeceras de respuesta
  @Header('EPN', 'SISTEMAS') //cabeceras de respuesta
  parametrosConsulta(
    @Query() queryParams, //Obtener parametros de consulta
    @Param() params,
  ) {
    return {
      parametrosConsulta: queryParams,
      parametrosRuta: params,
    };
  }

  @Post('parametros-cuerpo') //por defecto vota el 201. Pero este significa creado. Asiq ue no usar este
  //porque no estamos creando
  @HttpCode(200)
  parametrosCuerpo(@Body() bodyParams, @Headers() cabecerasPeticion) {
    return {
      parametrosCuerpo: bodyParams,
      cabeceras: cabecerasPeticion,
    };
  }
}
