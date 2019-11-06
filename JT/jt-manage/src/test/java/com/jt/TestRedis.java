package com.jt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.Transaction;

public class TestRedis {
	
	private Jedis jedis;
	@Before
	public void init() {
		jedis = new Jedis("192.168.120.128",6379);	// linux服务器ip和redis端口号
	}
	/**
	 * 如果出错:
	 * 		1. ip端口检查
	 * 		2. Linux防火墙是否打开
	 * 		3. redis配置文件是否正确(3处)
	 * 		4. 检查redis的启动方式,是否按照.conf
	 * @throws InterruptedException 
	 */
	@Test
	public void testString() throws InterruptedException {
		/**
		 * 入门小测试
		 */
		
		jedis.set("1906","redis入门案例!");
		String value = jedis.get("1906");
		System.out.println(value);
	
		/**
		 * 测试:key相同时value值是否覆盖?
		 */
		jedis.set("1906","redis测试key重复");
		System.out.println(jedis.get("1906"));
	
		/**
		 * 新需求:如果值已经存在则不允许覆盖
		 * 新api setnx
		 */
		jedis.setnx("1906","NBA不看了!");
		jedis.setnx("19062","NBA不看了2!");
		System.out.println(jedis.get("1906"));
		System.out.println(jedis.get("19062"));
		
		/**
		 * 为数据设置超时时间
		 * 需要保证"原子性"(保证操作有效性)
		 * 赋值+设置超时同时生效
		 * 可以事务控制,也可以如下
		 */
//		jedis.set("time","超时测试");
//		jedis.expire("time", 60);
		jedis.setex("time", 100, "超时测试");
		Thread.sleep(3000);
		Long t = jedis.ttl("time");
		System.out.println("当前数据还能存活: " + t);
		
		/**
		 * 要求key存在时不允许操作+设置超时时间
		 * nx:不允许覆盖标识
		 * xx:可以覆盖标识
		 * ex单位秒
		 * px单位毫秒
		 * 
		 * 分布式锁:
		 * 	redis创建一个锁
		 * jedis.setnx()
		 * redis.setex()
		 */
		jedis.set("时间", "测试是否有效", "NX", "EX", 100);
		System.out.println(jedis.get("时间"));
		
		
	}
	
	/**
	 * 测试Hash
	 */
	@Test
	public void testHash() {
		jedis.hset("person2", "id","100");
		jedis.hset("person2", "name", "超人");
		System.out.println(jedis.hgetAll("person2"));
	}
	
	/**
	 * 测试List
	 */
	@Test
	public void testList() {
//		jedis.rpush("list", "1, 2, 3, 4");
//		System.out.println(jedis.lpop("list"));
		jedis.rpush("list3", "1","2","3","4");
		System.out.println(jedis.lpop("list3"));
//		System.out.println(jedis.lpop("list3"));
//		System.out.println(jedis.lpop("list3"));
//		System.out.println(jedis.lpop("list3"));
	}
	
	/**
	 * 测试事务
	 */
	@Test
	public void testTx() {
		//开启事务
		Transaction transaction = jedis.multi();
		try {
			transaction.set("a", "aaa");
			transaction.set("b", "bbb");
			transaction.set("c", "ccc");
			int a = 1/0;
		}catch (Exception e) {
			e.printStackTrace();
			transaction.discard();
		}
		
	}
	
	/**
	 * redis分片操作
	 */
	@Test
	public void testShards() {
		List<JedisShardInfo> list = new ArrayList<JedisShardInfo>();
		list.add(new JedisShardInfo("192.168.120.128",6379));
		list.add(new JedisShardInfo("192.168.120.128",6380));
		list.add(new JedisShardInfo("192.168.120.128",6381));
		ShardedJedis jedis = new ShardedJedis(list);	// 要求存入集合信息
		jedis.set("1906", "redis分片测试");
		System.out.println(jedis.get("1906"));
	}
	
	/**
	 * 测试哨兵
	 * 	调用原理:
	 * 		用户通过哨兵,连接redis的主机进行操作
	 * 		masterName:主机的变量名称!
	 * 		sentinels:redis节点信息
	 */
	@Test
	public void testSentinel() {
		Set<String> sentinels = new HashSet<String>();
		sentinels.add("192.168.120.128:26379");
		JedisSentinelPool sentinelPool = new JedisSentinelPool("mymaster", sentinels);
		Jedis jedis = sentinelPool.getResource();
		jedis.set("1906", "哨兵demo测试成功!?");
		System.out.println(jedis.get("1906"));
	}
	
	
	
	
	/**
	 * 利用spring整合redis集群
	 * 和分片几乎相同!!!
	 * 
	 * (这一段是直接复制课件的 以后要重写一下练习)
	 */
	@Test
	public void testCluster() {
		Set<HostAndPort> node = new HashSet<HostAndPort>();
		node.add(new HostAndPort("172.88.6.218",7000));
		node.add(new HostAndPort("172.88.6.218",7001));
		node.add(new HostAndPort("172.88.6.218",7002));
		node.add(new HostAndPort("172.88.6.217",7003));
		node.add(new HostAndPort("172.88.6.217",7004));
		node.add(new HostAndPort("172.88.6.217",7005));
		JedisCluster cluster = new JedisCluster(node);
		cluster.set("1906", "redis集群测试成功!!!!");
		System.out.println(cluster.get("1906"));
	}
	
}
