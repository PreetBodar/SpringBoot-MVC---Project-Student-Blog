package BlogAcadamy.BlogAcadamy.services;

import BlogAcadamy.BlogAcadamy.Entity.User;
import BlogAcadamy.BlogAcadamy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user)
    {
        userRepository.save(user);
    }

    public User getUser(String username)
    {
        return userRepository.findById(username).orElse(null);
    }

    public String checkUser(User user)
    {
        User existingUser = userRepository.findById(user.getUsername()).orElse(null);
        if(existingUser==null) {
            return "null";
        }

        else
        {
            if (existingUser.getPassword().equals(user.getPassword()))
                return "valid";
            else
                return "invalid";
        }
    }

    public String checkDuplicate(User user)
    {
        User existingUser = userRepository.findById(user.getUsername()).orElse(null);
        if(existingUser == null)
            return "new";
        else{
            return "exists";
        }
    }
}

