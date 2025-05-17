package com.itau.microservice.transactions.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itau.microservice.transactions.model.dto.TransationDTO;
import com.itau.microservice.transactions.model.entity.TransationModel;
import org.slf4j.Logger;

@Service
public class TransationService {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private Logger logger;

  private List<TransationModel> transations = new ArrayList<>();

  public ResponseEntity<Void> addTransation(TransationDTO transationDTO) {
    logger.info("addTransation called. DTO: {}", transationDTO);

    try {
      logger.debug("Converting TransationDTO to TransationModel. DTO: {}", transationDTO);
      TransationModel transationModel = objectMapper.convertValue(transationDTO, TransationModel.class);
      logger.debug("Conversion successful. TransationModel: {}", transationModel);

      transations.add(transationModel);
      logger.info("Transaction added successfully. Total transactions: {}", transations.size());

      return ResponseEntity.ok().body(null);
    } catch (Exception e) {
      logger.error("Error while adding transaction. DTO: {}", transationDTO, e);
      return ResponseEntity.status(500).body(null);
    }
  }

  public ResponseEntity<Void> deleteAllTransations() {
    logger.info("deleteAllTransations called. Deleting all transactions.");

    try {
      transations.clear();
      logger.info("All transactions deleted successfully.");
      return ResponseEntity.ok().body(null);
    } catch (Exception e) {
      logger.error("Error while deleting transactions", e);
      return ResponseEntity.status(500).body(null);
    }
  }

  public List<TransationModel> getAllTransations() {
    logger.info("getAllTransations called. Returning {} transactions.", transations.size());
    return transations;
  }
}
