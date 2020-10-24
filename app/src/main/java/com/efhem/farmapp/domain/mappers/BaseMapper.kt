package com.efhem.farmapp.domain.mappers

interface BaseMapper<E, D> {

    fun mapToDomain(type: E): D

    fun mapToDto(type: D): E

    fun mapToDomainList(models: List<E>?): List<D> {
        val list = mutableListOf<D>()
        models?.forEach {
            list.add(mapToDomain(it))
        }

        return list
    }

    fun mapToDtoList(models: List<D>?): List<E> {
        val list = mutableListOf<E>()
        models?.forEach {
            list.add(mapToDto(it))
        }

        return list
    }
}
