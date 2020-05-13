package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.QuoteDao;
import cz.janakdom.backend.model.database.*;
import cz.janakdom.backend.model.dto.OutputQuoteDto;
import cz.janakdom.backend.model.dto.QuoteDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Page<OutputQuoteDto> findAllByUser(String username, Pageable pageable){
        Page<Quote> quotes = dao.findAllByUserUsername(username, pageable);

        return createQuotePage(quotes, username);
    }

    public Page<OutputQuoteDto> findAll(String username, Pageable pageable){
        Page<Quote> quotes = dao.findAll(pageable);
        return createQuotePage(quotes, username);
    }

    public Page<OutputQuoteDto> createQuotePage(Page<Quote> quotes, String username){
        List<OutputQuoteDto> output = new ArrayList<>();

        for (Quote q : quotes.getContent()) {
            output.add(convertQuote(q, username));
        }
        return new PageImpl<>(output, quotes.getPageable(), output.size());
    }

    public OutputQuoteDto convertQuote(Quote quote, String username){
        OutputQuoteDto newQ = new OutputQuoteDto();
        BeanUtils.copyProperties(quote, newQ, "scores");
        newQ.setUservoted(false);
        newQ.setUserscore(0);

        int sum = quote.getScores().stream().mapToInt(QuoteRating::getScore).sum();
        List<QuoteRating> userscore = quote.getScores().stream().filter(o -> o.getUser().getUsername().equals(username)).collect(Collectors.toList());

        newQ.setScore(sum / (double)quote.getScores().size());

        if(userscore.size() == 1){
            newQ.setUserscore(userscore.get(0).getScore());
            newQ.setUservoted(true);
        }
        return newQ;
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

        if(filled.getAuthor() == null || !filled.getAuthor().getId().equals(quote.getAuthorId())) {
            Author author = authorService.findById(quote.getAuthorId());
            filled.setAuthor(author);
        }

        if(filled.getUser() == null){
            User user = userService.findByUsername(username);
            filled.setUser(user);
        }

        if(filled.getAuthor() == null || filled.getUser() == null){
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
