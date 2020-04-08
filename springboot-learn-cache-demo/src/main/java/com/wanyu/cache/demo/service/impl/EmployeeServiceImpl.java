package com.wanyu.cache.demo.service.impl;


import com.wanyu.cache.demo.entity.Employee;
import com.wanyu.cache.demo.mapper.EmployeeMapper;
import com.wanyu.cache.demo.service.IEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
@Slf4j
//@CacheConfig(cacheNames = "emp") 这里指定的话，下面每个注解就不需要指定cacheNames了
public class EmployeeServiceImpl implements IEmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;

    /**
     * 将数据开启缓存
     * CacheManager管理多个Cache组件,对缓存的真正CURD操作在Cache组件中，每一个缓存组件有自己唯一的名字。
     * Cacheable的几个属性
     *      cacheNames/value: 指定缓存组件的名字；
     *      key:缓存数据使用的key,可以用它来指定，默认使用方法参数的值，1-方法的返回值
     *          可以编写spEL #id :参数id的值
     *      keyGenerator: key生成器，也可以指定自己的key生成器，它和key二选一
     *          自定义key生成器
     *      cacheManager: 指定缓存管理器；
     *      condition: 指定符合条件的情况才会被缓存
     *          condition = "#a0>1" 满足第一个参数大于1的数据才会被缓存
     *      unless: 否定缓存，当unless为true的时候不会被缓存，false的时候会被缓存
     *      sync: 是否开启异步
     *      target + method + args
     *原理：
     *      1.自动配置类：CacheAutoConfiguration
     *      2.缓存配置类：
     *          org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration
     *          org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration
     *          org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration
     *          org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration
     *          org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration
     *          org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration
     *          org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration
     *          org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration
     *          org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration
     *          org.springframework.boot.autoconfigure.cache.NoOpCacheConfiguration
     *      3.默认生效的缓存配置：SimpleCacheConfiguration 如何确定 打开springboot的debug模式  在yml中添加 debug: true
     *      4.SimpleCacheConfiguration会给容器中注册一个CacheManager的Bean ConcurrentMapCacheManager
     *      5.可以获取和创建一个ConcurrentMapCacheManager类型的缓存管理组件，作用是讲数据保存到ConcurrentMap中。
     *运行流程：
     *      1.方法运行之前，先去查询Cache(缓存组件)，按照cacheNames指定的名字获取；
     *          （CacheManager的getCache(String name)方法）第一次获取的时候，一般都没有创建，这时候会自动创建一个ConcurrentMapCache
     *      2.去Cache中查找缓存内容，使用一个key，默认的方法是参数；
     *        key是按照某种策略生成的；默认是使用keyGenerator生成的，默认使用SimpleKeyGenerator生成key
     *        SimpleKeyGenerator生成key的默认策略：
     *              如果没有参数，key= new SimpleKey()
     *              如果有一个参数，就用这个参数作为默认key
     *              如果有多个参数，就把多个参数作为key
     *      3.没查到缓存就会调用目标方法；
     *      4.将目标方法放进缓存中
     *Cacheable 标注的方法执行之前先检查缓存中是否有数据，按照默认的key去查询缓存，
     *      如果没有就运行方法并将结果放入到缓存中，以后再来调用就可以直接使用缓存中的数据；
     *核心：
     *      1.使用CacheManager【ConcurrentMapCacheManager】按照名字得到Cache【ConcurrentMapCache】组件
     *      2.key使用keyGenerator生成的，默认使用SimpleKeyGenerator生成的
     * @param id 员工id
     * @return Employee 员工信息
     */
    @Override
    @Cacheable(cacheNames = {"emp"}/*, keyGenerator = "myKeyGenerator",condition = "#a0>0"*/)
    public Employee getEmpById(Long id) {

        log.info("查看{}号员工",id);
        return employeeMapper.selectById(id);
    }

    /**
     * 注解@CachePut 既调用方法，有更新缓存数据。
     * 运行时机：
     *      1.先调用目标方法
     *      2.将目标方法的结果缓存起来
     * 测试：因为需要指定key,才能去更新缓存中对应的数据
     *      所以上面的Cacheable的我们自定义的key生成器，实际上是不能使用的
     *      可以分析，毕竟我们自定义的key是根据类名+方法名+[参数]生成的，这里我们并没有办法去获取和上面
     *      一样的key,所以我们必须注释掉上面的key自定义生成器
     *
     * 注解@Caching 定义多功能缓存，
     *     适用场景: 当我们更新一个Employee员工的时候，必然会导致list缓存失效
     *              这个时候，就需要清空list的缓存
     * @param employee 员工信息
     * @return Employee 更新的员工id
     */
    @Override
    @CachePut(cacheNames = {"emp"},key = "#result.id")
//    @Caching(
//            put = {
//                    @CachePut(cacheNames = "emp",key = "#result.id")
//            },
//            evict = {
//                    @CacheEvict(cacheNames = "emp",key="#root.targetClass")
//            }
//    )
    public Employee updateEmp(Employee employee) {
        log.info("更新id是{}的数据",employee.getId());
        employeeMapper.updateById(employee);
        return employee;
    }

    /**
     * 注解@CacheEvict 删除某个缓存，根据key
     *  key：指定要删除缓存的key
     *  allEntries:是否删除所有缓存，慎用
     *  beforeInvocation: 是否在方法执行之前，清除所有缓存，用在方法出错，
     * @param id 员工id
     */
    @Override
    @CacheEvict(cacheNames = {"emp"},key = "#id")
//  不仅要删除对应id缓存，还要删除list的缓存，这个时候就发现了这个优点
//    @Caching(
//            evict = {
//                    @CacheEvict(cacheNames = {"emp"},key = "#id"),
//                    @CacheEvict(cacheNames = {"emp"},key = "#root.targetClass")
//            }
//    )
    public Boolean deleteEmp(Long id) {
        log.info("删除的员工id是{}",id);
        return employeeMapper.deleteById(id) > 0;
    }

    /**
     * 这里缓存所有
     * @return List<Employee>
     */
    @Override
    @Cacheable(cacheNames = "emp",key="#root.targetClass")
    public List<Employee> list(){
        log.info("查询所有员工");
        return employeeMapper.selectList(null);
    }

    /**
     * 针对上面的指定特殊的key导致，无法直接清空缓存，这里可以指定一个特殊的key,
     * 在新增之后，
     * @param employee 新增员工
     */
    @Override
    @CacheEvict(cacheNames = {"emp"},key="#root.targetClass")
    public Employee insertEmp(Employee employee) {
         employeeMapper.insert(employee);
         return employee;
    }
}
