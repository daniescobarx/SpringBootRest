package com.application.rest.controllers;


import com.application.rest.controllers.DTO.MakerDTO;
import com.application.rest.entities.Maker;
import com.application.rest.service.IMakerService;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.hibernate.mapping.Any;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class MakerControllerTest {

    @InjectMocks
    private MakerController makerController;

    @Mock
    private IMakerService makerService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    public void testFindAll(){
//        List<Maker> makers = new ArrayList<>();
//        when(makerService.findAll()).thenReturn(makers);
//    }

    @Test
    public void testfindById(){
        Maker maker = new Maker();
        maker.setId(3L);
        maker.setName("dani");
        when(makerService.findById(any())).thenReturn(Optional.of(maker));

        ResponseEntity<?> result = makerController.findById(2L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(200, result.getStatusCode().value());
    }

    @Test
    public void testfindByIdMakerisnotPresent(){
        when(makerService.findById(any())).thenReturn(Optional.empty());

        ResponseEntity<?> result = makerController.findById(2L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(404, result.getStatusCode().value());
    }

    @Test
    public void testFindAll(){
        Maker maker1 = new Maker();
        maker1.setId(3L);
        maker1.setName("dani");

        Maker maker2 = new Maker();
        maker2.setId(1L);
        maker2.setName("paula");

        Maker maker3 = new Maker();
        maker3.setId(4L);
        maker3.setName("maria");

        when(makerService.findAll()).thenReturn(List.of(maker1, maker2, maker3));
        ResponseEntity<?> result = makerController.findAll();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(200, result.getStatusCode().value());
    }

    @Test
    public void testSave() throws URISyntaxException {
        MakerDTO makerDTO = MakerDTO.builder().id(3L)
                .name("dani").build();

        doNothing().when(makerService).save(any());
        ResponseEntity<?> result = makerController.save(makerDTO);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(201, result.getStatusCode().value());
    }

    @Test
    public void testSaveNotsave() throws URISyntaxException {

        doNothing().when(makerService).save(any());

        ResponseEntity<?> result = makerController.save(MakerDTO.builder().name("").build());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(400, result.getStatusCode().value());
    }

    @Test
    public void testUpdateMaker(){
        Maker maker = Maker.builder().id(3L)
                .name("maria").build();

        MakerDTO makerDTO = MakerDTO.builder().name("dani").build();
        Long expectedId = 2L;

        when(makerService.findById(any())).thenReturn(Optional.of(maker));
        doNothing().when(makerService).save(any());

        ResponseEntity<?> result = makerController.updateMaker(expectedId, makerDTO);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(200, result.getStatusCode().value());
        Assertions.assertEquals("Registro atualizado", result.getBody().toString());
    }

    @Test
    public void testUpdateMakernot(){
        MakerDTO makerDTO = MakerDTO.builder().name("dani").build();
        Long expectedId = 2L;

        when(makerService.findById(any())).thenReturn(Optional.empty());

        ResponseEntity<?> result = makerController.updateMaker(expectedId, makerDTO);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(404, result.getStatusCode().value());
    }

    @Test
    public void testDeleteByid(){
        Long expectedId = 3L;

        doNothing().when(makerService).deleteById(any());
        ResponseEntity<?> result = makerController.deleteById(expectedId);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(200, result.getStatusCode().value());
        Assertions.assertEquals("Registro deletado", result.getBody().toString());
    }

    @Test
    public void testNotDeleteByid(){
        Long expectedId = null;

        ResponseEntity<?> result = makerController.deleteById(expectedId);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(400, result.getStatusCode().value());

    }

}
