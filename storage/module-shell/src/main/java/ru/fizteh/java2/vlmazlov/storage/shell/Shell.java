package ru.fizteh.java2.vlmazlov.storage.shell;

import java.util.*;
import com.google.common.base.Joiner;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import ru.fizteh.java2.vlmazlov.storage.shell.commands.api.Command;
import ru.fizteh.java2.vlmazlov.storage.shell.commands.api.CommandFailException;
import ru.fizteh.java2.vlmazlov.storage.shell.commands.api.UserInterruptionException;
import ru.fizteh.java2.vlmazlov.storage.shell.commands.api.WrongCommandException;

import javax.annotation.PostConstruct;

@Controller
@Lazy
@Scope("prototype")

public class Shell<T extends ShellState> {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Shell.class);
    private static final String INVITATION = "$ ";

    private final Map<String, Command<T>> supportedCommands;

    @Autowired(required = true)
    private T state;

    @PostConstruct
    public void init() {
        logger.info("Shell has started");
        logger.info("Starting state: {}", state.getStateDescription());

        System.out.println("Supported commands:");

        for (String commandName: supportedCommands.keySet()) {
            System.out.println(commandName);
        }
    }

    @Autowired
    public Shell(Command<T>[] commands) {

        Map<String, Command<T>> supportedCommands = new TreeMap<String, Command<T>>();

        for (Command<T> command : commands) {
            supportedCommands.put(command.getName(), command);
        }

        this.supportedCommands = Collections.unmodifiableMap(supportedCommands);
    }

    public void process(String[] args)
            throws WrongCommandException, CommandFailException, UserInterruptionException {

        if (0 != args.length) {

            String arg = Joiner.on(" ").join(args);

            executeLine(arg);
        } else {
            interactiveMode();
        }
    }

    private String[] parseLine(String commandLine) {
        commandLine = commandLine.trim();
        return commandLine.split("(\\s*;\\s*)", -1);
    }

    private void executeLine(String commandLine)
            throws WrongCommandException, CommandFailException, UserInterruptionException {

        for (String exArg : parseLine(commandLine)) {
            logger.trace("Current state: {}", state.getStateDescription());

            invokeCommand(exArg.split("\\s+(?![^\\(]*\\))"));
        }
    }

    private void interactiveMode() {
        Scanner inputScanner = new Scanner(System.in);

        do {
            System.out.print(INVITATION);

            try {
                executeLine(inputScanner.nextLine());
            } catch (WrongCommandException | CommandFailException ex) {

                System.err.println(ex.getMessage());
            } catch (UserInterruptionException ex) {

                return;
            }

        } while (!Thread.currentThread().isInterrupted());
    }

    private void invokeCommand(String[] toExecute)
            throws WrongCommandException, CommandFailException, UserInterruptionException {
        //toExecute[0] should be the beginning of the command
        if (0 == toExecute.length) {
            throw new WrongCommandException("Empty command");
        }

        if (toExecute[0].matches("\\s*")) {
            throw new WrongCommandException("Syntax error near unexpected token ;");
        }

        Command invokedCommand = supportedCommands.get(toExecute[0]);

        if (null == invokedCommand) {
            throw new WrongCommandException("Unknown command: " + toExecute[0]);
        } else if ((toExecute.length - 1) != invokedCommand.getArgNum()) {
            throw new WrongCommandException("Ivalid number of arguments for " 
                + toExecute[0] + ": " + (toExecute.length - 1));
        }

        logger.info("Executing command {} ", toExecute[0]);

        invokedCommand.execute(Arrays.copyOfRange(toExecute, 1, toExecute.length), state, System.out);
    }
}

