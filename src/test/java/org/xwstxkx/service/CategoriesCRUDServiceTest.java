//package org.xwstxkx.service;
//
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.xwstxkx.repository.CategoryRepository;
//import org.xwstxkx.service.crud.CategoriesCRUDService;
//
//@SpringBootTest
//class CategoriesCRUDServiceTest {
//
//    @InjectMocks
//    private CategoriesCRUDService categoriesCRUDService;
//
//    @Mock
//    private CategoryRepository categoryRepository;
//
////    @BeforeEach
////    public void init {
////
////    }
////    @Test
////    void getCategory() throws ObjectNotFound {
////        CategoryEntity category_1 = new CategoryEntity(1L, "Категория", null, null);
////        when(categoryRepository.findById(1L)).thenReturn(Optional.ofNullable(category_1));
////
////        CategoryModel category_2 = categoriesCRUDService.getCategory(1L);
////        Assertions.assertNotNull(category_2);
////        Assertions.assertEquals(category_2.getName(), "Категория");
////    }
//}