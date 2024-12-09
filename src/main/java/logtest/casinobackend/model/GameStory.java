package logtest.casinobackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Document("story")
@AllArgsConstructor
@Builder
public class GameStory {
    @Id
    private String id;
    @NotNull
    private Date date;
    @NotBlank
    private String gameName;
    @NotNull
    private BigDecimal betAmount; // Сумма ставки
    @NotNull
    private BigDecimal winAmount; // может быть отрицательной
    @NotNull
    private BigDecimal rateCoefficient; // коэффициент ставки
    @NotNull
    private boolean isWinner;
    @NotNull
    private String coinSide;
    @DBRef
    @JsonIgnore
    private AuthUser authUser; // ссылка на пользователя
}
