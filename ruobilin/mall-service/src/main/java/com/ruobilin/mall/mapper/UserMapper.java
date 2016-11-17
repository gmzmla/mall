package com.ruobilin.mall.mapper;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.ruobilin.mall.entity.User;

public interface UserMapper {
   List<User> list();
   
   User getUser(Map param);
   
   void insert(User user);

   User getByEmail(String email);

   void update(User u);

   User getById(Long id);

   List<User> findAll(String name, PageBounds pageBounds);
   
   void deleteById(Long id);
}
