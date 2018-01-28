####Netty之ByteBuf
> 工作原理

* 通过维护2个不同的索引：一个用于读取，一个用于写入。
* 任何write和read方法都会修改这2个索引值，而set和get则不会。

> ByteBuf的使用模式

* 堆缓冲

    最常用的ByteBuf模式是将数据存储在堆空间中，提供快速分配和释放
  
* 直接缓冲 
    
    通过JVM本地调用来分配内存，避免了每次IO操作之前都需要将数据复制到一个中间缓冲中，缺点是分配和销毁的代价很高。
    
* 复合缓冲区

    通过数据结构来实现相同实例的的共享
    
* 派生缓冲区
    
1. duplicate()
2. slice()
3. Unpooled.unmodifiableBuffer
4. order()
5. readSlice()
  
  这些方法都会返回一个新的ByteBuf实例，它具有自己的读索引，写索引和标记索引，然而内部的数据是共享的
  意味着一旦修改任何内容，它的源实例也会被修改，因此对于拷贝操作使用cope()
  
 * ByteBufHolder
 
 提供访问底层数据的方法
 1. content() 返回由这个ByteBufHolder持有的ByteBuf
 2. copy() 返回一个深拷贝
 3. duplicate 返回一个浅拷贝
    