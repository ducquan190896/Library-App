package libraymanage.practice.librarypracticeapp.Service;

import java.util.List;

import libraymanage.practice.librarypracticeapp.Entity.Message;
import libraymanage.practice.librarypracticeapp.Entity.Request.AnswerRequest;
import libraymanage.practice.librarypracticeapp.Entity.Request.QuestionRequest;
import libraymanage.practice.librarypracticeapp.Entity.Response.AnswerResponse;
import libraymanage.practice.librarypracticeapp.Entity.Response.QuestionResponse;

public interface MessageService {
    List<AnswerResponse> getMessages();
    List<AnswerResponse> getClosedMessages();
    List<QuestionResponse> getOpenMessages();
    List<AnswerResponse> getClosedMessagesByAuthUser();
    List<QuestionResponse> getOpenMessagesByAuthUser();
    AnswerResponse getMessageById(Long id);
    QuestionResponse createQuestion(QuestionRequest questionRequest);
    AnswerResponse addAnswer(AnswerRequest answerRequest);
    void deleteMessage(Long id);
    
}
