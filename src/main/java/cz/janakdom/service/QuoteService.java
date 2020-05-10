package cz.janakdom.service;

import cz.janakdom.dao.QuoteDao;
import cz.janakdom.model.database.Author;
import cz.janakdom.model.database.Category;
import cz.janakdom.model.database.Quote;
import cz.janakdom.model.dto.AuthorDto;
import cz.janakdom.model.dto.QuoteDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service(value = "quoteService")
public class QuoteService {

    private final QuoteDao dao;

    @Autowired private AuthorService authorService;
    @Autowired private CategoryService categoryService;

    @Autowired
    public QuoteService(QuoteDao dao) {
        this.dao = dao;
    }

    public List<Quote> findAll(){
        return dao.findAll();
    }

    public Quote findById(int id) {
        Optional<Quote> optional = dao.findById(id);
        return optional.orElse(null);
    }

    public Quote findByQuote(String quote){
        Optional<Quote> optional = Optional.ofNullable(dao.findByQuote(quote));
        return optional.orElse(null);
    }

    public boolean quoteExists(String quote) {
        return findByQuote(quote) != null;
    }

    public void delete(int id) {
        dao.deleteById(id);
    }

    public QuoteDto update(Integer id, QuoteDto quoteDto) {
        Quote quote = findById(id);
        if(quote != null) {
            quote = fillQuote(quoteDto, quote);

            if(quote == null)
                return null;

            dao.save(quote);
            return quoteDto;
        }
        return null;
    }

    public Quote save(QuoteDto quote) {
        Quote newQuote = fillQuote(quote, null);

        if(newQuote == null)
            return null;

        return dao.save(newQuote);
    }

    private Quote fillQuote(QuoteDto quote, Quote filled){
        if(filled == null){
            filled = new Quote();
        }

        filled.setGlobal(quote.getGlobal());
        filled.setQuote(quote.getQuote());

        Author author = authorService.findById(quote.getAuthorId());

        if(author == null){
            return null;
        }

        filled.setAuthor(author);

        filled.getCategories().clear();

        for (Integer categoryId: quote.getCategories()) {
            Category category = categoryService.findById(categoryId);

            if(category != null) {
                filled.getCategories().add(category);
            }
        }

        return filled;
    }

    public Quote save(Quote quote) {
        return dao.save(quote);
    }

    public boolean canBeUpdate(int id, String quote) {
        Quote find = findByQuote(quote);
        if(find == null) return true;
        return find.getId() == id;
    }
}
