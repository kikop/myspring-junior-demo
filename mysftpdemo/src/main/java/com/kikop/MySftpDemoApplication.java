package com.kikop;

import com.kikop.sftp.MultiThreadSftpUtil;
import com.kikop.sftp.SftpPoolFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Future;

/**
 * @author kikop
 * @version 1.0
 * @project mysftpdemo
 * @file App
 * @desc Hello world!
 * @date 2025/7/19
 * @time 7:37
 * @by IDE: IntelliJ IDEA
 */
public class MySftpDemoApplication {

    private static final Logger logger = LoggerFactory.getLogger(MySftpDemoApplication.class);

    public static void main(String[] args) {

    }

    private static void testLog(){

        logger.trace("这是 TRACE 级别的日志");
        logger.debug("这是 DEBUG 级别的日志");
        logger.info("这是 INFO 级别的日志");
        logger.warn("这是 WARN 级别的日志");
        logger.error("这是 ERROR 级别的日志");
    }

    private static void testSftp(){

        SftpPoolFactory sftpPoolFactory = new SftpPoolFactory("sftp.example.com", 22, "userName", "password");
        // 连接池大小,
        // 线程池大小
        MultiThreadSftpUtil multiThreadSftpUtil = new MultiThreadSftpUtil(sftpPoolFactory, 10, 5);

        try {
            Future<Boolean> uploadFileAsyncFuture = multiThreadSftpUtil.uploadFileAsync("/local/path/file1.txt", "/remote/path/file1.txt");
            try {
                Boolean uploadFileAsynResult = uploadFileAsyncFuture.get();
                System.out.println("单文件上传结果:" + uploadFileAsynResult);
            } catch (Exception ex) {
                System.out.println("多线程操作sftp异常");
            }

        } finally {
            multiThreadSftpUtil.shutdown();
        }
    }
}
