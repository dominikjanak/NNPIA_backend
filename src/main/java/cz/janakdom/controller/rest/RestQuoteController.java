package cz.janakdom.controller.rest;

import cz.janakdom.service.QuoteServiceImplementation;
import cz.janakdom.model.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/quote")
@CrossOrigin
public class RestQuoteController {

    private final QuoteServiceImplementation quoteService;

    @Autowired
    public RestQuoteController(QuoteServiceImplementation quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping
    public List<Quote> getAllQuotes() {
        return quoteService.findAll();
    }

    @GetMapping("{quoteId}")
    public Quote getQuoteById(@PathVariable Integer quoteId) {
        return quoteService.findById(quoteId);
    }

}
