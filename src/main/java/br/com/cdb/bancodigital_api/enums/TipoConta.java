package br.com.cdb.bancodigital_api.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TipoConta {
    CORRENTE, POUPANCA;

    @JsonCreator
    public static TipoConta from(String value) {
        return TipoConta.valueOf(value.toUpperCase());
    }
}
