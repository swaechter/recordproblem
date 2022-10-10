package ch.swaechter.components.account;

import ch.swaechter.components.account.dto.AuthenticationDto;
import ch.swaechter.components.account.dto.TokenDto;
import io.micronaut.context.annotation.Context;

import java.util.Optional;

@Context
public class AccountService {

    public Optional<TokenDto> login(AuthenticationDto authenticationDto) throws Exception {
        return Optional.of(new TokenDto("Fancy token!"));
    }
}
