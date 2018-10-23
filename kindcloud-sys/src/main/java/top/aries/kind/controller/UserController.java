package top.aries.kind.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.aries.kind.model.User;
import top.aries.kind.service.UserService;

/**
 * Created by Administrator on 2017/8/16.
 */
@RestController     //相当于@Controller和@ResponseBody两个注解
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Logger kind = LoggerFactory.getLogger("kind");

    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    public int addUser(User user) {
        return userService.addUser(user);
    }

    @RequestMapping(value = "/all/{pageNum}/{pageSize}", produces = {"application/json;charset=UTF-8"})
    public Object findAllUser(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {

        return userService.findAllUser(pageNum, pageSize);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public User getUserById(@PathVariable("id") Integer id) {
        return userService.getUserById(id);
    }


    @RequestMapping(value = "/", produces = {"application/json;charset=UTF-8"})
    public String hello() {
        logger.info("进入了UserController的hello方法！--controller记录");
        kind.info("进入了UserController的hello方法！--kind记录");
        return "hello,UserController!";
    }
}