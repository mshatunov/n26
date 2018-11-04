package com.n26.controller

import com.n26.BaseTest
import com.n26.storage.TransactionStorage
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner

import java.time.LocalDateTime

import static com.n26.controller.StatisticsController.STATISTICS_URI
import static com.n26.controller.TransactionController.TRANSACTIONS_URI
import static groovy.json.JsonOutput.toJson
import static org.hamcrest.Matchers.equalTo
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner.class)
@SpringBootTest
class StatisticsControllerTest extends BaseTest {

    @Autowired
    TransactionStorage transactionStorage

    @Before
    void prepare() {
        transactionStorage.clearTransactions()
    }

    @Test
    void getStatisticsDifferentPeriodSuccess() {
        mockMvc.perform(post(TRANSACTIONS_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(["amount"   : "10",
                                 "timestamp": LocalDateTime.now().minusSeconds(10).toString() + 'Z'] as Map)))
        mockMvc.perform(post(TRANSACTIONS_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(["amount"   : "20",
                                 "timestamp": LocalDateTime.now().minusSeconds(20).toString() + 'Z'] as Map)))
        mockMvc.perform(post(TRANSACTIONS_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(["amount"   : "50",
                                 "timestamp": LocalDateTime.now().minusSeconds(30).toString() + 'Z'] as Map)))

        mockMvc.perform(get(STATISTICS_URI))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath('$.sum', equalTo(80)))
                .andExpect(jsonPath('$.avg', equalTo(26.67D)))
                .andExpect(jsonPath('$.max', equalTo(50)))
                .andExpect(jsonPath('$.min', equalTo(10)))
                .andExpect(jsonPath('$.count', equalTo(3)))
    }

    @Test
    void getStatisticsOnePeriodSuccess() {
        def now = LocalDateTime.now().toString() + 'Z'
        mockMvc.perform(post(TRANSACTIONS_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(["amount"   : "10",
                                 "timestamp": now] as Map)))
        mockMvc.perform(post(TRANSACTIONS_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(["amount"   : "20",
                                 "timestamp": now] as Map)))
        mockMvc.perform(post(TRANSACTIONS_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(["amount"   : "50",
                                 "timestamp": now] as Map)))

        mockMvc.perform(get(STATISTICS_URI))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath('$.sum', equalTo(80)))
                .andExpect(jsonPath('$.avg', equalTo(26.67D)))
                .andExpect(jsonPath('$.max', equalTo(50)))
                .andExpect(jsonPath('$.min', equalTo(10)))
                .andExpect(jsonPath('$.count', equalTo(3)))
    }

}