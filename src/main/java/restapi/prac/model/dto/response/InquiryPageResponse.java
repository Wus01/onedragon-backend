package restapi.prac.model.dto.response;

import java.util.List;

public record InquiryPageResponse(List<InquiryResponse> items, int page, int totalPages, long totalElements) {
}
