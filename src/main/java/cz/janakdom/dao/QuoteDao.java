package cz.janakdom.dao;

import cz.janakdom.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteDao extends JpaRepository<Quote, Integer> {

}
