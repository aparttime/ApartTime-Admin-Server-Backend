package com.aparttime.websocket.dto;

/**
 * @param serverId     현재 연결된 서버의 고유 ID
 * @param sessionId    STOMP 세션의 고유 ID
 * @param lastPongTime 마지막 PONG 응답 시간 (Unix Timestamp, milliseconds, String 타입)
 * @param ipAddress    사용자의 접속 IP 주소
 * @param connectedAt  연결 시작 시간 (Unix Timestamp, milliseconds, String 타입)
 */

public record SessionInfo(
    String serverId,
    String sessionId,
    String lastPongTime,
    String ipAddress,
    String connectedAt
) {

    public static SessionInfo of(
        String serverId,
        String sessionId,
        String lastPongTime,
        String ipAddress,
        String connectedAt
    ) {
        return new SessionInfo(
            serverId,
            sessionId,
            lastPongTime,
            ipAddress,
            connectedAt
        );
    }

}
