package com.kikop.sftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author kikop
 * @version 1.0
 * @project mysftpdemo
 * @file SftpPoolFactory
 * @desc 多线程SFTP工具类(基于commons2_poll和连接池)
 * @date 2025/7/19
 * @time 7:37
 * @by IDE: IntelliJ IDEA
 */
public class MultiThreadSftpUtil {
    private static final Logger logger = LoggerFactory.getLogger(MultiThreadSftpUtil.class);
    private ObjectPool<ChannelSftp> sftpObjectPool;
    private ExecutorService threadPool;
    private int maxThread;

    public MultiThreadSftpUtil(SftpPoolFactory sftpPoolFactory, int poolSize, int maxThread) {
        // 1.配置连接池
        GenericObjectPoolConfig objectGenericObjectPoolConfig = new GenericObjectPoolConfig();
        // 最大连接阈值
        objectGenericObjectPoolConfig.setMaxTotal(poolSize);
        // 最大空闲连接数
        objectGenericObjectPoolConfig.setMaxIdle(poolSize / 2);
        // 最小空闲连接数
        objectGenericObjectPoolConfig.setMinIdle(5);
        // 借用验证
        objectGenericObjectPoolConfig.setTestOnBorrow(true);
        // 规划验证
        objectGenericObjectPoolConfig.setTestOnReturn(true);
        // 空闲超时时间
        objectGenericObjectPoolConfig.setMinEvictableIdleTimeMillis(60000);

        // 2.构建sftpPool
        this.sftpObjectPool = new GenericObjectPool<>(sftpPoolFactory, objectGenericObjectPoolConfig);
        this.maxThread = maxThread;
        this.threadPool = new ThreadPoolExecutor(maxThread, maxThread, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(), r -> new Thread(r, "sftp-task-" + r.hashCode()), new ThreadPoolExecutor.CallerRunsPolicy());

    }

    public Future<Boolean> uploadFileAsync(String localPath, String remotePath) {
        return threadPool.submit(() -> {
            ChannelSftp channelSftp = null;
            try {
                // 1.借用
                channelSftp = this.sftpObjectPool.borrowObject();
                createRemoteDirectory(channelSftp, remotePath);
                try (FileInputStream fileInputStream = new FileInputStream(localPath)) {
                    channelSftp.put(fileInputStream, remotePath);
                    logger.info("异步上传成功:{}->{}", localPath, remotePath);
                    return true;
                }
            } catch (Exception ex) {
                logger.error("异步上传失败:{}->{}|{}", localPath, remotePath, ex.getMessage(), ex);
                return false;
            } finally {
                if (channelSftp != null) {
                    // 2.finally中调用,避免连接池资源耗尽
                    // 归还
                    this.sftpObjectPool.returnObject(channelSftp);
                }
            }
        });
    }

    public List<Boolean> batchUploadFileSync(List<String> localPaths, String remoteDir) {

        try {
            // 1.异步上传
            List<Future<Boolean>> futureList = localPaths.stream().map(localPath -> {
                String remotePath = remoteDir + "/" + new File(localPath).getName();
                return uploadFileAsync(localPath, remotePath);
            }).collect(Collectors.toList());

            //  2.这里等待一下异步上传,再查询结果List<Boolean>
            return futureList.stream().map(future -> {
                try {
                    return future.get(60, TimeUnit.SECONDS);
                } catch (Exception ex) {
                    logger.error("批量上传超时或异常", ex);
                    return false;
                }
            }).collect(Collectors.toList());

        } catch (Exception ex) {
            logger.error("批量上传失败", ex);
            // 3.异常处理
            return localPaths.stream().map(p -> false).collect(Collectors.toList());
        }
    }

    private void createRemoteDirectory(ChannelSftp channelSftp, String remotePath) throws SftpException {
        String dirPath = remotePath;
        if (remotePath.contains("/")) {
            dirPath = remotePath.substring(0, remotePath.lastIndexOf("/"));
        }
        if (!dirPath.isEmpty() && !dirPath.equals("/")) {
            try {
                channelSftp.cd(dirPath);
            } catch (SftpException e) {
                //throw new RuntimeException(e);
                if (e.id == ChannelSftp.SSH_FX_NO_SUCH_FILE) {
                    createRemoteDirectory(channelSftp, dirPath);
                    channelSftp.mkdir(dirPath);
                    channelSftp.cd(dirPath);
                } else {
                    throw e;
                }
            }
        }
    }

    /**
     * 关闭资源
     */
    public void shutdown() {
        try {
            if (sftpObjectPool != null) {
                sftpObjectPool.close();
            }
        } catch (Exception ex) {
            logger.error("关闭sftp连接池失败", ex);
        }
        if (threadPool != null && !threadPool.isShutdown()) {
            threadPool.shutdown();
            try {
                if (!this.threadPool.awaitTermination(30, TimeUnit.SECONDS)) {
                    threadPool.shutdownNow();
                }
            } catch (InterruptedException ex) {
                threadPool.shutdownNow();
                Thread.currentThread().interrupt();
            }
            logger.info("线程池关闭");
        }

    }

    public Future<Boolean> downloadFileAsync(String localPath, String remotePath) {
        return threadPool.submit(() -> {
            ChannelSftp channelSftp = null;
            try {
                // 1.借用
                channelSftp = this.sftpObjectPool.borrowObject();
                File localFile = new File(localPath);
                File parentDir = localFile.getParentFile();
                if (!parentDir.exists()) {
                    parentDir.mkdir();
                }
                try (FileOutputStream fileOutputStream = new FileOutputStream(localFile)) {
                    channelSftp.get(remotePath, fileOutputStream);
                    logger.info("异步下载成功:{}->{}", remotePath, localPath);
                    return true;
                }
            } catch (Exception ex) {
                logger.error("异步下载失败:{}->{}|{}", remotePath, localPath, ex.getMessage(), ex);
                return false;
            } finally {
                if (channelSftp != null) {
                    // 2.归还
                    this.sftpObjectPool.returnObject(channelSftp);
                }
            }
        });
    }
}
