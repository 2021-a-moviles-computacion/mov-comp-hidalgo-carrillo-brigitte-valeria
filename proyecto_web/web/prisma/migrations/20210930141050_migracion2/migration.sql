/*
  Warnings:

  - You are about to drop the `EPN_USUARIO` table. If the table is not empty, all the data it contains will be lost.
  - You are about to drop the `Mascota` table. If the table is not empty, all the data it contains will be lost.

*/
-- DropForeignKey
ALTER TABLE `Mascota` DROP FOREIGN KEY `Mascota_usuarioId_fkey`;

-- DropTable
DROP TABLE `EPN_USUARIO`;

-- DropTable
DROP TABLE `Mascota`;

-- CreateTable
CREATE TABLE `Serie` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `nombre_serie` VARCHAR(191) NOT NULL,
    `nombre_director` VARCHAR(191) NOT NULL,
    `fecha_creacion` DATETIME(3) NOT NULL,
    `tiene_emmy` BOOLEAN NOT NULL,

    PRIMARY KEY (`id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
