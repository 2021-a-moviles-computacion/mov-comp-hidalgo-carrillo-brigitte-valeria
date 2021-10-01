import {
  IsEmpty,
  IsNotEmpty,
  IsOptional,
  IsString,
  MaxLength,
  MinLength,
} from 'class-validator';

export class UsuarioCrearDto {
  @IsNotEmpty() // requerido
  @IsString()
  @MinLength(3)
  @MaxLength(10)
  apellido: string;

  @IsOptional()
  @IsString()
  @MinLength(3)
  @MaxLength(10)
  nombre: string;

  @IsEmpty() // debe estar vacio
  fechaCreacion: string;
}
