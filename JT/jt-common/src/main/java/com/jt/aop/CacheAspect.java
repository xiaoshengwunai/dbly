package com.jt.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.jt.anno.Cache_Find;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;

/**
 * 缓存切面
 * 	写到common 大家都能用
 *
 */
@Aspect	//标识这个类是个切面
@Component	// 将该类交给Spring容器管理
public class CacheAspect {
	
	/**
	 * 由于当前切面位于common中,有个特点,以后但凡引入common程序中的,必须依赖一个jedis
	 * 所以加一个属性 required = false  说明这个类不是必须的,只有调用时候才添加 用
	 */
	@Autowired(required = false)
//	private ShardedJedis jedis;
	private JedisCluster jedis;
	
	/**
	 * 方法名无所谓,其他的写法比较固定.
	 * 通知的选择
	 * 	根据是否需要控制目标方法的执行
	 * 	所用用环绕通知
	 * 
	 * 用环绕通知就必须得和proceedingjoinpoint 而不是joinpoint
	 * 
	 * 步骤:
	 * 	1. 动态生成key 
	 * 		(通过key查缓存,key从注解来)
	 * 		为了更唯一,我们包名.类名.方法名::parentId
	 * 		:: 是行业内习惯!
	 * 
	 * 注解属性: 老方法是写,又介绍了一个新方法!等效的
	 * 
	 */
//	@Around("@annotation(com.jt.anno.Canche_Find)")	// 不用这个写法
	@Around("@annotation(cacheFind)")	//这里方法名和参数列表的一致
	public Object around(ProceedingJoinPoint joinPoint, Cache_Find cacheFind) {	// proceed() 调用 jp参数要放首位!
		String key = getKey(joinPoint, cacheFind);
		String result = jedis.get(key);
		Object data = null;
			try {
				if (StringUtils.isEmpty(result)) {
					// 缓存没数据,目标方法执行查询数据
					data = joinPoint.proceed();
					String value = ObjectMapperUtil.toJSON(data);
					
					if (cacheFind.seconds() == 0) {	//为0 表示用户没打算设置超时时间
						jedis.set(key, value);
					}else {
						jedis.setex(key, cacheFind.seconds(), value);
					}
					System.out.println("AOP数据库查询");
					
				}else { //表示缓存中有数据
//					Class returnClass = getClass();
					Class returnClass = getClass(joinPoint);
					data = ObjectMapperUtil.toObject(result, returnClass);
					System.out.println("AOP查询缓存");
				}
			} catch (Throwable e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		
		return data;
		
	}
	
	private Class getClass(ProceedingJoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		return signature.getReturnType();
	}

	/**
	 * 获取key的方法
	 * 包名.类名.方法名::parentId怎么获取?
	 */
	private String getKey(ProceedingJoinPoint joinPoint, Cache_Find cacheFind) {
		// 先判断用户是否自定义key(传过来了值?)
		String key = cacheFind.key();
		if (! StringUtils.isEmpty(key)) {
			return key;	//返回用户自己定义的key
		}
		// 获取包名.类名.方法名::parentId
		String className = joinPoint.getSignature().getDeclaringTypeName();	//包名+类名都有了
		String methodName = joinPoint.getSignature().getName();
		Object[] args = joinPoint.getArgs();
		key = className + "." + methodName + "::" + args[0];
		// 表示需要自动生成key
		return key;
	}
	
//	private
	
}
