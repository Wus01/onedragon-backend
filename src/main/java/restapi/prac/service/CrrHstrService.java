package restapi.prac.service;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import restapi.prac.model.CrrHstrId;
import restapi.prac.model.CrrHstrVo;
import restapi.prac.repository.CrrHstrRepository;
import java.util.Optional;

@Service
public class CrrHstrService {

    private final CrrHstrRepository crrHstrRepository;

    public CrrHstrService(CrrHstrRepository crrHstrRepository){
        this.crrHstrRepository = crrHstrRepository;
    }

    // SELECT 1
    public Optional<CrrHstrVo> getCrrHstr(String userId ,Integer storeId){
        return crrHstrRepository.findById(new CrrHstrId(userId, storeId));
    }

    // UPDATE
    public Optional<CrrHstrVo> updateCrrHstr(String userId, Integer storeId, CrrHstrVo updateCrrHstrVo){
        CrrHstrId id = new CrrHstrId(userId, storeId);

        return crrHstrRepository.findById(id)
                .map(bfCrrHstrVo->{
                    BeanUtils.copyProperties(updateCrrHstrVo,bfCrrHstrVo,
                                "userId", "storeId");
                    // 추후 수정필요
                    //bfCrrHstrVo.setUpdtId("jin12345");
            return crrHstrRepository.save(bfCrrHstrVo);
        });
    }

    // DELETE
    public boolean deleteCrrHstr(String userId, Integer storeId){
        CrrHstrId id = new CrrHstrId(userId, storeId);

        return crrHstrRepository.findById(id).map(post->{
            crrHstrRepository.delete(post);
            return true;
        }).orElse(false);
    }

    // INSERT
    public CrrHstrVo createCrrHstr(CrrHstrVo crrHstrVo){
        // 추후 Auditing 완전 자동화 버전 생성(rgstId, updtId)
        crrHstrVo.setRgstId("1");   // 테스트용 사용자 ID
        crrHstrVo.setUpdtId("1");
        return crrHstrRepository.save(crrHstrVo);
    }



}
