package com.itau.microservice.transactions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itau.microservice.transactions.model.dto.TransationDTO;
import com.itau.microservice.transactions.service.TransationService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.slf4j.Logger;

@Controller
@RequestMapping("/transacao")
public class TransationController {

  @Autowired
  private TransationService transationService;

  @Autowired
  private Logger logger;

  @PostMapping()
  public ResponseEntity<Void> addTransation(@RequestBody @Valid TransationDTO transationDTO, BindingResult result) {
    logger.info("POST /transacao endpoint called. DTO: {}", transationDTO);

    if (result.hasErrors()) {
      logger.warn("Validation errors found in TransationDTO: {}", result.getAllErrors());
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
    }

    logger.debug("Valid TransationDTO received. Proceeding to service call.");
    ResponseEntity<Void> response = transationService.addTransation(transationDTO);
    logger.info("Transation added successfully. Response: {}", response.getStatusCode());

    return response;
  }

  @DeleteMapping()
  public ResponseEntity<Void> deleteAllTransations() {
    logger.info("DELETE /transacao endpoint called. Deleting all transactions.");

    ResponseEntity<Void> response = transationService.deleteAllTransations();

    logger.info("All transactions deleted. Response: {}", response.getStatusCode());

    return response;
  }
}
