package ru.icc.cells.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunveil on 27/06/16.
 */
public class TextBlock extends Rectangle {

    public TextBlock() {
        super();
    }

    private List<TextChunk> chunks = new ArrayList<>();

    public List<TextChunk> getChunks() {
        return chunks;
    }

    public void add(TextChunk textChunk) {
        join(textChunk);
        chunks.add(textChunk);
    }

    public void add(TextBlock textBlock) {
        join(textBlock);
        chunks.addAll(textBlock.chunks);
    }

    @Override
    protected <T extends Rectangle> void join(T other) {
        if (isEmpty()) {
            setLeft(other.getLeft());
            setBottom(other.getBottom());
            setRight(other.getRight());
            setTop(other.getTop());
        } else {
            super.join(other);
        }
    }

    public boolean isEmpty() {
        return chunks.isEmpty();
    }

    public String getText() {
        StringBuilder stringBuilder = new StringBuilder();
        for (TextChunk chunk : chunks) {
            stringBuilder.append(chunk.getText());
        }
        return stringBuilder.toString();
    }
}
