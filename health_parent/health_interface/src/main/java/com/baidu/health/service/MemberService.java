package com.baidu.health.service;

import com.baidu.health.pojo.Member;

/**
 * <p>
 *
 * </p>
 *
 * @author: Eric
 * @since: 2021/1/12
 */
public interface MemberService {
    /**
     * 通过手机号码判断是否为会员
     * @param telephone
     * @return
     */
    Member findByTelephone(String telephone);

    /**
     * 通过手机号码判断是否为会员
     * @param member
     */
    void add(Member member);
}
