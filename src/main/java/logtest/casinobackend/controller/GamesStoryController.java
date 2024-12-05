package logtest.casinobackend.controller;

import logtest.casinobackend.model.AuthUser;
import logtest.casinobackend.model.GameStory;
import logtest.casinobackend.repository.AuthUserRepository;
import logtest.casinobackend.repository.GameStoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/games/story")
@AllArgsConstructor
public class GamesStoryController {
    private AuthUserRepository authUserRepository;
    private GameStoryRepository gameStoryRepository;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllGames(Authentication auth) {
        Optional<AuthUser> authUser = authUserRepository.findByUsername(auth.getName());
        if (authUser.isPresent()) {
            List<GameStory> gameStories = gameStoryRepository.findByAuthUserId(authUser.get().getId());
            if (!gameStories.isEmpty()) {
                return ResponseEntity.ok(gameStories);
            }
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/getByGameName/{gameName}")
    public ResponseEntity<?> getByGameName(Authentication auth, @PathVariable String gameName) {
        Optional<AuthUser> authUser = authUserRepository.findByUsername(auth.getName());
        if (authUser.isPresent()) {
            List<GameStory> gameStories = gameStoryRepository
                    .findGameStoriesByGameNameAndAuthUserId(gameName, authUser.get().getId());
            if (!gameStories.isEmpty()) {
                return ResponseEntity.ok(gameStories);
            }
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }
}
