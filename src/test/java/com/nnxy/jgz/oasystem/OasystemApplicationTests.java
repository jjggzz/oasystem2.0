package com.nnxy.jgz.oasystem;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class OasystemApplicationTests {

    @Test
    void contextLoads() {
        String fun = "MD5";
        String pwd = "123456";
        ByteSource credentialsSalt = ByteSource.Util.bytes("admin");
        int i = 1024;
        SimpleHash simpleHash = new SimpleHash(fun, pwd, credentialsSalt, i);
        System.out.println(simpleHash);

    }

}
