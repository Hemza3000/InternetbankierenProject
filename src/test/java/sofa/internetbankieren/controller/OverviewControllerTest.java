package sofa.internetbankieren.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OverviewController.class)
class OverviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //@MockBean
    // nog niet nodig


    public OverviewControllerTest(){
        super();
    }

 /*   @Test
    void getOverviewTest() {
        try {
            MockHttpServletRequestBuilder postRequest =
                    MockMvcRequestBuilders.get("/overview");
//      postRequest.param("input_1", "XX");
            ResultActions response = mockMvc.perform(postRequest);
            response.andDo(print()).andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}