package libraymanage.practice.librarypracticeapp.Service;

import java.util.List;

import libraymanage.practice.librarypracticeapp.Entity.Book;

public interface BookService {
    List<Book> getBooks();
    List<Book> getBookByTitle(String title);
    List<Book> getBookByPage(int page);
    Book getBookById(Long id);
    void addBook(Book book);
    void deleteBook(Long id);
    Book decreaseBookCopies(Long id, int quantity);
    Book increaseBookCopies(Long id, int quantity);
}
