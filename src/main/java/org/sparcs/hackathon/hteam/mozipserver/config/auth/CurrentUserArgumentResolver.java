package org.sparcs.hackathon.hteam.mozipserver.config.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtParser jwtParser;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(
        MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory
    ) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String token = AuthorizationExtractor.extract(Objects.requireNonNull(request));
        return token == null ? null : getUserIdFromToken(token);
    }

    private Long getUserIdFromToken(String token) {
        Claims payload = jwtParser.parseClaimsJws(token).getBody();
        return Long.valueOf(String.valueOf(payload.get("id")));
    }
}
