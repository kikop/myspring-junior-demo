package com.kikop.sftp;

import com.jcraft.jsch.*;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kikop
 * @version 1.0
 * @project mysftpdemo
 * @file SftpPoolFactory
 * @desc sftp连接池工厂
 * @date 2025/7/19
 * @time 7:37
 * @by IDE: IntelliJ IDEA
 */
public class SftpPoolFactory implements PooledObjectFactory<ChannelSftp> {

    private static final Logger logger = LoggerFactory.getLogger(SftpPoolFactory.class);

    private String host;
    private int port;
    private String userName;
    private String password;
    private String privateKeyPath;
    // 连接超时时间(毫秒)
    private int connectionTimeout;
    // 会话超时时间(毫秒)
    private int sessionTimeout;

    public SftpPoolFactory(String host, int port, String userName, String password) {
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.password = password;
        this.connectionTimeout = 30000;
        this.sessionTimeout = 30000;
    }

    public SftpPoolFactory(String host, int port, String userName, String password, String privateKeyPath) {
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.password = password;
        this.privateKeyPath = privateKeyPath;
        this.connectionTimeout = 30000;
        this.sessionTimeout = 30000;
    }

    /**
     * 1.激活sftp连接
     * 会话保持:
     * SFTP连接长时间无操作,可能被服务器断开,建议添加心跳机制 todo
     * @param pooledObject
     * @throws Exception
     */
    @Override
    public void activateObject(PooledObject<ChannelSftp> pooledObject) throws Exception {
        logger.info("激活sftp连接");
    }

    /**
     * 2.归还至sftp连接池
     *
     * @param pooledObject
     * @throws Exception
     */
    @Override
    public void passivateObject(PooledObject<ChannelSftp> pooledObject) throws Exception {
        logger.info("归还至sftp连接池");
    }

    /**
     * 3.连接验证
     *
     * @param pooledObject
     * @return
     */
    @Override
    public boolean validateObject(PooledObject<ChannelSftp> pooledObject) {

        try {
            logger.info("连接验证");
            ChannelSftp channelSftp = pooledObject.getObject();
            channelSftp.ls("/");
            return true;
        } catch (SftpException e) {
            //throw new RuntimeException(e);
            logger.warn("sftp连接验证失败,准备销毁连接", e);
        }
        return false;
    }

    /**
     * 4.销毁sftp连接
     *
     * @param pooledObject
     * @throws Exception
     */
    @Override
    public void destroyObject(PooledObject<ChannelSftp> pooledObject) throws Exception {
        ChannelSftp channelSftp = pooledObject.getObject();
        if (channelSftp != null) {
            try {
                channelSftp.disconnect();
            } finally {
                Session session = channelSftp.getSession();
                if (session != null) {
                    session.disconnect();
                }
            }
        }
        logger.info("销毁sftp连接");
    }

    /**
     * 5.创建sftp连接
     *
     * @return
     * @throws Exception
     */
    @Override
    public PooledObject<ChannelSftp> makeObject() throws Exception {
        JSch jSch = new JSch();
        if (privateKeyPath != null) {
            jSch.addIdentity(privateKeyPath);
        }
        Session session = jSch.getSession(userName, host, port);
        if (password != null) {
            session.setPassword(password);
        }
        // 配置连接参数
        session.setConfig("StrictHostKeyChecking", "no");

        // 基于JSch库的内置心跳(Session连接前设置) todo
        session.setServerAliveInterval(30000);
        session.setServerAliveCountMax(3);

        session.connect(connectionTimeout);


        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
        channelSftp.connect(sessionTimeout);
        // 支持中文文件名
        channelSftp.setFilenameEncoding("UTF-8");
        logger.info("创建sftp连接:{}@{}:{}", userName, host, port);
        return new DefaultPooledObject<>(channelSftp);
    }


}
