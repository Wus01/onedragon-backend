package restapi.prac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restapi.prac.model.PostEntity;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {



}
