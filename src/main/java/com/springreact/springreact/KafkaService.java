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
	static String topic = "first-topic";
	
	static {
		
		
		
		 kafkaProps = new Properties();
		kafkaProps.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapserver);
		kafkaProps.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		kafkaProps.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		kafkaProps.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "my-group2");
		kafkaProps.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		kafkaProps.setProperty(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "10000");
		kafkaProps.setProperty(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "60000");
		
		
		kafkaProps.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapserver);
		kafkaProps.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		kafkaProps.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		
	}

	
		public List<String> consumeMessage() {
			List<String> messageLst = new ArrayList<>();
			
		KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(kafkaProps);
		
		consumer.subscribe(Arrays.asList(topic));
		

		ConsumerRecords<String,String> records =  consumer.poll(Duration.ofSeconds(2));
		while(records.isEmpty()) {
			System.out.println("inside while");
			records =  consumer.poll(Duration.ofSeconds(5));
     }
		
		System.out.println("-----------------------------------------------------------------------------------");
		
		for(ConsumerRecord<String,String> rec : records) {
			System.out.println("record value: "+rec.value());
			messageLst.add(rec.value());
		}
		
		
		System.out.println("Done Consuming");
				return messageLst;
		

	}
		public String producedMessage(String message) {
			KafkaProducer<String,String> producer = new KafkaProducer<String,String>(kafkaProps);
			
			ProducerRecord<String, String> record = new ProducerRecord<String, String> (topic, message);
			
			producer.send(record);
			producer.flush();
			producer.close();
			return message;
		}

}
