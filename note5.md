####EventLoop和Nettty的线程模型####

一个EventLoop将由一个永远都不会的Thread驱动，同时任务可以直接提交给EventLoop实现
以立即执行或者调度执行。**根据配置和可用核心的不同，一个EventLoop可能会有多个实例用以优化资源的使用**
。EventLoop的parent方法可以返回所属Group的实例。
 
 Netty4中，所有的IO操作都由已分配的线程来执行 
 
 >任务调度
 
 可以使用Channel.eventLoop().schedule(new Runnable(),time,size)来实现定时任务
 可以使用Channel.eventLoop().scheduleAtFixedRate(Runnable,beign,interval,size)实现周期调度任务
 
 >实现细节
 
 Netty线程卓越的性能取决于对象当前线程身份的确定，如果当前线程正是支撑EventLoop的线程，那么所提交的代码将会被直接执行，否则会把该任务放到内部队列中
 以便稍后执行，
 每个EventLoop都会有它自己的任务队列，独立于其他EventLoop
 对于阻塞操作，建议使用一个专门的EventLoop解决
 **对于Channel使用ThreadLoacal解决一些问题时错误的，因为多个Channel可能会共享一个EventLoop，而一个EventLoop被一个线程所持有**
 对于OIO情况又有些不同，EventLoopGroup会为每一个Channel分配一个EventLoop
 
 ###Bootstrap###
 
 Bootstrap在引导的过程中 必须设置的3个组件
 * group()
 * channel()
 * handler()
 
 引导顺序
 1. 在bind()方法被调用时候，ServerBootStrap会创建一个新的ServerChannel实例
 2. ServerChannel会创建新的Channel来处理新的连接
 3. 新的Channel中也可以创建新的BootStrap 而通过channel.eventloop()来实现共享EventLoop
 
 >引导过程中添加多个ChannelHandler
 
 可以给bootstrap设置channelhandler时候添加一个ChannelInitializer实例
 它需要一个继承自Channel的类  在它被注册到EventLoop之后会被调用它的initChannel方法
 这样可以获取Pipeline实例 来组装handler链