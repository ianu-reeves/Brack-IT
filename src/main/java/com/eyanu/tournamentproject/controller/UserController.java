package com.eyanu.tournamentproject.controller;

import com.eyanu.tournamentproject.entity.tournament.Tournament;
import com.eyanu.tournamentproject.entity.user.User;
import com.eyanu.tournamentproject.service.interfaces.TournamentService;
import com.eyanu.tournamentproject.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TournamentService tournamentService;

    @RequestMapping("/user/{username}")
    public String showProfilePage(@PathVariable String username, Model model) {
        User user = userService.findUserByUsername(username);
        model.addAttribute("user", user);
        List<Tournament> tournaments = tournamentService.findTournamentsByOwner(user);
        model.addAttribute("tournaments", tournaments);

        return "profile-page";
    }

}
