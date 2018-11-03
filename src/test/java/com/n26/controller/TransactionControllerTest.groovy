package com.n26.controller

import com.n26.BaseTest
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.ResultActions

import static com.n26.controller.TransactionController.TRANSACTIONS_URI
import static groovy.json.JsonOutput.toJson
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@RunWith(SpringRunner.class)
@SpringBootTest
class TransactionControllerTest extends BaseTest {

    @Test
    void postTransactionSuccess() {
        ResultActions response = mockMvc.perform(post(TRANSACTIONS_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(["amount"   : "12.3343",
                                 "timestamp": "2018-07-17T09:59:51.312Z"] as Map)))

        int status = response.andReturn().getResponse().getStatus()
        assert status == 200
    }


}
