package ru.job4j.dreamjob.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Application;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PostDBStoreTest {

    private static final String DELETE_POSTS = "DELETE FROM post";
    private final PostDBStore store = new PostDBStore(new Application().loadPool());

    @BeforeEach
    void wipeTable() {
        try (Connection cn = new Application().loadPool().getConnection();
             PreparedStatement ps = cn.prepareStatement(DELETE_POSTS)) {
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenAddPost() {
        Post post = new Post(0, "Java Job", "Description for Java Job", false,
                new City(1, null), LocalDateTime.now());
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName()).isEqualTo(post.getName());
    }

    @Test
    public void whenUpdatePost() {
        Post post = new Post(0, "Java Job", "Description for Java Job", false,
                new City(1, null), LocalDateTime.now());
        store.add(post);
        Post postForUpdate = new Post(post.getId(), "JS Job", "Description for JS Job", false,
                new City(1, null), LocalDateTime.now());
        store.update(postForUpdate);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName()).isEqualTo(postForUpdate.getName());
    }

    @Test
    public void whenFindAllPosts() {
        Post firstPost = new Post(0, "Java Job", "Description for Java Job", false,
                new City(1, null), LocalDateTime.now());
        Post secondPost = new Post(0, "JS Job", "Description for JS Job", false,
                new City(1, null), LocalDateTime.now());
        store.add(firstPost);
        store.add(secondPost);
        List<Post> posts = store.findAll();
        assertThat(posts.size()).isEqualTo(2);
    }
}
