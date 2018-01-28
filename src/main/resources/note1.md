###Netty的组件和设计
***
####概述
* channel 相比于JDK的channel做了包装，提供了简便强大的Api
* EventLoop 像是Selector 控制流，多线程操作，并发
* ChannelFuture 异步通知多线程相关
***
####接口相关
>Channel接口
* 提供基本的IO操作 
* 是对Socket操作的极大简化
>EventLoop接口
* 多个EventLoop组成EventLoopGroup
* channel注册在EventLoop上
* EventLoop处理IO事件
* EventLoop由专门线程持有
>ChannelFuture接口
* netty中所有的操作都是异步的 和多线程的Future原理和作用相似
* ChannelFuture上注册监听器 以便在某个操作成功之后得到通知
>ChannelHandler接口
* 简单看来是处理进站和出站的处理器
* 实际上是受“事件”触发的 用于几乎所有的动作 例如产生异常，格式转换
>ChannelPipeline接口
* ChannelPipeline为ChannelHandler提供了容器，当ChannelHandler被创建的时候，
    自动分配到它的Pipeline的接口上初始化顺序如下
 1. 一个ChannelInitializer的实现被注册到BootStrap中
 2. ChannelInitializer的initChannel()被调用将在ChannelPipeline中安装一组ChannelHandler
 3. ChannelInitializer将自己从Pipeline中移除
 
    **链式处理机制**
* 当ChannelPipeline和ChannelHandler绑定后会分配一个ChanelHandlerContext的对象，用于获取底层Channel
* 在Netty中，有2种发送消息的方式，一种是直接写到Channel中，会导致消息从尾端流动，而写入ChannelHandlerContext则从下一端流动
>BootStrap类
* 用来掩盖不同网络行为的事实
* ServerBootStrap需要2个EventLoopGroup其中一组用于监听端口，一组用于Socket通信(事实上平时写JavaNio的时候只使用一个Selector)

