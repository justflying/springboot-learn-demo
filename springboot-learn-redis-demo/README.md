### SpringBoot 整合 Redis
### 1. Redis原始命令学习
####  1.1 Redis数据类型介绍

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

#### 1.2 string类型基础命令介绍

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

#### 1.3  Redis expires: keys with limited time to live 

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

#### 1.4 list类型基础命令介绍

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

#### 1.5 hash(字典)类型基础命令操作

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

#### 1.6 set类型基础命令

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

#### 1.7 zset(有序集合)类型基础命令

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

### 2.Springboot整合Jedis

首先针对springboot低版本的与jedis整合,如果想使用，建议建立三个子模块，然后进行分别测试，我在这里只用了一个模块，主要原因是，我自己写的三个整合，我知道该去注释哪些代码，让三个启动以后平安无事的运行我想验证的东西，如果对这三种不了解，不建议直接运行这个模块，毕竟刚上来就各种排错，积极性就会被打击，建立三个模块，互不干扰，按照我下面给的步骤，一个一个的引入，基本不会有什么问题。

#### 2.1. 先把依赖引入

```xml
        <dependency>
            <groupId>redis.clients</groupId>
            <artifactId>jedis</artifactId>
        </dependency>

        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-pool2</artifactId>
        </dependency>
	    <!--非必须引入包，是为了后续偷懒-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

```

#### 2.2 再配置application-jedis.yml 

因为jedis会和后续引入的lettuce冲突这里使用其名字做个配置文件

```yml
spring:
  redis:
    port: 6379
    password: myroot
    host: 192.168.211.133
    jedis:
      pool:
        max-active: 2
        min-idle: 1
        max-idle: 2
    timeout: 2000
```

#### 2.3 开始上java代码 JedisConfig.java

```java
package com.wanyu.springboot.learn.redis.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@Slf4j
public class JedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;

    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxTotal(maxActive);
        return jedisPoolConfig;
    }

    @Bean
    public JedisPool jedisPool(){
        log.info("JedisPool连接成功：端口：{}，主机：{}",port,host);
        return new JedisPool(jedisPoolConfig(),host,port,timeout,password);
    }
}

```

UserController.java 名字可以随意

```java
package com.wanyu.springboot.learn.redis.demo.controller;

import com.wanyu.springboot.learn.redis.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/redis")
public class UserController {


    @Autowired
    private IUserService userService;


    @GetMapping(value = "/jedis/{key}")
    public String getStringValue(@PathVariable("key") String key){
        return userService.getStringByJedis(key);
    }

}
```

IUserService.java

```java
package com.wanyu.springboot.learn.redis.demo.service;

public interface IUserService {

    String getStringByJedis(String key);
}
```

UserServiceImpl.java

```java
package com.wanyu.springboot.learn.redis.demo.service.impl;

import com.wanyu.springboot.learn.redis.demo.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {


    @Autowired
    private JedisPool jedisPool;


    @Override
    public String getStringValueByJedis(String key) {
        Jedis jedis = jedisPool.getResource();
        try{
            if (jedis.exists(key)) {
                log.info("从redis中获取值");
                return jedis.get(key);
            }else{
                // 这里是模拟从数据库拿数据，真实应用的时候请把这段代码替换为操作数据库的代码
                log.info("从数据库中获取值");
                String value = "learn jedis";
                jedis.set(key,value);
                return value;
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally{
            jedis.close();
        }
        return null;
    }
}

```

然后启动的时候，spring.profiles.active 设置为jedis ,启动成功后浏览器访问localhost:8080/redis/jedis/name就可以完成一个小的redis调用了，这里因为不是主要讲Jedis客户端所以不做太详细的配置，上面的代码中有个问题就是jedis需要代码中关闭（不知道是不是我配置的有问题，如果有更好的办法请告诉我，issue里面提示，谢谢）

#### 2.4 基于jedis的分布式锁

RedisLock.java

```java
package com.wanyu.springboot.learn.redis.demo.util;

import java.util.concurrent.TimeUnit;

public interface RedisLock {


    boolean tryGetDistributeLock(String lockKey,String lockValue,
                                 Integer expireTime, TimeUnit timeUnit);

    boolean releaseDistributeLock(String lockKey,String lockValue);
}
```

JedisDistributeLock.java

```java
package com.wanyu.springboot.learn.redis.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Component
public class JedisDistributeLock implements  RedisLock{


    @Autowired
    private JedisPool jedisPool;

    @Override
    public boolean tryGetDistributeLock(String lockKey, String lockValue,
                                        Integer expireTime, TimeUnit timeUnit) {
        Jedis jedis = jedisPool.getResource();
        SetParams setParams = new SetParams();
        setParams.nx().px(timeUnit.toMillis(expireTime));
        boolean ok = "OK".equals(jedis.set(lockKey, lockValue, setParams));
        jedis.close();
        return ok;
    }


    @Override
    public boolean releaseDistributeLock(String lockKey, String lockValue) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Jedis jedis = jedisPool.getResource();
        Object eval = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(lockValue));
        jedis.close();
        return eval.equals(1);
    }
}

```

RedisDistributeLockController.java

```java
package com.wanyu.springboot.learn.redis.demo.controller;

import com.wanyu.springboot.learn.redis.demo.util.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/test")
public class RedisDistributeLockController {

    @Autowired
    private RedisLock jedisDistributeLock;

    @RequestMapping(value = "/jedis-lock")
    public String jedisLock(){
        String lockKey = "lockKey";
        String lockValue = "lockValue";
        boolean lockResult = jedisDistributeLock.tryGetDistributeLock(lockKey, lockValue,
                2, TimeUnit.MINUTES);
        if(lockResult){
            System.out.println("开始执行实际业务");
            if(jedisDistributeLock.releaseDistributeLock(lockKey, lockValue)){
                System.out.println("执行完业务，释放锁");
            }
            return "success";
        }
        return "over";
    }

}

```





### 3.Springboot整合lettuce

#### 3.1 先引入依赖

```xml
        <!-- springboot 2.x 以后默认的redis操作工具 所以只需要引入这个-->
	    <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
```

#### 3.2 引入配置文件

```yml
spring:
  redis:
    host: 192.168.1.4
    port: 6379
    password: myroot
    database: 0
    lettuce:
      pool:
        max-active: 8
        min-idle: 0
        max-idle: 8
        max-wait: 1000 # 连接池最大阻塞等待时间
      shutdown-timeout: 100 # 关闭超时
```

#### 3.3 开始上java代码

RedisConfig.java

```java
package com.wanyu.springboot.learn.redis.demo.config;

import com.wanyu.springboot.learn.redis.demo.util.FastJson2JsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
public class RedisConfig {


    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory connectionFactory){

        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(connectionFactory);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
	    // 这里我使用的是fastjson来做序列化与反序列化，没有使用默认的 jackson2
        FastJson2JsonRedisSerializer<Object> jsonRedisSerializer = new FastJson2JsonRedisSerializer<>(Object.class);

        redisTemplate.setKeySerializer(stringRedisSerializer);

        redisTemplate.setValueSerializer(jsonRedisSerializer);

        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
```

```java
package com.wanyu.springboot.learn.redis.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


@Slf4j
public class FastJson2JsonRedisSerializer<T> implements RedisSerializer<T> {


    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private Class<T> clazz;

    static {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }
    public FastJson2JsonRedisSerializer(Class<T> clazz){
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if(t == null){
            return new byte[0];
        }
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        return JSON.parseObject(str, clazz);
    }
}
```

UserController.java

```java
package com.wanyu.springboot.learn.redis.demo.controller;

import com.wanyu.springboot.learn.redis.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/redis")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping(value = "/lettuce/{key}")
    public String testLettuce(@PathVariable("key") String key){
        return userService.getStringByLettuce(key);
    }
}
```

IUserService.java

```java
package com.wanyu.springboot.learn.redis.demo.service;

public interface IUserService {

    String getStringByLettuce(String key);
}
```

UserServiceImpl.java

```java
package com.wanyu.springboot.learn.redis.demo.service.impl;

import com.wanyu.springboot.learn.redis.demo.entity.User;
import com.wanyu.springboot.learn.redis.demo.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Slf4j
@Service
public class UserServiceImpl implements IUserService {
	
    	@Autowired
   		private RedisTemplate<String,Object> redisTemplate;
    	
    	@Override
	    public String getStringByLettuce(String key){
        if(Optional.ofNullable(redisTemplate.hasKey(key)).orElse( false)){
            log.info("从redis中查询出来的数据");
            return (String) redisTemplate.opsForValue().get(key);
        }else{
            log.info("从数据库中查出来的数据");
            String value = "学习Lettuce";
            redisTemplate.opsForValue().set(key,value);
            return value;
        }
    }
}
```

#### 3.4 基于lettuce的分布式锁

RedisLock.java

```java
package com.wanyu.springboot.learn.redis.demo.util;

import java.util.concurrent.TimeUnit;

public interface RedisLock {


    boolean tryGetDistributeLock(String lockKey,String lockValue,
                                 Integer expireTime, TimeUnit timeUnit);

    boolean releaseDistributeLock(String lockKey,String lockValue);
}
```

LettuceDistributeLock.java

```java
package com.wanyu.springboot.learn.redis.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class LettuceDistributeLock implements  RedisLock {


    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 加锁 set key value nx ex
     * @param lockKey 锁键
     * @param lockValue 锁值
     * @param expireTime 过期时间
     * @param timeUnit 实践单位
     * @return boolean 设置成功为true,设置失败为false
     */
    @Override
    public  boolean tryGetDistributeLock(String lockKey,
                                        String lockValue, Integer expireTime, TimeUnit timeUnit){
        return Optional.ofNullable(
                            redisTemplate.opsForValue().setIfAbsent(lockKey,lockValue,expireTime,timeUnit))
                       .orElse(false);
    }

    /**
     *  释放锁
     * @param lockKey 锁键
     * @return boolean true 释放锁成功，false 释放锁失败
     */
    @Override
    public  boolean releaseDistributeLock(String lockKey,String lockValue){

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

        RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);

        return Optional.ofNullable(redisTemplate.execute(redisScript,
                Collections.singletonList(lockKey), lockValue)).orElse(0L) > 0;
    }
}

```

RedisDistributeLockController.java

```java
package com.wanyu.springboot.learn.redis.demo.controller;

import com.wanyu.springboot.learn.redis.demo.util.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/test")
public class RedisDistributeLockController {


    @Autowired
    private RedisLock lettuceDistributeLock;


    @RequestMapping(value = "/lettuce-lock")
    public String lettuceLock(){
        String lockKey = "lockKey";
        String lockValue = "lockValue";
        boolean lockResult = lettuceDistributeLock.tryGetDistributeLock(lockKey, lockValue,
                2, TimeUnit.MINUTES);
        if(lockResult){
            System.out.println("开始执行实际业务");
            if(lettuceDistributeLock.releaseDistributeLock(lockKey, lockValue)){
                System.out.println("执行完业务以后，释放锁");
            }
            return "success";
        }
        return "over";
    }
}
```

#### 3.5 整合redis cluster

只需要修改yml,其余无需修改

```yml
server:
  port: 18080
spring:
  redis:
    password: myroot
    database: 0
    cluster:
      nodes:
        - 192.168.1.4:7000
        - 192.168.1.4:7001
        - 192.168.1.4:7002
        - 192.168.1.4:7003
        - 192.168.1.4:7004
        - 192.168.1.4:7005
      max-redirects: 3
    lettuce:
      pool:
        max-active: 8
        min-idle: 0
        max-idle: 8
        max-wait: 1000 # 连接池最大阻塞等待时间
      shutdown-timeout: 100 # 关闭超时
    timeout: 6000ms
```



### 4.Springboot整合redisson

#### 4.1 引入依赖

```xml
        <!--引入 redisson jar包-->
        <dependency>
            <groupId>org.redisson</groupId>
            <artifactId>redisson</artifactId>
            <version>3.12.3</version>
        </dependency>
        
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-pool2</artifactId>
        </dependency>
```

#### 4.2 引入配置文件

这个可以和lettuce的共用这部分，就不写个application-redisson.yml了

```yml
spring:
  redis:
    host: 192.168.1.4
    port: 6379
    password: myroot
    database: 0
```

#### 4.3 java代码部分

RedissonConfig.java

```java
package com.wanyu.springboot.learn.redis.demo.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.database}")
    private int database;

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        String redisUrl  = String.format("redis://%s:%s",host,port);
        // 演示单机模式
        config.useSingleServer().setAddress(redisUrl).setPassword(password);
        config.useSingleServer().setDatabase(database);
        return Redisson.create(config);
    }
}
```

UserController.java

```java
package com.wanyu.springboot.learn.redis.demo.controller;

import com.wanyu.springboot.learn.redis.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/redis")
public class UserController {

    @Autowired
    private IUserService userService;
    @GetMapping(value = "/redisson/{key}")
    public String testRedisson(@PathVariable("key") String key){
        return userService.getStringByRedisson(key);
    }

}
```

IUserService.java

```java
package com.wanyu.springboot.learn.redis.demo.service;

public interface IUserService {

    String getStringByRedisson(String key);

}
```

UserServiceImpl.java

```java
package com.wanyu.springboot.learn.redis.demo.service.impl;

import com.wanyu.springboot.learn.redis.demo.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private RedissonClient redissonClient;
    
    @Override
    public String getStringByRedisson(String key) {
        RBucket<Object> bucket = redissonClient.getBucket(key);
        if(bucket.isExists()){
            log.info("从redis中查询出来的数据");
            return bucket.get().toString();
        }else{
            log.info("从数据库中查出来的数据");
            String value = "学习Redisson";
            bucket.set(value);
            return value;
        }
    }
}
```

#### 4.4  基于redisson的分布式锁

```java
package com.wanyu.springboot.learn.redis.demo.controller;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/test")
public class RedisDistributeLockController {

    @Autowired
    private RedissonClient redissonClient;

    @RequestMapping(value = "/redisson-lock")
    public String redissonLock(){
        String lockKey = "lockKey";
        RLock lock = redissonClient.getLock(lockKey);
        try{
            lock.lock();
            System.out.println("开始执行实际业务");
        }finally {
            lock.unlock();
        }
        return "over";
    }

}
```

### 5.关于三种客户端的使用分析

jedis: jedis线程不安全，其实不推荐使用这个，只因为很多项目使用的是老版本springboot,所以无奈的使用这个。

lettuce:线程安全，推荐使用这个

redisson：自带很多优秀的功能，对于分支锁，支持redlock

关于分布式锁,单机版的时候，上面实现的三种锁，使用起来，基本上没什么大事，一旦是主从架构，就会导致一个问题出现,如果刚抢到锁 ，Master突然挂掉，还没来得及同步到slave,然后slave上面没有这个锁，就会被其他的线程抢到，这就会导致锁失效，高并发下，会出现这种情况，造成大量的问题，所以关于这个问题，redis作者在在redis官网推荐了redlock来实现，但是redlock并不是完美的。

在使用redlock的之前推荐看这篇文章：

[怎样做可靠的分布式锁，Redlock 真的可行么？](https://blog.csdn.net/chen_kkw/article/details/81433470)





