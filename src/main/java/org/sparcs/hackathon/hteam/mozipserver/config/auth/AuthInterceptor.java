package org.sparcs.hackathon.hteam.mozipserver.config.auth;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtParser jwtParser;

    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler
    ) throws Exception {
        Authorize authorize = null;

        if (handler instanceof HandlerMethod) {
            authorize = getAuthorize((HandlerMethod) handler);
        }

        if (authorize != null) {
            validateAuthorization(request);
        }

        return true;
    }

    private Authorize getAuthorize(HandlerMethod handlerMethod) {
        Authorize authorize = handlerMethod.getMethodAnnotation(Authorize.class);

        if (authorize == null) {
            authorize = handlerMethod.getBean().getClass().getAnnotation(Authorize.class);
        }

        return authorize;
    }

    private void validateAuthorization(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);

        if (token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}