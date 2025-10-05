package restapi.prac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restapi.prac.model.Post;
import restapi.prac.service.PostService;

import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {
/*
* RESTful API 설계 원칙에 따라, 기존 리소스를 생성(Create)할 때는 POST, 조회(Read)할 때는 GET, 수정(Update)할 때는 PUT 또는 PATCH, 삭제(Delete)할 때는 DELETE 메서드를 사용하는 것이 일반적입니다.
*
*
*
* */
    @Autowired
    private PostService postService;

    // 리스트조회
    @GetMapping
    public ResponseEntity<Page<Post>> listPost(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Post> posts = postService.getPosts(pageable);
        return ResponseEntity.ok().body(posts);
    }
    
    // 단건조회
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id){
        Optional<Post> postOpt = postService.getPost(id);
        return postOpt.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    // 생성
    @PostMapping
    public ResponseEntity<Post> creatPost(@RequestBody Post post){
        Post createdPost = postService.createPost(post);
        return ResponseEntity.ok(createdPost);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post updatePost){
        Optional<Post> updated = postService.updatePost(id, updatePost);
        return updated.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id){
        boolean deleted = postService.deletePost(id);
        if(deleted){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}

