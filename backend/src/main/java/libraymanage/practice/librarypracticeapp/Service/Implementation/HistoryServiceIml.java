package libraymanage.practice.librarypracticeapp.Service.Implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import libraymanage.practice.librarypracticeapp.Entity.Book;
import libraymanage.practice.librarypracticeapp.Entity.History;
import libraymanage.practice.librarypracticeapp.Entity.Users;
import libraymanage.practice.librarypracticeapp.Exception.AppIllegalStateException;
import libraymanage.practice.librarypracticeapp.Exception.EntityNotFoundException;
import libraymanage.practice.librarypracticeapp.Repository.BookRepos;
import libraymanage.practice.librarypracticeapp.Repository.HistoryRepos;
import libraymanage.practice.librarypracticeapp.Repository.UserRepos;
import libraymanage.practice.librarypracticeapp.Service.HistoryService;

@Service
public class HistoryServiceIml implements HistoryService {
    @Autowired
    HistoryRepos historyRepos;

    @Autowired
    UserRepos userRepos;

    @Autowired
    BookRepos bookRepos;

    @Override
    public List<History> getHistories() {
        return historyRepos.findAll();
    }

    @Override
    public List<History> getHistoriesByAuthUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(username == null) {
            throw new AppIllegalStateException("the auth username is null in authorization");
        }
        Optional<Users> entity = userRepos.findByUsername(username);
        if(!entity.isPresent()) {
            throw new EntityNotFoundException("the user with username " + username+ " not found");
        }
        Users user = entity.get();
        return historyRepos.findHistoryByUser(user);
    }

    @Override
    public List<History> getHistoriesByBook(Long bookId) {
        Optional<Book> entity = bookRepos.findById(bookId);
        if(!entity.isPresent()) {
            throw new EntityNotFoundException("the book with id " + bookId + " not found");
        }
        Book book = entity.get();
        return historyRepos.findHistoryByBook(book);
    }

    @Override
    public List<History> getHistoriesByUser(Long userId) {
        Optional<Users> entity = userRepos.findById(userId);
        if(!entity.isPresent()) {
            throw new EntityNotFoundException("the user with id " + userId+ " not found");
        }
        Users user = entity.get();
        return historyRepos.findHistoryByUser(user);
    }

    @Override
    public History getHistoryById(Long id) {
       Optional<History> entity = historyRepos.findById(id);
       if(entity.isPresent()) {
        return entity.get();
       }
       throw new EntityNotFoundException("the history with id " + id + " not found");
    }

    @Override
    public List<History> getHistoryByUserAndBook(long userId, Long bookId) {
        Optional<Book> entity = bookRepos.findById(bookId);
        if(!entity.isPresent()) {
            throw new EntityNotFoundException("the book with id " + bookId + " not found");
        }
        Book book = entity.get();
        Optional<Users> userEntity = userRepos.findById(userId);
        if(!userEntity.isPresent()) {
            throw new EntityNotFoundException("the user with id " + userId+ " not found");
        }
        Users user = userEntity.get();
        return historyRepos.findByUserAndBook(user, book);
    }

    

}
