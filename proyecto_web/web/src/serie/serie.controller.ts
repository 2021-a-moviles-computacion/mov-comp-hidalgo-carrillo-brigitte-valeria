import { SerieService } from './serie.service';
import {
  BadRequestException,
  Body,
  Controller,
  Get,
  InternalServerErrorException,
  Param,
  Post,
  Query,
  Res,
} from '@nestjs/common';
import { SerieCrearDto } from '../dto/serie-crear.dto';
import { validate } from 'class-validator';
import { stringify } from 'querystring';

@Controller('serie')
export class SerieController {
  constructor(private serieService: SerieService) {}
  /*
  @Get(':idSerie')
  obtenerUno(@Param() parametrosRuta) {
    return this.serieService.buscarUno(+parametrosRuta.idSerie);
  }
*/

  @Post('eliminar-serie/:idSerie')
  async eliminarUsuario(@Res() response, @Param() parametrosRuta) {
    try {
      await this.serieService.eliminarUno(+parametrosRuta.idSerie);
      response.redirect('/serie/lista-serie' + '?mensaje=Se eliminó la serie');
    } catch (error) {
      console.error(error);
      throw new InternalServerErrorException('Error');
    }
  }

  @Post('vista-editar/:idSerie')
  async vistaEditar(@Res() response, @Param() parametrosRuta) {
    try {
      const serie = await this.serieService.buscarUno(+parametrosRuta.idSerie);

      response.render('serie/editar-serie', {
        datos: {
          serie: serie,
        },
      });
    } catch (error) {
      console.error(error);
      throw new InternalServerErrorException('Error');
    }
  }

  @Get('vista-crear')
  vistaCrear(@Res() response, @Query() queryParams) {
    response.render('serie/crear-serie', {
      datos: {
        mensaje: queryParams.mensaje,
      },
    });
  }

  @Post('editar-serie-formulario/:idSerie')
  async editarSerieFormulario(
    @Res() response,
    @Body() parametrosCuerpo,
    @Param() parametrosRuta,
  ) {
    const serieCrearDTO = new SerieCrearDto();
    serieCrearDTO.nombre_serie = parametrosCuerpo.nombre_serie;
    serieCrearDTO.nombre_director = parametrosCuerpo.nombre_director;
    serieCrearDTO.fecha_lanzamiento = parametrosCuerpo.fecha_lanzamiento;
    serieCrearDTO.tiene_emmy = !!parametrosCuerpo.tiene_emmy;
    serieCrearDTO.temporadas = +parametrosCuerpo.temporadas;
    try {
      const errores = await validate(serieCrearDTO);

      if (errores.length > 0) {
        console.log(JSON.stringify(errores));
        response.redirect(
          '/serie/lista-serie' +
            '?mensaje= Datos erróneos. Favor ingresar bien los datos',
        );
      } else {
        await this.serieService.actualizarUno({
          id: +parametrosRuta.idSerie,
          data: serieCrearDTO,
        });
        response.redirect(
          '/serie/lista-serie' +
            '?mensaje=Se editó la serie ' +
            parametrosCuerpo.nombre_serie,
        );
      }
    } catch (error) {
      console.error({ error: error, mensaje: 'Errores en editar serie' });
      throw new InternalServerErrorException('Error servidor');
    }
  }

  @Post('crear-serie-formulario')
  async crearSerieFormulario(@Res() response, @Body() parametrosCuerpo) {
    const serieCrearDTO = new SerieCrearDto();
    serieCrearDTO.nombre_serie = parametrosCuerpo.nombre_serie;
    serieCrearDTO.nombre_director = parametrosCuerpo.nombre_director;
    serieCrearDTO.fecha_lanzamiento = parametrosCuerpo.fecha_lanzamiento;
    serieCrearDTO.tiene_emmy = !!parametrosCuerpo.tiene_emmy;
    serieCrearDTO.temporadas = +parametrosCuerpo.temporadas;

    try {
      const errores = await validate(serieCrearDTO);

      if (errores.length > 0) {
        response.redirect(
          '/serie/vista-crear' +
            '?mensaje= Ingreso de datos erróneo. Favor ingresar bien los datos',
        );
        // @ts-ignore
        console.log(JSON, stringify(errores));
        throw new BadRequestException('No envia bien parametros: ');
      } else {
        await this.serieService.crearUno(serieCrearDTO);
        response.redirect(
          '/serie/vista-crear' +
            '?mensaje=Se creó la serie ' +
            parametrosCuerpo.nombre_serie,
        );
      }
    } catch (error) {
      console.error({ error: error, mensaje: 'Errores en crear usuario' });
      throw new InternalServerErrorException('Error servidor');
    }
  }

  @Get('lista-serie')
  async listaSerie(@Res() response, @Query() parametrosConsulta) {
    try {
      const respuesta = await this.serieService.buscarMuchos({
        skip: parametrosConsulta.skip ? +parametrosConsulta.skip : undefined,
        take: parametrosConsulta.take ? +parametrosConsulta.take : undefined,
        busqueda: parametrosConsulta.busqueda
          ? parametrosConsulta.busqueda
          : undefined,
      });
      response.render('serie/lista-serie', {
        datos: {
          serie: respuesta,
          mensaje: parametrosConsulta.mensaje,
        },
      });
    } catch (error) {
      throw new InternalServerErrorException('Error del servidor');
    }
  }

  @Post()
  async crearUno(@Body() parametrosCuerpo) {
    const serieCrearDTO = new SerieCrearDto();
    serieCrearDTO.nombre_serie = parametrosCuerpo.nombre_serie;
    serieCrearDTO.nombre_director = parametrosCuerpo.nombre_director;
    serieCrearDTO.fecha_lanzamiento = parametrosCuerpo.fecha_lanzamiento;
    serieCrearDTO.tiene_emmy = parametrosCuerpo.tiene_emmy;
    serieCrearDTO.temporadas = parametrosCuerpo.temporadas;

    try {
      const errores = await validate(serieCrearDTO);

      // @ts-ignore
      if (errores > 0) {
        // @ts-ignore
        console.log(JSON, stringify(errores));
        throw new BadRequestException('No envía bien parámetros');
      } else {
        return this.serieService.crearUno(serieCrearDTO);
      }
    } catch (error) {
      console.log({ error: error, mensaje: 'Errores en crear usuario' });
      throw new InternalServerErrorException('Error del servidor');
    }
  }
}
