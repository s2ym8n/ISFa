package isf;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class Config {
    
    private Map<String, Employee> employees;

    public Config(String storeConfigPath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Read store configuration file
        byte[] storeConfigBytes = Files.readAllBytes(Paths.get(storeConfigPath));
        String storeConfigJson = new String(storeConfigBytes, "UTF-8");

        // Deserialize store configuration JSON to Map<String, Employee>
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        employees = objectMapper.readValue(storeConfigJson, typeFactory.constructMapType(HashMap.class, String.class, Employee.class));
    }

    public Map<String, Employee> getEmployees() {
        return employees;
    }

}
