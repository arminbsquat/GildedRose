package fi.oulu.tol.sqat.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fi.oulu.tol.sqat.GildedRose;
import fi.oulu.tol.sqat.Item;

public class GildedRoseTest {

// Example scenarios for testing
//   Item("+5 Dexterity Vest", 10, 20));
//   Item("Aged Brie", 2, 0));
//   Item("Elixir of the Mongoose", 5, 7));
//   Item("Sulfuras, Hand of Ragnaros", 0, 80));
//   Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
//   Item("Conjured Mana Cake", 3, 6));

	// AgedBrie tests start
	@Test
	public void testUpdateEndOfDay_AgedBrie_Quality_10_11() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Aged Brie", 2, 10) );
		
		// Act
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store.getItems();
		Item itemBrie = items.get(0);
		assertEquals(11, itemBrie.getQuality());
	}
    
	@Test 
	public void testUpdateEndOfDay__AgedBrie_QualitylessThen50() {
		GildedRose store = new GildedRose();
		store.addItem(new Item("Aged Brie", 2, 50) );
		
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		
		List<Item> items = store.getItems();
		Item itemBrie = items.get(0);
		assertEquals(50, itemBrie.getQuality());
		
	}
	// AgedBrie tests end
	
	//Sulfuras tests start
	@Test 
	public void testUpdateEndOfDay_SulfurasQualityConstant() {
		GildedRose store = new GildedRose();
		store.addItem(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
		
		store.updateEndOfDay();
		
		List<Item> items = store.getItems();
		Item itemSulf = items.get(0);
		assertEquals(80, itemSulf.getQuality());	
	}
	//Sulfuras tests end
	
	// Backstage pass tests start 
	@Test
	public void testUpdateEndOfDay_BackstagePass_QualityIncreaseBy2_10orLessDaysLeft() {
		GildedRose store = new GildedRose();
		store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
		
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		
		store.updateEndOfDay();
		
		List<Item> items = store.getItems();
		Item itemBackst = items.get(0);
		assertEquals( 27, itemBackst.getQuality());	
	}
	
	@Test
	public void testUpdateEndOfDay_BackstagePass_QualityIncreaseBy3_5orLessDaysLeft() {
		GildedRose store = new GildedRose();
		store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
		
		store.updateEndOfDay(); // +1
		store.updateEndOfDay(); // +1
		store.updateEndOfDay(); // +1
		store.updateEndOfDay(); // +1
		store.updateEndOfDay(); // +1
		store.updateEndOfDay(); // +2
		store.updateEndOfDay(); // +2
		store.updateEndOfDay(); // +2
		store.updateEndOfDay(); // +2 
		store.updateEndOfDay(); // +2
		
		store.updateEndOfDay(); // +3 result: 38
		
		List<Item> items = store.getItems();
		Item itemBackst = items.get(0);
		assertEquals( 38, itemBackst.getQuality());	
	}
	
	@Test
	public void testUpdateEndOfDay_BackstagePass_QualityIncreaseBy1_20to21() {
		GildedRose store = new GildedRose();
		store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
		
		store.updateEndOfDay();
		
		List<Item> items = store.getItems();
		Item itemBackst = items.get(0);
		assertEquals( 21, itemBackst.getQuality());	
	}
	// Backstage pass tests end
	
	// usual item test 
	@Test 
	public void testUpdateEndOfDay_plus5_Dexterity_Vest_QualityDecrease20to19() {
		GildedRose store = new GildedRose();
		store.addItem(new Item("+5 Dexterity Vest", 10, 20));
		store.updateEndOfDay();
		
		List<Item> items = store.getItems();
		Item itemSulf = items.get(0);
		assertEquals(19, itemSulf.getQuality());
	}
	
	@Test 
	public void testUpdateEndOfDay_plus5_Dexterity_Vest_SellInDecrease10to9() {
		GildedRose store = new GildedRose();
		store.addItem(new Item("+5 Dexterity Vest", 10, 20));
		store.updateEndOfDay();
		
		List<Item> items = store.getItems();
		Item itemSulf = items.get(0);
		assertEquals(9, itemSulf.getSellIn());
	}
	
	@Test 
	public void testUpdateEndOfDay_plus5_Dexterity_Vest_DoubleQualityDecrease10to8() {
		GildedRose store = new GildedRose();
		store.addItem(new Item("+5 Dexterity Vest", 10, 20));
		
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay(); 
		store.updateEndOfDay(); //11th
		
		List<Item> items = store.getItems();
		Item itemSulf = items.get(0);
		assertEquals(8, itemSulf.getQuality());
	}
	
	@Test 
	public void testUpdateEndOfDay_plus5_Dexterity_Vest_NeverNegativeQuality() {
		GildedRose store = new GildedRose();
		store.addItem(new Item("+5 Dexterity Vest", 10, 20));
		
		// -1
		for( int i = 0; i < 10; i++)
		{
			store.updateEndOfDay();
		}

		// -2
		for( int i = 0; i < 5; i++)
		{
			store.updateEndOfDay();
		}
		
		store.updateEndOfDay(); //once more
		
		List<Item> items = store.getItems();
		Item itemSulf = items.get(0);
		assertEquals(0, itemSulf.getQuality());
	}
	// usual item test end
	
	
	
	
	@Test
	public void testUpdateEndOfDay() {
		fail("Test not implemented");
	}
}
