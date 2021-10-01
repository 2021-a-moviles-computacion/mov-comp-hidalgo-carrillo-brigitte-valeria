import { Injectable } from '@nestjs/common';
import { PrismaService } from '../prisma.service';
import { Prisma } from '@prisma/client';

@Injectable()
export class SerieService {
  constructor(private prisma: PrismaService) {}

  /*
  buscarUno(id?: number) {
    return this.prisma.serie.findUnique({
      where: {
        id: id,
      },
    });
  }
*/

  buscarUno(id: number) {
    return this.prisma.serie.findUnique({
      where: {
        id: id,
      },
    });
  }

  buscarMuchos(parametrosBusqueda: {
    skip?: number; // registros que te saltes
    take?: number; // registros tomas 1
    busqueda?: string;
  }) {
    const or = parametrosBusqueda.busqueda
      ? {
          OR: [
            { nombre_serie: { contains: parametrosBusqueda.busqueda } },
            { nombre_director: { contains: parametrosBusqueda.busqueda } },
          ],
        }
      : {};
    console.log(or);
    return this.prisma.serie.findMany({
      where: or,
      take: Number(parametrosBusqueda.take) || undefined,
      skip: Number(parametrosBusqueda.skip) || undefined,
    });
  }

  crearUno(serie: Prisma.SerieCreateInput) {
    return this.prisma.serie.create({
      data: serie,
    });
  }

  actualizarUno(parametrosActualizar: {
    id: number;
    data: Prisma.SerieUpdateInput;
  }) {
    return this.prisma.serie.update({
      data: parametrosActualizar.data,
      where: {
        id: parametrosActualizar.id,
      },
    });
  }

  eliminarUno(id: number) {
    return this.prisma.serie.delete({
      where: { id: id },
    });
  }
}
