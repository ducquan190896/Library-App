package libraymanage.practice.librarypracticeapp.Service.Implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import libraymanage.practice.librarypracticeapp.Entity.Message;
import libraymanage.practice.librarypracticeapp.Entity.Role;
import libraymanage.practice.librarypracticeapp.Entity.Users;
import libraymanage.practice.librarypracticeapp.Entity.Request.AnswerRequest;
import libraymanage.practice.librarypracticeapp.Entity.Request.QuestionRequest;
import libraymanage.practice.librarypracticeapp.Entity.Response.AnswerResponse;
import libraymanage.practice.librarypracticeapp.Entity.Response.QuestionResponse;
import libraymanage.practice.librarypracticeapp.Exception.AppIllegalStateException;
import libraymanage.practice.librarypracticeapp.Exception.EntityNotFoundException;
import libraymanage.practice.librarypracticeapp.Repository.MessageRepos;
import libraymanage.practice.librarypracticeapp.Repository.UserRepos;
import libraymanage.practice.librarypracticeapp.Service.MessageService;

@Service
public class MessageServiceIml implements MessageService {
    @Autowired
    MessageRepos messageRepos;
    @Autowired
    UserRepos userRepos;
    @Override
    public AnswerResponse addAnswer(AnswerRequest answerRequest) {
        Optional<Message> messageEntity = messageRepos.findById(answerRequest.getMessageId());
        if(!messageEntity.isPresent()) {
            throw new EntityNotFoundException("the message with id " + answerRequest.getMessageId() + " not found");
        }
        Message message = messageEntity.get();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(username == null) {
            throw new AppIllegalStateException("the user not found in authorization");
        }
        Optional<Users> adminEntity = userRepos.findByUsername(username);
        if(!adminEntity.isPresent()) {
            throw new EntityNotFoundException("the user with user name" + username +  " not found");
        }
        Users admin = adminEntity.get();
        if(!admin.getRole().getName().equals(Role.ADMIN.getName())) {
            throw new AppIllegalStateException("the role of auth user is not authorized to answer the question");
        }
        message.updateAnswer(answerRequest.getAnswer(), admin, LocalDateTime.now());
        messageRepos.save(message);
        
        Users user = message.getUser();
        user.getMessagesUser().stream().map(mess -> mess.getId() == message.getId() ? message : mess).collect(Collectors.toList());
        userRepos.save(user);

        admin.getMessagesAdmin().stream().map(mess -> mess.getId() == message.getId() ? message : mess).collect(Collectors.toList());
        userRepos.save(admin);
        
        return new AnswerResponse(message.getId(), message.getTitle(), message.getQuestion(), user.getId(), user.getUsername(), message.getQuestionTime(), message.getAnswer(), admin.getId(), admin.getUsername(), message.getAnswerTime(), message.getClosed());
    }
    @Override
    public QuestionResponse createQuestion(QuestionRequest questionRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(username == null) {
            throw new AppIllegalStateException("the user not found in authorization");
        }
        Optional<Users> userEntity = userRepos.findByUsername(username);
        if(!userEntity.isPresent()) {
            throw new EntityNotFoundException("the user with user name" + username +  " not found");
        }
        Users user = userEntity.get();
        Message message = new Message(questionRequest.getTitle(), user, questionRequest.getQuestion(), LocalDateTime.now());
        messageRepos.save(message);
        user.getMessagesUser().add(message);
        userRepos.save(user);

        return new QuestionResponse(message.getId(), message.getTitle(), message.getQuestion(), user.getId(), username, message.getQuestionTime(), message.getClosed());
    }
    @Override
    public void deleteMessage(Long id) {
        Optional<Message> entity = messageRepos.findById(id);
      if(!entity.isPresent()) {
        throw new EntityNotFoundException("the message with id " + id + " not found");
    }
      messageRepos.deleteById(id);
        
    }
    @Override
    public List<AnswerResponse> getClosedMessages() {
        return messageRepos.findMessageByClosed(true).stream().map(message -> new AnswerResponse(message.getId(), message.getTitle(), message.getQuestion(), message.getUser().getId(), message.getUser().getUsername(), message.getQuestionTime(), message.getAnswer(), message.getAdmin().getId(), message.getAdmin().getUsername(), message.getAnswerTime(), message.getClosed())).collect(Collectors.toList());
    }
    @Override
    public List<AnswerResponse> getClosedMessagesByAuthUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(username == null) {
            throw new AppIllegalStateException("the user not found in authorization");
        }
        Optional<Users> userEntity = userRepos.findByUsername(username);
        if(!userEntity.isPresent()) {
            throw new EntityNotFoundException("the user with user name" + username +  " not found");
        }
        Users user = userEntity.get();
        return messageRepos.findMessageByUserAndClosed(user, true).stream().map(message -> new AnswerResponse(message.getId(), message.getTitle(), message.getQuestion(), message.getUser().getId(), message.getUser().getUsername(), message.getQuestionTime(), message.getAnswer(), message.getAdmin().getId(), message.getAdmin().getUsername(), message.getAnswerTime(), message.getClosed())).collect(Collectors.toList());
    }
    @Override
    public AnswerResponse getMessageById(Long id) {
      Optional<Message> entity = messageRepos.findById(id);
      if(!entity.isPresent()) {
        throw new EntityNotFoundException("the message with id " + id + " not found");
    }
        Message message = entity.get();
         if(message.getClosed() == true) {
            return new AnswerResponse(message.getId(), message.getTitle(), message.getQuestion(), message.getUser().getId(), message.getUser().getUsername(), message.getQuestionTime(), message.getAnswer(), message.getAdmin().getId(), message.getAdmin().getUsername(), message.getAnswerTime(), message.getClosed());
         } 
         return new AnswerResponse(message.getId(), message.getTitle(), message.getQuestion(), message.getUser().getId(), message.getUser().getUsername(), message.getQuestionTime(),null, 0L, null, null, message.getClosed());
    }
    @Override
    public List<AnswerResponse> getMessages() {
       return null;
    }
    @Override
    public List<QuestionResponse> getOpenMessages() {
       return messageRepos.findMessageByClosed(false).stream().map(message -> new QuestionResponse(message.getId(), message.getTitle(), message.getQuestion(), message.getUser().getId(), message.getUser().getUsername(), message.getQuestionTime(), message.getClosed())).collect(Collectors.toList());
    }
    @Override
    public List<QuestionResponse> getOpenMessagesByAuthUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(username == null) {
            throw new AppIllegalStateException("the user not found in authorization");
        }
        Optional<Users> userEntity = userRepos.findByUsername(username);
        if(!userEntity.isPresent()) {
            throw new EntityNotFoundException("the user with user name" + username +  " not found");
        }
        Users user = userEntity.get();
        return messageRepos.findMessageByUserAndClosed(user, false).stream().map(message -> new QuestionResponse(message.getId(), message.getTitle(), message.getQuestion(), message.getUser().getId(), message.getUser().getUsername(), message.getQuestionTime(), message.getClosed())).collect(Collectors.toList());
    }

    
}
