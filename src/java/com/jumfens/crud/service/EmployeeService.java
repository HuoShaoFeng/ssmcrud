package com.jumfens.crud.service;


import com.jumfens.crud.bean.Employee;
import com.jumfens.crud.bean.EmployeeExample;
import com.jumfens.crud.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;


    public Employee getEmp(Integer id){
        return employeeMapper.selectByPrimaryKey(id);
    }

    public List<Employee> getAll(){
        //查询所有（没有条件）
        EmployeeExample example = new EmployeeExample();
        example.setOrderByClause("emp_id asc");
        return employeeMapper.selectByExampleWithDept(example);
    }

    public void saveEmp(Employee employee){
        employeeMapper.insertSelective(employee);
    }

    public boolean checkUser(String name){
        EmployeeExample example = new EmployeeExample();
        example.createCriteria().andEmpNameEqualTo(name);


        long count = employeeMapper.countByExample(example);
        return count==0;//返回ture 表示数据库中没有这个用户名 即可用
    }

    public void updateEmp(Employee employee){
        employeeMapper.updateByPrimaryKeySelective(employee);
    }

    public void deleteEmpById(Integer id){
        employeeMapper.deleteByPrimaryKey(id);
    }

    public void deleteBatch(List<Integer> ids){
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andEmpIdIn(ids);
        employeeMapper.deleteByExample(example);
    }

}
