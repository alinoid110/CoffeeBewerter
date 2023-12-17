package org.example.CoffeeBewerter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;



@Setter
@Getter
@NoArgsConstructor
@Data
@Entity(name = "review")
public class CafeReview {
    @Id
    private Long idUser;

    private String userName;

    private String nameCafeReview;

    private Integer gradeCafeReview;

    private String commentsCafeReview;

    private Timestamp addReview;

    public Long  geIdtUser() {
        return idUser;
    }

    public void  setIdUser(Integer idReview) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNameCafeReview() {
        return nameCafeReview;
    }

    public void setNameCafeReview(String nameCafeReview) {
        this.nameCafeReview = nameCafeReview;
    }

    public Integer getGradeCafeReview() {
        return gradeCafeReview;
    }

    public void setGradeCafeReview(Integer gradeCafeReview) {
        this.gradeCafeReview = gradeCafeReview;
    }

    public String getCommentsCafeReview() {
        return commentsCafeReview;
    }

    public void setCommentsCafeReview(String commentsCafeReview) {
        this.commentsCafeReview = commentsCafeReview;
    }

    public Timestamp getAddReview() {
        return addReview;
    }

    public void setAddReview(Timestamp addReview) {
        this.addReview = addReview;
    }

    @Override
    public String toString() {
        return "CafeReview{" +
                "idReview=" + idUser +
                ", userName='" + userName + '\'' +
                ", nameCafeReview='" + nameCafeReview + '\'' +
                ", gradeCafeReview=" + gradeCafeReview +
                ", commentsCafeReview='" + commentsCafeReview + '\'' +
                ", addReview=" + addReview +
                '}';
    }
}
