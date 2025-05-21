package ru.sechko.homework.libraryapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookDto(
    @NotBlank String title,
    @NotNull Long authorId,
    @NotNull Integer year
){ }
