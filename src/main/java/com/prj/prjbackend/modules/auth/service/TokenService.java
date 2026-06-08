package com.prj.prjbackend.modules.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.prj.prjbackend.modules.profile.Profile;
import com.prj.prjbackend.modules.user.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class TokenService {
    //TODO: Perguntar ao Mozar se é permitido varíaveis de ambiente
    private final String secret = "54cd5b827c0ec938fa072a29b177469c843317b095591dc846767aa338bac600";

    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        List<String> roles = user.getProfiles().stream()
                .map(Profile::getName)
                .toList();

        return JWT.create()
                .withIssuer("prj-backend")
                .withSubject(user.getEmail())
                .withClaim("roles", roles)
                .withExpiresAt(Instant.now().plusSeconds(7200))
                .sign(algorithm);
    }
}
