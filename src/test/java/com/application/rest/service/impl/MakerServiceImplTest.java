package com.application.rest.service.impl;

import com.application.rest.entities.Maker;
import com.application.rest.persistence.IMakerDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MakerServiceImplTest {
    @InjectMocks
    MakerServiceImpl makerService;

    @Mock
    IMakerDAO makerDAO;

    @BeforeEach
    void setUp(){
        //inicializando mocks.
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        List<Maker> makers = Arrays.asList(new Maker(), new Maker());

        // quando findAll for chamado, retornar a lista de makers
        when(makerDAO.findAll()).thenReturn(makers);
        List<Maker> result = makerService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void testFindById(){
        Maker maker = new Maker();
        makerService.save(maker);
        verify(makerDAO).save(maker);

    }


}
