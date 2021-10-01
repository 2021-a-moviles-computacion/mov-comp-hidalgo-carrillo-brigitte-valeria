import { Module } from '@nestjs/common';
import { UsuarioService } from './usuario.service';
import { UsuarioController } from './usuario.controller';
import { PrismaService } from '../prisma.service';



@Module({
  imports: [
    // modulos importados
  ],
  providers: [
    // declaramos servicio
    UsuarioService,
    PrismaService,
  ],
  exports: [
    // exportamos servicio
    UsuarioService,
  ],
  controllers: [
    // declaramos controladores
    UsuarioController,
  ],
})
export class UsuarioModule {}
