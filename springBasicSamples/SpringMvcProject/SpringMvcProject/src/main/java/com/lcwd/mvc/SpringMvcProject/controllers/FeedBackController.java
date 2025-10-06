package com.lcwd.mvc.SpringMvcProject.controllers;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class FeedBackController {


//    @RequestMapping(value = "/feedbacks",method = RequestMethod.GET)

    @GetMapping("/feedbacks")
    public List<String> getFeedbacks() {
        List<String> feedbacks = Arrays.asList(
                "Good course",
                "Nice one",
                "valuable thing"
        );
        return feedbacks;
    }

    //@RequestMapping(value="/create-feedback",method=POST)
    @PostMapping("/create-feedback")
    public String createFeedback(){
        System.out.println("Feedback created");
        return "Created feedback";
    }

    /*
    @PutMapping
    @DeletingMapping
    @PatchMapping

     */
//    @PatchMapping
//    @DeleteMapping

}
