package restapi.prac.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import restapi.prac.model.Post;
import restapi.prac.repository.PostRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    // 전체조회
    public Page<Post> getPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    // 단건조회
    public Optional<Post> getPost(Long id){
        return postRepository.findById(id);
    }

    // 저장
    public Post createPost(Post post){
        return postRepository.save(post);
    }

    //  수정
    public Optional<Post> updatePost(Long id, Post updatePost){
        return postRepository.findById(id).map(post->{
            post.setTitle(updatePost.getTitle());
            post.setContent(updatePost.getContent());
            return postRepository.save(post);
        });
    }

    // 삭제
    public boolean deletePost(Long id){
        return postRepository.findById(id).map(post->{
            postRepository.delete(post);
            return true;
        }).orElse(false);
    }
}
