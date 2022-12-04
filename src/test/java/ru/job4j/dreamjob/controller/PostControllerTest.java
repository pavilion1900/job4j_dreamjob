package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PostControllerTest {

    @Test
    public void whenFindAllPosts() {
        List<Post> posts = Arrays.asList(
                new Post(1, "New post", "Description for new post", false,
                        new City(1, "Москва"), LocalDateTime.now()),
                new Post(2, "New post", "Description for new post", false,
                        new City(2, "Санкт-Петербург"), LocalDateTime.now())
        );
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        when(postService.findAll()).thenReturn(posts);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.findAll(model, session);
        verify(model).addAttribute("posts", posts);
        verify(postService).findAll();
        assertThat(page).isEqualTo("posts");
    }

    @Test
    public void whenAddPost() {
        List<City> cities = Arrays.asList(
                new City(1, "Москва"),
                new City(2, "Санкт-Петербург"),
                new City(3, "Екатеринбург")
        );
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        when(cityService.findAllCities()).thenReturn(cities);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.addPost(model, session);
        verify(model).addAttribute("cities", cities);
        verify(cityService).findAllCities();
        assertThat(page).isEqualTo("addPost");
    }

    @Test
    public void whenCreatePost() {
        Post input = new Post(1, "New post", "Description for new post", false,
                new City(1, null), LocalDateTime.now());
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.createPost(input);
        verify(postService).add(input);
        assertThat(page).isEqualTo("redirect:/posts");
    }

    @Test
    public void whenUpdatePost() {
        Post input = new Post(1, "New post", "Description for new post", false,
                new City(1, null), LocalDateTime.now());
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.updatePost(input);
        verify(postService).update(input);
        assertThat(page).isEqualTo("redirect:/posts");
    }

    @Test
    public void whenFormUpdatePost() {
        List<City> cities = Arrays.asList(
                new City(1, "Москва"),
                new City(2, "Санкт-Петербург"),
                new City(3, "Екатеринбург")
        );
        Post post = new Post(1, "New post", "Description for new post", false,
                new City(1, "Москва"), LocalDateTime.now());
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        when(postService.findById(post.getId())).thenReturn(post);
        CityService cityService = mock(CityService.class);
        when(cityService.findAllCities()).thenReturn(cities);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.formUpdatePost(model, post.getId(), session);
        verify(model).addAttribute("post", post);
        verify(model).addAttribute("cities", cities);
        verify(cityService).findAllCities();
        assertThat(page).isEqualTo("updatePost");
    }
}
