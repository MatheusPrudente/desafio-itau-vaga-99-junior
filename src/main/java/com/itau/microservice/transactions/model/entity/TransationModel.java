package com.itau.microservice.transactions.model.entity;

import java.time.OffsetDateTime;

public class TransationModel {
  private Double valor;
  private OffsetDateTime dataHora;

  public TransationModel() {
  }

  public TransationModel(Double valor, OffsetDateTime dataHora) {
    this.valor = valor;
    this.dataHora = dataHora;
  }

  public Double getValor() {
    return valor;
  }

  public void setValor(Double valor) {
    this.valor = valor;
  }

  public OffsetDateTime getDataHora() {
    return dataHora;
  }

  public void setDataHora(OffsetDateTime dataHora) {
    this.dataHora = dataHora;
  }
}
