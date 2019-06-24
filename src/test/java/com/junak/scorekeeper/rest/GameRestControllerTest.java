package com.junak.scorekeeper.rest;

import static org.assertj.core.api.Assertions.assertThat;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.junak.scorekeeper.ScorekeeperApplication;
import com.junak.scorekeeper.dao.GameRepository;
import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.service.interfaces.GameService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ScorekeeperApplication.class)
@AutoConfigureMockMvc
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DatabaseSetup("classpath:/test-datasets.xml")
public class GameRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameService gameService;

    @Test
    public void getGameTest() throws IOException, Exception{
        mockMvc.perform(post("/api/games/1")
                .contentType(MediaType.APPLICATION_JSON));

        Game theGame = gameService.findById(1);
        assertThat(theGame.getHomeTeam().getTeamNameLong()).contains("Avengers");

    }


}