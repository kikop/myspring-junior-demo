package com.kikop.mapper;

import com.kikop.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

//@Mapper
public interface UserMapper {

    @Select("select * from user where id=#{id}")
    User getById(Integer id);
}
