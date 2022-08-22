package com.grgbanking.netty;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.execution.ExecutionHandler;

public class DatabaseGatewayPipelineFactory implements ChannelPipelineFactory {

    private final ExecutionHandler executionHandler;

    public DatabaseGatewayPipelineFactory(ExecutionHandler executionHandler) {
        this.executionHandler = executionHandler;
    }

    public ChannelPipeline getPipeline() {
        return Channels.pipeline(
                null,
                null,
                executionHandler, // 多个pipeline之间必须共享同一个ExecutionHandler
                null);//业务逻辑handler，IO密集
    }
}
