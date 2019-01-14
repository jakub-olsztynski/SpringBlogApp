package pl.reaktor.blogapplication.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_m;
    @NotBlank(message = "komentarz nie może być pusty")
    private String message;
    @NotBlank(message = "pole autor nie może być puste")
    private String author = "anonim";
    private Date date_added = new Date();
    @ManyToOne
    @JoinColumn(name = "post_id_b")
    Post post;

    public Comment() {
    }

    public Comment(String message, String author, Post post) {
        this.message = message;
        this.author = author;
        this.post = post;
    }

    public long getId_m() {
        return id_m;
    }

    public void setId_m(long id_m) {
        this.id_m = id_m;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate_added() {
        return date_added;
    }

    public void setDate_added(Date date_added) {
        this.date_added = date_added;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
