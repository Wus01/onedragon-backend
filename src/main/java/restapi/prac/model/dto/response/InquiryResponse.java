package restapi.prac.model.dto.response;

import restapi.prac.model.entity.InquiryEntity;

import java.time.Instant;

public record InquiryResponse(Long inquiryNo, String title, String content, Instant createdAt) {
    public static InquiryResponse from(InquiryEntity inquiry) {
        return new InquiryResponse(inquiry.getInquiryNo(), inquiry.getTitle(), inquiry.getContent(), inquiry.getCreatedAt());
    }
}
