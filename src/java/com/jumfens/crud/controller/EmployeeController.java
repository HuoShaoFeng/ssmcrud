package com.jumfens.crud.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jumfens.crud.bean.Employee;
import com.jumfens.crud.bean.Msg;
import com.jumfens.crud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/emp/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Msg getEmp(@PathVariable("id") Integer id){
        Employee employee = employeeService.getEmp(id);
        return Msg.success().add("emp",employee);

    }



    //支持@ResponseBody需要加入Jackson支持包
    @RequestMapping("/emps")
    @ResponseBody
    public Msg getEmps(@RequestParam(value = "pn",defaultValue = "1")Integer pn){
        //引入PageHelper分页查询插件
        PageHelper.startPage(pn,5);
        //startPage后面紧跟的这个查询就是一个分页查询
        List<Employee> emps = employeeService.getAll();
//        使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了
//        封装了详细的分页信息，包括查询出来的数据集
        PageInfo pageInfo = new PageInfo(emps,5);//5是连续显示的页数（导航条上的1，2，3,4,5页）

        return Msg.success().add("pageInfo",pageInfo);
    }



    @RequestMapping(value = "/emp",method = RequestMethod.POST)
    @ResponseBody
    public Msg saveEmp(@Valid Employee employee, BindingResult result){
        if(result.hasErrors()){
            //校验失败，应该返回失败，在模态框中显示校验失败的错误信息
            Map<String, Object> map = new HashMap<String, Object>();
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError fieldError : errors) {
                System.out.println("错误的字段名："+fieldError.getField());
                System.out.println("错误信息："+fieldError.getDefaultMessage());
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return Msg.fail().add("errorFields", map);
        }else{
            employeeService.saveEmp(employee);
            return Msg.success();
        }
    }


    @RequestMapping("/checkuser")
    @ResponseBody
    public Msg checkUser(String empName){
        String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
        if(!empName.matches(regx)){
            return Msg.fail().add("va_msg","用户名不合法");
        }



        boolean b = employeeService.checkUser(empName);
        if(b){
            return Msg.success();
        }else {
            return Msg.fail().add("va_msg","用户名已经存在");
        }
    }

    /*
    @RequestMapping("/emps")
    public String getEmps(@RequestParam(value = "pn",defaultValue = "1")Integer pn,
                          Model model){
        //引入PageHelper分页查询插件
        PageHelper.startPage(pn,5);
        //startPage后面紧跟的这个查询就是一个分页查询
        List<Employee> emps = employeeService.getAll();
//        使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了
//        封装了详细的分页信息，包括查询出来的数据集
        PageInfo pageInfo = new PageInfo(emps,5);//5是连续显示的页数（导航条上的1，2，3,4,5页）
        model.addAttribute("pageInfo",pageInfo);
        return "list";
    }
    */


    @RequestMapping(value = "/emp/{empId}",method = RequestMethod.PUT)
    @ResponseBody
    public Msg updateEmp(Employee employee){
        System.out.println("/emp/{empId}");
        employeeService.updateEmp(employee);
        return Msg.success();
    }


    //单个删除  批量删除    二合一
    //多个ID用‘-’分开：1-2-3，一个ID就是1
    @RequestMapping(value = "/emp/{ids}",method = RequestMethod.DELETE)
    @ResponseBody
    public Msg deleteEmpById(@PathVariable("ids") String ids){
        //批量删除
        List<Integer> idsList = new ArrayList<Integer>();
        if(ids.contains("-")){
            String[] idsArr = ids.split("-");
            for (String id:idsArr
                 ) {
                idsList.add(Integer.parseInt(id));
            }
            employeeService.deleteBatch(idsList);
        }else {//单个删除
            employeeService.deleteEmpById(Integer.parseInt(ids));
        }

        return Msg.success();
    }









}




























