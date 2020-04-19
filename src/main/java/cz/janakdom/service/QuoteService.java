package cz.janakdom.service;

import cz.janakdom.model.Quote;

import java.util.List;

public interface QuoteService {

    List<Quote> findAll();

    Quote findById(Integer quoteId);
}
