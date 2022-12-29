package com.zuosuo.treehole.factory;

import com.zuosuo.biudb.entity.BiuHoleNoteListViewEntity;
import com.zuosuo.biudb.entity.BiuLabelEntity;
import com.zuosuo.biudb.entity.BiuMoodEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NoteCache {

    public BiuHoleNoteListViewEntity entity;
    public BiuMoodEntity mood;
    public BiuLabelEntity label;
    public NoteCacheListCount comments;
    public Map favors;
    public List<String> images;

    public NoteCache(BiuHoleNoteListViewEntity entity) {
        this.entity = entity;
        images = new ArrayList<>();
    }

    public void init(BiuHoleNoteListViewEntity entity) {
        this.entity = entity;
        mood = null;
        label = null;
        comments = null;
        favors = null;
        images = new ArrayList<>();
    }
}
