package logtest.casinobackend.controller;

import logtest.casinobackend.model.AuthUser;
import logtest.casinobackend.model.GameStory;
import logtest.casinobackend.repository.AuthUserRepository;
import logtest.casinobackend.repository.GameStoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class TestController {
    private AuthUserRepository authUserRepository;
    private GameStoryRepository gameStoryRepository;

    @GetMapping
    public String test() {
        return "test";
    }

//    @PostMapping
//    public ResponseEntity<?> createStory(Authentication authentication) {
//        String username = authentication.getName();
//        GameStory gameStory = new GameStory();
//
//        gameStory.setDate(new Date());
//        gameStory.setGameName("Ракетка");
//        gameStory.setBetAmount(new BigDecimal("100"));
//        gameStory.setWinAmount(new BigDecimal("-100"));
//        gameStory.setRateCoefficient(new BigDecimal("0.01"));
//        gameStory.setWinner(false);
//        gameStory.setAuthUser(authUserRepository.findByUsername(username).orElse(null));
//        if (gameStory.getAuthUser() == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//        gameStoryRepository.save(gameStory);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }

    @PostMapping("/findByUser")
    public ResponseEntity<?> findByUser(Authentication authentication) {
        AuthUser authUser = authUserRepository.findByUsername(authentication.getName()).orElse(null);
        if (authUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        List<GameStory> gameStories = gameStoryRepository.findByAuthUserId(authUser.getId());
        return ResponseEntity.status(HttpStatus.OK).body(gameStories);
    }
}
