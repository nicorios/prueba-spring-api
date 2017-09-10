package com.oksocios.controller;

import com.oksocios.model.Movement;
import com.oksocios.service.ConceptService;
import com.oksocios.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class BalanceController {

    private final MovementService movementService;
    private final ConceptService conceptService;

    @Autowired
    public BalanceController(MovementService movementService, ConceptService conceptService) {
        this.movementService = movementService;
        this.conceptService = conceptService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/balance")
    public String getBalance(Model model, @SessionAttribute Long idEstablishment){
        model.addAttribute("concepts", conceptService.getConceptsByEstablishmentId(idEstablishment));
        model.addAttribute("movements", movementService.getMovements(idEstablishment));
        return "balance";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/balance")
    public ResponseEntity saveMovement(@RequestBody Movement movement, @SessionAttribute Long idEstablishment, Principal principal){
        return new ResponseEntity(HttpStatus.OK);
    }
}
