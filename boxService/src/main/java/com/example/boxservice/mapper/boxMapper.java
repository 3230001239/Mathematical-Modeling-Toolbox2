package com.example.boxservice.mapper;

import com.example.boxservice.pojo.box_model;
import com.example.boxservice.pojo.box_user;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface boxMapper {
    @Select("select * from box_user where utele = #{utele} and upwd = #{upwd}")
    public List<box_user> userLogin(@Param("utele") String utele , @Param("upwd") String upwd);

    @Insert("insert into box_user values(null,#{uname},#{utele},#{upwd})")
    int addUser(@Param("uname")String uname,@Param("upwd")String upwd,@Param("utele")String utele);

    @Select("select * from box_model where mname like concat('%',#{mname},'%')")
    List<box_model> modelSearch(@Param("mname") String mname);

    @Select("select * from box_model")
    List<box_model>getAllModel();

    @Select("select * from box_model where mtid = #{mtid}")
    List<box_model> modelType(@Param("mtid")String mtid);
}
