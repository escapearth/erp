package org.erp.model.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberStatusType {

    /**
     * 조직원 정상 사용 상태
     */
    WORKER("1"),

    /**
     * 조직원 휴직 상태
     */
    RETIRE("2"),

    /**
     * 조직원 삭제 상태
     */
    DELETED("3"),

    /**
     * 조직원 퇴사 상태
     */
    LEAVE("4");

    private String status;

    @Override
    public String toString() {
        return super.toString();
    }
}
