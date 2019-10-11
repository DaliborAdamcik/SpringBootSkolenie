package com.example.demo.resolver;

import com.example.demo.AutowireHelper;
import com.example.demo.exc.InvalidNameOrPasswordException;
import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

public class CurrentUserResolver implements HandlerMethodArgumentResolver {
    private static final Log LOGGER = LogFactory.getLog(CurrentUserResolver.class);

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return User.class.equals(methodParameter.getParameterType()) && methodParameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        LOGGER.info(String.format("Requesting user authorization (%s)", nativeWebRequest.getClass().getName()));

        String usrName = nativeWebRequest.getHeader("Auth-name");
        LOGGER.info(String.format("User name is '%s'", usrName));

        Optional<User> usro = AutowireHelper.getBean(UserRepository.class).findByName(usrName);
        if (usro.isPresent()) {
            String password = nativeWebRequest.getHeader("Auth-pass");
            LOGGER.info(String.format("Password is '%s'", password));
            User usr = usro.get();

            if (usr.isPasswordValid(password))
                return usr;
        }

        throw new InvalidNameOrPasswordException("To access resource is requested use of valid credentials");
    }

}
