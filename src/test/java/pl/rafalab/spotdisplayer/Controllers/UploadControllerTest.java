package pl.rafalab.spotdisplayer.Controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.rafalab.spotdisplayer.Models.MyRole;
import pl.rafalab.spotdisplayer.Models.MyUser;
import pl.rafalab.spotdisplayer.Services.WeldingSpotService;
import pl.rafalab.spotdisplayer.TesetUtils.TestUtils;
import pl.rafalab.spotdisplayer.Utils.FileCrawlerImpl;
import pl.rafalab.spotdisplayer.Utils.Interfaces.WeldingSpotWorker;
import pl.rafalab.spotdisplayer.Utils.TextWorkerImpl;
import pl.rafalab.spotdisplayer.Utils.UnzipUtil;
import pl.rafalab.spotdisplayer.Utils.UsefulUtils;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UploadControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FileCrawlerImpl fileCrawler;

    @Mock
    private UnzipUtil unzipUtil;

    @Mock
    private TextWorkerImpl textWorker;

    @Mock
    private UsefulUtils usefulUtils;

    @Mock
    private WeldingSpotWorker weldingSpotWorker;

    @Mock
    private WeldingSpotService weldingSpotService;

    private UploadController uploadController;

    private MyUser myUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        uploadController = new UploadController(unzipUtil, fileCrawler, textWorker, weldingSpotWorker, weldingSpotService, usefulUtils);
        mockMvc = MockMvcBuilders
                .standaloneSetup(uploadController)
                .build();

        ReflectionTestUtils.setField(uploadController, "uploadFolder", "someFolder");

        myUser = new MyUser();
        myUser.setId(1L);
        myUser.setPassword("password");
        myUser.setRoles(TestUtils.getUserRoles());
        myUser.setUsername("user");

    }

    @Test
    void correct_list_of_files_should_return_httpOk_status_with_zipFile() throws Exception {
        MockMultipartFile mockMultipartFile = checkIfFilenameIsCorrect("files", "content.zip");
        when(usefulUtils.getUserFromRequest(any())).thenReturn(myUser);

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/upload")
                        .file(mockMultipartFile);
        this.mockMvc.perform(builder).andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }


    @Test
    void correct_list_of_files_should_return_httpOk_status_with_rarFile() throws Exception {
        MockMultipartFile mockMultipartFile = checkIfFilenameIsCorrect("files", "content.rar");
        when(usefulUtils.getUserFromRequest(any())).thenReturn(myUser);

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/upload")
                        .file(mockMultipartFile);
        this.mockMvc.perform(builder).andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void correct_list_of_files_should_return_httpOk_status_with_incorrect_format() throws Exception {
        MockMultipartFile mockMultipartFile = checkIfFilenameIsCorrect("files", "content.txt");
        when(usefulUtils.getUserFromRequest(any())).thenReturn(myUser);

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/upload")
                        .file(mockMultipartFile);
        this.mockMvc.perform(builder).andExpect(status().isUnprocessableEntity())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    void correct_list_of_files_should_return_httpOk_status_with_null() throws Exception {
        MockMultipartFile mockMultipartFile = checkIfFilenameIsCorrect("files", null);
        when(usefulUtils.getUserFromRequest(any())).thenReturn(myUser);

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/upload")
                        .file(mockMultipartFile);
        this.mockMvc.perform(builder).andExpect(status().isUnprocessableEntity())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    void correct_list_of_files_should_return_BadRequest_status_with_incorrect_files_key_value() throws Exception {
        MockMultipartFile mockMultipartFile = checkIfFilenameIsCorrect("incorreect", "somename");

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/upload")
                        .file(mockMultipartFile);
        this.mockMvc.perform(builder).andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    private MockMultipartFile checkIfFilenameIsCorrect(String keyVlues, String fileName) throws Exception {
        return new MockMultipartFile(
                keyVlues, fileName, MediaType.APPLICATION_OCTET_STREAM_VALUE, "test data".getBytes());

    }

}
