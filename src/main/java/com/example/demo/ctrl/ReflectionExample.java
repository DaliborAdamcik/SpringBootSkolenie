package com.example.demo.ctrl;

import com.example.demo.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Id;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/refl")
public class ReflectionExample {

    @GetMapping("/field")
    public Map<String, Object> getClassFields(@RequestParam("clzName") String clzName) {
        Map<String, Object> map = new HashMap<>();
        try {
            Class<?> clz = Class.forName(clzName);
            for (Field f : clz.getDeclaredFields()) {
                Map<String, Object> soj = new HashMap<>();
                map.put(f.getName(), soj);
                soj.put("class", f.getType());
                List<String> anots = new ArrayList<>();
                for (Annotation a : f.getDeclaredAnnotations())
                    anots.add(a.annotationType().getSimpleName());

                soj.put("anot", anots);
            }

        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        return map;
    }

    @GetMapping("crusr")
    public Object crateUser() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        Constructor<User> cont = User.class.getConstructor(String.class, String.class);
        Object[] params = new Object[cont.getParameterCount()];
        int i = 0;
        for (Parameter param : cont.getParameters()) {
            if (param.getType().equals(String.class))
                params[i] = param.getName();

            if (param.getType().equals(Long.class))
                params[i] = Long.valueOf(i);

            i++;

        }
        Object ret = cont.newInstance(params);
        ret.getClass().getMethod("setId", Long.class).invoke(ret, Long.valueOf(i));


        return ret;
    }
}
