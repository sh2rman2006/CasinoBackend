package logtest.casinobackend.controller;

import logtest.casinobackend.dtos.CoinRequest;
import logtest.casinobackend.model.AuthUser;
import logtest.casinobackend.model.GameStory;
import logtest.casinobackend.repository.AuthUserRepository;
import logtest.casinobackend.repository.GameStoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/games/coin")
@AllArgsConstructor
public class CoinController {
    private AuthUserRepository authUserRepository;
    private GameStoryRepository gameStoryRepository;


    @PostMapping
    @Transactional
    public ResponseEntity<?> coinFlipper(@RequestBody CoinRequest coinRequest, Authentication authentication) {
        Optional<AuthUser> authUserOptional = authUserRepository.findByUsername(authentication.getName());
        if (authUserOptional.isPresent()) {

            AuthUser authUser = authUserOptional.get();
            BigDecimal balance = authUser.getBalance();
            BigDecimal betAmount;

            if (balance.compareTo(BigDecimal.ZERO) == 0) {
                authUser.setAccountNonLocked(false);
                authUserRepository.save(authUser);
            }
            // валидация на запрос
            if (coinRequest.getChoose() != 1 && coinRequest.getChoose() != 2) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect coin side request");
            }
            try {
                betAmount = new BigDecimal(coinRequest.getBetAmount());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The bid amount was entered incorrectly");
            }
            // вывод запроса
            System.out.println(betAmount);
            System.out.println(coinRequest.getChoose());

            if (balance.compareTo(betAmount) >= 0 && betAmount.compareTo(BigDecimal.ZERO) > 0) {
                GameStory gameStory = GameStory
                        .builder()
                        .gameName("Coin")
                        .date(new Date())
                        .betAmount(betAmount)
                        .rateCoefficient(new BigDecimal("1.95"))
                        .authUser(authUser)
                        .build();

                Random rand = new Random();
                int randomNum = rand.nextInt(2) + 1;

                if (randomNum == coinRequest.getChoose()) {
                    authUser.setBalance(balance.add(betAmount.multiply(new BigDecimal("1.95"))));
                    authUserRepository.save(authUser);
                    gameStory.setWinner(true);
                    gameStory.setWinAmount(betAmount.multiply(new BigDecimal("1.95")));
                    gameStoryRepository.save(gameStory);
                    return ResponseEntity.ok(Map.of(
                            "isWinner", true,
                            "valueOfCoin", randomNum
                    ));
                } else {
                    authUser.setBalance(balance.subtract(betAmount));
                    authUserRepository.save(authUser);
                    gameStory.setWinner(false);
                    gameStory.setWinAmount(betAmount.negate());
                    gameStoryRepository.save(gameStory);
                    return ResponseEntity.ok(Map.of(
                            "isWinner", false,
                            "valueOfCoin", randomNum
                    ));
                }

            } else {
                return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED)
                        .body("there are not enough funds to debit the amount from the account");
            }

        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
