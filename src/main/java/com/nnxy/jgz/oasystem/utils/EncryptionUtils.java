package com.nnxy.jgz.oasystem.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 加密工具
 * @author JGZ
 * CreateTime 2019/12/18 10:32
 * Email 1945282561@qq.com
 */
public class EncryptionUtils {

    /**
     * MD5加密算法
     * @param factor 加密因子
     * @param source 源字符串
     * @param count 加密次数
     * @return
     */
    public static String encryptionByMD5(String factor,String source,Integer count){
        ByteSource credentialsSalt = ByteSource.Util.bytes(factor);
        return new SimpleHash("MD5",source,credentialsSalt,count).toString();
    }
}
