package com.junak.scorekeeper.rest;

import static org.assertj.core.api.Assertions.assertThat;

import com.junak.scorekeeper.ScorekeeperApplication;
import com.junak.scorekeeper.dao.PlayerRepository;
import com.junak.scorekeeper.entity.Player;
import com.junak.scorekeeper.service.interfaces.PlayerService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ScorekeeperApplication.class)
@AutoConfigureMockMvc
public class PlayerRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerService playerService;

    @After
    public void resetDb() {
        playerRepository.deleteAll();
    }

    @Test
    public void whenValidInput_thenCreateEmployee() throws IOException, Exception {
        Player testPlayer = new Player();
        testPlayer.setFirstName("Bob");

        mockMvc.perform(post("/api/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(testPlayer)));

        List<Player> found = playerService.findAll();
        assertThat(found).extracting(Player::getFirstName).contains("Bob");
    }
}
















