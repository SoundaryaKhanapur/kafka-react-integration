package com.springreact.springreact;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserSerializer implements Serializer<User> {
	
	@Override
	public byte[] serialize(String topic, User data) {
		byte[] retVal = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			retVal = objectMapper.writeValueAsString(data).getBytes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}

}
