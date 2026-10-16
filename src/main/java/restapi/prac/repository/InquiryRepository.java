package restapi.prac.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import restapi.prac.model.entity.InquiryEntity;

public interface InquiryRepository extends JpaRepository<InquiryEntity, Long> {
    Page<InquiryEntity> findByUserIdOrderByCreatedAtDescInquiryNoDesc(String userId, Pageable pageable);
}
