package coveo.suggestions.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SuggestionsAppConfiguration.class})
public class SuggestionsAppIntegrationTestConfiguration {


}
