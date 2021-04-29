package cz.uhk.fim.bs.pickngo_web_be.BaguetteItem;

import cz.uhk.fim.bs.pickngo_web_be.BaguetteOrder.BaguetteOrder;
import cz.uhk.fim.bs.pickngo_web_be.BaguetteOrder.BaguetteOrderRepository;
import cz.uhk.fim.bs.pickngo_web_be.Customer.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BaguetteItemServiceTest {
    @Mock
    private BaguetteOrderRepository baguetteOrderRepository;
    @Mock private BaguetteItemRepository baguetteItemRepository;
    private BaguetteItemService underTest;
    @BeforeEach
    void setUp() {
        underTest = new BaguetteItemService(baguetteOrderRepository, baguetteItemRepository);
    }

    @Test
    void getBaguetteItems() {
        //when
        underTest.getBaguetteItems();
        //then
        verify(baguetteItemRepository).findAll();
    }

    @Test
    void getBaguetteItemsByBaguetteOrder() {
        BaguetteOrder baguetteOrder = new BaguetteOrder(55L, new Customer(),2.0, new Date(), 1, "note" );

        List<BaguetteItem> list = new ArrayList<>();
        BaguetteItem baguetteItem = new BaguetteItem(58L, baguetteOrder, 2.0, false);
        list.add(baguetteItem);
        baguetteOrder.setBaguetteItems(list);
        given(baguetteOrderRepository.findById(baguetteOrder.getId())).willReturn(Optional.of(baguetteOrder));
        given(baguetteItemRepository.findAllByBaguetteOrder_Id(baguetteOrder.getId())).willReturn(list);
        underTest.getBaguetteItemsByBaguetteOrder(baguetteOrder.getId());
        verify(baguetteItemRepository).findAllByBaguetteOrder_Id(baguetteOrder.getId());
    }

    @Test
    void getBaguetteItem() {
        Long id = 84L;
        BaguetteItem baguetteItem = new BaguetteItem(id, new BaguetteOrder(), 1.0, false);
        given(baguetteItemRepository.findById(id)).willReturn(Optional.of(baguetteItem));
        underTest.getBaguetteItem(id);
        verify(baguetteItemRepository).findById(id);
    }
}