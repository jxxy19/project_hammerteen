package org.fullstack4.hammerteen.domain;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="hamt_lecture_reply")
public class LectureReplyEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int lectureReplyIdx;
    @Column(nullable = false)
    private int lectureIdx;
    @Column(length = 300, nullable = false)
    private String reviewContent;
    @Column(length = 1, nullable = false)
    private int rating;
    @Column(length = 20, nullable = false)
    private String userId;

    public void modify(String reviewContent,int rating){
        this.reviewContent = reviewContent;
        this.rating = rating;
        super.setModify_date(LocalDateTime.now());

    }
}

