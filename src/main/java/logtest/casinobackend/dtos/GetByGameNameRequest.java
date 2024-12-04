package logtest.casinobackend.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GetByGameNameRequest {
    @NotBlank
    private String gameName;
}
