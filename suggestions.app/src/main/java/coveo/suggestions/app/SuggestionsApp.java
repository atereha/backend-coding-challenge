package coveo.suggestions.app;

import coveo.suggestions.app.config.SuggestionsAppConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(SuggestionsAppConfiguration.class)
public class SuggestionsApp {

    public static void main(String[] args) {
        SpringApplication.run(SuggestionsApp.class, args);
    }
}
