package com.sml.burberry.controller;

import com.sml.burberry.dao.BurberryDAO;
import com.sml.burberry.dao.BurberryShoeRepository;
import com.sml.burberry.dao.NextShoeRepository;
import com.sml.burberry.model.GenericShoe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michaelgoode on 29/11/2016.
 */

@Controller
@RequestMapping("/next")
public class NextController {

    @Autowired
    private NextShoeRepository nextShoeRepository;

//    @Autowired
//    private NextDAO nextDAO;

    @RequestMapping(value = "/", method= RequestMethod.GET)
    public ModelAndView index() {
        System.out.println("NextController::index");
        return new ModelAndView("next/index");
    }

    @RequestMapping(value = "addshoe", method=RequestMethod.GET)
    public ModelAndView showform() {
        return new ModelAndView("next/addshoe","command", new GenericShoe());
    }

    @RequestMapping(value = "saveshoe", method = RequestMethod.GET)
    public ModelAndView addShoe(@ModelAttribute("shoe") GenericShoe genericShoe) {
        nextShoeRepository.saveShoe(genericShoe.getImagecode(), genericShoe.getImagename());
        ModelAndView modelAndView = new ModelAndView("next/addshoe","command",new GenericShoe());
        modelAndView.addObject("shoe", genericShoe);
        return modelAndView;
    }

    @RequestMapping(value = "/findshoe", method = RequestMethod.GET)
    public ModelAndView getShoe(@ModelAttribute("shoe") GenericShoe genericShoe) {
        System.out.println("NextController::findShoe() " + genericShoe.getImagecode());
        List<GenericShoe> list = new ArrayList<>();
        if (( genericShoe.getImagecode() != null ) || ( genericShoe.getImagename() != null )) {
            if (genericShoe.getSearch().equalsIgnoreCase("reference")) {
                list = nextShoeRepository.findShoeByReference(genericShoe.getImagecode());
            } else {
                list = nextShoeRepository.findShoeByImage(genericShoe.getImagecode());
            }
        }
        ModelAndView modelAndView = new ModelAndView("next/findshoe","command",new GenericShoe());
        modelAndView.addObject("list", list);
        modelAndView.addObject("shoe", genericShoe);
        return modelAndView;
    }
}

