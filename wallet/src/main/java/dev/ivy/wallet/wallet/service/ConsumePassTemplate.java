package dev.ivy.wallet.wallet.service;

import com.alibaba.fastjson.JSON;
import dev.ivy.wallet.wallet.constant.Constants;
import dev.ivy.wallet.wallet.vo.PassTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * <h1>Consume PassTemplate from Kafka</h1>
 */
@Slf4j
@Component
public class ConsumePassTemplate{

    private final IHBasePassService passService;

    public ConsumePassTemplate(IHBasePassService passService) {
        this.passService = passService;
    }

    @KafkaListener(topics = {Constants.TEMPLATE_TOPIC})
    public void receive(@Payload String passTemplate,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

        log.info("Consume Received PassTemplate : {}", passTemplate);

        PassTemplate pt;

        try {
            pt = JSON.parseObject(passTemplate, PassTemplate.class);
        } catch (Exception ex) {
            log.error("Parse PassTemplate Error: {}", ex.getMessage());
            return;
        }
        log.info("DropPassTemplateToHBase: {}", passService.dropPassTemplateToHBase(pt));
    }
}
