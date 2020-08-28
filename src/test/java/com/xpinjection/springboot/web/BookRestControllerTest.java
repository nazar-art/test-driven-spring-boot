package com.xpinjection.springboot.web;

import com.xpinjection.springboot.domain.Book;
import com.xpinjection.springboot.service.BookService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Alimenkou Mikalai
 */
@RunWith(MockitoJUnitRunner.class)
public class BookRestControllerTest {

    @Mock
    private BookService bookService;

    private MockMvc mockMvc;

    private final List<Book> books = asList(
            new Book("First", "A"),
            new Book("Second", "A")
    );

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @Before
    public void init() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new BookRestController(bookService))
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

    @Test
    public void booksAreReturnedForAuthor() throws Exception {
        when(bookService.findBooksByAuthor("A")).thenReturn(books);

        mockMvc.perform(get("/books?author=A")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].name").value("First"))
                .andExpect(jsonPath("$[0].author").value("A"))
                .andExpect(jsonPath("$[1].name").value("Second"))
                .andExpect(jsonPath("$[1].author").value("A"))
                .andDo(document("books search by author",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Accept").description("Content-Type")
                        )
                ));
    }

    @Test
    public void ifAuthorParamIsMissedThrowException() throws Exception {
        mockMvc.perform(get("/books").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(document("books without author param",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Accept").description("Content-Type")))
                );
    }
}