package com.aparttime.websocket.dto;

/**
 * @param memberId    STOMP 세션의 사용자 ID
 * @param serverId    현재 연결된 서버의 고유 ID
 * @param sessionId   STOMP 세션의 고유 ID
 * @param ipAddress   사용자의 접속 IP 주소
 * @param connectedAt 연결 시작 시간 (Unix Timestamp, milliseconds, String 타입)
 */

public record SessionInfo(
    String memberId,
    String serverId,
    String sessionId,
    String ipAddress,
    String connectedAt
) {

    public static SessionInfo of(
        String memberId,
        String serverId,
        String sessionId,
        String ipAddress,
        String connectedAt
    ) {
        return new SessionInfo(
            memberId,
            serverId,
            sessionId,
            ipAddress,
            connectedAt
        );
    }

}
