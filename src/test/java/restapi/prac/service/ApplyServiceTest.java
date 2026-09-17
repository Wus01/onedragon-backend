package restapi.prac.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import restapi.prac.component.SseEmitters;
import restapi.prac.model.entity.ApplyEntity;
import restapi.prac.model.entity.HiringBoardEntity;
import restapi.prac.model.entity.UserInfoEntity;
import restapi.prac.repository.ApplyRepository;
import restapi.prac.repository.HiringRepository;
import restapi.prac.repository.NotificationRepository;
import restapi.prac.repository.UserInfoRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplyServiceTest {

    @Mock
    private ApplyRepository applyRepository;
    @Mock
    private HiringRepository hiringRepository;
    @Mock
    private UserInfoRepository userInfoRepository;
    @Mock
    private NotificationRepository notificationRepository;
    @Mock
    private SseEmitters sseEmitters;

    @InjectMocks
    private ApplyService applyService;

    @Test
    void insertApplyInfoSetsRequiredInitialState() {
        HiringBoardEntity hiringBoard = new HiringBoardEntity();
        hiringBoard.setHiringNo(7L);
        hiringBoard.setHiringSts("01");
        hiringBoard.setRgstId("owner");

        UserInfoEntity applicant = new UserInfoEntity();
        applicant.setUserId("applicant");

        when(hiringRepository.findById(7L)).thenReturn(Optional.of(hiringBoard));
        when(applyRepository.existsByHiringBoardEntity_HiringNoAndUserInfo_UserId(7L, "applicant"))
                .thenReturn(false);
        when(userInfoRepository.findById("applicant")).thenReturn(Optional.of(applicant));
        when(applyRepository.save(any(ApplyEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ApplyEntity result = applyService.insertApplyInfo(7L, "applicant");

        assertEquals("N", result.getApplySucYn());
        assertEquals("01", result.getApplySts());
        assertEquals("applicant", result.getRgstId());
        assertEquals(hiringBoard, result.getHiringBoardEntity());
        assertEquals(applicant, result.getUserInfo());
    }

    @Test
    void insertApplyInfoRejectsDuplicateApplicant() {
        HiringBoardEntity hiringBoard = new HiringBoardEntity();
        hiringBoard.setHiringNo(7L);
        hiringBoard.setHiringSts("01");
        hiringBoard.setRgstId("owner");

        when(hiringRepository.findById(7L)).thenReturn(Optional.of(hiringBoard));
        when(applyRepository.existsByHiringBoardEntity_HiringNoAndUserInfo_UserId(7L, "applicant"))
                .thenReturn(true);

        IllegalStateException error = assertThrows(
                IllegalStateException.class,
                () -> applyService.insertApplyInfo(7L, "applicant")
        );

        assertEquals("이미 지원하신 공고입니다.", error.getMessage());
        verify(applyRepository, never()).save(any());
    }
}
