package com.afdevelopment.biblioteca.repository;

import com.afdevelopment.biblioteca.dto.BookDto;
import com.afdevelopment.biblioteca.model.Book;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBookFromDto(BookDto bookDto, @MappingTarget Book entity);
}
