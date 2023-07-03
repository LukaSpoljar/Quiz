package hr.tvz.project.quizbackend.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.tvz.project.quizbackend.domain.PlayerDTO;
import hr.tvz.project.quizbackend.domain.RegisterForm;
import hr.tvz.project.quizbackend.entity.PlayerDB;
import hr.tvz.project.quizbackend.entity.PlayerResponse;
import hr.tvz.project.quizbackend.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;


import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayerControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PlayerService playerService;

    @Test
    public void testAllOk() throws Exception{
        mvc.perform(get("/player/test"))
                .andExpect(status().isOk())
                .andExpect(content().string("All is OK"));
    }
    @Test
    public void testGetAll() throws Exception{
        List<PlayerDB> playerDBList= Arrays.asList(new PlayerDB(), new PlayerDB(), new PlayerDB());
        when(playerService.getAllPlayers()).thenReturn(playerDBList);

        mvc.perform(get("/player/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",hasSize(playerDBList.size())));
    }
    @Test
    public void testGetPlayerWithValidId()throws Exception{

        String TEST_ID = "1";
        PlayerDB player = new PlayerDB();
        PlayerDTO expectedPlayerDTO = new PlayerDTO(player);
        when(playerService.getPlayer(anyLong())).thenReturn(Optional.of(player));

        mvc.perform(get("/player/" + TEST_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.uuid",is(expectedPlayerDTO.getUuid())));
    }
    @Test
    public void testGetPlayerWithInvalidId()throws Exception{
        mvc.perform(get("/player/10"))
                .andExpect(status().isNotFound());
    }
    @DirtiesContext
    @Test
    public void testCreatePlayerValidRequest()throws Exception{
        String TEST_NAME = "marin";
        String TEST_PASSWORD = "aB#123456";
        String TEST_BOT = "";

        RegisterForm validRegisterForm = new RegisterForm(TEST_NAME,TEST_PASSWORD,TEST_BOT);
        when(playerService.validateNewPlayer(anyString(),anyString(),anyString())).thenReturn(PlayerResponse.OK);

        PlayerDB createdPlayerDB = new PlayerDB();
        when(playerService.createPlayer(anyString(), anyString())).thenReturn(Optional.of(createdPlayerDB));


        mvc.perform(post("/player/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRegisterForm))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(createdPlayerDB.getUsername()))
                .andExpect(jsonPath("$.uuid").value(createdPlayerDB.getUuid()));
    }
    @Test
    public void testCreatePlayerWithInvalidUsername() throws Exception {
        String TEST_PASSWORD = "aB#123456";
        String TEST_BOT = "";
        RegisterForm invalidRegisterForm = new RegisterForm(null, TEST_PASSWORD, TEST_BOT);

        when(playerService.validateNewPlayer(anyString(), anyString(), anyString())).thenReturn(PlayerResponse.USERNAME_INVALID);

        mvc.perform(post("/player/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRegisterForm)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid request"));

    }
    @Test
    public void testCreatePlayerWithInvalidPassword() throws Exception {
        String TEST_NAME = "marin";
        String TEST_BOT = "";
        RegisterForm invalidRegisterForm = new RegisterForm(TEST_NAME, null, TEST_BOT);

        when(playerService.validateNewPlayer(anyString(), anyString(), anyString())).thenReturn(PlayerResponse.PASSWORD_INVALID);

        mvc.perform(post("/player/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRegisterForm)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid request"));

    }
    @Test
    public void testCreatePlayerWithInvalidEmail() throws Exception {
        String TEST_NAME = "marin";
        String TEST_PASSWORD = "aB#123456";
        String TEST_BOT = "bot";
        RegisterForm invalidRegisterForm = new RegisterForm(TEST_NAME, TEST_PASSWORD, TEST_BOT);

        when(playerService.validateNewPlayer(anyString(), anyString(), anyString())).thenReturn(PlayerResponse.PASSWORD_INVALID);

        mvc.perform(post("/player/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRegisterForm)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid request"));

    }
    @DirtiesContext
    @Test
    public void testDeletePlayerWithExistingUsername() throws Exception {
        String existingUsername = "username";

        when(playerService.deletePlayer(existingUsername)).thenReturn(true);

        mvc.perform(delete("/player/{username}", existingUsername)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
    @Test
    public void testDeletePlayerWithInvalidUsername() throws Exception {
        String existingUsername = "username";

        when(playerService.deletePlayer(existingUsername)).thenReturn(false);

        mvc.perform(delete("/player/{username}", existingUsername)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotImplemented());
    }
    @DirtiesContext
    @Test
    public void testUpdatePlayerWithValidRequest() throws Exception {
        RegisterForm validRegisterForm = new RegisterForm("username", "newPassword", "email");

        PlayerDB updatedPlayerDB = new PlayerDB();
        when(playerService.updatePassword(anyString(), anyString())).thenReturn(Optional.of(updatedPlayerDB));

        mvc.perform(put("/player/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRegisterForm)))
                .andExpect(status().isAccepted());
    }
    @Test
    public void testUpdatePlayerWithInvalidRequest() throws Exception {
        RegisterForm invalidRegisterForm = new RegisterForm(null, null, null);

        when(playerService.updatePassword(anyString(), anyString())).thenReturn(Optional.empty());

        mvc.perform(put("/player/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRegisterForm)))
                .andExpect(status().isNotFound());
    }
    @Test
    public void testLoginPlayerWithValidCredentials() throws Exception {
        RegisterForm validRegisterForm = new RegisterForm("username", "password", "email");

        PlayerDB playerDB = new PlayerDB();
        when(playerService.login(anyString(), anyString())).thenReturn(Optional.of(playerDB));

        mvc.perform(post("/player/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRegisterForm)))
                .andExpect(status().isOk())
                .andExpect(content().string(playerDB.getUuid()));
    }
    @Test
    public void testLoginPlayerWithInvalidCredentials() throws Exception {
        RegisterForm invalidRegisterForm = new RegisterForm("username", "password", "email");

        when(playerService.login(anyString(), anyString())).thenReturn(Optional.empty());

        mvc.perform(post("/player/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRegisterForm)))
                .andExpect(status().isNotFound());
    }

}
