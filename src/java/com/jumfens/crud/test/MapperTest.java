package com.jumfens.crud.test;


import com.jumfens.crud.bean.Employee;
import com.jumfens.crud.dao.DepartmentMapper;
import com.jumfens.crud.dao.EmployeeMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * 测试dao层的工作
 *推荐Spring的项目就可以使用Spring的单元测试，可以自动注入我们需要的组件
 *1、导入SpringTest模块
 *2、@ContextConfiguration指定Spring配置文件的位置
 *3、直接autowired要使用的组件即可
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class MapperTest {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private SqlSession sqlSession;

    @Test
    public void testMapper(){

//        departmentMapper.insertSelective(new Department(1,"开发部"));
//        departmentMapper.insertSelective(new Department(null,"测试部"));
//        departmentMapper.insertSelective(new Department(null,"产品部"));

        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);

        Employee employee = new Employee(1,"Lucy","F","Lucy@163.com",1);

//        mapper.insertSelective(employee);

        String uid = "";
        String gender = "M";
        Integer did = 1;
        for (int i = 0; i < 1000 ; i++) {
            uid = UUID.randomUUID().toString().substring(0,5)+"-"+(i+1);
            if((i&0x1)==0){//偶数
                gender = "F";
            }else{
                gender = "M";
            }

            did = i%3 + 1;
            mapper.insertSelective(new Employee(i+1,uid,gender,uid+"@163.com",did));
        }
        System.out.println("done.....");
    }



}



























