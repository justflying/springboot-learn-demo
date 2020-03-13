### 阅读本文档之前，强烈要求对照RabbitMQ的官方文档，里面有队列图形，有助于理解。 [点击这里](https://www.rabbitmq.com/getstarted.html)

#### 1.简单队列模式：

​    具有一个生产者，一个QUEUE,一个消费者
​    [ 点击参考官网指导](https://www.rabbitmq.com/tutorials/tutorial-one-java.html )

#### 2.工作队列WorkQueue：

​    具有一个消费者，一个工作队列，多个消费者


试验发现消费者1和消费者2处理的消息量是一样的，可以说明WORKQUEUE默认采用的是轮训分发（round-robin）

[点击参考官网指导](https://www.rabbitmq.com/tutorials/tutorial-two-java.html)

#### 3.工作队列WorkQueue fair模式

​     该模式 第一步，在消费者里面加上 channel.basicQos(1) 保证一次只分发一个，
​     同时channel.basicConsume的第二个参数ack确认，修改为false,取消自动应答， 并处理完毕后手动应答

​	channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);

```java
 DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" ConsumerOne Received: " + message);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                System.out.println(" ConsumerOne is Done ");
                // 应答消费完毕
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
            }
        };
channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> {});
```

公平模式之后，测试发现，睡眠时间短的消费者，消费的消息多，睡眠时间长的消费者，消费的消息少。

> Using this code we can be sure that even if you kill a worker using CTRL+C while it was processing a message, nothing will be lost. Soon after the worker dies all unacknowledged messages will be redelivered.
>
> 使用上面的代码能够及时我们使用了CtrL + C 停止了一个消费者，也不会有消息丢失，消费者死掉之后，没有被消费的消息会被重新分发



但是这样会有一个问题就是：Forgotten acknowledgment

在官网上有这样一段介绍 ：

> #### Forgotten acknowledgment
>
> It's a common mistake to miss the **basicAck**. It's an easy error, but the consequences are serious. Messages will be redelivered when your client quits (which may look like random redelivery), but RabbitMQ will eat more and more memory as it won't be able to release any unacked messages.
>
> 丢失basicAck是很常见的错误，很容易出错，但是结果是严重的，当你的客户端停止消息将会被重新分发（看起来像是随机分发），但是RabbitMQ会因为无法释放任何unacked的消息占用越来越多内存。
>
> In order to debug this kind of mistake you can use rabbitmqctl to print the messages_unacknowledged field:
>
> 为了调试这种错误，你可以使用 **rabbitmqctl**  命令打印 **messages_unacknowledged** 字段
>
> ```bash
> sudo rabbitmqctl list_queues name messages_ready messages_unacknowledged
> ```
>
> On Windows, drop the sudo:
>
> 在**Windows**系统上，删掉sudo
>
> ```bash
> rabbitmqctl.bat list_queues name messages_ready messages_unacknowledged
> ```

上面只能保证客户端出问题，消息不会丢失，但是当RabbitMQ Server 出问题的时候，上面的就解决不了了，这时候我们需要**Message Durability**

修改生产者和消费者的代码如下

```java
boolean durable = true;
channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
```

这里同样会出问题报错

> Although this command is correct by itself, it won't work in our present setup. That's because we've already defined a queue called **hello** which is not durable. RabbitMQ doesn't allow you to redefine an existing queue with different parameters and will return an error to any program that tries to do that. But there is a quick workaround - let's declare a queue with different name, for example **task_queue**:
>
> 尽管这个命令本身是正确的，但是在当前设置下是不可能成功的，因为我们已经定义了非持久化队列 **hello**(在这里说明我们代码中定义的是QUEUE_NAME)，RabbitMQ不允许使用不同的参数重定义一个已经存在的队列，并且会返回一个错误给任何试图这样做的程序，但是有一个快速的解决办法，让我们用不同的名称声明一个队列，例如：**task_name** (emmmm,顺便吐槽一句，这是什么快速解决办法，这是另起炉灶，不跟以前的小伙伴玩了)

#### 4.订阅模式

##### 4.1  fanout模式：不处理路由模式 (采用网上的说法)  对应的代码代码在ps包下

引用官网对它的一点介绍

> The fanout exchange is very simple. As you can probably guess from the name, it just broadcasts all the messages it receives to all the queues it knows. And that's exactly what we need for our logger.
>
> 不处理路由交换机非常简单，就像你可以从名字猜出来（说实话我真没从名字猜出来，fanout的汉语翻译是扇形，网上总结为不处理路由），它会**广播**全部的消息到它知道的**所有**队列，并且这就是我们logger需要的.

后续的 代码写法中，我写的和官网有点不太一样，官网介绍了Temporary queues 说了一堆，最后总结两点

> Firstly, whenever we connect to Rabbit we need a fresh, empty queue. To do this we could create a queue with a random name, or, even better - let the server choose a random queue name for us.
>
> 首先：不论什么时候我们连接Rabbit，我们需要一个新的，空的队列。为了做到这一点，我们可以创建一个随机名字的队列，最好服务端给我们创建一个随机队列。
>
> Secondly, once we disconnect the consumer the queue should be automatically deleted.
>
> 其次：一旦我们断开消费者这个队列应该自动删除。



为了上面两点，所以官网采用的是服务器默认创建的队列，没消费者连接的时候自动删除，而我写的demo采用了指定队列的方式（原因是当时不明白官方文档为什么用默认的，然后想尝试使用自己指定的队列可不可以，事实证明可以，写这个文档的时候，回去认真的看了上面的那段两个点，理解了官网demo的作用，既然是测试，两者无所谓，实际应用的话，考虑一下实际生产业务）

生产者需要添加声明交换机

```java
channel.exchangeDeclare("logs", "fanout");
```

同时发布消息的时候，指定交换机

```java
channel.basicPublish( "logs", "", null, message.getBytes());
```



消费者中声明交换机以及创建一个默认队列,然后进行默认队列绑定交换机

```java
channel.exchangeDeclare("logs","fanout"); 
/**
  * In the Java client, when we supply no parameters to queueDeclare() we create a         * non-durable, exclusive, autodelete queue with a generated name:
  * Java 客户端中，当我们不给queueDeclare()提供参数的时候，会创建一个不持久化、独占、自动删除的默认名字的   * 队列
  */
String queueName = channel.queueDeclare().getQueue();
channel.queueBind(queueName, "logs", "");
```

这样一个使用Temporary queues 的demo就完成了。[参考官方文档](https://www.rabbitmq.com/tutorials/tutorial-three-java.html)

##### 4.2 direct模式：路由模式 (采用网上的说法)

对比不处理路由模式，路由模式在代码中有一点小小的变动

生产者中：

```java
// 指定为direct模式
channel.exchangeDeclare(EXCHANGE_NAME,"direct");
// 在routingKey那里你要指定对应的routingKey，这样发送的消息会通过交换机指定到路由的队列中
channel.basicPublish(EXCHANGE_NAME,"error",null,msg.getBytes(StandardCharsets.UTF_8));
```

消费者1：

```java

		channel.exchangeDeclare(EXCHANGE_NAME,"direct");
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String error_routing_key = "error";
        String info_routing_key = "info";
        String warn_routing_key = "warn";
		// 可以绑定多个routingKey
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME, error_routing_key);
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME, info_routing_key);
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME, warn_routing_key);
```

消费者2:

```java
		channel.exchangeDeclare(EXCHANGE_NAME,"direct");
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String info_routing_key = "error";
		channel.queueBind(QUEUE_NAME,EXCHANGE_NAME, info_routing_key);

```

```java
// 生产者中当消息发布到routingKey= "error"的时候，会发现消费者1,2都能收到
channel.basicPublish(EXCHANGE_NAME,"error",null,msg.getBytes(StandardCharsets.UTF_8));
// 生产者中当消息发布到routingKey= "info"的时候，会发现消费者1
channel.basicPublish(EXCHANGE_NAME,"info",null,msg.getBytes(StandardCharsets.UTF_8));
```

##### 4.3 topic 模式：主题模式

引自官网：

> Although using the direct exchange improved our system, it still has limitations - it can't do routing based on multiple criteria.
>
> 尽管使用路由交换机改进了系统，但它始终有限制，不能基于多规则路由。
>
> ........
>
> Messages sent to a topic exchange can't have an arbitrary routing_key - it must be a list of words, delimited by dots. The words can be anything, but usually they specify some features connected to the message. A few valid routing key examples: "stock.usd.nyse", "nyse.vmw", "quick.orange.rabbit". There can be as many words in the routing key as you like, up to the limit of 255 bytes.
>
> 发往主题交换机的消息不能是任意的routing_key,必须是被逗号分隔的单词列表。可以使用任意单词，但是通常有一些特殊的象征和消息关联起来。一些有效的routing key如下："stock.usd.nyse", "nyse.vmw", "quick.orange.rabbit"。只要你愿意routing key可以不超过255个字节的任意数量的单词。

想要使用好Topic模式就必要知道这两key

> - \* (star) can substitute for exactly one word.    只能代替一个单词
> - \# (hash) can substitute for zero or more words. 可以代替0或者多个单词

> #### Topic exchange
>
> **主题交换机**
>
> Topic exchange is powerful and can behave like other exchanges.
>
> 主题交换机强大，并且可以当做其他交换机使用
>
> When a queue is bound with "#" (hash) binding key - it will receive all the messages, regardless of the routing key - like in fanout exchange.
>
> 当一个队列被#绑定为key，它会收到所有消息，无视一切routing key, 就像不处理路由交换机
>
> When special characters, "*" (star) and "#" (hash), aren't used in bindings, the topic exchange will behave just like a direct one.
>
> 当特殊符号 * 和 # 不作为key绑定的时候，主题交换机就像一个路由交换机
>
> 

介绍了那么多，下面就来讲下代码层面主要区别

生产者

```java
channel.exchangeDeclare("exchange_topic","topic");
```

消费者1: 

```java
 		// 3. 声明（创建）一个交换机
        channel.exchangeDeclare("exchange_topic","topic");

        // 4. 创建一个Queue
        String queue = channel.queueDeclare().getQueue();

        String key = "test.#";

        channel.queueBind(queue,"exchange_topic",key);
```

 消费者2：

```java
 		// 3. 声明（创建）一个交换机
        channel.exchangeDeclare("exchange_topic","topic");

        // 4. 创建一个Queue
        String queue = channel.queueDeclare().getQueue();

        String key = "test.*";

        channel.queueBind(queue,"exchange_topic",key);
```

结果：

```java
// 当生产者的routing key 为 test.info的时候，消费者1和2都能收到
channel.basicPublish(EXCHANGE_NAME,"test.info",null,msg.getBytes());
// 当生产者的routing key 为 test.info.xxx 的时候，只有消费者1能收到
channel.basicPublish(EXCHANGE_NAME,"test.info.xxx",null,msg.getBytes());

```

#### 5.rpc [参考官网](https://www.rabbitmq.com/tutorials/tutorial-six-java.html)

> In the [second tutorial](https://www.rabbitmq.com/tutorials/tutorial-two-java.html) we learned how to use *Work Queues* to distribute time-consuming tasks among multiple workers.
>
> 在第二个指南里我们学习了怎么用工作队列在多个消费者分配耗时的任务
>
> But what if we need to run a function on a remote computer and wait for the result? Well, that's a different story. This pattern is commonly known as *Remote Procedure Call* or *RPC*.
>
> 但是如果我们需要在远程电脑上运行函数并且等待结果呢？那就另当别论了，这种模式通常称为远程过程调用或RPC

#### 6.Publisher Confirms （消息确认机制）[参考官网](https://www.rabbitmq.com/tutorials/tutorial-seven-java.html)

引自官网：

> [Publisher confirms](https://www.rabbitmq.com/confirms.html#publisher-confirms) are a RabbitMQ extension to implement reliable publishing. When publisher confirms are enabled on a channel, messages the client publishes are confirmed asynchronously by the broker, meaning they have been taken care of on the server side.
>
> 消息确认机制是RabbitMQ 实现可信赖上传消息的扩展，当一个channel开启了消息确认机制，生产者发布的消息将会被broker 异步确认，意味着他们已经被服务端处理了。

##### 6.1  消息确认机制之串行化 

又分为单独和批量

##### 6.2 消息确认机制之异步