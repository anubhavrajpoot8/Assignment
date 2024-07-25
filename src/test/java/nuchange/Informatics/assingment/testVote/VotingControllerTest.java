package nuchange.Informatics.assingment.testVote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VotingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testEnterCandidate() throws Exception {
        mockMvc.perform(post("/api/voting/entercandidate/amit")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Candidate amit entered successfully"))
                .andDo(print());
    }

    @Test
    public void testCastVote() throws Exception {
        mockMvc.perform(post("/api/voting/entercandidate/raju"));
        mockMvc.perform(post("/api/voting/castvote/raju")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Vote cast for raju. New count: 1"))
                .andDo(print());
    }

    @Test
    public void testCountVote() throws Exception {
        // Enter a candidate and cast a vote
        mockMvc.perform(post("/api/voting/entercandidate/mohan"));
        mockMvc.perform(post("/api/voting/castvote/mohan"));
        // Then count the vote
        mockMvc.perform(get("/api/voting/countvote/mohan")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Vote count for mohan: 1"))
                .andDo(print());
    }

    @Test
    public void testListVote() throws Exception {
        // Enter candidates and cast votes
        mockMvc.perform(post("/api/voting/entercandidate/sonu"));
        mockMvc.perform(post("/api/voting/castvote/sonu"));
        // Then list the votes
        mockMvc.perform(get("/api/voting/listvote")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sonu").value(1))
                .andDo(print());
    }

//    @Test
//    public void testGetWinner() throws Exception {
//        // Test when there are no candidates
//        mockMvc.perform(get("/api/voting/getwinner")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().string("No candidates or votes yet"))
//                .andDo(print());
//
//        // Enter candidates
//        mockMvc.perform(post("/api/voting/entercandidate/amit"));
//        mockMvc.perform(post("/api/voting/entercandidate/raju"));
//
//       
//
//        // Cast votes
//        mockMvc.perform(post("/api/voting/castvote/amit") 
//        		.accept(MediaType.APPLICATION_JSON))
//		        .andExpect(status().isOk())
//		        .andExpect(content().string("Vote cast for amit. New count: 1"))
//		        .andDo(print());
//        mockMvc.perform(post("/api/voting/castvote/amit") 
//        		.accept(MediaType.APPLICATION_JSON))
//		        .andExpect(status().isOk())
//		        .andExpect(content().string("Vote cast for amit. New count: 2"))
//		        .andDo(print());
//        mockMvc.perform(post("/api/voting/castvote/raju") 
//        		.accept(MediaType.APPLICATION_JSON))
//		        .andExpect(status().isOk())
//		        .andExpect(content().string("Vote cast for raju. New count: 1"))
//		        .andDo(print());
//
//        // Test when there's a clear winner
//        mockMvc.perform(get("/api/voting/getwinner")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().string("winner is amit"))
//                .andDo(print());
//
//        // Cast more votes to create a tie
//        mockMvc.perform(post("/api/voting/castvote/raju") 
//        		.accept(MediaType.APPLICATION_JSON))
//		        .andExpect(status().isOk())
//		        .andExpect(content().string("Vote cast for raju. New count: 2"))
//		        .andDo(print());
//
//        // Test when there's a tie
//        mockMvc.perform(get("/api/voting/getwinner")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().string("No winner - tie between amit, raju"))
//                .andDo(print());
//    }
}