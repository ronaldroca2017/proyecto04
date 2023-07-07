package com.nttdata.clientmicroservice.mapper;

public interface EntityMapper <D, E> {
    E toEntity(D dto);
    D toDto(E entity);
}
