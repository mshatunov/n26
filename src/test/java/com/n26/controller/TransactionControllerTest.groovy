package com.n26.controller

import com.n26.BaseTest
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.ResultActions

import java.time.LocalDateTime

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
                                 "timestamp": LocalDateTime.now().toString() + 'Z'] as Map)))

        int status = response.andReturn().getResponse().getStatus()
        assert status == 201
    }

    @Test
    void postTransactionInvalidJson() {
        ResultActions response = mockMvc.perform(post(TRANSACTIONS_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content("invalid json"))

        int status = response.andReturn().getResponse().getStatus()
        assert status == 400
    }

    @Test
    void postTransactionOld() {
        ResultActions response = mockMvc.perform(post(TRANSACTIONS_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(["amount"   :  "12.3343",
                                 "timestamp": LocalDateTime.now().minusYears(1).toString() + 'Z'] as Map)))

        int status = response.andReturn().getResponse().getStatus()
        assert status == 204
    }

    @Test
    void postTransactionFuture() {
        ResultActions response = mockMvc.perform(post(TRANSACTIONS_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(["amount"   :  "12.3343",
                                 "timestamp": LocalDateTime.now().plusYears(1).toString() + 'Z'] as Map)))

        int status = response.andReturn().getResponse().getStatus()
        assert status == 422
    }

}
