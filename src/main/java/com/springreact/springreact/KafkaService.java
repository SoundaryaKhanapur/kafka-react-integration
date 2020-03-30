package com.springreact.springreact;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {
	static Properties kafkaProps;
	static String bootstrapserver="127.0.0.1:9092";
	static String topic = "second-topic";
	
	static {
		
		
		
		 kafkaProps = new Properties();
		kafkaProps.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapserver);
		kafkaProps.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UserDeserializer.class.getName());
		kafkaProps.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, UserDeserializer.class.getName());
		kafkaProps.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "my-group2");
		kafkaProps.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		kafkaProps.setProperty(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "10000");
		kafkaProps.setProperty(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "60000");
		
		
		kafkaProps.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapserver);
		kafkaProps.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, UserSerializer.class.getName());
		kafkaProps.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, UserSerializer.class.getName());
		
	}

	
		public List<User> consumeMessage() {
			List<User> messageLst = new ArrayList<>();
			
		KafkaConsumer<String,User> consumer = new KafkaConsumer<String, User>(kafkaProps);
		
		consumer.subscribe(Arrays.asList(topic));
		

		ConsumerRecords<String,User> records =  consumer.poll(Duration.ofSeconds(2));
		while(records.isEmpty()) {
			System.out.println("inside while");
			records =  consumer.poll(Duration.ofSeconds(5));
     }
		
		System.out.println("-----------------------------------------------------------------------------------");
		
		for(ConsumerRecord<String,User> rec : records) {
			System.out.println("record value: "+rec.value());
			messageLst.add(rec.value());
		}
		
		
		System.out.println("Done Consuming");
				return messageLst;
		

	}
		public User producedMessage(User user) {
			KafkaProducer<String,User> producer = new KafkaProducer<String,User>(kafkaProps);
			
			ProducerRecord<String, User> record = new ProducerRecord<String, User> (topic, user);
			
			producer.send(record);
			producer.flush();
			producer.close();
			return user;
		}

}
