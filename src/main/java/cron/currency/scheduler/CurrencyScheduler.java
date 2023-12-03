package cron.currency.scheduler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cron.currency.config.LocalDateAdapter;
import cron.currency.dto.CurrencyDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@EnableScheduling
public class CurrencyScheduler {

    private final String baseUrl = "http://data.fixer.io/api/latest?access_key=";

    @Value("${access.key}")
    private String accessKey;

    //смена базовой валюты доступна только с платной подпиской. По дефолту стоит евро.
    //private final String baseCurrency = "&base=USD";
    private final String symbols = "&symbols=USD,EUR,RUB";

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();


    @Scheduled(cron = "* */45 * * * *")
    public void getCurrencyRates(){
        try {
            URL url = new URI(baseUrl + accessKey + symbols).toURL();
            URLConnection yc = url.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()));
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null)
                json.append(line);
            in.close();

            CurrencyDto currencyDto = gson.fromJson(json.toString(), CurrencyDto.class);
            currencyDto.setCurrentDate(LocalDateTime.now());
            System.out.println(currencyDto);

        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
