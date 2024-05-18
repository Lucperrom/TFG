package org.springframework.samples.pubus.paper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;
import java.util.Optional;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.pubus.exceptions.ResourceNotFoundException;
import org.springframework.security.core.context.SecurityContext;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@SpringBootTest
@AutoConfigureTestDatabase
public class PaperServiceTest {
    
    @Mock
    private PaperRepository paperRepository;

    @InjectMocks
    private PaperService paperService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindPaperTypes() {
        // Arrange
        List<PaperType> paperTypes = new ArrayList<>();
        when(paperRepository.findPaperTypes()).thenReturn(paperTypes);

        // Act
        List<PaperType> result = paperService.findPaperTypes();

        // Assert
        assertEquals(paperTypes, result);
    }

    @Test
    void testFindAll() {
        // Arrange
        List<Paper> papers = new ArrayList<>();
        when(paperRepository.findAll()).thenReturn(papers);

        // Act
        Collection<Paper> result = paperService.findAll();

        // Assert
        assertEquals(papers, result);
    }

    @Test
    void testFindPaperById() {
        // Arrange
        int id = 1;
        Paper paper = new Paper();
        when(paperRepository.findById(id)).thenReturn(Optional.of(paper));

        // Act
        Paper result = paperService.findPaperById(id);

        // Assert
        assertEquals(paper, result);
    }

    @Test
    void testFindPaperById_NotFound() {
        // Arrange
        int id = 1;
        when(paperRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> paperService.findPaperById(id));
    }

    @Test
    void testFindPaperByTitle() {
        // Arrange
        String title = "Test Paper";
        Paper paper = new Paper();
        when(paperRepository.findByTitle(title)).thenReturn(Optional.of(paper));

        // Act
        Paper result = paperService.findPaperByTitle(title);

        // Assert
        assertEquals(paper, result);
    }

        @Test
    void testFindAllPapersAbstractWord() {
        // Arrange
        String word = "abstract";
        List<Paper> papers = new ArrayList<>();
        papers.add(new Paper());
        when(paperRepository.findAllPapersByAbstractWord(word)).thenReturn(papers);

        // Act
        List<Paper> result = paperService.findAllPapersAbstractWord(word);

        // Assert
        assertEquals(papers, result);
    }

    // Test for findAllPapersByKeyword
    @Test
    void testFindAllPapersByKeyword() {
        // Arrange
        String keyword = "keyword";
        List<Paper> papers = new ArrayList<>();
        papers.add(new Paper());
        when(paperRepository.findAllPapersByKeyWord(keyword)).thenReturn(papers);

        // Act
        List<Paper> result = paperService.findAllPapersByKeyword(keyword);

        // Assert
        assertEquals(papers, result);
    }

    // Test for findAllPapersByType
    @Test
    void testFindAllPapersByType() {
        // Arrange
        String paperType = "type";
        List<Paper> papers = new ArrayList<>();
        papers.add(new Paper());
        when(paperRepository.findAllPapersByPaperType(paperType)).thenReturn(papers);

        // Act
        List<Paper> result = paperService.findAllPapersByType(paperType);

        // Assert
        assertEquals(papers, result);
    }

    // Test for findAllPapersByAuthor
    @Test
    void testFindAllPapersByAuthor() {
        // Arrange
        String author = "author";
        List<Paper> papers = new ArrayList<>();
        papers.add(new Paper());
        when(paperRepository.findAllPapersByAuthor(author)).thenReturn(papers);

        // Act
        List<Paper> result = paperService.findAllPapersByAuthor(author);

        // Assert
        assertEquals(papers, result);
    }

    // Test for findAllPapersByUserId
    @Test
    void testFindAllPapersByUserId() {
        // Arrange
        int id = 1;
        List<Paper> papers = new ArrayList<>();
        papers.add(new Paper());
        when(paperRepository.findAllPapersByUserId(id)).thenReturn(papers);

        // Act
        List<Paper> result = paperService.findAllPapersByUserId(id);

        // Assert
        assertEquals(papers, result);
    }

    // Test for deletePaper
    @Test
    void testDeletePaper() {
        // Arrange
        int id = 1;
        Paper paper = new Paper();
        when(paperRepository.findById(id)).thenReturn(Optional.of(paper));

        // Act
        assertDoesNotThrow(() -> paperService.deletePaper(id));

        // Assert
        verify(paperRepository, times(1)).delete(paper);
    }



    //Test for getPaperWithTitleAndIdDifferent
    @Test
    void testGetPaperWithTitleAndIdDifferent() {
        // Arrange
        Paper paper = new Paper();
        paper.setId(1);
        paper.setTitle("Title");
        List<Paper> papers = new ArrayList<>();
        papers.add(paper);
        when(paperRepository.findAllPapersByUserId(anyInt())).thenReturn(papers);

        // Act
        Paper result = assertDoesNotThrow(() -> paperService.getPaperWithTitleAndIdDifferent(paper));

        // Assert
        assertNull(result);
    }



}