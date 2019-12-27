package com.nnxy.jgz.oasystem.mapper;

import com.nnxy.jgz.oasystem.entity.FlowLine;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author JGZ
 * CreateTime 2019/12/25 13:44
 * Email 1945282561@qq.com
 */
@Mapper
public interface FlowNodeLineMapper {
    /**
     * 插入流程线
     * @param flowLineList
     */
    void insert(List<FlowLine> flowLineList);
}
