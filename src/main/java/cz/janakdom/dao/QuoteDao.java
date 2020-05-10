package cz.janakdom.dao;

import cz.janakdom.model.database.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteDao extends JpaRepository<Quote, Integer> {

}
