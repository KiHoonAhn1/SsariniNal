//package com.playssafy.playssafy.service;
//
//import com.playssafy.playssafy.dto.member.Member;
//import com.playssafy.playssafy.exception.MemberExistException;
//import com.playssafy.playssafy.exception.MemberNotFoundException;
//
//public interface MemberService {
//
//    /**
//     * 사용자 등록
//     *
//     * @param user 사용자정보
//     * @return User 사용자정보
//     */
//    Member save(Member user) throws MemberExistException;
//
//    /**
//     * 사용자정보 수정
//     *
//     * @param user 사용자정보
//     * @return User 사용자정보
//     */
//    Member update(Member user) throws MemberNotFoundException;
//
//    /**
//     * 사용자 정보 조회
//     *
//     * @param userId userId에 해당하는 사용자 정보 조회
//     */
//    Member findMemberByUserId(String userId) throws MemberNotFoundException;
//
//    /**
//     * 사용자 정보 조회
//     *
//     * @param id User 객체 Key
//     * @return 사용자 정보
//     * @throws MemberNotFoundException 사용자 정보 존재하지 않은 경우 예외처리
//     */
//    Member findById(Long id) throws MemberNotFoundException;
//
//    /**
//     * 사용자 삭제
//     *
//     * @param userId 삭제대상 사용자정보
//     */
//    void remove(String userId);
//
//}
