import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TaskRepository {
    private final String dbFilePath;

    public TaskRepository(String dbFilePath) {
        this.dbFilePath = dbFilePath;
    }

    public JSONArray loadTasks() {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(dbFilePath)) {
            Object obj = parser.parse(reader);
            if (obj instanceof JSONArray) {
                return (JSONArray) obj;
            }
        } catch (IOException | ParseException e) {
            logError("Lỗi khi đọc file database: " + e.getMessage());
        }
        return new JSONArray();
    }

    public void saveTasks(JSONArray tasksData) {
        try (FileWriter file = new FileWriter(dbFilePath)) {
            file.write(tasksData.toJSONString());
            file.flush();
        } catch (IOException e) {
            logError("Lỗi khi ghi vào file database: " + e.getMessage());
        }
    }

    private void logError(String message) {
        System.err.println(message);
    }
}
