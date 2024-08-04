package ua.denys.db.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.boot.jdbc.EmbeddedDatabaseConnection.H2;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED;
import static ua.denys.consts.ClientDTOs.TEST_CLIENT_DTO_1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ua.denys.service.IdCreator;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AUTO_CONFIGURED, connection = H2)
@DisplayNameGeneration(ReplaceUnderscores.class)
public class ChatFacadeTest {
  @Autowired private ClientFacade clientFacade;
  @Autowired private ChatFacade chatFacade;
  @MockBean
  private IdCreator idCreator;

  @BeforeEach
  public void beforeEach() {
    when(idCreator.createId()).thenReturn("1");
    clientFacade.createClient(TEST_CLIENT_DTO_1.getName());
  }

  @Test
  public void should_find_id_1() {
    // given
    chatFacade.createChat("Test chat 1", TEST_CLIENT_DTO_1);
    // when
    var found = chatFacade.findById("1");
    // then
    assertNotNull(found);
  }
}
