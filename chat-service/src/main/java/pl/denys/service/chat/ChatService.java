package pl.denys.service.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.denys.configuration.context.CorrelationIdContextHolder;
import pl.denys.dto.chat.ChatDTO;
import pl.denys.dto.chat.ChatPreviewDTO;
import pl.denys.dto.chat.PrivateChatDTO;
import pl.denys.event.ChatCreatedEvent;
import pl.denys.mapper.ChatMapper;
import pl.denys.model.UserEntity;
import pl.denys.model.chat.Chat;
import pl.denys.model.chat.PrivateChat;
import pl.denys.model.invitelink.InviteLink;
import pl.denys.model.member.Member;
import pl.denys.repository.chat.ChatRepository;
import pl.denys.repository.chat.PrivateChatRepository;
import pl.denys.repository.invitelink.InviteLinkRepository;
import pl.denys.repository.member.MemberRepository;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final StreamBridge streamBridge;
    private final PrivateChatRepository privateChatRepository;
    private final ChatRepository chatRepository;
    private final MemberRepository memberRepository;
    private final InviteLinkRepository inviteLinkRepository;

    private final ChatMapper mapper = Mappers.getMapper(ChatMapper.class);

    public void createChat(ChatDTO chatDTO) {
        Chat chat = null;
        PrivateChat privateChat = null;
        var creatorId = SecurityContextHolder.getContext().getAuthentication().getName();
        var isPublic = chatDTO.getIsPublic();
        chatDTO.setCreator(creatorId);
        chatDTO.setName(chatDTO.getPartnerId());
        if (isPublic) {
            chat = createPublicChat(chatDTO);
        } else {
            privateChat = createPrivateChat(chatDTO);
        }

        streamBridge.send("chat-created-out-0", new ChatCreatedEvent(chat, privateChat, isPublic, CorrelationIdContextHolder.getCorrelationId()));
    }

    public PrivateChat createPrivateChat(ChatDTO chatDTO) {
        var chat = mapper.chatDTOToPrivateChat(chatDTO);
        chat.setCreatedAt(Timestamp.from(Instant.now(Clock.systemUTC())));
        return chat;
    }

    private Chat createPublicChat(ChatDTO chatDTO) {
        var chat = mapper.chatDTOToChat(chatDTO);
        chat.setCreatedAt(Timestamp.from(Instant.now(Clock.systemUTC())));
        return chat;
    }

    public List<PrivateChatDTO> getAllPrivateByCreatorIdOrPartnerId() {
        var userId = SecurityContextHolder.getContext().getAuthentication().getName();
        var privateChats = privateChatRepository.findAllByCreatorIdOrPartnerId(new UserEntity(userId, null, null));
        privateChats.forEach(privateChat -> privateChat.setCustomerId(userId));
        return mapper.privateChatToPrivateChatDTOList(privateChats);
    }

    public List<Chat> getAllPublicByUserId() {
        var userId = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = new UserEntity();
        user.setId(userId);
        var publicChats = memberRepository.findAllByUser(user);
        return publicChats.stream().map(Member::getChat).toList();
    }

    public void enterChat(String code) {
        var correlationId = CorrelationIdContextHolder.getCorrelationId();
        var userId = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = new UserEntity();
        user.setId(userId);
        var inviteLink = inviteLinkRepository.findById(code);
        if (inviteLink.isPresent()) {
            var chat = inviteLink.get().getChat();

            if (memberRepository.existsByUserAndChat(user, chat)) {
                throw new RuntimeException("User is member of this chat already with code " + code + " and correlationId " + correlationId);
            } else {
                memberRepository.save(new Member(null, user, chat));
            }
        } else {
            throw new RuntimeException(String.format("Code %s isn't exists with correlationId %s", code, correlationId));
        }
    }

    public String createInviteCodeOrReturnExisting(Long chatId) {
        var correlationId = CorrelationIdContextHolder.getCorrelationId();
        var userId = SecurityContextHolder.getContext().getAuthentication().getName();
        var uuid = UUID.randomUUID().toString();
        var isAuthorized = chatRepository.existsByCreatorAndId(userId, chatId);
        if (isAuthorized) {


            var chat = new Chat();
            chat.setId(chatId);

            var optionalInviteLink = inviteLinkRepository.findByChat(chat);
            if (optionalInviteLink.isPresent()) return optionalInviteLink.get().getUuid();

            var inviteLink = new InviteLink(uuid, chat);

            inviteLinkRepository.save(inviteLink);

            return uuid;
        } else {
            throw new RuntimeException("User is not authorized with correlationId " + correlationId);
        }
    }

    public ChatPreviewDTO getChatPreview(String code) {
        String correlationId = CorrelationIdContextHolder.getCorrelationId();
        log.info("Getting chat preview for chat {}", code);
        var inviteLink = inviteLinkRepository.findById(code);
        if (inviteLink.isPresent()) {
            var chat = inviteLink.get().getChat();
            return new ChatPreviewDTO(null, chat.getName(), chat.getDescription());
        } else {
            throw new RuntimeException(String.format("Code %s isn't exists with correlationId %s", code, correlationId));
        }
    }

    public List<Member> getMembersByChatId(Long chatId) {
        return memberRepository.findAllByChatId(chatId);
    }

    public void removeMember(Long memId) {
        var userId = SecurityContextHolder.getContext().getAuthentication().getName();
        var isCreator = this.chatRepository.existsByCreatorAndId(userId, memberRepository.findChatIdById(memId));
        if (isCreator){
            memberRepository.deleteById(memId);
        }else {
            throw new RuntimeException("User has no rights to remove user member with id " + memId);
        }
    }
}
