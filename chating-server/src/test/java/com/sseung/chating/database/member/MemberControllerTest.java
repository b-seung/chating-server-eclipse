package com.sseung.chating.database.member;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = MemberController.class)
public class MemberControllerTest {
    @Autowired
    private MockMvc mvc;

    //java.lang.IllegalStateException: Failed to load ApplicationContextエラー発生
    //原因は
    //org.springframework.beans.factory.UnsatisfiedDependencyException:
    //Error creating bean with name 'memberController': Unsatisfied dependency expressed through field 'memberRepository';

    @MockBean
    private MemberRepository memberRepository;

    @Test
    public void allMember_return() throws Exception {
        String text = "allMember";

        mvc.perform(get("/member"))
                .andExpect(status().isOk())
                .andExpect(content().string(text));
    }

//    @Test
//    public void allMember_return2() throws Exception {
//
//        mvc.perform(get("/member/all"))
//                .andExpect(status().isOk());
//    }
}