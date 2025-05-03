package restapi.prac.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Schema(description = "게시글 정보")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "게시글 고유 ID", example = "1")

    private Long id;

    @Column(nullable = false)
    @Schema(description = "게시글 제목", example = "안녕하세요")
    private String title;

    @Column(nullable = false, length = 5000)
    @Schema(description = "게시글 내용", example = "Spring Boot 학습중입니다.")
    private String content;

    // 기본 생성자
    public Post() {}

    // 생성자
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // getter, setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}