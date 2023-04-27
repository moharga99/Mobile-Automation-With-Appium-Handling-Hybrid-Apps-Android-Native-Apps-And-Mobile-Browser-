package testcase;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;

public class TestCase_001 extends BaseTest {
	// Fill Form and Checkout Item
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
		Thread.sleep(3000);

		// Add to cart item
		driver.findElement(AppiumBy
				.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Jordan 6 Rings\"));"));
		int listProduct = driver.findElements(AppiumBy.id("com.androidsample.generalstore:id/productName")).size();
		for (int i = 0; i < listProduct; i++) {
			String productName = driver.findElements(AppiumBy.id("com.androidsample.generalstore:id/productName"))
					.get(i).getText();
			if (productName.equalsIgnoreCase("Jordan 6 Rings")) {
				driver.findElements(AppiumBy.id("com.androidsample.generalstore:id/productAddCart")).get(i).click();
			}
		}
		driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();

		// Assertion chosen item
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.attributeContains(
				driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/toolbar_title")), "text", "Cart"));
		String chosenProduct = driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/productName"))
				.getText();
		Assert.assertEquals(chosenProduct, "Jordan 6 Rings");
	}
}
