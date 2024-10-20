package com.example.jwtparser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Key;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;


public class JwtParser {

    private final String jwtSecret;
    private final Claims claims;

    public JwtParser(String jwtToken, String jwtSecret) {
        this.jwtSecret = jwtSecret;
        this.claims = extractAllClaims(jwtToken);
    }

    /**
     * Извлечение имени пользователя из токена
     *
     * @return имя пользователя
     */
    public String extractUsername() {
        return this.claims.getSubject();
    }

    public Collection<? extends GrantedAuthority> extractAuthorities() {
        Object scopeObject = this.claims.get("scope");

        if (scopeObject instanceof Collection<?> scopes) {

            return scopes.stream()
                    .filter(item -> item instanceof Map)  // Проверяем, что элемент является Map
                    .map(item -> (Map<?, ?>) item)  // Приводим к Map
                    .map(scopeMap -> new SimpleGrantedAuthority((String) scopeMap.get("authority")))  // Извлекаем "authority"
                    .toList();
        }

        return Collections.emptyList();  // Возвращаем пустой список, если scope не найден
    }

    /**
     * Извлечение данных из токена
     *
     * @param token           токен
     * @param claimsResolvers функция извлечения данных
     * @param <T>             тип данных
     * @return данные
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    /**
     * Извлечение всех данных из токена
     *
     * @param token токен
     * @return данные
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Получение ключа для подписи токена
     *
     * @return ключ
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
