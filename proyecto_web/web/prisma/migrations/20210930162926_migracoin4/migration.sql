/*
  Warnings:

  - You are about to drop the column `fecha_creacion` on the `Serie` table. All the data in the column will be lost.
  - Added the required column `fecha_lanzamiento` to the `Serie` table without a default value. This is not possible if the table is not empty.

*/
-- AlterTable
ALTER TABLE `Serie` DROP COLUMN `fecha_creacion`,
    ADD COLUMN `fecha_creacion_dato` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    ADD COLUMN `fecha_lanzamiento` VARCHAR(191) NOT NULL;
