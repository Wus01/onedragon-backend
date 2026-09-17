package restapi.prac.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restapi.prac.component.SseEmitters;
import restapi.prac.model.dto.response.ApplyDTO;
import restapi.prac.model.dto.response.HiringBoardDTO;
import restapi.prac.model.entity.ApplyEntity;
import restapi.prac.model.entity.HiringBoardEntity;
import restapi.prac.model.entity.NotificationEntity;
import restapi.prac.model.entity.UserInfoEntity;
import restapi.prac.repository.ApplyRepository;
import restapi.prac.repository.HiringRepository;
import restapi.prac.repository.NotificationRepository;
import restapi.prac.repository.UserInfoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplyService {

    private final ApplyRepository applyRepository;
    private final HiringRepository hiringRepository;
    private final UserInfoRepository userInfoRepository;
    private final NotificationRepository notificationRepository;
    private final SseEmitters sseEmitters;

    //상세조회
    public Optional<ApplyEntity> getPost(Long id){

        return applyRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<ApplyEntity> getApplyListByHiringNo(Long hiringNo) {

        return applyRepository.findByHiringNoWithUserInfo(hiringNo);
    }

    // 지원하기
    @Transactional
    public ApplyEntity insertApplyInfo(Long hiringNo, String applicantId){
        if (hiringNo == null) {
            throw new IllegalArgumentException("채용 공고 번호가 필요합니다.");
        }

        HiringBoardEntity hiringBoard = hiringRepository.findById(hiringNo)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채용 공고입니다."));

        if ("02".equals(hiringBoard.getHiringSts())) {
            throw new IllegalStateException("마감된 채용 공고에는 지원할 수 없습니다.");
        }

        if (applicantId.equals(hiringBoard.getRgstId()) || applicantId.equals(hiringBoard.getUserId())) {
            throw new IllegalStateException("본인이 등록한 채용 공고에는 지원할 수 없습니다.");
        }

        // 지원 여부는 등록자 감사 컬럼이 아닌 실제 지원자 관계를 기준으로 확인한다.
        if (applyRepository.existsByHiringBoardEntity_HiringNoAndUserInfo_UserId(hiringNo, applicantId)) {
            throw new IllegalStateException("이미 지원하신 공고입니다.");
        }

        UserInfoEntity userInfo = userInfoRepository.findById(applicantId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        ApplyEntity applyInfo = ApplyEntity.builder()
                .applySucYn("N")
                .applySts("01")
                .rgstId(applicantId)
                .hiringBoardEntity(hiringBoard)
                .userInfo(userInfo)
                .build();

        return applyRepository.save(applyInfo);
    }

    // 지원자 확정
    @Transactional
    public void confirmApply(HiringBoardDTO hiringBoardDTO) {
        String userId = hiringBoardDTO.getUserId();
        Long hiringNo = hiringBoardDTO.getHiringNo();
        List<Long> applyNos = hiringBoardDTO.getApplyNos();
        String hiringSts = hiringBoardDTO.getHiringSts();
        String applySts = hiringBoardDTO.getApplySts();

        // 1. 첫 번째 업데이트 (공고 상태 변경)
        int result1 = applyRepository.updateStatusHiringBoard(userId, hiringNo); //id만

        // 2. 두 번째 업데이트 (apply_info)
        int result2 = applyRepository.updateStatusApplyInfo(userId, applyNos, hiringNo, applySts); //id, hiringNo

        // 3. 알림 테이블에 insert(notification)
        // appyNo로 applyUserId 조회
        List<ApplyEntity> applyInfoList = applyRepository.findAllById(applyNos);

        // 알림테이블에 insert
        String message = "지원하신 공고에 최종 확정되셨습니다! 🎉";
        String targetUrl = "/hiring/" + hiringNo; // 공고 상세 페이지로 연결
        List<NotificationEntity> notifications = new ArrayList<>();

        for(ApplyEntity applyInfo : applyInfoList){
            System.out.println("🔥 디버그 - applyNo: " + applyInfo.getApplyNo() + ", rcvrId: " + applyInfo.getRgstId());
            NotificationEntity noti = NotificationEntity.builder()
                    .applyNo(applyInfo.getApplyNo())
                    .rcvrId(applyInfo.getRgstId())
                    .readYn("N")
                    .notiContent(message)
                    .targetUrl(targetUrl)
                    .rgstId(userId)
                    .build();
            notifications.add(noti);
        }

        // save가 반환값이 없어서 result 체크하기 위해 List에 담기
        List<NotificationEntity> savedNotifications = notificationRepository.saveAll(notifications);

        int result3 = savedNotifications.size();
        if (result1 == 0 || result2 == 0 || result3 == 0) {
            throw new RuntimeException("업데이트 대상이 존재하지 않거나, 알림이 생성되지 않았습니다.");
        }

        for (NotificationEntity noti : savedNotifications) {
            try {
                // rcvrId(지원자 아이디)를 키값으로 해서 알림 데이터 전송
                sseEmitters.send(noti.getRcvrId(), noti);
            } catch (Exception e) {
                // 💡 꿀팁: SSE 전송에 실패하더라도 지원 확정(DB 저장) 자체가 롤백되면 안 되므로
                // try-catch로 감싸서 로그만 남기고 무시하는 것이 좋습니다.
                log.debug("SSE 알림 전송 실패 - 수신자: " + noti.getRcvrId());
            }
        }
    }

    @Transactional(readOnly = true) // readOnly 달면 성능 좋아진다함
    public ApplyDTO checkApplySts(Long hiringNo, String rgstId) {
        ApplyDTO result = new ApplyDTO();
        return applyRepository.findByHiringBoardEntity_HiringNoAndUserInfo_UserId(hiringNo, rgstId)
                .map(apply -> {
                    boolean isAccepted = "04".equals(apply.getApplySts()); //합격여부확인
                    result.setApplySts(apply.getApplySts());
                    result.setAccepted(isAccepted);
                    result.setApplied(true);
                    return result;
                })
                .orElseGet(() -> {
                    // 3. 데이터가 없으면(지원안함): 모두 false, 상태값 null로 반환
                    result.setApplySts(null);
                    result.setAccepted(false);
                    result.setApplied(false);
                    return result;
                });
    }

}
