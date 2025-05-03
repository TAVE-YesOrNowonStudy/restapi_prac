package restapi.prac.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "게시글 API", description = "게시글 CRUD API")
public class PostController {

    @Autowired
    private PostService postService;

    @Operation(summary = "게시글 목록 조회", description = "페이지네이션을 적용하여 게시글 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<Page<Post>> listPosts(
            @Parameter(description = "페이지 번호 (0부터 시작)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postService.getPosts(pageable);
        return ResponseEntity.ok(posts);
    }

    @Operation(summary = "게시글 조회", description = "ID로 특정 게시글을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(
            @Parameter(description = "게시글 ID") @PathVariable Long id) {
        Optional<Post> postOpt = postService.getPost(id);
        return postOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "게시글 생성", description = "새로운 게시글을 생성합니다.")
    @PostMapping
    public ResponseEntity<Post> createPost(
            @Parameter(description = "게시글 정보") @RequestBody Post post) {
        Post createdPost = postService.createPost(post);
        return ResponseEntity.ok(createdPost);
    }

    @Operation(summary = "게시글 수정", description = "기존 게시글을 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(
            @Parameter(description = "게시글 ID") @PathVariable Long id,
            @Parameter(description = "수정할 게시글 정보") @RequestBody Post updatedPost) {
        Optional<Post> updated = postService.updatePost(id, updatedPost);
        return updated.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @Parameter(description = "게시글 ID") @PathVariable Long id) {
        boolean deleted = postService.deletePost(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}