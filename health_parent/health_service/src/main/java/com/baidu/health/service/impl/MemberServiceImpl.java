package com.baidu.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baidu.health.dao.MemberDao;
import com.baidu.health.pojo.Member;
import com.baidu.health.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;


@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    /**
     * 通过手机号码判断是否为会员
     *
     * @param telephone
     * @return
     */
    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    /**
     * 通过手机号码判断是否为会员
     *
     * @param member
     */
    @Override
    public void add(Member member) {
        memberDao.add(member);
    }
}
