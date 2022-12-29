package com.zuosuo.biudb.config;

public enum BiuTableEnum {
    USER("biu_users"), USER_INDEX_VIEW("biu_user_index_views"), AREA("biu_areas"), INTEREST("biu_interests"), MESSAGE("biu_messages"),
    USER_INTEREST("biu_user_interests"), USER_COMMUNICATE("biu_user_communicates"), USER_IMAGE("biu_user_images"),
    USER_COLLECT("biu_user_collects"), HOLE_NOTE("biu_hole_notes"), MOOD("biu_moods"), HOLE_NOTE_MOOD("biu_hole_note_moods"),
    LABEL("biu_labels"), HOLE_NOTE_LABEL("biu_hole_note_labels"), USER_SEX("biu_user_sexes"), USER_VIEW("biu_user_views"),
    USER_REPORT("biu_user_reports"), USER_FAVOR("biu_user_favors"), HOLE_NOTE_COMMENT("biu_hole_note_comments"), HOLE_NOTE_VIEW("biu_hole_note_views"), HOLE_NOTE_LIST_VIEW("biu_hole_note_list_views"),
    USER_BLACKLIST("biu_user_blacklist"), USER_FRIEND("biu_user_friends"), USER_FRIEND_MEMBER("biu_user_friend_members"), USER_FRIEND_MESSAGE("biu_user_friend_messages"),
    USER_FRIEND_COMMUNICATE("biu_user_friend_communicates"), USER_FRIEND_COMMUNICATE_LOG("biu_user_friend_communicate_logs"),
    USER_READ_LOG("biu_user_read_logs"), HOLE_NOTE_COMMENT_VIEW("biu_hole_note_comment_views"), MESSAGE_VIEW("biu_message_views"),
    KEYWORD("biu_keywords"), WECHAT_FILTER_TRACE("biu_wechat_filter_traces");

    private String value;

    private BiuTableEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
