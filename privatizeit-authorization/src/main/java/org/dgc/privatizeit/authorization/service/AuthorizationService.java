package org.dgc.privatizeit.authorization.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.dgc.privatizeit.authorization.domain.User;
import org.dgc.privatizeit.authorization.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Service
public class AuthorizationService
{
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationService.class);
    private static final long EXP_LENGTH = 86400000000L;

    @Autowired
    private UserRepository userRepository;

    @Value("${application.properties.security.secret}")
    private String secret;

    /**
     * Register new user.
     *
     * @param user to be registered.
     */
    public void registerUser(User user)
    {
        try
        {
            if (!userRepository.findById(user.getName()).isPresent())
            {
                user.setId(user.getName());
                user.setPassword(new String(MessageDigest.getInstance("MD5").digest(user.getPassword().getBytes())));

                userRepository.save(user);
            }
            else
            {
                throw new RuntimeException("User already registered");
            }
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException("Internal Exception", e);
        }
    }

    /**
     * Authenticates user against database and creates a JWT token that will be used for authentication by the rest of
     * services.
     *
     * @param user to be authenticated.
     * @return a JWT token.
     */
    public String authenticateUser(User user)
    {
        try
        {
            User regUser = userRepository.findById(user.getName()).orElseThrow(() -> new Throwable("User not found"));

            if (!regUser.getPassword().equals(new String(MessageDigest.getInstance("MD5").digest(user.getPassword().getBytes()))))
            {
                throw new RuntimeException("Incorrect password");
            }

            return Jwts.builder()
                    .setSubject(user.getName())
                    .setExpiration(new Date(System.currentTimeMillis() + EXP_LENGTH))
                    .signWith(SignatureAlgorithm.HS512, secret)
                    .compact();
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException("Internal exception", e);
        }
        catch (Throwable throwable)
        {
            throw new RuntimeException(throwable.getMessage());
        }
    }
}
