package restapi.prac.controller;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import restapi.prac.common.ApiResponse;
import restapi.prac.model.dto.request.InquiryRequest;
import restapi.prac.model.dto.response.InquiryPageResponse;
import restapi.prac.model.dto.response.InquiryResponse;
import restapi.prac.service.InquiryService;
import restapi.prac.service.JwtService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inquiries")
public class InquiryController {
    private final InquiryService inquiryService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<InquiryResponse> create(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody InquiryRequest request) {
        String userId = authenticatedUserId(authorization);
        return ResponseEntity.status(HttpStatus.CREATED).body(inquiryService.create(userId, request));
    }

    @GetMapping
    public InquiryPageResponse findMine(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam(defaultValue = "0") int page) {
        return inquiryService.findMine(authenticatedUserId(authorization), page);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> invalidRequest(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(ApiResponse.error(exception.getMessage()));
    }

    private String authenticatedUserId(String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ") || authorization.substring(7).isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        try {
            String userId = jwtService.getUserIdFromToken(authorization.substring(7).trim());
            if (userId == null || userId.isBlank()) throw new IllegalArgumentException();
            return userId;
        } catch (JwtException | IllegalArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인 정보가 유효하지 않습니다.");
        }
    }
}
