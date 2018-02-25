package notjoe.pulse.common.capability;

import java.util.concurrent.Callable;

public class NoteHandlerFactory implements Callable<NoteHandler> {
    @Override
    public NoteHandler call() throws Exception {
        return (note, facing) -> {}; // What a sad lambda.
    }
}
