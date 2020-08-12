package example1;

import example1.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler {

    @Autowired
    OrderRepository orderRepository;

    //Shipped복사하기
    @StreamListener(KafkaProcessor.INPUT)
    public void onShiped(@Payload Shiped shiped) {
        if (shiped.isMe()) {
            //1.orderId가져오기
            Optional<Order> orderOptional = orderRepository.findById(shiped.getOrderId());//key로 entity 가져오기
            //2.Order에 status 바꾸기
            Order order = orderOptional.get();
            order.setStatus(shiped.getStatus());
            //3. Order 배송상태 저장하기
            orderRepository.save(order);
            System.out.println("Order Status Changed : " + order.getStatus());
        }
    }
    //order에 status 추가, set/get 하기
    //order에 Delivery에 shiped를 listening해서 -> order id를 findby해서
    //order에 추가된 배송에 추가된 상태값 set
}
