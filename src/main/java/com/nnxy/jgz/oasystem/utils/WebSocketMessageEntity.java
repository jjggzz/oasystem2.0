package com.nnxy.jgz.oasystem.utils;

import lombok.Data;

/**
 * webSocket消息实体
 * @author JGZ
 * CreateTime 2019/12/27 10:47
 * Email 1945282561@qq.com
 */
@Data
public class WebSocketMessageEntity {
    /**
     * 消息类型
     * 0 聊天消息
     * 1 通知消息
     */
    private Integer type;

    /**
     * 发送人id
     */
    private String userId;

    /**
     * 发送人姓名
     */
    private String userName;
    /**
     * 消息内容
     */
    private String content;

    /**
     * 是否广播
     */
    private Boolean isBroadcast;

    /**
     * 如果是广播消息，这代表是部门id，如果不是广播消息代表目标用户id
     */
    private String targetId;


}
