public class UnknownCommand extends Command {
    public UnknownCommand() {
        super();
    }

    @Override
    public void execute(Storage storage, TaskList taskList) {
        Ui.addMessage("Sorry, but I don't know what that means \u2639");
        Ui.sendMessages();
    }
}
