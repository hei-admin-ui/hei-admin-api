package school.hei.haapi.integration;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import school.hei.haapi.SentryConf;
import school.hei.haapi.endpoint.rest.api.PayingApi;
import school.hei.haapi.endpoint.rest.client.ApiClient;
import school.hei.haapi.endpoint.rest.client.ApiException;
import school.hei.haapi.endpoint.rest.model.CreateDelayPenaltyChange;
import school.hei.haapi.endpoint.rest.model.CreateFee;
import school.hei.haapi.endpoint.rest.model.DelayPenalty;
import school.hei.haapi.endpoint.rest.model.Fee;
import school.hei.haapi.endpoint.rest.security.cognito.CognitoComponent;
import school.hei.haapi.integration.conf.AbstractContextInitializer;
import school.hei.haapi.integration.conf.TestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static school.hei.haapi.integration.conf.TestUtils.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = DelayPenaltyIT.ContextInitializer.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DelayPenaltyIT {
    @MockBean
    private SentryConf sentryConf;
    @MockBean
    private CognitoComponent cognitoComponentMock;

    private static ApiClient anApiClient(String token) {
        return TestUtils.anApiClient(token, DelayPenaltyIT.ContextInitializer.SERVER_PORT);
    }

    static CreateDelayPenaltyChange delayPenalty1() {
        CreateDelayPenaltyChange delayPenalty = new CreateDelayPenaltyChange();
        delayPenalty.setInterestPercent(100);
        delayPenalty.setInterestTimerate(CreateDelayPenaltyChange.InterestTimerateEnum.DAILY);
        delayPenalty.setGraceDelay(1);
        delayPenalty.setApplicabilityDelayAfterGrace(100);
        return delayPenalty;
    }
    static DelayPenalty delayPenalty2() {
        DelayPenalty delayPenalty = new DelayPenalty();
        delayPenalty.setId(DELAY_PENALTY2_ID);
        delayPenalty.setInterestPercent(3);
        delayPenalty.setInterestTimerate(DelayPenalty.InterestTimerateEnum.DAILY);
        delayPenalty.setGraceDelay(7);
        delayPenalty.setApplicabilityDelayAfterGrace(3);
        delayPenalty.setCreationDatetime(Instant.parse("2022-11-08T08:25:24.00Z"));
        return delayPenalty;
    }

    @BeforeEach
    void setUp() {
        setUpCognito(cognitoComponentMock);
    }


    @Test
    @Order(1)
    void student_read_ok() throws ApiException {
        ApiClient student1Client = anApiClient(STUDENT1_TOKEN);
        PayingApi api = new PayingApi(student1Client);

        DelayPenalty activePenalty = api.getDelayPenalty();
        assertEquals(delayPenalty2(), activePenalty);

    }


    @Test
    @Order(2)
    void student_write_ko() throws ApiException {
        ApiClient student1Client = anApiClient(STUDENT1_TOKEN);
        PayingApi api = new PayingApi(student1Client);

        assertThrowsApiException(
                "{\"type\":\"403 FORBIDDEN\",\"message\":\"Access is denied\"}",
                () -> api.createDelayPenaltyChange(delayPenalty1()));
    }


    @Test
    @Order(3)
    void manager_write_ok() throws ApiException {
        ApiClient manager1Client = anApiClient(MANAGER1_TOKEN);
        PayingApi api = new PayingApi(manager1Client);

        DelayPenalty actual = api.createDelayPenaltyChange(delayPenalty1());

        DelayPenalty delayPenalty = new DelayPenalty();
        delayPenalty.setId(actual.getId());
        delayPenalty.setInterestPercent(100);
        delayPenalty.setInterestTimerate(DelayPenalty.InterestTimerateEnum.DAILY);
        delayPenalty.setGraceDelay(1);
        delayPenalty.setApplicabilityDelayAfterGrace(100);
        delayPenalty.setCreationDatetime(actual.getCreationDatetime());

        assertEquals(actual,delayPenalty);
    }


    static class ContextInitializer extends AbstractContextInitializer {
        public static final int SERVER_PORT = anAvailableRandomPort();

        @Override
        public int getServerPort() {
            return SERVER_PORT;
        }
    }
}
