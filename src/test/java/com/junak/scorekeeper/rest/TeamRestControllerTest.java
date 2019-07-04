package com.junak.scorekeeper.rest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.junak.scorekeeper.ScorekeeperApplication;
import com.junak.scorekeeper.dao.GameRepository;
import com.junak.scorekeeper.dao.TeamRepository;
import com.junak.scorekeeper.entity.Game;
import com.junak.scorekeeper.entity.Team;
import com.junak.scorekeeper.service.interfaces.GameService;
import com.junak.scorekeeper.service.interfaces.TeamService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ScorekeeperApplication.class)
//@AutoConfigureMockMvc
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DatabaseSetup("classpath:team-datasets.xml")
public class TeamRestControllerTest {

    @Autowired
    private TestEntityManager entityManager;

//    @Autowired
//    private MockMvc mockMvc;

    @Autowired
    private TeamRepository teamRepository;

//    @Autowired
//    private TeamService teamService;

    @Test
    public void getTeamTest() throws IOException, Exception{
//        mockMvc.perform(post("/api/teams/1")
//                .contentType(MediaType.APPLICATION_JSON));
//
//        Team theTeam = teamService.findById(1);
//        assertThat(theTeam.getTeamNameLong()).contains("Avengers");

        Team theTeam = teamRepository.getOne(1);
        assertThat(theTeam.getTeamNameLong(), is(equalTo("Avengers")));

    }
}
