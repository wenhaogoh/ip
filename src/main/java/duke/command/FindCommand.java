package duke.command;

import duke.DukeException;
import duke.Storage;
import duke.TaskList;

/**
 * Encapsulates a find command to be executed by Duke.
 * Finds and displays tasks containing the specific keywords from the TaskList.
 */
public class FindCommand extends Command {
    private String keyword;
    
    public FindCommand(String keyword) {
        super();
        this.keyword = keyword;
    }
    
    @Override
    public void execute(Storage storage, TaskList taskList) throws DukeException {
        taskList.findTasks(keyword);
    }
}
