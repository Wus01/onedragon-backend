package restapi.prac.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
public class ApplyService {

    private final ApplyRepository applyRepository;
    private final HiringRepository hiringRepository;
    private final UserInfoRepository userInfoRepository;
    private final NotificationRepository notificationRepository;

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
    public ApplyEntity insertApplyInfo(ApplyDTO applyDto){
        // 지원여부 확인
        if (applyRepository.existsByHiringBoardEntity_HiringNoAndRgstId(applyDto.getHiringNo(), applyDto.getRgstId())) {
            throw new IllegalStateException("이미 지원하신 공고입니다.");
        }

        // DTO로 받아온 경우에는 builder()써서 Entity형태로 바꾸는 작업 필요
        // Entity로 받아온 경우에는 그냥 save 때리면 됨
        // hiringNo는 ApplyEntity에서 hiringBoardEntity 객체 안에 있어서 따로 세팅해줘야함
        Long hiringNo = applyDto.getHiringNo();
        String rgstId = applyDto.getRgstId();
        HiringBoardEntity faceHiringBoard = hiringRepository.getReferenceById(hiringNo);
        UserInfoEntity userInfo = userInfoRepository.getReferenceById(rgstId);
        ApplyEntity applyInfo = ApplyEntity.builder()
                .applySucYn(applyDto.getApplySucYn())
                .rgstId(applyDto.getRgstId())
//                .applyUserId(applyDto.getRgstId())
                .hiringBoardEntity(faceHiringBoard)
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
        System.out.println("🔥 디버그 - notifications: " + notifications.size());
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
    }

    @Transactional
    public boolean checkApplySts(Long hiringNo, String rgstId) {
        return applyRepository.existsByHiringBoardEntity_HiringNoAndRgstId(hiringNo, rgstId);
    }


}
