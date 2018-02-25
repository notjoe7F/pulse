package notjoe.pulse.api.guidebook;

import io.vavr.collection.Vector;

public class Guidebook {
    private final Vector<GuidebookEntry> entries;

    public Guidebook(Vector<GuidebookEntry> entries) {
        this.entries = entries;
    }

    public Vector<GuidebookEntry> getEntriesOnPage(GuidebookCategory category) {
        return entries.filter(guidebookEntry -> guidebookEntry.getCategory() == category);
    }

    public Vector<GuidebookEntry> getEntries() {
        return entries;
    }
}
