package notjoe.pulse.common.content;

import io.vavr.collection.Vector;
import notjoe.pulse.api.guidebook.Guidebook;
import notjoe.pulse.api.guidebook.GuidebookEntry;

public final class GuidebookEntries {
    private Vector<GuidebookEntry> entries;

    public GuidebookEntries() {
        entries = Vector.empty();
    }

    public void addEntries(Vector<GuidebookEntry> newEntries) {
        entries = entries.appendAll(newEntries);
    }

    public Guidebook getGuidebook() {
        return new Guidebook(entries);
    }
}
