package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.QuoteDao;
import cz.janakdom.backend.model.database.Author;
import cz.janakdom.backend.model.database.Category;
import cz.janakdom.backend.model.database.Quote;
import cz.janakdom.backend.model.database.User;
import cz.janakdom.backend.model.dto.QuoteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "quoteService")
public class QuoteService {

    private final QuoteDao dao;

    @Autowired private AuthorService authorService;
    @Autowired private CategoryService categoryService;
    @Autowired private UserService userService;

    @Autowired
    public QuoteService(QuoteDao dao) {
        this.dao = dao;
    }

    public Quote findById(int id, String username) {
        Optional<Quote> optional = Optional.ofNullable(dao.findByIdAndUserUsername(id, username));
        return optional.orElse(null);
    }

    public Quote findById(int id) {
        Optional<Quote> optional = dao.findById(id);
        return optional.orElse(null);
    }

    public Page<Quote> findAllByUser(String username, Pageable pageable){
        return dao.findAllByUserUsername(username, pageable);
    }

    public Page<Quote> findAll(Pageable pageable){
        return dao.findAll(pageable);
    }

    public Quote findByQuote(String quote){
        Optional<Quote> optional = Optional.ofNullable(dao.findByQuote(quote));
        return optional.orElse(null);
    }

    public boolean quoteExists(String quote) {
        return findByQuote(quote) != null;
    }

    public void delete(int id, String username) {
        dao.deleteByIdAndUserUsername(id, username);
    }

    public QuoteDto update(Integer id, String username, QuoteDto quoteDto) {
        Quote quote = findById(id, username);
        if(quote != null) {
            quote = fillQuote(quoteDto, null, quote);

            if(quote == null)
                return null;

            dao.save(quote);
            return quoteDto;
        }
        return null;
    }

    public Quote save(QuoteDto quote, String username) {
        Quote newQuote = fillQuote(quote, username, null);

        if(newQuote == null)
            return null;

        return dao.save(newQuote);
    }

    private Quote fillQuote(QuoteDto quote, String username, Quote filled){
        if(filled == null){
            filled = new Quote();
        }

        filled.setGlobal(quote.getGlobal());
        filled.setQuote(quote.getQuote());

        Author author = authorService.findById(quote.getAuthorId());
        filled.setAuthor(author);

        User user = null;
        if(filled.getUser() == null){
            user = userService.findByUsername(username);
            filled.setUser(user);
        }

        if(author == null || user == null){
            return null;
        }

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
