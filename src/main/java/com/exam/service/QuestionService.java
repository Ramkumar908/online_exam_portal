package com.exam.service;

import com.exam.model.MarksDetails;
import com.exam.model.ResponseWrapper;
import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;

import java.util.List;
import java.util.Set;

public interface QuestionService {

    public Question addQuestion(Question question);

    public Question updateQuestion(Question question);

    public Set<Question> getQuestions();

    public Question getQuestion(Long questionId);

    public Set<Question> getQuestionsOfQuiz(Quiz quiz);

    public void deleteQuestion(Long quesId);

    public Question get(Long questionsId);

	public ResponseWrapper sendEmail(String username, MarksDetails marks);

	public List<Question> getQuestionDetails();



}
