package testcase;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;

public class TestCase_003 extends BaseTest {
	// Checkout Item and Assert Total Amount
	@Test
	public void checkoutItem() throws InterruptedException {
		// Fill form page
		driver.findElement(AppiumBy.id("android:id/text1")).click();
		driver.findElement(
				AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Indonesia\"));"));
		driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='Indonesia']")).click();
		driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/nameField"))
				.sendKeys("Jannasha Lanaya Azzahra");
		driver.hideKeyboard();
		driver.findElement(AppiumBy.xpath("//android.widget.RadioButton[@text='Female']")).click();
		driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/btnLetsShop")).click();
		Thread.sleep(2000);

		// Add to cart items
		driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text='ADD TO CART']")).get(0).click();
		driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text='ADD TO CART']")).get(0).click();
		driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
		Thread.sleep(2000);

		// Assertion total amount
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.attributeContains(
				driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/toolbar_title")), "text", "Cart"));

		List<WebElement> productPrices = driver
				.findElements(AppiumBy.id("com.androidsample.generalstore:id/productPrice"));

		int pricesCount = productPrices.size();
		double sumPrice = 0;

		for (int i = 0; i < pricesCount; i++) {
			String productAmount = productPrices.get(i).getText();
			Double productPrice = getFormattedAmount(productAmount);
			sumPrice = sumPrice + productPrice;
		}

		String totalAmountText = driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/totalAmountLbl"))
				.getText();
		Double totalAmount = getFormattedAmount(totalAmountText);
		Assert.assertEquals(sumPrice, totalAmount);

		// Click Term & Conditions
		WebElement termConditions = driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/termsButton"));
		longPressAction(termConditions);
		driver.findElement(AppiumBy.id("android:id/button1")).click();
		driver.findElement(AppiumBy.className("android.widget.CheckBox")).click();

		// Checkout Items
		driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/btnProceed")).click();
		Thread.sleep(2000);

	}
}
