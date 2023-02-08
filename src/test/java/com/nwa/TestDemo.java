package com.nwa;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nwa.mapper.UserMapper;
import com.nwa.pojo.User;
import com.nwa.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author Lucky友人a
 * @Date 2023/2/7 -9:39
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestDemo {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisUtil redisUtil;
//查全部
    @Test
    public void testSelectList() {
        List<User> users = userMapper.selectList(null);
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testRedis() {
        redisUtil.set("asbc", "lu1ckyaaa");
        System.out.println("hhhh");
    }
//新增，id自增
    @Test
    public void testInsert() {
        User user = new User();
        user.setAge(20);
        user.setMail("test@3itcast.cn");
        user.setName("曹23操");
        user.setUserName("cao3cao");
        user.setPassword("1233456");
        int result = userMapper.insert(user);
        //返回的result是受影响的行数，并不是自增 后的id
        System.out.println("result = " + result);
        System.out.println(user.getId()); //自增后的id会回填到对象中
    }
    //根据 ID 修改
    @Test
    public void testUpdateById() {
        User user = new User();
        user.setId(6L); //主键
        user.setAge(21); //更新的字段 //根据id更新，更新不为null的字段
        userMapper.updateById(user);
    }
    //根据其他条件修改
    @Test
    public void testUpdate() {
        User user = new User();
        user.setAge(22); //更新的字段 //更新的条件
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", 6); //执行更新操作
        int result = userMapper.update(user, wrapper);
        //或者
        // UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        // wrapper.eq("id", 6).set("age", 23);
        // 执行更新操作
        // int result = userMapper.update(null, wrapper);
        System.out.println("result = " + result);

    }

    /**
     * 删除通过id
     */
    @Test
    public void testDeleteById() { //执行删除操作
       int result = userMapper.deleteById(6L);
       System.out.println("result = " + result);
    }
/**
 * 自定义删除
 */

@Test
public void testDeleteByMap(){

    Map<String,Object> map = new HashMap<>();
    map.put("user_name", "zhangsan");
    map.put("password", "999999");

    // 根据map删除数据，多条件之间是and关系
    int result = this.userMapper.deleteByMap(map);
    System.out.println("result => " + result);
}

    @Test
    public void testDelete(){

        //用法一：
//        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        wrapper.eq("user_name", "caocao1")
//                .eq("password", "123456");

        //用法二：
        User user = new User();
        user.setPassword("123456");
        user.setUserName("caocao");

        QueryWrapper<User> wrapper = new QueryWrapper<>(user);

        // 根据包装条件做删除
        int result = this.userMapper.delete(wrapper);
        System.out.println("result => " + result);
    }

    @Test
    public void  testDeleteBatchIds(){
        // 根据id批量删除数据
        List<Object> list = new ArrayList<>();
        for (int i = 0; i <Integer.parseInt("7"); i++) {
            list.add(i);
        }

        int result = this.userMapper.deleteBatchIds(Arrays.asList(10L, 11L));
        System.out.println("result => " + result);
    }

    @Test
    public void testSelectBatchIds(){
        // 根据id批量查询数据
        List<User> users = this.userMapper.selectBatchIds(Arrays.asList(2L, 3L, 4L, 100L));
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testSelectOne(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //查询条件
        wrapper.eq("password", "123456");
        // 查询的数据超过一条时，会抛出异常
        User user = this.userMapper.selectOne(wrapper);
        System.out.println(user);
    }

    @Test
    public void testSelectCount(){

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.gt("age", 20); // 条件：年龄大于20岁的用户

        // 根据条件查询数据条数
        Integer count = this.userMapper.selectCount(wrapper);
        System.out.println("count => " + count);
    }



    // 测试分页查询
    @Test
    public void testSelectPage(){

//        Page<User> page = new Page<>(3,1); //查询第一页，查询1条数据
//
//        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        //设置查询条件
//        wrapper.like("email", "itcast");
//
//        IPage<User> iPage = this.userMapper.selectPage(page, wrapper);
//        System.out.println("数据总条数： " + iPage.getTotal());
//        System.out.println("数据总页数： " + iPage.getPages());
//        System.out.println("当前页数： " + iPage.getCurrent());
//
//        List<User> records = iPage.getRecords();
//        for (User record : records) {
//            System.out.println(record);
//        }

    }

    /**
     * 自定义的方法
     */
    @Test
    public void testFindById(){
        User user = this.userMapper.findById(2L);
        System.out.println(user);
    }

    @Test
    public void testAllEq(){

        Map<String,Object> params = new HashMap<>();
        params.put("name", "李四");
        params.put("age", "20");
        params.put("password", null);

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //SELECT id,user_name,name,age,email AS mail FROM tb_user WHERE password IS NULL AND name = ? AND age = ?
//        wrapper.allEq(params);
        //SELECT id,user_name,name,age,email AS mail FROM tb_user WHERE name = ? AND age = ?
//        wrapper.allEq(params, false);

        //SELECT id,user_name,name,age,email AS mail FROM tb_user WHERE age = ?
//        wrapper.allEq((k, v) -> (k.equals("age") || k.equals("id")) , params);
        //SELECT id,user_name,name,age,email AS mail FROM tb_user WHERE name = ? AND age = ?
        wrapper.allEq((k, v) -> (k.equals("age") || k.equals("id") || k.equals("name")) , params);

        List<User> users = this.userMapper.selectList(wrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testEq() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        //SELECT id,user_name,password,name,age,email FROM tb_user WHERE password = ? AND age >= ? AND name IN (?,?,?)
        wrapper.eq("password", "123456")
                .ge("age", 20)
                .in("name", "李四", "王五", "赵六");

        List<User> users = this.userMapper.selectList(wrapper);
        for (User user : users) {
            System.out.println(user);
        }

    }

    @Test
    public void testLike(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // SELECT id,user_name,name,age,email AS mail FROM tb_user WHERE name LIKE ?
        // 参数：%五(String)
        wrapper.likeLeft("name", "五");

        List<User> users = this.userMapper.selectList(wrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testOrderByAgeDesc(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //按照年龄倒序排序
        // SELECT id,user_name,name,age,email AS mail FROM tb_user ORDER BY age DESC
        wrapper.orderByDesc("age");

        List<User> users = this.userMapper.selectList(wrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testOr(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // SELECT id,user_name,name,age,email AS mail FROM tb_user WHERE name = ? OR age = ?
        wrapper.eq("name", "王五").or().eq("age", 21);

        List<User> users = this.userMapper.selectList(wrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testSelect(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //SELECT id,name,age FROM tb_user WHERE name = ? OR age = ?
        wrapper.eq("name", "王五")
                .or()
                .eq("age", 21)
                .select("id","name","age"); //指定查询的字段

        List<User> users = this.userMapper.selectList(wrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }

}