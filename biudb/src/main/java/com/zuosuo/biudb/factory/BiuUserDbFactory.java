package com.zuosuo.biudb.factory;

import com.zuosuo.biudb.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("BiuUserDbFactory")
public class BiuUserDbFactory {

    @Autowired
    private BiuUserImpl biuUserImpl;
    @Autowired
    private BiuInterestImpl biuInterestImpl;
    @Autowired
    private BiuMessageImpl biuMessageImpl;
    @Autowired
    private BiuUserInterestImpl biuUserInterestImpl;
    @Autowired
    private BiuUserCommunicateImpl biuUserCommunicateImpl;
    @Autowired
    private BiuUserImageImpl biuUserImageImpl;
    @Autowired
    private BiuUserCollectImpl biuUserCollectImpl;
    @Autowired
    private BiuUserSexImpl biuUserSexImpl;
    @Autowired
    private BiuUserViewImpl biuUserViewImpl;
    @Autowired
    private BiuUserReportImpl biuUserReportImpl;
    @Autowired
    private BiuUserBlacklistImpl biuUserBlacklistImpl;
    @Autowired
    private BiuUserFriendImpl biuUserFriendImpl;
    @Autowired
    private BiuUserFriendMemberImpl biuUserFriendMemberImpl;
    @Autowired
    private BiuUserFriendCommunicateImpl biuUserFriendCommunicateImpl;
    @Autowired
    private BiuUserFriendCommunicateLogImpl biuUserFriendCommunicateLogImpl;

    public BiuUserImpl getBiuUserImpl() {
        return biuUserImpl;
    }

    public BiuInterestImpl getBiuInterestImpl() {
        return biuInterestImpl;
    }

    public BiuMessageImpl getBiuMessageImpl() {
        return biuMessageImpl;
    }

    public BiuUserInterestImpl getBiuUserInterestImpl() {
        return biuUserInterestImpl;
    }

    public BiuUserCommunicateImpl getBiuUserCommunicateImpl() {
        return biuUserCommunicateImpl;
    }

    public BiuUserImageImpl getBiuUserImageImpl() {
        return biuUserImageImpl;
    }

    public BiuUserCollectImpl getBiuUserCollectImpl() {
        return biuUserCollectImpl;
    }

    public BiuUserSexImpl getBiuUserSexImpl() {
        return biuUserSexImpl;
    }

    public BiuUserViewImpl getBiuUserViewImpl() {
        return biuUserViewImpl;
    }

    public BiuUserReportImpl getBiuUserReportImpl() {
        return biuUserReportImpl;
    }

    public BiuUserBlacklistImpl getBiuUserBlacklistImpl() {
        return biuUserBlacklistImpl;
    }

    public BiuUserFriendImpl getBiuUserFriendImpl() {
        return biuUserFriendImpl;
    }

    public BiuUserFriendMemberImpl getBiuUserFriendMemberImpl() {
        return biuUserFriendMemberImpl;
    }

    public BiuUserFriendCommunicateImpl getBiuUserFriendCommunicateImpl() {
        return biuUserFriendCommunicateImpl;
    }

    public BiuUserFriendCommunicateLogImpl getBiuUserFriendCommunicateLogImpl() {
        return biuUserFriendCommunicateLogImpl;
    }
}
