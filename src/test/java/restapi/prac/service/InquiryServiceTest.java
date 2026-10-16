package restapi.prac.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import restapi.prac.model.dto.request.InquiryRequest;
import restapi.prac.model.entity.InquiryEntity;
import restapi.prac.repository.InquiryRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InquiryServiceTest {
    private final InquiryRepository repository = mock(InquiryRepository.class);
    private final InquiryService service = new InquiryService(repository);

    @Test
    void savesTrimmedInquiryWithOwnerAndTimestamp() {
        when(repository.save(any())).thenAnswer(invocation -> {
            InquiryEntity entity = invocation.getArgument(0);
            assertEquals("member", entity.getUserId());
            assertNotNull(entity.getCreatedAt());
            return entity;
        });
        var result = service.create("member", new InquiryRequest("  제목  ", "  내용\n두 번째 줄  "));
        assertEquals("제목", result.title());
        assertEquals("내용\n두 번째 줄", result.content());
        verify(repository).save(any());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\n\t"})
    void rejectsBlankTitleOrContent(String value) {
        assertThrows(IllegalArgumentException.class, () -> service.create("member", new InquiryRequest(value, "내용")));
        assertThrows(IllegalArgumentException.class, () -> service.create("member", new InquiryRequest("제목", value)));
        verifyNoInteractions(repository);
    }

    @Test
    void rejectsOversizedInputBeforeSaving() {
        assertThrows(IllegalArgumentException.class, () -> service.create("member", new InquiryRequest("a".repeat(101), "내용")));
        assertThrows(IllegalArgumentException.class, () -> service.create("member", new InquiryRequest("제목", "a".repeat(5001))));
        verifyNoInteractions(repository);
    }

    @Test
    void permitsMaximumLengths() {
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        var result = service.create("member", new InquiryRequest("a".repeat(100), "b".repeat(5000)));
        assertEquals(100, result.title().length());
        assertEquals(5000, result.content().length());
    }

    @Test
    void readsOnlyOwnersPageWithFixedPageSize() {
        var pageable = PageRequest.of(1, 10);
        when(repository.findByUserIdOrderByCreatedAtDescInquiryNoDesc("member", pageable)).thenReturn(
                new PageImpl<>(List.of(new InquiryEntity("member", "제목", "내용")), pageable, 11));
        var page = service.findMine("member", 1);
        assertEquals(11, page.totalElements());
        assertEquals(2, page.totalPages());
        assertEquals(1, page.page());
        assertEquals("제목", page.items().get(0).title());
        verify(repository).findByUserIdOrderByCreatedAtDescInquiryNoDesc("member", pageable);
    }

    @Test
    void rejectsNegativePage() {
        assertThrows(IllegalArgumentException.class, () -> service.findMine("member", -1));
        verifyNoInteractions(repository);
    }
}
