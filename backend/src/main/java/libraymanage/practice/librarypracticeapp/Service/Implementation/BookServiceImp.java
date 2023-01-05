package libraymanage.practice.librarypracticeapp.Service.Implementation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import libraymanage.practice.librarypracticeapp.Entity.Book;
import libraymanage.practice.librarypracticeapp.Exception.AppIllegalStateException;
import libraymanage.practice.librarypracticeapp.Exception.EntityExistException;
import libraymanage.practice.librarypracticeapp.Exception.EntityNotFoundException;
import libraymanage.practice.librarypracticeapp.Repository.BookRepos;
import libraymanage.practice.librarypracticeapp.Service.BookService;

@Service
public class BookServiceImp implements BookService {
    @Autowired
    BookRepos bookRepos;

    @Override
    public void addBook(Book book) {
        Optional<Book> entity = bookRepos.findByTitleAndAuthor(book.getTitle(), book.getAuthor());
        if(entity.isPresent()) {
            throw new EntityExistException("the book with title" + book.getTitle() + " and the author " + book.getAuthor() + " already exist");
        }
        bookRepos.save(book);
        
    }

    @Override
    public Book decreaseBookCopies(Long id, int quantity) {
        Optional<Book> entity = bookRepos.findById(id);
        Book book = isCheck(entity, id);
        if(book.getCopies() - quantity < 0) {
            throw new AppIllegalStateException("the quantity decreased is higher than the total copies of book, cannot decrease its copies");
        }
        if(book.getCopiesAvailable() - quantity < 0) {
            throw new AppIllegalStateException("the quantity decreased is higher than the  available copies of book, cannot decrease its copies");
        }

        book.setCopies(book.getCopies() - quantity);
        book.setCopiesAvailable(book.getCopiesAvailable() - quantity);
       return bookRepos.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        Optional<Book> entity = bookRepos.findById(id);
        Book book = isCheck(entity, id);
        bookRepos.delete(book);
        
    }

    @Override
    public Book getBookById(Long id) {
        Optional<Book> entity = bookRepos.findById(id);
        Book book = isCheck(entity, id);
        return book;
    }

    @Override
    public List<Book> getBookByTitle(String title) {
        List<Book> books = bookRepos.findAll();
        List<Book> newBooks = books.stream().filter(bok -> bok.getTitle().contains(title)).collect(Collectors.toList());
        return newBooks;
    }

    @Override
    public List<Book> getBooks() {
        return bookRepos.findAll();
    }

    @Override
    public Book increaseBookCopies(Long id, int quantity) {
        Optional<Book> entity = bookRepos.findById(id);
        Book book = isCheck(entity, id);
       

        book.setCopies(book.getCopies() + quantity);
        book.setCopiesAvailable(book.getCopiesAvailable() + quantity);
       return bookRepos.save(book);
    }

    @Override
    public List<Book> getBookByPage(int page) {
        if(page < 0) {
            throw new AppIllegalStateException("the page " + page + " must be higher  than  or equals to 0 ");
        }
        Pageable sortedByPage = PageRequest.of(page, 3, Sort.by("title").descending());
        List<Book> books = bookRepos.findAll(sortedByPage).getContent();
        return books;
    }

    private Book isCheck(Optional<Book> entity, Long id) {
        if(entity.isPresent()) {
            return entity.get();
        }
        throw new EntityNotFoundException("the book with id " + id + " not found");
    }

    
}
