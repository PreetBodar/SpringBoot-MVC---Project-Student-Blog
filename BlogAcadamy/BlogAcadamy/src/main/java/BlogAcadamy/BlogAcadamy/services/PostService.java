package BlogAcadamy.BlogAcadamy.services;

import BlogAcadamy.BlogAcadamy.Entity.Post;
import BlogAcadamy.BlogAcadamy.repository.PostRepository;
import org.hibernate.dialect.function.array.JsonArrayViaElementArgumentReturnTypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post getPost(int id)
    {
        return postRepository.findById(id).orElse(null);
    }

    public List<Post> getAllPost(){
        return postRepository.findAll();
    }

    public void savePost(Post post)
    {
        postRepository.save(post);
    }

    public void addLike(Post post){
        post.addLike();
        postRepository.save(post);
    }

    public  void addDislike(Post post)
    {
        post.addDislike();
        postRepository.save(post);
    }

    public void addComment(Post post , String comment)
    {
        post.setLastComment(comment);
        postRepository.save(post);
    }
}
