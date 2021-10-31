package br.com.unipac.protocoloapi.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @GetMapping(value = {"/", "/welcome"})
    public String welcome(Model model) {
        model.addAttribute("bemVindo", "Seja bem bindo");
        return "welcome";
    }

}
