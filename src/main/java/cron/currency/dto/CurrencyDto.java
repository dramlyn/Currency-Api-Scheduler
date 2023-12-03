package cron.currency.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CurrencyDto {
    private boolean success;
    private String timestamp;
    private String base;
    private LocalDate date;
    private Map<String, Float> rates;
    private transient LocalDateTime currentDate;
}
