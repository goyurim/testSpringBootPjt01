package com.yurim.boot.springboot.domain.posts;

import com.yurim.boot.springboot.domin.posts.Posts;
import com.yurim.boot.springboot.domin.posts.PostsRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository ;

    @After
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void postSaveAndReturn(){
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("yurim")
                .build());

        List<Posts> postsList = postsRepository.findAll();

        Posts post = postsList.get(0);
        log.info("post title="+post.getTitle()+" posts content="+post.getContent());
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록(){
        LocalDateTime now = LocalDateTime.of(2019,6,4,0,0,0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        List<Posts> all = postsRepository.findAll();
        Posts posts = all.get(0);

        System.out.println(">>>>>>>>>>>>>CreatedDate="+posts.getCreatedDate()+", modifiedDate="+posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}
