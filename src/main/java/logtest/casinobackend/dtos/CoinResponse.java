package logtest.casinobackend.dtos;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CoinResponse {
    private boolean isWinner;
    private int valueOfCoin;
}
