
package com.example.repository;

import com.example.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
     // Get a book by ISBN
     @Query(value = "SELECT * FROM book WHERE isbn = :isbn", nativeQuery = true)
     Book getBookByIsbn(@Param("isbn") String isbn);

}



