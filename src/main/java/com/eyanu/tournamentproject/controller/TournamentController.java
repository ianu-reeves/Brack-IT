package com.eyanu.tournamentproject.controller;

import com.eyanu.tournamentproject.model.DisplayTournament;
import com.eyanu.tournamentproject.model.TournamentForm;
import com.eyanu.tournamentproject.entity.tournament.Tournament;
import com.eyanu.tournamentproject.enums.Region;
import com.eyanu.tournamentproject.service.interfaces.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/tournaments")
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("regions", Region.values());
        model.addAttribute("tournamentForm", new TournamentForm());
        return "tournament-form";
    }

    @GetMapping("/show/{id}")
    public String showTournamentDisplayPage(@PathVariable int id, Model model) {
        Tournament tournament = tournamentService.findTournamentById(id);
        DisplayTournament displayTournament = new DisplayTournament(tournament);
        displayTournament.generateDisplayObjects(displayTournament.getMaxTournamentDepth());
        model.addAttribute("tournament", displayTournament);
        return "tournament-display";
    }

    @PostMapping("/process")
    public String processTournament(
            @Valid @ModelAttribute("tournamentForm") TournamentForm tournamentForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "tournament-form";
        }
        int id = tournamentService.saveNewFromForm(tournamentForm);

        return "redirect:/tournaments/show/" + id;
    }
}
