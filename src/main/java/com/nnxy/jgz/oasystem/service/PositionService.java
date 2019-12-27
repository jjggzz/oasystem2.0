package com.nnxy.jgz.oasystem.service;

import com.nnxy.jgz.oasystem.entity.Position;

import java.util.List;

/**
 * @author JGZ
 * CreateTime 2019/12/19 16:42
 * Email 1945282561@qq.com
 */
public interface PositionService {

    /**
     * 获取职位列表
     * @return
     */
    List<Position> positionList();

    /**
     * 添加职位
     * @param position
     */
    void addPosition(Position position);

    /**
     * 删除职位
     * @param positionId
     */
    void delPositionById(String positionId);

    /**
     * 修改职位
     * @param position
     */
    void alterPosition(Position position);
}
