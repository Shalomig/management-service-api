
package com.example.controller;

import com.example.model.Book;
import com.example.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public List<Book> listBooks() {
        return bookRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        // Check if the book with the given ISBN already exists
        Book book_list = bookRepository.getBookByIsbn(book.getIsbn());
    
        if (book_list == null) {
            // Save the book if it does not exist
            Book savedBook = bookRepository.save(book);
            return ResponseEntity.ok(savedBook);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("A book with ISBN " + book.getIsbn() + " already exists.");
        }

    }
    

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        Book existingBook = bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found"));
        existingBook.setName(book.getName());
        existingBook.setIsbn(book.getIsbn());
        existingBook.setPublishDate(book.getPublishDate());
        existingBook.setPrice(book.getPrice());
        existingBook.setBookType(book.getBookType());
        return bookRepository.save(existingBook);
    }

   @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        if (!bookRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        }
        bookRepository.deleteById(id);
        return ResponseEntity.ok("Book deleted successfully"); // Return a 200 OK with a message
    }

}




