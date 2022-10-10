package ch.swaechter.components.account.dto;

import io.micronaut.core.annotation.Introspected;

@Introspected
public record TokenDto(

        String token
) {
}
