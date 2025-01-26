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
        return <div className="text-center text-gray-800">로딩 중...</div>;
    }

    return (
        <div className="bg-gray-50 p-6 rounded-lg shadow-lg">
            <h2 className="text-2xl font-bold text-gray-800 mb-6 text-center">채팅방 목록</h2>
            {chatRooms.length === 0 ? (
                <p className="text-center text-gray-800">가입된 채팅방이 없습니다.</p>
            ) : (
                <ul className="space-y-4">
                    {chatRooms.map((chatRoom) => (
                        <li
                            key={chatRoom.id}
                            className="border border-gray-300 rounded-lg p-4 bg-white shadow hover:shadow-md transition"
                        >
                            <h3 className="text-xl font-semibold text-gray-800 mb-2">
                                {chatRoom.club.name}
                            </h3>
                            <p className="text-gray-700 mb-2">{chatRoom.club.description}</p>
                            <p className="text-gray-800 font-medium mb-3">
                                생성자: <span className="font-normal">{chatRoom.club.creator}</span>
                            </p>
                            <div>
                                <p className="text-gray-800 font-medium mb-2">참여자:</p>
                                <ul className="space-y-1 pl-4">
                                    {chatRoom.club.members.map((member) => (
                                        <li
                                            key={member.memberId}
                                            className="text-gray-700 flex items-center gap-2"
                                        >
                                            <span className="font-medium">{member.username}</span>
                                            <span className="text-sm text-gray-500">
                                                ({member.role})
                                            </span>
                                        </li>
                                    ))}
                                </ul>
                            </div>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default ChatRoomList;
