package com.BusBooking.BusBookingSystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BusBookingSystemApplicationTests {
    @Autowired MockMvc mockMvc;
    @Autowired JdbcTemplate jdbcTemplate;
    @Autowired ObjectMapper objectMapper;

    @BeforeEach
    void cleanDatabase() {
        jdbcTemplate.update("DELETE FROM booking");
        jdbcTemplate.update("DELETE FROM bus");
        jdbcTemplate.update("DELETE FROM \"user\"");
    }

    @Test
    void contextLoads() { }

    @Test
    void registersListsAndRejectsInvalidUser() throws Exception {
        mockMvc.perform(post("/api/users/register").contentType(MediaType.APPLICATION_JSON)
                .content("""{"name":"Asha Rao","email":"asha@example.com","phoneNumber":"9876543210"}"""))
                .andExpect(status().isOk()).andExpect(jsonPath("$.phoneNumber").value("9876543210"));
        mockMvc.perform(get("/api/users")).andExpect(status().isOk()).andExpect(jsonPath("$[0].email").value("asha@example.com"));
        mockMvc.perform(post("/api/users/register").contentType(MediaType.APPLICATION_JSON)
                .content("""{"name":"","email":"bad","phoneNumber":"12"}"""))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.errors.email").exists());
    }

    @Test
    void bookingConsumesSeatsAndCancellationRestoresThem() throws Exception {
        String userJson = mockMvc.perform(post("/api/users/register").contentType(MediaType.APPLICATION_JSON)
                .content("""{"name":"Asha Rao","email":"asha@example.com","phoneNumber":"9876543210"}"""))
                .andReturn().getResponse().getContentAsString();
        long userId = objectMapper.readTree(userJson).get("id").asLong();
        String busJson = mockMvc.perform(post("/api/buses").contentType(MediaType.APPLICATION_JSON)
                .content("""{"busName":"City Bus","source":"A","destination":"B","totalSeats":3,"availableSeats":3,"fare":20}"""))
                .andReturn().getResponse().getContentAsString();
        long busId = objectMapper.readTree(busJson).get("id").asLong();
        String bookingJson = mockMvc.perform(post("/api/bookings").contentType(MediaType.APPLICATION_JSON)
                .content("""{"userId":%d,"busId":%d,"seats":2}""".formatted(userId, busId)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        long bookingId = objectMapper.readTree(bookingJson).get("id").asLong();
        mockMvc.perform(get("/api/buses/" + busId)).andExpect(jsonPath("$.availableSeats").value(1));
        mockMvc.perform(post("/api/bookings").contentType(MediaType.APPLICATION_JSON)
                .content("""{"userId":%d,"busId":%d,"seats":2}""".formatted(userId, busId)))
                .andExpect(status().isBadRequest());
        mockMvc.perform(get("/api/bookings/user/" + userId)).andExpect(status().isOk()).andExpect(jsonPath("$[0].status").value("CONFIRMED"));
        mockMvc.perform(put("/api/bookings/" + bookingId + "/cancel")).andExpect(status().isOk()).andExpect(jsonPath("$.status").value("CANCELLED"));
        mockMvc.perform(get("/api/buses/" + busId)).andExpect(jsonPath("$.availableSeats").value(3));
    }
}
