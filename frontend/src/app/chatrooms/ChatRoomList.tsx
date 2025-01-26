"use client";

import React, { useState, useEffect } from "react";
import { useRouter } from "next/navigation"; // useRouter for navigation
import { ChatRoom } from "./types";

interface ChatRoomListProps {
    memberId: number;
}

const ChatRoomList: React.FC<ChatRoomListProps> = ({ memberId }) => {
    const [chatRooms, setChatRooms] = useState<ChatRoom[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const router = useRouter();

    useEffect(() => {
        const fetchChatRooms = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/v1/chatrooms/${memberId}`);
                if (!response.ok) {
                    throw new Error("채팅방 목록을 불러오는 데 실패했습니다.");
                }
                const data = await response.json();
                setChatRooms(data);
            } catch (error) {
                console.error("Error:", error);
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
            {chatRooms.length === 0 ? (
                <p className="text-center text-gray-800">가입된 채팅방이 없습니다.</p>
            ) : (
                <ul className="space-y-4">
                    {chatRooms.map((chatRoom) => (
                        <li
                            key={chatRoom.id}
                            onClick={() => router.push(`/chatrooms/${chatRoom.id}`)} // Navigate to the chat room
                            className="border border-gray-300 rounded-lg p-4 bg-white shadow hover:shadow-md transition cursor-pointer"
                        >
                            <h3 className="text-xl font-semibold text-gray-800 mb-2">
                                {chatRoom.club.name}
                            </h3>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default ChatRoomList;
