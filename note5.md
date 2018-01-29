####EventLoop和Nettty的线程模型

一个EventLoop将由一个永远都不会的Thread驱动，同时任务可以直接提交给EventLoop实现
以立即执行或者调度执行。**根据配置和可用核心的不同，一个EventLoop可能会有多个实例用以优化资源的使用**
。EventLoop的parent方法可以返回所属Group的实例。
 
 Netty4中，所有的IO操作都由已分配的线程来执行 
 