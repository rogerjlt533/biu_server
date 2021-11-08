package com.zuosuo.biudb.config;

public enum BiuTableEnum {
    USER("biu_users"), AREA("biu_areas"), INTEREST("biu_interests"), MESSAGE("biu_messages"),
    USER_INTEREST("biu_user_interests"), USER_COMMUNICATE("biu_user_communicates"), USER_IMAGE("biu_user_images"),
    USER_COLLECT("biu_user_collects"), HOLE_NOTE("biu_hole_notes"), MOOD("biu_moods"), HOLE_NOTE_MOOD("biu_hole_note_moods"),
    LABEL("biu_labels"), HOLE_NOTE_LABEL("biu_hole_note_labels"), USER_SEX("biu_user_sexes"), USER_VIEW("biu_user_views"),
    USER_REPORT("biu_user_reports"), USER_FAVOR("biu_user_favors"), HOLE_NOTE_COMMENT("biu_hole_note_comments"), HOLE_NOTE_VIEW("biu_hole_note_views");

    private String value;

    private BiuTableEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
