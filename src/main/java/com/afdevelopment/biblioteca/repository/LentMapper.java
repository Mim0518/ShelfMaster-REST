package com.afdevelopment.biblioteca.repository;

import com.afdevelopment.biblioteca.dto.LentDto;
import com.afdevelopment.biblioteca.model.Lent;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface LentMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBookFromDto(LentDto lentDto, @MappingTarget Lent entity);
}
