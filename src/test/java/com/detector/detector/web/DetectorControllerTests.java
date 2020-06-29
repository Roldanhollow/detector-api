package com.detector.detector.web;

import com.detector.detector.common.Util;
import com.detector.detector.module.DNAdto;
import com.detector.detector.module.Stat;
import com.detector.detector.service.DetectorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = DetectorController.class)
public class DetectorControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DetectorService detectorServiceMock;

    private final ResponseEntity RESPONSE_OK = new ResponseEntity(HttpStatus.OK);
    private final ResponseEntity RESPONSE_FORBIDDEN = new ResponseEntity(HttpStatus.FORBIDDEN);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getStats_OK() throws Exception {
        when(detectorServiceMock.getStats()).thenReturn(Util.statBuilder());
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.get("/stats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Util.dnaDTOBuilder())));
        result.andExpect(status().isOk());
    }

    @Test
    public void callCheckMutant_OK() throws Exception {
        when(detectorServiceMock.isMutant(Util.dnaDTOBuilder().getDna())).thenReturn(Boolean.TRUE);
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(Util.dnaDTOBuilder())));
        result.andExpect(status().isOk());
    }

    @Test
    public void callCheckMutant_FAIL() throws Exception {
        when(detectorServiceMock.isMutant(Util.dnaDTOBuilder().getDna())).thenReturn(Boolean.FALSE);
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(Util.dnaDTOBuilder())));
        result.andExpect(status().isForbidden());
    }

    @Test
    public void checkMutant_OK() {
        ResponseEntity expectedResponse = commonDeclarationForCheckMutant(Boolean.TRUE).checkMutant(Util.dnaDTOBuilder());
        Assertions.assertThat(expectedResponse).isEqualTo(RESPONSE_OK);
    }

    @Test
    public void checkMutant_Fail() {
        ResponseEntity expectedResponse = commonDeclarationForCheckMutant(Boolean.FALSE).checkMutant(Util.dnaDTOBuilder());
        Assertions.assertThat(expectedResponse).isEqualTo(RESPONSE_FORBIDDEN);
    }

    @Test
    public void getStats() {
        Stat stat = Util.statBuilder();
        when(detectorServiceMock.getStats()).thenReturn(stat);
        DetectorController detectorController = new DetectorController(detectorServiceMock);
        Assertions.assertThat(detectorController.getStats()).isEqualTo(new ResponseEntity<>(stat, HttpStatus.OK));
    }

    private DetectorController commonDeclarationForCheckMutant(boolean isMutantMock) {
        when(detectorServiceMock.isMutant(Util.dnaDTOBuilder().getDna())).thenReturn(isMutantMock);
        return new DetectorController(detectorServiceMock);
    }
}
