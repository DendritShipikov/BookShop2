package com.dendrit.bookshop.common.audit;

import com.dendrit.bookshop.common.audit.aspects.AuditAspect;
import com.dendrit.bookshop.common.audit.data.ExecutionTime;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Import(AuditAspect.class)
public class AuditConfiguration {

    @Bean
    public ProducerFactory<String, ExecutionTime> producerFactory() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return new DefaultKafkaProducerFactory<>(properties);
    }

    @Bean
    public KafkaTemplate<String, ExecutionTime> kafkaTemplate(ProducerFactory<String, ExecutionTime> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

}
