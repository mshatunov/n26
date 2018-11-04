package com.n26.controller

import com.n26.BaseTest
import com.n26.configuration.ApplicationProperties
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner

import java.time.LocalDateTime
import java.time.ZoneId

import static com.n26.controller.TransactionController.TRANSACTIONS_URI
import static groovy.json.JsonOutput.toJson
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner.class)
@SpringBootTest
class TransactionControllerTest extends BaseTest {

    @Autowired
    ApplicationProperties properties

    @Test
    void postTransactionSuccess() {
        mockMvc.perform(post(TRANSACTIONS_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(["amount"   : "12.3343",
                                 "timestamp": LocalDateTime.now(ZoneId.of(properties.getTimezone())).toString() + 'Z'] as Map )))
                                 .andExpect(status().is(HttpStatus.CREATED.value()))
    }

    @Test
    void postTransactionInvalidJson() {
        mockMvc.perform(post(TRANSACTIONS_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content("invalid json"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
    }

    @Test
    void postTransactionOld() {
        mockMvc.perform(post(TRANSACTIONS_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(["amount"   : "12.3343",
                                 "timestamp": LocalDateTime.now(ZoneId.of(properties.getTimezone())).minusYears(1).toString() + 'Z'] as Map)))
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()))
    }

    @Test
    void postTransactionFuture() {
        mockMvc.perform(post(TRANSACTIONS_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(["amount"   : "12.3343",
                                 "timestamp": LocalDateTime.now(ZoneId.of(properties.getTimezone())).plusYears(1).toString() + 'Z'] as Map)))
                .andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
    }

    @Test
    void clearTransactionsSuccess() {
        mockMvc.perform(delete(TRANSACTIONS_URI))
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()))
    }

}
