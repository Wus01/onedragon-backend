package restapi.prac.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restapi.prac.model.dto.request.InquiryRequest;
import restapi.prac.model.dto.response.InquiryPageResponse;
import restapi.prac.model.dto.response.InquiryResponse;
import restapi.prac.model.entity.InquiryEntity;
import restapi.prac.repository.InquiryRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InquiryService {
    private final InquiryRepository inquiryRepository;

    @Transactional
    public InquiryResponse create(String userId, InquiryRequest request) {
        if (request == null) throw new IllegalArgumentException("문의 내용을 입력해 주세요.");
        String title = validateText(request.title(), 100, "제목");
        String content = validateText(request.content(), 5000, "내용");
        return InquiryResponse.from(inquiryRepository.save(new InquiryEntity(userId, title, content)));
    }

    public InquiryPageResponse findMine(String userId, int page) {
        if (page < 0) throw new IllegalArgumentException("페이지 번호가 올바르지 않습니다.");
        var inquiries = inquiryRepository.findByUserIdOrderByCreatedAtDescInquiryNoDesc(userId, PageRequest.of(page, 10));
        return new InquiryPageResponse(inquiries.getContent().stream().map(InquiryResponse::from).toList(),
                inquiries.getNumber(), inquiries.getTotalPages(), inquiries.getTotalElements());
    }

    private String validateText(String value, int maxLength, String label) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(label + "을 입력해 주세요.");
        if (value.length() > maxLength) throw new IllegalArgumentException(label + "은 " + maxLength + "자 이내로 입력해 주세요.");
        return value.strip();
    }
}
