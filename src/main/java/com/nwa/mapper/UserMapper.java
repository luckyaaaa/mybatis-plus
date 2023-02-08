package com.nwa.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nwa.pojo.User;

//这是mp的第一种使用方式，点开这个继承类可以看到很多方法
//第二种是第一种基础上，在实体那边也继承一个mp类
public interface UserMapper extends BaseMapper<User> {

    User findById(Long id);

}
