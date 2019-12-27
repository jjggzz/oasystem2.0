package com.nnxy.jgz.oasystem.controller;

import com.nnxy.jgz.oasystem.entity.Department;
import com.nnxy.jgz.oasystem.service.DepartmentService;
import com.nnxy.jgz.oasystem.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门控制层
 * @author JGZ
 * CreateTime 2019/12/18 14:15
 * Email 1945282561@qq.com
 */
@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 获取部门列表
     * @return
     */
    @GetMapping("/department/departmentList")
    public ResponseMessage departmentList(){
        try {
            //查询部门列表
            List<Department> departmentList = departmentService.departmentList();
            ResponseMessage responseMessage = new ResponseMessage("0","获取部门列表成功");
            responseMessage.getData().put("departmentList",departmentList);
            return responseMessage;
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","获取部门列表失败");
        }
    }

    /**
     * 添加部门
     * @param department
     * @return
     */
    @PostMapping("/department/add")
    public ResponseMessage addDepartment(@RequestBody Department department){
        try {
          Department dbDepartment = departmentService.addDepartment(department);
          ResponseMessage responseMessage = new ResponseMessage("0","添加部门成功");
          responseMessage.getData().put("newDepartment",dbDepartment);
            return responseMessage;
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","添加部门失败");
        }
    }

    /**
     * 删除部门
     * @param departmentId
     * @return
     */
    @DeleteMapping("/department/{departmentId}")
    public ResponseMessage deleteDepartment(@PathVariable("departmentId") String departmentId){
        try {
            departmentService.deleteDepartment(departmentId);
            return new ResponseMessage("0", "删除成功");
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1", "删除失败");
        }
    }

    /**
     * 修改部门
     * @param departmentId
     * @param department
     * @return
     */
    @PutMapping("/department/{departmentId}")
    public ResponseMessage updateDepartment(@PathVariable("departmentId") String departmentId,@RequestBody Department department){
        try {
            department.setDepartmentId(departmentId);
            departmentService.update(department);
            return new ResponseMessage("0", "修改成功");
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1", "修改失败");
        }
    }

}
