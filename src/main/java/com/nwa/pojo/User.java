package com.nwa.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_user")//这里来对应数据库里的表名,也可以yml配置tb的头。
public class User {

    @TableId(type = IdType.AUTO) //插入的自动id增加,也可以yml文件里配置
    private Long id;
    private String userName;

    @TableField(select = false) //查询时不返回该字段的值
    private String password;
    private String name;
    private Integer age;

    @TableField(value = "email") //指定数据表中字段名
    private String mail;

    @TableField(exist = false)
    private String address; //在数据库表中是不存在的
}