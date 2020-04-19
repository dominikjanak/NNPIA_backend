package cz.janakdom.service;

import cz.janakdom.dao.QuoteDao;
import cz.janakdom.model.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class QuoteServiceImplementation implements QuoteService {

    private final QuoteDao quoteDao;

    @Autowired
    public QuoteServiceImplementation(QuoteDao quoteDao) {
        this.quoteDao = quoteDao;
    }

    @Override
    public List<Quote> findAll() {
        return quoteDao.findAll();
    }

    @Override
    public Quote findById(Integer quoteId) {
        if (quoteDao.findById(quoteId).isPresent()) {
            return quoteDao.findById(quoteId).get();
        } else {
            throw new NoSuchElementException("Quote with ID: " + quoteId + " was not found!");
        }
    }
}
