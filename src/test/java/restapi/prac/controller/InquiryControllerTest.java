package restapi.prac.controller;

import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import restapi.prac.model.dto.request.InquiryRequest;
import restapi.prac.model.dto.response.InquiryPageResponse;
import restapi.prac.model.dto.response.InquiryResponse;
import restapi.prac.service.InquiryService;
import restapi.prac.service.JwtService;

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class InquiryControllerTest {
    private final InquiryService service = mock(InquiryService.class);
    private final JwtService jwt = mock(JwtService.class);
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new InquiryController(service, jwt)).build();
    }

    @Test
    void createUsesVerifiedTokenIdentityAndReturnsCreatedDto() throws Exception {
        when(jwt.getUserIdFromToken("valid-token")).thenReturn("member");
        when(service.create(eq("member"), any())).thenReturn(
                new InquiryResponse(1L, "제목", "내용", Instant.parse("2026-10-17T00:00:00Z")));

        mvc.perform(post("/api/inquiries").header("Authorization", "Bearer valid-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"제목\",\"content\":\"내용\",\"userId\":\"forged-user\"}"))
                .andExpect(status().isCreated()).andExpect(jsonPath("inquiryNo").value(1))
                .andExpect(jsonPath("userId").doesNotExist());

        verify(service).create("member", new InquiryRequest("제목", "내용"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"Basic token", "Bearer "})
    void bothEndpointsRejectMissingOrMalformedAuthorization(String authorization) throws Exception {
        var getRequest = get("/api/inquiries");
        var postRequest = post("/api/inquiries").contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"title\",\"content\":\"content\"}");
        if (authorization != null) {
            getRequest.header("Authorization", authorization);
            postRequest.header("Authorization", authorization);
        }
        mvc.perform(getRequest).andExpect(status().isUnauthorized());
        mvc.perform(postRequest).andExpect(status().isUnauthorized());
        verifyNoInteractions(service);
    }

    @Test
    void invalidTokenCannotReadInquiries() throws Exception {
        when(jwt.getUserIdFromToken("invalid-token")).thenThrow(new JwtException("invalid"));
        mvc.perform(get("/api/inquiries").header("Authorization", "Bearer invalid-token"))
                .andExpect(status().isUnauthorized());
        verifyNoInteractions(service);
    }

    @Test
    void listIgnoresRequestedUserAndUsesTokenOwner() throws Exception {
        when(jwt.getUserIdFromToken("valid-token")).thenReturn("member");
        when(service.findMine("member", 2)).thenReturn(new InquiryPageResponse(List.of(), 2, 3, 21));
        mvc.perform(get("/api/inquiries?page=2&userId=another-user")
                        .header("Authorization", "Bearer valid-token"))
                .andExpect(status().isOk()).andExpect(jsonPath("page").value(2))
                .andExpect(jsonPath("totalElements").value(21));
        verify(service).findMine("member", 2);
    }

    @Test
    void validationErrorsReturnSafeMessage() throws Exception {
        when(jwt.getUserIdFromToken("valid-token")).thenReturn("member");
        when(service.create(eq("member"), any())).thenThrow(new IllegalArgumentException("제목을 입력해 주세요."));
        mvc.perform(post("/api/inquiries").header("Authorization", "Bearer valid-token")
                        .contentType(MediaType.APPLICATION_JSON).content("{\"title\":\" \",\"content\":\"내용\"}"))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("message").value("제목을 입력해 주세요."));
    }
}
