package com.nnxy.jgz.oasystem.service;

import com.nnxy.jgz.oasystem.entity.Department;

import java.util.List;

/**
 * @author JGZ
 * CreateTime 2019/12/18 14:20
 * Email 1945282561@qq.com
 */
public interface DepartmentService {

    /**
     * 获取部门列表
     * @return
     */
    List<Department> departmentList();

    /**
     * 添加部门
     * @param department
     * @return
     */
    Department addDepartment(Department department);

    /**
     * 删除部门
     * @param departmentId
     */
    void deleteDepartment(String departmentId);

    /**
     * 修改部门
     * @param department
     */
    void update(Department department);
}
