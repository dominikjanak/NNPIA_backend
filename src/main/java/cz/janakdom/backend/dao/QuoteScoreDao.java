package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.QuoteRating;
import cz.janakdom.backend.model.database.QuoteScoreKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuoteScoreDao extends JpaRepository<QuoteRating, QuoteScoreKey> {
    QuoteRating findByQuoteIdAndUserId(Integer quote_id, Integer user_id);
    List<QuoteRating> findAllByQuoteId(Integer quote_id);
    List<QuoteRating> findAllByUserId(Integer user_id);
    void deleteByQuoteIdAndUserId(Integer quote_id, Integer user_id);
}
