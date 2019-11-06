package com.jt.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
///jt-sso/src/main/resources/properties/redis.properties
@Configuration
@PropertySource("classpath:/properties/redis.properties")	// 这里配置文件写在manage模块中,因为主启动类在namage中,从中加载
public class RedisConfig {

//	@Value("${redis.host}")
//	private String host;
//	@Value("${redis.port}")
//	private Integer port;
//	
//	@Bean
//	public Jedis getJedis() {
//		return new Jedis(host,port);
//	
//	List<JedisShardInfo> list = new ArrayList<JedisShardInfo>();
//	list.add(new JedisShardInfo("192.168.120.128",6379));
//	list.add(new JedisShardInfo("192.168.120.128",6380));
//	list.add(new JedisShardInfo("192.168.120.128",6381));
//	ShardedJedis jedis = new ShardedJedis(list);	// 要求存入集合信息
//	jedis.set("1906", "redis分片测试");
//	System.out.println(jedis.get("1906"));
	@Value("${redis.nodes}")
	private String nodes;
	
//	@Bean
//	public ShardedJedis shardedJedis() {
//		List<JedisShardInfo> shards = getList();
//		return new ShardedJedis(shards);
//	}
	
//	private List<JedisShardInfo> getList(){
//		List<JedisShardInfo> list = new ArrayList<JedisShardInfo>();
//		String[] arrayNodes = nodes.split(",");
//		// node = ip:port
//		for (String node : arrayNodes) {
//			String host = node.split(":")[0];
//			int port = Integer.parseInt(node.split(":")[1]);
//			list.add(new JedisShardInfo(host, port));
//		}
//		return list;
//	}
	
//	@Bean
//	@Scope("prototype")	//多例对象  用户使用时创建
//	public Jedis jedis(@Autowired JedisSentinelPool pool) {
//		
//		return pool.getResource();
//	}

//	/**
//	 * 配置哨兵
//	 * @Scope("prototype")设置多例!! 避免"明明池中有很多对象,但别人一次只能拿一个(单例不太合适)"
//	 * 池单独设置比较合适,池不能单独
//	 * 
//	 * 注意: 这种写法比较合理,以后要逐渐习惯这种写法!!!
//	 */
//	//定义池对象
//	@Bean
//	public JedisSentinelPool jedisSentinelPool() {
//		System.out.println(nodes);
//		Set<String> sentinels = new HashSet<>();
//		sentinels.add(nodes);
//		return new JedisSentinelPool("mymaster", sentinels);
//	}
	
	
	/**
	 * 配置集群
	 * (暂时还不能测试通过 前端展示失败 先学新课 不管了!)
	 */
	@Bean
	public JedisCluster jedisCluster() {
		Set<HostAndPort> nodeSet = new HashSet<HostAndPort>();
		String[] arrayNodes = nodes.split(","); 
		for(String node : arrayNodes) {
			String host = node.split(":")[0];		
			int port = Integer.parseInt(node.split(":")[1]);
			nodeSet.add(new HostAndPort(host, port));
			System.out.println("我是RedisCofig 我的数据: " + node);
		}
		return new JedisCluster(nodeSet);
	}
	
	
	
}
