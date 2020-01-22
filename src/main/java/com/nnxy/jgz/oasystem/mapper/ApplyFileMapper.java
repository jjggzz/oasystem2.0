package com.nnxy.jgz.oasystem.mapper;

import com.nnxy.jgz.oasystem.entity.ApplyFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author JGZ
 * CreateTime 2020/1/16 12:15
 * Email 1945282561@qq.com
 */
@Mapper
public interface ApplyFileMapper {

    /**
     * 插入任务文件
     * @param applyFile
     */
    void insert(ApplyFile applyFile);

    /**
     * 删除记录
     * @param fileList
     */
    void deleteByList(List<ApplyFile> fileList);

    /**
     * 通过id查询星系
     * @param fileId
     * @return
     */
    ApplyFile selectById(String fileId);
}
