package com.grgbanking.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.extern.flogger.Flogger;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class ConcurrentPerformanceServerHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = Logger.getLogger("ConcurrentPerformanceServerHandler");

    AtomicInteger counter = new AtomicInteger(0);
    static ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    static ExecutorService executorService = Executors.newFixedThreadPool(100);

    private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(
                            Unpooled.copiedBuffer("HEARTBEAT", CharsetUtil.UTF_8));

    public ConcurrentPerformanceServerHandler(){
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduledExecutorService.scheduleAtFixedRate(() ->{
            int qps = counter.getAndSet(0);
            //System.out.println("The Server QPS is : " + qps);
        },0, 1000, TimeUnit.MILLISECONDS);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //executor.
        ByteBuf in = (ByteBuf) msg;
        try {
            while (in.isReadable()) { // (1)
                logger.info(String.valueOf(((char) in.readByte())));
                System.out.flush();
            }
        } finally {
            ReferenceCountUtil.release(msg); // (2)
        }
        executorService.execute(() ->{
            counter.incrementAndGet();
            Random random = new Random();
            try {
                TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
     public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        logger.info("关闭连接----");
                     if(evt instanceof IdleStateEvent) {
                             // 发送心跳到远端
                             ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate())
                                 .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);    // 关闭连接
                         } else {
                             // 传递给下一个处理程序
                             super.userEventTriggered(ctx, evt);
                         }
    }
}


