package com.nnxy.jgz.oasystem.service.impl;

import com.nnxy.jgz.oasystem.entity.Department;
import com.nnxy.jgz.oasystem.mapper.DepartmentMapper;
import com.nnxy.jgz.oasystem.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author JGZ
 * CreateTime 2019/12/18 14:20
 * Email 1945282561@qq.com
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<Department> departmentList() {
        return departmentMapper.departmentList();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public Department addDepartment(Department department) {
        if(department.getDepartmentParent() == null){
            //如果父部门属性为空，添加一个空的父部门，为了迎合mapper的sql语句
            department.setDepartmentParent(new Department());
        }
        String DepartmentId = UUID.randomUUID().toString().replace("-", "");
        department.setDepartmentId(DepartmentId);
        departmentMapper.insert(department);
        Department dbDepartment = departmentMapper.getDepartmentByDepartmentId(DepartmentId);
        return dbDepartment;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void deleteDepartment(String departmentId) {
        //获取被删部门的树
        Department departmentTree = departmentMapper.getDepartmentTreeByDepartmentId(departmentId);
        if(departmentTree.getChildDepartmentList().size()!= 0) {
            String delParent = null;
            //如果父部门不为空则获取父部门
            if (departmentTree.getDepartmentParent() != null) {
                delParent = departmentTree.getDepartmentParent().getDepartmentId();
            }
            System.out.println(departmentTree);
            //递归修改树
            recursionUpdateDepartmentLevel(departmentTree);
            //修改被删节点的子节点
            for (Department c : departmentTree.getChildDepartmentList()) {
                c.getDepartmentParent().setDepartmentId(delParent);
            }

            //将树转为列表
            List<Department> departmentList = new ArrayList<>();
            for (Department child : departmentTree.getChildDepartmentList()) {
                //添加子节点
                departmentTreeToList(departmentList, child);
            }
            //批量修改
            departmentMapper.updateLevelAndParentId(departmentList);
        }
        departmentMapper.deleteById(departmentTree.getDepartmentId());
        System.out.println(departmentTree);


    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void update(Department department) {
        departmentMapper.update(department);
    }

    /**
     * 将部门树变为列表
     * @param departmentList
     * @param departmentTree
     */
    private void departmentTreeToList(List<Department> departmentList, Department departmentTree){
        //将自己加入列表
        departmentList.add(departmentTree);
        if(departmentTree.getChildDepartmentList().size() == 0){
            //如果没有子节点则结束
            return;
        }
        for (Department child:departmentTree.getChildDepartmentList()) {
            //递归添加修改子节点
            departmentTreeToList(departmentList,child);
        }

    }

    /**
     * 递归修改部门的级别(上升一级)
     * @param departmentTree
     */
    private void recursionUpdateDepartmentLevel(Department departmentTree){
        //修改自己的级别(上升一级)
        departmentTree.setDepartmentLevel(departmentTree.getDepartmentLevel()-1);
        if(departmentTree.getChildDepartmentList().size() == 0){
            //如果没有子节点则结束
            return;
        }
        for (Department child:departmentTree.getChildDepartmentList()) {
            //递归修改子节点
            recursionUpdateDepartmentLevel(child);
        }

    }

}
