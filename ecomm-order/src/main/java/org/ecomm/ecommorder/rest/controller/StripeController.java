package org.ecomm.ecommorder.rest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("stripe")
@Slf4j
public class StripeController {

    @PostMapping("callback")
    public void callback(@RequestBody Object request){
       log.info("stripe payment intent ::: {}", request);
    }

}
