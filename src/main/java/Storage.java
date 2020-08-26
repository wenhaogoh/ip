import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private File file;
    
    public Storage(String path) throws DukeException {
        this.file = new File(path);
        try {
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        } catch (IOException e) {
            throw new DukeException("Error creating or locating storage.");
        }
    }

    public void saveTasks(List<Task> tasks) throws DukeException {
        try {
            FileWriter fileWriter = new FileWriter(file);
            for (Task task : tasks) {
                fileWriter.write(task.toData() + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            throw new DukeException("Error saving tasks to storage.");
        }
    }
    
    public List<Task> getTasks() throws DukeException {
        try {
            List<Task> tasks = new ArrayList<>();
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String input = bufferedReader.readLine();
            while (input != null) {
                String[] entryBreakdown = input.split(" \\| ");
                
                if (entryBreakdown.length < 3) {
                    throw new DukeException("File corrupted");
                }
                if (!(entryBreakdown[1].equals("0") || entryBreakdown[1].equals("1"))) {
                    throw new DukeException("File corrupted");
                }
                
                String type = entryBreakdown[0];
                boolean isDone = entryBreakdown[1].equals("1");
                String description = entryBreakdown[2];

                Task task;
                switch (type) {
                    case ("T"):
                        task = new Todo(description);
                        break;
                    case ("D"):
                        if (entryBreakdown.length != 4) {
                            throw new DukeException("File corrupted");
                        }
                        String by = entryBreakdown[3];
                        task = new Deadline(description, LocalDateTime.parse(by));
                        break;
                    case ("E"):
                        if (entryBreakdown.length != 4) {
                            throw new DukeException("File corrupted");
                        }
                        String at = entryBreakdown[3];
                        task = new Event(description, LocalDateTime.parse(at));
                        break;
                    default:
                        throw new DukeException("File corrupted.");
                }
                if (isDone) {
                    task.markAsDone();
                }
                tasks.add(task);
                
                input = bufferedReader.readLine();
            }
            return tasks;
        } catch (IOException e) {
            throw new DukeException("Error retrieving tasks from storage.");
        }
    }
}
