package helpers;


import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReportHelper {

    public static void generateCucumberReport() {
        File reportOutputDirectory = new File("target");
        ArrayList<String> jsonFile = new ArrayList<String>();

        jsonFile.add("target/cucumber.json");

        String projectName = "testng-cucumber";

        Configuration configuration = new Configuration(reportOutputDirectory, projectName);
        configuration.addClassifications("Platform", System.getProperty("os.name"));
        configuration.addClassifications("Browser", "Chrome");
        configuration.addClassifications("Branch", "release/1.0");

        List<String> classificationsFiles = new ArrayList<String>();
        classificationsFiles.add("src/test/resources/config/config.properties");
        configuration.addClassificationFiles(classificationsFiles);

        ReportBuilder reportBuilder = new ReportBuilder(jsonFile, configuration);
        reportBuilder.generateReports();


    }
}
