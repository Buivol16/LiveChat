package pl.denys.notification_service.config;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class AuthenticationChannelInterceptor implements ChannelInterceptor {
  private final JwtDecoder jwtDecoder;

  @Override
  public @Nullable Message<?> preSend(Message<?> message, MessageChannel channel) {
    StompHeaderAccessor accessor =
        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
    if (StompCommand.CONNECT.equals(accessor.getCommand())) {
      String authorizationHeader = accessor.getFirstNativeHeader("Authorization");

      if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
        throw new AccessDeniedException("Missing or invalid Authorization header");
      }

      String token = authorizationHeader.substring(7);

      Jwt jwt = jwtDecoder.decode(token);

      String username = jwt.getSubject();

      if (username == null) {
        username = jwt.getClaimAsString("name");
      }

      if (username == null) {
        username = jwt.getSubject();
      }

      JwtAuthenticationToken authentication =
          new JwtAuthenticationToken(jwt, Collections.emptyList(), username);

      accessor.setUser(authentication);
    }
    return message;
  }
}
