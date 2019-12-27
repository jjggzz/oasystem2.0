package com.nnxy.jgz.oasystem.controller;

import com.alibaba.fastjson.JSON;
import com.nnxy.jgz.oasystem.utils.WebSocketMessageEntity;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * webSocket服务类
 * @author JGZ
 * CreateTime 2019/12/27 9:42
 * Email 1945282561@qq.com
 */
@ServerEndpoint(value = "/webSocket/{userId}/{departmentId}")
@Component
public class WebSocketServer {

    /**
     * 存储当前连接的对象列表
     * 以depId为大分类
     * userid为小分类
     */
    private static ConcurrentHashMap<String,ConcurrentHashMap<String,WebSocketServer>>
            concurrentHashMap = new ConcurrentHashMap<String,ConcurrentHashMap<String,WebSocketServer>>();

    static {
        //初始化公共聊天室
        concurrentHashMap.put("public",new ConcurrentHashMap<String,WebSocketServer>());
    }

    /**
     * 与某个会话对象的连接
     */
    private Session session;

    /**
     * 构造器
     */
    public WebSocketServer(){
        System.out.println("ws初始化");
    }

    /**
     * 建立连接调用的方法
     * @param departmentId
     * @param userId
     * @param session
     */
    @OnOpen
    public void open(@PathParam("departmentId") String departmentId,
                     @PathParam("userId") String userId,
                     Session session){
        System.out.println("用户" + userId + "请求建立连接,部门号为：" + departmentId);
        //设置连接对象
        this.session = session;
        //放入工共的聊天室
        ConcurrentHashMap<String, WebSocketServer> aPublic = concurrentHashMap.get("public");
        aPublic.put(userId,this);
        //如果有部门则放入部门聊天室
        if(departmentId!=null && !"undefined".equals(departmentId)){
            ConcurrentHashMap<String, WebSocketServer> dep = concurrentHashMap.get(departmentId);
            if(dep != null){
                //如果部门聊天室存在，直接放入
                dep.put(userId,this);
            }
            else{
                //若不存在则创建一个放入
                ConcurrentHashMap<String, WebSocketServer> c = new ConcurrentHashMap<String, WebSocketServer>();
                c.put(userId,this);
                concurrentHashMap.put(departmentId,c);
            }

        }

    }


    /**
     * 关闭连接掉用的方法
     * @param departmentId
     * @param userId
     */
    @OnClose
    public void close(@PathParam("departmentId") String departmentId,
                      @PathParam("userId")String userId){
        System.out.println(userId + "用户断开连接，部门号为：" + departmentId);
        //从工共聊天室退出
        ConcurrentHashMap<String, WebSocketServer> aPublic = concurrentHashMap.get("public");
        aPublic.remove(userId);
        if(departmentId!=null && !"undefined".equals(departmentId)){
            //如果有部门则从部门聊天退出
            ConcurrentHashMap<String, WebSocketServer> dep = concurrentHashMap.get(departmentId);
            dep.remove(userId);
        }
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void error(Session session, Throwable error) {
        try {
            session.getBasicRemote().sendText("服务端发生错误");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 接收到客户端消息时调用
     * @param message
     * @param session
     */
    @OnMessage
    public void message(String message,Session session){
        //将前端传递的消息转换为自己定义的消息实体
        WebSocketMessageEntity messageEntity = JSON.parseObject(message, WebSocketMessageEntity.class);
        if(messageEntity.getType() == 1){
            //如果是通知消息，群发
            broadcast(message,"public");
        }
        if(messageEntity.getType() == 0){
            //如果是聊天消息，判断是否广播
            if(messageEntity.getIsBroadcast()){
                //如果是聊天广播则发送到对应的部门
                broadcast(message,messageEntity.getTargetId());
            }
            else{
                //发到对应的个人
                toOne(message,messageEntity.getTargetId());
            }
        }

    }

    /**
     * 群发消息到目标频道
     * @param message
     */
    public static void broadcast(String message,String targetId){
        concurrentHashMap.forEach((k,v)->{
            if(k.equals(targetId)){
                //如果满足对应关系
                //群发消息到对应的部门
                v.forEach((k2,v2)->{
                    try {
                        v2.sendMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    /**
     * 发送到对应的个人
     * @param message
     * @param targetId
     */
    public static void toOne(String message,String targetId){
        ConcurrentHashMap<String, WebSocketServer> aPublic = concurrentHashMap.get("public");
        aPublic.forEach((k1,v1)->{
            //符合id
            if(k1.equals(targetId)){
                try {
                    //发送到对应的个人
                    v1.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 发送消息
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }



}
