import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { UsuarioModule } from './usuario/usuario.module';
import { PrismaService } from './prisma.service';
import { SerieModule } from './serie/serie.module';
//DECORADOR -->Funciones que ayudan hacer algo extra al código
@Module({
  imports: [UsuarioModule, SerieModule], //modulos importados
  controllers: [AppController], //controladores
  //controladores: los que receptan los doucmentos
  providers: [AppService, PrismaService], //servicios de este módiblo
  exports: [AppService], //servicios exportados (que se pueden usar fuera de este módulo)
})
export class AppModule {}
