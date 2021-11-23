package com.supportportal.service;

public class LoanTaxaService implements TaxaService {
    @Override
    public Double taxa(Double valor) {
        if (valor <= 10000 ) {
            return valor * 0.20;
        }else {
            return valor * 0.15;
        }
    }
}
