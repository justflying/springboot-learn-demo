#### 1.简单队列模式：

​    具有一个生产者，一个QUEUE,一个消费者
​    P---->QUEUE---->C
​    [ 点击参考官网指导](https://www.rabbitmq.com/tutorials/tutorial-one-java.html )

#### 2.工作队列WorkQueue：

​    具有一个消费者，一个工作队列，多个消费者
​                      					|---->C
​    P---->WORK QUEUE--
​                      					|---->C
  

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



