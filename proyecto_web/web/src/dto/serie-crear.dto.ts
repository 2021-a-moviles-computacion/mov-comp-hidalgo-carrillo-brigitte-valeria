import {
  IsBoolean,
  IsDate, IsDateString,
  IsEmpty, IsIn,
  IsInt,
  IsNotEmpty,
  IsNumber,
  IsOptional,
  IsPositive,
  IsString,
  MaxLength,
  MinLength,
} from 'class-validator';

export class SerieCrearDto {



  @IsNotEmpty() // requerido
  @IsString()
  @MinLength(3)
  @MaxLength(50)
  nombre_serie: string;

  @IsNotEmpty() // requerido
  @IsString()
  @MinLength(3)
  @MaxLength(50)
  nombre_director: string;

  @IsDateString()
  @IsNotEmpty()
  fecha_lanzamiento: string;

  @IsBoolean()
  @IsNotEmpty()
  tiene_emmy: boolean;

  @IsInt()
  @IsPositive()
  temporadas: number;
}
