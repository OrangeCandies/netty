####单元测试

> EmbeddedChannel概述

Netty提供的一种特殊的简化测试的组件，是一种特殊的channel,它将入站数据或者出战数据直接写在它的方法里以这种方式，可以直接了当地完成测试
它提供了一组关键的API

|名称|职责|
|---|----|
|writeInbound(Object...msg)|将模拟入站数据写入Channel中，可以通过readInbound()来获取这些信息|
|readInbound()| 从Channel中获取一个入站信息|
|writeOutbound(Object...msg)| 将模拟出站的数据写入Channel中，可以通过readOutbound()来获取这些信息|
|readOutbound()| 从Channel中获取一个出站信息|
