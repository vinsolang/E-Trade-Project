package backend.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TelegramService {

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.chat.id}")
    private String chatId;

    public void sendMessage(String text) {
        try {
            // Use raw text directly, no URLEncoder
            String url = String.format(
                "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s&parse_mode=Markdown",
                botToken, chatId, text
            );
            new RestTemplate().getForObject(url, String.class);
        } catch (Exception e) {
            System.err.println("Telegram send failed: " + e.getMessage());
        }
    }
}
