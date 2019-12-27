package com.nnxy.jgz.oasystem.dto;

import com.nnxy.jgz.oasystem.entity.Position;
import lombok.Data;

/**
 * @author JGZ
 * CreateTime 2019/12/19 17:01
 * Email 1945282561@qq.com
 */
@Data
public class PositionDto  {
    private String positionName;
    private String[] permissionId;
}
