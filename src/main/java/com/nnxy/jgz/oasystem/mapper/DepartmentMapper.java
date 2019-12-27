package com.nnxy.jgz.oasystem.mapper;

import com.nnxy.jgz.oasystem.entity.Department;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author JGZ
 * CreateTime 2019/12/18 14:21
 * Email 1945282561@qq.com
 */
@Mapper
public interface DepartmentMapper {

    /**
     * 获取部门列表
     * @return
     */
    List<Department> departmentList();

    /**
     * 插入部门
     * @param department
     */
    void insert(Department department);

    /**
     * 通过部门id获取部门
     * @param departmentId
     * @return
     */
    Department getDepartmentByDepartmentId(String departmentId);

    /**
     * 获取部门树
     * @param departmentId
     * @return
     */
    Department getDepartmentTreeByDepartmentId(String departmentId);

    /**
     * 修改级别和父部门id
     * @param departmentList
     */
    void updateLevelAndParentId(List<Department> departmentList);

    /**
     * 通过id删除部门
     * @param departmentId
     */
    void deleteById(String departmentId);

    /**
     * 修改部门
     * @param department
     */
    void update(Department department);
}
