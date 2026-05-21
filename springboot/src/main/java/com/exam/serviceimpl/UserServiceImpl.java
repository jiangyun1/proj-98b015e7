package com.exam.serviceimpl;
import com.exam.entity.Users;
import com.exam.repository.UserRepository;
import com.exam.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public int addUsers(Users users) {
        // TODO Auto-generated method stub
        Users u = userRepository.save(users);
        System.out.println(u.getUserName());
        return 1;
    }

    @Override
    public void update(Users users) {

    }

    @Override
    public Users selectUserByName(Users users) {
        Users u = null;
        if(users != null && !StringUtils.isEmpty(users.getUserName())) {
            u = userRepository.findByUserName(users.getUserName());
        }
        return u;
    }
}