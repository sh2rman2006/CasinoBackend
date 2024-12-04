package logtest.casinobackend.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CoinRequest {
    @NotNull
    private int choose;
    @NotNull
    private String betAmount;
}
