package com.igo.base.test;

import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class RedisJava {
	
	Jedis jedis = null;
	Integer deadTime = 1000;//单位秒
	String password = "zhouc";
	
	public RedisJava(String host, Integer port) {
		jedis = new Jedis(host, port);
		jedis.auth(password);
		System.out.println("connect redis success.......");
		//�鿴�����Ƿ�����
	    System.out.println("Server is running: "+jedis.ping());
	}
	
	public void fetchAll() {
		Set<String> keys = jedis.keys("*");
		System.out.println(keys);
		for (String key : keys) {
			//none (key不存在) string (字符串) list (列表) set (集合) zset (有序集) hash (哈希表)
			String keyType = jedis.type(key);
			if ("string".equals(keyType)) {
				System.out.println(key + " = " + jedis.get(key));
			} else if ("list".equals(keyType)) {
				List<String> list = jedis.lrange(key, 0, -1);
				System.out.println(key + " = " + list);
			}
		}
	}

	/**
	 * 插入字符串  插入成功返回OK
	 * @param key
	 * @param value
	 */
	public void add(String key, String value) {
		System.out.println(jedis.set(key, value));
	}
	
	public void add(String key, String...values) {
		jedis.lpush(key, values);
	}
	
	public void add(String key, String value, Integer seconds) {
		jedis.setex(key, seconds, value);
	}
	
	public void fecthByKey(String key) {
		System.out.println(jedis.get(key));
	}
	
	public void deleteByKey(String... keys) {
		for (String key : keys) {
			System.out.println(jedis.del(key));
		}
	}

	public static void main(String[] args) {
		RedisJava redisJava = new RedisJava("192.168.189.128", 6379);
		//redisJava.add("zhouc", "zhouc");
		//redisJava.deleteByKey("zhouc");
		//redisJava.add("testlist", "2", "3", "1");
		redisJava.fetchAll();
	}
	
	
}
