package com.n26

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

import javax.annotation.PostConstruct

@RunWith(SpringRunner.class)
@SpringBootTest
class BaseTest {
    protected MockMvc mockMvc

    @Autowired
    WebApplicationContext context

    @PostConstruct
    def setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .build()
    }

}
