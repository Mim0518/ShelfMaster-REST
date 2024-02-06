package com.afdevelopment.biblioteca.repository;

import com.afdevelopment.biblioteca.dto.ShelfDto;
import com.afdevelopment.biblioteca.model.Shelf;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ShelfMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateShelfFromDto(ShelfDto shelfDto, @MappingTarget Shelf entity);
}
