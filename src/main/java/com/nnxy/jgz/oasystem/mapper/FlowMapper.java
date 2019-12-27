package com.nnxy.jgz.oasystem.mapper;

import com.nnxy.jgz.oasystem.entity.Flow;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author JGZ
 * CreateTime 2019/12/20 14:37
 * Email 1945282561@qq.com
 */
@Mapper
public interface FlowMapper {
    /**
     * 获取流程列表
     * @return
     */
    List<Flow> flowList();

    /**
     * 修改流程
     * @param flow
     */
    void update(Flow flow);

    /**
     * 通过id删除
     * @param flowId
     */
    void deleteByFlowId(String flowId);

    /**
     * 插入流程
     * @param flow
     */
    void insert(Flow flow);
}
