package org.example.gateway.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {


    @Value("${jwt.signing.key}")
    private String jwtSecret;

    public AuthorizationHeaderFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            String authHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authHeader.replace("Bearer ", "");

            List<String> jwtAuthorities = getAuthorities(jwt);

            boolean match = jwtAuthorities.stream()
                    .anyMatch(authority -> config.getAuthorities().contains(authority));

            if (!match) return onError(exchange, HttpStatus.FORBIDDEN);

//            if (!isValidJwt(jwt)) {
//                return onError(exchange, HttpStatus.UNAUTHORIZED);
//            }

            return chain.filter(exchange);
        };
    }

    private List<String> getAuthorities(String jwt) {

        List<String> authorities = new ArrayList<>();

        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        SecretKey signingKey = Keys.hmacShaKeyFor(keyBytes);

        try {
            List<Map<String, String>> scopes = Jwts.parser()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody()
                    .get("scope", List.class);

            authorities.addAll(
                    scopes.stream()
                            .map(map -> map.get("authority"))
                            .toList()
            );
        } catch (Exception ex) {
            return authorities;
        }
        return authorities;
    }

//    private boolean isValidJwt(String jwt) {
//
//        String subject = null;
//
//        boolean returnValue = true;
//
//        String jwtSecret = env.getProperty("jwt.signing.key");
//
//        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
//        SecretKey signingKey = Keys.hmacShaKeyFor(keyBytes);
//
//        try {
//            subject = Jwts.parser()
//                    .setSigningKey(signingKey)
//                    .build()
//                    .parseClaimsJws(jwt)
//                    .getBody()
//                    .getSubject();
//        } catch (Exception ex) {
//            returnValue = false;
//        }
//
//        if (subject == null || subject.isEmpty()) {
//            returnValue = false;
//        }
//
//        return returnValue;
//    }

    private Mono<Void> onError(ServerWebExchange exchange,
                               HttpStatus httpStatus) {

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        return response.setComplete();

    }

    @Getter
    public static class Config {
        private List<String> authorities;

        public void setAuthorities(String authorities) {
            this.authorities = List.of(authorities.split(" "));
        }
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return List.of("authorities");
    }
}
