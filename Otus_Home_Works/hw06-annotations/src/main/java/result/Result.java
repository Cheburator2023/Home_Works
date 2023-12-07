package result;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Result {

    //private Map<String, String> map = new HashMap<>();
    public void results(Map<String, String> map) {
        Integer tests = Integer.parseInt(map.get("Tests"));
        Integer passed = Integer.parseInt(map.get("Passed"));
        System.out.printf("Tests : %d\nPassed: %d\nFailed: %d\n",
                tests, passed, tests - passed);
    }
}
