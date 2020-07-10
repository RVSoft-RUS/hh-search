package com.rvs.hhsearch.controller;

import com.rvs.hhsearch.model.User;
import com.rvs.hhsearch.model.Vacancy;
import com.rvs.hhsearch.searcher.HH_Searcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/hh")
public class HHController {
    private HH_Searcher searcher;

    @Autowired
    public HHController(HH_Searcher searcher) {
        this.searcher = searcher;
    }

    @GetMapping("/select")
    public String selectTown() {
        return "/hh/select";
    }

    @PostMapping("/select")
    public ModelAndView getAllVacancies(WebRequest request) {
        String town = request.getParameter("town");
        String vacancyText = request.getParameter("vacancy");
        ModelAndView mv = new ModelAndView();
        List<Vacancy> vacancyList = searcher.getVacancies(vacancyText, town);
        mv.addObject("vacancies", vacancyList);
        mv.setViewName("hh/info");
        return mv;
    }
}
