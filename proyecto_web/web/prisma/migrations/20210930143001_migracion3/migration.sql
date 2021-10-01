/*
  Warnings:

  - Added the required column `temporadas` to the `Serie` table without a default value. This is not possible if the table is not empty.

*/
-- AlterTable
ALTER TABLE `Serie` ADD COLUMN `temporadas` INTEGER NOT NULL;
