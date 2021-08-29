package com.exam.service.impl;

import com.exam.model.MarksDetails;
import com.exam.model.ResponseWrapper;
import com.exam.model.StatusDescriptionModel;
import com.exam.model.User;
import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;
import com.exam.repo.QuestionRepository;
import com.exam.repo.UserRepository;
import com.exam.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender sender;

    @Override
    public Question addQuestion(Question question) {
        return this.questionRepository.save(question);
    }

    @Override
    public Question updateQuestion(Question question) {
        return this.questionRepository.save(question);
    }

    @Override
    public Set<Question> getQuestions() {
        return new HashSet<>(this.questionRepository.findAll());
    }

    @Override
    public Question getQuestion(Long questionId) {
        return this.questionRepository.findById(questionId).get();
    }

    @Override
    public Set<Question> getQuestionsOfQuiz(Quiz quiz) {
        return this.questionRepository.findByQuiz(quiz);
    }

    @Override
    public void deleteQuestion(Long quesId) {
        Question question = new Question();
        question.setQuesId(quesId);
        this.questionRepository.delete(question);
    }

    @Override
    public Question get(Long questionsId) {
       return this.questionRepository.getOne(questionsId);
    }

	@Override
	public ResponseWrapper sendEmail(String username, MarksDetails marks) {
		
		ResponseWrapper response= new ResponseWrapper();
		StatusDescriptionModel statusDescription= new StatusDescriptionModel();
		response.setStatusDescription(statusDescription);
		
		User user= userRepository.findByUsername(username);
		
		try {
			this.sendMail(user,marks);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		response.getStatusDescription().setStatusMessage("Mail send on your email id kindly check");;
		
		return response;
		
		
		
	}
	public String sendMail(User user,MarksDetails marks) {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(user.getEmail());
            String html = "<!doctype html>\n" +
                    "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\"\n" +
                    "      xmlns:th=\"http://www.thymeleaf.org\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\"\n" +
                    "          content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">\n" +
                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                    "    <title>Email</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<div>Congratulations <b>" + user.getFirstName() + "</b></div>\n" +
                    "\n" +
                    "<div> Your Attempt is <b>" + marks.getAttempted() + "</b></div>\n" +
                    "<div> Your correct answer is <b>" + marks.getCorrectAnswers() + "</b></div>\n" +
                    "<div> You have got marks is <b>" + marks.getMarksGot() + "</b></div>\n" +
                    "</body>\n" +
                    "</html>\n";
            helper.setText(html,true);
            helper.setSubject("Result");
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error while sending mail ..";
        }
        sender.send(message);
        return "Mail Sent Success!";
    }


	@Override
	public List<Question> getQuestionDetails() {
	
		ResponseWrapper response= new ResponseWrapper();
		StatusDescriptionModel statusDescription= new StatusDescriptionModel();
		response.setStatusDescription(statusDescription);
	
		List<Question>ques= this.questionRepository.findAll();
       
		return ques;
	}
	
}
