
package example1.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@FeignClient(name="Delivery", url="${api.url.delivery}") // Cloud에서는 Delivery:8080, localhost는 localhost:8082

public interface CancelationService {

    @RequestMapping(method= RequestMethod.POST, path="/cancelations")
    public void cancel(@RequestBody Cancelation cancelation);

}