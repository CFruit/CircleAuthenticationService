package TestService;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting")
public class greetingController {
	@Autowired
	@Qualifier("greetingTest")
    public Greeting greeting;
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/test")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
       /* return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));*/
    	return greeting;
    }
}