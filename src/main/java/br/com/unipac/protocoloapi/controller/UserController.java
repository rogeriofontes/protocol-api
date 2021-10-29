package br.com.unipac.protocoloapi.controller;

import br.com.unipac.protocoloapi.model.domain.Role;
import br.com.unipac.protocoloapi.model.domain.User;
import br.com.unipac.protocoloapi.model.repositories.RoleRepository;
import br.com.unipac.protocoloapi.model.service.SecurityService;
import br.com.unipac.protocoloapi.model.service.UserService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/registration")
    public String registration(Model model) {
        if (isAuthenticated()) {
            return "redirect:/";
        }

        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String registrationOk(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        List<Role> roles = roleRepository.findAll();
        userForm.setRoles(new HashSet<>(roles));
        userService.save(userForm);
        securityService.autoLogin(userForm.getUsername(), userForm.getPassword());
        return "redirect:/welcome";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (isAuthenticated()) {
            return "redirect:/";
        }

        if (error != null) {
            model.addAttribute("error", "Seu Usuario e Senha estão incorretos");
        }

        if (logout != null) {
            model.addAttribute("message", "Vocë deslogou da Conta");
        }

        return "login";
    }

    private boolean isAuthenticated() {
        return securityService.isAuthenticated();
    }
}
