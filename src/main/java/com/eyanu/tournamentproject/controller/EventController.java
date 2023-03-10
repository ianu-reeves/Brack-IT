package com.eyanu.tournamentproject.controller;

import com.eyanu.tournamentproject.entity.tournament.Application;
import com.eyanu.tournamentproject.entity.tournament.ApplicationId;
import com.eyanu.tournamentproject.entity.user.User;
import com.eyanu.tournamentproject.enums.EventType;
import com.eyanu.tournamentproject.enums.Region;
import com.eyanu.tournamentproject.enums.SortMethod;
import com.eyanu.tournamentproject.enums.SortOrder;
import com.eyanu.tournamentproject.model.ApplicationForm;
import com.eyanu.tournamentproject.model.EventForm;
import com.eyanu.tournamentproject.entity.tournament.Event;
import com.eyanu.tournamentproject.service.interfaces.ApplicationService;
import com.eyanu.tournamentproject.service.interfaces.EventService;
import com.eyanu.tournamentproject.service.interfaces.UserService;
import com.eyanu.tournamentproject.util.TimezoneGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/events")
@SessionAttributes({"availableRegions", "timezones", "eventTypes"})
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor editor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, editor);
    }

    @GetMapping("/")
    public String eventsPage() {
        return "event-main";
    }

    @RequestMapping("/browse/{page}")
    public String browseEventsPage(
            @PathVariable(required = false) Integer page,
            @RequestParam(required = false) Integer resultsPerPage,
            @RequestParam(required = false) SortMethod sortMethod,
            @RequestParam(required = false) SortOrder order,
            Model model
    ) {
        if (resultsPerPage == null || resultsPerPage < 25 || resultsPerPage > 100 || (resultsPerPage%25!=0)) {
            resultsPerPage = 25;
        }
        int totalPages = eventService.findLastPageNumber(resultsPerPage);
        if (page == null || page < 1 || page > totalPages) {
            page = 1;
        }
        if (sortMethod == null) {
            sortMethod = SortMethod.CREATION_DATE;  // order by creation date
        }
        if (order == null) {
            order = SortOrder.DESC; //
        }
        List<Event> events = eventService.findEventsByPage(page-1, resultsPerPage, sortMethod, order);

        model.addAttribute("events", events);
        model.addAttribute("sortMethods", SortMethod.values());
        model.addAttribute("sortOrders", SortOrder.values());

        return "event-browse";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model){
        //List<String> zoneIds = new ArrayList<>(ZoneId.getAvailableZoneIds());
        //Collections.sort(zoneIds);
        Map<String, String> zones = TimezoneGetter.getTimezones();
//        for (String zoneId : zoneIds) {
//            // TODO: Add function to query instant desired and return the offset for that moment
//            // i.e. to allow a date to be picked and have the offset at that moment displayed rather than
//            // the current offset
//            ZoneOffset offset = ZoneId.of(zoneId).getRules().getOffset(Instant.now());
//            zones.put(zoneId, zoneId + "(" + offset + " GMT)");
//        }
        model.addAttribute("timezones", zones);
        model.addAttribute("eventTypes", EventType.values());
        model.addAttribute("availableRegions", Region.values());
        model.addAttribute("eventForm", new EventForm());

        return "event-form";
    }
    @PostMapping(value = "/process")
    public String saveCreateForm(@Valid @ModelAttribute("eventForm") EventForm eventForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "event-form";
        }

        eventService.save(eventForm);
        return "redirect:/";
    }
//    @PostMapping(value = "/create", params = "cancel")
//    public String cancelEventCreation(Model model) {
//        model.addAttribute("info", "Event creation cancelled.");
//        return "redirect:/events";
//    }

    @GetMapping("/{id}/edit}")
    public String showEditForm(@PathVariable int id, Model model) {
        Event event = eventService.findEventById(id);
        EventForm eventForm = new EventForm(event);
        model.addAttribute("eventForm", eventForm);

        return "event-edit-form";
    }
    @PostMapping("/{id}/edit")
    public String saveEditForm(
            @Valid @ModelAttribute("event") EventForm eventForm, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "event-edit-form";
        }
        eventService.update(eventForm);

        return "redirect:/events" + eventForm.getId();
    }

    @GetMapping("/{id}")
    public String showEventDisplayPage(@PathVariable int id, Model model) {
        User user = userService.getCurrentlyAuthenticatedUser();
        Event event = eventService.findEventById(id);

        model.addAttribute("applicationForm", new ApplicationForm());
        model.addAttribute("event", event);
        model.addAttribute("user", user);
        model.addAttribute("canApply", applicationService.canApply(user, event));
        model.addAttribute("hasApplied", applicationService.hasApplied(user, event));
        return "event-display";
    }

    @PostMapping("/apply")
    public String apply(@ModelAttribute ApplicationForm applicationForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("info", "There was an error processing your application.");
            return "redirect:/events/"+applicationForm.getEventId();
        }
        Event event = eventService.findEventById(applicationForm.getEventId());
        User user = userService.findUserById(applicationForm.getUserId());
        ApplicationId applicationId = new ApplicationId(user, event);
        applicationService.apply(applicationId);
        model.addAttribute("event", applicationId.getEvent());
        return "application-success";
    }

    @PostMapping("/unapply")
    public String unapply(@ModelAttribute ApplicationForm applicationForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addAttribute("info", "Unable to withdraw your application. Please try again later or contact the event organizer.");
            return "redirect:/events/" + applicationForm.getEventId();
        }
        Event event = eventService.findEventById(applicationForm.getEventId());
        User user = userService.findUserById(applicationForm.getUserId());
        ApplicationId applicationId = new ApplicationId(user, event);
        Application application = applicationService.findApplicationById(applicationId);
        applicationService.delete(application);
        redirectAttributes.addFlashAttribute("info", "You have successfully withdrawn from the event.");

        return "redirect:/events/" + event.getId();
    }

    @PostMapping("/finalize")
    public String finalizeEventPage(@ModelAttribute("event") Event event, Model model) {
        if (event.getTournament().getCompetitors().size() <= 1) {
            model.addAttribute("error", "Event must have 2 or more competitors");
        } else {
            eventService.finalizeEvent(event);
        }

        return "redirect:/events/" + event.getId();
    }

}
