package cz.janakdom.service;

import cz.janakdom.dao.QuoteDao;
import cz.janakdom.model.database.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service(value = "quoteService")
public class QuoteService {

    private final QuoteDao quoteDao;

    @Autowired
    public QuoteService(QuoteDao quoteDao) {
        this.quoteDao = quoteDao;
    }

    public List<Quote> findAll() {
        return quoteDao.findAll();
    }

    public Quote findById(Integer quoteId) {
        if (quoteDao.findById(quoteId).isPresent()) {
            return quoteDao.findById(quoteId).get();
        } else {
            throw new NoSuchElementException("Quote with ID: " + quoteId + " was not found!");
        }
    }
}
