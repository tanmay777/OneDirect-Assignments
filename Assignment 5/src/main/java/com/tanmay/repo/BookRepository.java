package com.tanmay.repo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

import com.tanmay.model.Book;

/**
 * @author tanmaysowdhaman
 * Dec 14, 2017
 */
public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findByTitle(String title);

    Optional<Book> findOne(long id);
}
