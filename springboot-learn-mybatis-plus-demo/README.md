#### Mybatis Plus Learn Demo
1.依赖添加 这里只展示添加mybatis-plus的jar包，其他默认已导入
```xml
       <dependency>
           <groupId>com.baomidou</groupId>
           <artifactId>mybatis-plus-boot-starter</artifactId>
           <version>3.1.0</version>
       </dependency> 
```
2.配置文件,这里默认使用的mysql驱动是8.0+版本，所以driver-class-name不一样，低版本去掉cj
```yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: root
    password: 123456
    url: jdbc:mysql://192.168.211.133:3306/mybatis-plus-demo?useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true
    hikari:
      idle-timeout: 60000
      maximum-pool-size: 30
      minimum-idle: 10
```
3.在Spring Boot启动文件中增加@MapperScan注解，扫描Mapper文件
```java
@SpringBootApplication
@MapperScan("com.wanyu.mybatis.plus.demo.mapper") // 这里是Mapper文件所在的包目录
public class MybatisPlusDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusDemoApplication.class, args);
    }

}
```
4.xxxMapper 继承 BaseMapper<T> T 是要操作的实体泛型
```java
public interface UserMapper extends BaseMapper<User> {

}
```
5.sql直接导出，并放在resource下面的sql里面

6.test下面会对各个函数进行测试使用，以及更多用法，

| 函数名                  |             中文    |  
|:-----------------------|:---------------
| AllEq                  |    全等于           |
7.分页插件的使用
使用分页插件，就可以配置一个MybatisPlusConfig配置类，需要对Application类做点小变动