package libraymanage.practice.librarypracticeapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import libraymanage.practice.librarypracticeapp.Entity.History;
import libraymanage.practice.librarypracticeapp.Service.HistoryService;

@RestController
@RequestMapping("/api/histories")
public class HistoryController {
    @Autowired
    HistoryService historyService;
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<History>> getAll() {
        return new ResponseEntity<List<History>>(historyService.getHistories(), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<History>> getAllByUser(@PathVariable Long userId) {
        return new ResponseEntity<List<History>>(historyService.getHistoriesByUser(userId), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<History>> getAllByBook(@PathVariable Long bookId) {
        return new ResponseEntity<List<History>>(historyService.getHistoriesByBook(bookId), HttpStatus.OK);
    }

    @GetMapping("/authUser")
    public ResponseEntity<List<History>> getAllByAuthUser() {
        return new ResponseEntity<List<History>>(historyService.getHistoriesByAuthUser(), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/book/{bookId}/user/{userId}")
    public ResponseEntity<List<History>> getAllByBookAndUser(@PathVariable Long bookId, @PathVariable Long userId) {
        return new ResponseEntity<List<History>>(historyService.getHistoryByUserAndBook(userId, bookId), HttpStatus.OK);
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<History> getById(@PathVariable Long id) {
        return new ResponseEntity<History>(historyService.getHistoryById(id), HttpStatus.OK);
    }
}
