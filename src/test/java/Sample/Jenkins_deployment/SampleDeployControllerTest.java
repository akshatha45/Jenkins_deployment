package Sample.Jenkins_deployment;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(controllers = {SampleDeployController.class})
public class SampleDeployControllerTest {
	
    @Autowired
    private WebApplicationContext context;
    
    private MockMvc mvc;

	  @BeforeEach
	    void setup() {
	        this.mvc = MockMvcBuilders.webAppContextSetup(context)
	                .build();
	    }
	
    @Test
    void serviceIsAvailable() throws Exception {
        mvc.perform(get("/test"))
                .andExpect(status().isOk())
                .andExpect(content().string("Testing Jenkins Deployment REST api."));
    }
}
