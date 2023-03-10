package com.eyanu.tournamentproject.controller;

import com.eyanu.tournamentproject.entity.user.User;
import com.eyanu.tournamentproject.enums.Region;
import com.eyanu.tournamentproject.model.RegistrationForm;
import com.eyanu.tournamentproject.service.interfaces.UserService;
import com.eyanu.tournamentproject.util.TimezoneGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
@SessionAttributes({"regions", "timezones"})
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        model.addAttribute("regions", Region.values());
        model.addAttribute("timezones", TimezoneGetter.getTimezones());
        return "registration-form";
    }

    @PostMapping("/process")
    public String processRegistration(
            @Valid @ModelAttribute("registrationForm") RegistrationForm registrationForm,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "registration-form";
        }

        User existingUser = userService.findUserByUsername(registrationForm.getUsername());
        if (existingUser != null) {
            model.addAttribute("registrationForm", registrationForm);
            model.addAttribute("regions", Region.values());
            model.addAttribute("error", "That username is invalid or already in use");

            return "registration-form";
        }

        userService.save(registrationForm);

        return "registration-confirmation";
    }
}
