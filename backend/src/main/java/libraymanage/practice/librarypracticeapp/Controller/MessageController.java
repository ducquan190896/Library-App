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

import libraymanage.practice.librarypracticeapp.Entity.Request.AnswerRequest;
import libraymanage.practice.librarypracticeapp.Entity.Request.QuestionRequest;
import libraymanage.practice.librarypracticeapp.Entity.Response.AnswerResponse;
import libraymanage.practice.librarypracticeapp.Entity.Response.QuestionResponse;
import libraymanage.practice.librarypracticeapp.Repository.MessageRepos;
import libraymanage.practice.librarypracticeapp.Service.MessageService;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    @Autowired
    MessageService messageService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/openMessages")
    public ResponseEntity<List<QuestionResponse>> getOpenMessages() {
        return new ResponseEntity<List<QuestionResponse>>(messageService.getOpenMessages(), HttpStatus.OK);
    }
    @GetMapping("/authUser/openMessages")
    public ResponseEntity<List<QuestionResponse>> getOpenMessagesByAuthUser() {
        return new ResponseEntity<List<QuestionResponse>>(messageService.getOpenMessagesByAuthUser(), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/closedMessages")
    public ResponseEntity<List<AnswerResponse>> getClosedMessages() {
        return new ResponseEntity<List<AnswerResponse>>(messageService.getClosedMessages(), HttpStatus.OK);
    }
    @GetMapping("/authUser/closedMessages")
    public ResponseEntity<List<AnswerResponse>> getClosedMessagesByAuthUser() {
        return new ResponseEntity<List<AnswerResponse>>(messageService.getClosedMessagesByAuthUser(), HttpStatus.OK);
    }
    @PostMapping("/addQuestion")
    public ResponseEntity<QuestionResponse> createQuestion(@Valid @RequestBody QuestionRequest questionRequest) {
        return new ResponseEntity<QuestionResponse>(messageService.createQuestion(questionRequest), HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/addAnswer")
    public ResponseEntity<AnswerResponse> addAnswer(@Valid @RequestBody AnswerRequest answerRequest) {
        return new ResponseEntity<AnswerResponse>(messageService.addAnswer(answerRequest), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<AnswerResponse> getMessageById(@PathVariable Long id) {
        return new ResponseEntity<AnswerResponse>(messageService.getMessageById(id), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/id/{id}")
    public ResponseEntity<HttpStatus> deleteMessageById(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return new ResponseEntity<HttpStatus>( HttpStatus.OK);
    }
}
