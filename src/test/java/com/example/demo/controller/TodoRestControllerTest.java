package com.example.demo.controller;

import com.example.demo.model.Todo;
import com.example.demo.service.TodoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TodoRestController.class)
public class TodoRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TodoService todoService;

    @Test
    public void testFindAll() throws Exception {
        List<Todo> allTodos = IntStream.range(0, 10)
                .mapToObj(i -> new Todo(i, "title-" + i, "detail-" + i))
                .collect(Collectors.toList());

        given(todoService.getAll()).willReturn(allTodos);

        mvc.perform(get("/api/v1/todo").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)))
                .andExpect(jsonPath("$[0].id", is(0)))
                .andExpect(jsonPath("$[0].title", is("title-0")))
                .andExpect(jsonPath("$[0].detail", is("detail-0")));
    }

}
