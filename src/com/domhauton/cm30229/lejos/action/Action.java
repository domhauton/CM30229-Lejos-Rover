package com.domhauton.cm30229.lejos.action;

import com.domhauton.cm30229.lejos.instruction.InstructionExecutionManager;

/**
 * Created by dominic on 08/02/17.
 */
public interface Action {
    void execute(InstructionExecutionManager executionManager);
}
