package pl.rafalab.spotdisplayer.Controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UploadControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private UploadController uploadController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(uploadController)
                .build();
        ReflectionTestUtils.setField(uploadController, "uploadFolder","someFolder");

    }

    @Test
    void correct_list_of_files_should_return_httpOk_status_with_zipFile() throws Exception {
        MockMultipartFile mockMultipartFile = checkIfFilenameIsCorrect("files","content.zip");

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/upload")
                        .file(mockMultipartFile);
        this.mockMvc.perform(builder).andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }


    @Test
    void correct_list_of_files_should_return_httpOk_status_with_rarFile() throws Exception {
        MockMultipartFile mockMultipartFile = checkIfFilenameIsCorrect("files","content.zip");


        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/upload")
                        .file(mockMultipartFile);
        this.mockMvc.perform(builder).andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void correct_list_of_files_should_return_httpOk_status_with_incorrect_format() throws Exception {
        MockMultipartFile mockMultipartFile = checkIfFilenameIsCorrect("files","content.txt");


        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/upload")
                        .file(mockMultipartFile);
        this.mockMvc.perform(builder).andExpect(status().isUnprocessableEntity())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    void correct_list_of_files_should_return_httpOk_status_with_null() throws Exception {
        MockMultipartFile mockMultipartFile = checkIfFilenameIsCorrect("files",null);

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/upload")
                        .file(mockMultipartFile);
        this.mockMvc.perform(builder).andExpect(status().isUnprocessableEntity())
                .andDo(MockMvcResultHandlers.print());
    }



    @Test
    void correct_list_of_files_should_return_httpOk_status_with_incorrect_files_key_value() throws Exception {
        MockMultipartFile mockMultipartFile = checkIfFilenameIsCorrect("incorreect","somename");

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/upload")
                        .file(mockMultipartFile);
        this.mockMvc.perform(builder).andExpect(status().isUnprocessableEntity())
                .andDo(MockMvcResultHandlers.print());
    }

    private MockMultipartFile checkIfFilenameIsCorrect(String keyVlues,String fileName) throws Exception {
        return new MockMultipartFile(
                keyVlues, fileName, MediaType.APPLICATION_OCTET_STREAM_VALUE, "test data".getBytes());

    }


}
