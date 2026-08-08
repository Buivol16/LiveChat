package pl.denys.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.denys.model.chat.Chat;
import pl.denys.model.chat.PrivateChat;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatAndPrivateChatPairDTO {
    private List<Chat> chats;
    private List<PrivateChat> privateChats;

    public static ChatAndPrivateChatPairDTO from(List<Chat> chat, List<PrivateChat> privateChat){
        var dto = new ChatAndPrivateChatPairDTO();
        dto.setPrivateChats(privateChat);
        dto.setChats(chat);
        return dto;
    }
}
