package com.nnxy.jgz.oasystem.mapper;

import com.nnxy.jgz.oasystem.entity.Position;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author JGZ
 * CreateTime 2019/12/19 16:44
 * Email 1945282561@qq.com
 */
@Mapper
public interface PositionMapper {
    /**
     *获取职位列表
     * @return
     */
    List<Position> positionList();

    /**
     * 插入职位
     * @param position
     */
    void insert(Position position);

    /**
     * 插入职位和权限的中间表
     * @param position
     */
    void insertPositionPermission(Position position);

    /**
     * 通过id删除职位
     * @param positionId
     */
    void deleteById(String positionId);

    /**
     * 修改职位信息
     * @param position
     */
    void update(Position position);

    /**
     * 通过职位id删除职位权限表中的记录
     * @param positionId
     */
    void deletePositionPermissionByPositionId(String positionId);
}
