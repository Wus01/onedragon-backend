package restapi.prac.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import restapi.prac.model.PostEntity;
import restapi.prac.repository.PostRepository;

import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    // 전체조회
    public Page<PostEntity> getPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    // 단건조회
    public Optional<PostEntity> getPost(Long id){
        return postRepository.findById(id);
    }

    // 저장
    public PostEntity createPost(PostEntity postEntity){
        return postRepository.save(postEntity);
    }

    //  수정
    public Optional<PostEntity> updatePost(Long id, PostEntity updatePostEntity){
        return postRepository.findById(id).map(postEntity ->{
            postEntity.setTitle(updatePostEntity.getTitle());
            postEntity.setContent(updatePostEntity.getContent());
            return postRepository.save(postEntity);
        });
    }

    // 삭제
    public boolean deletePost(Long id){
        return postRepository.findById(id).map(postEntity ->{
            postRepository.delete(postEntity);
            return true;
        }).orElse(false);
    }
}
