package action;

import instruction.InstructionExecutionManager;

/**
 * Created by dominic on 08/02/17.
 */
public interface Action {
    void execute(InstructionExecutionManager executionManager);
}
