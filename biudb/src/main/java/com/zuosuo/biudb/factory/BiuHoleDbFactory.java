package com.zuosuo.biudb.factory;

import com.zuosuo.biudb.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("BiuHoleDbFactory")
public class BiuHoleDbFactory {

    @Autowired
    private BiuHoleNoteImpl biuHoleNoteImpl;
    @Autowired
    private BiuMoodImpl moodImpl;
    @Autowired
    private BiuHoleNoteMoodImpl biuHoleNoteMoodImpl;
    @Autowired
    private BiuLabelImpl labelImpl;
    @Autowired
    private BiuHoleNoteLabelImpl biuHoleNoteLabelImpl;
    @Autowired
    private BiuHoleNoteCommentImpl biuHoleNoteCommentImpl;
    @Autowired
    private BiuUserFavorImpl biuUserFavorImpl;
    @Autowired
    private BiuHoleNoteViewImpl holeNoteViewImpl;
    @Autowired
    private BiuHoleNoteListViewImpl holeNoteListViewImpl;
    @Autowired
    private BiuHoleNoteCommentViewImpl holeNoteCommentViewImpl;

    public BiuHoleNoteImpl getBiuHoleNoteImpl() {
        return biuHoleNoteImpl;
    }

    public BiuMoodImpl getMoodImpl() {
        return moodImpl;
    }

    public BiuHoleNoteMoodImpl getBiuHoleNoteMoodImpl() {
        return biuHoleNoteMoodImpl;
    }

    public BiuLabelImpl getLabelImpl() {
        return labelImpl;
    }

    public BiuHoleNoteLabelImpl getBiuHoleNoteLabelImpl() {
        return biuHoleNoteLabelImpl;
    }

    public BiuHoleNoteCommentImpl getBiuHoleNoteCommentImpl() {
        return biuHoleNoteCommentImpl;
    }

    public BiuUserFavorImpl getBiuUserFavorImpl() {
        return biuUserFavorImpl;
    }

    public BiuHoleNoteViewImpl getHoleNoteViewImpl() {
        return holeNoteViewImpl;
    }

    public BiuHoleNoteListViewImpl getHoleNoteListViewImpl() {
        return holeNoteListViewImpl;
    }

    public BiuHoleNoteCommentViewImpl getHoleNoteCommentViewImpl() {
        return holeNoteCommentViewImpl;
    }
}
