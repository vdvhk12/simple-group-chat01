"use client";

import React, { useState, useEffect } from 'react';
import { ChatRoom } from './types';

interface ChatRoomListProps {
    memberId: number;
}

const ChatRoomList: React.FC<ChatRoomListProps> = ({ memberId }) => {
    const [chatRooms, setChatRooms] = useState<ChatRoom[]>([]);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        const fetchChatRooms = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/v1/chatrooms/${memberId}`);
                if (!response.ok) {
                    throw new Error('채팅방 목록을 불러오는 데 실패했습니다.');
                }
                const data = await response.json();
                setChatRooms(data);
            } catch (error) {
                console.error('Error:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchChatRooms();
    }, [memberId]);

    if (loading) {
        return <div>로딩 중...</div>;
    }

    return (
        <div>
            <h2>채팅방 목록</h2>
            {chatRooms.length === 0 ? (
                <p>가입된 채팅방이 없습니다.</p>
            ) : (
                <ul>
                    {chatRooms.map((chatRoom) => (
                        <li key={chatRoom.id}>
                            <h3>{chatRoom.club.name}</h3>
                            <p>{chatRoom.club.description}</p>
                            <p>생성자: {chatRoom.club.creator}</p>
                            <p>참여자:</p>
                            <ul>
                                {chatRoom.club.members.map((member) => (
                                    <li key={member.memberId}>
                                        {member.username} ({member.role})
                                    </li>
                                ))}
                            </ul>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default ChatRoomList;