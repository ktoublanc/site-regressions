import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by kevin on 04/05/2016.
 */
@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:build/cucumber", "json:build/cucumber.json"})
public class CucumberTests {
}
