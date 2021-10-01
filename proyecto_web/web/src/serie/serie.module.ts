import { PrismaService } from '../prisma.service';
import { SerieService } from './serie.service';
import { SerieController } from './serie.controller';
import { Module } from '@nestjs/common';

@Module({
  imports: [
    // modulos importados
  ],
  providers: [
    // declaramos servicio
    SerieService,
    PrismaService,
  ],
  exports: [
    // exportamos servicio
    SerieService,
  ],
  controllers: [
    // declaramos controladores
    SerieController,
  ],
})
export class SerieModule {}
