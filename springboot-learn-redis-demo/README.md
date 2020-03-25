### SpringBoot 整合 Redis
#### 1. Redis原始命令学习
#####  1.1 Redis数据类型介绍

官方介绍：

> - Binary-safe strings.
> - 二进制安全字符串类型
> - Lists: collections of string elements sorted according to the order of insertion. They are basically *linked lists*.
> - 根据插入顺序排序的集合，本质上链式列表。
> - Sets: collections of unique, unsorted string elements.
> - 唯一而又无序的字符串集合
> - Sorted sets, similar to Sets but where every string element is associated to a floating number value, called *score*. The elements are always taken sorted by their score, so unlike Sets it is possible to retrieve a range of elements (for example you may ask: give me the top 10, or the bottom 10).
> - 有序集合，类似Set,但是每一个字符串元素都和一个叫做score的浮点型数字相关联，元素总是根据他们的score排序，所以，与Set不同，它可以检索一系列元素。（例如：你可能要求：给我最上面10个，或者最下面10）
> - Hashes, which are maps composed of fields associated with values. Both the field and the value are strings. This is very similar to Ruby or Python hashes.
> - 哈希，它们是由与值关联的字段组成的映射。字段和值都是字符串，和Ruby 或者Python的hashes很像
> - Bit arrays (or simply bitmaps): it is possible, using special commands, to handle String values like an array of bits: you can set and clear individual bits, count all the bits set to 1, find the first set or unset bit, and so forth.
> - 位数组（或者叫位图）：可以使用特殊命令处理字符串值，如位数组：你可以设置和清除单个比特，数数所有设置为1的位，找到第一组或未设置的位，以此类推。
> - HyperLogLogs: this is a probabilistic data structure which is used in order to estimate the cardinality of a set. Don't be scared, it is simpler than it seems... See later in the HyperLogLog section of this tutorial.
> - 这是一种概率数据结构，用于估计集合的基数。别害怕，这比看上去简单.请参阅本教程的HyperLogLog部分后面的内容。
> - Streams: append-only collections of map-like entries that provide an abstract log data type. They are covered in depth in the [Introduction to Redis Streams](https://redis.io/topics/streams-intro).
> - 仅附加提供抽象日志数据类型的类映射项的集合。在Redis Streams 介绍中，他们被深入地讨论了。

##### 1.2 string类型基础命令介绍

```shell
# set name lisi  设置string 类型 key 为name, value 为lisi
> set name lisi   
OK
# get name  获取key为name ,对应的value
> get name
lisi
# set name zhangsan nx  当key name 不存在的时候，设置value为zhangsan,
> set name zhangsan nx 
0
# set name zhangsan xx  当key name存在的时候，设置value 为zhangsan
> set name zhangsan xx
OK
> set  counter 5
OK
# incr counter   设置一个key 为counter，然后value是数字的时候，该方法是自增 counter
> incr counter
6
# incrby counter 50  自增的数目是50
> incrby counter 50
56
# mset key1 value1 [key value]  批量设值
> mset a 10 b 20 c 30
OK
# mget key1 [key] 批量获取
> mget a b c
10
20
30
# exists key 查看key是否存在
> exists name
1
# del key  删除 key，删除以后再调用exists key 会发现结果为 0
> del name
1
> exists name
0
# type key 获取某个key的类型，如果key不存在为none
> type a
string
> type name
none
```

##### 1.3  Redis expires: keys with limited time to live 

> Before continuing with more complex data structures, we need to discuss another feature which works regardless of the value type, and is called **Redis expires**. Basically you can set a timeout for a key, which is a limited time to live. When the time to live elapses, the key is automatically destroyed, exactly as if the user called the [DEL](https://redis.io/commands/del) command with the key.
>
> 在介绍更多复杂结构的数据之前，我们需要讨论另一种无视值类型的的特征，就是Redis过期。基本上你可以为key设置一个过期时间，限值key的生存时间，当生命流逝的时候，更确切点的说如果用户调用key的del命令，key就被自动销毁了。
>
> A few quick info about Redis expires:
>
> - They can be set both using seconds or milliseconds precision.
> - However the expire time resolution is always 1 millisecond.
> - Information about expires are replicated and persisted on disk, the time virtually passes when your Redis server remains stopped (this means that Redis saves the date at which a key will expire).
>
> 一些快速消息关于Redis过期：
>
> - 它们可以精确到秒或者毫秒。
> - 然而，过期时间分辨率总是1毫秒。
> - 有关过期的信息将复制并保存在磁盘上，当您的Redis服务器仍然停止时，时间实际上会过去(这意味着Redis保存密钥过期的日期)。

```shell
# expire key 10  设置key过期时间为10s
> expire a 10
1
# 10s后查询发现不存在key 为a 的键值对
> get  a
null
# 集合set 命令一起的设置
> set mykey mvalue ex 10
OK
# 查看某个key剩余存活时间
> ttl mykey
4
```

##### 1.4 list类型基础命令介绍

```shell
# rpush key value 从右侧开始存放数据
> rpush mylist A
1
> rpush mylist B
2
# lpush key value 从左侧开始存放数据 有点类似于入栈
> lpush mylist first-element
3
# lrange mylist value 展示list中的元素
> lrange mylist 0 -1
first-element
A
B
# rpush key value1 value2 [value3] 可以一次存入多个
> rpush mylist 1 2 3 4 5 "foo bar"
9
> lrange mylist 0 -1
first-element
A
B
1
2
3
4
5
foo bar
# rpop key 从右边开始把元素拿出来，当所有元素都没有的时候，就会返回一个null
> rpop mylist
foo bar
# 再使用查看，发现字后一个元素不见了
> lrange mylist 0 -1
first-element
A
B
1
2
3
4
5
# 截断并只保留指定的值，ltrim key [start,end]  其中index 可以为负值，
> ltrim mylist 0 2
OK

```

##### 1.5 hash(字典)类型基础命令操作

```shell
# 设置值 hset key field value
> hset books java "think in java"
1
> hset books python "pyhthon cookbook"
1
# 获取值 hget key field
> hget books java
think in java
# 获取全部 hgetall key
> hgetall books
java
think in java
python
pyhthon cookbook
# 获取长度 hlen key
> hlen books
2
# 批量设值 hmset key field value [field1 value1]
> hmset books go "think in go" golang "think in golang"
OK
> hlen books
4
# 同时 也可以对hash对象的某个字段进行增长
> hmset  user  name "zhangsan" age 20
2
> hincrby user age 2
22
> hget user age
22
```

##### 1.6 set类型基础命令

```shell
# sadd key value [value1]添加元素
> sadd mybooks java
1
> sadd mybooks python go
2
# 重复添加的时候发现并不会成功,返回结果是0
> sadd mybooks go
0
# 查看set成员，会发现和插入顺序并不相同，set是无序的
> smembers mybooks
go
python
java
# 查看某个元素是否为集合元素 ，是的话返回1 不是的话返回0
> sismember mybooks java
1
# 查看元素成员个数
> scard mybooks
3
# 弹出一个元素
> spop mybooks
java
```

##### 1.7 zset(有序集合)类型基础命令

 ```shell
# 添加元素
> zadd zbooks 9.0 "think in java"
1
> zadd zbooks 8.9 "java concurrency"
1
> zadd zbooks 8.6 "java cookbook"
1
# 按score 排序列出，参数区间为排名范围
> zrange zbooks 0 -1
java cookbook
java concurrency
think in java
# 按score逆序列出，参数区间为排名范围
> zrange zbooks 0 -1
java cookbook
java concurrency
think in java
# 查看元素个数
> zcard zbooks
3
# 根据value 获取对应的score
> zscore zbooks "java concurrency"
8.9000000000000004
# 根据value 查看排名 从0开始
> zrank zbooks "java cookbook"
0
# 根据分值区间遍历 zset
> zrangebyscore zbooks 0 8.91
java cookbook
java concurrency
 ```

