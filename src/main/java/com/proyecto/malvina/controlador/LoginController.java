package com.proyecto.malvina.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class LoginController {

    
    @GetMapping("/login")
    public String login(ModelMap modelo, @RequestParam(required=false) String error, @RequestParam(required=false) String ok) {
        if (error != null) {
            modelo.put("error", "Usuario o contraseñas incorrectos");
        }
        modelo.put("ok", ok);
        return "login.html";
    }
    
}