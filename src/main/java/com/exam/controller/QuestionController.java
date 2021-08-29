package com.exam.controller;

import com.exam.model.MarksDetails;
import com.exam.model.ResponseWrapper;
import com.exam.model.StatusDescriptionModel;
import com.exam.model.StudentResult;
import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;
import com.exam.service.QuestionService;
import com.exam.service.QuizService;
import com.exam.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;
import javax.servlet.http.HttpServlet;

import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/question")
public class QuestionController {
	@Autowired
	private QuestionService service;

	@Autowired
	private QuizService quizService;
	
	@Autowired
	private UserService userService;

	/**
	 * // add question
	 * 
	 * @param question
	 * @return
	 */
	
	Map<String,StudentResult> map=new HashMap<String,StudentResult>();
	@PostMapping("/")
	public ResponseEntity<Question> add(@RequestBody Question question) {
		return ResponseEntity.ok(this.service.addQuestion(question));
	}

	/**
	 * // update the question
	 * 
	 * @param question
	 * @return
	 */

	@PutMapping("/")
	public ResponseEntity<Question> update(@RequestBody Question question) {
		return ResponseEntity.ok(this.service.updateQuestion(question));
	}

	/**
	 * // get all question of any quid
	 * 
	 * @param qid
	 * @return
	 */
	@GetMapping("/quiz/{qid}")
	public ResponseEntity<?> getQuestionsOfQuiz(@PathVariable("qid") Long qid) {
//        Quiz quiz = new Quiz();
//        quiz.setqId(qid);
//        Set<Question> questionsOfQuiz = this.service.getQuestionsOfQuiz(quiz);
//        return ResponseEntity.ok(questionsOfQuiz);

		Quiz quiz = this.quizService.getQuiz(qid);
		Set<Question> questions = quiz.getQuestions();
		List list = new ArrayList(questions);
		if (list.size() > Integer.parseInt(quiz.getNumberOfQuestions())) {
			list = list.subList(0, Integer.parseInt(quiz.getNumberOfQuestions() + 1));
		}
		Collections.shuffle(list);
		return ResponseEntity.ok(list);

	}

	/**
	 * get questioin wise id
	 * 
	 * @param qid
	 * @return
	 */
	@GetMapping("/quiz/all/{qid}")
	public ResponseEntity<?> getQuestionsOfQuizAdmin(@PathVariable("qid") Long qid) {
		Quiz quiz = new Quiz();
		quiz.setqId(qid);
		Set<Question> questionsOfQuiz = this.service.getQuestionsOfQuiz(quiz);
		return ResponseEntity.ok(questionsOfQuiz);

//        return ResponseEntity.ok(list);

	}

	/**
	 * // get Question number and Question
	 * 
	 * @return
	 */

	@GetMapping(value = "/quiz/question/details")
	public ResponseEntity<?> getQuestions() {

		ResponseWrapper response = new ResponseWrapper();
		StatusDescriptionModel statusDescription = new StatusDescriptionModel();
		response.setStatusDescription(statusDescription);

		List<Question> quiz = this.service.getQuestionDetails();

		return new ResponseEntity<>(quiz, HttpStatus.OK);

	}

	/**
	 * // get single question
	 * 
	 * @param quesId
	 * @return
	 */
	@GetMapping("/{quesId}")
	public Question get(@PathVariable("quesId") Long quesId) {
		return this.service.getQuestion(quesId);
	}

	/**
	 * // delete question
	 * 
	 * @param quesId
	 */

	@DeleteMapping("/{quesId}")
	public void delete(@PathVariable("quesId") Long quesId) {
		this.service.deleteQuestion(quesId);
	}

	/**
	 * // eval quiz
	 * 
	 * @param questions
	 * @return
	 */
	@PostMapping("/eval-quiz")
	public ResponseEntity<?> evalQuiz(@RequestBody List<Question> questions) {
		System.out.println(questions);
		int marksGot = 0;
		int correctAnswers = 0;
		int attempted = 0;
		for (Question q : questions) {
			// single questions
			Question question = this.service.get(q.getQuesId());
			if (question.getAnswer().equals(q.getGivenAnswer())) {
				// correct
				correctAnswers++;

				double marksSingle = Double.parseDouble(questions.get(0).getQuiz().getMaxMarks()) / questions.size();
				// this.questions[0].quiz.maxMarks / this.questions.length;
				marksGot += marksSingle;

			}

			if (q.getGivenAnswer() != null) {
				attempted++;
			}

		}
		;

		 StudentResult result=new StudentResult();
		 result.setMarksGot(marksGot);
		 result.setCorrectAnswers(correctAnswers);
         result.setAttempted(attempted);
	     map.put("Student_Sheet", result);
//		Map<String, Object> map = Map.of("marksGot", marksGot, "correctAnswers", correctAnswers, "attempted",
//				attempted);
		return ResponseEntity.ok(map);

	}

	/**
	 * send marks on mail
	 * 
	 * @param username
	 * @param marks
	 * @return
	 */

	@PostMapping(value = "/v1/email/send/{username}")
	public ResponseEntity<ResponseWrapper> SendEmail(@PathVariable String username, @RequestBody MarksDetails marks) {

		StatusDescriptionModel statusDescription = new StatusDescriptionModel();
		ResponseWrapper response = new ResponseWrapper();
		response.setStatusDescription(statusDescription);

		response = this.service.sendEmail(username, marks);

		return new ResponseEntity<ResponseWrapper>(response, HttpStatus.OK);

	}
	
	 /*
     * Forgot password api
     */
	
	@PostMapping(value="/forgotPas/{email}")
    public ResponseEntity<StatusDescriptionModel> userForgetPass(@PathVariable("email") String email)
    {
    	
    	System.out.println("getUserMail is"+email);
    	StatusDescriptionModel forgetPasOtp=userService.forgetPass(email);
    	
		return new ResponseEntity<StatusDescriptionModel>(forgetPasOtp,HttpStatus.OK);
    	
    
    }

}
