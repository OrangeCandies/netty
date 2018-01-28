ChannelHandler家族

> Channel生命周期

| 状态 |描述|
|-----|---------|
|ChannelUnregistered| Channel已经被创建还未注册EventLoop|
|ChannelRegistered| Channel已经被注册到EventLoop|
|ChannelActive|Channel处于活动的状态，已经连接好远程节点，可以收发数据|
|ChannelIncative| 没有连接好远程节点|

**流程图**

ChannelUnregistered -> ChannelIncative -> ChannelActive -> ChannelUnregistered

> ChannelHandler的生命周期

|类型|描述|
|----|-----|
|handlerAdded| 当把ChannelHandler添加到Pipeline中被调用|
|handlerRemoved| 当把channelHandler从Pipeline中移除时调用|
|exceptionCaught| 当处理过程中发生异常调用|

Netty定义了下面两个重要的接口
* channelInnboundHandler-- 处理入站数据以及各种状态的变化
* ChannelOutboundHandler-- 处理出数据并允许拦截所有的操作

