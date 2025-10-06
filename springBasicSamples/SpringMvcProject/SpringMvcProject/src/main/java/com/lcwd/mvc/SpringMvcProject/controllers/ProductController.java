package com.lcwd.mvc.SpringMvcProject.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("/getProduct")
    public String getProduct(
            @RequestParam("productName") String name,
            @RequestParam(value = "productRating", defaultValue = "0", required = false) int rating,
            @RequestParam("productId") int id) {
        System.out.println("ProductName: " + name);
        System.out.println("ProductId " + id);
        System.out.println("Product Rating " + rating);
        return "This is testing product url";
    }

    @RequestMapping("/checkProduct/{productId}/{productName}/{productRating}")
    public String checkProduct(
            @PathVariable("productId") int id,
            @PathVariable String productName,
            @PathVariable int productRating
    ) {
//        System.out.println("ProductName " + productName);
//        System.out.println("product Id " + id);
//        System.out.println("product Rating " + productRating);
        logger.error("ProductName {} {}", productName, "Testing argument");
        logger.warn("ProductId {}", id);
        logger.info("Product Rating {}", productRating);
        logger.debug("This is testing for debug");
        return "this is checking the concept of path variable";
    }

}
