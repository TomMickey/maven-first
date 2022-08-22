package com.grgbanking.netty;

import org.jboss.netty.bootstrap.Bootstrap;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class TestNettyServerTest {

    private DatabaseGatewayPipelineFactory pipelineFactory;

    public void run(){
        // 构造一个服务端Bootstrap实例，并通过构造方法指定一个ChannelFactory实现
// 其中后两个参数分别是BOSS和WORK的线程池
        Bootstrap serverBootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));

// 注册用户自己实现的ChannelPipelineFactory
        serverBootstrap.setPipelineFactory(this.pipelineFactory);

// 调用bind等待客户端来连接
        ((ServerBootstrap) serverBootstrap).bind(new InetSocketAddress(9100));
    }
}
