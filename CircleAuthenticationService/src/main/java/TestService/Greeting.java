package TestService;


import org.springframework.stereotype.Component;

@Component("greetingTest")
public class Greeting {

    private final long id;
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public Greeting() {
    	this.id = 1;
        this.content = "initValue";
    }
    
    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}