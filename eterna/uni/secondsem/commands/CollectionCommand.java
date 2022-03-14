package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.CollectionManager;

public abstract class CollectionCommand extends Command {
    protected CollectionManager collection;
    public CollectionCommand(CollectionManager _collection) {
        collection = _collection;
    }
}
