### SpringBoot 整合 Redis
#### 1. Redis原始命令学习
#####  1.1. Redis数据类型介绍

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

