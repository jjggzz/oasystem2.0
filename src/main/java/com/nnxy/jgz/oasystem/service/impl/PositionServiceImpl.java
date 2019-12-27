package com.nnxy.jgz.oasystem.service.impl;

import com.nnxy.jgz.oasystem.entity.Position;
import com.nnxy.jgz.oasystem.mapper.PositionMapper;
import com.nnxy.jgz.oasystem.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author JGZ
 * CreateTime 2019/12/19 16:43
 * Email 1945282561@qq.com
 */
@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    public PositionMapper positionMapper;

    @Override
    public List<Position> positionList() {
        return positionMapper.positionList();
    }

    @Override
    public void addPosition(Position position) {

        positionMapper.insert(position);
        if(position.getPermissionList().size() != 0){
            //如果具有权限
            positionMapper.insertPositionPermission(position);
        }
    }

    @Override
    public void delPositionById(String positionId) {
        positionMapper.deleteById(positionId);
    }

    @Override
    public void alterPosition(Position position) {
        //修改职位表
        positionMapper.update(position);
        //删除职位权限表
        positionMapper.deletePositionPermissionByPositionId(position.getPositionId());
        if(position.getPermissionList().size() != 0){
            //如果具有权限
            positionMapper.insertPositionPermission(position);
        }

    }
}
