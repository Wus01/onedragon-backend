package restapi.prac.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import restapi.prac.model.dto.response.ApplyDTO;
import restapi.prac.service.ApplyService;
import restapi.prac.service.JwtService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ApplyControllerTest {

    private final ApplyService applyService = mock(ApplyService.class);
    private final JwtService jwtService = mock(JwtService.class);
    private final ApplyController applyController = new ApplyController(applyService, jwtService);

    @Test
    void insertApplyUsesAuthenticatedUserInsteadOfRequestUser() {
        ApplyDTO request = new ApplyDTO();
        request.setHiringNo(11L);
        request.setRgstId("forged-user");
        when(jwtService.getUserIdFromToken("valid-token")).thenReturn("authenticated-user");

        ResponseEntity<?> response = applyController.insertApplyInfo(request, "Bearer valid-token");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(applyService).insertApplyInfo(11L, "authenticated-user");
    }

    @Test
    void insertApplyRejectsMissingToken() {
        ApplyDTO request = new ApplyDTO();
        request.setHiringNo(11L);

        ResponseEntity<?> response = applyController.insertApplyInfo(request, null);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("로그인이 필요합니다.", response.getBody());
    }
}
