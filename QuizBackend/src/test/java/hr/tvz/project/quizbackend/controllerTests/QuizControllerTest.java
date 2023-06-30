package hr.tvz.project.quizbackend.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.tvz.project.quizbackend.domain.*;
import hr.tvz.project.quizbackend.entity.CategoryDB;
import hr.tvz.project.quizbackend.entity.PlayerDB;
import hr.tvz.project.quizbackend.entity.QuestionDB;
import hr.tvz.project.quizbackend.entity.QuizDB;
import hr.tvz.project.quizbackend.service.QuizService;
import hr.tvz.project.quizbackend.service.ResultService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class QuizControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private QuizService quizService;
    @MockBean
    private ResultService quizResultService;

    @Test
    public void testGetAllQuizzes()throws Exception{
        Long TEST_ID = 1L;
        String TEST_NAME = "alphaQuiz";
        PlayerDB TEST_AUTHOR = new PlayerDB();
        TEST_AUTHOR.setUsername("štef");
        CategoryDB TEST_CATEGORY = new CategoryDB();
        List<QuestionDB> questionDBList = new ArrayList<>();

        Long TEST_ID2 = 2L;
        String TEST_NAME2 = "betaQuiz";
        PlayerDB TEST_AUTHOR2 = new PlayerDB();
        TEST_AUTHOR.setUsername("ivan");
        CategoryDB TEST_CATEGORY2 = new CategoryDB();
        List<QuestionDB> questionDBList2 = new ArrayList<>();


        List<QuizDB> quizDBList = new ArrayList<>();
        QuizDB quiz1 = new QuizDB();
        quiz1.setId(TEST_ID);
        quiz1.setName(TEST_NAME);
        quiz1.setAuthor(TEST_AUTHOR);
        quiz1.setCategory(TEST_CATEGORY);
        quiz1.setQuestions(questionDBList);

        QuizDB quiz2 = new QuizDB();
        quiz2.setId(TEST_ID2);
        quiz2.setName(TEST_NAME2);
        quiz2.setAuthor(TEST_AUTHOR2);
        quiz2.setCategory(TEST_CATEGORY2);
        quiz2.setQuestions(questionDBList2);

        quizDBList.add(quiz1);
        quizDBList.add(quiz2);

        when(quizService.getAllQuizzes()).thenReturn(quizDBList);

        List<QuizBasicDTO> expectedQuizDTOList = quizDBList.stream().map(quiz -> new QuizBasicDTO(quiz)).toList();

        mvc.perform(get("/quiz").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$",hasSize(2)));
    }

    @Test
    public void testGetQuizWithExistingId() throws Exception {
        Long TEST_ID = 1L;
        String TEST_NAME = "alphaQuiz";
        PlayerDB TEST_AUTHOR = new PlayerDB();
        TEST_AUTHOR.setUsername("štef");
        CategoryDB TEST_CATEGORY = new CategoryDB();
        List<QuestionDB> questionDBList = new ArrayList<>();

        QuizDB quizDB = new QuizDB();
        quizDB.setId(TEST_ID);
        quizDB.setName(TEST_NAME);
        quizDB.setAuthor(TEST_AUTHOR);
        quizDB.setCategory(TEST_CATEGORY);
        quizDB.setQuestions(questionDBList);


        when(quizService.getQuiz(TEST_ID)).thenReturn(Optional.of(quizDB));

        QuizDTO expectedQuizDTO = new QuizDTO(quizDB);
        mvc.perform(get("/quiz/" + TEST_ID)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name",is(expectedQuizDTO.getName())))
                .andExpect(jsonPath("$.author",is(expectedQuizDTO.getAuthor())))
                .andExpect(jsonPath("$.uuid",is(expectedQuizDTO.getUuid())))
                .andExpect(jsonPath("$.questionCount",is(expectedQuizDTO.getQuestionCount())))
                .andExpect(jsonPath("$.questions",is(expectedQuizDTO.getQuestions())));

    }

    @Test
    public void testGetQuizWithNonExistingId() throws Exception {
        Long nonExistingId = 2L;

        when(quizService.getQuiz(nonExistingId)).thenReturn(Optional.empty());

        mvc.perform(get("/quiz/" + nonExistingId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("QUIZ_NOT_FOUND"));

    }

    @Test
    public void testGetCategories() throws Exception {

        CategoryDB category1 = new CategoryDB();
        category1.setId(1L);
        category1.setName("General knowledge");

        CategoryDB category2 = new CategoryDB();
        category2.setId(2L);
        category2.setName("Scientific knowledge");

        List<CategoryDB> categories = Arrays.asList(category1, category2);

        when(quizService.getCategories()).thenReturn(categories);

        mvc.perform(get("/quiz/category")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(categories.size()));
    }

    @Test
    public void testGetResults() throws Exception {
        Long quizId = 1L;

        QuizResultsCollectionDTO resultsCollection = new QuizResultsCollectionDTO();

        when(quizResultService.getByQuizId(quizId)).thenReturn(resultsCollection);

        mvc.perform(get("/quiz/"+quizId+"/results")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.quizId").value(resultsCollection.getQuizId()))
                .andExpect(jsonPath("$.quizName").value(resultsCollection.getQuizName()))
                .andExpect(jsonPath("$.scoreboard").value(resultsCollection.getScoreboard()));
    }

    @Test
    public void testCreateQuizWithAuthorError() throws Exception {
        CreateQuizForm createQuizForm = new CreateQuizForm();

        CreateQuizResponse createResponse = new CreateQuizResponse(Optional.of("AUTHOR_NOT_FOUND"),Optional.empty());
        when(quizService.createQuiz(createQuizForm)).thenReturn(createResponse);

        mvc.perform(post("/quiz/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createQuizForm))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        }
    @Test
    public void testCreateQuizWithCategoryError() throws Exception {
        CreateQuizForm createQuizForm = new CreateQuizForm();

        CreateQuizResponse createResponse = new CreateQuizResponse(Optional.of("CATEGORY_NOT_FOUND"),Optional.empty());
        when(quizService.createQuiz(createQuizForm)).thenReturn(createResponse);

        mvc.perform(post("/quiz/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createQuizForm))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @DirtiesContext
    @Test
    public void testCreateQuizWithValidQuiz() throws Exception {
        String TEST_NAME = "alphaQuiz";
        PlayerDB TEST_AUTHOR = new PlayerDB();
        TEST_AUTHOR.setUsername("štef");
        CategoryDB TEST_CATEGORY = new CategoryDB();
        List<QuestionDB> questionDBList = new ArrayList<>();

        QuizDB expectedQuizDB = new QuizDB(TEST_NAME, TEST_AUTHOR,TEST_CATEGORY, questionDBList);

        Long TEST_AUTHOR_ID = 1L;
        Long TEST_CATEGORY_ID = 1L;
        List<CreateQuizQuestion> TEST_QUESTIONS = new ArrayList<>();
        CreateQuizForm createQuizForm = new CreateQuizForm(TEST_NAME, TEST_AUTHOR_ID, TEST_CATEGORY_ID, TEST_QUESTIONS);

        CreateQuizResponse createResponse = new CreateQuizResponse(Optional.empty(),Optional.of(expectedQuizDB));
        when(quizService.createQuiz(createQuizForm)).thenReturn(createResponse);

        mvc.perform(post("/quiz/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createQuizForm))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(createResponse.getQuiz().get().getName()))
                /*.andExpect(jsonPath("$.author").value(createResponse.getQuiz().get().getAuthor().getUsername()))*/;

    }

    @Test
    public void testSolveQuizNoQuiz() throws Exception {

        String TEST_AUTHOR_UUID = "6e48c18a-72c4-4a63-9098-5069e07ff966";
        String TEST_QUIZ_UUID = "0edb2d28-7d47-4a26-80b7-53465b5af2a6";
        List<SolveQuizAnswer> TEST_QUIZ_ANSWERS = new ArrayList<>();
        SolveQuizForm solveQuizForm = new SolveQuizForm(TEST_AUTHOR_UUID,TEST_QUIZ_UUID,TEST_QUIZ_ANSWERS);

        SolveQuizResponse solveResponse = new SolveQuizResponse(Optional.of("QUIZ_NOT_FOUND"),Optional.empty());

        when(quizResultService.solveQuiz(solveQuizForm)).thenReturn(solveResponse);

        mvc.perform(post("/quiz/solve")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(solveQuizForm))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                /*.andExpect(jsonPath("$").value(solveResponse.getError().toString()))*/;
    }

    @Test
    public void testSolveQuizNoPlayer() throws Exception {

        String TEST_AUTHOR_UUID = "6e48c18a-72c4-4a63-9098-5069e07ff966";
        String TEST_QUIZ_UUID = "0edb2d28-7d47-4a26-80b7-53465b5af2a6";
        List<SolveQuizAnswer> TEST_QUIZ_ANSWERS = new ArrayList<>();
        SolveQuizForm solveQuizForm = new SolveQuizForm(TEST_AUTHOR_UUID,TEST_QUIZ_UUID,TEST_QUIZ_ANSWERS);

        SolveQuizResponse solveResponse = new SolveQuizResponse(Optional.of("PLAYER_NOT_FOUND"),Optional.empty());

        when(quizResultService.solveQuiz(solveQuizForm)).thenReturn(solveResponse);

        mvc.perform(post("/quiz/solve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(solveQuizForm))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSolveQuizSolved() throws Exception {

        String TEST_AUTHOR_UUID = "6e48c18a-72c4-4a63-9098-5069e07ff966";
        String TEST_QUIZ_UUID = "0edb2d28-7d47-4a26-80b7-53465b5af2a6";
        List<SolveQuizAnswer> TEST_QUIZ_ANSWERS = new ArrayList<>();
        SolveQuizForm solveQuizForm = new SolveQuizForm(TEST_AUTHOR_UUID,TEST_QUIZ_UUID,TEST_QUIZ_ANSWERS);

        SolveQuizResponse solveResponse = new SolveQuizResponse(Optional.of("QUIZ_ALREADY_SOLVED"),Optional.empty());

        when(quizResultService.solveQuiz(solveQuizForm)).thenReturn(solveResponse);

        mvc.perform(post("/quiz/solve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(solveQuizForm))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}

