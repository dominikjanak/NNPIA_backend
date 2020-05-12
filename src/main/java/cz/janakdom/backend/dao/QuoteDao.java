package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.Quote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteDao extends JpaRepository<Quote, Integer> {
    Quote findByQuote(String quote);
    Quote findByIdAndUserUsername(Integer id, String user_username);
    Page<Quote> findAllByUserUsername(String user_username, Pageable pageable);
    Page<Quote> findAll(Pageable pageable);
}
