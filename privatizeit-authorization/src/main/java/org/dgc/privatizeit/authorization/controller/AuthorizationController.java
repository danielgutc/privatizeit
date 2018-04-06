package org.dgc.privatizeit.authorization.controller;

import org.dgc.privatizeit.authorization.service.AuthorizationService;
import org.dgc.privatizeit.authorization.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
public class AuthorizationController
{
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationController.class);

    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";

    @Autowired
    private AuthorizationService authorizationService;

    /**
     * Register users to the service.
     * @param user to be registered.
     * @return the status
     */
    @PostMapping("/signup")
    public Mono<ResponseEntity> signUp(@RequestBody User user)
    {
        try
        {
            authorizationService.registerUser(user);

            return Mono.just(ResponseEntity.created(new URI("TODO")).build());
        }
        catch (Exception ex)
        {
            logger.error(ex.getMessage(), ex);

            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        }
    }

    /**
     * Sign in method.
     * @param user who signs in
     * @return the JWT security that will be used by other services during the authentication
     */
    @PostMapping("/signin")
    public Mono<ResponseEntity> signIn(@RequestBody User user)
    {
        try
        {
            String token = authorizationService.authenticateUser(user);
            logger.info(token);

            return Mono.just(ResponseEntity.status(HttpStatus.OK).header(HEADER_STRING, TOKEN_PREFIX + " " + token).build());
        }
        catch (Exception ex)
        {
            logger.error(ex.getMessage(), ex);

            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
    }
}
