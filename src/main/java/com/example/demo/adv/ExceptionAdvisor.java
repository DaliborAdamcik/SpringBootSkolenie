package com.example.demo.adv;

import com.example.demo.exc.InvalidNameOrPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionAdvisor {

    @ExceptionHandler(value = {InvalidNameOrPasswordException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    private @ResponseBody Map<String, Object> handleException(InvalidNameOrPasswordException exc) {
        Map<String, Object> map = new HashMap<>();
        map.put("WhatsHappend", "You tried to authorize user with bad credentials.");
        map.put("ExcClz", exc.getClass().getName());
        map.put("When", new Date());
        map.put("Where", "Your sitting place");

        return map;
    }
}
