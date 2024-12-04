//package logtest.casinobackend.controller;
//
//import logtest.casinobackend.dtos.CoinRequest;
//import logtest.casinobackend.dtos.CoinResponse;
//import logtest.casinobackend.model.AuthUser;
//import logtest.casinobackend.model.GameStory;
//import logtest.casinobackend.repository.AuthUserRepository;
//import logtest.casinobackend.repository.GameStoryRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.Map;
//import java.util.Optional;
//import java.util.Random;
//import java.util.concurrent.ThreadLocalRandom;
//
//@RestController
//@RequestMapping("/games")
//@AllArgsConstructor
//public class CoinController {
//    private final AuthUserRepository authUserRepository;
//    private final GameStoryRepository gameStoryRepository;
//
//    @PostMapping("/coin")
//    @Transactional
//    public ResponseEntity<?> flipCoin(Authentication auth, @RequestBody CoinRequest coinRequest) {
//        Optional<AuthUser> authUserOptional = authUserRepository.findByUsername(auth.getName());
//        if (authUserOptional.isPresent()) {
//            AuthUser authUser = authUserOptional.get();
//
//        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//    }
//}
