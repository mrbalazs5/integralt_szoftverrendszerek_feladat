package hu.unimiskolc.iit.config;
import hu.unimiskolc.iit.handler.ChatWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

	private final static String ENDPOINT = "/api/socket";

	@Bean
	public WebSocketHandler getChatWebSocketHandler(){
		return new ChatWebSocketHandler();
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
		webSocketHandlerRegistry.addHandler(getChatWebSocketHandler(), ENDPOINT)
				.setAllowedOrigins("*");
	}
}