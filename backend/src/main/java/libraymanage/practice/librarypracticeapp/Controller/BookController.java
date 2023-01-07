package libraymanage.practice.librarypracticeapp.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import libraymanage.practice.librarypracticeapp.Entity.Book;
import libraymanage.practice.librarypracticeapp.Service.BookService;
   
@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    BookService bookService;

    @PreAuthorize("permitAll()")
    @GetMapping("/all")
    public ResponseEntity<List<Book>> getBooks() {
        return new ResponseEntity<>(bookService.getBooks(), HttpStatus.OK);
    }
    @PreAuthorize("permitAll()")
    @GetMapping("/page/{page}")
    public ResponseEntity<List<Book>> getBooksByPage(@PathVariable int page) {
        return new ResponseEntity<>(bookService.getBookByPage(page), HttpStatus.OK);
    }
    @PreAuthorize("permitAll()")
    @GetMapping("/title/{title}")
    public ResponseEntity<List<Book>> getBooksByTitle(@PathVariable String title) {
        return new ResponseEntity<>(bookService.getBookByTitle(title), HttpStatus.OK);
    }
    @PreAuthorize("permitAll()")
    @GetMapping("/id/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/")
    public ResponseEntity<HttpStatus> saveBook(@Valid @RequestBody Book book) {
        bookService.addBook(book);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/id/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id); 
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }     
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/id/{id}/increasequantity/{quantity}")
    public ResponseEntity<Book> increaseCopies(@PathVariable Long id, @PathVariable int quantity) {
        return new ResponseEntity<>(bookService.increaseBookCopies(id, quantity), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/id/{id}/decreasequantity/{quantity}")
    public ResponseEntity<Book> decreaseCopies(@PathVariable Long id, @PathVariable int quantity) {
        return new ResponseEntity<>(bookService.decreaseBookCopies(id, quantity), HttpStatus.OK);
    }



}
