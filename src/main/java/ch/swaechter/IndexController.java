package ch.swaechter;

import ch.swaechter.components.account.AccountService;
import ch.swaechter.components.account.dto.AuthenticationDto;
import ch.swaechter.components.account.dto.TokenDto;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.validation.Validated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Controller
@Validated
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    private final AccountService accountService;

    public IndexController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Get(value = "/", produces = MediaType.TEXT_PLAIN)
    public String showWelcomeMessage() {
        logger.info("showWelcomeMessage");
        return "Welcome to the native image example!";
    }

    @Get(value = "/login", produces = MediaType.APPLICATION_JSON)
    public Optional<TokenDto> login() throws Exception {
        // Log
        logger.info("login");

        // Get a token and check it
        Optional<TokenDto> optionalTokenDto = accountService.login(new AuthenticationDto("admin", "123456aAbB"));
        logger.info("Is valid: " + optionalTokenDto.isPresent());

        return optionalTokenDto;
    }
}
