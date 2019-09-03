package com.surpass.vision.service;

import java.time.Duration;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	Duration d;

	public void set(String key, Object value) {
		// 更改在redis里面查看key编码问题
		RedisSerializer redisSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(redisSerializer);
		ValueOperations<String, Object> vo = redisTemplate.opsForValue();
		// System.out.println(value);
		vo.set(key, value);
		//Object o = get(key);
		//System.out.println(o);
		
	}
//    public void set(String key, Object value,Duration timeout) {
//        //更改在redis里面查看key编码问题
//        RedisSerializer redisSerializer =new StringRedisSerializer();
//        redisTemplate.setKeySerializer(redisSerializer);
//        ValueOperations<String,Object> vo = redisTemplate.opsForValue();
//        vo.set(key, value, timeout);
//    }

	public Object get(String key) {
		try {
			ValueOperations<String, Object> vo = redisTemplate.opsForValue();

			return vo.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public void delete(String key) {
		// 更改在redis里面查看key编码问题
		RedisSerializer redisSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(redisSerializer);
		redisTemplate.delete(key);
		ValueOperations<String, Object> vo = redisTemplate.opsForValue();
	}

}
