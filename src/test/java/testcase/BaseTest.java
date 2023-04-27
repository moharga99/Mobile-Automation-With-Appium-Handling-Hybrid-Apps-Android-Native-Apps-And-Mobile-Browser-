package testcase;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class BaseTest {

	public AndroidDriver driver;
	public AppiumDriverLocalService service;

	@BeforeClass
	public void configureAppium() throws MalformedURLException {
		// Configuration For Start Appium Service Automatically
		service = new AppiumServiceBuilder()
				.withAppiumJS(new File("C:\\Users\\user\\AppData\\Roaming\\npm\\node_modules\\appium\\lib\\main.js"))
				.withIPAddress("127.0.0.1").usingPort(4723).build();
		// Start Service
		// service.start();

		// Configuration for UiAutomator2
		UiAutomator2Options options = new UiAutomator2Options();
		options.setDeviceName("ArgaEmulator");
		options.setChromedriverExecutable("D:\\Course\\Driver\\ChromeDriver\\chromedriver.exe");
		options.setApp("D:\\Course\\Appium\\src\\General-Store.apk");

		// Declare AndroidDriver
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);

		// Config Timeout 10s
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

	// Method Long Press
	public void longPressAction(WebElement element) {
		((JavascriptExecutor) driver).executeScript("mobile: longClickGesture",
				ImmutableMap.of("elementId", ((RemoteWebElement) element).getId(), "duration", 2000));
	}

	// Method Scroll With Looping
	public void scrollAction() {
		boolean canScrollMore;
		do {
			canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap
					.of("left", 100, "top", 100, "width", 200, "height", 200, "direction", "down", "percent", 3.0));
		} while (canScrollMore);
	}

	// Method Swipe
	public void swipeAction(WebElement element, String direction) {
		((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of("elementId",
				((RemoteWebElement) element).getId(), "direction", direction, "percent", 0.75));
	}

	public Double getFormattedAmount(String amount) {
		Double price = Double.parseDouble(amount.substring(1));
		return price;
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
		service.stop();
	}
}
